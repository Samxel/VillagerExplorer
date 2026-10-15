package com.samxel.villagerexplorer.client;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.samxel.villagerexplorer.Tr;
import com.samxel.villagerexplorer.VillagerExplorer;
import com.samxel.villagerexplorer.config.ExplorerConfig;
import com.samxel.villagerexplorer.screen.StyleScreen;
import com.samxel.villagerexplorer.screen.VillagerExplorerScreen;
import com.samxel.villagerexplorer.screen.style.GuiStyles;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class VillagerExplorerCommand {

    private VillagerExplorerCommand() {
    }

    public static <S> LiteralArgumentBuilder<S> create() {
        return LiteralArgumentBuilder.<S>literal(VillagerExplorer.MOD_ID)
                .executes(context -> open())
                .then(LiteralArgumentBuilder.<S>literal("style")
                        .executes(context -> openStyleScreen())
                        .then(RequiredArgumentBuilder.<S, String>argument("name", StringArgumentType.word())
                                .suggests((context, builder) -> {
                                    Arrays.stream(GuiStyles.values()).map(GuiStyles::id).forEach(builder::suggest);
                                    return builder.buildFuture();
                                })
                                .executes(VillagerExplorerCommand::setStyle)));
    }

    private static <S> int setStyle(CommandContext<S> context) {
        String name = StringArgumentType.getString(context, "name");
        return GuiStyles.byId(name).map(style -> {
            GuiStyles.setCurrent(style);
            ExplorerConfig.save();
            message(Tr.of("command.style_set", Component.literal(style.id()).withStyle(ChatFormatting.GREEN)));
            return open();
        }).orElseGet(() -> {
            message(Tr.of("command.unknown_style", name, availableStyles()).withStyle(ChatFormatting.RED));
            return 0;
        });
    }

    private static int open() {
        VillagerExplorerClient.openLater(VillagerExplorerScreen::new);
        return 1;
    }

    private static int openStyleScreen() {
        VillagerExplorerClient.openLater(() -> new StyleScreen(null));
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
