package io.github.mooy1.slimequest.implementation.stages.stage2;

import io.github.mooy1.slimequest.implementation.Quest;
import io.github.mooy1.slimequest.implementation.QuestPage;

public class InfinityExpansionBasics extends QuestPage {
    public InfinityExpansionBasics() {
        super("IE Basics", "InfinityExpansion");

    }

    @Override
    public void registerQuests() {
        quests.add(new Quest("Strainer", 0, "Hint: put it in water", 19, "STRAINER_BASE",
                new int[] {28, 37, 38}, new int[] {}, new String[]{}, new int[]{}).setReward("BASIC_STRAINER", 1)
        );
    }
}
