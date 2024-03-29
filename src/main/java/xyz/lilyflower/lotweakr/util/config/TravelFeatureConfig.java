package xyz.lilyflower.lotweakr.util.config;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraftforge.common.config.Configuration;

public class TravelFeatureConfig {
    public static boolean UNLOCK_WAYPOINTS = false;
    public static boolean NO_WAYPOINT_LOCKING = false;
    private static List<String> DISABLED_WAYPOINTS;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);
        DISABLED_WAYPOINTS = Arrays.asList(configuration.getStringList("disabledWaypoints", "travel", new String[]{},
                "List of waypoints to disable.\n" +
                "Format: Internal waypoint name - see `dumpWaypoints` in `debug.cfg` for a list.\n" +
                "Example: 'MORANNON' would disable the Black Gate waypoint (display names and internal names often do not match!)"
        ));

        TravelFeatureConfig.UNLOCK_WAYPOINTS = configuration.getBoolean("unlockAllWaypoints", "travel", false, "Unlocks all fast travel waypoints.");
        TravelFeatureConfig.NO_WAYPOINT_LOCKING = configuration.getBoolean("disableWaypointLocking", "travel", false, "Disables alignment-based waypoint locking.");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static boolean isWaypointDisabled(LOTRWaypoint waypoint) {
        return DISABLED_WAYPOINTS.contains(waypoint.name());
    }
}
