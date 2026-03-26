package net.shadowking21.regeneratingblocks.events;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Marker;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.shadowking21.regeneratingblocks.RegeneratingBlocks;
import net.shadowking21.regeneratingblocks.blockentities.RegeneratingBlockEntity;
import net.shadowking21.regeneratingblocks.blocks.RegeneratingBlock;
import net.shadowking21.regeneratingblocks.registry.BlockRegistry;
import net.shadowking21.regeneratingblocks.utils.RBUtils;

import java.util.List;
import java.util.Objects;

public class RBEvents {

    public static void init()
    {
        onBlockBreak();
        onTagBlockBreak();
    }

    private static void onBlockBreak()
    {
        BlockEvent.BREAK.register((level, blockPos, blockState, serverPlayer, intValue) -> {
            if (level.isClientSide) return EventResult.pass();

            // 1. Ищем маркер (с небольшим запасом по области)
            List<Marker> markers = level.getEntitiesOfClass(Marker.class, new AABB(blockPos).inflate(0.2));

            if (markers.isEmpty()) return EventResult.pass();

            for (Marker marker : markers) {
                CompoundTag fullNbt = new CompoundTag();
                marker.saveWithoutId(fullNbt);

                if (fullNbt.contains("data")) {
                    CompoundTag customData = fullNbt.getCompound("data");

                    if (customData.getBoolean("IsRegeneratingPoint")) {

                        // --- КЛЮЧЕВОЙ МОМЕНТ: ВЫПАДЕНИЕ РЕСУРСОВ ---
                        // Мы вызываем дроп ДО замены блока.
                        // Параметры: (состояние, мир, позиция, BlockEntity, игрок, предмет в руке)
                        Block.dropResources(
                                blockState,
                                level,
                                blockPos,
                                level.getBlockEntity(blockPos),
                                serverPlayer,
                                serverPlayer.getMainHandItem()
                        );
                        // -------------------------------------------

                        String target = customData.getString("TargetBlock");
                        int timer = customData.getInt("RegenTimer");

                        // Ставим магический блок обратно
                        level.setBlockAndUpdate(blockPos, BlockRegistry.REGEN_BLOCK.get().defaultBlockState());

                        BlockEntity be = level.getBlockEntity(blockPos);
                        if (be instanceof RegeneratingBlockEntity regenBe) {
                            regenBe.setTargetBlock(target, timer);
                        }

                        marker.discard();

                        // Прерываем стандартную поломку (чтобы блок не исчез и не было ВТОРОГО дропа)
                        return EventResult.interruptFalse();
                    }
                }
            }
            return EventResult.pass();
        });
    }

    public static void onTagBlockBreak()
    {
        BlockEvent.BREAK.register((level, blockPos, blockState, serverPlayer, intValue) -> {
            boolean hasTag = blockState.getTags().anyMatch(blockTagKey ->
                    blockTagKey.location().getPath().startsWith("regenerating/") && blockTagKey.location().getNamespace().equals(RegeneratingBlocks.MOD_ID));
            if (hasTag)
            {
                var timer = blockState.getTags()
                        .map(tag -> tag.location().getPath()) // Получаем путь, например "regenerating/100"
                        .filter(path -> path.startsWith("regenerating/")) // Оставляем только те, что в нашей папке
                        .map(path -> {
                            try {
                                String timeStr = path.substring(path.lastIndexOf('/') + 1);
                                return Integer.parseInt(timeStr);
                            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .findFirst()
                        .orElse(RegeneratingBlock.DEFAULT_TIMER);

                level.setBlockAndUpdate(blockPos, BlockRegistry.REGEN_BLOCK.get().defaultBlockState());
                BlockEntity be = level.getBlockEntity(blockPos);
                if (be instanceof RegeneratingBlockEntity regenBe) {
                    regenBe.setTargetBlock(RBUtils.getNameOfBlock(blockState.getBlock()), timer);
                }

                Block.dropResources(
                        blockState,
                        level,
                        blockPos,
                        level.getBlockEntity(blockPos),
                        serverPlayer,
                        serverPlayer.getMainHandItem()
                );
                return EventResult.interruptFalse();
            }
            return EventResult.pass();
        });
    }

}
