package net.shadowking21.regeneratingblocks.forge.integration.jade;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.shadowking21.regeneratingblocks.blockentities.RegeneratingBlockEntity;
import net.shadowking21.regeneratingblocks.utils.RBUtils;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum RegenBlockComponentProvider implements IBlockComponentProvider {
    INSTANCE;
    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {

        if (blockAccessor.getBlockEntity() instanceof RegeneratingBlockEntity regeneratingBlock)
        {
            Block target = RBUtils.getBlockByName(regeneratingBlock.getTargetBlock());
            iTooltip.add(Component.translatable("tooltip.regeneratingblocks.morphto").withStyle(ChatFormatting.GRAY)
                    .append(target.getName().withStyle(ChatFormatting.YELLOW)));

            iTooltip.add(Component.translatable("jade.regeneratingblocks.remain").withStyle(ChatFormatting.GRAY)
                    .append(Component.literal(String.valueOf(regeneratingBlock.getCurrentTime())).withStyle(ChatFormatting.AQUA))
                    .append(Component.translatable("tooltip.regeneratingblocks.second").withStyle(ChatFormatting.AQUA)));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return RegenBlockWailaPlugin.regenBlockProviderId;
    }
}
