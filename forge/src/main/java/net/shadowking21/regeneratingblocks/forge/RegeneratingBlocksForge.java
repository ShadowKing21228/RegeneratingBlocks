package net.shadowking21.regeneratingblocks.forge;

import net.shadowking21.regeneratingblocks.RegeneratingBlocks;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.shadowking21.regeneratingblocks.forge.config.ConfigInit;

@Mod(RegeneratingBlocks.MOD_ID)
public final class RegeneratingBlocksForge {
    public RegeneratingBlocksForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(RegeneratingBlocks.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        ConfigInit.init();
        RegeneratingBlocks.init();
    }
}
