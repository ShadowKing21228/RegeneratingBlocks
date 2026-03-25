package net.shadowking21.regeneratingblocks.fabric.integration.jade;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.shadowking21.regeneratingblocks.blockentities.RegeneratingBlockEntity;
import net.shadowking21.regeneratingblocks.utils.RBUtils;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum RegenBlockComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        var serverData = blockAccessor.getServerData();
        if (serverData.contains(RBUtils.CURRENT_TIME_TAG) && serverData.contains(RBUtils.TIMER_TAG) && serverData.contains(RBUtils.TARGET_BLOCK_TAG))
        {
            Block target = RBUtils.getBlockByName(serverData.getString(RBUtils.TARGET_BLOCK_TAG));
            iTooltip.add(Component.translatable("tooltip.regeneratingblocks.morphto").withStyle(ChatFormatting.GRAY)
                    .append(target.getName().withStyle(ChatFormatting.YELLOW)));

            iTooltip.add(Component.translatable("jade.regeneratingblocks.remain").withStyle(ChatFormatting.GRAY)
                    .append(Component.literal(String.valueOf((float) serverData.getInt(RBUtils.CURRENT_TIME_TAG) / 20)).withStyle(ChatFormatting.AQUA))
                    .append(Component.literal(" / "))
                    .append(Component.literal(String.valueOf((float) serverData.getInt(RBUtils.TIMER_TAG) / 20)).withStyle(ChatFormatting.AQUA))
                    .append(Component.translatable("tooltip.regeneratingblocks.second").withStyle(ChatFormatting.AQUA)));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return RegenBlockWailaPlugin.regenBlockProviderId;
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        var be = (RegeneratingBlockEntity) blockAccessor.getBlockEntity();
        compoundTag.putInt(RBUtils.CURRENT_TIME_TAG, be.getCurrentTime());
        compoundTag.putInt(RBUtils.TIMER_TAG, be.getTimer());
        compoundTag.putString(RBUtils.TARGET_BLOCK_TAG, be.getTargetBlock());
    }
}
