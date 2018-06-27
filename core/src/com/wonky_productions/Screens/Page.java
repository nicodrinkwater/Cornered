package com.wonky_productions.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.wonky_productions.Constants;
import com.wonky_productions.Objects.BackButton;
import com.wonky_productions.Objects.LevelData;
import com.wonky_productions.Touch;
import com.wonky_productions.Touch;

public class Page {


    AssetManager manager;
    Master master;
    TextureAtlas atlas;
    TextureRegion square, backTile;
    float xMovement, yMovement;
    Vector2 movement;
    Touch touch;
    Vector2 carPos;

    enum PAGE_STATE {OFF, COMING, GOING, ON}
    enum START_POS {LEFT, RIGHT, UP, DOWN}
    enum EXIT_POS {LEFT, RIGHT, UP, DOWN}
    PAGE_STATE pageState;
    START_POS startPos;
    EXIT_POS exitPos;
    int scrWidth, scrHeight;
    float wipeSpeed;
    Color backColor, origBackCol;
    Vector2 cameraPos, touchPos;
    OrthographicCamera camera;
    BitmapFont fSmall, fMed, fLarge;
    int barSize, backTileSize;
    float barTimer;
    boolean touchable;
    boolean levelReset;
    boolean goingBackPage;

    BackButton backButton;

    public Page(Master master, AssetManager manager, Touch touch, START_POS startPos, EXIT_POS exitPos, Color background) {
        this.master = master;
        this.manager = manager;
        this.startPos = startPos;
        this.exitPos = exitPos;
        this.touch = touch;
        this.backColor = background;
        pageState = PAGE_STATE.OFF;
        scrWidth = Constants.SCREEN_WIDTH;
        scrHeight = Constants.SCREEN_HEIGHT;
        wipeSpeed = Constants.WIPE_SPEED;
        xMovement = 0;
        yMovement = 0;

        movement = new Vector2();
        cameraPos = master.cameraPos;
        carPos = new Vector2();
        camera = master.camera;

        fSmall = master.font12;
        fMed = master.font100;
        fLarge = master.font200;

        touchPos = new Vector2(-1, -1);

        origBackCol = new Color(background);

        sortStartPos();

        barSize = 75;



    }

    private void sortStartPos() {
        if(startPos == START_POS.LEFT){
            xMovement = -scrWidth - barSize;
            yMovement = 0;
        } else if(startPos == START_POS.RIGHT){
            xMovement = scrWidth + barSize;
            yMovement = 0;
        } else if(startPos == START_POS.UP){
            yMovement = scrHeight + barSize;
            xMovement = 0;
        } else if(startPos == START_POS.DOWN){
            yMovement = -scrHeight - barSize;
            xMovement = 0;
        }

        backColor.r = 0.6f * origBackCol.r;
        backColor.g = 0.6f * origBackCol.g;
        backColor.b = 0.6f * origBackCol.b;

        movement.set(xMovement, yMovement);
    }

    public void render(float delta, SpriteBatch batch){

        movement.set(xMovement, yMovement);
        if(pageState != PAGE_STATE.OFF) {
            update(delta);
        }
        if(pageState != PAGE_STATE.OFF) {
            draw(batch);
        }
    }

    protected void update(float delta) {

        if(pageState == PAGE_STATE.GOING){
            leave(delta);
        } else if(pageState == PAGE_STATE.COMING){
            arrive(delta);
        }
    }

    protected void arrive(float delta) {
        barTimer += delta;
        switch(startPos){
            case RIGHT:
                xMovement -= wipeSpeed * delta;
                if(xMovement < 0){
                    xMovement = 0;
                    pageState = PAGE_STATE.ON;
                }
                break;
            case LEFT:
                xMovement += wipeSpeed * delta;
                if(xMovement > 0){
                    xMovement = 0;
                    pageState = PAGE_STATE.ON;
                }
                break;
            case DOWN:
                yMovement += wipeSpeed * delta;
                if(yMovement > 0){
                    yMovement = 0;
                    pageState = PAGE_STATE.ON;
                }
                break;
            case UP:
                yMovement -= wipeSpeed * delta;
                if(yMovement < 0){
                    yMovement = 0;
                    pageState = PAGE_STATE.ON;
                }
                break;
        }

        //lighten(delta);
    }

    public void resetOn() {
        xMovement = 0;
        yMovement = 0;
        pageState = PAGE_STATE.ON;
        movement.set(0,0);
        backColor.set(origBackCol);

    }

    protected void leave(float delta) {

        barTimer += delta;
        switch(exitPos){
            case LEFT:
                xMovement -= wipeSpeed * delta;
                if(xMovement < -scrWidth - barSize){
                    turnPageOff();
                }
                break;
            case RIGHT:
                xMovement += wipeSpeed * delta;
                if(xMovement > scrWidth + barSize){
                    turnPageOff();
                }
                break;
            case UP:
                yMovement += wipeSpeed * delta;
                if(yMovement > scrHeight + barSize){
                    turnPageOff();
                }
                break;
            case DOWN:
                yMovement -= wipeSpeed * delta;
                if(yMovement < -scrHeight - barSize){
                    turnPageOff();
                }
                break;
        }

        //darken(delta);
    }

    protected void draw(SpriteBatch batch){
        batch.setColor(backColor);
        batch.draw(square, xMovement, yMovement, scrWidth, scrHeight);
        drawBar(batch);
    }

    public void darken(float delta){
        backColor.r *= 1 - delta / 2;
        backColor.g *= 1 - delta / 2;
        backColor.b *= 1 - delta / 2;
    }


    public void lighten(float delta){
        backColor.r *= 1 + delta / 2;
        backColor.g *= 1 + delta / 2;
        backColor.b *= 1 + delta / 2;

        if(backColor.r > origBackCol.r){
            backColor.r = origBackCol.r;
        }
        if(backColor.g > origBackCol.g){
            backColor.g = origBackCol.g;
        }
        if(backColor.b > origBackCol.b){
            backColor.b = origBackCol.b;
        }
    }

    protected void drawBar(SpriteBatch batch) {
        batch.setColor(0,0,0,1);
        if(pageState == PAGE_STATE.GOING){
            switch (exitPos){
                case UP:
                    batch.draw(square, 0, yMovement - barSize, scrWidth, barSize);
                    break;
                case  DOWN:
                    batch.draw(square, 0, yMovement + scrHeight, scrWidth, barSize);
                    break;
                case LEFT:
                    batch.draw(square, xMovement + scrWidth, 0, barSize, scrHeight);
                    break;
                case RIGHT:
                    batch.draw(square, xMovement - barSize, 0, barSize, scrHeight);
                    break;
            }
        }
        if(pageState == PAGE_STATE.COMING){
            switch (startPos){
                case UP:
                    batch.draw(square, 0, yMovement - barSize, scrWidth, barSize);
                    break;
                case  DOWN:
                    batch.draw(square, 0, yMovement + scrHeight, scrWidth, barSize);
                    break;
                case LEFT:
                    batch.draw(square, xMovement + scrWidth, 0, barSize, scrHeight);
                    break;
                case RIGHT:
                    batch.draw(square, xMovement - barSize, 0, barSize, scrHeight);
                    break;
            }
        }
    }


    public void turnPageOff() {
        sortStartPos();
        pageState = PAGE_STATE.OFF;
        levelReset = false;
    }

    public void touched(Vector2 screenTouchPos){
        touchPos.set(fromScreenToGame(screenTouchPos));
    }



    public Color getBackgroundColor() {
        return backColor;
    }

    public Vector2 getCameraPos() {
        return cameraPos;
    }

    public float getYMovement() {
        return yMovement;
    }

    public void setYMovement(float yMovement) {
        this.yMovement = yMovement;
    }

    public float getXMovement() {
        return xMovement;
    }

    public void setXMovement(float xMovement) {
        this.xMovement = xMovement;
    }

    protected Vector2 fromScreenToGame(Vector2 pos){
        pos.x = Constants.SCREEN_WIDTH * (pos.x / Gdx.graphics.getWidth());
        pos.y = Constants.SCREEN_HEIGHT * ((Gdx.graphics.getHeight() -pos.y) / Gdx.graphics.getHeight());
        return pos;
    }


    public float getZoom() {
        return camera.zoom;
    }

    public boolean isTouchable() {
        return touchable;
    }

    public void createNewLevel(LevelData data){
        master.level.createLevel(data);
        master.level.resetEverything();
        levelReset = true;
    }

    public Vector2 getMovement() {
        return movement;
    }

    public Vector2 getCarPos(){
        return carPos;
    }

    public void goBackPage(){

    }
}
