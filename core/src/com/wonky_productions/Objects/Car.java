package com.wonky_productions.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.wonky_productions.Constants;
import com.wonky_productions.Screens.Level;

public class Car {

    float endTimer;
    Level level;
    Track track;
    TextureAtlas atlas;
    TextureRegion square, roundSquare, circle, octogon, quarter, squareOutline, crossQuarter, shadow;
    float speed, skidSpeedLimit, startSpeed, printTimer;
    int size;
    Array<Scrap> tail, explosion;
    Array<TrackRemains> print;
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

    float distTravelled;

    public float getDistTravelled() {
        return distTravelled;
    }


    enum STATE { START, NORMAL, HIT_WALL, REACHED_END}
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

    public Car(Level level, TextureAtlas atlas, int size, Color centerCol, Color ringCol, int startSpeed, float initialGrip) {
        this.level = level;
        this.atlas = atlas;
        this.size = size;
        square = atlas.findRegion("square");
        octogon = atlas.findRegion("pointer");
        roundSquare = atlas.findRegion("cross");
        circle = atlas.findRegion("circle");
        quarter = atlas.findRegion("TopTriangle");
        squareOutline = atlas.findRegion("squareOutline");
        crossQuarter = atlas.findRegion("crossFatSplit");
        shadow = atlas.findRegion("shadowSq");
        position = new Vector2(level.getStartPos());
        center = new Vector2(0,0);
        color = new Color(0, 0, 0,1);
        direction = DIRECTION.UP;
        speed = 0;
        this.startSpeed = startSpeed;
        tail = new Array<Scrap>();
        explosion = new Array<Scrap>();
        print = new Array<TrackRemains>();
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
        centerAlpha = 0.3f;
        centerFlashTimer = 0;
        endTimer = 0;

        centerColor = centerCol;
        ringBright = 0.2f;
        this.ringColor = ringCol;
        shade1 = 0;
        shade2 = 0;
        shade3 = 0;
        shade4 = 0;
        lightDir = 340;
        shadeMin = 0;
        shadeMax = 0.65f;
        grip = initialGrip;

        actualPos = new Vector2(Constants.SCREEN_WIDTH / 2 - size/2, Constants.SCREEN_HEIGHT/2 - size/2);
        cameraPos = new Vector2();

        distTravelled = 0;
        barSize = 70;
        barCarPos = new Vector2(60 - barSize/2, 0);
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

    public void setDistTravelled(float distTravelled) {
        this.distTravelled = distTravelled;
    }

    public void update(float delta){

        workTimers(delta);

        if(startTimer < 5) {
            startTimer += delta;
        }

        workShades();

        barCarPos.y = level.getBarCarY() - 40;

        skidRate = 1 - skidFactor * delta;


        if(track == null){
            track = level.getTrack();
        }

        if(state != STATE.HIT_WALL && Math.random() * delta * 60 > 250 / speed){
            tail.add(new Scrap(this, track, atlas, direction, color));
        }

        if(state == STATE.HIT_WALL){
            endTimer += delta;
            rotation = 0;
            targetRot = 0;
            if(endTimer < 0.2f) {
                shake(delta);
            }
        }

        if(state == STATE.REACHED_END){
            speed *= (1 - 3 * delta);
            if(speed < 10){

                speed = 0;
            }
            centerRot += delta * 200;
            System.out.println(distTravelled);
        }

        switch(direction) {
            case UP:
                velocity.y = speed;
                velocity.x *= skidRate;
                if(velocity.x > skidSpeedLimit){
                    turning = TURNING.ANTI;
                    centerTurning = TURNING.ANTI;
                } else if(velocity.x < -skidSpeedLimit){
                    turning = TURNING.CLOCK;
                    centerTurning = TURNING.CLOCK;
                } else  {
                    if(turnComplete) {
                        turning = TURNING.STILL;
                    }
                    if(centerTurned) {
                        centerTurning = TURNING.STILL;
                    }
                }
                break;
            case DOWN:
                velocity.y = -speed;
                velocity.x *= skidRate;

                if(velocity.x < -skidSpeedLimit){
                    turning = TURNING.ANTI;
                    centerTurning = TURNING.ANTI;
                } else if(velocity.x > skidSpeedLimit){
                    turning = TURNING.CLOCK;
                    centerTurning = TURNING.CLOCK;
                } else {
                    if(turnComplete) {
                        turning = TURNING.STILL;
                    }
                    if(centerTurned) {
                        centerTurning = TURNING.STILL;
                    }
                }
                break;
            case LEFT:
                velocity.x = -speed;
                velocity.y *= skidRate;

                if(velocity.y > skidSpeedLimit){
                    turning = TURNING.ANTI;
                    centerTurning = TURNING.ANTI;
                } else if(velocity.y < -skidSpeedLimit){
                    turning = TURNING.CLOCK;
                    centerTurning = TURNING.CLOCK;
                } else {
                    if(turnComplete) {
                        turning = TURNING.STILL;
                    }
                    if(centerTurned) {
                        centerTurning = TURNING.STILL;
                    }
                }
                break;
            case RIGHT:
                velocity.x = speed;
                velocity.y *= skidRate;

                if(velocity.y < -skidSpeedLimit){
                    turning = TURNING.ANTI;
                    centerTurning = TURNING.ANTI;
                } else if(velocity.y > skidSpeedLimit){
                    turning = TURNING.CLOCK;
                    centerTurning = TURNING.CLOCK;
                } else{
                    if(turnComplete) {
                        turning = TURNING.STILL;
                        centerTurning = TURNING.STILL;
                    }
                    if(centerTurned) {

                    }
                }

                break;
        }

        if(Math.abs(velocity.x) < 3){
            velocity.x = 0;
        }
        if(Math.abs(velocity.y) < 3){
            velocity.y = 0;
        }


        position.x += velocity.x * delta;
        position.y += velocity.y * delta;

        center.x = position.x + size/2;
        center.y = position.y + size/2;

        cameraPos.set(center.x - Constants.SCREEN_WIDTH/2, center.y - Constants.SCREEN_HEIGHT / 2);

        if(state != STATE.HIT_WALL && state != STATE.REACHED_END) {
            checkCollision();
        }

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

        if(state != STATE.REACHED_END) {
            sortTurning360(delta);
        }
        sortGrip();
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

        if(printTimer > 0.00f + 10 / speed && state != STATE.HIT_WALL){
            float opp = velocity.y;
            float hyp = (float) Math.sqrt((velocity.y * velocity.y) + (velocity.x * velocity.x));
            float prRot = (float) Math.acos(opp / hyp);
            if(velocity.x > 0 && velocity.y > 0){
                prRot = -prRot;
            } else if(velocity.x > 0 && velocity.y < 0){
                prRot = - prRot;
            }

            print.add(new TrackRemains(this, atlas.findRegion("fuzz"), center, Color.BLACK, prRot, (int)(size * 0.5f), size));
            printTimer = 0;
        }

        if(centerFlashTimer < 0.2 && centerAlpha < 0.9f){
            centerAlpha += delta * 3;
        } else if(centerFlashTimer < 0.4 && centerAlpha > 0.5f){
            centerAlpha -= delta * 3;
        }
    }

    public void shake(float delta){
        if(direction == DIRECTION.RIGHT) {
            if (centerFlashTimer < 0.04) {
                position.x += 400 * delta;
            } else if (centerFlashTimer < 0.08) {
                position.y += 300 * delta;
            } else if (centerFlashTimer < 0.12) {
                position.x -= 200 * delta;
            } else if (centerFlashTimer < 0.16) {
                position.y -= 100 * delta;
            } else {
                centerFlashTimer = 0;
            }
        } else if(direction == DIRECTION.LEFT) {
            if (centerFlashTimer < 0.04) {
                position.x -= 400 * delta;
            } else if (centerFlashTimer < 0.08) {
                position.y += 300 * delta;
            } else if (centerFlashTimer < 0.12) {
                position.x += 200 * delta;
            } else if (centerFlashTimer < 0.16) {
                position.y -= 100 * delta;
            } else {
                centerFlashTimer = 0;
            }
        } else if(direction == DIRECTION.UP){
            if (centerFlashTimer < 0.04) {
                position.y += 400 * delta;
            } else if (centerFlashTimer < 0.08) {
                position.x += 300 * delta;
            } else if (centerFlashTimer < 0.12) {
                position.y -= 200 * delta;
            } else if (centerFlashTimer < 0.16) {
                position.x -= 100 * delta;
            } else {
                centerFlashTimer = 0;
            }
        } else if(direction == DIRECTION.DOWN){
            if (centerFlashTimer < 0.04) {
                position.y -= 400 * delta;
            } else if (centerFlashTimer < 0.08) {
                position.x += 300 * delta;
            } else if (centerFlashTimer < 0.12) {
                position.y += 200 * delta;
            } else if (centerFlashTimer < 0.16) {
                position.x -= 100 * delta;
            } else {
                centerFlashTimer = 0;
            }
        }

    }

    private void sortGrip() {
        char c = track.getSquare(center);

        switch(c){
            case '1': skidFactor = 10 * grip;
                break;
            case '2': skidFactor = 8 * grip;
                break;
            case '3': skidFactor = 6 * grip;
                break;
            case '4': skidFactor = 5 * grip;
                break;
            case '5': skidFactor = 4 * grip;
                break;
            case '6': skidFactor = 3f * grip;
                break;
            case '7': skidFactor = 2.3f * grip;
                break;
            case '8': skidFactor = 1.7f * grip;
                break;
            case '9': skidFactor = 1f * grip;
                break;
            default: skidFactor = 10 * grip;
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

    public void sortTurning360(float delta){
        float q =  Constants.SQUARE_SIZE / 15.0f;
        refineRotations();
        if(!turnComplete){
            if(turning == TURNING.ANTI){
                rotation += 1.50f * delta * speed / q;

                if(rotation > targetRot && rotation - targetRot < 10){
                    rotation = targetRot;
                    turnComplete = true;
                    level.addCornerToCount();
                }

            } else if(turning == TURNING.CLOCK){
                rotation -= 1.50f * delta * speed / q;
                if(rotation < targetRot && targetRot - rotation < 10){
                    rotation = targetRot;
                    turnComplete = true;
                    level.addCornerToCount();
                }
            }
        } else {
            if(turning == TURNING.ANTI){
                rotation += 1.50f * delta * (10 - (rotation - targetRot) / q);
                if(rotation > targetRot + 10){
                    rotation = targetRot + 10;
                }
            } else if (turning == TURNING.CLOCK){
                rotation -= 1.50f * delta * (10 + (rotation - targetRot) / q);
                if(rotation < -10 + targetRot){
                    rotation = -10 + targetRot;
                }
            }
        }

        if(!centerTurned){
            if(centerTurning == TURNING.ANTI) {
                centerRot += 2.00f * delta * speed / q;
                if (centerRot > targetRot && centerRot - targetRot < 10) {
                    centerRot = targetRot;
                    centerTurned = true;
                }


            } else if (centerTurning == TURNING.CLOCK){
                centerRot -= 2.00 * delta * speed / q;
                if (centerRot < targetRot && targetRot - centerRot < 10) {
                    centerRot = targetRot;
                    centerTurned = true;
                }

            }
        } else {
            if(centerTurning == TURNING.ANTI){
                centerRot += 2.00f * delta * (6 - (centerRot - targetRot) / q);

                if(centerRot > 6 + targetRot){
                    centerRot = 6 + targetRot;
                }
            } else if (centerTurning == TURNING.CLOCK){
                centerRot -= 2.00f * delta * (6 + (centerRot - targetRot) / q);
                if(centerRot < -6 + targetRot){
                    centerRot = -6 + targetRot;
                }
            }
        }

        if(turning != TURNING.STILL && turnComplete){
            ringBright += delta / 3;
            if(ringBright > 1f){
                ringBright = 1f;
            }
        } else {
            ringBright -= delta/2;
            if(ringBright < 0.5f){
                ringBright = 0.5f;
            }
        }

        if(turning == TURNING.STILL) {
            rotation += (targetRot - rotation)/ 10.0f;
            if (rotation < 1 + targetRot && rotation > -1 + targetRot) {
                rotation = targetRot;
            }
        }

        if(centerTurning == TURNING.STILL){
            centerRot += (targetRot - centerRot) / 10.0f;
            if(targetRot != 0){
                if (centerRot < 1 + targetRot && centerRot > -1 + targetRot) {
                    centerRot = targetRot;
                }
            } else {
                if (centerRot < 1 || centerRot > 359) {
                    centerRot = targetRot;
                }
            }
        }
    }

    private void sortTurning(float delta) {


        if(!turnComplete){
            if(turning == TURNING.ANTI){
                rotation += 150 * delta * speed * skidRate * skidRate /(2 * size);
                if(rotation > 90){
                    rotation = 0;
                    turnComplete = true;
                    level.addCornerToCount();
                }

            } else if(turning == TURNING.CLOCK){
                rotation -= 150 * delta * speed * skidRate * skidRate / (2 * size);
                if(rotation < -90){
                    rotation = 0;
                    turnComplete = true;
                    level.addCornerToCount();
                }

            }
        } else {
            if(turning == TURNING.ANTI){
                rotation += 150 * delta * (10 - rotation) * speed * skidRate * skidRate / (20 * size);

                if(rotation > 10){
                    rotation = 10;
                }
            } else if (turning == TURNING.CLOCK){
                rotation -= 150 * delta * (10 + rotation) * speed * skidRate * skidRate / (20 * size);
                if(rotation < -10){
                    rotation = -10;
                }
            }
        }

        if(!centerTurned){
            if(turning == TURNING.ANTI) {
                centerRot += 150 * delta * speed * skidRate * skidRate / (1.4f * size);
                if (centerRot > 90) {
                    centerRot = 0;
                    centerTurned = true;
                }
            } else if (turning == TURNING.CLOCK){
                centerRot -= 150 * delta * speed * skidRate * skidRate / (1.4f * size);
                if(centerRot < -90){
                    centerRot = 0;
                    centerTurned = true;
                }
            }
        } else {
            if(turning == TURNING.ANTI){
                centerRot += 150 * delta * (6 - centerRot) * speed * skidRate * skidRate / (14 * size);

                if(centerRot > 6){
                    centerRot = 6;
                }
            } else if (turning == TURNING.CLOCK){
                centerRot -= 150 * delta * (6 + centerRot) * speed * skidRate * skidRate / (14 * size);
                if(centerRot < -6){
                    centerRot = -6;
                }
            }
        }


        if(turning != TURNING.STILL){
            ringBright += delta / 3;
            if(ringBright > 0.4f){
                ringBright = 0.4f;
            }
        } else {
            ringBright -= delta / 5;
            if(ringBright < 0.24f){
                ringBright = 0.24f;
            }
        }


        if(turning == TURNING.STILL){
            rotation *= skidRate * skidRate;
            if(rotation < 1 &&  rotation > - 1){
                rotation = 0;
            }
            centerRot *= skidRate * skidRate;
            if(centerRot < 1 &&  centerRot > - 1){
                centerRot = 0;
            }
        }
    }

    public void reachedEnd() {
        state = STATE.REACHED_END;
    }

    public void reset() {

        position.set(level.getStartPos());
        turnComplete = false;
        middleTurned = false;
        centerTurned = false;
        centerFlashTimer = 1;

        direction = startDir;
        if(startDir == DIRECTION.UP){
            rotation = 0;
            targetRot = 0;
            centerRot = 0;
        } else if(startDir == DIRECTION.DOWN){
            rotation = 180;
            targetRot = 180;
            centerRot = 180;
        } else if(startDir == DIRECTION.LEFT){
            rotation = 90;
            targetRot = 90;
            centerRot = 90;
        } else if(startDir == DIRECTION.RIGHT){
            rotation = 270;
            targetRot = 270;
            centerRot = 270;
        }
        turning = TURNING.STILL;
        centerTurning = TURNING.STILL;

        state = STATE.NORMAL;
        speed = startSpeed;
        startTimer = 0;

        explosion.clear();
        tail.clear();
        print.clear();
        endTimer = 0;
        distTravelled = 0;
    }

    private void checkCollision() {

        float r = 0;

        if(rotation >= 0 && rotation < 90){
            r = rotation;
        } else if (rotation >= 90 && rotation < 180){
            r = rotation - 90;
        } else if (rotation >= 180 && rotation < 270){
            r = rotation - 180;
        } else if (rotation >= 270 && rotation < 360){
            r = rotation - 270;
        } else if (rotation >= -90 && rotation < 0){
            r = rotation + 90;
        } else if (rotation >= -180 && rotation < -90){
            r = rotation + 180;
        }

        float a = ((45 - Math.abs(45 - r)) / 45.0f) * size * 0.14f;
        float b = size * r / 90.0f;

        if(track.checkHitWall(position.x + b, position.y - a) || track.checkHitWall(position.x + size + a, position.y + b)
            || track.checkHitWall(position.x - a, position.y + size - b) || track.checkHitWall(position.x + size - b, position.y + size + a)

            || track.checkHitWall(position.x + size * 0.2f, position.y + size * 0.5f) || track.checkHitWall(position.x + size * 0.5f, position.y - 0.2f * size)
                || track.checkHitWall(position.x + 0.5f * size, position.y + size * 0.8f) || track.checkHitWall(position.x + 0.8f * size, position.y + 0.5f * size)){
            state = STATE.HIT_WALL;
            level.carHitWall();
        }

        if(state == STATE.HIT_WALL){
            createExplosion();
        }
    }

    private void createExplosion() {

        for (int i = 0; i < 20; i++) {
            explosion.add(new ExplosionScrap(this, track, atlas, DIRECTION.RIGHT, color, size));
        }
        speed = 0;
        level.writeData2();
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

    public float getStartTimer() {
        return startTimer;
    }

    public void slowDown(){
        speed -= 100;
    }

    public DIRECTION getDirection() {
        return direction;
    }

    public void changeDirection(int i) {

        if(state == STATE.REACHED_END){
            return;
        }

        switch (i) {
            case 1:
                direction = DIRECTION.UP;
                targetRot = 0;
                break;
            case 3:
                direction = DIRECTION.DOWN;
                targetRot = 180;
                break;
            case 4:
                direction = DIRECTION.LEFT;
                targetRot = 90;
                break;
            case 2:
                direction = DIRECTION.RIGHT;
                targetRot = 270;
        }

        turnComplete = false;
        centerTurned = false;
        middleTurned = false;
        centerFlashTimer = 0;

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
