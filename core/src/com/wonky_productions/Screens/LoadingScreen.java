package com.wonky_productions.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.wonky_productions.Touch;

public class LoadingScreen extends Page {

    BitmapFont font12, font100, font200;
    FreeTypeFontGenerator generator;
    float timer, flashTimer;
    float bar;
    private Color loaderColor;

    public LoadingScreen(Master master, AssetManager manager, Touch touch, START_POS startPos, EXIT_POS exitPos, Color background) {
        super(master, manager, touch, startPos, exitPos, background);
        pageState = PAGE_STATE.ON;
        atlas = new TextureAtlas("Grid.pack");
        square = atlas.findRegion("square");
        backTile = atlas.findRegion("TrackTileDark");
        backTileSize = 60;
        loadAssets();
        timer = 0;
        bar = 0;
        loaderColor = new Color(0.3f, 0.7f, 0.5f, 0.8f);
    }

    private void loadAssets() {

        generator = new FreeTypeFontGenerator(Gdx.files.internal("imagine_font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.borderWidth = 1;
        parameter.borderColor.set(1, 1, 1, 0.6f);
        font12 = generator.generateFont(parameter);
        parameter.size = 40;
        parameter.borderWidth = 2;
        parameter.borderColor.set(1, 1, 1, 0.6f);
        font100 = generator.generateFont(parameter);
        parameter.size = 90;
        parameter.borderWidth = 4;
        parameter.borderColor.set(1, 1, 1, 0.6f);

        font200 = generator.generateFont(parameter);

        master.font12 = font12;
        master.font100 = font100;
        master.font200 = font200;
        manager.load("Grid.pack", TextureAtlas.class);
    }

    @Override
    protected void update(float delta) {

        timer += delta;

        bar = MathUtils.clamp(manager.getProgress(), 0, timer / 1.5f);

        if(manager.update() && bar >= 1){
            flash(delta);

        }
        super.update(delta);
        if(pageState == PAGE_STATE.OFF){
            dispose();
        }
    }

    @Override
    protected void draw(SpriteBatch batch) {
        super.draw(batch);
        batch.setColor(0.1f, 0.2f, 0.1f, 0.9f);
        for (int i = 0; i < scrHeight / backTileSize; i++) {
            for (int j = 0; j < scrWidth / backTileSize; j++) {
                batch.draw(backTile, j * backTileSize + movement.x, i * backTileSize + movement.y, backTileSize, backTileSize);
            }
        }
        drawBar(batch);
        drawLoader(batch);
    }

    private void drawLoader(SpriteBatch batch) {
        batch.setColor(0,0,0, 0.4f);
        batch.draw(square, scrWidth/2 - 200 + movement.x, scrHeight/2 - 100 + movement.y, 400, 200);
        batch.draw(square, scrWidth/2 - 190 + movement.x, scrHeight/2 - 90 + movement.y, 380, 180);
        batch.setColor(0.3f, 0.7f, 0.3f, 0.5f);
        batch.draw(square, scrWidth/2 - 180 + movement.x, scrHeight/2 - 80 + movement.y, 360, 160);
        batch.setColor(0,0,0,1);
        batch.draw(square, scrWidth/2 - 174 + movement.x, scrHeight/2 - 74 + movement.y, 348, 148);
        batch.setColor(loaderColor);
        batch.draw(square, scrWidth/2 - 160 + movement.x, scrHeight/2 - 60 + movement.y, 320 * bar, 120);
    }

    public void dispose(){
        atlas.dispose();
    }

    public void flash(float delta){
        flashTimer += delta;
        if(flashTimer < 0.1f){
            loaderColor.a += delta * 2;
            if(loaderColor.a > 1){
                loaderColor.a = 1;
            }
        } else if(flashTimer < 0.4f){
            loaderColor.a -= delta * 2;
        } else {
            pageState = PAGE_STATE.GOING;
            master.loadPages();
        }
    }
}
