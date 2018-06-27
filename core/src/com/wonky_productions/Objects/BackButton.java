package com.wonky_productions.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.wonky_productions.Constants;
import com.wonky_productions.Screens.Page;
import com.wonky_productions.Touch;

public class BackButton {
    Rectangle rect;
    TextureAtlas atlas;
    TextureRegion square, backImage;
    Color color;
    Touch touch;
    float timer;
    enum  STATE { OFF, TOUCHING, CHOSEN }
    STATE state;
    Page page;
    float bright;

    public BackButton(Page page, TextureAtlas atlas, Touch touch) {
        this.page = page;
        this.atlas = atlas;
        this.touch = touch;

        square = atlas.findRegion("square");
        backImage = atlas.findRegion("Back");

        timer = 0;
        bright = 0.5f;
        color = new Color(bright, bright, bright, 1);
        rect = new Rectangle(Constants.SCREEN_WIDTH - 175, 25, 150, 150);

        state = STATE.OFF;
    }

    public void update(float delta, Color color){
        if(state == STATE.OFF && rect.contains(touch.getTouchPos())){
            state = STATE.TOUCHING;
        } else if(touch.getState(page) == Touch.STATE.RELEASED && rect.contains(touch.getTouchPos())){
            state = STATE.CHOSEN;
        } else if(!rect.contains(touch.getTouchPos())){
            state = STATE.OFF;
        }

        if(state == STATE.TOUCHING){
            bright += delta * 2;
        } else if(state == STATE.OFF){
            bright -= delta * 2;
        } else if(state == STATE.CHOSEN){
            page.goBackPage();
            state = STATE.OFF;
        }
        bright = MathUtils.clamp(bright, 0.5f, 0.9f);

        this.color = color;
    }

    public void draw(SpriteBatch batch){
        batch.setColor(0,0,0,0.2f);
        batch.draw(square, rect.x + page.getXMovement(), rect.y, 150, 150);
        batch.draw(square, rect.x + page.getXMovement() + 10, rect.y + 10, 130, 130);

        batch.setColor(color.r * bright, color.g * bright, color.b * bright, 1);
        batch.draw(backImage, rect.x + page.getXMovement() + 25, rect.y + 25);
    }
}
