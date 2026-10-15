package com.samxel.villagerexplorer.client;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.samxel.villagerexplorer.VillagerExplorer;
import com.samxel.villagerexplorer.config.StyleConfig;
import com.samxel.villagerexplorer.screen.style.GuiStyles;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class VillagerExplorerCommand {
    private static boolean openRequested;

    private VillagerExplorerCommand() {
    }

    public static <S> LiteralArgumentBuilder<S> create() {
        return LiteralArgumentBuilder.<S>literal(VillagerExplorer.MOD_ID)
                .executes(context -> requestOpen())
                .then(LiteralArgumentBuilder.<S>literal("style")
                        .executes(context -> showStyle())
                        .then(RequiredArgumentBuilder.<S, String>argument("name", StringArgumentType.word())
                                .suggests((context, builder) -> {
                                    Arrays.stream(GuiStyles.values()).map(GuiStyles::id).forEach(builder::suggest);
                                    return builder.buildFuture();
                                })
                                .executes(VillagerExplorerCommand::setStyle)));
    }

    public static boolean consumeOpenRequest() {
        boolean requested = openRequested;
        openRequested = false;
        return requested;
    }

    private static <S> int setStyle(CommandContext<S> context) {
        String name = StringArgumentType.getString(context, "name");
        return GuiStyles.byId(name).map(style -> {
            GuiStyles.setCurrent(style);
            StyleConfig.save();
            message(Component.literal("Villager Explorer style set to ").append(Component.literal(style.id()).withStyle(ChatFormatting.GREEN)));
            return requestOpen();
        }).orElseGet(() -> {
            message(Component.literal("Unknown style: " + name + ". Available: " + availableStyles()).withStyle(ChatFormatting.RED));
            return 0;
        });
    }

    private static int showStyle() {
        message(Component.literal("Current style: ")
                .append(Component.literal(GuiStyles.current().id()).withStyle(ChatFormatting.GREEN))
                .append(Component.literal(" (available: " + availableStyles() + ")")));
        return 1;
    }

    // the chat screen closes after the command runs, so the explorer opens on the next client tick
    private static int requestOpen() {
        openRequested = true;
        return 1;
    }

    private static String availableStyles() {
        return Arrays.stream(GuiStyles.values()).map(GuiStyles::id).collect(Collectors.joining(", "));
    }

    private static void message(Component component) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            minecraft.player.sendSystemMessage(component);
        }
    }
}
