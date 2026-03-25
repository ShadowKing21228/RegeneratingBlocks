package net.shadowking21.regeneratingblocks.fabric.integration.jade;

import net.minecraft.resources.ResourceLocation;
import net.shadowking21.regeneratingblocks.blockentities.RegeneratingBlockEntity;
import net.shadowking21.regeneratingblocks.blocks.RegeneratingBlock;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class RegenBlockWailaPlugin implements IWailaPlugin {

    public static ResourceLocation regenBlockProviderId = new ResourceLocation("regeneratingblocks","regen_block_tooltip");
    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(RegenBlockComponentProvider.INSTANCE, RegeneratingBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(RegenBlockComponentProvider.INSTANCE, RegeneratingBlock.class);
    }
}