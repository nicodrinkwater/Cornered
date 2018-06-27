package com.wonky_productions.Screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.wonky_productions.Objects.*;
import com.wonky_productions.Touch;


public class Menu extends Page {

    int frameHeight, frameOffGround, tileWidth, tileSize, trackSqSize;
    TextureRegion frameTop, frameSide, frameTL, frameTR, frameBL, frameBR, tile;
    Color frameColor;
    int fSize;
    private Color tileColor;
    MenuTrack track;
    float sideways;
    MenuButton play;
    TrackCreater creater;
    LevelData data;
    float speed;
    Vector2 origCarPos;
    private Color nameColor;
    MenuCar car;
    float titleTimer;

    public Menu(Master master, AssetManager manager, Touch touch, START_POS arriveDir, EXIT_POS leaveDir, Color background) {
        super(master, manager, touch, arriveDir, leaveDir, background);
        atlas = manager.get("Grid.pack", TextureAtlas.class);
        square = atlas.findRegion("square");
        backTile = atlas.findRegion("TrackTileDark");
        backTileSize = 50;
        frameTop = atlas.findRegion("wallShadowTop");
        frameSide = atlas.findRegion("wallShadowSide");
        frameBL = atlas.findRegion("wallShadowCornerBL");
        frameBR = atlas.findRegion("wallShadowCornerBR");
        frameTL = atlas.findRegion("wallShadowCornerTL");
        frameTR = atlas.findRegion("wallShadowCornerTR");
        tile = atlas.findRegion("TrackTileFlatLight");

        tileSize = 50;

        frameHeight = 650;
        frameOffGround = 100;
        sideways = 0;

        frameColor = new Color(0.4f, 0.4f, 0.4f, 1);
        tileColor = new Color(0.2f, 0.2f, 0.2f, 1);

        data = new LevelData("FAST");
        creater = new TrackCreater("");

        trackSqSize = 50;

        track = new MenuTrack(this, atlas, creater.getLayout(), 400, 50, trackSqSize, data);
        car = new MenuCar(this,data, track, atlas, 115);

        sortButtons();

        speed = 600;

        carPos.y =  - 150;
        origCarPos = new Vector2(carPos);
        nameColor = new Color(0f, 0.2f , 0.6f, 0.8f);

    }

    private void sortButtons() {
        play = new MenuButton("Play", new Vector2(scrWidth/2 - 225, frameOffGround + frameHeight/4 - 40), this, square, fMed, new Color(0f,0.2f, 0.8f,1), movement);
    }

    @Override
    protected void draw(SpriteBatch batch) {
        super.draw(batch);
        batch.setColor(0.1f, 0.1f, 0.1f, 1f);

        for (int i = 0; i < frameOffGround / tileSize; i++) {
            for (int j = 0; j < scrWidth / backTileSize + 1; j++) {
                batch.draw(backTile, j * backTileSize + movement.x, i * backTileSize + movement.y, backTileSize, backTileSize);
            }
        }


        for (int i = 0; i < (scrHeight - frameOffGround - frameHeight) / tileSize + 1; i++) {
            for (int j = 0; j < scrWidth / backTileSize + 1; j++) {
                batch.draw(backTile, j * backTileSize + movement.x, frameOffGround + frameHeight +  i * backTileSize + movement.y, backTileSize, backTileSize);
            }
        }

        drawTiles(batch);
        drawFrame(batch);

        play.draw(batch);
        track.draw(batch);
        track.drawWall(batch);

        car.draw(batch);
        drawTrackFrame(batch);
        drawTitle(batch);

        drawBar(batch);

    }

    private void drawTitle(SpriteBatch batch) {

        int titleWidth  = 760;
        int titleHeight = 200;

        batch.setColor(0,0,0, 0.4f);

        float d = scrWidth/2 - titleWidth/2 + movement.x;
        float h = 60 + movement.y;

        batch.draw(square, d, h, titleWidth, titleHeight);
        batch.draw(square, d + 10, h + 10, titleWidth - 20, titleHeight - 20);

        batch.setColor(nameColor.r, nameColor.g, nameColor.b, 0.6f);
        batch.draw(square, d + 20, h + 20, titleWidth - 40, titleHeight - 40);

        batch.setColor(0,0,0,1f);
        batch.draw(square, d + 26, h + 26, titleWidth - 52, titleHeight - 52);

      /*  fLarge.setColor(0.3f,0.3f,0.3f, 0.4f);
        fLarge.draw(batch, "CORNERED", d + 110 - 4, h + 149 - 8);*/
        fLarge.setColor(0.4f, 0f, 0.5f, 1);
        fLarge.draw(batch, "CORNERED", d + 100, h + 130);
    }

    private void drawTrackFrame(SpriteBatch batch) {
        batch.setColor(frameColor);
        batch.draw(frameTop, 7 * tileSize, frameOffGround + frameHeight - 250 + movement.y, scrWidth  - 14 * tileSize, tileSize);
        batch.draw(frameTop, 7 * tileSize, frameOffGround + frameHeight + 100 + movement.y, scrWidth  - 14 * tileSize, tileSize);
        batch.draw(frameSide, 6 * tileSize, frameOffGround + frameHeight - 250 + movement.y, tileSize, 350);
        batch.draw(frameSide, 31 * tileSize, frameOffGround + frameHeight - 250 + movement.y, tileSize, 350);

        batch.draw(frameBL, 6 * tileSize, frameOffGround + frameHeight - 250 + movement.y, tileSize, tileSize);
        batch.draw(frameBR, 31 * tileSize, frameOffGround + frameHeight - 250 + movement.y, tileSize, tileSize);

        batch.draw(frameTL, 6 * tileSize, frameOffGround + frameHeight + 100 + movement.y, tileSize, tileSize);
        batch.draw(frameTR, 31 * tileSize, frameOffGround + frameHeight + 100 + movement.y, tileSize, tileSize);

        batch.setColor(0,0,0,0.3f);
        batch.draw(square, 6 * tileSize - 10, frameOffGround + frameHeight - 250 + movement.y - 20, 26 * tileSize, 20);
        batch.draw(square, 7 * tileSize, frameOffGround + frameHeight + 100 + movement.y - 20, 24 * tileSize, 20);
        batch.draw(square, 6 * tileSize - 10, frameOffGround + frameHeight - 250 + movement.y, 10, 4 * tileSize - 20);
    }

    private void drawTiles(SpriteBatch batch) {
        batch.setColor(tileColor);
        for (int i = 0; i < frameHeight / tileSize; i++) {
            for (int j = 0; j < scrWidth / tileSize + 1; j++) {
                batch.draw(tile, j * tileSize + movement.x, i * tileSize + frameOffGround + movement.y, tileSize, tileSize);
            }
        }
    }


    private void drawFrame(SpriteBatch batch) {
        batch.setColor(frameColor);
        batch.draw(frameTop, movement.x, frameOffGround + movement.y, scrWidth, 50);
        batch.draw(frameTop, movement.x, frameOffGround + frameHeight + movement.y - 50, scrWidth, 50);

        batch.setColor(0,0,0,0.3f);
        batch.draw(frameTop, movement.x, frameOffGround + movement.y - 20, scrWidth, 20);
        batch.draw(frameTop, movement.x, frameOffGround + frameHeight + movement.y - 70, scrWidth, 20);
    }

    @Override
    protected void update(float delta) {

        super.update(delta);

        carPos.x += speed * delta;
        origCarPos.x += speed * delta;
        if(carPos.x > 150 * 70) {
            carPos.x -= 96 * 70;
            origCarPos.x -= 96 * 70;
            //car.jumpBack(96 * 70);
        }

        track.update(delta);
        car.update(delta);
        workTitle(delta);

        play.update(delta);
        play.checkButton(touch);
    }

    private void workTitle(float delta) {
        titleTimer += delta;
        if(titleTimer < 2){
            nameColor.a += 0.05 * delta;
           // nameColor.r += delta /8;
        } else if(titleTimer < 4){
            nameColor.a -= 0.05 * delta;
           // nameColor.r -= delta /8;
        } else {
            titleTimer = 0;
        }
    }

    public float getSideways() {
        return sideways;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public void goToNextScreen() {
        pageState = PAGE_STATE.GOING;
        master.menuSelector.createMe("Menu");
        master.menuSelector.resetOn();
    }
}
