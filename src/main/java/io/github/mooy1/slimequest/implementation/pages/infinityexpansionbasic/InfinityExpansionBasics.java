package io.github.mooy1.slimequest.implementation.pages.infinityexpansionbasic;

import io.github.mooy1.slimequest.implementation.Quest;
import io.github.mooy1.slimequest.implementation.QuestPage;

public class InfinityExpansionBasics extends QuestPage {
    public InfinityExpansionBasics() {
        super();
        quests.add(new Quest("Strainer", 500, new int[] {}, "Hint: put it in water", "STRAINER_BASE", 19,
                new int[] {28, 37, 38}, new String[]{}, new int[]{}).setReward("BASIC_STRAINER", 1)
        );
    }
}
