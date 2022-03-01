package drunkblood.nethershulker.render;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import drunkblood.nethershulker.NetheriteShulker;
import drunkblood.nethershulker.block.NetheriteShulkerBlock;
import drunkblood.nethershulker.blockentity.NetheriteShulkerBlockEntity;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.List;
import java.util.stream.Stream;

import static net.minecraft.client.renderer.Sheets.SHULKER_SHEET;

public class NetheriteShulkerRenderer implements BlockEntityRenderer<NetheriteShulkerBlockEntity> {
    private final ShulkerModel model;
    public static final List<Material> TEXTURE_LOCATIONS = Stream.of("white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black", "default")
            .map((color) -> new Material(SHULKER_SHEET, new ResourceLocation(NetheriteShulker.MODID, "entity/shulker/nethershulker_" + color))).collect(ImmutableList.toImmutableList());

    public NetheriteShulkerRenderer(BlockEntityRendererProvider.Context context ){
        this.model = new ShulkerModel(context.bakeLayer(ModelLayers.SHULKER));
    }

    @Override
    public void render(NetheriteShulkerBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverla) {
        Direction direction = Direction.UP;
        if (blockEntity.hasLevel()) {
            BlockState blockstate = blockEntity.getLevel().getBlockState(blockEntity.getBlockPos());
            if (blockstate.getBlock() instanceof NetheriteShulkerBlock) {
                direction = blockstate.getValue(BlockStateProperties.FACING);
            }
        }

        DyeColor dyecolor = blockEntity.getColor();
        Material material;
        if (dyecolor == null) {
            // get last ie default
            material = TEXTURE_LOCATIONS.get(TEXTURE_LOCATIONS.size() - 1);
        } else {
            material = TEXTURE_LOCATIONS.get(dyecolor.getId());
        }
        poseStack.pushPose();
        poseStack.translate(0.5D, 0.5D, 0.5D);
        float f = 0.9995F;
        poseStack.scale(0.9995F, 0.9995F, 0.9995F);
        poseStack.mulPose(direction.getRotation());
        poseStack.scale(1.0F, -1.0F, -1.0F);
        poseStack.translate(0.0D, -1.0D, 0.0D);
        ModelPart modelpart = this.model.getLid();
        modelpart.setPos(0.0F, 24.0F - blockEntity.getProgress(partialTicks) * 0.5F * 16.0F, 0.0F);
        modelpart.yRot = 270.0F * blockEntity.getProgress(partialTicks) * ((float)Math.PI / 180F);
        VertexConsumer vertexconsumer = material.buffer(bufferSource, RenderType::entityCutoutNoCull);
        this.model.renderToBuffer(poseStack, vertexconsumer, combinedLight, combinedOverla, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}
