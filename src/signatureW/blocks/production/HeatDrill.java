package signatureW.blocks.production;



import mindustry.world.blocks.production.BurstDrill;
import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.meta.*;


public class HeatDrill extends BurstDrill {

    public float heatRequirement = 10f;
    public float overheatScale = 1f;
    public float maxEfficiency = 2f;


    public TextureRegion heatRegion;
    public Color heatColor = new Color(1f, 0.22f, 0.22f, 0.8f);
    public float heatPulse = 0.3f, heatPulseScl = 10f;



    public HeatDrill(String name) {
        super(name);
    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("heat", (HeatDrillBuild entity) ->
                new Bar(() ->
                        Core.bundle.format("bar.heatpercent", (int)(entity.heat + 0.01f), (int)(entity.efficiencyScale() * 100 + 0.01f)),
                        () -> Pal.lightOrange,
                        () -> entity.heat / heatRequirement));
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.input, heatRequirement, StatUnit.heatUnits);
        stats.add(Stat.maxEfficiency, (int)(maxEfficiency * 100f), StatUnit.percent);
    }

    @Override
    public void load(){
        super.load();
        heatRegion = Core.atlas.find(name + "-heat", "oh-no");
    }

    public class HeatDrillBuild extends BurstDrillBuild implements HeatConsumer{
        public float[] sideHeat = new float[4];
        public float heat = 0f;

        @Override
        public void updateTile(){
            heat = calculateHeat(sideHeat);
            super.updateTile();
        }

        @Override
        public float efficiencyScale(){
            float over = Math.max(heat - heatRequirement, 0f);
            return Math.min(Mathf.clamp((heat+heatRequirement*0.1f) / heatRequirement) + over / heatRequirement * overheatScale, maxEfficiency);
        }

        @Override
        public void draw(){
            super.draw();

            Draw.z(Layer.blockAdditive);
            float[] side = sideHeat(); // this.sideHeat()
            for(int i = 0; i < 4; i++){
                if(side[i] > 0){
                    Draw.blend(Blending.additive);
                    Draw.color(heatColor, side[i] / heatRequirement() * (heatColor.a * (1f - heatPulse + Mathf.absin(heatPulseScl, heatPulse))));
                    Draw.rect(heatRegion, x, y, i * 90f);
                    Draw.blend();
                    Draw.color();
                }
            }
            Draw.z(Layer.block);
        }

        @Override
        public float heatRequirement(){
            return heatRequirement;
        }

        @Override
        public float[] sideHeat(){
            return sideHeat;
        }
    }

}