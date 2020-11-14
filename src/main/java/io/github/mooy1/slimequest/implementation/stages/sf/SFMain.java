package io.github.mooy1.slimequest.implementation.stages.sf;

import io.github.mooy1.slimequest.SlimeQuest;
import io.github.mooy1.slimequest.implementation.Quest;
import io.github.mooy1.slimequest.implementation.QuestPage;
import io.github.mooy1.slimequest.implementation.QuestStage;
import io.github.mooy1.slimequest.implementation.data.QuestData;
import io.github.thebusybiscuit.slimefun4.api.events.MultiBlockInteractEvent;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.multiblocks.Smeltery;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SFMain extends QuestStage implements Listener {
    public SFMain() {
        super("Slimefun", "Slimefun", 0, Material.SLIME_BLOCK);
    }

    @Override
    protected void addPages() {
        // NOTE TO RILEY: EDIT SLOTSAFFECTED
        add(new QuestPage("The Beginning", new int[0])
            .add(new Quest("Forty-Niner", 120, new int[0], "You begin having dreams of fortune...",
                37, new int[]{28}, new SlimefunItemStack[]{SlimefunItems.GOLD_PAN}, new int[]{1})
                .setReward(SlimefunItems.GOLD_DUST, 1))
            .add(new Quest("First Harvest", 121, new int[]{120}, "Is that... GOLD?",
                19, new int[]{20, 21, 22}, new SlimefunItemStack[]{SlimefunItems.SIFTED_ORE}, new int[]{1})
                .setReward(SlimefunItems.SIFTED_ORE, 4))
            .add(new Quest("Foundry", 122, new int[]{121}, "Time to make some alloys",
                23, new int[0], new String[0], new int[0])
                .setCustomReq("Use a Smeltery", "FLINT_AND_STEEL").setXP(3))
        );

        SlimeQuest.registerEvents(this);
    }

    @EventHandler
    public void onSmelteryUse(MultiBlockInteractEvent e) {
        if (e.getMultiBlock().getSlimefunItem() instanceof Smeltery) {
            Player p = e.getPlayer();
            if (!QuestData.check(p, 122) && QuestData.check(p, 121)) {
                Quest quest = Quest.quests.get(Quest.ids.indexOf(122));
                quest.giveUnlock(e.getPlayer(), true);
                quest.giveRewards(p, p.getInventory(), p.getInventory().getStorageContents());
            }
        }
    }
}
