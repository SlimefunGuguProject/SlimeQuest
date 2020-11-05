package io.github.mooy1.slimequest.implementation;

import io.github.mooy1.slimequest.utils.StackUtils;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * This object represents a single group or stage of pages and quests
 *
 * @author Mooy1
 *
 */
public abstract class QuestStage {

    @Getter
    private final ItemStack item;
    @Getter
    public final List<QuestPage> pages = new ArrayList<>();
    @Getter
    private final String name;
    @Getter
    private final String reqPlugin;
    @Getter
    private final int reqStageID;
    @Getter
    private final int stageID;
    @Getter
    private int finalID = -1;

    /**
     * This method creates a new quest stage which initializes multiple pages
     *
     * @param name unique name
     * @param reqPlugin required plugin name ex: "ExtraTools"
     * @param stageID unique id of this stage
     * @param reqStageID the id of the stage that must be completed before this can be started
     * where the required stage to open this stage is, it should be from slimefun, vanilla, or this stage's addon.
     * @param mat material of display item
     */
    public QuestStage(String name, int stageID, String reqPlugin, int reqStageID, Material mat) {
        this.name = name;
        this.stageID = stageID;
        this.reqPlugin = reqPlugin;
        this.reqStageID = reqStageID;

        List<String> lores = new ArrayList<>();
        ItemStack item = new ItemStack(mat);
        StackUtils.insertLoreAndRename(item, lores, ChatColor.GOLD + name);
        this.item = item;
    }

    protected void add(QuestPage page) {
        this.pages.add(page);
    }

    protected abstract void addPages();

    public void findFinalID() {
        QuestPage page = this.pages.get(this.pages.size() - 1);
        this.finalID = page.quests.get(page.quests.size() - 1).getId();
    }
}
