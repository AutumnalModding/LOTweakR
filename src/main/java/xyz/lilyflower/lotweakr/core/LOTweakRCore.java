package xyz.lilyflower.lotweakr.core;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import net.minecraft.launchwrapper.LaunchClassLoader;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.SortingIndex(1002)
@IFMLLoadingPlugin.TransformerExclusions("xyz.lilyflower.lotweakr.core")
public class LOTweakRCore implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{LOTweakRTransformer.class.getName()};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    // CURSED REFLECTION MY BELOVED
    static {
        ClassLoader cl = LOTweakRCore.class.getClassLoader(); // we could probably use LaunchClassLoader here?
        if (cl instanceof LaunchClassLoader) {
            LaunchClassLoader loader = (LaunchClassLoader) cl;
            try {
                Field field = loader.getClass().getDeclaredField("transformerExceptions"); // Why is this private?
                field.setAccessible(true);
                Object obj = field.get(loader);

                // This should not be possible. But it is!
                if (obj instanceof Set) {
                    Set set = (Set) obj;
                    set.remove("lotr.common.coremod");
                }

                loader.registerTransformer("xyz.lilyflower.lotweakr.core.LOTweakRTransformer"); // register transformer early, we HAVE to load after LOTR
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

