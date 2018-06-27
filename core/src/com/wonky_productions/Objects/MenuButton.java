package com.wonky_productions.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.wonky_productions.Screens.Menu;
import com.wonky_productions.Touch;

public class MenuButton {

    String name;
    int n;
    Vector2 position, movement;
    Rectangle rect;
    int width, height;
    enum STATE {OFF, ON, TOUCHED, CHOSEN}
    STATE state;
    Menu menu;
    TextureRegion square;
    BitmapFont font;
    Color color;
    float alpha;
    float timer;

    public MenuButton(String name, Vector2 position, Menu menu, TextureRegion square, BitmapFont font, Color color, Vector2 movement) {
        this.name = name;
        this.position = position;
        this.movement = movement;
        this.menu = menu;
        this.square = square;
        this.font = font;
        this.color = color;
        state = STATE.ON;
        width = 450;
        height = menu.getFrameHeight() / 3;
        rect = new Rectangle(position.x + movement.x, position.y + movement.y, width, height);
        n = name.length();
        alpha = 0.4f;
    }

    public void update(float delta){
        rect.x = position.x + movement.x + menu.getSideways();
        rect.y = position.y + movement.y;

        if(state == STATE.ON) {
            timer += delta;
            if (timer < 0.4f) {
                alpha += delta;
            } else if (timer < 0.8f) {
                alpha -= delta;
            } else {
                timer = 0;
            }
        }

        alpha = MathUtils.clamp(alpha, 0.4f, 0.7f);

        if(state == STATE.TOUCHED){
            alpha += delta;
            if(alpha > 0.89){
                alpha = 0.89f;
            }
        }

        if(state == STATE.CHOSEN){
            color.b += delta;
            if(color.b > 1){
                color.b = 1;
            }
        }


    }

    public void checkButton(Touch touch){
        if(touch.getState(menu) != Touch.STATE.NOT){
            if(rect.contains(touch.getTouchPos())){
                state = STATE.TOUCHED;
                if(touch.getState(menu) == Touch.STATE.RELEASED){
                    state = STATE.CHOSEN;
                    menu.goToNextScreen();
                    timer = 0;
                }
            }
        }
    }


    public void draw(SpriteBatch batch){
        if(state != STATE.OFF){
            batch.setColor(0,0,0,0.3f);
            batch.draw(square, rect.x, rect.y, width, height);
            batch.draw(square, rect.x + 10, rect.y + 10, width - 20, height - 20);
            batch.setColor(color.r, color.g, color.b, alpha * 0.6f);
            batch.draw(square, rect.x + 20, rect.y + 20, width - 40, height - 40);
            batch.setColor(0,0,0,0.9f);
            batch.draw(square, rect.x + 26, rect.y + 26, width - 52, height - 52);

            drawTitle(batch);
        }
    }

    private void drawTitle(SpriteBatch batch) {
        font.setColor(color.r, color.g, color.b, alpha);
        font.draw(batch, name, rect.x + width / 2 - 50, rect.y + height / 2 + 15);

    }
}
