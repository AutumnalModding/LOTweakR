package xyz.lilyflower.lotweakr.util.config;

import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class GenericFeatureConfig {
    public static boolean UNLOCK_COSMETICS = false;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        UNLOCK_COSMETICS = configuration.getBoolean("unlockCosmetics", "misc", false, "Unlocks all player-specific LOTR cosmetics.");


        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
