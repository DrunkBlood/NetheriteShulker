package drunkblood.nethershulker;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class NetheriteShulkerRenderer implements BlockEntityRenderer<NetheriteShulkerBlockEntity> {
    private final ShulkerModel<?> model;
    public NetheriteShulkerRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new ShulkerModel(context.bakeLayer(ModelLayers.SHULKER));
    }
    @Override
    public void render(NetheriteShulkerBlockEntity blockEntity, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        Direction direction = Direction.UP;
        if (blockEntity.hasLevel()) {
            BlockState blockstate = blockEntity.getLevel().getBlockState(blockEntity.getBlockPos());
            if (blockstate.getBlock() instanceof NetheriteShulkerBlock) {
                direction = blockstate.getValue(BlockStateProperties.FACING);
            }
        }

        // TODO add color
//        DyeColor dyecolor = blockEntity.getColor();
//        Material material;
//        if (dyecolor == null) {
//            material = Sheets.DEFAULT_SHULKER_TEXTURE_LOCATION;
//        } else {
//            material = Sheets.SHULKER_TEXTURE_LOCATION.get(dyecolor.getId());
//        }

        Material material;
        material = Sheets.DEFAULT_SHULKER_TEXTURE_LOCATION;

        poseStack.pushPose();
        poseStack.translate(0.5D, 0.5D, 0.5D);
        float f = 0.9995F;
        poseStack.scale(0.9995F, 0.9995F, 0.9995F);
        poseStack.mulPose(direction.getRotation());
        poseStack.scale(1.0F, -1.0F, -1.0F);
        poseStack.translate(0.0D, -1.0D, 0.0D);
        ModelPart modelpart = this.model.getLid();
        float progress = blockEntity.getProgress(partialTicks);
        if(progress >= 0.5f){
            progress += 0.0f;

        }
        modelpart.setPos(0.0F, 24.0F - progress * 0.5F * 16.0F, 0.0F);
        modelpart.yRot = 270.0F * progress * ((float)Math.PI / 180F);
        VertexConsumer vertexconsumer = material.buffer(bufferSource, RenderType::entityCutoutNoCull);
        this.model.renderToBuffer(poseStack, vertexconsumer, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }

    public static void register(){
        BlockEntityRenderers.register(NetheriteShulker.NETHERITE_SHULKER_BLOCK_ENTITY.get(), NetheriteShulkerRenderer::new);
    }
}
