package io.github.mooy1.slimequest.implementation;

import io.github.mooy1.slimequest.implementation.data.PlayerData;
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
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class QuestMenu {

    private static final int PREV = 0;
    private static final int NEXT = 8;
    private static final ItemStack BACKGROUND = new CustomItem(Material.GRAY_STAINED_GLASS_PANE, " ");
    private static final ItemStack NONE = new CustomItem(Material.BLACK_STAINED_GLASS_PANE, " ");

    public static final List<QuestStage> stages = new ArrayList<>();

    private final Player p;
    private final int stageID;
    private final int pageID;
    private final QuestPage page;
    private final List<QuestPage> pages;

    public QuestMenu(Player p, int stageID, int pageID, boolean silent) {
        this.p = p;
        this.stageID = stageID;
        this.pageID = pageID;
        this.pages = stages.get(stageID).getRegisteredPages();
        this.page = this.pages.get(pageID);

        QuestRegistry.history.put(p, new Pair<>(stageID, pageID));

        if (!silent) {
            p.playSound(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
        }

        open();
    }

    public void open() {
        ChestMenu menu = new ChestMenu("&8Stage " + (stageID + 1) + ": " + page.getName());
        menu.setEmptySlotsClickable(false);

        //premake background
        for (int i = 0 ; i < 54 ; i++) {
            menu.addItem(i, BACKGROUND, ChestMenuUtils.getEmptyClickHandler());
        }

        //each stage
        for (int i = 0 ; i < stages.size() ; i++) {
            if (i == stageID) {
                ItemStack item = stages.get(i).getItem().clone();
                StackUtils.enchant(item);
                menu.addItem(i + 1, item, ChestMenuUtils.getEmptyClickHandler());
                continue;
            }

            if (!PlayerData.get().check(p, stages.get(i).getRequiredID())) {
                ItemStack item = stages.get(i).getItem().clone();
                item.setType(Material.BARRIER);
                menu.addItem(i + 1, item, (player, slot, itemStack, clickAction) -> {
                    MessageUtils.messageWithCD(p, ChatColor.RED + "You must complete the quest: " + Quest.nameFromID(stages.get(slot - 1).getRequiredID()) + " first!", 1000);
                    return false;
                });
                continue;
            }

            menu.addItem(i + 1, stages.get(i).getItem(), (player, slot, itemStack, clickAction) -> {
                new QuestMenu(p, slot - 1, 0, false);
                return false;
            });
        }

        //stage slots that aren't available
        for (int i = stages.size() ; i < 7 ; i++) {
            menu.addItem(i + 1, NONE, ChestMenuUtils.getEmptyClickHandler());
        }

        //next
        Pair<ItemStack, ChestMenu.MenuClickHandler> next = makeNextButton(stageID, pageID, pages, p);
        menu.addItem(NEXT, next.getFirstValue(), next.getSecondValue());

        //previous
        Pair<ItemStack, ChestMenu.MenuClickHandler> prev = makePrevButton(stageID, pageID, pages, p);
        menu.addItem(PREV, prev.getFirstValue(), prev.getSecondValue());

        page.makeMenu(menu, p, stageID, pageID);

        menu.open(p);
    }

    private Pair<ItemStack, ChestMenu.MenuClickHandler> makeNextButton(int stageID, int pageID, List<QuestPage> pages, Player p) {
        if (pageID < pages.size() - 1) {

            return new Pair<>(ChestMenuUtils.getNextButton(p, pageID + 1, pages.size()), (player, i, itemStack, clickAction) -> {
                new QuestMenu(p, stageID, pageID + 1, false);
                return false;
            });
        }

        if (stageID < stages.size() - 1) {

            QuestStage target = stages.get(stageID + 1);

            if (!PlayerData.get().check(p, target.getRequiredID())) { //check if viewable

                return new Pair<>(new CustomItem(Material.BARRIER, ChatColor.RED + "Next Stage: " + target.getName()), (player, i, itemStack, clickAction) -> {
                    MessageUtils.messageWithCD(p, ChatColor.RED + "You must complete the quest: " + Quest.nameFromID(target.getRequiredID()) + " first!", 1000);
                    p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1, 1);
                    return false;
                });
            }

            return new Pair<>(new CustomItem(Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "Next Stage: " + stages.get(stageID + 1).getName()), (player, i, itemStack, clickAction) -> {
                new QuestMenu(p, stageID + 1, 0, false);
                return false;
            });
        }

        return new Pair<>(new CustomItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Finish"), (player, i, itemStack, clickAction) -> false);
    }

    private Pair<ItemStack, ChestMenu.MenuClickHandler> makePrevButton(int stageID, int pageID, List<QuestPage> pages, Player p) {
        if (pageID > 0) {

            return new Pair<>(ChestMenuUtils.getPreviousButton(p, pageID + 1, pages.size()), (player, i, itemStack, clickAction) -> {
                new QuestMenu(p, stageID, pageID - 1, false);
                return false;
            });
        }

        if (stageID > 0) {

            return new Pair<>(new CustomItem(Material.ORANGE_STAINED_GLASS_PANE, ChatColor.GOLD + "Previous Stage: " + stages.get(stageID - 1).getName()), (player, i, itemStack, clickAction) -> {
                new QuestMenu(p, stageID - 1, stages.get(stageID -1).getRegisteredPages().size() - 1, false);
                return false;
            });
        }

        return new Pair<>(new CustomItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Start"), (player, i, itemStack, clickAction) -> false);
    }
}
