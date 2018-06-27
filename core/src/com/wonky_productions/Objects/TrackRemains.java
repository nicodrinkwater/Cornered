package com.wonky_productions.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class TrackRemains {

    TextureRegion square;
    Vector2 position;
    Color color;
    float rotation, timer;
    int width, height;
    boolean dead;
    Car car;

    public TrackRemains(Car car, TextureRegion square, Vector2 position, Color color, float rotation, int width, int height) {
        this.car = car;
        this.square = square;
        this.position = new Vector2(position);
        this.color = new Color(color);
        this.rotation = (180 * rotation) / (float) Math.PI;
        timer = 0;
        this.width = width;
        this.height = height;
    }

    public void update(float delta){
        timer += delta;
        if(timer < 0.5f) {
            color.a = 0.05f;
        } else if(timer < 4){
            color.a = 0.05f * (4 - timer) / 3.5f;
        } else {
            dead = true;
        }
    }

    public void draw(SpriteBatch batch){
        batch.setColor(0,0,0, color.a);
        batch.draw(square, position.x - width/2 - car.cameraPos.x, position.y - height/2 - car.cameraPos.y, width/2, height/2, width, height, color.a * 20, color.a * 20, rotation);
    }
}
