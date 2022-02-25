package drunkblood.nethershulker;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.dispenser.ShulkerBoxDispenseBehavior;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.client.renderer.Sheets.SHULKER_SHEET;

@Mod(NetheriteShulker.MODID)
public class NetheriteShulker {
    public static final String MODID = "nethershulker";
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);

    public static final String NETHERITE_SHULKER_REGISTRY_NAME = "netherite_shulker";
    public static final RegistryObject<BlockEntityType<NetheriteShulkerBlockEntity>> NETHERITE_SHULKER_BLOCK_ENTITY;

    static {
        NETHERITE_SHULKER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
                NETHERITE_SHULKER_REGISTRY_NAME,
                () -> {
                    Block[] validBlocks = new Block[17];
                    validBlocks[16] = ModBlocks.NETHERITE_SHULKER_DEFAULT.get();
                    for (DyeColor dyeColor : DyeColor.values()){
                        Block block = NetheriteShulkerBlock.getBlockByColor(dyeColor);
                        validBlocks[dyeColor.getId()] = block;
                    }
                    return BlockEntityType.Builder.of(NetheriteShulkerBlockEntity::new, validBlocks).build(null);
                }
        );

    }
    public static final RegistryObject<MenuType<NetheriteShulkerMenu>> NETHERITE_SHULKER_CONTAINER = CONTAINERS.register(
            NETHERITE_SHULKER_REGISTRY_NAME,
            () -> IForgeMenuType.create(((windowId, inv, data) -> new NetheriteShulkerMenu(windowId, data.readBlockPos(), inv, inv.player)))
    );

    public NetheriteShulker(){
        final IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.BLOCKS.register(modBus);
        ModItems.ITEMS.register(modBus);
        BLOCK_ENTITY_TYPES.register(modBus);
        CONTAINERS.register(modBus);
        modBus.addListener(this::gatherData);
        modBus.addListener(this::textureStitch);
        modBus.addListener(this::clientSetup);
        modBus.addListener(this::registerBERenders);
        modBus.addListener(this::commonSetup);
    }

    /*
    --------------------------------------------------------------------
    EVENTS
    --------------------------------------------------------------------
     */

    public void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new GeneratorProviders.ModRecipes(generator));
            generator.addProvider(new GeneratorProviders.ModLootTables(generator));
        }
        if (event.includeClient()) {
            generator.addProvider(new GeneratorProviders.ModBlockStates(generator, event.getExistingFileHelper()));
            generator.addProvider(new GeneratorProviders.ModBlockTags(generator, event.getExistingFileHelper()));
            generator.addProvider(new GeneratorProviders.ModItemModels(generator, event.getExistingFileHelper()));
            generator.addProvider(new GeneratorProviders.EnglishLanguageProvider(generator, "en_us"));
        }
    }

    public void textureStitch(TextureStitchEvent.Pre event){
        if(!event.getAtlas().location().equals(SHULKER_SHEET)){
            return;
        }
        for(Material material : NetheriteShulkerRenderer.TEXTURE_LOCATIONS){
            event.addSprite(material.texture());
        }
    }

    public void clientSetup(FMLClientSetupEvent event){
        event.enqueueWork(() -> {
            MenuScreens.register(NETHERITE_SHULKER_CONTAINER.get(), NetheriteShulkerScreen::new);
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.NETHERITE_SHULKER_DEFAULT.get(), RenderType.translucent());
        });
    }

    public void commonSetup(FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            DispenserBlock.registerBehavior(ModBlocks.NETHERITE_SHULKER_DEFAULT.get().asItem(), new ShulkerBoxDispenseBehavior());

            for(DyeColor dyecolor : DyeColor.values()) {
                DispenserBlock.registerBehavior(NetheriteShulkerBlock.getBlockByColor(dyecolor).asItem(), new ShulkerBoxDispenseBehavior());
            }
        });
    }

    public void registerBERenders(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(NetheriteShulker.NETHERITE_SHULKER_BLOCK_ENTITY.get(), NetheriteShulkerRenderer::new);
    }

}
