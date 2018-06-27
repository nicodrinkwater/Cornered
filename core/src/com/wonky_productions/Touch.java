package com.wonky_productions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.wonky_productions.Screens.Page;

public class Touch {

    Vector2 touchPos, firstTouch, lastTouch, drag;
    float timer;
    int swipeMeasure, dragMeasure;
    Page touchablePage;

    public Vector2 getTouchPos() {
        return touchPos;
    }

    public enum STATE {JUST_TOUCHED, TOUCHING, DRAG, SWIPE, NOT, RELEASED}
    STATE state;
    Constants.DIRECTION direction;
    boolean touchInProgress;

    public Touch() {
        touchPos = new Vector2();
        firstTouch = new Vector2();
        lastTouch = new Vector2();
        drag = new Vector2();
        state = STATE.NOT;
        direction = Constants.DIRECTION.NONE;
        swipeMeasure = 120;
        dragMeasure = 30;
    }

    public void update(float delta){
        if(Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            touchPos = fromScreenToGame(touchPos);
            if(!touchInProgress){
                firstTouch.set(touchPos);
                state = STATE.JUST_TOUCHED;
            }
            touchInProgress = true;
        } else {
            if(touchInProgress) {
                touchInProgress = false;
                timer = 0;
                if(state == STATE.TOUCHING) {
                    state = STATE.RELEASED;
                } else {
                    state = STATE.NOT;
                }
            } else {
                state = STATE.NOT;
            }
        }

        if(touchInProgress){
            if(timer > 0){
                if(touchPos.x - lastTouch.x < -swipeMeasure){
                    state = STATE.SWIPE;
                    direction = Constants.DIRECTION.LEFT;
                } else if(touchPos.x - lastTouch.x > swipeMeasure){
                    state = STATE.SWIPE;
                    direction = Constants.DIRECTION.RIGHT;
                } else if(touchPos.y - lastTouch.y < -swipeMeasure){
                    state = STATE.SWIPE;
                    direction = Constants.DIRECTION.DOWN;
                } else if(touchPos.y - lastTouch.y > swipeMeasure){
                    state = STATE.SWIPE;
                    direction = Constants.DIRECTION.UP;
                } else if(timer > 0f && Math.abs(touchPos.x - firstTouch.x) + Math.abs(touchPos.y - firstTouch.y) > dragMeasure){
                    drag.set(touchPos.x - lastTouch.x, touchPos.y - lastTouch.y);
                    state = STATE.DRAG;
                } else {
                    state = STATE.TOUCHING;
                }
            } 
            timer += delta;
        }
        lastTouch.set(touchPos);
    }

    protected Vector2 fromScreenToGame(Vector2 pos){
        pos.x = Constants.SCREEN_WIDTH * (pos.x / Gdx.graphics.getWidth());
        pos.y = Constants.SCREEN_HEIGHT * ((Gdx.graphics.getHeight() -pos.y) / Gdx.graphics.getHeight());
        return pos;
    }

    public float getDragX(){
        return drag.x;
    }

    public float getDragY(){
        return drag.y;
    }

    public STATE getState(Page page) {
        if(this.touchablePage == page) {
            return state;
        } else {
            return STATE.NOT;
        }
    }

    public Constants.DIRECTION getDirection() {
        return direction;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public void setTouchablePage(Page page){
        touchablePage = page;
    }

}
