package drunkblood.nethershulker;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

//TODO fix block being able to fit in shulker box
public class NetheriteShulkerBlock extends Block implements EntityBlock {
    public static final String SHULKER_SCREEN_NAME = "screen.nethershulker.shulker";

    public NetheriteShulkerBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return NetheriteShulker.NETHERITE_SHULKER_BLOCK_ENTITY.get().create(pos, blockState);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState blockState, Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof NetheriteShulkerBlockEntity) {
            NetheriteShulkerBlockEntity shulkerboxblockentity = (NetheriteShulkerBlockEntity)blockentity;
            if (!level.isClientSide && player.isCreative() && !shulkerboxblockentity.isEmpty()) {
                ItemStack itemstack = new ItemStack(NetheriteShulker.NETHERITE_SHULKER.get());
                blockentity.saveToItem(itemstack);

                ItemEntity itementity = new ItemEntity(level, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, itemstack);
                itementity.setDefaultPickUpDelay();
                level.addFreshEntity(itementity);
            }
        }

        super.playerWillDestroy(level, pos, blockState, player);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        if(!level.isClientSide){
            BlockEntity be = level.getBlockEntity(blockPos);
            if(be instanceof NetheriteShulkerBlockEntity){
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return new TranslatableComponent(SHULKER_SCREEN_NAME);
                    }

                    @Nullable
                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player1) {
                        return new NetheriteShulkerContainer(windowId, blockPos, inv, player1);
                    }
                };
                NetworkHooks.openGui((ServerPlayer) player, containerProvider, be.getBlockPos());
            } else {
                throw new IllegalStateException("Block Entity was not linked");
            }
        }
        return InteractionResult.SUCCESS;
    }
}
