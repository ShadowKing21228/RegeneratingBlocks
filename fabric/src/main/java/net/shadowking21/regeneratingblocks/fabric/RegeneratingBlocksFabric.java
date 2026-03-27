package net.shadowking21.regeneratingblocks.fabric;

import net.shadowking21.regeneratingblocks.RegeneratingBlocks;
import net.fabricmc.api.ModInitializer;
import net.shadowking21.regeneratingblocks.fabric.config.ConfigInit;

public final class RegeneratingBlocksFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        ConfigInit.init();
        RegeneratingBlocks.init();
    }
}
