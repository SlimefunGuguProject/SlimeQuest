package io.github.mooy1.slimequest.implementation.stages.sf;

import io.github.mooy1.slimequest.implementation.Quest;
import io.github.mooy1.slimequest.implementation.QuestPage;
import io.github.mooy1.slimequest.implementation.QuestStage;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;

public class SFBasic extends QuestStage {
    public SFBasic() {
        super("Slimefun Basics", "Slimefun", -1, Material.STICK);
    }

    @Override
    protected void addPages() {
        add(new QuestPage("Basics", new int[0])
                .add(new Quest("Portable Stuff", 100, new int[]{}, "",
                        37, new int[]{28, 19}, new SlimefunItemStack[]{SlimefunItems.PORTABLE_CRAFTER, SlimefunItems.PORTABLE_DUSTBIN}, new int[]{1, 1}))
        );
        add(new QuestPage("Backpacks", new int[] {36})
                .add(new Quest("Backpack 1", 110, new int[]{}, "",
                        37, new int[]{28, 19}, new SlimefunItemStack[]{SlimefunItems.BACKPACK_SMALL}, new int[]{1}))
        );
    }
}
