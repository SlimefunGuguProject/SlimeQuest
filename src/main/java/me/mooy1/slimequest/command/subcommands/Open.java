package me.mooy1.slimequest.command.subcommands;

import me.mooy1.slimequest.SlimeQuest;
import me.mooy1.slimequest.command.QuestCommand;
import me.mooy1.slimequest.command.SubCommand;
import me.mooy1.slimequest.implementation.QuestRegistry;
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