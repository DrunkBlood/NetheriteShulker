package drunkblood.nethershulker.data;

import drunkblood.nethershulker.NetheriteShulker;
import drunkblood.nethershulker.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import static drunkblood.nethershulker.block.NetheriteShulkerBlock.SHULKER_SCREEN_NAME;

public class GermanLanguageProvider extends LanguageProvider {
    public GermanLanguageProvider(DataGenerator gen, String locale) {
        super(gen, NetheriteShulker.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(ModBlocks.NETHERITE_SHULKER_DEFAULT.get(), "Netheritshulker-Kiste");
        add(ModBlocks.NETHERITE_SHULKER_WHITE.get(), "White Netheritshulker-Kiste");
        add(ModBlocks.NETHERITE_SHULKER_ORANGE.get(), "Orange Netheritshulker-Kiste");
        add(ModBlocks.NETHERITE_SHULKER_MAGENTA.get(), "Magenta Netheritshulker-Kiste");
        add(ModBlocks.NETHERITE_SHULKER_LIGHT_BLUE.get(), "Light Blue Netheritshulker-Kiste");
        add(ModBlocks.NETHERITE_SHULKER_YELLOW.get(), "Yellow Netheritshulker-Kiste");
        add(ModBlocks.NETHERITE_SHULKER_LIME.get(), "Lime Netheritshulker-Kiste");
        add(ModBlocks.NETHERITE_SHULKER_PINK.get(), "Pink Netheritshulker-Kiste");
        add(ModBlocks.NETHERITE_SHULKER_GRAY.get(), "Gray Netheritshulker-Kiste");
        add(ModBlocks.NETHERITE_SHULKER_LIGHT_GRAY.get(), "Light Gray Netheritshulker-Kiste");
        add(ModBlocks.NETHERITE_SHULKER_CYAN.get(), "Cyan Netheritshulker-Kiste");
        add(ModBlocks.NETHERITE_SHULKER_PURPLE.get(), "Purple Netheritshulker-Kiste");
        add(ModBlocks.NETHERITE_SHULKER_BLUE.get(), "Blue Netheritshulker-Kiste");
        add(ModBlocks.NETHERITE_SHULKER_BROWN.get(), "Brown Netheritshulker-Kiste");
        add(ModBlocks.NETHERITE_SHULKER_GREEN.get(), "Green Netheritshulker-Kiste");
        add(ModBlocks.NETHERITE_SHULKER_RED.get(), "Red Netheritshulker-Kiste");
        add(ModBlocks.NETHERITE_SHULKER_BLACK.get(), "Black Netheritshulker-Kiste");
        add(SHULKER_SCREEN_NAME, "Netheritshulker-Kiste");
    }
}
