package signatureW.blocks.power;

import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.consumers.ConsumeItemCharged;
import arc.graphics.*;
import arc.math.*;
import mindustry.graphics.*;


public class ConsumeGeneratorColor extends ConsumeGenerator {

    public Color lightColor = Color.valueOf("53B6D7");
    public float lightRadius = 2f;


    public ConsumeGeneratorColor(String name) {
        super(name);
    }

    public class ChargeGeneratorBuild extends ConsumeGeneratorBuild {
        @Override
        public void drawLight(){
            drawer.drawLight(this);
            Drawf.light(x, y, lightRadius*8f, lightColor, 0.5f * warmup);
        }
    }
}