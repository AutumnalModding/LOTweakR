package xyz.lilyflower.lotweakr.mixin.mixins;

import xyz.lilyflower.lotweakr.Config;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionRelations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LOTRFactionRelations.class)
public class NPCNeutralifierMixin {
    @Inject(method = "getRelations", at = @At("HEAD"), cancellable = true, remap = false)
    private static void neutralify(LOTRFaction f1, LOTRFaction f2, CallbackInfoReturnable<LOTRFactionRelations.Relation> cir) {
        if (Config.LOTR.MAKE_NPCS_NEUTRAL) {
            cir.setReturnValue(LOTRFactionRelations.Relation.NEUTRAL);
        }
    }
}
