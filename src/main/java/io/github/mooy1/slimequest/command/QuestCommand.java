package io.github.mooy1.slimequest.command;

import io.github.mooy1.slimequest.SlimeQuest;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Command stuff, Many stuffs from Slimefun's Command stuff as well as modified stuff
 *
 * @author Mooy1
 *
 */
public class QuestCommand implements CommandExecutor, Listener {

    private boolean registered = false;
    private final SlimeQuest plugin;
    public final List<SubCommand> commands = new LinkedList<>();

    public QuestCommand(@Nonnull SlimeQuest plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
        if (args.length > 0) {
            for (SubCommand command : commands) {
                if (args[0].equalsIgnoreCase(command.getName())) {
                    command.onExecute(sender, args);
                    return true;
                }
            }
        }
        return false;
    }
    @Nonnull
    public SlimeQuest getPlugin() {
        return plugin;
    }

    public void register() {
        Validate.isTrue(!registered, "SlimeQuest's subcommands have already been registered!");

        registered = true;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        Objects.requireNonNull(plugin.getCommand("slimequest")).setExecutor(this);
        Objects.requireNonNull(plugin.getCommand("slimequest")).setTabCompleter(new QuestTabCompleter(this));
        commands.addAll(SubCommands.getAllCommands(this));
    }

    public void sendHelp(@Nonnull CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(ChatColors.color("&7----------&6&l SlimeQuest &7----------"));
        sender.sendMessage("");
        sender.sendMessage(ChatColors.color("&6Aliases: &e/sq, /sfquest"));
        sender.sendMessage("");

        for (SubCommand cmd : commands) {
            if (!cmd.isHidden()) {
                sender.sendMessage(ChatColors.color("&6/sq " + cmd.getName() + " &e- " + cmd.getDescription()));
            }
        }

        sender.sendMessage("");
    }

    public void sendNoPerm(@Nonnull CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
    }

    public void sendPlayerOnly(@Nonnull CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Only players can run this command!");
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equalsIgnoreCase("/help slimequest")) {
            sendHelp(e.getPlayer());
            e.setCancelled(true);
        }
    }

    public List<String> getSubCommandNames() {
        return commands.stream().map(SubCommand::getName).collect(Collectors.toList());
    }
}
