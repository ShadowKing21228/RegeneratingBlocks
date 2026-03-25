package net.shadowking21.regeneratingblocks.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.shadowking21.regeneratingblocks.blocks.RegeneratingBlock;

import static net.shadowking21.regeneratingblocks.RegeneratingBlocks.MOD_ID;

public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Block> REGEN_BLOCK = BLOCKS.register(
            new ResourceLocation(MOD_ID, "regen_block"),
            () -> new RegeneratingBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(10.0f))
    );

    public static final RegistrySupplier<Item> REGEN_BLOCK_ITEM = ITEMS.register(
            new ResourceLocation(MOD_ID, "regen_block"),
            () -> new BlockItem(REGEN_BLOCK.get(), new Item.Properties())
    );


    public static void init() {
        BLOCKS.register();
        ITEMS.register();
    }
}