package signatureW.blocks.production;


import mindustry.world.blocks.production.BurstDrill;

import arc.*;
import arc.math.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.ui.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.meta.*;

import arc.*;
import arc.math.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.ui.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.meta.*;


public class HeatDrill extends BurstDrill {

    public float heatRequirement = 10f;
    public float overheatScale = 1f;
    public float maxEfficiency = 2f;

    public HeatDrill(String name) {
        super(name);
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("heat", (HeatDrillBuild entity) ->
                new Bar(() ->
                        Core.bundle.format("bar.heatpercent", (int)(entity.heat + 0.01f), (int)(entity.efficiencyScale() * 100 + 0.01f)),
                        () -> Pal.lightOrange,
                        () -> entity.heat / heatRequirement));
    }

    //  TODO Show heat requirements


    public class HeatDrillBuild extends BurstDrillBuild implements HeatConsumer{
        public float[] sideHeat = new float[4];
        public float heat = 0f;

        @Override
        public void updateTile(){
            heat = calculateHeat(sideHeat);

            super.updateTile();
        }

        @Override
        public float efficiencyScale(){
            float over = Math.max(heat - heatRequirement, 0f);
            return Math.min(Mathf.clamp((heat+heatRequirement*0.1f) / heatRequirement) + over / heatRequirement * overheatScale, maxEfficiency);
        }


        @Override
        public float heatRequirement(){
            return heatRequirement;
        }

        @Override
        public float[] sideHeat(){
            return sideHeat;
        }
    }

}