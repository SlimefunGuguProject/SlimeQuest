package io.github.mooy1.slimequest.implementation;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * This object represents a new quest menu being opened for a player
 *
 * @author Mooy1
 *
 */
public class QuestMenu {

    private static final int PREV = 0;
    private static final int NEXT = 8;
    public static final ItemStack BACKGROUND = new CustomItem(Material.GRAY_STAINED_GLASS_PANE, " ");
    private static final List<QuestStage> stages = QuestRegistry.stages;

    private final Player p;
    private final int stageID;
    private final int pageID;
    private final QuestPage page;
    private final List<QuestPage> pages;

    public QuestMenu(Player p, int stageID, int pageID, boolean silent) {
        this.p = p;
        this.stageID = stageID;
        this.pageID = pageID;
        this.pages = stages.get(stageID).getPages();
        this.page = this.pages.get(pageID);

        QuestRegistry.history.put(p, new Pair<>(stageID, pageID));

        if (!silent) {
            p.playSound(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
        }

        open();
    }

    public void open() {
        ChestMenu menu = new ChestMenu("&8" + stages.get(stageID).getName());
        menu.setEmptySlotsClickable(false);

        //premake background
        for (int i = 0 ; i < 54 ; i++) {
            menu.addItem(i, BACKGROUND, ChestMenuUtils.getEmptyClickHandler());
        }

        //next
        Pair<ItemStack, ChestMenu.MenuClickHandler> next = makeNextButton(stageID, pageID, pages, p);
        menu.addItem(NEXT, next.getFirstValue(), next.getSecondValue());

        //previous
        Pair<ItemStack, ChestMenu.MenuClickHandler> prev = makePrevButton(stageID, pageID, pages, p);
        menu.addItem(PREV, prev.getFirstValue(), prev.getSecondValue());

        //add quests
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

        return new Pair<>(BACKGROUND, ChestMenuUtils.getEmptyClickHandler());
    }

    private Pair<ItemStack, ChestMenu.MenuClickHandler> makePrevButton(int stageID, int pageID, List<QuestPage> pages, Player p) {
        if (pageID > 0) {

            return new Pair<>(ChestMenuUtils.getPreviousButton(p, pageID + 1, pages.size()), (player, i, itemStack, clickAction) -> {
                new QuestMenu(p, stageID, pageID - 1, false);
                return false;
            });
        }

        return new Pair<>(BACKGROUND, ChestMenuUtils.getEmptyClickHandler());
    }
}
