package io.github.mooy1.slimequest.implementation.data;

import io.github.mooy1.slimequest.SlimeQuest;
import io.github.mooy1.slimequest.implementation.Quest;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PlayerData {

    protected static NamespacedKey key = new NamespacedKey(SlimeQuest.getInstance(), "quest");

    private PlayerData() {}

    @Nullable
    public static int[] getIDs(@Nonnull Player p) {
         return p.getPersistentDataContainer().get(key, PersistentDataType.INTEGER_ARRAY);
    }

    @Nonnull
    public static List<String> getNames(@Nonnull Player p) {
        List<String> unlocked = new ArrayList<>();

        int[] data = getIDs(p);

        if (data != null) {
            for (int i : data) {
                int index = Quest.ids.indexOf(i);
                if (index > -1) {
                    unlocked.add(Quest.names.get(index));
                } else {
                    unlocked.add(ChatColor.RED + "UNKNOWN" + ChatColor.GREEN);
                }
            }
        }

        return unlocked;
    }

    public static void add(@Nonnull Player p, int id) {
        int[] ids = new int[] { id };

        if (p.getPersistentDataContainer().has(key, PersistentDataType.INTEGER_ARRAY)) {

            int[] current = getIDs(p);

            if (current != null) {
                ids = new int[current.length + 1];
                ids[0] = id;
                System.arraycopy(current, 0, ids, 1, current.length);
            }

        }

        p.getPersistentDataContainer().set(key, PersistentDataType.INTEGER_ARRAY, ids);
    }

    public static void remove(@Nonnull Player p, int remove) {

        if (!p.getPersistentDataContainer().has(key, PersistentDataType.INTEGER_ARRAY)) {
            return;
        }

        int[] current = getIDs(p);

        if (current == null || current.length == 0) {
            return;
        }

        List<Integer> newList = new ArrayList<>();

        for (int i : current) {
            newList.add(i);
        }

        if (!newList.remove((Integer) remove)) {
            return;
        }

        int[] newArray = new int[newList.size()];

        int i = 0;
        for (int id : newList) {
            newArray[i] = id;
            i++;
        }

        p.getPersistentDataContainer().set(key, PersistentDataType.INTEGER_ARRAY, newArray);
    }

    public static void reset(@Nonnull Player p) {
        p.getPersistentDataContainer().remove(key);
    }

    public static boolean check(@Nonnull Player p, int id) {
        return ArrayUtils.contains(getIDs(p), id);
    }

    public static boolean checkAll(@Nonnull Player p, int[] ids) {
        int match = 0;
        int[] has = getIDs(p);

        for (int id : ids) {
            if (ArrayUtils.contains(has, id)) {
                match++;
            }
        }

        return (match == ids.length);
    }
}