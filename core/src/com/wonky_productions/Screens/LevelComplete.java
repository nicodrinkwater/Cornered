package com.wonky_productions.Screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.wonky_productions.Touch;

public class LevelComplete extends Page {

    boolean goTOMenuInstead;
    Color actualTitleColor, actualTitleFrameColor;
    GlyphLayout titleLayout;
    String title;
    float glow, timer, fact, glowTimer;
    int n;

    public LevelComplete(Master master, AssetManager manager, Touch touch,START_POS startPos, EXIT_POS exitPos, Color background) {
        super(master, manager, touch, startPos, exitPos, background);
        atlas = manager.get("Grid.pack", TextureAtlas.class);
        square = atlas.findRegion("square");
        backTile = atlas.findRegion("TrackTileFlatter");
        backTileSize = 60;
        timer = 0;
        fact = 0.1f;

        actualTitleColor = new Color(0,0,1,1);
        actualTitleFrameColor = new Color(0,0,1,1);
        titleLayout = new GlyphLayout();
        glow = 1;
        n = 0;
        title = "CONGRATULATIONS!!";
        barSize = 70;
    }

    @Override
    protected void update(float delta) {
        super.update(delta);

        glowTimer += delta;
        timer += delta;

        if(pageState == PAGE_STATE.ON) {
            if (glowTimer < fact / 2) {
                // glow -= delta * 5;
            } else if (glowTimer < fact) {
                // glow += delta * 5;
            } else {
                glowTimer = 0;
                if (n < title.length())
                    n++;
            }
        }

        goTOMenuInstead = false;

        if(levelReset){
            levelReset = false;
            master.selector.moveUpOne();
            if(goTOMenuInstead){
                master.menuSelector.pageState = PAGE_STATE.COMING;
            } else {
                master.selector.pageState = PAGE_STATE.COMING;
            }
        }

        if(touch.getState(this) == Touch.STATE.JUST_TOUCHED && master.menu.pageState == PAGE_STATE.OFF){
            levelReset = true;
            master.level.resetEverything();
        }

        if(pageState == PAGE_STATE.ON){
            master.level.pageState = PAGE_STATE.OFF;
        }

        if(master.menuSelector.pageState == PAGE_STATE.ON || master.selector.pageState == PAGE_STATE.ON){
            glowTimer = 0;
            timer = 0;
            n = 0;
            turnPageOff();
        }
    }

    @Override
    protected void draw(SpriteBatch batch) {

        batch.setColor(actualTitleColor.r/4, actualTitleColor.g/4, actualTitleColor.b/4, 1);
        for (int i = 0; i < scrHeight / backTileSize + 1; i++) {
            for (int j = 0; j < scrWidth / backTileSize + 1; j++) {
                batch.draw(backTile, j * backTileSize + movement.x, i * backTileSize + movement.y, backTileSize, backTileSize);
            }
        }
        drawTitle(batch);
        drawBar(batch);
    }

    public void drawTitle(SpriteBatch batch) {

        batch.setColor(0,0,0, 0.4f);

        int width = 1360;
        int height = 300;

        float d = scrWidth/2 -  width/2 + movement.x;
        float h = scrHeight/2 - height/2;

        batch.draw(square, d, h, width, height);
        batch.draw(square, d + 10, h + 10, width - 20, height - 20);

        batch.setColor(actualTitleColor.r/2, actualTitleColor.g/2, actualTitleColor.b/2, 1f);

        batch.draw(square, d + 20, h + 20, width - 40, height - 40);

        batch.setColor(0,0,0,0.8f);
        batch.draw(square, d + 26, h + 26, width - 52, height - 52);


        titleLayout.setText(fLarge, title.substring(0, n));

   /*     fLarge.setColor(0.3f,0.3f,0.3f, 0.6f * glow);
        fLarge.draw(batch, title.substring(0, n), d + width/2 - 470, h + 200 - 8);
*/
        fLarge.setColor(actualTitleColor.r, actualTitleColor.g, actualTitleColor.b, 1 * glow);
        fLarge.draw(batch, title.substring(0, n), d + width/2 - 510, h + 180);
    }

    public void goToMenu() {
        goTOMenuInstead = true;
    }

    public void setColor(Color color){
        actualTitleColor = color;
    }
}
