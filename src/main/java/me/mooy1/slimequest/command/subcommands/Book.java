package me.mooy1.slimequest.command.subcommands;

import me.mooy1.slimequest.SlimeQuest;
import me.mooy1.slimequest.command.QuestCommand;
import me.mooy1.slimequest.command.SubCommand;
import me.mooy1.slimequest.implementation.QuestBook;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class Book extends SubCommand {
    public Book(SlimeQuest plugin, QuestCommand cmd) {
        super(plugin, cmd, "book", "gives you a quest book", false);
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        if (!(sender instanceof Player)) {
            cmd.sendPlayerOnly(sender);
            return;
        }
        Player p = (Player) sender;

        Inventory inv = p.getInventory();
        if (Arrays.asList(inv.getStorageContents()).contains(null)) {

            inv.addItem(QuestBook.QUESTBOOK);

        }
    }
}