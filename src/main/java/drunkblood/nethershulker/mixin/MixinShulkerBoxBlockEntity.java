package drunkblood.nethershulker.mixin;

import drunkblood.nethershulker.NetheriteShulkerBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(ShulkerBoxBlockEntity.class)
public class MixinShulkerBoxBlockEntity {
    @Inject(method = "canPlaceItemThroughFace(ILnet/minecraft/world/item/ItemStack;Lnet/minecraft/core/Direction;)Z", at = @At("RETURN"), cancellable = true)
    private void isNetheriteShulker(int p_59663_, ItemStack itemStack, @Nullable Direction direction, CallbackInfoReturnable<Boolean> cir){
        if(cir.getReturnValue()){
            cir.setReturnValue(!(Block.byItem(itemStack.getItem()) instanceof NetheriteShulkerBlock));
        }
    }
}
