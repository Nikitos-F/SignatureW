package signatureW.blocks.crafting;

import arc.*;
import arc.math.*;
import arc.util.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.ui.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.production.HeatCrafter;


public class OptionalHeatCrafter extends HeatCrafter {

    public OptionalHeatCrafter(String name) {
        super(name);
    }

    public class OptionalHeatCrafterBuild extends HeatCrafterBuild {

        @Override
        public float efficiencyScale(){
            float over = Math.max(heat - heatRequirement, 0f);
            return Math.min(Mathf.clamp((heat+heatRequirement*0.1f) / heatRequirement) + over / heatRequirement * overheatScale, maxEfficiency);
        }

    }
}