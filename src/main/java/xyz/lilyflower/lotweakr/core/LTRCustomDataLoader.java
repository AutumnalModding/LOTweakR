package xyz.lilyflower.lotweakr.core;

import java.util.ArrayList;
import java.util.List;
import xyz.lilyflower.lotweakr.util.config.loader.CustomFactionLoader;
import xyz.lilyflower.lotweakr.util.config.loader.CustomInvasionLoader;

public interface LTRCustomDataLoader {
    List<LTRCustomDataLoader> LOADERS = new ArrayList<>();

    void run();

    static void add(LTRCustomDataLoader loader) {
        LOADERS.add(loader);
    }

    static void runAll() {
        add(new CustomFactionLoader());
        add(new CustomInvasionLoader());

        LOADERS.forEach(LTRCustomDataLoader::run);
    }
}
