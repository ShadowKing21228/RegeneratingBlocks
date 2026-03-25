package net.shadowking21.regeneratingblocks.utils;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class RBUtils {

    public static final String CURRENT_TIME_TAG = "currentTime";

    public static final String TIMER_TAG = "timer";

    public static final String TARGET_BLOCK_TAG = "targetBlockId";

    public static Block getBlockByName(String block)
    {
        return BuiltInRegistries.BLOCK.get(new ResourceLocation(block));
    }
}
