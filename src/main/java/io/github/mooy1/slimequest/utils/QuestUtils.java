package io.github.mooy1.slimequest.utils;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class QuestUtils {

    private QuestUtils() {}

    public void addQuestStack(ChestMenu menu, int slot, ItemStack display, @Nonnull HashMap<String, Integer> req, Player p) {
        menu.addItem(slot, makeQuestItem(display, req, p), makeQuestHandler(req, p));
    }

    @Nonnull
    private ItemStack makeQuestItem(@Nonnull ItemStack display, @Nonnull HashMap<String, Integer> req, Player p) {
        ItemStack item = display.clone();

        //lore stuff


        return item;
    }

    @Nonnull
    private ChestMenu.MenuClickHandler makeQuestHandler(@Nonnull HashMap<String, Integer> req, Player p) {
        if (false) { //if player unlocked it already
            return ChestMenuUtils.getEmptyClickHandler();
        }

        return (player, i, itemStack, clickAction) -> {
            Inventory inv = player.getInventory();

            Map<String, Integer> contents = new HashMap<>();

            for (ItemStack item : inv.getContents()) {
                String id = StackUtils.getIDFromItem(item);
                if (id == null) continue;
                contents.merge(id, item.getAmount(), Integer::sum);
            }

            int match = 0;
            for (String id : req.keySet()) {
                if (contents.containsKey(id) && contents.get(id) >= req.get(id)) {
                    match++;
                }
            }

            if (match == req.size()) {
                //unlock the quest
            }

            return false;
        };
    }
}