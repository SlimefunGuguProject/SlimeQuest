package io.github.mooy1.slimequest.implementation.stages.basic;

import io.github.mooy1.slimequest.implementation.Quest;
import io.github.mooy1.slimequest.implementation.QuestPage;

public class VanillaStart extends QuestPage {
    public VanillaStart() {
        super();
        quests.add(new Quest("Wood", 0, "Punch trees...", 19, "OAK_LOG",
                new int[] {28, 37, 38}, new int[] {}, new String[]{"OAK_LOG"}, new int[]{8}).setConsume()
        );
        quests.add(new Quest("Mining", 1, "It begins", 39, "STONE_PICKAXE",
                new int[] {40, 41, 30, 21}, new int[]{0}, new String[]{"STONE_PICKAXE"}, new int[]{1})
        );
        quests.add(new Quest("Coal", 2, "You're gonna need a lot of this", 12, "COAL",
                new int[] {13, 14}, new int[]{1}, new String[]{"COAL"}, new int[]{16}).setXP(333)
        );
        quests.add(new Quest("Furnace", 3, "Tier 0 furnace", 42, "FURNACE",
                new int[] {33, 24}, new int[]{1}, new String[]{"FURNACE"}, new int[]{1}).setConsume().setXP(777).setCustomReq("EEEE").setReward("VOID_INGOT", 1)
        );
        quests.add(new Quest("Iron", 4, "Your first ingot", 15, "IRON_INGOT",
                new int[] {}, new int[]{2, 3}, new String[]{"IRON_INGOT"}, new int[]{1}).setReward("DIRT", 64)
        );
    }
}
