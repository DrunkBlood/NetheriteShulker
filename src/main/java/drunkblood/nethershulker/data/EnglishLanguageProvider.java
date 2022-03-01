package drunkblood.nethershulker.data;

import drunkblood.nethershulker.NetheriteShulker;
import drunkblood.nethershulker.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import static drunkblood.nethershulker.block.NetheriteShulkerBlock.SHULKER_SCREEN_NAME;

public class EnglishLanguageProvider extends LanguageProvider {
    public EnglishLanguageProvider(DataGenerator gen, String locale) {
        super(gen, NetheriteShulker.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(ModBlocks.NETHERITE_SHULKER_DEFAULT.get(), "Netherite Shulker");
        add(ModBlocks.NETHERITE_SHULKER_WHITE.get(), "White Netherite Shulker");
        add(ModBlocks.NETHERITE_SHULKER_ORANGE.get(), "Orange Netherite Shulker");
        add(ModBlocks.NETHERITE_SHULKER_MAGENTA.get(), "Magenta Netherite Shulker");
        add(ModBlocks.NETHERITE_SHULKER_LIGHT_BLUE.get(), "Light Blue Netherite Shulker");
        add(ModBlocks.NETHERITE_SHULKER_YELLOW.get(), "Yellow Netherite Shulker");
        add(ModBlocks.NETHERITE_SHULKER_LIME.get(), "Lime Netherite Shulker");
        add(ModBlocks.NETHERITE_SHULKER_PINK.get(), "Pink Netherite Shulker");
        add(ModBlocks.NETHERITE_SHULKER_GRAY.get(), "Gray Netherite Shulker");
        add(ModBlocks.NETHERITE_SHULKER_LIGHT_GRAY.get(), "Light Gray Netherite Shulker");
        add(ModBlocks.NETHERITE_SHULKER_CYAN.get(), "Cyan Netherite Shulker");
        add(ModBlocks.NETHERITE_SHULKER_PURPLE.get(), "Purple Netherite Shulker");
        add(ModBlocks.NETHERITE_SHULKER_BLUE.get(), "Blue Netherite Shulker");
        add(ModBlocks.NETHERITE_SHULKER_BROWN.get(), "Brown Netherite Shulker");
        add(ModBlocks.NETHERITE_SHULKER_GREEN.get(), "Green Netherite Shulker");
        add(ModBlocks.NETHERITE_SHULKER_RED.get(), "Red Netherite Shulker");
        add(ModBlocks.NETHERITE_SHULKER_BLACK.get(), "Black Netherite Shulker");
        add(SHULKER_SCREEN_NAME, "Netherite Shulker");
    }
}
