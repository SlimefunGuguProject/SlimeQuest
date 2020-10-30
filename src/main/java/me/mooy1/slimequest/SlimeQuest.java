package me.mooy1.slimequest;

import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import lombok.NonNull;
import me.mooy1.slimequest.quests.QuestBook;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import org.bukkit.util.StringUtil;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SlimeQuest extends JavaPlugin implements SlimefunAddon, Listener {

    private static SlimeQuest instance;
    public final Config config = new Config(this);

    @Override
    public void onEnable() {
        instance = this;

        //stats
        //@SuppressWarnings("unused")
        //final Metrics metrics = new Metrics(this, 8991);

        PaperLib.suggestPaper(this);

        //auto update
        /*if (getDescription().getVersion().startsWith("DEV - ")) {
            getLogger().log(Level.INFO, "Starting auto update");
            Updater updater = new GitHubBuildsUpdater(this, this.getFile(), "Mooy1/InfinityExpansion/master");
            updater.start();
        } else {
            getLogger().log(Level.WARNING, "You must be on a DEV build to auto update!");
        }*/

        new QuestBook().register(this);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equalsIgnoreCase("/help slimequest")) {
            sendHelp(e.getPlayer());
            e.setCancelled(true);
        }
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        boolean isPlayer = sender instanceof Player;
        Player p = null;

        if (isPlayer) {
            p = (Player) sender;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                sendHelp(sender);
                return true;
            } else
            if (args[0].equalsIgnoreCase("open") && isPlayer) {
                //open quests
                return true;
            } else
            if (args[0].equalsIgnoreCase("book") && isPlayer) {

                Inventory inv = p.getInventory();
                if (Arrays.asList(inv.getContents()).contains(null)) {

                    inv.addItem(QuestBook.QUESTBOOK);

                } else {

                    Location l = p.getLocation();
                    Objects.requireNonNull(l.getWorld()).dropItemNaturally(l, QuestBook.QUESTBOOK);
                }
                return true;
            }

        } else
        if (args.length == 2) {

            if (sender.hasPermission("slimequest.admin")) {

                if (args[0].equalsIgnoreCase("reset")) {
                    Player target = Bukkit.getPlayer(args[1]);

                    if (target != null) {

                        //reset quest progress of target player

                    } else {

                        sender.sendMessage(ChatColor.RED + "Invalid player");
                    }

                    return true;
                }
            }

        } else
        if (args.length == 3) {

            if (sender.hasPermission("slimequest.admin")) {

                if (args[0].equalsIgnoreCase("unlock")) {
                    Player target = Bukkit.getPlayer(args[1]);

                    if (target != null) {

                        if (true) { //check for quest id here
                            //unlock that quest

                        } else {
                            sender.sendMessage(ChatColor.RED + "Invalid id");
                        }

                    } else {
                        sender.sendMessage(ChatColor.RED + "Invalid player");
                    }

                    return true;
                }
            }
        }

        return false;
    }

    private void sendHelp(@Nonnull CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(ChatColors.color("&7----------&a&l SlimeQuest &7----------"));
        sender.sendMessage("");
        sender.sendMessage(ChatColors.color("&6Aliases: &e/sq, /sfquest))"));
        sender.sendMessage("");

        for (int i = 0; i < BASE_COMMANDS.size() ; i++) {
            sender.sendMessage(ChatColors.color("&6/sq " + BASE_COMMANDS.get(i) + " &e- " + BASE_DESCRIPTIONS.get(i)));
        }

        if (sender.hasPermission("slimequest.admin")) {
            for (int i = 0; i < ADMIN_COMMANDS.size() ; i++) {
                sender.sendMessage(ChatColors.color("&6/sq " + ADMIN_COMMANDS.get(i) + " &e- " + ADMIN_DESCRIPTIONS.get(i)));
            }
        }
    }

    private static final List<String> BASE_COMMANDS = new ArrayList<>(Arrays.asList("help", "open", "book"));
    private static final List<String> BASE_DESCRIPTIONS = new ArrayList<>(Arrays.asList("Displays this", "Opens the quest book", "Give you a quest book"));
    private static final List<String> ADMIN_COMMANDS = new ArrayList<>(Arrays.asList("unlock", "reset"));
    private static final List<String> ADMIN_DESCRIPTIONS = new ArrayList<>(Arrays.asList("Unlocks a quest for a player", "Resets a player's quest progress"));

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String alias, @Nonnull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], BASE_COMMANDS, completions);
            if (sender.hasPermission("slimequest.admin")) {
                StringUtil.copyPartialMatches(args[0], ADMIN_COMMANDS, completions);
            }
        }

        Collections.sort(completions);

        return completions;
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/Mooy1/SlimeQuest/issues";
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Nonnull
    public static SlimeQuest getInstance() {
        return instance;
    }
}
