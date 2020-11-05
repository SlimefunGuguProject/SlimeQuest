package io.github.mooy1.slimequest.implementation;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
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

    private static final int BACK = 0;
    private static final int PREV = 45;
    private static final int NEXT = 53;
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
        ChestMenu menu = new ChestMenu("&8" + stages.get(this.stageID).getName() + " - " + page.getName());
        menu.setEmptySlotsClickable(false);

        //premake background
        for (int i = 0 ; i < 54 ; i++) {
            menu.addItem(i, BACKGROUND, ChestMenuUtils.getEmptyClickHandler());
        }

        menu.addItem(BACK, ChestMenuUtils.getBackButton(p, ChatColor.GOLD + "To all stages"), (player, i, itemStack, clickAction) -> {
            QuestRegistry.openMain(player);
            return false;
        });

        //next
        Pair<ItemStack, ChestMenu.MenuClickHandler> next = makeNextButton();
        menu.addItem(NEXT, next.getFirstValue(), next.getSecondValue());

        //previous
        Pair<ItemStack, ChestMenu.MenuClickHandler> prev = makePrevButton();
        menu.addItem(PREV, prev.getFirstValue(), prev.getSecondValue());

        //add quests
        this.page.makeMenu(menu, this.p, this.stageID, this.pageID);

        menu.open(p);
    }

    private Pair<ItemStack, ChestMenu.MenuClickHandler> makeNextButton() {
        if (this.pageID < this.pages.size() - 1) {

            return new Pair<>(ChestMenuUtils.getNextButton(this.p, this.pageID + 1, this.pages.size()), (player, i, itemStack, clickAction) -> {
                new QuestMenu(player, this.stageID, this.pageID + 1, false);
                return false;
            });
        }

        return new Pair<>(BACKGROUND, ChestMenuUtils.getEmptyClickHandler());
    }

    private Pair<ItemStack, ChestMenu.MenuClickHandler> makePrevButton() {
        if (pageID > 0) {

            return new Pair<>(ChestMenuUtils.getPreviousButton(this.p, this.pageID + 1, this.pages.size()), (player, i, itemStack, clickAction) -> {
                new QuestMenu(player, this.stageID, this.pageID - 1, false);
                return false;
            });
        }

        return new Pair<>(BACKGROUND, ChestMenuUtils.getEmptyClickHandler());
    }
}
