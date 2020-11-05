package io.github.mooy1.slimequest.command.subcommands;

import io.github.mooy1.slimequest.SlimeQuest;
import io.github.mooy1.slimequest.command.QuestCommand;
import io.github.mooy1.slimequest.command.SubCommand;
import io.github.mooy1.slimequest.implementation.Quest;
import io.github.mooy1.slimequest.implementation.QuestRegistry;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class List extends SubCommand {
    public List(SlimeQuest plugin, QuestCommand cmd) {
        super(plugin, cmd, "list", "lists registered stages and quests", true);
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {

        if (args.length < 2) {
            sender.sendMessage(ChatColor.WHITE + "Usage: /slimequest list <stages/quests>");
            return;
        }

        if (args[1].equals("stages")) {
            sender.sendMessage(ChatColor.GREEN + QuestRegistry.stageNames.toString());
            return;
        }

        if (args[1].equals("quests")) {
            sender.sendMessage(ChatColor.GREEN + Quest.names.toString());
            return;
        }

        sender.sendMessage(ChatColor.RED + "Invalid input!");

    }

    @Override
    public java.util.List<String> onTab(@Nonnull CommandSender sender, @Nonnull String[] args) {
        java.util.List<String> tabs = new ArrayList<>();
        if (sender.hasPermission("slimequest.admin")) {
            if (args.length == 2) {

                tabs.add("stages");
                tabs.add("quests");

            }
        }
        return tabs;
    }
}