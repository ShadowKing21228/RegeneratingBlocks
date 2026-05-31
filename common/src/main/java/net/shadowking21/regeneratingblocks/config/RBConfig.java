package net.shadowking21.regeneratingblocks.config;

import net.shadowking21.shadowconfig.annotation.ConfigComment;

public class RBConfig {

    public RBConfig(){}

    @ConfigComment("Default Timer for regenerating block with no nbt data")
    public int defaultTimer = 1200;

    @ConfigComment("Default Block which regenerating block is morph to with no nbt data")
    public String defaultBlock = "minecraft:cobblestone";
}
