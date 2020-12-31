package io.github.mooy1.slimequest.command.subcommands;

import io.github.mooy1.slimequest.SlimeQuest;
import io.github.mooy1.slimequest.command.QuestCommand;
import io.github.mooy1.slimequest.command.SubCommand;
import io.github.mooy1.slimequest.implementation.Quest;
import io.github.mooy1.slimequest.implementation.QuestPage;
import io.github.mooy1.slimequest.implementation.QuestRegistry;
import io.github.mooy1.slimequest.implementation.QuestStage;
import io.github.mooy1.slimequest.implementation.data.QuestData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Complete extends SubCommand {
    public Complete(SlimeQuest plugin, QuestCommand cmd) {
        super(plugin, cmd, "complete", "completes a quest for a player", true);
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {

        if (args.length != 4 || !(args[2].equals("quest") || args[2].equals("stage"))) {
            sender.sendMessage(ChatColor.WHITE + "用法: /slimequest complete <player> <quest/stage> <name>");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Invalid player!");
            return;
        }

        if (args[2].equals("quest")) { //quest

            if (!Quest.names.contains(args[3])) {
                sender.sendMessage(ChatColor.RED + "无效的任务!");
                return;
            }

            int index = Quest.names.indexOf(args[3]);
            int targetID = Quest.ids.get(index);

            if (QuestData.check(target, targetID)) {
                sender.sendMessage(ChatColor.RED + target.getName() + " 已经完成了该任务!");
                return;
            }

            Quest.quests.get(index).giveRewards(target, target.getInventory(), target.getInventory().getStorageContents());
            Quest.quests.get(index).giveUnlock(target, true);
            sender.sendMessage(ChatColor.GREEN + "完成的任务" + args[3] + " 对于 " + target.getName());

        } else if (args[2].equals("stage")) { //stage

            if (!QuestRegistry.stageNames.contains(args[3])) {
                sender.sendMessage(ChatColor.RED + "无效阶段!");
                return;
            }

            QuestStage stage = QuestRegistry.stages.get(QuestRegistry.stageNames.indexOf(args[3]));

            for (QuestPage page : stage.pages) {
                for (Quest quest : page.quests) {
                    if (QuestData.check(target, quest.getId())) continue;
                    quest.giveRewards(target, target.getInventory(), target.getInventory().getStorageContents());
                    quest.giveUnlock(target, true);
                }
            }

            sender.sendMessage(ChatColor.GREEN + "完成的阶段 " + stage.getName() + " 对于 " + target.getName());
        }
    }

    @Override
    public List<String> onTab(@Nonnull CommandSender sender, @Nonnull String[] args) {
        List<String> tabs = new ArrayList<>();
        if (sender.hasPermission("slimequest.admin")) {
            switch (args.length) {
                case 2: {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        tabs.add(p.getName());
                    }
                    break;
                }
                case 3: {
                    tabs.add("quest");
                    tabs.add("stage");
                    break;
                }
                case 4: {
                    if (args[2].equals("quest")) {

                        Player p = Bukkit.getPlayer(args[1]);

                        if (p != null) {
                            tabs.addAll(Quest.names);
                            tabs.removeAll(QuestData.getNames(p));
                        }

                    } else if (args[2].equals("stage")) {

                        tabs.addAll(QuestRegistry.stageNames);
                    }
                    break;
                }
            }
        }
        return tabs;
    }
}
