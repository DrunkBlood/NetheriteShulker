package drunkblood.nethershulker.recipe;

import drunkblood.nethershulker.NetheriteShulker;
import drunkblood.nethershulker.block.NetheriteShulkerBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;

public class NetheriteShulkerColoring extends CustomRecipe {
    public NetheriteShulkerColoring(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingContainer craftingContainer, Level level) {
        int i = 0;
        int j = 0;

        for(int k = 0; k < craftingContainer.getContainerSize(); ++k) {
            ItemStack itemstack = craftingContainer.getItem(k);
            if (!itemstack.isEmpty()) {
                if (Block.byItem(itemstack.getItem()) instanceof NetheriteShulkerBlock) {
                    ++i;
                } else {
                    if (!itemstack.is(net.minecraftforge.common.Tags.Items.DYES)) {
                        return false;
                    }

                    ++j;
                }

                if (j > 1 || i > 1) {
                    return false;
                }
            }
        }

        return i == 1 && j == 1;
    }

    @Override
    public ItemStack assemble(CraftingContainer container) {
        ItemStack itemstack = ItemStack.EMPTY;
        net.minecraft.world.item.DyeColor dyecolor = net.minecraft.world.item.DyeColor.WHITE;

        for(int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemstack1 = container.getItem(i);
            if (!itemstack1.isEmpty()) {
                Item item = itemstack1.getItem();
                if (Block.byItem(item) instanceof ShulkerBoxBlock) {
                    itemstack = itemstack1;
                } else {
                    net.minecraft.world.item.DyeColor tmp = net.minecraft.world.item.DyeColor.getColor(itemstack1);
                    if (tmp != null) dyecolor = tmp;
                }
            }
        }

        ItemStack itemstack2 = NetheriteShulkerBlock.getColoredItemStack(dyecolor);
        if (itemstack.hasTag()) {
            itemstack2.setTag(itemstack.getTag().copy());
        }

        return itemstack2;
    }

    @Override
    public boolean canCraftInDimensions(int x, int y) {
        return x * y >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NetheriteShulker.NETHERITE_SHULKER_COLORING.get();
    }
}
