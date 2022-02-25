package drunkblood.nethershulker;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static drunkblood.nethershulker.NetheriteShulkerBlock.SHULKER_SCREEN_NAME;

public class NetheriteShulkerBlockEntity extends BlockEntity {
    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    private int openCount;
    private NetheriteShulkerBlockEntity.AnimationStatus animationStatus = NetheriteShulkerBlockEntity.AnimationStatus.CLOSED;
    private float progress;
    private float progressOld;
    private Component name;
    private final DyeColor color;

    public NetheriteShulkerBlockEntity(DyeColor dyeColor, BlockPos pos, BlockState blockState) {
        super(NetheriteShulker.NETHERITE_SHULKER_BLOCK_ENTITY.get(), pos, blockState);
        color = dyeColor;
    }
    public NetheriteShulkerBlockEntity(BlockPos pos, BlockState blockState) {
        super(NetheriteShulker.NETHERITE_SHULKER_BLOCK_ENTITY.get(), pos, blockState);
        color =  NetheriteShulkerBlock.getColorFromBlock(blockState.getBlock());
    }

    public LazyOptional<IItemHandler> getHandler() {
        return handler;
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
        if(tag.contains("CustomName")) {
            name = Component.Serializer.fromJson(tag.getString("CustomName"));
        }
        super.load(tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("Inventory", itemHandler.serializeNBT());
        if(name != null) tag.putString("CustomName", Component.Serializer.toJson(this.name));
    }

    public Component getName() {
        return this.name != null ? this.name : new TranslatableComponent(SHULKER_SCREEN_NAME);
    }

    public Component getDisplayName() {
        return this.getName();
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

    public void startOpen(Player player) {
        if (!player.isSpectator()) {
            if (this.openCount < 0) {
                this.openCount = 0;
            }

            ++this.openCount;
            this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, this.openCount);
            if (this.openCount == 1) {
                this.level.gameEvent(player, GameEvent.CONTAINER_OPEN, this.worldPosition);
                this.level.playSound((Player)null, this.worldPosition, SoundEvents.SHULKER_BOX_OPEN, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
            }
        }

    }

    public void stopOpen(Player player) {
        if (!player.isSpectator()) {
            --this.openCount;
            this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, this.openCount);
            if (this.openCount <= 0) {
                this.level.gameEvent(player, GameEvent.CONTAINER_CLOSE, this.worldPosition);
                this.level.playSound((Player)null, this.worldPosition, SoundEvents.SHULKER_BOX_CLOSE, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
            }
        }

    }

    @Override
    public boolean triggerEvent(int triggerOpening, int eventOpenCount) {
        if (triggerOpening == 1) {
            this.openCount = eventOpenCount;
            if (eventOpenCount == 0) {
                this.animationStatus = NetheriteShulkerBlockEntity.AnimationStatus.CLOSING;
                doNeighborUpdates(this.getLevel(), this.worldPosition, this.getBlockState());
            }

            if (eventOpenCount == 1) {
                this.animationStatus = NetheriteShulkerBlockEntity.AnimationStatus.OPENING;
                doNeighborUpdates(this.getLevel(), this.worldPosition, this.getBlockState());
            }

            return true;
        } else {
            return super.triggerEvent(triggerOpening, eventOpenCount);
        }
    }

    public float getProgress(float partialTicks) {
        return Mth.lerp(partialTicks, this.progressOld, this.progress);
    }

    public void updateAnimation(Level level, BlockPos pos, BlockState state) {
        this.progressOld = this.progress;
        switch (this.animationStatus) {
            case CLOSED -> this.progress = 0.0F;
            case OPENING -> {
                this.progress += 0.1F;
                if (this.progress >= 1.0F) {
                    this.animationStatus = AnimationStatus.OPENED;
                    this.progress = 1.0F;
                    doNeighborUpdates(level, pos, state);
                }
                this.moveCollidedEntities(level, pos, state);
            }
            case CLOSING -> {
                this.progress -= 0.1F;
                if (this.progress <= 0.0F) {
                    this.animationStatus = AnimationStatus.CLOSED;
                    this.progress = 0.0F;
                    doNeighborUpdates(level, pos, state);
                }
            }
            case OPENED -> this.progress = 1.0F;
        }
    }

    private static void doNeighborUpdates(Level level, BlockPos blockPos, BlockState state) {
        state.updateNeighbourShapes(level, blockPos, 3);
    }

    private void moveCollidedEntities(Level level, BlockPos pos, BlockState blockState) {
        if (blockState.getBlock() instanceof NetheriteShulkerBlock) {
            Direction direction = blockState.getValue(BlockStateProperties.FACING);
            AABB aabb = Shulker.getProgressDeltaAabb(direction, this.progressOld, this.progress).move(pos);
            List<Entity> list = level.getEntities((Entity)null, aabb);
            if (!list.isEmpty()) {
                for(int i = 0; i < list.size(); ++i) {
                    Entity entity = list.get(i);
                    if (entity.getPistonPushReaction() != PushReaction.IGNORE) {
                        entity.move(MoverType.SHULKER_BOX, new Vec3((aabb.getXsize() + 0.01D) * (double)direction.getStepX(), (aabb.getYsize() + 0.01D) * (double)direction.getStepY(), (aabb.getZsize() + 0.01D) * (double)direction.getStepZ()));
                    }
                }

            }
        }
    }

    public void tick(Level lvl, BlockPos pos, BlockState blockState, NetheriteShulkerBlockEntity tile) {
        tile.updateAnimation(lvl, pos, blockState);
    }

    public NetheriteShulkerBlockEntity.AnimationStatus getAnimationStatus() {
        return animationStatus;
    }

    public AABB getBoundingBox(BlockState blockState) {
        return Shulker.getProgressAabb(blockState.getValue(BlockStateProperties.FACING), 0.5F * this.getProgress(1.0F));
    }

    public void setCustomName(Component hoverName) {
        this.name = hoverName;
    }

    @Nullable
    public DyeColor getColor() {
        return color;
    }

    public static enum AnimationStatus {
        CLOSED,
        OPENING,
        OPENED,
        CLOSING;
    }
}
