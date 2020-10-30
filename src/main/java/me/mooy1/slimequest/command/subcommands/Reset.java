package me.mooy1.slimequest.command.subcommands;

import me.mooy1.slimequest.SlimeQuest;
import me.mooy1.slimequest.command.QuestCommand;
import me.mooy1.slimequest.command.SubCommand;
import me.mooy1.slimequest.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Reset extends SubCommand {
    public Reset(SlimeQuest plugin, QuestCommand cmd) {
        super(plugin, cmd, "reset", "resets a player's quest progress", false);
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        if (args.length == 2) {
            if (sender.hasPermission("slimequest.admin")) {

                Player target = Bukkit.getPlayer(args[1]);

                if (target != null) {

                    //reset quest progress of target player

                } else {

                    sender.sendMessage(ChatColor.RED + "Invalid player");
                }
            } else {
                cmd.sendNoPerm(sender);
            }
        } else {
            sender.sendMessage(ChatColor.WHITE + "Usage: /slimequest reset <player>");
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

            }
        }
        return tabs;
    }
}