package com.wonky_productions.Screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wonky_productions.Objects.BackButton;
import com.wonky_productions.Touch;


public class TrackSelector extends Selector {


    public TrackSelector(Master master, AssetManager manager, Touch touch, START_POS startPos, EXIT_POS exitPos, Color background) {
        super(master, manager, touch, startPos, exitPos, background);
        backButton = new BackButton(this, atlas, touch);
    }

    @Override
    public void createMe(String name) {
        super.createMe(name);
    }

    private void drawLock(SpriteBatch batch) {
        if(currentCenter > 0 && datas.get(currentCenter - 1).getPercent() != 100 && Math.abs(swipeExtra) < 1 && !leftTouched && !rightTouched){
            currentChoosable = false;
            batch.setColor(0.5f,0.5f,0.5f, 0.5f * glow);
            batch.draw(square,track1pos + currentCenter * trackGap + frameGap - 200 + movement.x, frameOffGround, frameWidth + 300, frameHeight);
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

    @Override
    protected void drawBorders(SpriteBatch batch) {
        drawLock(batch);
        super.drawBorders(batch);
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
        if(pageState == PAGE_STATE.ON && state != STATE.ENDING) {
            backButton.update(delta, actualTitleFrameColor);
        }
        if(goingBackPage){
            goingBack();
        }
    }

    private void goingBack() {
        if( master.menuSelector.pageState == PAGE_STATE.ON){
            pageState = PAGE_STATE.OFF;
            goingBackPage = false;
        }
    }

    @Override
    protected void draw(SpriteBatch batch) {
        super.draw(batch);
        backButton.draw(batch);
    }

    @Override
    public void goBackPage() {
        master.menuSelector.pageState = PAGE_STATE.COMING;
        goingBackPage = true;

    }

    @Override
    public void drawGraphs(SpriteBatch batch) {

        numberGames = Integer.toString(datas.get(currentCenter).getNumberGames());
        percent  = Integer.toString(datas.get(currentCenter).getPercent());
        numberTotal  = Integer.toString(datas.get(currentCenter).getNumberTotal());

         super.drawGraphs(batch);
    }
}
