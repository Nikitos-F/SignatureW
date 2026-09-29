package signatureW.blocks.power;

import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.consumers.ConsumeItemCharged;


public class ChargeGenerator extends ConsumeGenerator {

    public ChargeGenerator(String name) {
        super(name);
        consume(new ConsumeItemCharged());
    }
}