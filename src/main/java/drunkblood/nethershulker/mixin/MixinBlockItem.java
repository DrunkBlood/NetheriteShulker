package drunkblood.nethershulker.mixin;

import drunkblood.nethershulker.block.NetheriteShulkerBlock;
import net.minecraft.world.item.BlockItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class MixinBlockItem {
    @Inject(method = "canFitInsideContainerItems()Z", at = @At("RETURN"), cancellable = true)
    private void isNetheriteShulker(CallbackInfoReturnable<Boolean> cir){
        if(cir.getReturnValue()){
            cir.setReturnValue(!(((BlockItem)(Object)this).getBlock() instanceof NetheriteShulkerBlock));
        }
    }
}
