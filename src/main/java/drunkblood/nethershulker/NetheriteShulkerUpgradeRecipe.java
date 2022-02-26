package drunkblood.nethershulker;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraftforge.items.ItemStackHandler;

import java.util.stream.Stream;

public class NetheriteShulkerUpgradeRecipe extends UpgradeRecipe {

    private final Ingredient base;
    private final Ingredient addition;
    private final ItemStack result;

    public NetheriteShulkerUpgradeRecipe(ResourceLocation resourceLocation, Ingredient base, Ingredient addition, ItemStack result) {
        super(resourceLocation, base, addition, result);
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    @Override
    public ItemStack assemble(Container container) {
        ItemStack itemstack = this.result.copy();
        CompoundTag sourceTag = container.getItem(0).getTag();
        if(sourceTag != null){
            if(sourceTag.contains("display")){
                itemstack.addTagElement("display", sourceTag.get("display"));
            }
            if(sourceTag.contains("BlockEntityTag")){
                sourceTag = sourceTag.getCompound("BlockEntityTag");
                CompoundTag resultTag = new CompoundTag();
                if(sourceTag.contains("Items", 9)){
                    ListTag items = sourceTag.getList("Items", 10);
                    if(items.size() < 27 && items.size() > 0){
                        NonNullList<ItemStack> itemStacks = NonNullList.withSize(27, ItemStack.EMPTY);
                        for(int i = 0; i < items.size(); ++i) {
                            CompoundTag itemTag = items.getCompound(i);
                            int j = sourceTag.getByte("Slot") & 255;
                            if (j >= 0 && j < 27) {
                                itemStacks.set(j, ItemStack.of(itemTag));
                            }
                        }
                        ItemStackHandler dummyHandler = new ItemStackHandler(itemStacks);
                        resultTag.put("Inventory", dummyHandler.serializeNBT());
                    }
                }
                if(sourceTag.contains("CustomName")){
                    resultTag.putString("CustomName", sourceTag.getString("CustomName"));
                }
                BlockItem.setBlockEntityData(itemstack, NetheriteShulker.NETHERITE_SHULKER_BLOCK_ENTITY.get(), resultTag);
            }
        }

        return itemstack;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return super.getSerializer();
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<NetheriteShulkerUpgradeRecipe> {
        public NetheriteShulkerUpgradeRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "base"));
            Ingredient ingredient1 = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "addition"));
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
            return new NetheriteShulkerUpgradeRecipe(resourceLocation, ingredient, ingredient1, itemstack);
        }

        public NetheriteShulkerUpgradeRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            Ingredient ingredient = Ingredient.fromNetwork(friendlyByteBuf);
            Ingredient ingredient1 = Ingredient.fromNetwork(friendlyByteBuf);
            ItemStack itemstack = friendlyByteBuf.readItem();
            return new NetheriteShulkerUpgradeRecipe(resourceLocation, ingredient, ingredient1, itemstack);
        }

        public void toNetwork(FriendlyByteBuf friendlyByteBuf, NetheriteShulkerUpgradeRecipe recipe) {
            recipe.base.toNetwork(friendlyByteBuf);
            recipe.addition.toNetwork(friendlyByteBuf);
            friendlyByteBuf.writeItem(recipe.result);
        }
    }
}
