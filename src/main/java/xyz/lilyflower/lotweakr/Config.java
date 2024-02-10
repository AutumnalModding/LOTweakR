package xyz.lilyflower.lotweakr;

import java.io.File;

import cpw.mods.fml.common.registry.GameRegistry;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

public class Config {
    public static class LOTR {
        public static boolean UNLOCK_COSMETICS = false;
        public static boolean MAKE_NPCS_NEUTRAL = false;
        public static boolean REMOVE_COMBAT = true;
        public static boolean UNLOCK_WAYPOINTS = false;
        public static boolean NO_WAYPOINT_LOCKING = true;
    }

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        LOTR.UNLOCK_COSMETICS = configuration.getBoolean("unlockCosmetics", "lotr", LOTR.UNLOCK_COSMETICS, "Unlock all player-specific LOTR cosmetics?");
        LOTR.MAKE_NPCS_NEUTRAL = configuration.getBoolean("makeNpcsNeutral", "lotr", LOTR.MAKE_NPCS_NEUTRAL, "Make all LOTR NPCs neutral?");
        LOTR.REMOVE_COMBAT = configuration.getBoolean("removeShittyCombat", "lotr", LOTR.REMOVE_COMBAT, "Remove the 1.9-style combat changes?");
        LOTR.UNLOCK_WAYPOINTS = configuration.getBoolean("unlockAllWaypoints", "lotr", LOTR.UNLOCK_WAYPOINTS, "Unlock all fast travel waypoints?");
        LOTR.NO_WAYPOINT_LOCKING = configuration.getBoolean("disableWaypointLocking", "lotr", LOTR.NO_WAYPOINT_LOCKING, "Disable alignment-based waypoint locking?");

        String[] items = configuration.getStringList("additionalCombatItems", "lotr", new String[]{"minecraft:golden_axe@1.5_1.0"},
                "List of items to add to the custom LOTR combat system.\n" +
                "Format: 'modid:item_name@speedMultiplier_reachMultiplier'\n" +
                "Example: 'minecraft:golden_axe@1.5_1.0"
        );

        for (String entry : items) {
            float speed = Float.parseFloat(entry.replaceAll(".*@", "").replaceAll("_.*", ""));
            float reach = Float.parseFloat(entry.replaceAll(".*_", ""));

            String modID = entry.replaceAll(":.*", "");
            String itemID = entry.replaceAll(".*:", "").replaceAll("@.*", "");

            Item item = GameRegistry.findItem(modID, itemID);

            if (item != null) {
                Opinionation.LOGGER.info("Registering item '" + modID + ":" + itemID + "' to the LOTR combat system (speed " + speed + "x, reach " + reach + "x)...");

                LOTRWeaponStats.registerMeleeSpeed(item, speed);
                LOTRWeaponStats.registerMeleeReach(item, reach);
            }
        }

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
