package io.github.mooy1.slimequest.command.subcommands;

import io.github.mooy1.slimequest.SlimeQuest;
import io.github.mooy1.slimequest.command.QuestCommand;
import io.github.mooy1.slimequest.command.SubCommand;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

public class Help extends SubCommand {
    public Help(SlimeQuest plugin, QuestCommand cmd) {
        super(plugin, cmd, "help", "displays this", false);
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        cmd.sendHelp(sender);
    }
}