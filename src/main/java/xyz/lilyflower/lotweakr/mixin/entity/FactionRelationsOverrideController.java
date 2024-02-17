package xyz.lilyflower.lotweakr.mixin.entity;

import java.io.File;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionRelations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LOTRFaction.class)
public class FactionRelationsOverrideController {
    @Inject(method = "initAllProperties", at = @At("TAIL"), remap = false)
    private static void addOverrides(CallbackInfo ci) {
        File overridesFile = new File(System.getProperty("user.dir") + "/config/lotweakr/factionRelationOverrides.cfg");
    }
}
