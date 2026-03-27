package net.shadowking21.regeneratingblocks.config;

import java.util.function.Supplier;

public class RBConfig {

    public RBConfig(){}

    public int defaultTimer = 1200;

    public String defaultBlock = "minecraft:cobblestone";

    public static Supplier<RBConfig> config;
}
