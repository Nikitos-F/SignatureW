package signatureW.blocks.turrets.korium;


import mindustry.world.blocks.defense.turrets.ItemTurret;
import arc.*;
import arc.math.*;
import arc.util.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.ui.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.meta.*;

public class KoriumItemTurret extends ItemTurret {

    public KoriumItemTurret(String name) {
        super(name);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        if(heatRequirement < 0) return;

        String text = "";
        if((int)sumAttribute(Attribute.heat, x, y) > 0){
            text = "[lightgray]Floor Heat: [#DB6464]" + (int)sumAttribute(Attribute.heat, x, y);
        } else {
            text = "[lightgray]Floor Heat: 0";
        }
        drawPlaceText(text, x, y, valid);
    }

    public class KoriumItemTurretBuild extends ItemTurretBuild {

        @Override
        public float calculateHeat(float[] sideHeat) {
            float baseHeat = super.calculateHeat(sideHeat);
            float floorAttr = sumAttribute(Attribute.heat, tile.x, tile.y);
            return baseHeat + floorAttr;
        }
    }

}