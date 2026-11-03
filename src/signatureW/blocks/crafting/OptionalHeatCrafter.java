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

    public float baseEfficicency = 0.2f;

    public OptionalHeatCrafter(String name) {
        super(name);
    }

    public class OptionalHeatCrafterBuild extends HeatCrafterBuild {

        private float RealNaxEfficiency = maxEfficiency - baseEfficicency;
        private float MinHeat = baseEfficicency * heatRequirement;



        @Override
        public void updateTile(){
            heat = calculateHeat(sideHeat) + MinHeat;

            if(efficiency > 0){
                progress += getProgressIncrease(craftTime);
                warmup = Mathf.approachDelta(warmup, warmupTarget(), warmupSpeed);
                //continuously output based on efficiency
                if(outputLiquids != null){
                    float inc = getProgressIncrease(1f);
                    for(var output : outputLiquids){
                        handleLiquid(this, output.liquid, Math.min(output.amount * inc, liquidCapacity - liquids.get(output.liquid)));
                    }
                }
                if(wasVisible && Mathf.chanceDelta(updateEffectChance)){
                    updateEffect.at(x + Mathf.range(size * updateEffectSpread), y + Mathf.range(size * updateEffectSpread));
                }
            }
            else{
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
            }

            totalProgress += warmup * Time.delta;
            if(progress >= 1f){
                craft();
            }

            dumpOutputs();
        }

        @Override
        public float efficiencyScale(){
            float over = Math.max(heat - heatRequirement, 0f);
            return Math.min(Mathf.clamp(heat / heatRequirement) + over / heatRequirement * overheatScale, maxEfficiency);
        }

    }
}