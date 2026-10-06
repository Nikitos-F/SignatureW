package signatureW.blocks.power;

import mindustry.world.blocks.power.NuclearReactor;
import arc.util.*;

import mindustry.Vars;
import mindustry.content.Weathers;
import arc.*;
import arc.math.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.Weather;
import mindustry.world.*;


public class EphyriteSignatureGenerator extends NuclearReactor {

    public EphyriteSignatureGenerator(String name) {
        super(name);
    }

    public class EphyriteSignatureGeneratorBuild extends NuclearReactorBuild {

        @Override
        public void createExplosion(){
            if(shouldExplode()){
                super.createExplosion();

                for (Weather.WeatherEntry entry : Vars.state.rules.weather) {
                    if (entry.weather == Weathers.rain) {

                        float duration = entry.always ? Float.POSITIVE_INFINITY : Mathf.random(entry.minDuration, entry.maxDuration);
                        entry.cooldown = duration + Mathf.random(entry.minFrequency, entry.maxFrequency);
                        Tmp.v1.setToRandomDirection();
                        Call.createWeather(entry.weather, entry.intensity, duration, Tmp.v1.x, Tmp.v1.y);

                    }
                }

            }
        }
    }
}