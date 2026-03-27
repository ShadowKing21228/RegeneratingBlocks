package net.shadowking21.regeneratingblocks.forge.config;

import net.shadowking21.regeneratingblocks.RegeneratingBlocks;
import net.shadowking21.regeneratingblocks.config.RBConfig;
import net.shadowking21.shadowconfig.config.BaseShadowConfig;
import net.shadowking21.shadowconfig.config.exstensions.toml.SCTomlConfig;

public class ConfigInit {

    public static BaseShadowConfig<ConfigModelForge> config;

    public static void init()
    {
        config = SCTomlConfig.Builder.builder(ConfigModelForge.class)
                .defaults(new ConfigModelForge())
                .modId(RegeneratingBlocks.MOD_ID)
                .build();

        RBConfig.config = ConfigInit::getConfig;
    }

    private static RBConfig getConfig()
    {
        var commonConfig = new RBConfig();
        var loaderConfig = config.getCurrentConfig();
        commonConfig.defaultBlock = loaderConfig.defaultBlock;
        commonConfig.defaultTimer = loaderConfig.defaultTimer;
        return commonConfig;
    }
}
