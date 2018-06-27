package com.wonky_productions.Objects;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;

public class FinalExplosionScrap  extends ExplosionScrap{
    public FinalExplosionScrap(Car car, Track track, TextureAtlas atlas, Car.DIRECTION direction, Color color, int carSize) {
        super(car, track, atlas, direction, color, carSize);

    }

    @Override
    public void makeRandom(int carSize) {
        float r = (float) Math.random();
        if(r < 0.25f){
            color.set(0,0,0,1);
        } else if(r < 0.5f){
            color.set(track.origColor);
        } else if(r < 0.75f){
            color.set(car.ringColor);
        } else {
            color.set(track.backgroundColor);
        }
        size = MathUtils.random(size, size * 3);
        velocity.x = MathUtils.random(-car.speed / 2 - 2 * carSize, car.speed / 2 + 2 * carSize);
        velocity.y = MathUtils.random(-car.speed / 2 - 2 * carSize, car.speed / 2 + 2 * carSize);

        accel = 0.99f - (float) Math.random() / 12;
    }

}
