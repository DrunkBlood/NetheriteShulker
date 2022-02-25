package drunkblood.nethershulker;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final RegistryObject<Item> NETHERITE_SHULKER_DEFAULT;
    public static final RegistryObject<Item> NETHERITE_SHULKER_WHITE;
    public static final RegistryObject<Item> NETHERITE_SHULKER_ORANGE;
    public static final RegistryObject<Item> NETHERITE_SHULKER_MAGENTA;
    public static final RegistryObject<Item> NETHERITE_SHULKER_LIGHT_BLUE;
    public static final RegistryObject<Item> NETHERITE_SHULKER_YELLOW;
    public static final RegistryObject<Item> NETHERITE_SHULKER_LIME;
    public static final RegistryObject<Item> NETHERITE_SHULKER_PINK;
    public static final RegistryObject<Item> NETHERITE_SHULKER_GRAY;
    public static final RegistryObject<Item> NETHERITE_SHULKER_LIGHT_GRAY;
    public static final RegistryObject<Item> NETHERITE_SHULKER_CYAN;
    public static final RegistryObject<Item> NETHERITE_SHULKER_PURPLE;
    public static final RegistryObject<Item> NETHERITE_SHULKER_BLUE;
    public static final RegistryObject<Item> NETHERITE_SHULKER_BROWN;
    public static final RegistryObject<Item> NETHERITE_SHULKER_GREEN;
    public static final RegistryObject<Item> NETHERITE_SHULKER_RED;
    public static final RegistryObject<Item> NETHERITE_SHULKER_BLACK;
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, NetheriteShulker.MODID);

    static {
        NETHERITE_SHULKER_DEFAULT = ITEMS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME,
                () -> new NetheriteShulkerItem(ModBlocks.NETHERITE_SHULKER_DEFAULT.get())
        );
        NETHERITE_SHULKER_WHITE = ITEMS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "white",
                () -> new NetheriteShulkerItem(ModBlocks.NETHERITE_SHULKER_WHITE.get())
        );
        NETHERITE_SHULKER_ORANGE = ITEMS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "orange",
                () -> new NetheriteShulkerItem(ModBlocks.NETHERITE_SHULKER_ORANGE.get())
        );
        NETHERITE_SHULKER_MAGENTA = ITEMS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "magenta",
                () -> new NetheriteShulkerItem(ModBlocks.NETHERITE_SHULKER_MAGENTA.get())
        );
        NETHERITE_SHULKER_LIGHT_BLUE = ITEMS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "light_blue",
                () -> new NetheriteShulkerItem(ModBlocks.NETHERITE_SHULKER_LIGHT_BLUE.get())
        );
        NETHERITE_SHULKER_YELLOW = ITEMS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "yellow",
                () -> new NetheriteShulkerItem(ModBlocks.NETHERITE_SHULKER_YELLOW.get())
        );
        NETHERITE_SHULKER_LIME = ITEMS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "lime",
                () -> new NetheriteShulkerItem(ModBlocks.NETHERITE_SHULKER_LIME.get())
        );
        NETHERITE_SHULKER_PINK = ITEMS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "pink",
                () -> new NetheriteShulkerItem(ModBlocks.NETHERITE_SHULKER_PINK.get())
        );
        NETHERITE_SHULKER_GRAY = ITEMS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "gray",
                () -> new NetheriteShulkerItem(ModBlocks.NETHERITE_SHULKER_GRAY.get())
        );
        NETHERITE_SHULKER_LIGHT_GRAY = ITEMS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "light_gray",
                () -> new NetheriteShulkerItem(ModBlocks.NETHERITE_SHULKER_LIGHT_GRAY.get())
        );
        NETHERITE_SHULKER_CYAN = ITEMS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "cyan",
                () -> new NetheriteShulkerItem(ModBlocks.NETHERITE_SHULKER_CYAN.get())
        );
        NETHERITE_SHULKER_PURPLE = ITEMS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "purple",
                () -> new NetheriteShulkerItem(ModBlocks.NETHERITE_SHULKER_PURPLE.get())
        );
        NETHERITE_SHULKER_BLUE = ITEMS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "blue",
                () -> new NetheriteShulkerItem(ModBlocks.NETHERITE_SHULKER_BLUE.get())
        );
        NETHERITE_SHULKER_BROWN = ITEMS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "brown",
                () -> new NetheriteShulkerItem(ModBlocks.NETHERITE_SHULKER_BROWN.get())
        );
        NETHERITE_SHULKER_GREEN = ITEMS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "green",
                () -> new NetheriteShulkerItem(ModBlocks.NETHERITE_SHULKER_GREEN.get())
        );
        NETHERITE_SHULKER_RED = ITEMS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "red",
                () -> new NetheriteShulkerItem(ModBlocks.NETHERITE_SHULKER_RED.get())
        );
        NETHERITE_SHULKER_BLACK = ITEMS.register(
                NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME + "_" + "black",
                () -> new NetheriteShulkerItem(ModBlocks.NETHERITE_SHULKER_BLACK.get())
        );}
}
