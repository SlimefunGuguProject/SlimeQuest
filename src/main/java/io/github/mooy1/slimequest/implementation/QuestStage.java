package io.github.mooy1.slimequest.implementation;

import io.github.mooy1.slimequest.implementation.stages.InfinityStart;
import io.github.mooy1.slimequest.implementation.stages.VanillaAdvanced;
import io.github.mooy1.slimequest.implementation.stages.VanillaStart;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class QuestStage {

    private final Type type;
    private final List<QuestPage> pages = new ArrayList<>();

    public QuestStage(Type type) {
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

    public int getRequiredID() {
        return this.pages.get(0).getReqID();
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {
        A("Basic",
                new QuestPage[] {new VanillaStart(), new VanillaAdvanced()},
                new String[] {"Vanilla", "Vanilla"}),
        B("Stone",
                new QuestPage[] {new InfinityStart() },
                new String[] {"InfinityExpansion"  }),
        C("Bronze",
                new QuestPage[] { },
                new String[] { }),
        D("Medieval",
                  new QuestPage[] { },
                new String[] { }),
        E("Industrial",
                  new QuestPage[] { },
                new String[] { }),
        F("Futuristic",
                  new QuestPage[] { },
                new String[] { }),
        G("Infinity",
                  new QuestPage[] {new InfinityStart() },
                new String[] {"InfinityExpansion" });

        @Nonnull
        private final String name;
        private final QuestPage[] pages;
        private final String[] req;
    }
}
