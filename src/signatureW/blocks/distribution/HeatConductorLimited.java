package signatureW.blocks.distribution;


import mindustry.world.blocks.heat.HeatConductor;

import arc.*;
import arc.graphics.g2d.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.draw.*;

import arc.graphics.*;
import arc.math.*;



public class HeatConductorLimited extends HeatConductor {

    public float maxHeat = 1000f;
    public float explodeThreshold = 2.5f;

    private float thresholdHealth;


    public HeatConductorLimited(String name) {
        super(name);
    }


    @Override
    public void setBars() {
        addBar("health", (HeatConductorLimitedBuild entity) ->
                new Bar("stat.health", Pal.health, () -> entity.health / entity.maxHealth)
                        .blink(Color.white)
        );
        addBar("heat", (HeatConductorBuild entity) -> new Bar(() -> Core.bundle.format("bar.heatamount", (int)(entity.heat + 0.001f)), () -> Pal.lightOrange, () -> entity.heat / visualMaxHeat));
    }

    public class HeatConductorLimitedBuild extends HeatConductorBuild {

        @Override
        public void updateTile(){
            updateHeat();
        }

        @Override
        public void updateHeat(){
            if(lastHeatUpdate == Vars.state.updateId) return;

            lastHeatUpdate = Vars.state.updateId;
            heat = enabled ? calculateHeat(sideHeat, cameFrom) : 0f;

//            absHealth = maxHealth;
//            maxHealth *= (1 - heat/(explodeThreshold*maxHeat));
        }
    }
}






















