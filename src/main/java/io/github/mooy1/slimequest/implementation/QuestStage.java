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
    private final String req;
    @Getter
    private final int reqID;

    public QuestStage(String name, String req, int reqID, Material mat) {
        this.name = name;
        this.req = req;
        this.reqID = reqID;

        List<String> lores = new ArrayList<>();
        ItemStack item = new ItemStack(mat);
        StackUtils.insertLoreAndRename(item, lores, ChatColor.GOLD + name);
        this.item = item;

        //add 'final id' to use for showing when complete
    }

    protected abstract void addPages();
}
