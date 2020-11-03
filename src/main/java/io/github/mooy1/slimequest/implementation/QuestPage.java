package io.github.mooy1.slimequest.implementation;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.entity.Player;

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

    public void makeMenu(ChestMenu menu, Player p, int stageID, int pageID) {
        for (Quest quest : this.quests) {
            quest.addQuestStacks(menu, p, stageID, pageID);
        }
    }
}