package io.github.mooy1.slimequest.command.subcommands;

import io.github.mooy1.slimequest.SlimeQuest;
import io.github.mooy1.slimequest.command.QuestCommand;
import io.github.mooy1.slimequest.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Unlock extends SubCommand {
    public Unlock(SlimeQuest plugin, QuestCommand cmd) {
        super(plugin, cmd, "unlock", "unlocks a quest for a player", false);
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        if (args.length == 3) {

            if (sender.hasPermission("slimequest.admin")) {

                Player target = Bukkit.getPlayer(args[1]);

                if (target != null) {

                    if (false) { //check for quest id here
                        //unlock that quest

                    } else {
                        sender.sendMessage(ChatColor.RED + "Invalid id");
                    }

                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid player");
                }

            } else {
                cmd.sendNoPerm(sender);
            }

        } else {
            sender.sendMessage(ChatColor.WHITE + "Usage: /slimequest unlock <player> <questID>");
        }
    }

    @Override
    public List<String> onTab(@Nonnull CommandSender sender, @Nonnull String[] args) {
        List<String> tabs = new ArrayList<>();
        if (sender.hasPermission("slimequest.admin")) {
            if (args.length == 2) {

                for (Player p : Bukkit.getOnlinePlayers()) {
                    tabs.add(p.getName());
                }

            } else if (args.length == 3) {

                //add quest ids here

            }
        }
        return tabs;
    }
}