package drunkblood.nethershulker.block;

import drunkblood.nethershulker.NetheriteShulker;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final RegistryObject<Block> NETHERITE_SHULKER_DEFAULT;
    public static final RegistryObject<Block> NETHERITE_SHULKER_WHITE;
    public static final RegistryObject<Block> NETHERITE_SHULKER_ORANGE;
    public static final RegistryObject<Block> NETHERITE_SHULKER_MAGENTA;
    public static final RegistryObject<Block> NETHERITE_SHULKER_LIGHT_BLUE;
    public static final RegistryObject<Block> NETHERITE_SHULKER_YELLOW;
    public static final RegistryObject<Block> NETHERITE_SHULKER_LIME;
    public static final RegistryObject<Block> NETHERITE_SHULKER_PINK;
    public static final RegistryObject<Block> NETHERITE_SHULKER_GRAY;
    public static final RegistryObject<Block> NETHERITE_SHULKER_LIGHT_GRAY;
    public static final RegistryObject<Block> NETHERITE_SHULKER_CYAN;
    public static final RegistryObject<Block> NETHERITE_SHULKER_PURPLE;
    public static final RegistryObject<Block> NETHERITE_SHULKER_BLUE;
    public static final RegistryObject<Block> NETHERITE_SHULKER_BROWN;
    public static final RegistryObject<Block> NETHERITE_SHULKER_GREEN;
    public static final RegistryObject<Block> NETHERITE_SHULKER_RED;
    public static final RegistryObject<Block> NETHERITE_SHULKER_BLACK;
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, NetheriteShulker.MODID);

    static {
        NETHERITE_SHULKER_DEFAULT = BLOCKS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME,
                () -> new NetheriteShulkerBlock(Block.Properties.copy(Blocks.SHULKER_BOX))
        );
        NETHERITE_SHULKER_WHITE = BLOCKS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "white",
                () -> new NetheriteShulkerBlock(DyeColor.WHITE, Block.Properties.copy(Blocks.WHITE_SHULKER_BOX))
        );
        NETHERITE_SHULKER_ORANGE = BLOCKS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "orange",
                () -> new NetheriteShulkerBlock(DyeColor.ORANGE, Block.Properties.copy(Blocks.ORANGE_SHULKER_BOX))
        );
        NETHERITE_SHULKER_MAGENTA = BLOCKS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "magenta",
                () -> new NetheriteShulkerBlock(DyeColor.MAGENTA, Block.Properties.copy(Blocks.MAGENTA_SHULKER_BOX))
        );
        NETHERITE_SHULKER_LIGHT_BLUE = BLOCKS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "light_blue",
                () -> new NetheriteShulkerBlock(DyeColor.LIGHT_BLUE, Block.Properties.copy(Blocks.LIGHT_BLUE_SHULKER_BOX))
        );
        NETHERITE_SHULKER_YELLOW = BLOCKS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "yellow",
                () -> new NetheriteShulkerBlock(DyeColor.YELLOW, Block.Properties.copy(Blocks.YELLOW_SHULKER_BOX))
        );
        NETHERITE_SHULKER_LIME = BLOCKS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "lime",
                () -> new NetheriteShulkerBlock(DyeColor.LIME, Block.Properties.copy(Blocks.LIME_SHULKER_BOX))
        );
        NETHERITE_SHULKER_PINK = BLOCKS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "pink",
                () -> new NetheriteShulkerBlock(DyeColor.PINK, Block.Properties.copy(Blocks.PINK_SHULKER_BOX))
        );
        NETHERITE_SHULKER_GRAY = BLOCKS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "gray",
                () -> new NetheriteShulkerBlock(DyeColor.GRAY, Block.Properties.copy(Blocks.GRAY_SHULKER_BOX))
        );
        NETHERITE_SHULKER_LIGHT_GRAY = BLOCKS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "light_gray",
                () -> new NetheriteShulkerBlock(DyeColor.LIGHT_GRAY, Block.Properties.copy(Blocks.LIGHT_GRAY_SHULKER_BOX))
        );
        NETHERITE_SHULKER_CYAN = BLOCKS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "cyan",
                () -> new NetheriteShulkerBlock(DyeColor.CYAN, Block.Properties.copy(Blocks.CYAN_SHULKER_BOX))
        );
        NETHERITE_SHULKER_PURPLE = BLOCKS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "purple",
                () -> new NetheriteShulkerBlock(DyeColor.PURPLE, Block.Properties.copy(Blocks.PURPLE_SHULKER_BOX))
        );
        NETHERITE_SHULKER_BLUE = BLOCKS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "blue",
                () -> new NetheriteShulkerBlock(DyeColor.BLUE, Block.Properties.copy(Blocks.BLUE_SHULKER_BOX))
        );
        NETHERITE_SHULKER_BROWN = BLOCKS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "brown",
                () -> new NetheriteShulkerBlock(DyeColor.BROWN, Block.Properties.copy(Blocks.BROWN_SHULKER_BOX))
        );
        NETHERITE_SHULKER_GREEN = BLOCKS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "green",
                () -> new NetheriteShulkerBlock(DyeColor.GREEN, Block.Properties.copy(Blocks.GREEN_SHULKER_BOX))
        );
        NETHERITE_SHULKER_RED = BLOCKS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "red",
                () -> new NetheriteShulkerBlock(DyeColor.RED, Block.Properties.copy(Blocks.RED_SHULKER_BOX))
        );
        NETHERITE_SHULKER_BLACK = BLOCKS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "black",
                () -> new NetheriteShulkerBlock(DyeColor.BLACK, Block.Properties.copy(Blocks.BLACK_SHULKER_BOX))
        );
    }
}
