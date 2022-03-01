package drunkblood.nethershulker.data;

import drunkblood.nethershulker.block.ModBlocks;
import drunkblood.nethershulker.item.ModItems;
import drunkblood.nethershulker.NetheriteShulker;
import drunkblood.nethershulker.block.NetheriteShulkerBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;

import static drunkblood.nethershulker.block.NetheriteShulkerBlock.SHULKER_SCREEN_NAME;

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


    public static class ModLootTables extends BaseLootTableProvider {

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
