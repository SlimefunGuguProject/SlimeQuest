package io.github.mooy1.slimequest.implementation;

import io.github.mooy1.slimequest.implementation.data.PlayerData;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

public abstract class QuestPage {
    public final List<Quest> quests = new ArrayList<>();

    /**
     * This object represents a page of quests which includes multiple quests and builds the menu
     *
     * @author Mooy1
     *
     */
    protected QuestPage() {}

    @ParametersAreNonnullByDefault
    public void makeMenu(ChestMenu menu, Player p, int stageID, int pageID) {
        for (Quest quest : this.quests) {
            quest.addQuestStacks(menu, p, stageID, pageID);
        }

        //add previous quest red/green status item
        int[] reqIDs = this.quests.get(this.quests.size() -1).getReqIDs(); //change to prev stage

        if (reqIDs.length > 0) {
            int slot = this.quests.get(0).getSlot() - 1;

            if (PlayerData.check(p, reqIDs[0])) {
                menu.addItem(slot, Quest.GREEN);
            } else {
                menu.addItem(slot, Quest.RED);
            }
        }
    }
}