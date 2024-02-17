package xyz.lilyflower.lotweakr.util.config;

import cpw.mods.fml.common.registry.GameRegistry;
import java.io.File;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import xyz.lilyflower.lotweakr.core.LOTweakR;

/**
 * <strong>NOTE:</strong> FO here stands for "Faction Override".
 */
public class CombatFeatureConfig {

    public static boolean DISABLE_ATTACK_TIMINGS = false;
    public static boolean ENABLE_GW_DEATH = false;
    public static boolean FO_NEUTRAL = false;
    public static boolean FO_ENEMY = false;
    public static boolean FO_FRIENDLY = false;
    public static boolean ENABLE_MASS_MURDER = false;
    public static boolean ENABLE_GENOCIDE = false;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        DISABLE_ATTACK_TIMINGS = configuration.getBoolean("removeNewCombat", "combat", false, "Removes the 1.9-style combat changes.");
        ENABLE_GW_DEATH = configuration.getBoolean("enableGreyWandererDeath", "combat", false, "Allows the Grey Wanderer to die.");

        FO_NEUTRAL = configuration.getBoolean("makeAllFactionsNeutral", "combat", false, "Makes all LOTR NPCs neutral to other factions, regardless of status or alignment.");
        FO_ENEMY = configuration.getBoolean("makeAllFactionsEnemies", "combat", false, "Makes all LOTR NPCs enemies to other factions, regardless of status or alignment.");
        FO_FRIENDLY = configuration.getBoolean("makeAllFactionsFriendly", "combat", false, "Makes all LOTR NPCs friendly to other factions, regardless of status or alignment.");

        // probably controversial to call it this
        ENABLE_MASS_MURDER = configuration.getBoolean("enableMassMurder", "combat", false, "Makes all LOTR NPCs attack others on sight - even their own kind!");
        ENABLE_GENOCIDE = configuration.getBoolean("enableGenocide", "combat", false, "Makes all LOTR NPCs attack ANYTHING on sight - even most non-NPC mobs!");

        String[] items = configuration.getStringList("additionalCombatItems", "combat", new String[]{},
                "List of items to add to the custom LOTR combat system.\n" +
                "Format: 'modid:item_name@speedMultiplier_reachMultiplier'\n" +
                "Example: 'minecraft:golden_axe@1.5_1.0"
        );

        registerModdedWeapons(items);

        if (ENABLE_MASS_MURDER) {
            for (LOTRFaction faction : LOTRFaction.values()) {
                faction.approvesWarCrimes = true;
            }
        }

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    private static void registerModdedWeapons(String[] items) {
        for (String entry : items) {
            float speed = Float.parseFloat(entry.replaceAll(".*@", "").replaceAll("_.*", ""));
            float reach = Float.parseFloat(entry.replaceAll(".*_", ""));

            String modID = entry.replaceAll(":.*", "");
            String itemID = entry.replaceAll(".*:", "").replaceAll("@.*", "");

            Item item = GameRegistry.findItem(modID, itemID);

            if (item != null) {
                LOTweakR.LOGGER.debug("Registering item '" + modID + ":" + itemID + "' to the LOTR combat system (speed " + speed + "x, reach " + reach + "x)...");

                LOTRWeaponStats.registerMeleeSpeed(item, speed);
                LOTRWeaponStats.registerMeleeReach(item, reach);
            }
        }
    }
}
