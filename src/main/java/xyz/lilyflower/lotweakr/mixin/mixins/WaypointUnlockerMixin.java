package xyz.lilyflower.lotweakr.mixin.mixins;

import xyz.lilyflower.lotweakr.Config;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LOTRWaypoint.class)
public class WaypointUnlockerMixin {
    @Inject(method = "hasPlayerUnlocked", at = @At("HEAD"), cancellable = true, remap = false)
    public void unlockAll(EntityPlayer entityplayer, CallbackInfoReturnable<Boolean> cir) {
        if (Config.LOTR.UNLOCK_WAYPOINTS) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "isCompatibleAlignment", at = @At("HEAD"), cancellable = true, remap = false)
    public void noLocking(EntityPlayer entityplayer, CallbackInfoReturnable<Boolean> cir) {
        if (Config.LOTR.NO_WAYPOINT_LOCKING) {
            cir.setReturnValue(true);
        }
    }
}
