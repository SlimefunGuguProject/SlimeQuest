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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
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

        for (QuestGroup.Type type : QuestGroup.Type.values()) {
            if (type.getReq() == null || manager.isPluginEnabled(type.getReq())) {
                QuestGroup group = new QuestGroup(type);
                instance.getLogger().log(Level.INFO, "Registering " + group.getName() + " Quests...");
                groups.add(group);
            }
        }

        instance.getLogger().log(Level.INFO, "Quests registered!");
    }

    public void openBook(Player p) {
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
    }

    private static final int PREV = 0;
    private static final int NEXT = 8;

    private void openGroup(Player p, int groupID, List<Integer> groupPages) {
        history.put(p, new Pair<>(groupID, groupPages));

        QuestGroup group = groups.get(groupID);
        int pageID = groupPages.get(groupID);
        int groupPage = groupID + 1;
        QuestPage[] pages = group.getPages();
        QuestPage page = pages[pageID];

        ChestMenu menu = new ChestMenu("&6" + group.getName() + " Quests (" + groupPage + ")");
        menu.setEmptySlotsClickable(false);

        menu.addItem(PREV, ChestMenuUtils.getPreviousButton(p, groupPage, groups.size()), (player, i, itemStack, clickAction) -> {
            if (groupID > 0) {
                MessageUtils.broadcast("PREV");
                openGroup(p, groupID - 1, groupPages);
            } else {
                MessageUtils.broadcast("PREV FAIL");
            }
            return false;
        });

        menu.addItem(NEXT, ChestMenuUtils.getNextButton(p, groupPage, groups.size()), (player, i, itemStack, clickAction) -> {
            if (groupID < groups.size() - 1) {
                MessageUtils.broadcast("NEXT");
                openGroup(p, groupID + 1, groupPages);
            } else {
                MessageUtils.broadcast(groupID + " " + (groups.size() - 1));
            }
            return false;
        });

        for (int i = 0 ; i < pages.length ; i++) {
            menu.addItem(i + 1, pages[i].getItem(), (player, slot, itemStack, clickAction) -> {
                groupPages.set(groupID, slot - 1);
                openGroup(p, groupID, groupPages);
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

        p.playSound(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
        menu.open(p);
    }

    public static QuestRegistry get() {
        return registry;
    }

    @EventHandler
    private void onPlayerLeave(PlayerQuitEvent e) {
        history.remove(e.getPlayer());
    }
}
