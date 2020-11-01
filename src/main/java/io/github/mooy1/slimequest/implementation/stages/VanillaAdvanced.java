package io.github.mooy1.slimequest.implementation.stages;

import io.github.mooy1.slimequest.implementation.Quest;
import io.github.mooy1.slimequest.implementation.QuestPage;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;

import java.util.List;
import java.util.Map;

public class VanillaAdvanced extends QuestPage {
    public VanillaAdvanced() {
        super(new CustomItem(Material.DIRT, "&aVanilla Advanced"), 0);
    }

    @Override
    public void addQuests(List<Quest> quests) {
        quests.add(new Quest("aaaaaaa", 1, "INFINITE_INGOT", 15, null, 0, 0,
                new String[] {"COBBLESTONE", "DIRT"}, new int[] {1, 65}, 0, true, null));
    }

    @Override
    public void makeBackground(Map<Material, Integer[]> map) {

    }
}
