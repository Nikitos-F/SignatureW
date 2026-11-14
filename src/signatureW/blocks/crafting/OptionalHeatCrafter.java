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


    public OptionalHeatCrafter(String name) {
        super(name);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);
        String text = "[lightgray]Base Heat: [#DB6464]" + ((int)sumAttribute(attribute, x, y) + (int)(baseEfficicency * heatRequirement));
        drawPlaceText(text, x, y, valid);
    }

    public class OptionalHeatCrafterBuild extends HeatCrafterBuild {
        private float MinHeat = baseEfficicency * heatRequirement;

        @Override
        public float calculateHeat(float[] sideHeat) {
            float baseHeat = super.calculateHeat(sideHeat);
            float floorAttr = sumAttribute(attribute, tile.x, tile.y);
            return baseHeat + floorAttr + MinHeat;
        }
    }
}