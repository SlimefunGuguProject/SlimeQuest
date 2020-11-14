package io.github.mooy1.slimequest.implementation;

import io.github.mooy1.slimequest.implementation.data.QuestData;
import lombok.Getter;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

/**
 * This object represents a page of quests which includes multiple quests and builds the menu
 *
 * @author Mooy1
 *
 */
public class QuestPage {
    public List<Quest> quests = new ArrayList<>();
    @Getter
    private final String name;
    private final int[] previousPageDisplaySlots;

    /**
     * This method creates a new quest page which is part of a quest stage
     *
     * @param name name of page to be displayed at top
     * @param previousPageDisplaySlots slots in menu that show the status of the required quest for the first quest in this page
     */
    public QuestPage(String name, int[] previousPageDisplaySlots) {
        this.name = name;
        this.previousPageDisplaySlots = previousPageDisplaySlots;
    }

    public QuestPage add(Quest quest) {
        this.quests.add(quest);
        return this;
    }


    public final void makeMenu(ChestMenu menu, Player p, int stageID, int pageID) {
        for (Quest quest : this.quests) {
            quest.addQuestStacks(menu, p, stageID, pageID);
        }

        //add previous page red/green status item

        if (pageID < 1) return;

        int[] ids = quests.get(0).getReqIDs();

        if (ids.length == 0) return;

        int id = ids[0];

        if (id < 0) return;

        int[] slots = this.previousPageDisplaySlots;

        if (slots == null || slots.length == 0) return;

        ItemStack display;

        if (QuestData.check(p, id)) {
            display = Quest.GREEN;
        } else {
            display = Quest.RED;
        }

        for (int slot : slots) {
            menu.addItem(slot, display);
        }
    }
}