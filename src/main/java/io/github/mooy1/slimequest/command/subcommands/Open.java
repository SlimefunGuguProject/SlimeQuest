package io.github.mooy1.slimequest.command.subcommands;

import io.github.mooy1.slimequest.implementation.QuestRegistry;
import io.github.mooy1.slimequest.SlimeQuest;
import io.github.mooy1.slimequest.command.QuestCommand;
import io.github.mooy1.slimequest.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class Open extends SubCommand {
    public Open(SlimeQuest plugin, QuestCommand cmd) {
        super(plugin, cmd, "open", "opens your quest book", false);
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        if (!(sender instanceof Player)) {
            cmd.sendPlayerOnly(sender);
            return;
        }
        Player p = (Player) sender;

        QuestRegistry.get().openBook(p);
    }
}