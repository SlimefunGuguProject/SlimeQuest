package io.github.mooy1.slimequest.command.subcommands;

import io.github.mooy1.slimequest.SlimeQuest;
import io.github.mooy1.slimequest.command.QuestCommand;
import io.github.mooy1.slimequest.command.SubCommand;
import io.github.mooy1.slimequest.implementation.Quest;
import io.github.mooy1.slimequest.implementation.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Remove extends SubCommand {
    public Remove(SlimeQuest plugin, QuestCommand cmd) {
        super(plugin, cmd, "remove", "removes a quest from a player", false);
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        if (!sender.hasPermission("slimequest.admin")) {
            cmd.sendNoPerm(sender);
            return;
        }

        if (args.length != 3) {
            sender.sendMessage(ChatColor.WHITE + "Usage: /slimequest remove <player> <quest>");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Invalid player!");
            return;
        }

        if (!Quest.names.contains(args[2])) {
            sender.sendMessage(ChatColor.RED + "Invalid quests!");
            return;
        }

        int index = Quest.names.indexOf(args[2]);
        int targetID = Quest.ids.get(index);

        if (!PlayerData.get().check(target, targetID)) {
            sender.sendMessage(ChatColor.RED + target.getName() + " hasn't completed that quest!");
            return;
        }

        PlayerData.get().remove(target, targetID);
        sender.sendMessage(ChatColor.YELLOW + "Removed quest " + args[2] + " from " + target.getName() + "!");
    }

    @Override
    public List<String> onTab(@Nonnull CommandSender sender, @Nonnull String[] args) {
        List<String> tabs = new ArrayList<>();
        if (sender.hasPermission("slimequest.admin")) {
            if (args.length == 2) {

                for (Player p : Bukkit.getOnlinePlayers()) {
                    tabs.add(p.getName());
                }

            } else {
                Player p = Bukkit.getPlayer(args[1]);

                if (p != null && args.length == 3) {
                    tabs.addAll(PlayerData.get().getNames(p));
                }
            }
        }
        return tabs;
    }
}