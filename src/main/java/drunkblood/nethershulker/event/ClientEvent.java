package drunkblood.nethershulker.event;

import drunkblood.nethershulker.block.ModBlocks;
import drunkblood.nethershulker.NetheriteShulker;
import drunkblood.nethershulker.render.NetheriteShulkerRenderer;
import drunkblood.nethershulker.render.NetheriteShulkerScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static drunkblood.nethershulker.NetheriteShulker.NETHERITE_SHULKER_CONTAINER;
import static net.minecraft.client.renderer.Sheets.SHULKER_SHEET;

@Mod.EventBusSubscriber(modid = NetheriteShulker.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvent {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        event.enqueueWork(() -> {
            MenuScreens.register(NETHERITE_SHULKER_CONTAINER.get(), NetheriteShulkerScreen::new);
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.NETHERITE_SHULKER_DEFAULT.get(), RenderType.translucent());
        });
    }
    @SubscribeEvent
    public static void textureStitch(TextureStitchEvent.Pre event){
        if(!event.getAtlas().location().equals(SHULKER_SHEET)){
            return;
        }
        for(Material material : NetheriteShulkerRenderer.TEXTURE_LOCATIONS){
            event.addSprite(material.texture());
        }
    }

    @SubscribeEvent
    public static void registerBERenders(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(NetheriteShulker.NETHERITE_SHULKER_BLOCK_ENTITY.get(), NetheriteShulkerRenderer::new);
    }
}
