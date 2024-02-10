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
        return new String[]{ASMTransformer.class.getName()};
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
        ClassLoader cl = LOTweakRCore.class.getClassLoader();
        if (cl instanceof LaunchClassLoader) { // has to be MC's classloader. idk why MC even has its own tbh.
            LaunchClassLoader loader = (LaunchClassLoader) cl;
            try {
                Field field = loader.getClass().getDeclaredField("transformerExceptions"); // LaunchWrapper transformer exceptions
                field.setAccessible(true);
                Object obj = field.get(loader);

                // "you can't coremod a coremod" OH YEAH??? REFLECTION GOES BRRR
                if (obj instanceof Set) {
                    Set set = (Set) obj;
                    set.remove("lotr.common.coremod"); // don't need to set the field back because Java is... one of the languages of all time, that's for sure.
                }

                loader.registerTransformer("xyz.lilyflower.lotweakr.core.ASMTransformer"); // I forgot why we have to do this. It just works.
            } catch (NoSuchFieldException | IllegalAccessException e) { // ah fuck, something broke lole
                throw new RuntimeException(e);
            }
        }
    }
}

