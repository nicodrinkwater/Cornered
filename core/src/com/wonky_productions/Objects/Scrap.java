package com.wonky_productions.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Scrap {

    TextureAtlas atlas;
    TextureRegion square, triangle, star, squareScrap;
    Car.DIRECTION direction;
    float size;
    float rotation, rotationSpeed, scale, accel;
    Vector2 velocity, position, prePos;
    Car car;
    Color color;
    boolean dead;
    float spread;
    Track track;
    float wallSquareSize;


    public Scrap(Car car, Track track, TextureAtlas atlas, Car.DIRECTION direction, Color color) {
        this.track = track;
        this.atlas = atlas;
        this.direction = direction;
        this.color = new Color(color);
        this.car = car;

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

    public Scrap(Car car, Track track, TextureAtlas atlas, Car.DIRECTION direction, Color color, Vector2 pos) {
        this.track = track;
        this.atlas = atlas;
        this.direction = direction;
        this.color = new Color(color);
        this.car = car;

        createMe2(pos);
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

        spread = car.speed / 3;
        dead = false;
        scale = 1;
        size = MathUtils.random(car.size/6, car.size/ 3.5f);
        rotation =  MathUtils.random(0, 90);
        position = new Vector2(car.center.x - size/2, car.center.y - size/2);
        prePos = new Vector2(car.center.x - size/2, car.center.y - size/2);
        rotationSpeed =  MathUtils.random(-180, 180);
        accel = 0.975f;
        float e = MathUtils.random(- car.speed / 3, car.speed / 3);
        switch(direction){

            case LEFT:
                velocity = new Vector2(-car.speed/2 + e, MathUtils.random(-spread, spread));
                break;
            case RIGHT:
                velocity = new Vector2(car.speed/2  + e, MathUtils.random(-spread, spread));
                break;
            case UP:
                velocity = new Vector2(MathUtils.random(-spread, spread), car.speed/2  + e);
                break;
            case DOWN:
                velocity = new Vector2(MathUtils.random(-spread, spread), -car.speed/2  + e);
                break;
        }
    }

    private void createMe2(Vector2 pos) {

        spread = car.speed / 3;
        dead = false;
        scale = 1;
        size = MathUtils.random(car.size/6, car.size/ 3.5f);
        rotation =  MathUtils.random(0, 90);
        position = new Vector2(pos);
        prePos = new Vector2(pos);
        rotationSpeed =  MathUtils.random(-180, 180);
        accel = 0.975f;
        float e = MathUtils.random(- car.speed / 3, car.speed / 3);
        switch(direction){

            case LEFT:
                velocity = new Vector2(-car.speed/2 + e, MathUtils.random(-spread, spread));
                break;
            case RIGHT:
                velocity = new Vector2(car.speed/2  + e, MathUtils.random(-spread, spread));
                break;
            case UP:
                velocity = new Vector2(MathUtils.random(-spread, spread), car.speed/2  + e);
                break;
            case DOWN:
                velocity = new Vector2(MathUtils.random(-spread, spread), -car.speed/2  + e);
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

        prePos.set(position.x, position.y);
    }

    protected void checkCollision() {

        if(track.checkHitWall(position.x + size/2, position.y + size/2)){

           if(velocity.y > 0 && velocity.x > 0){
               if(track.checkHitWall(position.x - wallSquareSize + size/2, position.y + size/2)) {
                   velocity.y = -velocity.y;
               } else if(track.checkHitWall(position.x + size/2, position.y + size/2 - wallSquareSize)){
                   velocity.x = -velocity.x;
               } else  if(Math.abs(position.x - prePos.x) > Math.abs(position.y - prePos.y)){
                   velocity.x = -velocity.x;
               } else {
                   velocity.y = -velocity.y;
               }
           } else if(velocity.y > 0 && velocity.x < 0){
               if(track.checkHitWall(position.x + wallSquareSize + size/2, position.y + size/2)) {
                   velocity.y = -velocity.y;
               } else if(track.checkHitWall(position.x + size/2, position.y + size/2 - wallSquareSize)){
                   velocity.x = -velocity.x;
               } else  if(Math.abs(position.x - prePos.x) > Math.abs(position.y - prePos.y)){
                   velocity.x = -velocity.x;
               } else {
                   velocity.y = -velocity.y;
               }
           } else  if(velocity.y < 0 && velocity.x > 0){
               if(track.checkHitWall(position.x - wallSquareSize + size/2, position.y + size/2)) {
                   velocity.y = -velocity.y;
               } else if(track.checkHitWall(position.x + size/2, position.y + size/2 + wallSquareSize)){
                   velocity.x = -velocity.x;
               } else  if(Math.abs(position.x - prePos.x) > Math.abs(position.y - prePos.y)){
                   velocity.x = -velocity.x;
               } else {
                   velocity.y = -velocity.y;
               }
           } else  if(velocity.y < 0 && velocity.x < 0){
               if(track.checkHitWall(position.x + wallSquareSize + size/2, position.y + size/2)) {
                   velocity.y = -velocity.y;
               } else if(track.checkHitWall(position.x + size/2, position.y + size/2 + wallSquareSize)){
                   velocity.x = -velocity.x;
               } else  if(Math.abs(position.x - prePos.x) > Math.abs(position.y - prePos.y)){
                   velocity.x = -velocity.x;
               } else {
                   velocity.y = -velocity.y;
               }
           }
        }
    }

    public void draw(SpriteBatch batch){
        batch.setColor(color);
        batch.draw(square, position.x - car.cameraPos.x, position.y - car.cameraPos.y, size/2, size/2, size, size, scale, scale, rotation);
    }
}
