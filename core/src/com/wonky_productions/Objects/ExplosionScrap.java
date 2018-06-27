package com.wonky_productions.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class ExplosionScrap extends Scrap {

    public ExplosionScrap(Car car, Track track, TextureAtlas atlas, Car.DIRECTION direction, Color color, int carSize) {
        super(car, track, atlas, direction, color);

        makeRandom(carSize);
    }

    public ExplosionScrap(Car car, Track track, TextureAtlas atlas, Car.DIRECTION direction, Color color, int carSize, Vector2 pos) {
        super(car, track, atlas, direction, color, pos);

        makeRandomForBar(carSize);
    }

    public void makeRandom(int carSize){
        size = MathUtils.random(size, size * 2 );
        velocity.x = MathUtils.random(-car.speed / 2 - 2 * carSize, car.speed / 2 + 2 * carSize);
        velocity.y = MathUtils.random(-car.speed / 2 - 2 * carSize, car.speed / 2 + 2 * carSize);
    }

    public void makeRandomForBar(int carSize){
        size = MathUtils.random(size / 2, size);
        velocity.x = MathUtils.random(-car.speed / 2 - 2 * carSize, car.speed / 2 + 2 * carSize);
        velocity.y = MathUtils.random(-car.speed / 2 - 2 * carSize, car.speed / 2 + 2 * carSize);
    }

    @Override
    public void update(float delta) {
        accel = 1- delta;
        rotation += 10 * rotationSpeed * delta;
        velocity.x *= accel + (1 - accel)/ 2.0f;
        velocity.y *= accel + (1 - accel)/ 2.0f;
        scale *= (1 - 2 * delta);

        position.x += velocity.x * delta;
        position.y += velocity.y * delta;

        if(scale < 0.09f){
            dead = true;
        }

        checkCollision();
    }
}
