package com.wonky_productions.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SpeedUp {
    TextureAtlas atlas;
    TextureRegion arrow;
    Color color1, color2, color3, color4, trackColor;
    boolean col1up, col2up, col3up, col4up;

    public enum DIRECTION {UP, DOWN, LEFT, RIGHT}
    DIRECTION direction;
    boolean inRange;
    enum STATE {WAITING, IN_RANGE, HIT, DEAD}
    STATE state;
    Vector2 center, origCenter;
    int width, height, sqSize;
    float rotation, animatTimer, gap;

    Rectangle rect;
    Vector2 camPos;

    public SpeedUp(TextureAtlas atlas, DIRECTION direction, float j, float i, int squareSize, Color trackColor, Vector2 camPos) {
        this.atlas = atlas;
        this.direction = direction;
        center = new Vector2((j + 0.5f) * squareSize, (i + 0.5f) * squareSize);
        origCenter = new Vector2(center);
        inRange = false;
        arrow = atlas.findRegion("SpeedUp");
        state = STATE.IN_RANGE;

        sortColors();

        sqSize = squareSize;
        this.trackColor = trackColor;
        this.camPos = camPos;

        switch (direction){
            case UP:
            case DOWN:
                rect = new Rectangle(center.x - 2.5f * squareSize, center.y - 0.5f * squareSize, 5 * squareSize, squareSize);
                break;
            case LEFT:
            case RIGHT:
                rect = new Rectangle(center.x - 0.5f * squareSize, center.y - 2.5f * squareSize, squareSize, 5 * squareSize);
        }

        width = arrow.getRegionWidth();
        height = arrow.getRegionHeight();

        animatTimer = 0;
    }

    private void sortColors() {
        color4 = new Color(0.3f,0.3f,0.3f,1);
        color3 = new Color(0.2f,0.2f,0.2f,1);
        color2 = new Color(0.1f,0.1f,0.1f,1);
        color1 = new Color(0f,0f,0.0f,1);
    }

    public void update(float delta){
        if(state == STATE.HIT){
            animateColorTimed(delta);
        }

        if(state == STATE.IN_RANGE){
            animateColor(delta);
        }

        center.x = origCenter.x - camPos.x;
        center.y = origCenter.y - camPos.y;
    }

    private void animateColorTimed(float delta) {
        animateColor(2 * delta);
    }

    private void animateColor(float delta) {

        if(col1up) {
            lighten(color1, delta, 1);
        } else {
            darken(color1, delta, 1);
        }

        if(col2up) {
            lighten(color2, delta, 2);
        } else {
            darken(color2, delta, 2);
        }

        if(col3up) {
            lighten(color3, delta, 3);
        } else {
            darken(color3, delta, 3);
        }

        if(col4up) {
            lighten(color4, delta, 4);
        } else {
            darken(color4, delta, 4);
        }

    }

    private void darken(Color col, float delta, int i) {
        col.r -= delta * 2f;
        col.g -= delta * 2f;
        col.b -= delta * 2f;

        if(col.r < 0.f){

            col.r = 0.f;
            col.g = 0.f;
            col.b = 0.f;

            switch(i){
                case 1: col1up = true;
                    break;
                case 2: col2up = true;
                    break;
                case 3: col3up = true;
                    break;
                case 4: col4up = true;
                    break;
            }
        }
    }

    private void lighten(Color col, float delta, int i) {
        col.r += delta * 0.5f;
        col.g += delta * 0.5f;
        col.b += delta * 0.5f;

        if(col.r > 0.3f){

            col.r = 0.3f;
            col.g = 0.3f;
            col.b = 0.3f;

            switch(i){
                case 1: col1up = false;
                    break;
                case 2: col2up = false;
                    break;
                case 3: col3up = false;
                    break;
                case 4: col4up = false;
                    break;
            }
        }
    }

    public void draw(SpriteBatch batch){


        switch (direction){
            case UP: rotation = 0;
                batch.setColor(color1);
                batch.draw(arrow, center.x - width/2, center.y - height/2, width/2, height/2, width, height, 2 , 2, rotation);
                batch.setColor(color2);
                batch.draw(arrow, center.x - width/2, center.y - height/2 + 1.2f * sqSize, width/2, height/2, width, height, 2 , 2, rotation);
                batch.setColor(color3);
                batch.draw(arrow, center.x - width/2, center.y - height/2 + 2.4f * sqSize, width/2, height/2, width, height, 2 , 2, rotation);
                batch.setColor(color4);
                batch.draw(arrow, center.x - width/2, center.y - height/2 + 3.6f * sqSize, width/2, height/2, width, height, 2 , 2, rotation);
                break;
            case DOWN: rotation = 180;
                batch.setColor(color1);
                batch.draw(arrow, center.x - width/2, center.y - height/2, width/2, height/2, width, height, 2 , 2, rotation);
                batch.setColor(color2);
                batch.draw(arrow, center.x - width/2, center.y - height/2 - 1.2f * sqSize, width/2, height/2, width, height, 2 , 2, rotation);
                batch.setColor(color3);
                batch.draw(arrow, center.x - width/2, center.y - height/2 - 2.4f * sqSize, width/2, height/2, width, height, 2 , 2, rotation);
                batch.setColor(color4);
                batch.draw(arrow, center.x - width/2, center.y - height/2 - 3.6f * sqSize, width/2, height/2, width, height, 2 , 2, rotation);
                break;
            case LEFT: rotation = 90;
                batch.setColor(color1);
                batch.draw(arrow, center.x - width/2, center.y - height/2, width/2, height/2, width, height, 2 , 2, rotation);
                batch.setColor(color2);
                batch.draw(arrow, center.x - width/2 - 1.2f * sqSize, center.y - height/2, width/2, height/2, width, height, 2 , 2, rotation);
                batch.setColor(color3);
                batch.draw(arrow, center.x - width/2 - 2.4f * sqSize, center.y - height/2 , width/2, height/2, width, height, 2 , 2, rotation);
                batch.setColor(color4);
                batch.draw(arrow, center.x - width/2 - 3.6f * sqSize, center.y - height/2 , width/2, height/2, width, height, 2 , 2, rotation);
                break;
            case RIGHT: rotation = 270;
                batch.setColor(color1);
                batch.draw(arrow, center.x - width/2, center.y - height/2, width/2, height/2, width, height, 2 , 2, rotation);
                batch.setColor(color2);
                batch.draw(arrow, center.x - width/2 + 1.2f * sqSize, center.y - height/2, width/2, height/2, width, height, 2 , 2, rotation);
                batch.setColor(color3);
                batch.draw(arrow, center.x - width/2 + 2.4f * sqSize, center.y - height/2 , width/2, height/2, width, height, 2 , 2, rotation);
                batch.setColor(color4);
                batch.draw(arrow, center.x - width/2 + 3.6f * sqSize, center.y - height/2 , width/2, height/2, width, height, 2 , 2, rotation);
                break;
        }
    }

    private void animateHit(float delta) {

    }

    public boolean checkCar(Vector2 carCenter){
        if(state == STATE.IN_RANGE && rect.contains(carCenter)){
            state = STATE.HIT;
            return  true;
        } else {
            return  false;
        }
    }

    public void reset() {
        state = STATE.IN_RANGE;
        animatTimer = 0;
        sortColors();
        col1up = false;
        col2up = false;
        col3up = false;
        col4up = false;
    }

}
