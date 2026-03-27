package net.shadowking21.regeneratingblocks.forge.config;

import net.shadowking21.shadowconfig.annotation.ConfigComment;

public class ConfigModelForge {

    public ConfigModelForge(){}

    @ConfigComment("Default Timer for regenerating block with no nbt data")
    public int defaultTimer = 1200;

    @ConfigComment("Default Block which regenerating block is morph to with no nbt data")
    public String defaultBlock = "minecraft:cobblestone";

}
