package signatureW.blocks.defense;

import arc.*;
import arc.audio.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.game.EventType.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import static mindustry.Vars.*;
import mindustry.world.blocks.defense.ForceProjector;

import mindustry.world.blocks.production.BurstDrill;
import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.meta.*;


public class ShieldedWall extends ForceProjector {

    public boolean isPassable = true;
    private boolean hasLiquidBoost = false;

    public ShieldedWall(String name) {
        super(name);
    }

    public void setStats() {
        super.setStats();
        stats.remove(Stat.liquidCapacity);
        stats.remove(Stat.itemCapacity);
        stats.remove(Stat.booster);
    }

    public void setBars(){
        super.setBars();
        removeBar("liquid");
    }

    public class ShieldedWallBuild extends ForceBuild {

        public void consumeUnits(Unit unit) {
            float overlapDst = (unit.hitSize / 2f + realRadius()) - unit.dst(this);

            if (overlapDst > 0) {
                if (overlapDst > unit.hitSize * 1.5f) {
                    unit.damage(50);
                } else {
                    unit.vel.setZero();
                    unit.move(Tmp.v1.set(unit.x, unit.y).sub(x, y).setLength(overlapDst + 0.01f));
                    if (Mathf.chanceDelta(0.12f * Time.delta)) {
                        Fx.circleColorSpark.at(unit.x, unit.y, team.color);
                    }
                }
            }
        }

        @Override
        public void updateTile() {
            super.updateTile();
            float rad = realRadius();

            if(!isPassable && rad > 1){
                Units.nearbyEnemies(team, x, y, rad + 10f, this::consumeUnits);
            }
        }
    }
}