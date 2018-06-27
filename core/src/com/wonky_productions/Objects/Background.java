package com.wonky_productions.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.wonky_productions.Constants;
import com.wonky_productions.Screens.Level;
import com.sun.javafx.geom.AreaOp;
import com.wonky_productions.Constants;
import com.wonky_productions.Screens.Level;


public class Background {

    Vector2 cameraPos, movement, oldCameraPos;
    Color color;
    TextureAtlas atlas;
    TextureRegion square, realSquare;
    Level level;
    float depth, squareSize, squareGap;
    int firstX, firstY, rangeX, rangeY;
    float darkness, intensity, lightRange, ave;
    int m;
    int n;

    int z;
    int x;

    int type;


    public Background(Level level, Color color, TextureAtlas atlas, float depth, int size, int type) {
        this.level = level;
        this.color = new Color(color);
        this.atlas = atlas;
        this.depth = depth;
        this.type = type;

        cameraPos = level.getCameraPos();
        oldCameraPos = new Vector2(cameraPos);
        movement = new Vector2(0,0);

        squareSize = size;
        squareGap = 1.2f * squareSize;

        rangeX = (int) (Constants.SCREEN_WIDTH / squareGap) + 5;
        rangeY = (int) (Constants.SCREEN_HEIGHT / squareGap) + 5;
        firstX = 0;
        firstY = 0;

        darkness = 0.1f;
        intensity = 0.04f;
        lightRange = 0.2f;

        ave = (color.r + color.g + color.b) / 3;

        realSquare = atlas.findRegion("square");
    }

    public void setLight(float darkness, float intensity, float lightRange){
        this.darkness = darkness;
        this.intensity = intensity;
        this.lightRange = lightRange;
    }

    public void darken(float delta){
        darkness *= 1 - delta /4;
        lightRange *= 1 - delta /4;

        goGrey(delta);
    }

    private void goGrey(float delta) {

        delta *= 1.2f;
        color.r += (ave - color.r) * delta;
        color.g += (ave - color.g) * delta;
        color.b += (ave - color.b) * delta;
    }


    public void update(float delta){

        rangeX = (int) (Constants.SCREEN_WIDTH / squareGap) + 5;
        rangeY = (int) (Constants.SCREEN_HEIGHT / squareGap) + 5;
        movement.x = - cameraPos.x / depth;
        movement.y = - cameraPos.y / depth;

        oldCameraPos.set(cameraPos);

        firstX = (int) (( - movement.x + Constants.SCREEN_WIDTH / 2)/ squareGap);
        firstY = (int) (( - movement.y + Constants.SCREEN_HEIGHT / 2)/ squareGap);
    }

    public void draw(SpriteBatch batch){

        for (int i = firstY; i < rangeY + firstY; i++) {

            for (int j = firstX; j < rangeX + firstX; j++) {

                sortPattern(i, j);

                float p = Math.abs(((j - 0.5f) * squareGap + movement.x - Constants.SCREEN_WIDTH));
                float q = Math.abs(((i - 0.5f) * squareGap + movement.y - Constants.SCREEN_HEIGHT));
                float dist = (float) Math.sqrt(p * p + q * q) / ( Constants.SCREEN_WIDTH + Constants.SCREEN_HEIGHT );
                if(dist < lightRange){
                    dist = darkness * dist / lightRange;
                } else {
                    dist = darkness;
                }
              /*  batch.setColor(
                        MathUtils.clamp(color.r * (darkness - dist - intensity / 2), 0, 1),
                        MathUtils.clamp(color.g * (darkness - dist - intensity /2),0, 1),
                        MathUtils.clamp(color.b * (darkness - dist- intensity/ 2), 0, 1) , 1);
                batch.draw(realSquare, (j - 1)  * squareGap + movement.x - Constants.SCREEN_WIDTH /2 - (squareGap - squareSize)/2, (i - 1) * squareGap + movement.y - Constants.SCREEN_HEIGHT / 2 - (squareGap - squareSize)/2, squareGap, squareGap);*/
                batch.setColor(color.r * (darkness - dist), color.g * (darkness - dist), color.b * (darkness - dist), color.a);
                batch.draw(square, (j - 1)  * squareGap + movement.x - Constants.SCREEN_WIDTH /2 - (squareGap - squareSize)/2, (i - 1) * squareGap + movement.y - Constants.SCREEN_HEIGHT / 2 - (squareGap - squareSize)/2, squareGap, squareGap);
                batch.setColor(color.r * (darkness + intensity - dist), color.g * (darkness + intensity - dist), color.b * (darkness + intensity - dist), color.a);
                batch.draw(square, (j - 1)  * squareGap + movement.x - Constants.SCREEN_WIDTH /2, (i - 1) * squareGap + movement.y - Constants.SCREEN_HEIGHT / 2, squareSize, squareSize);
                batch.setColor(color.r * (darkness + 2 * intensity - dist), color.g * (darkness + 2 * intensity - dist), color.b * (darkness + 2 * intensity - dist), color.a);
                batch.draw(square, (j - 1)  * squareGap + movement.x - Constants.SCREEN_WIDTH /2 + squareSize * 0.1f, (i - 1) * squareGap + movement.y - Constants.SCREEN_HEIGHT / 2  + squareSize * 0.1f, squareSize * 0.8f, squareSize * 0.8f);
            }
        }
    }

    private void sortPattern(int i, int j) {

        if(type == -1){
            m = i / 4;
            n = i - 4 * m;
            x = j / 3;
            z = j - 3 * x;
            if (n == z) {
                square = atlas.findRegion("DiSquareCross");
            } else {
                square = atlas.findRegion("DiSquare");
            }
        } else if(type == 0){
            square = atlas.findRegion("DiSquare");
        } else if(type == 1) {
            m = i / 3;
            n = i - 3 * m;
            x = j / 3;
            z = j - 3 * x;
            if (n > z) {
                square = atlas.findRegion("DiSquare");
            } else if (n == z) {
                square = atlas.findRegion("DiSquareInvert");
            } else if (n < z) {
                square = atlas.findRegion("DiSquareButton");
            }
        } else if(type == 2){
            m = i / 2;
            n = i - 2 * m;
            x = j / 2;
            z = j - 2 * x;
            if (n > z) {
                square = atlas.findRegion("DiSquare");
            } else if (n == z) {
                square = atlas.findRegion("DiSquareInvert");
            } else if (n < z) {
                square = atlas.findRegion("DiSquareButton");
            }
        } else if(type == 3){
            m = i / 2;
            n = i - 2 * m;
            x = j / 2;
            z = j - 2 * x;
            if (n == z) {
                square = atlas.findRegion("DiSquareInvert");
            } else {
                square = atlas.findRegion("DiSquareDark");
            }
        }
        else if(type == 4){
            m = i / 3;
            n = i - 3 * m;
            x = j / 2;
            z = j - 2 * x;
            if (n == z) {
                square = atlas.findRegion("DiSquareInvert2");
            } else {
                square = atlas.findRegion("DiSquareDark2");
            }
        }
        else if(type == 5){
            m = i / 4;
            n = i - 4 * m;
            x = j / 4;
            z = j - 4 * x;
            if (n == z) {
                square = atlas.findRegion("DiSquareInvert2");
            } else if(n > z + 1) {
                square = atlas.findRegion("DiSquareDark2");
            } else if(n < z - 1){
                square = atlas.findRegion("DiSquareButton");
            } else {
                square = atlas.findRegion("DiSquareDark");
            }
        }
    }


    public float getDarkness() {
        return darkness;
    }

    public void setDarkness(float darkness) {
        this.darkness = darkness;
    }

    public void restoreColor(Color backCol1) {
        color.set(backCol1);
    }
}
