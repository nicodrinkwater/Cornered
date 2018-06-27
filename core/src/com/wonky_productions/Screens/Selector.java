package com.wonky_productions.Screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.wonky_productions.Constants;
import com.wonky_productions.Objects.*;
import com.wonky_productions.Touch;

public class Selector extends Page {

    Constants.TRACK_TYPE trackType;
    Array<LevelData> datas;
    MultiTrack track;
    TrackCreater creater;
    MultiBackground trackBacks;
    SelectCar car;
    int trackSize, trackGap;
    float track1pos, origTrackPos;
    Vector2 origCarPos;
    Rectangle currentRect;
    float swipeExtra;
    float leftalpha, rightAlpha;
    boolean currentChoosable;

    TextureRegion frameTop, frameSide, frameTL, frameTR, frameBL, frameBR, tile, shadowBR, shadowTL, graph, graphBar, barBar, arrow, padlock;
    int frameWidth, frameHeight, frameOffGround, range, firstX, tileSize, frameGap;
    int limitLeft, limitRight, currentCenter;
    private Vector2 carPos2;
    protected boolean swiping;
    GlyphLayout titleLayout;
    String numberGames, numberTotal, percent;
    int menuType;
    boolean currentOpening;
    float openingTimer;

    public Vector2 getCarPos2() {
        return carPos2;
    }

    enum STATE {SELECTABLE, SWIPING_LEFT, SWIPING_RIGHT, SCROLLING, OTHER, ENDING}
    STATE state;
    float glow, speed;
    float endTimer;
    Color distCol, mphColor, gripColor, targetTitleColor, actualTitleColor, frameColor, backTileColor, frontTileColor;
    Color titleFrameColor, actualTitleFrameColor, writingColor;

    float actualDist, targetDist, actualGrip, targetGrip, actualSpeed, targetSpeed;
    Rectangle leftRect, rightRect;
    boolean leftTouched, rightTouched;
    float touchTimer;

    public Selector(Master master, AssetManager manager, Touch touch, START_POS startPos, EXIT_POS exitPos, Color background) {
        super(master, manager, touch, startPos, exitPos, background);

        atlas = manager.get("Grid.pack", TextureAtlas.class);
        square = atlas.findRegion("square");

        frameTop = atlas.findRegion("wallShadowTop");
        frameSide = atlas.findRegion("wallShadowSide");
        frameBL = atlas.findRegion("wallShadowCornerBL");
        frameBR = atlas.findRegion("wallShadowCornerBR");
        frameTL = atlas.findRegion("wallShadowCornerTL");
        frameTR = atlas.findRegion("wallShadowCornerTR");
        tile = atlas.findRegion("TrackTileFlatLight2");
        backTile = atlas.findRegion("TrackTileDark");
        shadowBR =atlas.findRegion("halfShadow2");
        shadowTL =atlas.findRegion("halfShadow");
        graph = atlas.findRegion("graph");
        graphBar = atlas.findRegion("barSide");
        barBar = atlas.findRegion("BarBar");
        arrow = atlas.findRegion("starTile");
        padlock = atlas.findRegion("Lock");

        datas = new Array<LevelData>();

        creater = new TrackCreater("");

        rightRect = new Rectangle(scrWidth - 150, scrHeight/2  - 75, 150, 150);
        leftRect  = new Rectangle(0, scrHeight/2  - 75, 150, 150);

        titleLayout = new GlyphLayout();
        leftalpha = 0;
        rightAlpha = 1;
        touchTimer = 0;

        openingTimer = 1;
    }

    public void createMe(String name){
        sortData(name);
        sortFrameSizing();

        carPos2 = new Vector2();

        trackSize = 50;
        track = new MultiTrack(this, atlas, creater.getLayout(), 400, 50, 50, datas);
        trackBacks = new MultiBackground(this, datas, atlas, trackSize);
        car = new SelectCar(this, datas, track, atlas, (int) (2.3f * trackSize));

        distCol = new Color(0,0,0.2f,1);
        gripColor = new Color(0,0,0.2f,1);
        mphColor = new Color(0,0,0.2f,1);

        writingColor = new Color();

        targetTitleColor = new Color(datas.get(0).getWallFlashCol());
        actualTitleColor = new Color(targetTitleColor);

        titleFrameColor = new Color(datas.get(0).getCar1());
        actualTitleFrameColor = new Color(titleFrameColor);

        carPos.x = 70 * 70;

        numberGames = Integer.toString(datas.get(0).getNumberGames());
        numberTotal = Integer.toString(datas.get(0).getNumberTotal());
        percent = Integer.toString(datas.get(0).getPercent());
    }

    public void sortData(String type){

        datas.clear();
        if(type == "Menu"){
            datas.add(new LevelData("EASY"));

            datas.add(new LevelData("TRICKY"));
            datas.add(new LevelData("FAST"));
            datas.add(new LevelData("FAST"));

            frameColor = new Color(0.3f, 0.3f, 0.3f, 1);
            backTileColor = new Color(0.1f, 0.1f, 0.1f, 1);
            frontTileColor = new Color(0.2f, 0.2f, 0.2f, 1);

        } else if(type == "EASY"){
            datas.add(new LevelData("ONE"));
            datas.add(new LevelData("TWO"));
            datas.add(new LevelData("THREE"));
            datas.add(new LevelData("FOUR"));
            datas.add(new LevelData("FIVE"));
            datas.add(new LevelData("SIX"));
            datas.add(new LevelData("SEVEN"));
            datas.add(new LevelData("EIGHT"));
            datas.add(new LevelData("NINE"));
            datas.add(new LevelData("TEN"));
            frameColor = new Color(0.3f, 0.3f, 0.3f, 1);
            backTileColor = new Color(0.1f, 0.1f, 0.1f, 1);
            frontTileColor = new Color(0.2f, 0.2f, 0.2f, 1);
            menuType = 1;
        } else if(type == "TRICKY"){
            datas.add(new LevelData("REDWOOD"));
            datas.add(new LevelData("SNOW"));
            datas.add(new LevelData("THREE"));
            datas.add(new LevelData("FOUR"));
            datas.add(new LevelData("FIVE"));
            frameColor = new Color(0.3f, 0.3f, 0.3f, 1);
            backTileColor = new Color(0.1f, 0.1f, 0.1f, 1);
            frontTileColor = new Color(0.2f, 0.2f, 0.2f, 1);
            menuType = 2;
        } else if(type == "Fast"){
            datas.add(new LevelData("ONE"));
            datas.add(new LevelData("TWO"));
            datas.add(new LevelData("THREE"));
            frameColor = new Color(0.3f, 0.3f, 0.3f, 1);
            backTileColor = new Color(0.1f, 0.1f, 0.1f, 1);
            frontTileColor = new Color(0.2f, 0.2f, 0.2f, 1);
            menuType = 3;
        }

        closeFiles();
    }

    protected void sortFrameSizing() {
        int z = 0;
        trackGap = 1700;
        track1pos = z;
        origTrackPos = z;
        origCarPos = new Vector2(carPos);

        limitLeft = z;
        limitRight = z - (datas.size - 1) * trackGap;
        currentCenter = 0;
        glow = 0;
        state = STATE.OTHER;

        speed = datas.get(0).getStartSpeed();

        actualDist = 0;
        actualGrip = 0;
        actualSpeed = 0;

        tileSize = 50;
        range = scrWidth / tileSize;
        frameWidth = 1450;
        int n = scrHeight / 100;
        frameHeight = (n  + 1) * 50 ;
        frameOffGround = 50 * (n / 2);
        frameGap = 300;
        //System.out.println("hEIGHT = " + frameHeight + ", offG = " + frameOffGround + ",  scrHeight = " + scrHeight);

         currentRect =new Rectangle(track1pos + 270, frameOffGround + 50 , frameWidth - 100, frameHeight - 100);
    }

    @Override
    protected void update(float delta) {
        super.update(delta);

        if(pageState == PAGE_STATE.ON){
            trackBacks.setRangeExtra(3);
        }


        if(touch.getState(this) == Touch.STATE.RELEASED && state == STATE.SELECTABLE && currentRect.contains(touch.getTouchPos()) && currentChoosable){
            state = STATE.ENDING;
            car.eplode();
        }

        if(state == STATE.ENDING){
            endTimer += delta;
            if(endTimer < 0.1f){
                car.shake(delta);
            }

            if(endTimer > 1){
                goToNextPage();
            }
        }

        leftTouched = false;
        rightTouched = false;

        if(touch.getState(this) == Touch.STATE.NOT && state != STATE.ENDING){
            state = STATE.OTHER;
        }

        if(state != STATE.ENDING) {
            workTitleColors(delta);
            checkArrows(delta);
            goToNearestCenter(delta);
            checkTouch();
            workGraph(delta);
            sortCarColor();
        }

        if(state == STATE.ENDING || pageState == PAGE_STATE.GOING){
            speed = 0;
            track.setRangeExtra(1);
        }

        carPos.x += speed * delta;
        origCarPos.x += speed * delta;
        carPos2.x += speed * delta;
        if(carPos.x > 150 * 70 && pageState == PAGE_STATE.ON && !car.isExploded()) {
            carPos.x -= 96 * 70;
            origCarPos.x -= 96 * 70;
            car.jumpBack(96 * 70);
        }

        track.update(delta);
        trackBacks.update(delta);
        car.update(delta);

    }

    protected void checkArrows(float delta) {


        if(currentCenter > 0){
            leftalpha += delta * 2;
        } else {
            leftalpha -= delta * 2;
        }

        if(currentCenter < datas.size - 1){
            rightAlpha += delta * 2;
        } else {
            rightAlpha -= delta * 2;
        }

        leftalpha = MathUtils.clamp(leftalpha, 0, 1);
        rightAlpha = MathUtils.clamp(rightAlpha, 0, 1);

        if(leftRect.contains(touch.getTouchPos()) && track1pos < origTrackPos){
            if(touch.getState(this) == Touch.STATE.RELEASED) {
                if(touchTimer < 0.3f && currentCenter + swipeExtra > 0) {
                    swipeExtra--;
                }
                touchTimer = 0;
            } else {
                if(touch.getState(this) == Touch.STATE.TOUCHING){
                    leftTouched = true;
                    track1pos += delta * 2000;
                    touchTimer += delta;
                    track.setRangeExtra(4);
                }
            }
        } else if(rightRect.contains(touch.getTouchPos()) && track1pos > origTrackPos - (trackGap * (datas.size - 1))){
            if(touch.getState(this) == Touch.STATE.RELEASED) {
                if(currentCenter + swipeExtra < datas.size - 1 && touchTimer < 0.3f) {
                    swipeExtra++;
                }
                touchTimer = 0;
            } else {
                if(touch.getState(this) == Touch.STATE.TOUCHING){
                    rightTouched = true;
                    track1pos -= delta * 2000;
                    touchTimer += delta;
                    track.setRangeExtra(4);
                }
            }
        }

    }

    protected void checkTouch() {
        if(touch.getState(this) == Touch.STATE.RELEASED || touch.getState(this) == Touch.STATE.NOT){
            swiping = false;
        }
        if(touch.getState(this) == Touch.STATE.DRAG){
            track1pos += 1.7f * touch.getDragX();
            track1pos = MathUtils.clamp(track1pos,  limitRight, limitLeft);
        } else if(touch.getState(this) == Touch.STATE.SWIPE && !swiping){
            if(state != STATE.ENDING){
                if(touch.getDirection() == Constants.DIRECTION.LEFT){
                    swipeExtra += 3;
                    swiping = true;
                } else if(touch.getDirection() == Constants.DIRECTION.RIGHT) {
                    swipeExtra -= 3;
                    swiping = true;
                }
            }
        }
    }


    protected void goToNextPage() {
        pageState = PAGE_STATE.GOING;
        trackBacks.setRangeExtra(3);
        track.setRangeExtra(1);
        master.level.createLevel(datas.get(currentCenter));
        master.level.pageState = PAGE_STATE.ON;
        endTimer = 0;
        state = STATE.OTHER;
    }

    private void workTitleColors(float delta) {
        actualTitleColor.r += (targetTitleColor.r - actualTitleColor.r) * delta * 4;
        actualTitleColor.g += (targetTitleColor.g - actualTitleColor.g) * delta * 4;
        actualTitleColor.b += (targetTitleColor.b - actualTitleColor.b) * delta * 4;
        actualTitleColor.a = 0.8f;

        actualTitleFrameColor.r += (titleFrameColor.r - actualTitleFrameColor.r) * delta * 4;
        actualTitleFrameColor.g += (titleFrameColor.g - actualTitleFrameColor.g) * delta * 4;
        actualTitleFrameColor.b += (titleFrameColor.b - actualTitleFrameColor.b) * delta * 4;
        actualTitleFrameColor.a = 0.8f;
    }

    protected void workGraph(float delta) {

        targetDist = datas.get(currentCenter).getLengthRating() * 250;
        targetSpeed = datas.get(currentCenter).getSpeedRating() * 250;
        targetGrip = datas.get(currentCenter).getGripRating() * 250;

        if(actualGrip > targetGrip + 1) {
            actualGrip += (targetGrip - actualGrip) * delta * 4 - 3;
        } else if(actualGrip < targetGrip - 1){
            actualGrip += (targetGrip - actualGrip) * delta * 4 + 3;
        }
        if(actualSpeed > targetSpeed + 1) {
            actualSpeed += (targetSpeed - actualSpeed) * delta * 4 - 3;
        } else if(actualSpeed < targetSpeed - 1){
            actualSpeed += (targetSpeed - actualSpeed) * delta * 4 + 3;
        }

        if(actualDist > targetDist + 1) {
            actualDist += (targetDist - actualDist) * delta * 4 - 3;
        } else if(actualDist < targetDist - 1){
            actualDist += (targetDist - actualDist) * delta * 4 + 3;
        }

        if(Math.abs(actualDist - targetDist) < 1){
            actualDist = targetDist;
        }

        if(Math.abs(actualSpeed - targetSpeed) < 1){
            actualSpeed = targetSpeed;
        }

        if(Math.abs(actualGrip - targetGrip) < 1){
            actualGrip = targetGrip;
        }
    }

    private void goToNearestCenter(float delta) {


        for (int i = 0; i < datas.size; i++) {
            if(track1pos > origTrackPos - trackGap * (i + 0.5f) && track1pos < origTrackPos - trackGap * (i - 0.5f)){
                if(i != currentCenter){
                    targetTitleColor.set(datas.get(i).getWallFlashCol());
                    numberGames = Integer.toString(datas.get(i).getNumberGames());
                    numberTotal = Integer.toString(datas.get(i).getNumberTotal());
                    percent = Integer.toString(datas.get(i).getPercent());

                    if(swipeExtra > 0){
                        swipeExtra --;
                    } else if(swipeExtra < 0){
                        swipeExtra ++;
                    }
                }
                currentCenter = i;
            }
        }

        if(state == STATE.OTHER) {
            if(track1pos > origTrackPos - trackGap * (currentCenter + swipeExtra) + 3) {
                track1pos += (origTrackPos - trackGap * (currentCenter + swipeExtra) - track1pos - 30) * 3 * delta;
            } else if(track1pos < origTrackPos - trackGap * (currentCenter + swipeExtra) - 3){
                track1pos += (origTrackPos - trackGap * (currentCenter + swipeExtra) - track1pos + 30) * 3 * delta;
            }
        }

        track1pos = MathUtils.clamp(track1pos, origTrackPos - (datas.size - 1) * trackGap, origTrackPos);

        glow = 1 - 2 * Math.abs(origTrackPos - trackGap * currentCenter - track1pos) / trackGap;
        glow = MathUtils.clamp(glow, 0, 1);

        if (track1pos > origTrackPos - trackGap * currentCenter - 3 && track1pos < origTrackPos - trackGap * currentCenter + 32) {
            //track1pos = origTrackPos - trackGap * currentCenter;
            state = STATE.SELECTABLE;
        }
        speed = datas.get(currentCenter).getStartSpeed() * 0.75f;
    }

    private void sortCarColor() {
        for (int i = 0; i < datas.size; i++) {
            if(scrWidth/2 > track1pos + i * trackGap && scrWidth/ 2 < track1pos + (i + 1) * trackGap){
                car.sortColors(datas.get(i));
            }
        }
    }

    @Override
    protected void draw(SpriteBatch batch) {

        trackBacks.draw(batch);
        track.draw(batch);
        track.drawWall( batch);
        car.draw(batch);
        batch.setColor(backTileColor);
        for (int i = 0; i < scrWidth / 50 + 1; i++) {
            batch.draw(backTile, i * 50 + movement.x, 0, 50, 50);
            batch.draw(backTile, i * 50 + movement.x, 50, 50, 50);
            batch.draw(backTile, i * 50 + movement.x, 100, 50, 50);
            batch.draw(backTile, i * 50 + movement.x, 150, 50, 50);
            batch.draw(backTile, i * 50 + movement.x, scrHeight - 50, 50, 50);
            batch.draw(backTile, i * 50 + movement.x, scrHeight - 100, 50, 50);
            batch.draw(backTile, i * 50 + movement.x, scrHeight - 150, 50, 50);
            batch.draw(backTile, i * 50 + movement.x, scrHeight - 200, 50, 50);
        }
        drawBorders(batch);
        drawGraphs(batch);
        drawTitle(batch);

        drawArrows(batch);

        drawLastBar(batch);
    }

    protected void drawArrows(SpriteBatch batch) {

        float a = 0;
        if(leftalpha > 0){
            batch.setColor(0,0,0,0.16f * leftalpha);
            batch.draw(square, movement.x,  scrHeight/2 - 75, 150, 150);
            batch.draw(square, movement.x,  scrHeight/2 - 65, 140, 130);

            a = 0.4f;
            if(leftTouched){
                a = 0.9f;
            }
            batch.setColor(actualTitleFrameColor.r * a, actualTitleFrameColor.g * a, actualTitleFrameColor.b * a, 1 * leftalpha);
            batch.draw(arrow, movement.x, scrHeight/2 - 75, 75, 75, 150, 150, 0.8f, 0.8f, 0);
        }

        if(rightAlpha > 0) {
            batch.setColor(0, 0, 0, 0.16f * rightAlpha);
            batch.draw(square, scrWidth - 150 + movement.x, scrHeight / 2 - 75, 150, 150);
            batch.draw(square, scrWidth - 140 + movement.x, scrHeight / 2 - 65, 140, 130);

            if (rightTouched) {
                a = 0.8f;
            } else {
                a = 0.4f;
            }
            batch.setColor(actualTitleFrameColor.r * a, actualTitleFrameColor.g * a, actualTitleFrameColor.b * a, 1 * rightAlpha);
            batch.draw(arrow, scrWidth - 150 + movement.x, scrHeight / 2 - 75, 75, 75, 150, 150, 0.8f, 0.8f, 180);
        }
    }

    protected void drawLastBar(SpriteBatch batch){
        batch.setColor(0,0,0,1);
        batch.draw(square, scrWidth + movement.x, 0, 78, scrHeight);
    }

    protected void drawBorders(SpriteBatch batch) {

        firstX = (int) track1pos - 42;
        batch.setColor(frontTileColor);

        for (int i = - (firstX + 600) / tileSize; i < (scrWidth - firstX + 600) / tileSize + 1 ; i++) {
            for (int j = 0; j < 2; j++) {
                batch.draw(tile, i * tileSize + movement.x + firstX - 600,frameOffGround - (j + 1) * tileSize, tileSize, tileSize);

            }
            for (int j = 0; j < 2; j++) {
                batch.draw(tile, i * tileSize + movement.x + firstX - 600, j * tileSize + frameOffGround + frameHeight, tileSize, tileSize);
            }
        }

        for (int i = 0; i < datas.size + 1; i++) {
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < frameHeight / tileSize; k++) {
                    if( i * trackGap + j * tileSize + firstX < scrWidth) {
                        batch.draw(tile, i * trackGap + j * tileSize + movement.x + firstX, frameOffGround + k * tileSize, tileSize, tileSize);
                    }
                }
            }
        }

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < frameHeight/ tileSize; j++) {
                batch.draw(tile, i * tileSize + movement.x + firstX - 600, j * tileSize + frameOffGround, tileSize, tileSize);
            }
        }

        for (int i = 0; i < (scrWidth - firstX + trackGap * datas.size) / tileSize; i++) {
            for (int j = - 2; j < frameHeight / tileSize + 2; j++) {
                if( i * tileSize + firstX + trackGap * datas.size < scrWidth) {
                    batch.draw(tile, i * tileSize + movement.x + firstX + trackGap * datas.size, frameOffGround + j * tileSize, tileSize, tileSize);
                }
            }
        }
        drawFrames(batch);
    }

    protected void drawFrames(SpriteBatch batch) {
        if(pageState != PAGE_STATE.GOING && pageState != PAGE_STATE.COMING) {
            for (int i = 0; i < datas.size; i++) {
                if (i * trackGap + firstX < 1700) {
                    if (i == currentCenter) {
                        batch.setColor(frameColor.r + 0.16f * glow, frameColor.g + 0.16f * glow, frameColor.b + 0.16f * glow, 1);
                    } else {
                        batch.setColor(frameColor);
                    }
                    batch.draw(frameSide, track1pos + i * trackGap - 60 + movement.x + frameGap, frameOffGround, 40, frameHeight);
                    batch.draw(frameSide, track1pos + (i + 1) * trackGap - 60 + movement.x, frameOffGround, 40, frameHeight);
                    batch.draw(frameTop, track1pos + i * trackGap - 20 + movement.x + frameGap, frameOffGround, trackGap - frameGap - 40, 40);
                    batch.draw(frameTop, track1pos + i * trackGap - 20 + movement.x + frameGap, frameOffGround + frameHeight, trackGap - frameGap - 40, 40);
                    batch.draw(frameTL, track1pos + i * trackGap - 60 + movement.x + frameGap, frameOffGround + frameHeight, 40, 40);
                    batch.draw(frameBL, track1pos + i * trackGap - 60 + movement.x + frameGap, frameOffGround, 40, 40);
                    batch.draw(frameBR, track1pos + i * trackGap - 60 + movement.x + trackGap, frameOffGround, 40, 40);
                    batch.draw(frameTR, track1pos + i * trackGap - 60 + movement.x + trackGap, frameOffGround + frameHeight, 40, 40);

                    batch.setColor(0, 0, 0, 0.3f);
                    batch.draw(square, track1pos + i * trackGap - 70 + movement.x + frameGap, frameOffGround - 20, trackGap - frameGap + 40, 20);
                    batch.draw(square, track1pos + i * trackGap - 70 + movement.x + frameGap, frameOffGround, 10, frameHeight + 10);
                    batch.draw(shadowBR, track1pos + i * trackGap - 70 + movement.x + frameGap, frameOffGround + frameHeight + 10, 10, 30);
                    batch.draw(shadowTL, track1pos + i * trackGap + movement.x + trackGap - 30, frameOffGround - 20, 10, 20);
                }
            }
        } else {
            int i = currentCenter;
            batch.setColor(frameColor.r + 0.16f * glow, frameColor.g + 0.16f * glow, frameColor.b + 0.16f * glow, 1);
            batch.draw(frameSide, track1pos + i * trackGap - 60 + movement.x + frameGap, frameOffGround, 40, frameHeight);
            batch.draw(frameSide, track1pos + (i + 1) * trackGap - 60 + movement.x, frameOffGround, 40, frameHeight);
            batch.draw(frameTop, track1pos + i * trackGap - 20 + movement.x + frameGap, frameOffGround, trackGap - frameGap - 40, 40);
            batch.draw(frameTop, track1pos + i * trackGap - 20 + movement.x + frameGap, frameOffGround + frameHeight, trackGap - frameGap - 40, 40);
            batch.draw(frameTL, track1pos + i * trackGap - 60 + movement.x + frameGap, frameOffGround + frameHeight, 40, 40);
            batch.draw(frameBL, track1pos + i * trackGap - 60 + movement.x + frameGap, frameOffGround, 40, 40);
            batch.draw(frameBR, track1pos + i * trackGap - 60 + movement.x + trackGap, frameOffGround, 40, 40);
            batch.draw(frameTR, track1pos + i * trackGap - 60 + movement.x + trackGap, frameOffGround + frameHeight, 40, 40);

            batch.setColor(0, 0, 0, 0.3f);
            batch.draw(square, track1pos + i * trackGap - 70 + movement.x + frameGap, frameOffGround - 20, trackGap - frameGap + 40, 20);
            batch.draw(square, track1pos + i * trackGap - 70 + movement.x + frameGap, frameOffGround, 10, frameHeight + 10);
            batch.draw(shadowBR, track1pos + i * trackGap - 70 + movement.x + frameGap, frameOffGround + frameHeight + 10, 10, 30);
            batch.draw(shadowTL, track1pos + i * trackGap + movement.x + trackGap - 30, frameOffGround - 20, 10, 20);
        }
        batch.setColor(frameColor);
        batch.draw(frameTop, movement.x, frameOffGround - 2 * tileSize - 40, scrWidth, 40);
        batch.draw(frameTop, movement.x, frameOffGround + frameHeight + 2 * tileSize, scrWidth, 40);
        batch.setColor(0, 0, 0, 0.3f);
        batch.draw(frameTop, movement.x, frameOffGround + frameHeight + 2 * tileSize - 20, scrWidth, 20);
        batch.setColor(0, 0, 0, 0.3f);
        batch.draw(frameTop, movement.x, frameOffGround - 2 * tileSize - 80, scrWidth, 40);
    }

    public void drawTitle(SpriteBatch batch){

        int titleWidth = 700;
        int titleHeight = 240;




        targetTitleColor.set(datas.get(currentCenter).getWallFlashCol());
        titleFrameColor.set(datas.get(currentCenter).getCar1());
        batch.setColor(0,0,0, 0.4f);

        float d = scrWidth/2 - titleWidth/2 + movement.x;
        float h = frameOffGround + frameHeight - 50;

        batch.draw(square, d, h, titleWidth, titleHeight);
        batch.draw(square, d + 10, h + 10, titleWidth - 20, titleHeight - 20);

        batch.setColor(actualTitleColor.r/2, actualTitleColor.g/2, actualTitleColor.b/2, 1f);

        batch.draw(square, d + 20, h + 20, titleWidth - 40, titleHeight - 40);

        batch.setColor(0,0,0,1f);
        batch.draw(square, d + 26, h + 26, titleWidth - 52, titleHeight - 52);

        titleLayout.setText(fLarge,datas.get(currentCenter).getName());

       /* fLarge.setColor(0.3f,0.3f,0.3f, 0.6f * glow);
        fLarge.draw(batch, datas.get(currentCenter).getName(), d + titleWidth/2 - titleLayout.width/2 - 4, h + 170 - 8);
*/
        fLarge.setColor(actualTitleFrameColor.r, actualTitleFrameColor.g, actualTitleFrameColor.b, 1 * glow);
        fLarge.draw(batch, datas.get(currentCenter).getName(), d + titleWidth/2 - titleLayout.width/2 + 6, h + 148);
    }


    public void drawGraphs(SpriteBatch batch) {

        batch.setColor(0f, 0f, 0f, 0.4f);

        float d = origTrackPos + 0.1f * trackGap - 75 + movement.x + frameGap;
        float s = origTrackPos + 0.2f * trackGap - 75 + movement.x + frameGap;
        float g = origTrackPos + 0.3f * trackGap - 75 + movement.x + frameGap;
        float q = origTrackPos + 0.4f * trackGap - 75 + movement.x + frameGap;

        int n = 20;

        batch.draw(square, d + 10, 20, 100, 340);
        batch.draw(square, s + 10, 20, 100, 340);
        batch.draw(square, g + 10, 20, 100, 340);

        batch.draw(square, q + 10, n, 600, 200);

        batch.setColor(actualTitleColor.r / 2, actualTitleColor.g / 2, actualTitleColor.b / 2, 1f);
        batch.draw(square, d + 20, 30, 80, 280);
        batch.draw(square, s + 20, 30, 80, 280);
        batch.draw(square, g + 20, 30, 80, 280);

        batch.draw(square, q + 20, n + 10, 580, 180);

        batch.setColor(0f, 0f, 0f, 1f);

        batch.draw(square, d + 26, 36, 68, 268);
        batch.draw(square, s + 26, 36, 68, 268);
        batch.draw(square, g + 26, 36, 68, 268);

      /*  batch.draw(square, q + 26, 76, 568, 84);
        batch.draw(square, q + 26, 167, 568, 84);
        batch.draw(square, q + 26, 258, 568, 85);*/

        batch.draw(square, q + 26, n + 16, 568, 168);

        workGraphColors();

        batch.setColor(distCol);
        batch.draw(graphBar, d + 30, 40, 60, actualDist);

        batch.setColor(mphColor);
        batch.draw(graphBar, s + 30, 40, 60, actualSpeed);

        batch.setColor(gripColor);
        batch.draw(graphBar, g + 30, 40, 60, actualGrip);

        batch.setColor(distCol.r / 2, distCol.g / 2, distCol.b / 2, 1f);
        batch.draw(square, d + 30, 40 + actualDist, 60, 5);
        batch.setColor(mphColor.r / 2, mphColor.g / 2, mphColor.b / 2, 1f);
        batch.draw(square, s + 30, 40 + actualSpeed, 60, 5);
        batch.setColor(gripColor.r / 2, gripColor.g / 2, gripColor.b / 2, 1f);
        batch.draw(square, g + 30, 40 + actualGrip, 60, 5);

        writingColor.set(datas.get(currentCenter).getCarCross());

        fSmall.setColor(writingColor.r, writingColor.g, writingColor.b, 0.4f + 0.4f * glow);
        fSmall.draw(batch, "DIST", d + 26, 345);
        fSmall.draw(batch, "MPH", s + 28, 345);
        fSmall.draw(batch, "GRIP", g + 26, 345);

        fSmall.draw(batch, "ATTEMPTS", q + 70, n + 104 + 54);
        fSmall.draw(batch, "TOTAL ATTEMPTS", q + 70, n + 53 + 54);
        fSmall.draw(batch, "PERCENTAGE COMPLETE", q + 70, n + 5 + 54);


        fMed.setColor(0.3f, 0.3f, 0.3f, 0.6f * glow);

       /* titleLayout.setText(fMed, numberGames);
        fMed.draw(batch, numberGames, q + 600 - 60 - titleLayout.width -2, n + 109 + 60 - 4);

        titleLayout.setText(fMed, numberTotal);
        fMed.draw(batch, numberTotal, q + 600 - 60 - titleLayout.width - 2, n + 57 + 60 - 4);


        titleLayout.setText(fMed, percent);
        fMed.draw(batch, percent, q + 600 - 60 - titleLayout.width - 2, n + 5 + 60 -4);*/

        fMed.setColor(actualTitleFrameColor.r, actualTitleFrameColor.g, actualTitleFrameColor.b, 1 * glow);

        titleLayout.setText(fMed, numberGames);
        fMed.draw(batch, numberGames, q + 600 - 30 - titleLayout.width, n + 104 + 64);

        titleLayout.setText(fMed, numberTotal);
        fMed.draw(batch, numberTotal, q + 600 - 30 - titleLayout.width, n + 53 + 64);


        titleLayout.setText(fMed, percent);
        fMed.draw(batch, percent, q + 600 - 30 - titleLayout.width, n + 5 + 64);

    }

    private void workGraphColors() {
        mphColor.r = 0.7f * actualSpeed / 250;
        mphColor.g = 0.7f - mphColor.r;

        distCol.r = 0.7f * actualDist / 250;
        distCol.g = 0.7f - distCol.r;

        gripColor.g = 0.7f * actualGrip / 250;
        gripColor.r = 0.7f - gripColor.g;
    }

    public void setRecords(int numberGoes, int percent){
        for (int i = 0; i < datas.size; i++) {
            datas.get(i).readData();
        }
    }

    public void closeFiles() {
        for (int i = 0; i < datas.size; i++) {
            datas.get(i).makeGoesZero();
            datas.get(i).readData();
        }
    }


    public void moveUpOne() {
        if(currentCenter < datas.size - 1) {
            currentCenter++;
            track1pos -= trackGap;
        } else {
            master.menuSelector.moveUpOne();
            master.complete.goToMenu();
        }
    }

    public void setIfToOpen() {
        if(datas.get(currentCenter).getPercent() != 100){
            currentOpening = true;
        }
    }


    @Override
    public void turnPageOff() {
        car.reset();
        carPos.set(origCarPos);
        carPos2.set(origCarPos);
        trackBacks.setRangeExtra(0);
        if(datas.get(datas.size - 1).getPercent() == 100){
            master.menuSelector.locks[menuType - 1] = 1;
        }
        super.turnPageOff();
    }

    public float getTrackSize() {
        return trackSize;
    }

    public float getTrack1Pos() {
        return track1pos;
    }

    public int getTrackGap() {
        return trackGap;
    }
}

