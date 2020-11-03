package io.github.mooy1.slimequest.implementation;

import io.github.mooy1.slimequest.implementation.stages.basic.VanillaStart;
import io.github.mooy1.slimequest.implementation.stages.infinityexpansionbasic.InfinityExpansionBasics;
import io.github.mooy1.slimequest.utils.StackUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This object represents a single group or stage of pages and quests
 *
 * @author Mooy1
 *
 */
public class QuestStage {

    private final Type type;
    @Getter
    private final ItemStack item;
    @Getter
    private final List<QuestPage> pages;

    public QuestStage(Type type) {
        this.type = type;
        ItemStack item = type.getItem().clone();
        List<String> lores = new ArrayList<>();
        StackUtils.insertLoreAndRename(item, lores, ChatColor.GOLD + type.getName());
        this.item = item;
        this.pages = Arrays.asList(type.getPages());
    }

    @Nonnull
    public String getName() {
        return type.getName();
    }

    public int getRequiredID() {
        return type.getReqID();
    }

    public int getSpot() {
        return type.getSpot();
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {
        V("Vanilla Basics",
                new QuestPage[] {new VanillaStart()},
                -1, 0, 0, new ItemStack(Material.GRASS_BLOCK), "Vanilla"),
        SFB("Slimefun Basics",
                new QuestPage[] {},
                -1, 0, 1, new ItemStack(Material.GRASS_BLOCK), "Slimefun"),
        SF("Slimefun",
                new QuestPage[] {},
                -1, 0, 2, new ItemStack(Material.GRASS_BLOCK), "Slimefun"),
        B("Slimefun Advanced",
                new QuestPage[] {},
                -1, 0, 3, new ItemStack(Material.GRASS_BLOCK), "Slimefun"),
        C("SFCalc",
                new QuestPage[] {},
                -1, 0, 4, new ItemStack(Material.GRASS_BLOCK), "SFCalc"),
        IB("Infinity Basics",
                  new QuestPage[] {new InfinityExpansionBasics()},
                -1, 0, 5, new ItemStack(Material.GRASS_BLOCK), "InfinityExpansion"),
        FM("FluffyMachines",
                  new QuestPage[] {},
                -1, 0, 6, new ItemStack(Material.GRASS_BLOCK), "FluffyMachines"),
        LX("LiteExpansion",
                  new QuestPage[] {},
                -1, 0, 7, new ItemStack(Material.GRASS_BLOCK), "LiteExpansion"),
        IE("InfinityExpansion",
                  new QuestPage[] {},
                -1, 0, 8, new ItemStack(Material.GRASS_BLOCK), "InfinityExpansion");

        @Nonnull
        private final String name;
        private final QuestPage[] pages;
        private final int reqID;
        private final int id;
        private final int spot;
        private final ItemStack item;
        private final String reqAddon;
    }
}
