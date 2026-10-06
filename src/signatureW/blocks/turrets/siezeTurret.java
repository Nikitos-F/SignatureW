package signatureW.blocks;


import mindustry.world.blocks.defense.turrets.PowerTurret;


public class siezeTurret extends PowerTurret {

//    public float minItemCapacity = 50f;

    public siezeTurret(String name) {
        super(name);

//        targetAir = false;
//        targetGround = false;
//        shake = 5f;
    }

    @Override
    public void init() {
        super.init();
//        this.buildingFilter = b -> b.block.itemCapacity > minItemCapacity;
    }
}