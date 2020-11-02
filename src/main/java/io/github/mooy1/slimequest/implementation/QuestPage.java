package io.github.mooy1.slimequest.implementation;

import lombok.Getter;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class QuestPage {
    public final List<Quest> quests = new ArrayList<>();
    @Getter
    private final String name;
    @Getter
    private final String req;

    /**
     * This object represents a page of quests which includes multiple quests and builds the menu
     *
     */
    protected QuestPage(String name, String req) {
        this.name = name;
        this.req = req;
    }

    public void makeMenu(ChestMenu menu, Player p, int stageID, int pageID) {
        for (Quest quest : this.quests) {
            quest.addQuestStacks(menu, p, stageID, pageID);
        }
    }

    public abstract void registerQuests();
}