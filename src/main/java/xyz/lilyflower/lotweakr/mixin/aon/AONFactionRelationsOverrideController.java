package xyz.lilyflower.lotweakr.mixin.aon;

import xyz.lilyflower.lotweakr.util.config.CombatFeatureConfig;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionRelations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LOTRFactionRelations.class)
public class AONFactionRelationsOverrideController {
    @Inject(method = "getRelations", at = @At("HEAD"), cancellable = true, remap = false)
    private static void makeAllNeutral(LOTRFaction f1, LOTRFaction f2, CallbackInfoReturnable<LOTRFactionRelations.Relation> cir) {
        if (CombatFeatureConfig.FO_NEUTRAL) {
            cir.setReturnValue(LOTRFactionRelations.Relation.NEUTRAL);
        }
    }
    
    @Inject(method = "getRelations", at = @At("HEAD"), cancellable = true, remap = false)
    private static void makeAllHostile(LOTRFaction f1, LOTRFaction f2, CallbackInfoReturnable<LOTRFactionRelations.Relation> cir) {
        if (CombatFeatureConfig.FO_ENEMY) {
            if (CombatFeatureConfig.ENABLE_MASS_MURDER || f1 != f2) {
                cir.setReturnValue(LOTRFactionRelations.Relation.ENEMY);
            }
        }
    }

    @Inject(method = "getRelations", at = @At("HEAD"), cancellable = true, remap = false)
    private static void makeAllFriendly(LOTRFaction f1, LOTRFaction f2, CallbackInfoReturnable<LOTRFactionRelations.Relation> cir) {
        if (CombatFeatureConfig.FO_FRIENDLY) {
            cir.setReturnValue(LOTRFactionRelations.Relation.FRIEND);
        }
    }
}