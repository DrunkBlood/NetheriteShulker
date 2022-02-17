package drunkblood.nethershulker;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NetheriteShulkerBlockEntity extends BlockEntity {
    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    public NetheriteShulkerBlockEntity(BlockPos pos, BlockState blockState) {
        super(NetheriteShulker.NETHERITE_SHULKER_BLOCK_ENTITY.get(), pos, blockState);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("Inventory")) {
            itemHandler.deserializeNBT(tag.getCompound("Inventory"));
        }
        super.load(tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("Inventory", itemHandler.serializeNBT());
    }

    @Override
    public void saveToItem(ItemStack stack) {
        super.saveToItem(stack);
    }

    public boolean isEmpty() {
        final boolean[] isEmpty = {true};
        getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            for (int index = 0; index < 27; index++) {
                ItemStack stackInSlot = h.getStackInSlot(index);
                if(!stackInSlot.isEmpty()) {
                    isEmpty[0] = false;
                    break;
                }
            }
        });
        return isEmpty[0];
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(27) {
            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                Item item = stack.getItem();
                if(item instanceof BlockItem){
                    Block block = ((BlockItem) item).getBlock();
                    if(block instanceof ShulkerBoxBlock
                    || block instanceof NetheriteShulkerBlock){
                        return false;
                    }
                }
                return true;
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }
}
