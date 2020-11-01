package io.github.mooy1.slimequest.implementation;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.Getter;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class QuestPage {
    @Getter
    private final ItemStack item;
    @Getter
    private final int reqID;
    private final Map<Material, Integer[]> background = new HashMap<>();
    private final List<Quest> quests = new ArrayList<>();

    protected QuestPage(@Nonnull ItemStack item, int reqID) {
        this.item = item;
        this.reqID = reqID;
        makeBackground(background);
        addQuests(quests);
    }

    public void makeMenu(ChestMenu menu, Player p) {
        for (Material material : this.background.keySet()) {
            for (Integer i : this.background.get(material)) {
                menu.addItem(i, new ItemStack(material), ChestMenuUtils.getEmptyClickHandler());
            }
        }
        for (Quest quest : this.quests) {
            quest.addQuestStack(menu, p);
        }
    }

    public abstract void addQuests(List<Quest> quests);

    public abstract void makeBackground(Map<Material, Integer[]> map);
}
