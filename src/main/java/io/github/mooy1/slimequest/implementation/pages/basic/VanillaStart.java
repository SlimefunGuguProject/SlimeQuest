package io.github.mooy1.slimequest.implementation.pages.basic;

import io.github.mooy1.slimequest.implementation.Quest;
import io.github.mooy1.slimequest.implementation.QuestPage;

public class VanillaStart extends QuestPage {
    public VanillaStart() {
        super();
        quests.add(new Quest("Wood", 0, new int[] {}, "Punch trees...", "OAK_LOG",
                10, new int[] {11, 12}, new String[]{"OAK_LOG"}, new int[]{8}).setConsume()
        );
        quests.add(new Quest("Mining", 1, new int[]{0}, "It begins", "STONE_PICKAXE",
                13, new int[] {14, 15, 22, 31}, new String[]{"STONE_PICKAXE"}, new int[]{1})
        );
        quests.add(new Quest("Coal", 2, new int[]{1}, "You're gonna need a lot of this", "COAL",
                16, new int[] {25, 34}, new String[]{"COAL"}, new int[]{16}).setXP(333)
        );
        quests.add(new Quest("Furnace", 3, new int[]{1}, "Tier 0 furnace", "FURNACE",
                40, new int[] {41, 42}, new String[]{"FURNACE"}, new int[]{1}).setConsume().setXP(777).setCustomReq("EEEE").setReward("VOID_INGOT", 1)
        );
        quests.add(new Quest("Iron", 4, new int[]{2, 3}, "Your first ingot", "IRON_INGOT",
                43, new int[] {44}, new String[]{"IRON_INGOT"}, new int[]{1}).setReward("DIRT", 64)
        );
    }
}
