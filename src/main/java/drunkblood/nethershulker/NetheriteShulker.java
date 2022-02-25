package drunkblood.nethershulker;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

import static drunkblood.nethershulker.NetheriteShulkerBlock.SHULKER_SCREEN_NAME;

@Mod(NetheriteShulker.MODID)
public class NetheriteShulker {
    public static final String MODID = "nethershulker";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);

    public static final String NETHERITE_SHULKER_REGISTRY_NAME = "netherite_shulker";
    public static final RegistryObject<Block> NETHERITE_SHULKER = BLOCKS.register(
            NETHERITE_SHULKER_REGISTRY_NAME,
            () -> new NetheriteShulkerBlock(Block.Properties.copy(Blocks.SHULKER_BOX))
    );
    public static final RegistryObject<Item> NETHERITE_SHULKER_ITEM = ITEMS.register(
            NETHERITE_SHULKER_REGISTRY_NAME,
            () -> new BlockItem(NETHERITE_SHULKER.get(), new Item.Properties()
                    .tab(CreativeModeTab.TAB_TRANSPORTATION)
                    .fireResistant()
                    .stacksTo(1))
    );
    public static final RegistryObject<BlockEntityType<NetheriteShulkerBlockEntity>> NETHERITE_SHULKER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            NETHERITE_SHULKER_REGISTRY_NAME,
            () -> BlockEntityType.Builder.of(NetheriteShulkerBlockEntity::new, NETHERITE_SHULKER.get()).build(null)
    );
    public static final RegistryObject<MenuType<NetheriteShulkerMenu>> NETHERITE_SHULKER_CONTAINER = CONTAINERS.register(
            NETHERITE_SHULKER_REGISTRY_NAME,
            () -> IForgeMenuType.create(((windowId, inv, data) -> new NetheriteShulkerMenu(windowId, data.readBlockPos(), inv, inv.player)))
    );

    public NetheriteShulker(){
        final IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        BLOCK_ENTITY_TYPES.register(modBus);
        CONTAINERS.register(modBus);
        modBus.addListener(this::clientSetup);
        modBus.addListener(this::gatherData);
    }

    public void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new ModRecipes(generator));
            generator.addProvider(new ModLootTables(generator));
        }
        if (event.includeClient()) {
            generator.addProvider(new ModBlockStates(generator, event.getExistingFileHelper()));
            generator.addProvider(new ModBlockTags(generator, event.getExistingFileHelper()));
            generator.addProvider(new ModItemModels(generator, event.getExistingFileHelper()));
            generator.addProvider(new EnglishLanguageProvider(generator, "en_us"));
        }
    }
    public void clientSetup(FMLClientSetupEvent event){
        event.enqueueWork(() -> {
            MenuScreens.register(NETHERITE_SHULKER_CONTAINER.get(), NetheriteShulkerScreen::new);
            ItemBlockRenderTypes.setRenderLayer(NETHERITE_SHULKER.get(), RenderType.translucent());
            NetheriteShulkerRenderer.register();
        });
    }

    public static class ModBlockStates extends BlockStateProvider {

        public ModBlockStates(DataGenerator gen, ExistingFileHelper helper) {
            super(gen, MODID, helper);
        }

        @Override
        protected void registerStatesAndModels() {
            simpleBlock(NETHERITE_SHULKER.get());
        }
    }

    public static class ModBlockTags extends BlockTagsProvider {

        public ModBlockTags(DataGenerator generator, ExistingFileHelper helper) {
            super(generator, MODID, helper);
        }

        @Override
        protected void addTags() {
            tag(BlockTags.MINEABLE_WITH_PICKAXE)
                    .add(NETHERITE_SHULKER.get());
        }
    }

    public static class ModItemModels extends ItemModelProvider {

        public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
            super(generator, MODID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            withExistingParent(NETHERITE_SHULKER_ITEM.get().getRegistryName().getPath(), modLoc("block/" + NETHERITE_SHULKER_REGISTRY_NAME));
        }
    }

    private static class EnglishLanguageProvider extends LanguageProvider {

        public EnglishLanguageProvider(DataGenerator gen, String locale) {
            super(gen, MODID, locale);
        }

        @Override
        protected void addTranslations() {
            add(NETHERITE_SHULKER.get(), "Netherite Shulker");
            add(SHULKER_SCREEN_NAME, "Netherite Shulker");
        }
    }

    private static class ModRecipes extends RecipeProvider{

        public ModRecipes(DataGenerator generator) {
            super(generator);
        }

        @Override
        protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
            Ingredient base = Ingredient.of(Items.DIAMOND); // TODO change to shulker
            Ingredient addition = Ingredient.of(Items.NETHERITE_INGOT);
            Item result = NETHERITE_SHULKER_ITEM.get();
            UpgradeRecipeBuilder.smithing(base,addition,result)
                    .unlocks("has_netherite_ingot", RecipeProvider.has(Items.NETHERITE_INGOT))
                    .save(consumer, "netherite_shulker_smithing");
        }
    }

    private static class ModLootTables extends BaseLootTableProvider {

        public ModLootTables(DataGenerator generator) {
            super(generator);
        }

        @Override
        protected void addTables() {
            lootTables.put(NETHERITE_SHULKER.get(),
                    createStandardTable(
                            NETHERITE_SHULKER_REGISTRY_NAME,
                            NETHERITE_SHULKER.get(),
                            NETHERITE_SHULKER_BLOCK_ENTITY.get()));
        }
    }
}
