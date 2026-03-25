package net.shadowking21.regeneratingblocks.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.shadowking21.regeneratingblocks.blockentities.RegeneratingBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RegeneratingBlock extends Block implements EntityBlock {

    public final String defaultBlock = "minecraft:cobblestone";

    public final int defaultTimer = 20; //1200

    public RegeneratingBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        BlockEntity be = level.getBlockEntity(pos);

        if (be instanceof RegeneratingBlockEntity timedBe) {
            timedBe.setTargetBlock(getRegenerateBlock(stack), getRegenerateTimer(stack));
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RegeneratingBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {

        return level.isClientSide ? null : (lvl, pos, st, be) -> {
            if (be instanceof RegeneratingBlockEntity regeneratingBe) {
                regeneratingBe.tick(lvl, pos, st, regeneratingBe);
            }
        };
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter world, List<Component> tooltip, TooltipFlag flag) {
        String blockId = getRegenerateBlock(stack);
        int seconds = getRegenerateTimer(stack) / 20; // Переводим тики в секунды

        Block target = BuiltInRegistries.BLOCK.get(new ResourceLocation(blockId));

        tooltip.add(Component.translatable("tooltip.regeneratingblocks.morphto").withStyle(ChatFormatting.GRAY)
                .append(target.getName().withStyle(ChatFormatting.YELLOW)));

        tooltip.add(Component.translatable("tooltip.regeneratingblocks.time").withStyle(ChatFormatting.GRAY)
                        .append(Component.literal(String.valueOf(seconds)).withStyle(ChatFormatting.AQUA))
                .append(Component.translatable("tooltip.regeneratingblocks.second").withStyle(ChatFormatting.AQUA)));
    }

    public String getRegenerateBlock(ItemStack itemStack)
    {
        var tag = itemStack.getTag();
        return tag != null && tag.contains("TargetBlock", Tag.TAG_STRING) ? tag.getString("TargetBlock") : defaultBlock;
    }

    public int getRegenerateTimer(ItemStack itemStack)
    {
        var tag = itemStack.getTag();
        return tag != null && tag.contains("Timer", Tag.TAG_INT) ? tag.getInt("Timer") : defaultTimer;
    }


}
