package net.shadowking21.regeneratingblocks;

import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.world.item.CreativeModeTabs;
import net.shadowking21.regeneratingblocks.events.RBEvents;
import net.shadowking21.regeneratingblocks.registry.BlockEntityRegistry;
import net.shadowking21.regeneratingblocks.registry.BlockRegistry;

public final class RegeneratingBlocks {
    public static final String MOD_ID = "regeneratingblocks";

    public static void init() {
        BlockRegistry.init();
        BlockEntityRegistry.init();
        RBEvents.init();
        CreativeTabRegistry.append(CreativeModeTabs.FUNCTIONAL_BLOCKS,
                BlockRegistry.REGEN_BLOCK_ITEM
        );
    }
}