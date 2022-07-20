package drunkblood.nethershulker.recipe;

import drunkblood.nethershulker.NetheriteShulker;
import drunkblood.nethershulker.block.NetheriteShulkerBlock;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraftforge.common.Tags;
import net.minecraftforge.items.ItemStackHandler;

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
        ItemStack sourceStack = ItemStack.EMPTY;
        net.minecraft.world.item.DyeColor dyecolor = net.minecraft.world.item.DyeColor.WHITE;


        for(int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemstack1 = container.getItem(i);
            if (!itemstack1.isEmpty()) {
                Item item = itemstack1.getItem();
                if (Block.byItem(item) instanceof NetheriteShulkerBlock) {
                    sourceStack = itemstack1;
                } else if (itemstack1.is(Tags.Items.DYES)){
                    net.minecraft.world.item.DyeColor tmp = net.minecraft.world.item.DyeColor.getColor(itemstack1);
                    if (tmp != null) dyecolor = tmp;
                }
            }
        }

        ItemStack itemstack = NetheriteShulkerBlock.getColoredItemStack(dyecolor);
        CompoundTag sourceTag = sourceStack.getTag();
        if(sourceTag != null){
            if(sourceTag.contains("display")){
                itemstack.addTagElement("display", sourceTag.get("display"));
            }
            if(sourceTag.contains("BlockEntityTag")){
                sourceTag = sourceTag.getCompound("BlockEntityTag");
                CompoundTag resultTag = new CompoundTag();
                if(sourceTag.contains("CustomName")){
                    resultTag.putString("CustomName", sourceTag.getString("CustomName"));
                }
                if(sourceTag.contains("Inventory")){
                    sourceTag = sourceTag.getCompound("Inventory");
                    if(sourceTag.contains("Items", 9)){
                        ListTag items = sourceTag.getList("Items", 10);
                        if(items.size() < 27 && items.size() > 0){
                            NonNullList<ItemStack> itemStacks = NonNullList.withSize(27, ItemStack.EMPTY);
                            for(int i = 0; i < items.size(); ++i) {
                                CompoundTag itemTag = items.getCompound(i);
                                int j = itemTag.getByte("Slot") & 255;
                                if (j >= 0 && j < 27) {
                                    itemStacks.set(j, ItemStack.of(itemTag));
                                }
                            }
                            ItemStackHandler dummyHandler = new ItemStackHandler(itemStacks);
                            resultTag.put("Inventory", dummyHandler.serializeNBT());
                        }
                    }
                }
                BlockItem.setBlockEntityData(itemstack, NetheriteShulker.NETHERITE_SHULKER_BLOCK_ENTITY.get(), resultTag);
            }
        }
        return itemstack;
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
