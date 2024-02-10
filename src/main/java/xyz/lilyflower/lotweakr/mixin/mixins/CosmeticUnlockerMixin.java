package xyz.lilyflower.lotweakr.mixin.mixins;

import xyz.lilyflower.lotweakr.Config;
import lotr.common.playerdetails.ExclusiveGroup;
import lotr.common.playerdetails.PlayerDetails;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerDetails.class)
public class CosmeticUnlockerMixin {
    @Inject(method = "hasExclusiveGroup", at = @At("HEAD"), cancellable = true, remap = false)
    public void unlockAll(ExclusiveGroup group, CallbackInfoReturnable<Boolean> cir) {
        if (Config.LOTR.UNLOCK_COSMETICS) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "hasAnyExclusiveGroup", at = @At("HEAD"), cancellable = true, remap = false)
    public void unlockAny(ExclusiveGroup[] groups, CallbackInfoReturnable<Boolean> cir) {
        if (Config.LOTR.UNLOCK_COSMETICS) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "isReceivedFromApi", at = @At("HEAD"), cancellable = true, remap = false)
    public void noRefetch(CallbackInfoReturnable<Boolean> cir) {
        if (Config.LOTR.UNLOCK_COSMETICS) {
            cir.setReturnValue(true);
        }
    }
}
