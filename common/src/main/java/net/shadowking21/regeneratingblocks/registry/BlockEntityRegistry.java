package net.shadowking21.regeneratingblocks.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.shadowking21.regeneratingblocks.blockentities.RegeneratingBlockEntity;

import static net.shadowking21.regeneratingblocks.RegeneratingBlocks.MOD_ID;

public class BlockEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<RegeneratingBlockEntity>> REGEN_BLOCK_ENTITY =
            BLOCK_ENTITIES.register(
                    new ResourceLocation(MOD_ID, "regen_block_entity"),
                    () -> BlockEntityType.Builder.of(
                            RegeneratingBlockEntity::new,
                            BlockRegistry.REGEN_BLOCK.get()
                    ).build(null)
            );

    public static void init() {
        BLOCK_ENTITIES.register();
    }
}
