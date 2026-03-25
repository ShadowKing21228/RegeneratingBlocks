package net.shadowking21.regeneratingblocks.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.shadowking21.regeneratingblocks.registry.BlockRegistry;

import java.util.Collection;

public class RegeneratingBlocksCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        dispatcher.register(
                Commands.literal("regeneratingblocks")
                        .requires(source -> source.hasPermission(2)) // уровень 2 = как /give
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("block", BlockStateArgument.block(buildContext))
                                        .then(Commands.argument("timer", IntegerArgumentType.integer(1))
                                                .executes(RegeneratingBlocksCommand::execute)
                                        )
                                )
                        )
        );
    }

    private static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Collection<ServerPlayer> targets = EntityArgument.getPlayers(context, "target");
        BlockInput blockInput = BlockStateArgument.getBlock(context, "block");
        int timer = IntegerArgumentType.getInteger(context, "timer");

        // Получаем ItemStack твоего regenerating block
        ItemStack stack = new ItemStack(BlockRegistry.REGEN_BLOCK_ITEM.get()); // или как у тебя называется RegistryObject<Item>

        // Записываем данные в NBT (точно так же, как делает твой BlockEntity при размещении)
        CompoundTag tag = stack.getOrCreateTag();

        tag.putString("TargetBlock", blockInput.getState().getBlock().builtInRegistryHolder().key().location().toString());

        tag.putInt("Timer", timer);

        int countGiven = 0;
        for (ServerPlayer player : targets) {
            ItemStack copy = stack.copy();
            if (player.getInventory().add(copy)) {
                countGiven++;
            } else {
                // если инвентарь полон — дропаем на землю
                player.drop(copy, false);
            }
        }

        if (countGiven > 0) {
            int finalCountGiven = countGiven;
            context.getSource().sendSuccess(() ->
                    Component.translatable("commands.regeneratingblocks.success",
                            finalCountGiven, blockInput.getState().getBlock().getName(), timer), true);
        }

        return countGiven;
    }
}
