package io.github.mooy1.slimequest.implementation.stages;

import io.github.mooy1.slimequest.implementation.Quest;
import io.github.mooy1.slimequest.implementation.QuestPage;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;

import java.util.List;
import java.util.Map;

public class InfinityStart extends QuestPage {
    public InfinityStart() {
        super(new CustomItem(Material.GRASS_BLOCK, "&aINFINITY Start"), -1);
    }

    @Override
    public void addQuests(List<Quest> quests) {
        quests.add(new Quest("wood", 0, "OAK_LOG", 9, "VOID_INGOT", 1, 666,
                new String[] {"OAK_LOG"}, new int[] {1}, -1, false, null));
    }

    @Override
    public void makeBackground(Map<Material, Integer[]> map) {
        map.put(Material.ORANGE_STAINED_GLASS_PANE, new Integer[] {15, 16, 17, 18, 19, 20});
    }
}
