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

public class SFBasic extends QuestStage implements Listener {
    public SFBasic() {
        super("Slimefun Basics", "Slimefun", -1, Material.SLIME_BALL);
    }

    @Override
    protected void addPages() {
        add(new QuestPage("The Beginning", new int[0])
            .add(new Quest("Forty-Niner", 120, new int[0], "You begin having dreams of fortune...",
                37, new int[]{28, 38, 39, 40}, new SlimefunItemStack[]{SlimefunItems.GOLD_PAN}, new int[]{1})
                .setReward(SlimefunItems.GOLD_DUST, 1))
            .add(new Quest("First Harvest", 121, new int[]{120}, "Is that... GOLD?",
                19, new int[]{20, 21, 22}, new SlimefunItemStack[]{SlimefunItems.SIFTED_ORE}, new int[]{1})
                .setReward(SlimefunItems.SIFTED_ORE, 4))
            .add(new Quest("Foundry", 122, new int[]{121}, "Time to make some alloys",
                23, new int[]{32}, new String[0], new int[0])
                .setCustomReq("Use a Smeltery", "FLINT_AND_STEEL").setXP(3))
            .add(new Quest("Magnetic", 123, new int[]{120, 122}, "You need to be a little more attractive",
                41, new int[]{42, 43, 44}, new SlimefunItemStack[]{SlimefunItems.MAGNET}, new int[]{1})
                .setReward(SlimefunItems.BATTERY, 1))
        );
        add(new QuestPage("Moving On", new int[]{30, 33, 36, 37, 38, 39, 40, 41, 42})
            .add(new Quest("Electric", 124, new int[]{122, 123}, "Shocking, right?",
                21, new int[0], new SlimefunItemStack[]{SlimefunItems.ELECTRIC_MOTOR}, new int[]{1})
                .setReward(SlimefunItems.DAMASCUS_STEEL_INGOT, 4))
            .add(new Quest("Magic", 125, new int[]{122}, "I bet you have no idea what this is",
                24, new int[0], new SlimefunItemStack[]{SlimefunItems.MAGIC_LUMP_1}, new int[]{16})
                .setReward(SlimefunItems.MAGIC_LUMP_1, 16))
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
