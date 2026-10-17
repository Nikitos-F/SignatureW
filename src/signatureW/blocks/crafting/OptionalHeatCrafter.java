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
        public BlockStatus status(){
            if (this.heat == 0) {
                this.heat = 2f;
            }

            if(heatRequirement > 0 && (heat+1f) <= 0f && enabled) return BlockStatus.noInput;
            return super.status();
        }
    }
}