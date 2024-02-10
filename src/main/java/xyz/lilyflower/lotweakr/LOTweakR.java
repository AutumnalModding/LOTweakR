package xyz.lilyflower.lotweakr;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = LOTweakR.MODID, version = LOTweakR.VERSION)
public class LOTweakR
{
    public static final String MODID = "lotweakr";
    public static final String VERSION = "1.0";

    public static final Logger LOGGER = LogManager.getLogger("LOTweakR");

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    }
}
