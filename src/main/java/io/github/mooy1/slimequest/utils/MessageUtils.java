package io.github.mooy1.slimequest.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Collection of utils for sending messages to players and broadcasting
 *
 * @author Mooy1
 */
public class MessageUtils {

    private MessageUtils() {}
    private static final Map<Player, Long> coolDowns = new HashMap<>();

    public static final String NAME = ChatColor.GOLD + "SlimeQuest";
    public static final String PREFIX = (ChatColor.GREEN + "[" + NAME + ChatColor.GREEN + "]" + ChatColor.WHITE + " ");

    public static void message(@Nonnull Player p, @Nonnull String message) {
        p.sendMessage(PREFIX + message);
    }

    public static void messageWithCD(@Nonnull Player p, @Nonnull String message, long coolDown) {
        if (coolDowns.containsKey(p) && System.currentTimeMillis() - coolDowns.get(p) < coolDown) {
            return;
        }
        coolDowns.put(p, System.currentTimeMillis());
        p.sendMessage(PREFIX + message);
    }

    public static void broadcast(@Nonnull String message) {
        Bukkit.broadcastMessage(PREFIX + message);
    }
}