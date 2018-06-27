package com.wonky_productions.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.wonky_productions.Constants;
import com.wonky_productions.Screens.Page;

public class SelectCar {

    float endTimer;
    Page level;
    TextureAtlas atlas;
    TextureRegion square, roundSquare, circle, octogon, quarter, squareOutline, crossQuarter, shadow;
    float speed, skidSpeedLimit, startSpeed, printTimer;
    int size;
    Array<MultiScrap> tail, explosion;
    Array<MultiTrackRemains> print;
    Vector2 velocity, accel;
    float skidRate, rotation;
    private float skidFactor;
    protected float centerRot;
    boolean centerTurned, middleTurned;
    float centerAlpha, centerFlashTimer, ringBright;
    Color centerColor;
    float targetRot, shade1, shade2, shade3, shade4;
    float shadeMin, shadeMax, lightDir;
    private float startTimer;
    float barSize;
    Vector2 barCarPos;
    Array<LevelData> datas;
    LevelData currentData;
    MultiTrack multiTrack;

    float distTravelled;
    Vector2 moveMent, fixedCentre;

    public float getDistTravelled() {
        return distTravelled;
    }

    public void eplode() {
        createExplosion();
        state = STATE.HIT_WALL;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public enum STATE { START, NORMAL, HIT_WALL, REACHED_END}
    STATE state;

    public enum DIRECTION {UP, DOWN, LEFT, RIGHT}
    enum TURNING {CLOCK, ANTI, STILL}
    TURNING turning, centerTurning;
    boolean turnComplete;
    DIRECTION direction, startDir;
    boolean start;
    float grip;

    Vector2 position, center, actualPos, cameraPos;
    Color color, ringColor;

    public SelectCar(Page page, Array<LevelData> datas, MultiTrack multiTrack, TextureAtlas atlas, int size) {
        level = page;
        this.atlas = atlas;
        this.datas = datas;
        this.multiTrack = multiTrack;
        this.size = size;
        square = atlas.findRegion("square");
        octogon = atlas.findRegion("pointer");
        roundSquare = atlas.findRegion("cross");
        circle = atlas.findRegion("circle");
        quarter = atlas.findRegion("TopTriangle");
        squareOutline = atlas.findRegion("squareOutline");
        crossQuarter = atlas.findRegion("crossFatSplit");
        shadow = atlas.findRegion("shadowSq");
        position = page.getCarPos();
        center = new Vector2(0,0);
        color = new Color(0, 0, 0,1);
        direction = DIRECTION.UP;
        speed = 0;
        tail = new Array<MultiScrap>();
        explosion = new Array<MultiScrap>();
        print = new Array<MultiTrackRemains>();
        state = STATE.START;
        velocity = new Vector2(0,0);
        accel = new Vector2(0,0);
        skidRate = 0;
        rotation = 0;
        turning = TURNING.STILL;
        centerTurning = TURNING.STILL;
        turnComplete = false;
        skidSpeedLimit = 50;
        skidFactor = 0;
        centerRot = 0;
        centerAlpha = 0.6f;
        centerFlashTimer = 0;
        endTimer = 0;

        currentData = datas.get(0);

        centerColor = currentData.car1;
        ringBright = 0.6f;
        this.ringColor = currentData.carCross;
        shade1 = 0;
        shade2 = 0;
        shade3 = 0;
        shade4 = 0;
        lightDir = 340;
        shadeMin = 0;
        shadeMax = 0.65f;
        grip = currentData.initialGrip;
        this.

        actualPos = new Vector2(Constants.SCREEN_WIDTH / 2 - size/2, Constants.SCREEN_HEIGHT/2 - size/2);
        fixedCentre =  new Vector2(Constants.SCREEN_WIDTH / 2 - size/2, Constants.SCREEN_HEIGHT/2 - size/2);

        cameraPos = new Vector2();

        distTravelled = 0;
        barSize = 70;
        barCarPos = new Vector2(60 - barSize/2, 0);

        moveMent = page.getMovement();
    }

    public void sortColors(LevelData levelData) {
        centerColor = levelData.car1;
        ringColor = levelData.carCross;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDirection(TrackCreater.DIRECTION startDr) {
        if(startDr == TrackCreater.DIRECTION.UP){
            startDir = DIRECTION.UP;
        } else if(startDr == TrackCreater.DIRECTION.DOWN){
            startDir = DIRECTION.DOWN;
        } else if(startDr == TrackCreater.DIRECTION.LEFT){
            startDir= DIRECTION.LEFT;
        } else if(startDr == TrackCreater.DIRECTION.RIGHT){
            startDir = DIRECTION.RIGHT;
        }
    }

    public void update(float delta){


        actualPos.x = fixedCentre.x + moveMent.x;

        workTimers(delta);

        if(startTimer < 5) {
            startTimer += delta;
        }

        workShades();

       // barCarPos.y = level.getBarCarY() - 40;

        skidRate = 1 - skidFactor * delta;


        if(state != STATE.HIT_WALL && Math.random() * delta * 60 > 0.6f){
            tail.add(new MultiScrap(this, multiTrack, atlas));
        }

        if(state == STATE.HIT_WALL){
            endTimer += delta;
            rotation = 0;
            targetRot = 0;
            if(endTimer < 0.2f) {
                shake(delta);
            }
        }


        if(Math.abs(velocity.x) < 3){
            velocity.x = 0;
        }
        if(Math.abs(velocity.y) < 3){
            velocity.y = 0;
        }

        center.x = position.x + size/2;
        center.y = position.y + size/2;

        cameraPos.set(center.x - Constants.SCREEN_WIDTH/2, center.y - Constants.SCREEN_HEIGHT / 2);

        for (int i = 0; i < explosion.size; i++) {
            explosion.get(i).update(delta);
            try {
                if (explosion.get(i).dead) {
                    explosion.removeIndex(i);
                }
            } catch (Exception e){
            }
        }

        for (int i = 0; i < tail.size; i++) {
            tail.get(i).update(delta);
            try {
                if (tail.get(i).dead) {
                    tail.removeIndex(i);
                }
            } catch (Exception e){
            }
        }

        for (int i = 0; i < print.size; i++) {
            print.get(i).update(delta);
            try {
                if (print.get(i).dead) {
                    print.removeIndex(i);
                }
            } catch (Exception e){
            }
        }
    }

    private void workShades() {
        shade1 = getShade(rotation);
        shade2 = getShade(rotation + 90);
        shade3 = getShade(rotation + 180);
        shade4 = getShade(rotation + 270);
    }

    private float getShade(float rotation) {
        if(rotation > 360){
            rotation -= 360;
        } else if(rotation < 0){
            rotation += 360;
        }

        float b = Math.abs(rotation - lightDir);

        if(b > 180){
            b = 360 - b;
        }

        return shadeMin + b * (shadeMax - shadeMin) / 180;
    }

    private void workTimers(float delta) {
        if(state == STATE.NORMAL) {
            distTravelled += delta;
        }
        printTimer += delta;
        centerFlashTimer += delta;

        if(printTimer > -1f && state != STATE.HIT_WALL){
            print.add(new MultiTrackRemains(this, atlas.findRegion("fuzz"), center, (int)(size * 0.5), size, moveMent));
            printTimer = 0;
        }

        if(centerFlashTimer < 0.2 && centerAlpha < 0.9f){
            centerAlpha += delta * 3;
        } else if(centerFlashTimer < 0.4 && centerAlpha > 0.5f){
            centerAlpha -= delta * 3;
        }
    }

    public void shake(float delta){

        if (centerFlashTimer < 0.04) {
            position.x += 400 * delta;
        } else if (centerFlashTimer < 0.08) {
            position.y += 400 * delta;
        } else if (centerFlashTimer < 0.12) {
            position.x -= 400 * delta;
        } else if (centerFlashTimer < 0.16) {
            position.y -= 400 * delta;
        } else {
            centerFlashTimer = 0;
        }


    }

    public void refineRotations(){
        if(targetRot == 0){
            if (rotation > 180) {
                rotation -= 360;
            } else if (rotation < -180) {
                rotation += 360;
            }

            if (centerRot > 180) {
                centerRot -= 360;
            } else if (centerRot < -180) {
                centerRot += 360;
            }
        } else {
            if (rotation > 360) {
                rotation -= 360;
            } else if (rotation < 0) {
                rotation += 360;
            }

            if(centerRot > 360){
                centerRot -= 360;
            } else if(centerRot < 0){
                centerRot += 360;
            }
        }
    }

    public void reset() {

        state = STATE.NORMAL;
        startTimer = 0;

        explosion.clear();
        tail.clear();
        print.clear();
        endTimer = 0;
        distTravelled = 0;
    }

    private void createExplosion() {

        for (int i = 0; i < 30; i++) {
            explosion.add(new MultiExpScrap(this, multiTrack, atlas, 160));
        }
        speed = 0;
    }

    public void draw(SpriteBatch batch){

        for (int i = 0; i < tail.size; i++) {
            tail.get(i).draw(batch);
        }

        for (int i = 0; i < explosion.size; i++) {
            explosion.get(i).draw(batch);
        }


        for (int i = 0; i < print.size; i++) {
            print.get(i).draw(batch);
        }


        if(state != STATE.HIT_WALL) {

            // shadow
            batch.setColor(0,0,0,0.09f * shadeMax);
            for (int i = 0; i < 10; i++) {
                batch.draw(square,actualPos.x - i * (size / 130.0f), actualPos.y - 3 * i * size / 130.0f, size/2, size/2, size, size, 1f, 1f, rotation);
            }

            batch.setColor(0.1f, 0.1f, 0.1f, 1f);
            batch.draw(square, actualPos.x, actualPos.y, size/2, size/2, size, size, 1, 1, rotation);
            //batch.draw(square, barCarPos.x, barCarPos.y, barSize/2, barSize/2, barSize, barSize, 1, 1, rotation);

            batch.setColor(ringColor.r * ringBright, ringColor.g * ringBright, ringColor.b * ringBright, 1);

            batch.draw(square, actualPos.x, actualPos.y, size/2, size/2, size, size, 0.9f, 0.9f, rotation);

            //batch.setColor(ringColor.r * 0.6f, ringColor.g * 0.6f, ringColor.b * 0.6f, 1);
            //batch.draw(square, barCarPos.x, barCarPos.y, barSize/2, barSize/2, barSize, barSize, 0.9f, 0.9f, rotation);

            batch.setColor(centerColor);

            batch.draw(square,actualPos.x, actualPos.y, size/2, size/2, size, size, 0.7f, 0.7f, rotation);
            // batch.draw(square, barCarPos.x, barCarPos.y, barSize/2, barSize/2, barSize, barSize, 0.7f, 0.7f, rotation);

            batch.setColor(0,0,0, shade1);

            batch.draw(quarter,actualPos.x, actualPos.y , size/2, size/2, size, size, 1f, 1, rotation);
            //batch.draw(quarter, barCarPos.x, barCarPos.y, barSize/2, barSize/2, barSize, barSize, 1, 1, rotation);
            batch.setColor(0,0,0, shade2);
            batch.draw(quarter, actualPos.x, actualPos.y, size/2, size/2, size, size, 1,1, rotation + 90) ;
            //batch.draw(quarter, barCarPos.x, barCarPos.y, barSize/2, barSize/2, barSize, barSize, 1, 1, rotation + 90);
            batch.setColor(0,0,0, shade3);
            batch.draw(quarter, actualPos.x, actualPos.y, size/2, size/2, size, size, 1f, 1f, rotation + 180);
            // batch.draw(quarter, barCarPos.x, barCarPos.y, barSize/2, barSize/2, barSize, barSize, 1, 1, rotation + 180);
            batch.setColor(0,0,0, shade4);
            batch.draw(quarter,actualPos.x, actualPos.y, size/2, size/2, size, size, 1f, 1f, rotation + 270);
            //batch.draw(quarter, barCarPos.x, barCarPos.y, barSize/2, barSize/2, barSize, barSize, 1, 1, rotation + 270);

            batch.setColor(0.1f, 0.1f, 0.1f, 1f);
            batch.draw(square,actualPos.x, actualPos.y, size/2, size/2, size, size, 0.55f, 0.55f, rotation);
            // batch.draw(square, barCarPos.x, barCarPos.y, barSize/2, barSize/2, barSize, barSize, 0.4f,  0.4f, rotation);

            batch.setColor(ringColor.r * centerAlpha,ringColor.g * centerAlpha, ringColor.b * centerAlpha, 1);
            batch.draw(roundSquare,actualPos.x, actualPos.y, size/2, size/2, size, size, 0.4f, 0.4f, centerRot);
            //  batch.draw(roundSquare, barCarPos.x, barCarPos.y, barSize/2, barSize/2, barSize, barSize, 0.4f, 0.4f, centerRot);

            batch.setColor(0.1f, 0.1f, 0.1f, 1f);
            batch.draw(squareOutline,actualPos.x, actualPos.y, size/2, size/2, size, size, 0.55f, 0.55f, rotation);
            // batch.draw(squareOutline, barCarPos.x, barCarPos.y, barSize/2, barSize/2, barSize, barSize, 0.55f,  0.55f, rotation);


            batch.setColor(0.1f, 0.1f, 0.1f, 1f);
            batch.draw(square, actualPos.x, actualPos.y, size/2, size/2, size, size, 0.2f, 0.2f, rotation);
            // batch.draw(square, barCarPos.x, barCarPos.y, barSize/2, barSize/2, barSize, barSize, 0.2f,  0.2f, rotation);

            batch.setColor(centerColor.r, centerColor.g, centerColor.b, 0.6f);
            batch.draw(square, actualPos.x, actualPos.y, size/2, size/2, size, size, 0.14f, 0.14f, rotation);
            //  batch.draw(square, barCarPos.x, barCarPos.y, barSize/2, barSize/2, barSize, barSize, 0.14f,  0.14f, rotation);

        }
    }

    public void drawBarCar(SpriteBatch batch){
        batch.setColor(0.1f, 0.1f, 0.1f, 1f);
        batch.draw(square, barCarPos.x, barCarPos.y, barSize/2, barSize/2, barSize, barSize, 1, 1, rotation);

        batch.setColor(ringColor.r * 0.6f, ringColor.g * 0.6f, ringColor.b * 0.6f, 1);
        batch.draw(square, barCarPos.x, barCarPos.y, barSize/2, barSize/2, barSize, barSize, 0.9f, 0.9f, rotation);

        batch.setColor(centerColor);
        batch.draw(square, barCarPos.x, barCarPos.y, barSize/2, barSize/2, barSize, barSize, 0.7f, 0.7f, rotation);

        batch.setColor(0,0,0, shade1);
        batch.draw(quarter, barCarPos.x, barCarPos.y, barSize/2, barSize/2, barSize, barSize, 1, 1, rotation);

        batch.setColor(0,0,0, shade2);
        batch.draw(quarter, barCarPos.x, barCarPos.y, barSize/2, barSize/2, barSize, barSize, 1, 1, rotation + 90);

        batch.setColor(0,0,0, shade3);
        batch.draw(quarter, barCarPos.x, barCarPos.y, barSize/2, barSize/2, barSize, barSize, 1, 1, rotation + 180);

        batch.setColor(0,0,0, shade4);
        batch.draw(quarter, barCarPos.x, barCarPos.y, barSize/2, barSize/2, barSize, barSize, 1, 1, rotation + 270);

        batch.setColor(0.1f, 0.1f, 0.1f, 1f);
        batch.draw(square, barCarPos.x, barCarPos.y, barSize/2, barSize/2, barSize, barSize, 0.4f,  0.4f, rotation);

    }

    public void speedUp() {
        speed += 1 * size;
        centerFlashTimer = 0;
    }

    public void slowDown(){
        speed -= 100;
    }

    public DIRECTION getDirection() {
        return direction;
    }

    public void changeDirection(Track.DIRECTION directionWall) {

        if(state == STATE.REACHED_END){
            return;
        }
        if(startTimer > 1) {
            switch (directionWall) {
                case UP:
                    direction = DIRECTION.UP;
                    targetRot = 0;
                    break;
                case DOWN:
                    direction = DIRECTION.DOWN;
                    targetRot = 180;
                    break;
                case LEFT:
                    direction = DIRECTION.LEFT;
                    targetRot = 90;
                    break;
                case RIGHT:
                    direction = DIRECTION.RIGHT;
                    targetRot = 270;
            }

            turnComplete = false;
            centerTurned = false;
            middleTurned = false;
            centerFlashTimer = 0;
        }
    }

    public void jumpBack(int jump) {

        for (int i = 0; i < tail.size; i++) {
            tail.get(i).jumpBack(jump);
        }

        for (int i = 0; i < print.size; i++) {
            print.get(i).jumpBack(jump);

        }
    }

    public void grow(float delta) {
        size += delta * 100;
    }

    public void shrink(float delta) {
        size -= delta * 100;
    }


    public Vector2 getCenter() {
        return center;
    }

    public STATE getState() {
        return state;
    }

    public boolean isExploded() {
        if(state == STATE.HIT_WALL){
            return true;
        } else {
            return  false;
        }
    }

    public boolean isStart() {
        return start;
    }

    public void looseGrip(){
        grip -= 0.2f;
    }

    public void addGrip(){
        grip += 0.2f;
    }

    public Vector2 getCameraPos() {
        return cameraPos;
    }

    public void resetAll() {
        speed = 0;
        state = STATE.START;
    }
}
