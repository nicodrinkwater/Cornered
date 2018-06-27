package com.wonky_productions.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.wonky_productions.Constants;
import com.wonky_productions.Touch;

public class Master implements Screen {

    LoadingScreen loadingScreen;
    Level level;
    Menu menu;
    LevelComplete complete;
    Selector selector;
    MenuSelector menuSelector;
    OrthographicCamera camera;
    SpriteBatch batch;
    AssetManager manager;
    float pageNumber;
    Array<Page> pages;
    public Vector2 cameraPos;
    private boolean pagesLoaded;
    BitmapFont font12, font100, font200;
    boolean touchable;
    Touch touch;

    @Override
    public void show() {

        sortSizing();
        manager = new AssetManager();
        loadingScreen = new LoadingScreen(this, manager, touch, Page.START_POS.RIGHT, Page.EXIT_POS.LEFT, Color.BLUE);
        loadingScreen.resetOn();
        pages = new Array<Page>();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        cameraPos = new Vector2(0,0);
        touchable = true;
        touch = new Touch();
    }

    public void loadPages(){

        if(!pagesLoaded) {
            level = new Level(this, manager, touch, 1, new Color(0,0,0,1));
            menu = new Menu(this, manager, touch,Page.START_POS.UP, Page.EXIT_POS.UP, Color.DARK_GRAY);
            complete = new LevelComplete(this, manager, touch, Page.START_POS.LEFT, Page.EXIT_POS.RIGHT, Color.YELLOW);
            pageNumber = 0;
            menu.resetOn();
            selector = new TrackSelector(this, manager, touch, Page.START_POS.LEFT, Page.EXIT_POS.LEFT, new Color(0.3f,0.1f,0.1f,1));
            menuSelector = new MenuSelector(this, manager, touch, Page.START_POS.RIGHT, Page.EXIT_POS.RIGHT, new Color(0.3f,0.1f,0.1f,1));
            pages.add(level);
            pages.add(complete);
            pages.add(selector);
            pages.add(menuSelector);
            pages.add(menu);
            pagesLoaded = true;
        }
    }

    private void sortSizing() {
        float x = Gdx.graphics.getWidth();
        float y = Gdx.graphics.getHeight();

        Constants.SCREEN_WIDTH  = 1920;
        Constants.SCREEN_HEIGHT = (int) (1920 * y / x);
    }

    @Override
    public void render(float delta) {

        touch.update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for(Page page : pages){
            if(page.pageState == Page.PAGE_STATE.ON){
                touch.setTouchablePage(page);
            }
        }
        for(Page page : pages){
            if(page.pageState == Page.PAGE_STATE.COMING || page.pageState == Page.PAGE_STATE.GOING){
                touch.setTouchablePage(null);
            }
        }
        if(loadingScreen.pageState != Page.PAGE_STATE.OFF){
            touch.setTouchablePage(null);
        }
        for(Page page : pages){
            page.render(delta, batch);
        }

        loadingScreen.render(delta, batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
       // selector.closeFiles();
        menuSelector.dispose();
        manager.dispose();
    }
}
