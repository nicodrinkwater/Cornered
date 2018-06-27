package com.wonky_productions.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.wonky_productions.Constants;
import com.wonky_productions.Screens.Level;


public class CornerCounter {

    Array<Scrap> explosion;
    TextureAtlas atlas;
    TextureRegion square, side, top, carImage;
    Vector2 position, cameraPosition;
    Color color, trackCol;
   // BitmapFont fontLarge, fontSmall;
    float count,totalDist, scrPercent, fall;
    Level level;
    int scrWidth, scrHeight;
    float alpha;
    float percentage, brightness, vel, acc;
    int x, y;
    boolean changePerc;
    Car car;

    public CornerCounter(Level level, TextureAtlas atlas, Car car,Color color,  float totalDist, Color trackCol) {
        this.atlas = atlas;
        this.level = level;
        this.totalDist = totalDist;
        this.color = new Color(color);
        this.trackCol = new Color(trackCol);
        this.car = car;
        square = atlas.findRegion("square");
        side = atlas.findRegion("barSide");
        top = atlas.findRegion("BarBar");
        carImage = atlas.findRegion("DiSquareDarkFlatter");
        count = 0;
        scrPercent = 0;
        position = new Vector2(100,100);
        scrWidth = Constants.SCREEN_WIDTH;
        scrHeight = Constants.SCREEN_HEIGHT;
        alpha = 1;
        cameraPosition = level.getCameraPos();
        percentage = 0;
        x = 60;
        y = 100;
        vel = 0;
        acc = 1300;

        explosion = new Array<Scrap>();

    }

    public void update(float delta){


        position.set(x + level.getCameraPos().x, y - 5 + (scrHeight - 2 * y + 10) * scrPercent + level.getCameraPos().y);
      /*  position.x = 50;
        position.y = 100;

        count = level.getCount();
        percentage = count / totalCorners;
        color.a = alpha;



        if(scrPercent < percentage){
            scrPercent += delta / 10;
        }*/

        scrPercent = level.getDistTravelled() / totalDist;

        if(scrPercent > 1){
            scrPercent = 1;
        }

        brightness = 0.5f;

        if(scrPercent > 0.8){
            brightness = 0.5f + (scrPercent - 0.8f) * 1.8f;
        }

        if(scrPercent >= 1){

        }

        if(car.isExploded()){
            if(explosion.size == 0){
                createExplosion();
            }
            vel += acc * delta;
            fall += delta * vel;
            if(fall > (scrHeight - 2 * y + 10) * scrPercent){
                fall = (scrHeight - 2 * y + 10) * scrPercent;
            }
            for (int i = 0; i < explosion.size; i++) {
                explosion.get(i).update(delta);
                try {
                    if (explosion.get(i).dead) {
                        explosion.removeIndex(i);
                    }
                } catch (Exception e){
                }
            }
        }
    }

    public void draw(SpriteBatch batch){

        for(Scrap scrap : explosion){
            scrap.draw(batch);
        }
        batch.setColor(0,0,0, 0.2f);
        batch.draw(square, x - 20, y -20, 40, scrHeight - 2 * y + 40);
        batch.setColor(0f, 0f, 0f, 1f);
        batch.draw(square, x - 14, y - 14,  28, scrHeight - 2 * y + 28);
        batch.setColor(0.1f, 0.1f, 0.1f, 1);
        batch.draw(square, x - 10, y - 10, 20, scrHeight - 2 * y + 20);
        batch.setColor(trackCol.r * brightness, trackCol.g * brightness, trackCol.b * brightness, 1);
        batch.draw(side, x - 5, y - 5, 10, (scrHeight - 2 * y + 10) * scrPercent - fall);
        batch.setColor(0.2f,0.2f,0.2f,1);
        batch.draw(carImage, x - 20, y - 20 + (scrHeight - 2 * y + 10) * scrPercent - fall, 40, 40);
    }


    public void reset(Color scoreCol, float totalDist, Car car) {
        color.set(scoreCol);
        this.totalDist = totalDist;
        percentage = 0;
        scrPercent = 0;
        fall = 0;
        this.car = car;
        vel = 0;
        explosion.clear();
    }

    public float getY() {
        if(!car.isExploded()) {
            return y + (scrHeight - 2 * y + 10) * scrPercent;
        } else {
            return MathUtils.clamp(y + (scrHeight - 2 * y + 10) * scrPercent - fall, y, scrHeight);
        }
    }

    private void createExplosion() {
        for (int i = 0; i < 20; i++) {
            explosion.add(new ExplosionScrap(car, level.getTrack(), atlas, Car.DIRECTION.RIGHT, new Color(0,0,0,1), 100, position));
        }
    }

}
