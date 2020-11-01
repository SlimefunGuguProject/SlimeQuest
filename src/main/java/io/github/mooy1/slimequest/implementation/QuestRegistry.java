package io.github.mooy1.slimequest.implementation;

import io.github.mooy1.slimequest.implementation.data.PlayerData;
import io.github.mooy1.slimequest.utils.MessageUtils;
import io.github.mooy1.slimequest.utils.StackUtils;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.mooy1.slimequest.SlimeQuest;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class QuestRegistry implements Listener {

    private static QuestRegistry registry;
    private final List<QuestStage> stages = new ArrayList<>();
    private final HashMap<Player, Pair<Integer, List<Integer>>> history = new HashMap<>();

    public QuestRegistry(SlimeQuest instance) {
        registry = this;
        PluginManager manager = instance.getServer().getPluginManager();
        manager.registerEvents(this, instance);
        List<String> plugins = new ArrayList<>();

        for (QuestStage.Type type : QuestStage.Type.values()) {
            QuestStage stage = new QuestStage(type);

            for (int i = 0 ; i < type.getPages().length ; i++) {
                String req = type.getReq()[i];

                if (plugins.contains(req) || req.equals("Vanilla") || manager.isPluginEnabled(req)) {
                    if (!plugins.contains(req)) {
                        plugins.add(req);
                        instance.getLogger().log(Level.INFO, "Registering " + req + " Quests...");
                    }
                    stage.registerPage(type.getPages()[i]);
                }
            }

            if (stage.getRegisteredPages().size() > 0) {
                stages.add(stage);
            }
        }

        instance.getLogger().log(Level.INFO, "Quests registered!");
    }

    public void openBook(Player p) {
        if (!history.containsKey(p)) {
            List<Integer> stages = new ArrayList<>();
            for (int i = 0; i < this.stages.size() ; i++) {
                stages.add(0);
            }
            openGroup(p, 0, stages);
        } else {
            Pair<Integer, List<Integer>> playerHistory = history.get(p);
            openGroup(p, playerHistory.getFirstValue(), playerHistory.getSecondValue());
        }
    }

    private static final int PREV = 0;
    private static final int NEXT = 8;

    private void openGroup(Player p, int stageID, List<Integer> stages) {
        history.put(p, new Pair<>(stageID, stages));

        QuestStage stage = this.stages.get(stageID);
        int pageID = stages.get(stageID);
        List<QuestPage> pages = stage.getRegisteredPages();
        QuestPage page = pages.get(pageID);

        ChestMenu menu = new ChestMenu("&8Stage "+ stageID + ": " + stage.getName());
        menu.setEmptySlotsClickable(false);

        page.makeMenu(menu, p);

        menu.addItem(PREV, ChestMenuUtils.getPreviousButton(p, stageID + 1, this.stages.size()), (player, i, itemStack, clickAction) -> {
            if (stageID > 0) {
                openGroup(p, stageID - 1, stages);
            }
            return false;
        });

        menu.addItem(NEXT, ChestMenuUtils.getNextButton(p, stageID + 1, this.stages.size()), (player, i, itemStack, clickAction) -> {
            if (stageID < this.stages.size() - 1) {
                if (stage.getRequiredID() == -1 || PlayerData.get().check(p, stage.getRequiredID())) {
                    openGroup(p, stageID + 1, stages);
                } else {
                    MessageUtils.message(p, ChatColor.RED + "You must complete the quest: " + Quest.names.get(Quest.ids.indexOf(stage.getRequiredID())) + " first!");
                }
            }
            return false;
        });

        for (int i = 1 ; i < pages.size() + 1 ; i++) {
            ItemStack item = pages.get(i - 1).getItem();
            if (i - 1 == pageID) {
                menu.addItem(i, StackUtils.enchant(item.clone()), ChestMenuUtils.getEmptyClickHandler());
            } else {
                menu.addItem(i, item, (player, slot, itemStack, clickAction) -> {
                    if (page.getReqID() == -1 || PlayerData.get().check(p, page.getReqID())) {
                        stages.set(stageID, slot - 1);
                        openGroup(p, stageID, stages);
                    } else {
                        MessageUtils.message(p, ChatColor.RED + "You must complete the quest: " + Quest.names.get(Quest.ids.indexOf(page.getReqID())) + " first!");
                    }

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

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent e) {
        history.remove(e.getPlayer());
    }
}
