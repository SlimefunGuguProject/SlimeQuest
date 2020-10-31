package io.github.mooy1.slimequest.implementation;

import io.github.mooy1.slimequest.implementation.questpages.InfinityStart;
import io.github.mooy1.slimequest.implementation.questpages.VanillaAdvanced;
import io.github.mooy1.slimequest.implementation.questpages.VanillaStart;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class QuestGroup {

    private final Type type;
    private final List<QuestPage> pages = new ArrayList<>();

    public QuestGroup(Type type) {
        this.type = type;
    }

    @Nonnull
    public List<QuestPage> getRegisteredPages() {
        return pages;
    }

    public void registerPage(QuestPage page) {
        pages.add(page);
    }

    @Nonnull
    public String getName() {
        return type.getName();
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {
        A("Basic",
                new QuestPage[] {new VanillaStart(), new VanillaAdvanced()},
                new String[] {"Vanilla", "Vanilla"}),
        B("Beginnings",
                new QuestPage[] {new InfinityStart() },
                new String[] {"InfinityExpansion"}),
        C("Advanced",
                  new QuestPage[] { },
                new String[] { }),
        D("Infinity",
                  new QuestPage[] {new InfinityStart()  },
                new String[] {"InfinityExpansion" });

        @Nonnull
        private final String name;
        private final QuestPage[] pages;
        private final String[] req;
    }
}
