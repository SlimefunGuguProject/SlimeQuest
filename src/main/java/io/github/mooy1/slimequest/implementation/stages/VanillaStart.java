package io.github.mooy1.slimequest.implementation.stages;

import io.github.mooy1.slimequest.implementation.Quest;
import io.github.mooy1.slimequest.implementation.QuestPage;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;

import java.util.List;
import java.util.Map;

public class VanillaStart extends QuestPage {
    public VanillaStart() {
        super(new CustomItem(Material.GRASS_BLOCK, "&aVanilla Start"), -1);
    }

    @Override
    public void addQuests(List<Quest> quests) {

    }

    @Override
    public void makeBackground(Map<Material, Integer[]> map) {

    }
}
