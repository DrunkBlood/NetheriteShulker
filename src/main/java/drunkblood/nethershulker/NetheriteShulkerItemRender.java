package drunkblood.nethershulker;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Arrays;
import java.util.Comparator;

public class NetheriteShulkerItemRender extends BlockEntityWithoutLevelRenderer {
    private final BlockEntityRenderDispatcher blockEntityRenderDispatcher;

    public NetheriteShulkerItemRender(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
        super(blockEntityRenderDispatcher, entityModelSet);
        this.blockEntityRenderDispatcher = blockEntityRenderDispatcher;
    }

    private static final NetheriteShulkerBlockEntity[] SHULKER_BOXES = Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::getId)).map((dyeColor) -> new NetheriteShulkerBlockEntity(dyeColor, BlockPos.ZERO, ModBlocks.NETHERITE_SHULKER_DEFAULT.get().defaultBlockState())).toArray(NetheriteShulkerBlockEntity[]::new);
    private static final NetheriteShulkerBlockEntity DEFAULT_SHULKER_BOX = new NetheriteShulkerBlockEntity(null, BlockPos.ZERO, ModBlocks.NETHERITE_SHULKER_DEFAULT.get().defaultBlockState());
    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        if(itemStack.getItem() instanceof BlockItem blockItem){
            DyeColor dyeColor = NetheriteShulkerBlock.getColorFromItem(blockItem);
            BlockEntity blockEntity;
            if (dyeColor == null) {
                blockEntity = DEFAULT_SHULKER_BOX;
            } else {
                blockEntity = SHULKER_BOXES[dyeColor.getId()];
            }
            blockEntityRenderDispatcher.renderItem(blockEntity, poseStack, multiBufferSource, i, i1);

        }
    }
}
