package me.mooy1.slimequest.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

/**
 * Collection of utils for sending messages to players and broadcasting
 *
 * @author Mooy1
 */
public class MessageUtils {

    private MessageUtils() {}

    public static final String NAME = ChatColor.GREEN + "Slime" + ChatColor.GOLD + "Quest";
    public static final String PREFIX = (ChatColor.DARK_GREEN + "[" + NAME + ChatColor.DARK_GREEN + "]" + ChatColor.WHITE + " ");

    public static void message(@Nonnull Player p, @Nonnull String message) {
        p.sendMessage(PREFIX + message);
    }

    public static void broadcast(@Nonnull String message) {
        Bukkit.broadcastMessage(PREFIX + message);
    }
}