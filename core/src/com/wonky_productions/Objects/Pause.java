package com.wonky_productions.Objects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.wonky_productions.Constants;
import com.wonky_productions.Screens.Level;
import com.wonky_productions.Touch;

public class Pause {

    TextureAtlas atlas;
    TextureRegion button, square, d3, backBack, backFront;
    Level level;

    public enum STATE {OUT, COMING, PAUSED, GOING}
    STATE state;
    Rectangle rect, backRect;
    Color buttonColor;
    int scrWidth, scrHeight, border, buttonSize;
    Vector2 camPos;
    float timeSpeed, dTime;
    float squareSize;
    Touch touch;
    BitmapFont font;
    Color backButCol;


    public Pause(Level level, Touch touch, TextureAtlas atlas, Color color) {
        this.atlas = atlas;
        this.level = level;
        this.touch = touch;
        state = STATE.OUT;
        scrWidth = Constants.SCREEN_WIDTH;
        scrHeight = Constants.SCREEN_HEIGHT;
        border = 30;
        buttonSize = 140;
        button = atlas.findRegion("pauseBar");
        square = atlas.findRegion("square");
        d3 = atlas.findRegion("DiSquareDarkRim");
        backBack = atlas.findRegion("graph");
        font = level.getMedFont();

        backRect = new Rectangle(scrWidth /2 - 180, 100, 360, 160);

        buttonColor = color;
        rect = new Rectangle(scrWidth - border - buttonSize, scrHeight - border - buttonSize, buttonSize + border, buttonSize + border);
        camPos = level.getCameraPos();
        timeSpeed = 1;
        dTime = 0;
        squareSize = 40;
        backButCol = new Color();
    }

    public void setColor(Color scoreCol) {
        buttonColor.set(scoreCol);
    }


    public void draw(SpriteBatch batch){


        if(true){
            if(rect.contains(touch.getTouchPos()) && touch.getState(level) != Touch.STATE.NOT){
                buttonColor.set(level.getFlashColor().r * 0.8f, level.getFlashColor().g * 0.8f, level.getFlashColor().b * 0.8f, 0.8f);
            } else {
                buttonColor.set(level.getFlashColor().r * 0.4f, level.getFlashColor().g * 0.4f, level.getFlashColor().b * 0.4f, 0.8f);
            }
            batch.setColor(0, 0, 0, 0.2f);
            batch.draw(square, rect.x, rect.y  + scrHeight * (timeSpeed - 1), buttonSize, buttonSize);
            batch.setColor(0,0,0,0.3f);
            batch.draw(square, rect.x + 20, rect.y + 20  + scrHeight * (timeSpeed - 1), 40, 100);
            batch.draw(square, rect.x + 80, rect.y + 20  + scrHeight * (timeSpeed - 1), 40, 100);
            batch.setColor(buttonColor);
            batch.draw(square, rect.x + 30, rect.y + 30  + scrHeight * (timeSpeed - 1), 20, 80);
            batch.draw(square, rect.x + 90, rect.y + 30  + scrHeight * (timeSpeed - 1), 20, 80);
        }

        if(state != STATE.OUT){
            int h = scrWidth / 9;
            batch.setColor(0.1f,0.1f, 0.1f, 0.6f);
            batch.draw(square, 0, scrHeight * timeSpeed, scrWidth + 6, scrHeight + 6);
            drawRim(batch);

            drawBackButton(batch);
            //drawFixtures(batch);
        }

    }

    private void drawBackButton(SpriteBatch batch) {

        if(backRect.contains(touch.getTouchPos())){
            backButCol.set(level.getFlashColor().r * 0.8f, level.getFlashColor().g * 0.8f, level.getFlashColor().b * 0.8f, 1);
        } else {
            backButCol.set(level.getFlashColor().r * 0.4f, level.getFlashColor().g * 0.4f, level.getFlashColor().b * 0.4f, 1);
        }
        batch.setColor(0.1f, 0.1f, 0.1f, 0.4f);
        batch.draw(square, backRect.x , backRect.y + scrHeight * timeSpeed, backRect.width, backRect.height);
        batch.draw(square, backRect.x + 10, backRect.y + 10 + scrHeight * timeSpeed, backRect.width - 20, backRect.height - 20);
        batch.setColor(backButCol);
        batch.draw(square, backRect.x + 20, backRect.y + 20 + scrHeight * timeSpeed, backRect.width - 40, backRect.height - 40);
        batch.setColor(0.1f, 0.1f, 0.1f, 0.85f);
        batch.draw(square, backRect.x + 26, backRect.y + 26 + scrHeight * timeSpeed, backRect.width - 52, backRect.height - 52);
        font.setColor(0f, 0f, 0f, 0.2f);
        font.draw(batch, "Back", backRect.x + 120 - 3, backRect.y + 100 - 6 + scrHeight * timeSpeed);
        font.setColor(backButCol);
        font.draw(batch, "Back", backRect.x + 120, backRect.y + 100 + scrHeight * timeSpeed);
    }


    private void drawRim(SpriteBatch batch) {
        batch.setColor(0,0,0,0.8f);

        batch.draw(square, 0, scrHeight * timeSpeed - squareSize, scrWidth, squareSize);

    }

    private void drawFixtures(SpriteBatch batch) {
        int width = (int)( 0.25f * squareSize);
        batch.setColor(0,0,0, 1f);
        batch.draw(square, 0, scrHeight * timeSpeed  + scrHeight - width, scrWidth, width);
        batch.draw(square, 0, scrHeight * timeSpeed, scrWidth, width);
        batch.draw(square, 0, scrHeight * timeSpeed + width, width, scrHeight - 2 * width);
        batch.draw(square, scrWidth - width, scrHeight * timeSpeed + width, width, scrHeight - 2 * width);
    }


    public void update(float delta){

        if(state != STATE.OUT) {
            dTime = Gdx.graphics.getDeltaTime();
            if (state == STATE.COMING) {
                timeSpeed -= 2f * dTime;
                if (timeSpeed < 0) {
                    timeSpeed = 0;
                    state = STATE.PAUSED;
                }
            } else if (state == STATE.GOING) {
                timeSpeed += 2f * dTime;
                if (timeSpeed > 1) {
                    timeSpeed = 1;
                    state = STATE.OUT;
                }
            } else if (state == STATE.PAUSED) {
                //TODO

            }
        }
    }

    public boolean checkButton(){
        if(state == STATE.OUT ){
            if (rect.contains(touch.getTouchPos()) && level.getState() != Level.LEVEL_STATE.DEAD) {
                state = STATE.COMING;
                return true;
            }
        } else if(state == STATE.PAUSED){
            if(backRect.contains(touch.getTouchPos())){
                level.goBackToSelector();
            } else {
                state = STATE.GOING;
            }
            return true;
        }
        return  false;
    }


    public void reset() {
        timeSpeed = 1;
        state = STATE.OUT;
    }

    public float getTimeSpeed() {
        return timeSpeed;
    }

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }
}
