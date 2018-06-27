package com.wonky_productions.Screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.wonky_productions.Constants;
import com.wonky_productions.MenuManager;
import com.wonky_productions.Objects.BackButton;
import com.wonky_productions.Touch;

public class MenuSelector extends Selector {
    int st;
    int titleWidth, titleHeight;
    MenuManager menuManager;
    int[] locks;

    public MenuSelector(Master master, AssetManager manager, Touch touch, START_POS startPos, EXIT_POS exitPos, Color background) {
        super(master, manager, touch, startPos, exitPos, background);
        tile = atlas.findRegion("TrackTileFlatLight");
        titleWidth = 500;
        titleHeight = 240;
        menuManager = new MenuManager("Menu");

        backButton = new BackButton(this, atlas, touch);

        sortLocks();
    }

    private void sortLocks() {
        locks = new int[4];

        locks[0] = menuManager.getEasy();
        locks[1] = menuManager.getTricky();
        locks[2] = menuManager.getHard();
        locks[3] = menuManager.getExtra();

        menuManager.dispose();
    }

    @Override
    protected void sortFrameSizing() {
        st  = 0;
        trackGap = 1700;
        track1pos = st;
        origTrackPos = st;
        origCarPos = new Vector2(carPos);

        limitLeft = st;
        limitRight = st - (datas.size - 1) * trackGap;
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
        currentRect =new Rectangle(track1pos + 270, frameOffGround + 50, frameWidth - 100, frameHeight - 100);
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
            batch.draw(backTile, i * 50 + movement.x, 200, 50, 50);
            batch.draw(backTile, i * 50 + movement.x, scrHeight - 50, 50, 50);
            batch.draw(backTile, i * 50 + movement.x, scrHeight - 100, 50, 50);
            batch.draw(backTile, i * 50 + movement.x, scrHeight - 150, 50, 50);
            batch.draw(backTile, i * 50 + movement.x, scrHeight - 200, 50, 50);
            batch.draw(backTile, i * 50 + movement.x, scrHeight - 250, 50, 50);
        }

        drawLock(batch);
        drawBorders(batch);
        drawTitle(batch);
        drawGraphs(batch);
        drawChoose(batch);
        drawArrows(batch);

        batch.setColor(0,0,0,1);
        batch.draw(square, movement.x - 78, 0, 78, scrHeight);

    }

    private void drawLock(SpriteBatch batch) {
        if(locks[currentCenter] == 0  && Math.abs(swipeExtra) < 2){
            currentChoosable = false;
            batch.setColor(0.5f, 0.5f, 0.5f, 0.5f * glow);
            batch.draw(square,track1pos + currentCenter * trackGap + frameGap - 100 + movement.x, frameOffGround, frameWidth + 200, frameHeight);
            batch.setColor(0.5f,0.5f,0.5f, 1f * glow);
            batch.draw(padlock, track1pos + currentCenter * trackGap + frameGap + frameWidth/2 - 145 + movement.x, frameOffGround + frameHeight/2 - 60);
        } else if(currentOpening) {
            currentChoosable = false;
            batch.setColor(0.5f,0.5f,0.5f, 0.5f * openingTimer);
            batch.draw(square,track1pos + currentCenter * trackGap + frameGap - 200 + movement.x, frameOffGround, frameWidth + 300, frameHeight);
            batch.setColor(0.5f,0.5f,0.5f, 1f * openingTimer);
            batch.draw(padlock, track1pos + currentCenter * trackGap + frameGap + frameWidth/2 - 145 + movement.x, frameOffGround + frameHeight/2 - 60);
        } else {
            currentChoosable = true;
        }
    }

    private void drawChoose(SpriteBatch batch) {
        batch.setColor(0,0,0, 0.4f);
        int w = 800;
        int h = 150;
        float d = scrWidth/2 - w / 2 + movement.x;
        float v = 35;

        batch.draw(square, d, v, w, h);
        batch.draw(square, d + 10, v + 10, w - 20, h - 20);

        batch.setColor(actualTitleColor.r/2, actualTitleColor.g/2, actualTitleColor.b/2, 1f);

        batch.draw(square, d + 20, v + 20, w - 30, h - 30);

        batch.setColor(0,0,0,0.9f);
        batch.draw(square, d + 26, v + 26, w - 42, h - 42);


//        fMed.setColor(0.3f,0.3f,0.3f, 0.6f * glow);
//        fMed.draw(batch, "Choose track type...", d + 170 - 2, v + 100 - 4);

        fMed.setColor(actualTitleFrameColor.r, actualTitleFrameColor.g, actualTitleFrameColor.b, 1);
        fMed.draw(batch,  "Choose track type...", d + 145, v + 96);
    }

    @Override
    protected void drawBorders(SpriteBatch batch) {

        firstX = (int) track1pos - 42;
        batch.setColor(frontTileColor);

        for (int i = - (firstX + 600) / tileSize; i < (scrWidth - firstX + 600) / tileSize + 1 ; i++) {
            for (int j = 0; j < 2; j++) {
                if(i * tileSize + firstX - 600 > - 50) {
                    batch.draw(tile, i * tileSize + movement.x + firstX - 600, frameOffGround - (j + 1) * tileSize, tileSize, tileSize);
                    batch.draw(tile, i * tileSize + movement.x + firstX - 600, j * tileSize + frameOffGround + frameHeight, tileSize, tileSize);
                }
            }
        }

        for (int i = currentCenter; i < datas.size + 1; i++) {
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < frameHeight / tileSize; k++) {
                    if( i * trackGap + j * tileSize + firstX < scrWidth) {
                        batch.draw(tile, i * trackGap + j * tileSize + movement.x + firstX, frameOffGround + k * tileSize, tileSize, tileSize);
                    }
                }
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

    @Override
    protected void drawFrames(SpriteBatch batch) {
        if(pageState != PAGE_STATE.GOING && pageState != PAGE_STATE.COMING) {
            for (int i = 0; i < datas.size; i++) {
                if (i * trackGap + firstX < 1760) {


                    if (i == currentCenter) {
                        batch.setColor(frameColor.r + 0.3f * glow, frameColor.g + 0.3f * glow, frameColor.b + 0.3f * glow, 1);
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
            batch.setColor(frameColor);
            batch.draw(frameTop, movement.x, frameOffGround - 2 * tileSize - 40, scrWidth, 40);
            batch.draw(frameTop, movement.x, frameOffGround + frameHeight + 2 * tileSize, scrWidth, 40);
            batch.setColor(0, 0, 0, 0.3f);
            batch.draw(square, movement.x, frameOffGround + frameHeight + 2 * tileSize - 20, scrWidth, 20);
            batch.setColor(0, 0, 0, 0.3f);
            batch.draw(square, movement.x, frameOffGround - 2 * tileSize - 80, scrWidth, 40);
        } else {
            int i = currentCenter;
            if (i * trackGap + firstX < 1760) {


                if (i == currentCenter) {
                    batch.setColor(frameColor.r + 0.3f * glow, frameColor.g + 0.3f * glow, frameColor.b + 0.3f * glow, 1);
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
            batch.setColor(frameColor);
            batch.draw(frameTop, movement.x, frameOffGround - 2 * tileSize - 40, scrWidth, 40);
            batch.draw(frameTop, movement.x, frameOffGround + frameHeight + 2 * tileSize, scrWidth, 40);
            batch.setColor(0, 0, 0, 0.3f);
            batch.draw(frameTop, movement.x, frameOffGround + frameHeight + 2 * tileSize - 20, scrWidth, 20);
            batch.setColor(0, 0, 0, 0.4f);
            batch.draw(frameTop, movement.x, frameOffGround - 2 * tileSize - 80, scrWidth, 40);
        }
    }

    @Override
    public void drawTitle(SpriteBatch batch) {
        targetTitleColor.set(datas.get(currentCenter).getWallFlashCol());
        titleFrameColor.set(datas.get(currentCenter).getCar1());
        batch.setColor(0,0,0, 0.4f);

        float d = scrWidth/2 - titleWidth/2 + movement.x;
        float h = frameOffGround + frameHeight - 50;

        batch.draw(square, d, h, titleWidth, titleHeight);
        batch.draw(square, d + 10, h + 10, titleWidth - 20, titleHeight - 20);

        batch.setColor(actualTitleColor.r/2, actualTitleColor.g/2, actualTitleColor.b/2, 1f);

        batch.draw(square, d + 20, h + 20, titleWidth - 40, titleHeight - 40);

        batch.setColor(0,0,0,0.8f);
        batch.draw(square, d + 26, h + 26, titleWidth - 52, titleHeight - 52);

        titleLayout.setText(fLarge,datas.get(currentCenter).getName());

      /*  fLarge.setColor(0.3f,0.3f,0.3f, 0.6f * glow);
        fLarge.draw(batch, datas.get(currentCenter).getName(), d + titleWidth/2 - titleLayout.width/2 - 4, h + 170 - 8);*/

        fLarge.setColor(actualTitleFrameColor.r, actualTitleFrameColor.g, actualTitleFrameColor.b, 1 * glow);
        fLarge.draw(batch, datas.get(currentCenter).getName(), d + titleWidth/2 - titleLayout.width/2 + 6, h + 148);
    }

    @Override
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

    @Override
    public void drawGraphs(SpriteBatch batch) {

    }

    @Override
    protected void update(float delta) {
        if(currentOpening && pageState == PAGE_STATE.ON){
            openingTimer -= delta * 0.8f;
            if(openingTimer < 0){
                openingTimer = 1;
                currentOpening = false;
            }
        }
        super.update(delta);
    }

    @Override
    public void moveUpOne() {
        if(currentCenter < datas.size - 1){
            currentCenter++;
            if(locks[currentCenter] == 0){
                currentOpening = true;
            }
            locks[currentCenter] = 1;

            writeToFile();
            track1pos -= trackGap;
        }
    }

    private void writeToFile() {
        menuManager.setEasy(locks[0]);
        menuManager.setTricky(locks[1]);
        menuManager.setHard(locks[2]);
        menuManager.setExtra(locks[3]);
        System.out.println(locks[0] + ", " + locks[1] + ", " + locks[2] + ", " + locks[3]);
        menuManager.write();
    }

    @Override
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
                    swipeExtra ++;
                    swiping = true;
                } else if(touch.getDirection() == Constants.DIRECTION.RIGHT) {
                    swipeExtra --;
                    swiping = true;
                }
            }
        }
    }

    public void dispose(){
        menuManager.setEasy(locks[0]);
        menuManager.setTricky(locks[1]);
        menuManager.setHard(locks[2]);
        menuManager.setExtra(locks[3]);
        menuManager.write();
    }

    @Override
    protected void goToNextPage() {
        pageState = PAGE_STATE.GOING;
        trackBacks.setRangeExtra(2);
        trackBacks.setFirstExtra(2);
        track.setRangeExtra(1);
        master.selector.createMe(datas.get(currentCenter).getName());
        master.selector.resetOn();
        endTimer = 0;
        state = STATE.OTHER;
    }
}
