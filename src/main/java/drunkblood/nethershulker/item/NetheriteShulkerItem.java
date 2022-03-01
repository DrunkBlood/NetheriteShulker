package drunkblood.nethershulker.item;

import drunkblood.nethershulker.render.NetheriteShulkerItemRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.util.NonNullLazy;

import java.util.function.Consumer;

public class NetheriteShulkerItem extends BlockItem {
    public NetheriteShulkerItem(Block block, Properties properties) {
        super(block, properties);
    }

    public NetheriteShulkerItem(Block block){
        this(block, new Item.Properties()
                .tab(CreativeModeTab.TAB_DECORATIONS)
                .fireResistant()
                .stacksTo(1));
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer)
    {
        if (Minecraft.getInstance() == null) return;

        consumer.accept(new IItemRenderProperties()
        {
            static final NonNullLazy<BlockEntityWithoutLevelRenderer> renderer = NonNullLazy.of(() ->
                    new NetheriteShulkerItemRender(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()));


            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer()
            {
                return renderer.get();
            }
        });
    }
}
