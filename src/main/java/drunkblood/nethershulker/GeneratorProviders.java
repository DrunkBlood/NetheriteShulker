package drunkblood.nethershulker;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.function.Consumer;

import static drunkblood.nethershulker.NetheriteShulkerBlock.SHULKER_SCREEN_NAME;

public class GeneratorProviders {
    public static class ModBlockStates extends BlockStateProvider {

        public ModBlockStates(DataGenerator gen, ExistingFileHelper helper) {
            super(gen, NetheriteShulker.MODID, helper);
        }

        @Override
        protected void registerStatesAndModels() {
            simpleBlock(NetheriteShulkerBlock.getBlockByColor(null));
            for (DyeColor dyeColor : DyeColor.values()){
                simpleBlock(NetheriteShulkerBlock.getBlockByColor(dyeColor));
            }
        }
    }

    public static class ModBlockTags extends BlockTagsProvider {

        public ModBlockTags(DataGenerator generator, ExistingFileHelper helper) {
            super(generator, NetheriteShulker.MODID, helper);
        }

        @Override
        protected void addTags() {
            TagAppender<Block> appender = tag(BlockTags.MINEABLE_WITH_PICKAXE);
            appender.add(ModBlocks.NETHERITE_SHULKER_DEFAULT.get());
            for (DyeColor dyeColor : DyeColor.values()){
                appender.add(NetheriteShulkerBlock.getBlockByColor(dyeColor));
            }

        }
    }

    public static class ModItemModels extends ItemModelProvider {

        public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
            super(generator, NetheriteShulker.MODID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            String template_shulker_box = "item/template_netherite_shulker";
            withExistingParent(NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME, modLoc(template_shulker_box))
                    .texture("particle", modLoc("block/" + ModItems.NETHERITE_SHULKER_DEFAULT.get().getRegistryName().getPath()));
            for (DyeColor dyeColor : DyeColor.values()){
                Block block = NetheriteShulkerBlock.getBlockByColor(dyeColor);
                String name = block.getRegistryName().getPath();
                withExistingParent(name, modLoc(template_shulker_box))
                        .texture("particle", modLoc("block/" + name));
            }
        }
    }

    static class EnglishLanguageProvider extends LanguageProvider {

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

    static class ModLootTables extends BaseLootTableProvider {

        public ModLootTables(DataGenerator generator) {
            super(generator);
        }

        @Override
        protected void addTables() {
            lootTables.put(ModBlocks.NETHERITE_SHULKER_DEFAULT.get(),
                    createStandardTable(
                            NetheriteShulker.NETHERITE_SHULKER_REGISTRY_NAME,
                            ModBlocks.NETHERITE_SHULKER_DEFAULT.get(),
                            NetheriteShulker.NETHERITE_SHULKER_BLOCK_ENTITY.get()));
            for (DyeColor dyeColor : DyeColor.values()){
                Block block = NetheriteShulkerBlock.getBlockByColor(dyeColor);
                ResourceLocation regName = block.getRegistryName();
                lootTables.put(block,
                        createStandardTable(
                                regName.getPath(),
                                block,
                                NetheriteShulker.NETHERITE_SHULKER_BLOCK_ENTITY.get()
                        ));
            }
        }
    }
}
