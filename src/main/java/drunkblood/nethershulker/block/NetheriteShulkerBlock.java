package drunkblood.nethershulker.block;

import drunkblood.nethershulker.NetheriteShulker;
import drunkblood.nethershulker.render.NetheriteShulkerMenu;
import drunkblood.nethershulker.blockentity.NetheriteShulkerBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.world.level.block.ShulkerBoxBlock.CONTENTS;

public class NetheriteShulkerBlock extends Block implements EntityBlock {
    public static final String SHULKER_SCREEN_NAME = "screen.nethershulker.shulker";
    private final DyeColor color;

    public NetheriteShulkerBlock(Properties properties) {
        this(null, properties);
    }

    public NetheriteShulkerBlock(@Nullable DyeColor color, Properties properties){
        super(properties);
        this.color = color;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return NetheriteShulker.NETHERITE_SHULKER_BLOCK_ENTITY.get().create(pos, blockState);
    }

    @Override
    public boolean triggerEvent(BlockState blockState, Level level, BlockPos blockPos, int eventType, int eventData) {
        super.triggerEvent(blockState, level, blockPos, eventType, eventData);
        BlockEntity blockentity = level.getBlockEntity(blockPos);
        return blockentity != null && blockentity.triggerEvent(eventType, eventData);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> p_153214_) {
        return (lvl, pos, blockState, t) -> {
            if (t instanceof NetheriteShulkerBlockEntity tile && state.getBlock() instanceof NetheriteShulkerBlock) {
                tile.tick(lvl, pos, blockState, tile);
            }
        };
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState blockState, Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof NetheriteShulkerBlockEntity shulkerboxblockentity) {
            if (!level.isClientSide && player.isCreative() && !shulkerboxblockentity.isEmpty()) {
                ItemStack itemstack = getColoredItemStack(shulkerboxblockentity.getColor());
                blockentity.saveToItem(itemstack);

                ItemEntity itementity = new ItemEntity(level, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, itemstack);
                itementity.setDefaultPickUpDelay();
                level.addFreshEntity(itementity);
            }
        }

        super.playerWillDestroy(level, pos, blockState, player);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos pos, BlockState blockState1, boolean b) {
        if(blockState.is(blockState1.getBlock())){
            BlockEntity blockentity = level.getBlockEntity(pos);
            if(blockentity instanceof NetheriteShulkerBlockEntity){
                level.updateNeighbourForOutputSignal(pos, blockState.getBlock());
            }
        }
        super.onRemove(blockState, level, pos, blockState1, b);
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootContext.Builder builder) {
        BlockEntity blockentity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockentity instanceof NetheriteShulkerBlockEntity be) {
            final LazyOptional<IItemHandler> itemStackHandler = be.getHandler();
            builder = builder.withDynamicDrop(CONTENTS, (context, itemStackConsumer) -> {
                itemStackHandler.ifPresent(h -> {
                    for (int i = 0; i < 27; i++) {
                        itemStackConsumer.accept(h.getStackInSlot(i));
                    }
                });
            });
        }

        return super.getDrops(blockState, builder);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        if(level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else if(player.isSpectator()) {
            return InteractionResult.PASS;
        } else {
            BlockEntity be = level.getBlockEntity(blockPos);
            if(be instanceof NetheriteShulkerBlockEntity shulkerBlockEntity){
                if(canOpen(blockState, level, blockPos, shulkerBlockEntity)){
                    MenuProvider containerProvider = new MenuProvider() {
                        @Override
                        public Component getDisplayName() {
                            return shulkerBlockEntity.getDisplayName();
                        }

                        @Nullable
                        @Override
                        public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player1) {
                            return new NetheriteShulkerMenu(windowId, blockPos, inv, player1);
                        }
                    };
                    NetworkHooks.openGui((ServerPlayer) player, containerProvider, be.getBlockPos());
                    player.awardStat(Stats.OPEN_SHULKER_BOX);
                    PiglinAi.angerNearbyPiglins(player, true);
                }
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    private static boolean canOpen(BlockState blockState, Level level, BlockPos pos, NetheriteShulkerBlockEntity blockEntity) {
        if (blockEntity.getAnimationStatus() != NetheriteShulkerBlockEntity.AnimationStatus.CLOSED) {
            return true;
        } else {
            AABB aabb = Shulker.getProgressDeltaAabb(blockState.getValue(BlockStateProperties.FACING), 0.0F, 0.5F).move(pos).deflate(1.0E-6D);
            return level.noCollision(aabb);
        }
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter getter, BlockPos pos, CollisionContext context){
        BlockEntity blockentity = getter.getBlockEntity(pos);
        return blockentity instanceof NetheriteShulkerBlockEntity ? Shapes.create(((NetheriteShulkerBlockEntity)blockentity).getBoundingBox(blockState)) : Shapes.block();
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState blockState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos blockPos) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if(blockEntity instanceof NetheriteShulkerBlockEntity be){
            LazyOptional<IItemHandler> handler = be.getHandler();
            final int[] i = {0};
            final float[] f = {0.0F};
            handler.ifPresent((h) -> {
                for (int j = 0; j < 3 * 9; j++) {
                    ItemStack itemStack = h.getStackInSlot(j);
                    if(!itemStack.isEmpty()){
                        f[0] += (float) itemStack.getCount() / (float) itemStack.getMaxStackSize();
                        i[0]++;
                    }
                }
            });

            f[0] /= (float)(3 * 9);
            return Mth.floor(f[0] * 14.0F) + (i[0] > 0 ? 1 : 0);
        }
        return 0;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.FACING, context.getClickedFace());
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_60584_) {
        return PushReaction.DESTROY;
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(BlockStateProperties.FACING, rotation.rotate(blockState.getValue(BlockStateProperties.FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(BlockStateProperties.FACING)));
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        ItemStack itemstack = super.getCloneItemStack(blockGetter, blockPos, blockState);
        blockGetter.getBlockEntity(blockPos, NetheriteShulker.NETHERITE_SHULKER_BLOCK_ENTITY.get()).ifPresent((blockEntity) -> {
            blockEntity.saveToItem(itemstack);
        });
        return itemstack;
    }

    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        if (itemStack.hasCustomHoverName()) {
            BlockEntity blockentity = level.getBlockEntity(blockPos);
            if (blockentity instanceof NetheriteShulkerBlockEntity be) {
                be.setCustomName(itemStack.getHoverName());
            }
        }

    }

    @Override
    public void appendHoverText(ItemStack stack, @javax.annotation.Nullable BlockGetter blockGetter, List<Component> componentList, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, blockGetter, componentList, tooltipFlag);
        CompoundTag nbt = BlockItem.getBlockEntityData(stack);
        if (nbt != null) {
            if (nbt.contains("Inventory")) {
                nbt = nbt.getCompound("Inventory");
                int size = nbt.contains("Size", Tag.TAG_INT) ? nbt.getInt("Size") : 27;
                NonNullList<ItemStack> itemStacks = NonNullList.withSize(size, ItemStack.EMPTY);
                ListTag tagList = nbt.getList("Items", Tag.TAG_COMPOUND);
                for (int i = 0; i < tagList.size(); i++)
                {
                    CompoundTag itemTags = tagList.getCompound(i);
                    int slot = itemTags.getInt("Slot");

                    if (slot >= 0 && slot < size)
                    {
                        itemStacks.set(slot, ItemStack.of(itemTags));
                    }
                }
                int i = 0;
                int j = 0;

                for(ItemStack itemstack : itemStacks) {
                    if (!itemstack.isEmpty()) {
                        ++j;
                        if (i <= 4) {
                            ++i;
                            MutableComponent mutablecomponent = itemstack.getHoverName().copy();
                            mutablecomponent.append(" x").append(String.valueOf(itemstack.getCount()));
                            componentList.add(mutablecomponent);
                        }
                    }
                }

                if (j - i > 0) {
                    componentList.add((new TranslatableComponent("container.shulkerBox.more", j - i)).withStyle(ChatFormatting.ITALIC));
                }
            }
        }

    }

    public static DyeColor getColorFromBlock(Block block) {
        return block instanceof NetheriteShulkerBlock ? ((NetheriteShulkerBlock)block).getColor() : null;
    }

    @javax.annotation.Nullable
    public static DyeColor getColorFromItem(Item item) {
        return getColorFromBlock(Block.byItem(item));
    }

    public static Block getBlockByColor(@javax.annotation.Nullable DyeColor dyeColor) {
        if (dyeColor == null) {
            return ModBlocks.NETHERITE_SHULKER_DEFAULT.get();
        } else {
            switch(dyeColor) {
                case WHITE:
                    return ModBlocks.NETHERITE_SHULKER_WHITE.get();
                case ORANGE:
                    return ModBlocks.NETHERITE_SHULKER_ORANGE.get();
                case MAGENTA:
                    return ModBlocks.NETHERITE_SHULKER_MAGENTA.get();
                case LIGHT_BLUE:
                    return ModBlocks.NETHERITE_SHULKER_LIGHT_BLUE.get();
                case YELLOW:
                    return ModBlocks.NETHERITE_SHULKER_YELLOW.get();
                case LIME:
                    return ModBlocks.NETHERITE_SHULKER_LIME.get();
                case PINK:
                    return ModBlocks.NETHERITE_SHULKER_PINK.get();
                case GRAY:
                    return ModBlocks.NETHERITE_SHULKER_GRAY.get();
                case LIGHT_GRAY:
                    return ModBlocks.NETHERITE_SHULKER_LIGHT_GRAY.get();
                case CYAN:
                    return ModBlocks.NETHERITE_SHULKER_CYAN.get();
                case PURPLE:
                default:
                    return ModBlocks.NETHERITE_SHULKER_PURPLE.get();
                case BLUE:
                    return ModBlocks.NETHERITE_SHULKER_BLUE.get();
                case BROWN:
                    return ModBlocks.NETHERITE_SHULKER_BROWN.get();
                case GREEN:
                    return ModBlocks.NETHERITE_SHULKER_GREEN.get();
                case RED:
                    return ModBlocks.NETHERITE_SHULKER_RED.get();
                case BLACK:
                    return ModBlocks.NETHERITE_SHULKER_BLACK.get();
            }
        }
    }

    public static ItemStack getColoredItemStack(@Nullable DyeColor dyeColor) {
        return new ItemStack(getBlockByColor(dyeColor));
    }

    @Nullable
    public DyeColor getColor() {
        return color;
    }
}
