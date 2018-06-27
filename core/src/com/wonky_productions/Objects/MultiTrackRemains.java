package com.wonky_productions.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class MultiTrackRemains {

    TextureRegion square;
    Vector2 position;
    Color color;
    float rotation, timer;
    int width, height;
    boolean dead;
    SelectCar car;
    MenuCar car2;
    Vector2 movement;

    public MultiTrackRemains(SelectCar car, TextureRegion square, Vector2 position, int width, int height, Vector2 movement) {
        this.car = car;
        this.square = square;
        this.position = new Vector2(position);
        this.color = new Color(0,0,0,1);
        timer = 0;
        this.width = width /2;
        this.height = height / 2;
        this.movement = movement;
    }

    public MultiTrackRemains(MenuCar car, TextureRegion square, Vector2 position, int width, int height, Vector2 movement) {
        this.car2 = car;
        this.square = square;
        this.position = new Vector2(position);
        this.color = new Color(0,0,0,1);
        timer = 0;
        this.width = width /2;
        this.height = height / 2;
        this.movement = movement;
    }

    public void update(float delta){
        timer += delta;
        if(timer < 0.2f) {
            color.a = 0.05f;
        } else if(timer < 2){
            color.a = 0.05f * (2 - timer) / 1.8f;
        } else {
            dead = true;
        }
    }

    public void draw(SpriteBatch batch){
        if(car != null) {
            batch.setColor(0, 0, 0, color.a * 4);
            batch.draw(square, position.x - width / 2 - car.cameraPos.x + movement.x, position.y - height / 2 - car.cameraPos.y, width / 2, height / 2, width, height, 1, color.a * 20, rotation);
        } else {
            batch.setColor(0, 0, 0, color.a * 4);
            batch.draw(square, position.x - width / 2 - car2.cameraPos.x + movement.x, position.y - height / 2 + movement.y + 800, width / 2, height / 2, width, height, 1, color.a * 20, rotation);
        }
    }

    public void jumpBack(int jump) {
        position.x -= jump;
    }
}
