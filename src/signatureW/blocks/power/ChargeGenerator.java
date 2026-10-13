package signatureW.blocks.power;

import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.consumers.ConsumeItemCharged;
import arc.graphics.*;
import arc.math.*;
import mindustry.graphics.*;


public class ChargeGenerator extends ConsumeGenerator {

    public Color lightColor = Color.valueOf("53B6D7");


    public ChargeGenerator(String name) {
        super(name);
        consume(new ConsumeItemCharged());
    }

    public class ChargeGeneratorBuild extends ConsumeGeneratorBuild {
        @Override
        public void drawLight(){
            drawer.drawLight(this);
            Drawf.light(x, y, (60f + Mathf.absin(10f, 5f)) * size, lightColor, 0.5f * warmup);
        }
    }
}