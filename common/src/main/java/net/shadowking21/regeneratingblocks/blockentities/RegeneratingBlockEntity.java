package net.shadowking21.regeneratingblocks.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Marker;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.shadowking21.regeneratingblocks.registry.BlockEntityRegistry;

public class RegeneratingBlockEntity extends BlockEntity implements BlockEntityTicker<RegeneratingBlockEntity> {

    public int timer = 20;

    private int currentTime = timer;

    private String targetBlockId = "minecraft:air";

    public RegeneratingBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.REGEN_BLOCK_ENTITY.get(), pos, state);
    }

    public void tick(Level level, BlockPos pos, BlockState state, RegeneratingBlockEntity be) {
        if (level.isClientSide) return;

        be.currentTime--;
        if (be.currentTime <= 0) {
            // 1. Ставим финальный блок (например, Алмазную руду)
            Block target = BuiltInRegistries.BLOCK.get(new ResourceLocation(be.targetBlockId));
            level.setBlockAndUpdate(pos, target.defaultBlockState());

            // 2. Ставим "Знак" (Маркер), чтобы знать, что после поломки руды её надо вернуть
            Marker anchor = EntityType.MARKER.create(level);
            if (anchor != null) {
                // 1. Устанавливаем позицию (центр блока)
                anchor.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);

                // 2. Сначала добавляем в мир, чтобы у сущности появились UUID и мир
                level.addFreshEntity(anchor);

                // 3. Теперь безопасно обновляем NBT
                CompoundTag fullNbt = new CompoundTag();
                anchor.saveWithoutId(fullNbt); // Сохраняем текущее состояние (с позицией!)

                CompoundTag customData = new CompoundTag();
                customData.putBoolean("IsRegeneratingPoint", true);
                customData.putString("TargetBlock", be.targetBlockId);
                customData.putInt("RegenTimer", be.timer);

                // Кладем наши данные в "data"
                fullNbt.put("data", customData);

                // Загружаем обратно. Теперь позиция в fullNbt правильная, и она не сбросится.
                anchor.load(fullNbt);
            }
        }
    }

    public void setTargetBlock(String blockId, int time) {
        this.targetBlockId = blockId;
        this.timer = time;
        this.currentTime = timer;
        this.setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("CurrentTime", this.currentTime);
        nbt.putInt("Timer", this.timer);
        nbt.putString("TargetBlock", this.targetBlockId);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.currentTime = nbt.getInt("CurrentTime");
        this.timer = nbt.getInt("Timer");
        this.targetBlockId = nbt.getString("TargetBlock");
    }

    public int getCurrentTime()
    {
        return currentTime;
    }

    public int getTimer()
    {
        return timer;
    }

    public String getTargetBlock()
    {
        return targetBlockId;
    }
}