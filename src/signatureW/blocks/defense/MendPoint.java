package signatureW.blocks.defense;

import arc.math.*;
import mindustry.world.blocks.defense.MendProjector;
import mindustry.content.*;
import mindustry.graphics.*;
import arc.util.*;

import static mindustry.Vars.*;


public class MendPoint extends MendProjector {

    public int heal = 1;
    private boolean any = false;

    public MendPoint(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.remove(mindustry.world.meta.Stat.repairTime);
        stats.add(mindustry.world.meta.Stat.repairSpeed, heal/reload*60, mindustry.world.meta.StatUnit.perSecond);
    }


    public class MendPointBuild extends MendBuild {

        @Override
        public void updateTile(){
            boolean canHeal = !checkSuppression();

            smoothEfficiency = Mathf.lerpDelta(smoothEfficiency, efficiency, 0.08f);
            heat = Mathf.lerpDelta(heat, efficiency > 0 && canHeal ? 1f : 0f, 0.08f);
            charge += heat * delta();

            phaseHeat = Mathf.lerpDelta(phaseHeat, optionalEfficiency, 0.1f);

            if(optionalEfficiency > 0 && timer(timerUse, useTime / timeScale) && canHeal){
                consume();
            }

            if(charge >= reload && canHeal){
                float realRange = range;
                charge = 0f;

                any = false;

                indexer.eachBlock(this, realRange, b -> b.damaged() && !b.isHealSuppressed(), other -> {
                    other.heal(heal * efficiency);
                    other.recentlyHealed();
                    Fx.healBlockFull.at(other.x, other.y, other.block.size, baseColor, other.block);
                    any = true;
                });

                if(any){
                    mendSound.at(this, 1f + Mathf.range(0.1f), mendSoundVolume);
                }
            }
        }

        @Override
        public void drawSelect(){
            float realRange = range;
            indexer.eachBlock(this, realRange, other -> true, other -> Drawf.selected(other, Tmp.c1.set(baseColor).a(Mathf.absin(4f, 1f))));
            Drawf.dashCircle(x, y, realRange, baseColor);
        }
    }
}