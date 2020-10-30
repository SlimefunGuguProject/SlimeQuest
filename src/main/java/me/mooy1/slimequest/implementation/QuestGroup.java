package me.mooy1.slimequest.implementation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.slimequest.implementation.questpages.VanillaStart;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class QuestGroup {

    private final List<QuestPage> pages = new ArrayList<>();
    private final Type type;

    public QuestGroup(Type type) {
        this.type = type;
    }

    @Nonnull
    public QuestPage[] getPages() {
        return type.getPages();
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {
        VANILLA(new QuestPage[] {new VanillaStart()}, null);

        @Nonnull
        private final QuestPage[] pages;
        @Nullable
        private final String req;
    }
}
