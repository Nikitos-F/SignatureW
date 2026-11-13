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

    public Attribute attribute = Attribute.heat;
    public float maxBoost = 5f;
    public boolean displayEfficiency = true, displayScaledOutput = true;
    public float outputScale = 0f;
    public float boostScale = 0.12f;

    public OptionalHeatCrafter(String name) {
        super(name);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        if((!displayEfficiency || boostScale <= 0f) && (!displayScaledOutput || outputScale <= 0f)) return;

        drawPlaceText(
                (displayEfficiency && boostScale > 0f ?
                        Core.bundle.format("bar.efficiency", (int)((1f + Math.min(maxBoost, boostScale * sumAttribute(attribute, x, y))) * 100f))
                        : "") +
                        (displayScaledOutput && outputScale > 0f ?
                                "\n" + Core.bundle.format("bar.yield", (int)((1f + Math.min(maxBoost, outputScale * sumAttribute(attribute, x, y))) * 100f))
                                : ""), x, y, valid);
    }



    public class OptionalHeatCrafterBuild extends HeatCrafterBuild {
        public float attrsum;

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
            float heatFraction = Mathf.clamp(heat / heatRequirement);
            float over = Math.max(heat - heatRequirement, 0f);
            float heatBonus = Math.min(heatFraction + over / heatRequirement * overheatScale, maxEfficiency);
            return heatBonus + sumAttribute(attribute, tile.x, tile.y) * boostScale;
        }

        @Override
        public void pickedUp(){
            attrsum = 0f;
            warmup = 0f;
        }

        @Override
        public void onProximityUpdate(){
            super.onProximityUpdate();
            attrsum = sumAttribute(attribute, tile.x, tile.y);
        }

    }
}