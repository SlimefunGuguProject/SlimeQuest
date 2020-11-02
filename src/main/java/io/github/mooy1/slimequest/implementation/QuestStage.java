package io.github.mooy1.slimequest.implementation;

import io.github.mooy1.slimequest.implementation.stages.stage0.VanillaStart;
import io.github.mooy1.slimequest.implementation.stages.stage0.VanillaStartA;
import io.github.mooy1.slimequest.utils.StackUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class QuestStage {

    private final Type type;
    @Getter
    private final ItemStack item;
    private final List<QuestPage> pages = new ArrayList<>();

    public QuestStage(Type type, int id) {
        this.type = type;
        ItemStack item = type.getItem().clone();
        List<String> lores = new ArrayList<>();
        StackUtils.insertLoreAndRename(item, lores, ChatColor.GOLD + "Stage " + (id + 1) + ": " + type.getName());
        this.item = item;
    }

    @Nonnull
    public List<QuestPage> getRegisteredPages() {
        return pages;
    }

    public void registerPage(QuestPage page) {
        pages.add(page);
    }

    @Nonnull
    public String getName() {
        return type.getName();
    }

    public int getRequiredID() {
        return type.getReqID();
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {
        A("Basic",
                new QuestPage[] {new VanillaStart()},
                new String[] {"Vanilla"}, 0, new ItemStack(Material.GRASS_BLOCK)),
        B("Stone",
                new QuestPage[] {new VanillaStart(), new VanillaStartA()},
                new String[] {"Vanilla", "Vanilla"}, 0, new ItemStack(Material.STONE)),
        C("Bronze",
                new QuestPage[] {new VanillaStart()},
                new String[] {"Vanilla"}, 0, new ItemStack(Material.IRON_INGOT)),
        D("Medieval",
                  new QuestPage[] {new VanillaStart()},
                new String[] {"Vanilla"}, 0, new ItemStack(Material.NETHERITE_INGOT)),
        E("Industrial",
                  new QuestPage[] {new VanillaStart()},
                new String[] {"Vanilla"}, 0, new ItemStack(Material.PISTON)),
        F("Futuristic",
                  new QuestPage[] {new VanillaStart()},
                new String[] {"Vanilla"}, 0, new ItemStack(Material.IRON_BLOCK)),
        G("Infinity",
                  new QuestPage[] {new VanillaStart()},
                new String[] {"Vanilla"}, 4, new ItemStack(Material.NETHER_STAR));

        @Nonnull
        private final String name;
        private final QuestPage[] pages;
        private final String[] req;
        private final int reqID;
        private final ItemStack item;
    }
}
