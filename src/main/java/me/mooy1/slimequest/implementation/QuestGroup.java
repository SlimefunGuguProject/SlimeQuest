package me.mooy1.slimequest.implementation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.slimequest.implementation.questpages.InfinityStart;
import me.mooy1.slimequest.implementation.questpages.VanillaStart;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class QuestGroup {

    private final Type type;

    public QuestGroup(Type type) {
        this.type = type;
    }

    @Nonnull
    public QuestPage[] getPages() {
        return type.getPages();
    }

    @Nonnull
    public String getName() {
        return type.getName();
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {
        VANILLA(new QuestPage[] {new VanillaStart() }, "Vanilla", null),
        INFINITY(new QuestPage[] {new InfinityStart() }, "InfinityExpansion", "InfinityExpansion");

        @Nonnull
        private final QuestPage[] pages;
        private final String name;
        @Nullable
        private final String req;
    }
}
