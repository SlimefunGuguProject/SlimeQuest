package io.github.mooy1.slimequest.implementation;

import io.github.mooy1.slimequest.SlimeQuest;
import io.github.mooy1.slimequest.implementation.data.QuestData;
import io.github.mooy1.slimequest.implementation.stages.sf.SFBasic;
import io.github.mooy1.slimequest.implementation.stages.vanilla.Vanilla;
import io.github.mooy1.slimequest.utils.MessageUtils;
import io.github.mooy1.slimequest.utils.StackUtils;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
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

/**
 * This class holds the questbook history for each player and the registered quest stages
 *
 * @author Mooy1
 *
 */
public class QuestRegistry implements Listener {

    public static final HashMap<Player, Pair<Integer, Integer>> history = new HashMap<>();
    public static final List<QuestStage> stages = new ArrayList<>();
    public static final List<String> stageNames = new ArrayList<>();
    public static final QuestStage[] allStages = {
            new Vanilla(),
            new SFBasic()
    };

    public QuestRegistry(SlimeQuest instance) {
        PluginManager manager = instance.getServer().getPluginManager();
        manager.registerEvents(this, instance);
        List<String> plugins = new ArrayList<>();

        //register stages
        for (QuestStage stage : allStages) {

            String req = stage.getReqPlugin();

            //check for required addons
            if (!plugins.contains(req) && !req.equals("Vanilla") && !manager.isPluginEnabled(req)) continue;

            stage.addPages();

            if (stage.pages.size() == 0) continue;

            stage.findFinalID();

            stages.add(stage);
            stageNames.add(stage.getName().replace(" ", "-"));

            if (plugins.contains(req)) continue;

            plugins.add(req);
            instance.getLogger().log(Level.INFO, "Registering " + req + " Quests...");
        }

        instance.getLogger().log(Level.INFO, "Quests registered!");
    }

    public static void openBook(Player p, boolean forceMain) {
        if (forceMain) {
            openMain(p);
            return;
        }

        if (!history.containsKey(p)) {
            new QuestMenu(p, 0, 0, false);
            return;
        }

        Pair<Integer, Integer> pageStageHistory = history.get(p);
        if (pageStageHistory != null) {
            new QuestMenu(p, pageStageHistory.getFirstValue(), pageStageHistory.getSecondValue(), false);
            return;
        }

        openMain(p);
    }

    private static final ItemStack NONE = new CustomItem(Material.BLACK_STAINED_GLASS_PANE, " ");

    public static void openMain(Player p) {
        history.put(p, null);

        ChestMenu menu = new ChestMenu("&8Stages");
        menu.setEmptySlotsClickable(false);

        //background
        for (int i = 0 ; i < 36 ; i++) {
            menu.addItem(i, NONE, ChestMenuUtils.getEmptyClickHandler());
        }

        //add stages
        for (int i = 0 ; i < stages.size() ; i++) {

            QuestStage stage = stages.get(i);

            int lambdaI = i;

            if (stage.getReqStageID() > -1 && !QuestData.check(p, stages.get(stage.getReqStageID()).getFinalID())) {
                ItemStack item = stage.getItem().clone();
                item.setType(Material.BARRIER);

                menu.addItem(slotFromCounter(i), item, (player, slot, itemStack, clickAction) -> {
                    MessageUtils.messageWithCD(p, ChatColor.RED + "You must complete the stage: " + stageNames.get(stage.getReqStageID()) + " first!", 1000);
                    return false;
                });
                continue;
            }

            ItemStack item = stage.getItem().clone();
            List<String> lore = new ArrayList<>();

            //check if they finished the stage
            if (stage.getFinalID() > -1 && QuestData.check(p, stage.getFinalID())) {
                lore.add(ChatColor.GREEN + "Complete");
                StackUtils.enchant(item);
            } else {
                lore.add(ChatColor.RED + "Incomplete");
            }

            StackUtils.insertLoreAndRename(item, lore, null);

            menu.addItem(slotFromCounter(i), item, (player, slot, itemStack, clickAction) -> {
                new QuestMenu(p, lambdaI, 0, false);
                return false;
            });
        }

        //empty stage spots
        for (int i = stages.size() ; i < 14 ; i++) {
            menu.addItem(slotFromCounter(i), QuestMenu.BACKGROUND);
        }

        p.playSound(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);

        menu.open(p);
    }

    private static int slotFromCounter(int i) {
        if (i < 7) {
            return i + 10;
        }

        if (i < 14) {
            return i + 12;
        }

        return 53;
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent e) {
        history.remove(e.getPlayer());
    }
}
