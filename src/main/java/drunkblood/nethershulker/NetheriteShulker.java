package drunkblood.nethershulker;

import drunkblood.nethershulker.block.ModBlocks;
import drunkblood.nethershulker.block.NetheriteShulkerBlock;
import drunkblood.nethershulker.blockentity.NetheriteShulkerBlockEntity;
import drunkblood.nethershulker.data.GeneratorProviders;
import drunkblood.nethershulker.item.ModItems;
import drunkblood.nethershulker.recipe.NetheriteShulkerColoring;
import drunkblood.nethershulker.recipe.NetheriteShulkerUpgradeRecipe;
import drunkblood.nethershulker.render.NetheriteShulkerMenu;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;

@Mod(NetheriteShulker.MODID)
public class NetheriteShulker {
    public static final String MODID = "nethershulker";
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);

    public static final String NETHERITE_SHULKER_REGISTRY_NAME = "netherite_shulker";
    public static final RegistryObject<BlockEntityType<NetheriteShulkerBlockEntity>> NETHERITE_SHULKER_BLOCK_ENTITY;
    public static final RegistryObject<MenuType<NetheriteShulkerMenu>> NETHERITE_SHULKER_CONTAINER;
    public static final RegistryObject<SimpleRecipeSerializer<NetheriteShulkerColoring>> NETHERITE_SHULKER_COLORING;
    public static final RegistryObject<RecipeSerializer<NetheriteShulkerUpgradeRecipe>> NETHERITE_SHULKER_SMITHING;

    static {
        NETHERITE_SHULKER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
                NETHERITE_SHULKER_REGISTRY_NAME,
                () -> {
                    Set<Block> blockSet = new HashSet<>();
                    blockSet.add(ModBlocks.NETHERITE_SHULKER_DEFAULT.get());
                    for (DyeColor dyeColor : DyeColor.values()){
                        Block block = NetheriteShulkerBlock.getBlockByColor(dyeColor);
                        blockSet.add(block);
                    }
                    return new BlockEntityType<>(NetheriteShulkerBlockEntity::new, blockSet, null);
//                    return BlockEntityType.Builder.of(NetheriteShulkerBlockEntity::new, validBlocks).build(null);
                }
        );
        NETHERITE_SHULKER_CONTAINER = CONTAINERS.register(
                NETHERITE_SHULKER_REGISTRY_NAME,
                () -> IForgeMenuType.create(((windowId, inv, data) -> new NetheriteShulkerMenu(windowId, data.readBlockPos(), inv, inv.player)))
        );
        NETHERITE_SHULKER_COLORING = RECIPE_SERIALIZERS.register("netherite_shulker_coloring",
                () -> new SimpleRecipeSerializer<>(NetheriteShulkerColoring::new));
        NETHERITE_SHULKER_SMITHING = RECIPE_SERIALIZERS.register("netherite_shulker_smithing",
                NetheriteShulkerUpgradeRecipe.Serializer::new);
    }

    public NetheriteShulker(){
        FMLJavaModLoadingContext loadingContext = FMLJavaModLoadingContext.get();
        final IEventBus modBus = loadingContext.getModEventBus();
        ModBlocks.BLOCKS.register(modBus);
        ModItems.ITEMS.register(modBus);
        BLOCK_ENTITY_TYPES.register(modBus);
        CONTAINERS.register(modBus);
        RECIPE_SERIALIZERS.register(modBus);
    }


}
