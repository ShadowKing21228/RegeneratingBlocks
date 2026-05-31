package net.shadowking21.regeneratingblocks.config;

import net.shadowking21.regeneratingblocks.RegeneratingBlocks;
import net.shadowking21.shadowconfig.config.BaseShadowConfig;
import net.shadowking21.shadowconfig.config.exstensions.toml.SCTomlConfig;

import java.util.function.Supplier;

public class ConfigInit {

    private static BaseShadowConfig<RBConfig> configImpl;
    public static Supplier<RBConfig> config;

    public static void init()
    {
        configImpl = SCTomlConfig.Builder.builder(RBConfig.class)
                .defaults(new RBConfig())
                .modId(RegeneratingBlocks.MOD_ID)
                .build();

        config = ConfigInit::getConfig;
    }

    private static RBConfig getConfig()
    {
        return configImpl.getCurrentConfig();
    }
}

