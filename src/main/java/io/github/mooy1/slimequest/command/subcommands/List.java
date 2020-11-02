package io.github.mooy1.slimequest.command.subcommands;

import io.github.mooy1.slimequest.SlimeQuest;
import io.github.mooy1.slimequest.command.QuestCommand;
import io.github.mooy1.slimequest.command.SubCommand;
import io.github.mooy1.slimequest.implementation.Quest;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class List extends SubCommand {
    public List(SlimeQuest plugin, QuestCommand cmd) {
        super(plugin, cmd, "list", "lists all registered quests", false);
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {

        if (!sender.hasPermission("slimequest.admin")) {
            cmd.sendNoPerm(sender);
            return;
        }

        if (args.length != 2) {
            sender.sendMessage(ChatColor.WHITE + "Usage: /slimequest list <quests/names/ids>");
            return;
        }

        if (args[1].equals("quests")) {
            sender.sendMessage(ChatColor.GREEN + Quest.quests.toString());
            return;
        }

        if (args[1].equals("names")) {
            sender.sendMessage(ChatColor.GREEN + Quest.names.toString());
            return;
        }

        if (args[1].equals("ids")) {
            sender.sendMessage(ChatColor.GREEN + Quest.ids.toString());
            return;
        }

        sender.sendMessage(ChatColor.RED + "Invalid input! Valid inputs are: 'quests', 'names', 'ids'");

    }

    @Override
    public java.util.List<String> onTab(@Nonnull CommandSender sender, @Nonnull String[] args) {
        java.util.List<String> tabs = new ArrayList<>();
        if (sender.hasPermission("slimequest.admin")) {
            if (args.length == 2) {

                tabs.add("quests");
                tabs.add("names");
                tabs.add("ids");

            }
        }
        return tabs;
    }
}