package io.github.mooy1.slimequest.implementation.stages;

import io.github.mooy1.slimequest.implementation.QuestStage;
import io.github.mooy1.slimequest.implementation.pages.basic.VanillaStart;
import org.bukkit.Material;

public class VanillaBasic extends QuestStage {
    public VanillaBasic() {
        super("Vanilla Basics", "Vanilla", -1, Material.GRASS_BLOCK);
    }

    @Override
    protected void addPages() {
        this.pages.add(new VanillaStart());
    }
}
