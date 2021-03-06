package com.wonky_productions.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class MenuScrap {

    TextureAtlas atlas;
    TextureRegion square, triangle, star, squareScrap;
    Car.DIRECTION direction;
    float size;
    float rotation, rotationSpeed, scale, accel;
    Vector2 velocity, position, prePos, movement;
    MenuCar car;
    Color color;
    boolean dead;
    float spread;
    MenuTrack track;
    float wallSquareSize;

    public MenuScrap(MenuCar car, MenuTrack track, TextureAtlas atlas) {
        this.track = track;
        this.atlas = atlas;
        direction = Car.DIRECTION.RIGHT;
        color = new Color(0,0,0,1);
        this.car = car;

        movement = car.moveMent;
        createMe();
        square = atlas.findRegion("octagon");
        squareScrap = atlas.findRegion("SquareScrap");
        triangle = atlas.findRegion("Triangle");
        star = atlas.findRegion("Star");

        float r = (float)Math.random();
        if(r < 0.333){
            square = squareScrap;
        } else if(r < 0.666){
            square = triangle;
        } else {
            square = star;
        }
        wallSquareSize = track.squareSize;
    }


    private void createMe() {

        spread = 600 / 3;
        dead = false;
        scale = 1;
        size = MathUtils.random(car.size/6, car.size/ 3.5f);
        rotation =  MathUtils.random(0, 90);
        position = new Vector2(car.center.x - size/2, car.center.y - size/2);
        prePos = new Vector2(car.center.x - size/2, car.center.y - size/2);
        rotationSpeed =  MathUtils.random(-180, 180);
        accel = 0.975f;
        float e = MathUtils.random(- 700 / 3, 700 / 3);
        switch(direction){

            case LEFT:
                velocity = new Vector2(-400 + e, MathUtils.random(-spread, spread));
                break;
            case RIGHT:
                velocity = new Vector2(400  + e, MathUtils.random(-spread, spread));
                break;
            case UP:
                velocity = new Vector2(MathUtils.random(-spread, spread), 400  + e);
                break;
            case DOWN:
                velocity = new Vector2(MathUtils.random(-spread, spread), 400 + e);
                break;
        }
    }

    public void update(float delta){

        accel = 1 - delta;
        rotation += rotationSpeed * delta;
        velocity.x *= accel;
        velocity.y *= accel;
        scale *= accel;

        rotation += rotationSpeed * delta;


        position.x += velocity.x * delta;
        position.y += velocity.y * delta;

        if(Math.abs(scale) < 0.04f){
            dead = true;
        }

        checkCollision();
    }

    protected void checkCollision() {

            if(position.y > prePos.y + 100  || position.y < prePos.y - 100) {
                velocity.y = -velocity.y;
            }
    }

    public void draw(SpriteBatch batch){
        batch.setColor(color);
        batch.draw(square, position.x - car.cameraPos.x + movement.x, position.y + 800 + movement.y, size/2, size/2, size, size, scale, scale, rotation);
    }

    public void jumpBack(int jump) {
        position.x -= jump;
    }
}
