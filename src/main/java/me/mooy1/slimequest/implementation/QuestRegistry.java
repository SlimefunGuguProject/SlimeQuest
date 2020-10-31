package me.mooy1.slimequest.implementation;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mooy1.slimequest.SlimeQuest;
import me.mooy1.slimequest.utils.MessageUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class QuestRegistry implements Listener {

    public static QuestRegistry registry;
    private final List<QuestGroup> groups = new ArrayList<>();
    private final HashMap<Player, Pair<Integer, List<Integer>>> history = new HashMap<>();

    public QuestRegistry(SlimeQuest instance) {
        registry = this;
        PluginManager manager = instance.getServer().getPluginManager();
        manager.registerEvents(this, instance);
        List<String> plugins = new ArrayList<>();

        for (QuestGroup.Type type : QuestGroup.Type.values()) {
            QuestGroup group = new QuestGroup(type);

            for (int i = 0 ; i < type.getPages().length ; i++) {
                String req = type.getReq()[i];

                if (plugins.contains(req) || req.equals("Vanilla") || manager.isPluginEnabled(req)) {
                    if (!plugins.contains(req)) {
                        plugins.add(req);
                        instance.getLogger().log(Level.INFO, "Registering " + req + " Quests...");
                    }
                    group.registerPage(type.getPages()[i]);
                }
            }

            if (group.getRegisteredPages().size() > 0) {
                groups.add(group);
            }
        }

        instance.getLogger().log(Level.INFO, "Quests registered!");
    }

    public void openBook(Player p) {
        MessageUtils.broadcast(groups.toString());

        if (!history.containsKey(p)) {
            List<Integer> groupPages = new ArrayList<>();
            for (int i = 0 ; i < groups.size() ; i++) {
                groupPages.add(0);
            }
            openGroup(p, 0, groupPages);
        } else {
            Pair<Integer, List<Integer>> playerHistory = history.get(p);
            openGroup(p, playerHistory.getFirstValue(), playerHistory.getSecondValue());
        }

        MessageUtils.broadcast(history.get(p).toString());
    }

    private static final int PREV = 0;
    private static final int NEXT = 8;

    private void openGroup(Player p, int groupID, List<Integer> groupPages) {
        history.put(p, new Pair<>(groupID, groupPages));

        QuestGroup group = groups.get(groupID);
        int pageID = groupPages.get(groupID);
        List<QuestPage> pages = group.getRegisteredPages();
        QuestPage page = pages.get(pageID);

        MessageUtils.broadcast(groups.toString());
        MessageUtils.broadcast(group.getRegisteredPages().toString());

        ChestMenu menu = new ChestMenu("&8Stage: " + group.getName() + " (" + (groupID) + ")");
        menu.setEmptySlotsClickable(false);
        page.onOpen(p, menu);

        menu.addItem(PREV, ChestMenuUtils.getPreviousButton(p, groupID + 1, groups.size()), (player, i, itemStack, clickAction) -> {
            if (groupID > 0) {
                openGroup(p, groupID - 1, groupPages);
            }
            return false;
        });

        menu.addItem(NEXT, ChestMenuUtils.getNextButton(p, groupID + 1, groups.size()), (player, i, itemStack, clickAction) -> {
            if (groupID < groups.size() - 1) {
                openGroup(p, groupID + 1, groupPages);
            }
            return false;
        });

        for (int i = 1 ; i < pages.size() + 1 ; i++) {
            ItemStack item = pages.get(i - 1).getItem();
            if (i - 1 == pageID) {
                ItemStack currentItem = item.clone();
                currentItem.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
                ItemMeta meta = currentItem.getItemMeta();
                if (meta != null) {
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    currentItem.setItemMeta(meta);
                }
                menu.addItem(i, currentItem, ChestMenuUtils.getEmptyClickHandler());
            } else {
                menu.addItem(i, item, (player, slot, itemStack, clickAction) -> {
                    groupPages.set(groupID, slot - 1);
                    openGroup(p, groupID, groupPages);
                    return false;
                });
            }
        }

        for (int i = pages.size() + 1 ; i < 8 ; i++) {
            menu.addItem(i, new CustomItem(Material.GRAY_STAINED_GLASS_PANE, " "), ChestMenuUtils.getEmptyClickHandler());
        }

        p.playSound(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
        menu.open(p);
    }

    public static QuestRegistry get() {
        return registry;
    }
}
