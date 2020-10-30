package me.mooy1.slimequest.implementation;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mooy1.slimequest.SlimeQuest;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestRegistry {

    public static QuestRegistry registry;
    private final List<QuestGroup> groups = new ArrayList<>();
    private final HashMap<Player, Pair<QuestGroup, List<Integer>>> history = new HashMap<>();

    public QuestRegistry(SlimeQuest instance) {
        registry = this;
        groups.add(new QuestGroup(QuestGroup.Type.VANILLA));
    }

    public void openBook(Player p) {
        if (!history.containsKey(p)) {
            List<Integer> groupPages = new ArrayList<>();
            for (int i = 0 ; i < groups.size() ; i++) {
                groupPages.add(0);
            }
            openGroup(p, groups.get(0), groupPages);
        } else {
            Pair<QuestGroup, List<Integer>> playerHistory = history.get(p);
            openGroup(p, playerHistory.getFirstValue(), playerHistory.getSecondValue());
        }
    }

    private int groupIndex(QuestGroup group) {
        return groups.indexOf(group);
    }

    private static final int PREV = 0;
    private static final int NEXT = 8;

    private void openGroup(Player p, QuestGroup group, List<Integer> groupPages) {
        history.put(p, new Pair<>(group, groupPages));

        int groupID = groups.indexOf(group);
        int pageID = groupPages.get(groupID);
        QuestPage[] pages = group.getPages();
        QuestPage page = pages[pageID];

        ChestMenu menu = new ChestMenu("&6Quests - " + groupID);
        menu.setEmptySlotsClickable(false);

        menu.addItem(PREV, ChestMenuUtils.getPreviousButton(p, groupID - 1, groups.size()), (player, i, itemStack, clickAction) -> {
            if (groupID > 0) {
                openGroup(p, groups.get(groupID - 1), groupPages);
            }
            return false;
        });

        menu.addItem(NEXT, ChestMenuUtils.getNextButton(p, groupID + 1, groups.size()), (player, i, itemStack, clickAction) -> {
            if (groupID < groups.size() - 1) {
                openGroup(p, groups.get(groupID + 1), groupPages);
            }
            return false;
        });

        for (int i = 0 ; i < pages.length ; i++) {
            menu.addItem(i + 1, pages[i].getItem(), (player, slot, itemStack, clickAction) -> {
                setPage(groupPages, group, slot - 1);
                openGroup(p, group, groupPages);
                return false;
            });
            i++;
        }

        ItemStack currentPage = menu.getItemInSlot(pageID + 1).clone();
        currentPage.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        ItemMeta meta = currentPage.getItemMeta();
        if (meta != null) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            currentPage.setItemMeta(meta);
        }
        menu.replaceExistingItem(pageID + 1, currentPage);

        for (int i = pages.length ; i < 8 ; i++) {
            menu.addItem(i + 1, new CustomItem(Material.GRAY_STAINED_GLASS_PANE, ""), ChestMenuUtils.getEmptyClickHandler());
            i++;
        }

        page.onOpen(p, menu);

        menu.open(p);
    }

    private void setPage(List<Integer> groupPages, QuestGroup group, int page) {
        int index = groupIndex(group);
        groupPages.set(index, page);
    }

    public static QuestRegistry get() {
        return registry;
    }
}
