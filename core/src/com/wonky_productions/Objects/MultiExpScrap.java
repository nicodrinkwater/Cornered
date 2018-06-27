package com.wonky_productions.Objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;

public class MultiExpScrap extends MultiScrap {

    public MultiExpScrap(SelectCar car, MultiTrack track, TextureAtlas atlas, int carSize) {
        super(car, track, atlas);

        makeRandom(carSize);
    }

    public void makeRandom(int carSize){
        size = MathUtils.random(size / 2, size * 1.5f );
        velocity.x = MathUtils.random(-500 - 2 * carSize, 500 + 2 * carSize);
        velocity.y = MathUtils.random(-500 - 2 * carSize, 500 + 2 * carSize);
    }

    @Override
    public void update(float delta) {
        rotation += 10 * rotationSpeed * delta;
        velocity.x *= accel + (1 - accel)/ 2.0f;
        velocity.y *= accel + (1 - accel)/ 2.0f;
        scale *= 1 - 2 * delta;

        position.x += velocity.x * delta;
        position.y += velocity.y * delta;

        if(scale < 0.09f){
            dead = true;
        }

        checkCollision();
    }
}
