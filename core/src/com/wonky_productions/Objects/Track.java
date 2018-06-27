package com.wonky_productions.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.wonky_productions.Constants;
import com.wonky_productions.Screens.Level;

public class Track {

    Level level;
    TextureAtlas atlas;
    TextureRegion square, circle, cross, speedUp, slowDown, halfShadow, halfShadow2, wallSide, wallTop, wallBL, wallBR, wallTL, wallTR;
    TextureRegion flat;
    float scrWidth, scrHeight;
    char[][] layout;
    Color origColor, indColor, backgroundColor, wallCol, wallShadCol, origWallShadCol;
    int squareSize, layoutHeight, layoutWidth;
    float scaleX, scaleY, rotation;
    int rangeX, rangeY, firstX, firstY;
    Vector2 cameraPosition, indSquareCenter, carCenter;
    Color trackColor;
    float beatTimer;
    boolean drawSkidMarks;
    float skidMarkSize;
    private Color skidColor, origSkidColor, lineCol;
    int a, n;
    float total;
    protected Color wallColor;
    float depth;
    int shadowHeight, shadowWidth;
    int tileType;

    float finishFlashTimer;
    float finishFlashAlpha;

    float lightIn, lightOut, shadow, ave1, ave2, ave3;

    public Color getFlashColor() {
        return lineCol;
    }

    enum DIRECTION {LEFT, RIGHT, DOWN, UP}
    int carGridX, carGridY;
    DIRECTION direction;
    public Track(Level level, String name, TextureAtlas atlas, int layoutWidth, int layoutHeight, int squareSize, Color color, Color skidColor, Color lineCol, int tileType, Color wallCol, Color wallshadCol){
        this.level = level;
        this.atlas = atlas;
        origColor = new Color(color);
        this.skidColor = new Color(skidColor);
        origSkidColor = new Color(skidColor);
        this.squareSize = squareSize;
        indColor = new Color(color.r, color.g, color.b, color.a);
        scaleX = 1;
        scaleY = 1;
        rotation = 0;
        this.layoutWidth = layoutWidth;
        this.layoutHeight = layoutHeight;
        wallColor = new Color(0,0,0,1);
        depth = 1;
        this.lineCol = new Color(lineCol);

        layout = level.getLayout();
        this.tileType = tileType;

        scrWidth = Constants.SCREEN_WIDTH;
        scrHeight = Constants.SCREEN_HEIGHT;
        indSquareCenter = new Vector2(0,0);
        square = atlas.findRegion("square");
        circle = atlas.findRegion("circle2");
        cross = atlas.findRegion("square");
        speedUp = atlas.findRegion("Ring");
        slowDown = atlas.findRegion("Ring");

        halfShadow = atlas.findRegion("halfShadow");
        halfShadow2 = atlas.findRegion("halfShadow2");

        wallSide = atlas.findRegion("wallShadowSide");
        wallTop = atlas.findRegion("wallShadowTop");
        wallBL = atlas.findRegion("wallShadowCornerBL");
        wallBR = atlas.findRegion("wallShadowCornerBR");
        wallTL = atlas.findRegion("wallShadowCornerTL");
        wallTR = atlas.findRegion("wallShadowCornerTR");


        cameraPosition = level.getCameraPos();
        backgroundColor = level.getBackgroundColor();
        direction = DIRECTION.RIGHT;

        carCenter = level.getCarCenter();
        trackColor = new Color(0,0,1,1);
        beatTimer = 0;

        skidMarkSize = 0;
        drawSkidMarks = false;
        shadowHeight = (int)(0.9f * squareSize);
        shadowWidth = (int)(0.3f * squareSize);
        finishFlashAlpha = 0;
        finishFlashTimer = 0;

        lightIn = 0;
        lightOut = 600;
        shadow = 1f;

        ave1 = (origColor.r + origColor.g + origColor.b) / 3;
        ave2 = (origSkidColor.r + origSkidColor.g + origSkidColor.b) / 3;

        this.wallCol = wallCol;
        this.wallShadCol = wallshadCol;
        origWallShadCol = new Color(wallshadCol);

        ave3 = (wallshadCol.r + wallshadCol.g + wallshadCol.b) / 3;
    }

    public void setLight(int lightIn, int lightOut, float shadow){
        this.lightIn = lightIn;
        this.lightOut = lightOut;
        this.shadow = shadow;
    }

    public void update(float delta){
        sortRange();
        if(tileType == 1) {
            flat = atlas.findRegion("TrackTileDark");
        } else if(tileType == 2){
            flat = atlas.findRegion("TrackTileFlat");
        } else if(tileType == 3){
            flat = atlas.findRegion("TrackTileFlatter");
        } else if(tileType == 4){
            flat = atlas.findRegion("TrackTilePlain");
        }

        if(level.getState() != Level.LEVEL_STATE.FINISHED) {
            finishFlashTimer += delta;
            if (finishFlashTimer < 0.5f) {
                finishFlashAlpha += delta * 2;
            } else if (finishFlashTimer < 1) {
                finishFlashAlpha -= delta * 2;
            } else {
                finishFlashTimer = 0;
            }  finishFlashAlpha = MathUtils.clamp(finishFlashAlpha, 0, 1);
        } else {
            finishFlashTimer += delta;
            if (finishFlashTimer < 0.25f) {
                finishFlashAlpha += delta * 4;
            } else if (finishFlashTimer < 0.5f) {
                finishFlashAlpha -= delta * 4;
            } else {
                finishFlashTimer = 0;
            }
            finishFlashAlpha = MathUtils.clamp(finishFlashAlpha, 0.25f, 0.75f);
        }
    }

    public void darken(float delta){
        lightIn *= 1 - delta /8;
        lightOut *= 1 - delta /8;

        goGrey(delta);

    }

    public void restoreColor(Color a, Color b, Color c){
        origColor.set(a);
        origSkidColor.set(b);
        lineCol.set(c);
        wallShadCol.set(origWallShadCol);
    }

    private void goGrey(float delta) {

        origSkidColor.r += (ave2 - origSkidColor.r) * delta;
        origSkidColor.g += (ave2 - origSkidColor.g) * delta;
        origSkidColor.b += (ave2 - origSkidColor.b) * delta;


        origColor.r += (ave1 - origColor.r) * delta;
        origColor.g += (ave1 - origColor.g) * delta;
        origColor.b += (ave1 - origColor.b) * delta;

        delta *= 0.7f;

        lineCol.r += (0.6f - lineCol.r) * delta;
        lineCol.g += (0.6f - lineCol.g) * delta;
        lineCol.b += (0.6f - lineCol.b) * delta;

        wallShadCol.r += (ave3 - wallShadCol.r) * delta;
        wallShadCol.g += (ave3 - wallShadCol.g) * delta;
        wallShadCol.b += (ave3 - wallShadCol.b) * delta;

    }


    public void sortDirection(){
        carGridX = (int)level.getCarGrid().x;
        carGridY = (int)level.getCarGrid().y;

        if (level.getCarDir() == Car.DIRECTION.UP) {
            if (carGridX < 5) {
                direction = DIRECTION.RIGHT;
                return;
            } else if (carGridX > layoutWidth - 6) {
                direction = DIRECTION.LEFT;
                return;
            } else {
                for (int i = 0; i < 100 ; i++) {
                    if(layout[carGridY + i][carGridX] == 'W'){
                        for (int j = 0; j < 100; j++) {
                            if(layout[carGridY + i - 1][carGridX - j] == 'W'){
                                direction = DIRECTION.RIGHT;
                                return;
                            } else if(layout[carGridY + i - 1][carGridX + j] == 'W'){
                                direction = DIRECTION.LEFT;
                                return;
                            }
                        }
                    }
                }
            }
        }

        if (level.getCarDir() == Car.DIRECTION.DOWN) {
            if (carGridX < 5) {
                direction = DIRECTION.RIGHT;
                return;
            } else if (carGridX > layoutWidth - 6) {
                direction = DIRECTION.LEFT;
                return;
            } else {
                for (int i = 0; i < 100 ; i++) {
                    if(layout[carGridY - i][carGridX] == 'W'){
                        for (int j = 0; j < 100; j++) {
                            if(layout[carGridY - i + 1][carGridX - j] == 'W'){
                                direction = DIRECTION.RIGHT;
                                return;
                            } else if(layout[carGridY - i + 1][carGridX + j] == 'W'){
                                direction = DIRECTION.LEFT;
                                return;
                            }
                        }
                    }
                }
            }
        }

        if (level.getCarDir() == Car.DIRECTION.RIGHT) {
            if (carGridY < 5) {
                direction = DIRECTION.UP;
                return;
            } else if (carGridY > layoutHeight - 6) {
                direction = DIRECTION.DOWN;
                return;
            } else {
                for (int i = 0; i < 100 ; i++) {
                    if(layout[carGridY][carGridX + i] == 'W'){
                        for (int j = 0; j < 100; j++) {
                            if(layout[carGridY - j][carGridX + i - 1] == 'W'){
                                direction = DIRECTION.UP;
                                return;
                            } else if(layout[carGridY + j][carGridX + i - 1] == 'W'){
                                direction = DIRECTION.DOWN;
                                return;
                            }
                        }
                    }
                }
            }
        }

        if (level.getCarDir() == Car.DIRECTION.LEFT) {
            if (carGridY < 5) {
                direction = DIRECTION.UP;
                return;
            } else if (carGridY > layoutHeight - 6) {
                direction = DIRECTION.DOWN;
                return;
            } else {
                for (int i = 0; i < 100 ; i++) {
                    if(layout[carGridY][carGridX - i] == 'W'){
                        for (int j = 0; j < 100; j++) {
                            if(layout[carGridY - j][carGridX - i + 1] == 'W'){
                                direction = DIRECTION.UP;
                                return;
                            } else if(layout[carGridY + j][carGridX - i + 1] == 'W'){
                                direction = DIRECTION.DOWN;
                                return;
                            }
                        }
                    }
                }
            }
        }
    }


    protected void sortRange() {

        firstX = (int) ((cameraPosition.x)/ squareSize);
        firstY = (int) ((cameraPosition.y)/ squareSize);

        if(firstX < 0){
            firstX = 0;
        }

        if(firstY < 0){
            firstY = 0;
        }

        rangeX = Constants.SCREEN_WIDTH / squareSize + 3;
        rangeY = Constants.SCREEN_HEIGHT / squareSize + 3;

    }


    public void drawWall(SpriteBatch batch){

        for (int i = firstY; i < MathUtils.clamp(firstY + rangeY, 0, layoutHeight); i++) {
            for (int j = firstX; j < MathUtils.clamp(firstX + rangeX, 0, layoutWidth); j++) {
                if (layout[i][j] == 'W') {

                    batch.setColor(0,0,0, 0.2f);

                    if(layout[i - 1][j] != '.' && layout[i][j -1] != '.' && layout[i -1][j -1] != '.'){
                        batch.draw(square, j * squareSize - shadowWidth - cameraPosition.x, i * squareSize - shadowHeight - cameraPosition.y, squareSize, squareSize);
                        if(layout[i][j + 1] != 'W'){
                            batch.draw(halfShadow, (j + 1) * squareSize - shadowWidth - cameraPosition.x, i * squareSize - shadowHeight - cameraPosition.y, shadowWidth, shadowHeight);
                        }
                        if(layout[i + 1][j] != 'W' && layout[i][j - 1] != 'W'){
                            batch.draw(halfShadow2, (j) * squareSize - shadowWidth - cameraPosition.x, (i + 1) * squareSize - shadowHeight - cameraPosition.y, shadowWidth, shadowHeight);
                        }
                    }
                }
            }
        }

            for (int i = firstY; i < MathUtils.clamp(firstY + rangeY, 0, layoutHeight); i++) {
                for (int j = firstX; j < MathUtils.clamp(firstX + rangeX, 0, layoutWidth); j++) {
                    if (layout[i][j] == 'W') {
                        trackColor.set(origColor);
                        skidColor.set(origSkidColor);
                        drawSkidMarks = false;
                       // sortColor(layout[i][j]);

                        batch.setColor(wallCol);
                        batch.draw(square, j * squareSize - cameraPosition.x, i * squareSize- cameraPosition.y, squareSize/2 , squareSize/2, squareSize, squareSize, scaleX, scaleY, rotation);

                        drawWall3d(batch, i, j);

                        float dist = Math.abs((j + 0.5f) * squareSize - carCenter.x) + Math.abs((i + 0.5f) * squareSize - carCenter.y);
                        if(dist < 13 * squareSize){
                            batch.setColor(lineCol.r, lineCol.g, lineCol.b, (13 * squareSize - dist) / (13 * squareSize));
                            if(layout[i][j + 1] == 'W' && layout[i][j - 1] == 'W') {
                                batch.draw(square, j * squareSize - cameraPosition.x, i * squareSize - cameraPosition.y, squareSize / 2, squareSize / 2, squareSize, squareSize, 1, 0.2f, rotation);
                            } else if (layout[i + 1][j] == 'W' && layout[i - 1][j] == 'W'){
                                batch.draw(square, j * squareSize - cameraPosition.x, i * squareSize -cameraPosition.y, squareSize / 2, squareSize / 2, squareSize, squareSize, 0.2f, 1, rotation);
                            } else if(layout[i][j + 1] == 'W' && layout[i + 1][j] == 'W'){
                                batch.draw(square, j * squareSize + 0.4f * squareSize - cameraPosition.x, i * squareSize + 0.4f * squareSize - cameraPosition.y, 0.6f * squareSize, 0.2f * squareSize);
                                batch.draw(square, j * squareSize + 0.4f * squareSize - cameraPosition.x, i * squareSize + 0.6f * squareSize- cameraPosition.y, 0.2f * squareSize, 0.4f * squareSize);
                            } else if(layout[i][j + 1] == 'W' && layout[i - 1][j] == 'W'){
                                batch.draw(square, j * squareSize + 0.4f * squareSize - cameraPosition.x, i * squareSize + 0.4f * squareSize- cameraPosition.y, 0.6f * squareSize, 0.2f * squareSize);
                                batch.draw(square, j * squareSize + 0.4f * squareSize- cameraPosition.x, i * squareSize- cameraPosition.y, 0.2f * squareSize, 0.4f * squareSize);
                            }else if(layout[i][j - 1] == 'W' && layout[i - 1][j] == 'W'){
                                batch.draw(square, j * squareSize- cameraPosition.x, i * squareSize + 0.4f * squareSize- cameraPosition.y, 0.6f * squareSize, 0.2f * squareSize);
                                batch.draw(square, j * squareSize + 0.4f * squareSize- cameraPosition.x, i * squareSize- cameraPosition.y, 0.2f * squareSize, 0.4f * squareSize);
                            }else if(layout[i][j - 1] == 'W' && layout[i + 1][j] == 'W'){
                                batch.draw(square, j * squareSize- cameraPosition.x, i * squareSize + 0.4f * squareSize- cameraPosition.y, 0.6f * squareSize, 0.2f * squareSize);
                                batch.draw(square, j * squareSize + 0.4f * squareSize- cameraPosition.x, i * squareSize + 0.6f * squareSize- cameraPosition.y, 0.2f * squareSize, 0.4f * squareSize);
                            }
                        }
                    }
                }
            }

    }

    private void drawWall3d(SpriteBatch batch, int i, int j) {
        float dist = Math.abs((j + 0.5f) * squareSize - carCenter.x) + Math.abs((i + 0.5f) * squareSize - carCenter.y);
        float a = 0;
        if(dist < 1.2 * lightIn){
            a = 1;
        } else if (dist >= 1.2 * lightIn && dist < 1.1f * lightOut){
            a = (1.1f * lightOut - dist) / (1.1f * lightOut - 1.2f * lightIn);
        }
        batch.setColor(wallShadCol.r, wallShadCol.g, wallShadCol.b, a * origWallShadCol.a);
        if(layout[i][j + 1] == 'W' && layout[i][j-1] == 'W'){
            batch.draw(wallTop,j * squareSize - cameraPosition.x, i * squareSize- cameraPosition.y, squareSize, squareSize);
        } else if(layout[i - 1][j] == 'W' && layout[i + 1][j] == 'W'){
            batch.draw(wallSide,j * squareSize - cameraPosition.x, i * squareSize- cameraPosition.y, squareSize, squareSize);
        } else if(layout[i - 1][j] == 'W' && layout[i][j - 1] == 'W'){
            batch.draw(wallTR,j * squareSize - cameraPosition.x, i * squareSize- cameraPosition.y, squareSize, squareSize);
        }else if(layout[i + 1][j] == 'W' && layout[i][j - 1] == 'W'){
            batch.draw(wallBR,j * squareSize - cameraPosition.x, i * squareSize- cameraPosition.y, squareSize, squareSize);
        }else if(layout[i - 1][j] == 'W' && layout[i][j + 1] == 'W'){
            batch.draw(wallTL,j * squareSize - cameraPosition.x, i * squareSize- cameraPosition.y, squareSize, squareSize);
        }else if(layout[i + 1][j] == 'W' && layout[i][j + 1] == 'W'){
            batch.draw(wallBL,j * squareSize - cameraPosition.x, i * squareSize- cameraPosition.y, squareSize, squareSize);
        }
    }

    public void draw(SpriteBatch  batch){
        for (int i = firstY; i < MathUtils.clamp(firstY + rangeY, 0, layoutHeight); i++) {
            for (int j = firstX; j < MathUtils.clamp(firstX + rangeX, 0, layoutWidth); j++) {
                if(layout[i][j] != '.' && layout[i][j] != 'W'){
                    trackColor.set(origColor);
                    skidColor.set(origSkidColor);
                    drawSkidMarks = false;
                    sortColor(layout[i][j]);
                    if(drawSkidMarks){
                        drawSkidMarks(batch, i, j);
                    }
                }
            }
        }
    }


    public float workShadow(int i, int j, float distance){

        float angleUp = (i + 0.5f) * squareSize - carCenter.y;
        float angleAcross = (j + 0.5f) * squareSize - carCenter.x;
        boolean hitWall = false;

        if(Math.abs(angleUp) > Math.abs(angleAcross)){
            int max = 4 * (int)(Math.abs(angleUp) / squareSize);

            for (int k = 0; k < max; k++) {
                if(checkHitWall( (j + 0.5f) * squareSize - k * (angleAcross / max), (i + 0.5f) * squareSize - k * (angleUp / max)) && !hitWall){
                    if(!hitWall) {
                        distance *= shadow;
                        return distance;
                    }
                }
            }
        } else {
            int max = 4 * (int)(Math.abs(angleAcross) / squareSize);
            for (int k = 0; k < max; k++) {
                if(checkHitWall( (j + 0.5f) * squareSize - k * (angleAcross / max), (i + 0.5f) * squareSize - k * (angleUp / max)) && !hitWall){
                    if(!hitWall) {
                        distance *= shadow;
                        hitWall = true;
                        return distance;
                    }
                }
            }
        }
        return distance;
    }


    protected void workLight(int i, int j) {
        float distance = Math.abs((i + 0.5f) * squareSize - carCenter.y) + Math.abs((j + 0.5f) * squareSize - carCenter.x);

     /*   if(shadow != 1) {
            distance = workShadow(i, j, distance);
        }*/

        if (distance < lightIn) {
            //indColor.a = 1;
            float f = 0.7f + 0.2f * (lightIn - distance) / lightIn;
            indColor.r *= f;
            indColor.g *= f;
            indColor.b *= f;

            skidColor.r *= f;
            skidColor.g *= f;
            skidColor.b *= f;

        } else if (distance >= lightIn && distance < lightOut) {
            //indColor.a = (1000 - distance) / 500;
            float f = 0.7f * (lightOut - distance) / (lightOut - lightIn);
            indColor.r *= f;
            indColor.g *= f;
            indColor.b *= f;

            skidColor.r *= f;
            skidColor.g *= f;
            skidColor.b *= f;

        } else {
            indColor.r = 0f;
            indColor.g = 0f;
            indColor.b = 0f;

            skidColor.r = 0;
            skidColor.g = 0;
            skidColor.b = 0;
        }


    }

    public void drawSkidMarks(SpriteBatch batch, int i, int j) {

        float ave = getAverageSkid(i, j);
        float h =   (9 - ave) / 9;
        float f = h * h;

        rotation = 0;

        int m, n, x, z;

        trackColor.set(origColor);
        skidColor.set(origSkidColor);

        sortColor(layout[i][j]);
        workLight(i,j);

        indColor.r = indColor.r - f * (indColor.r - skidColor.r);
        indColor.g = indColor.g - f * (indColor.g - skidColor.g);
        indColor.b = indColor.b - f * (indColor.b - skidColor.b);
        batch.setColor(indColor.r, indColor.g, indColor.b, indColor.a + (1 - indColor.a) * h);
        batch.draw(square, j * squareSize - cameraPosition.x, i * squareSize -cameraPosition.y, squareSize, squareSize);
        batch.setColor(indColor.r, indColor.g, indColor.b, f /1.3f);
        batch.draw(flat, j * squareSize - cameraPosition.x, i * squareSize - cameraPosition.y, squareSize, squareSize);

        if(layout[i][j] == 'L'){

            batch.setColor(finishFlashAlpha, finishFlashAlpha, finishFlashAlpha, 1);
            batch.draw(square, j * squareSize - cameraPosition.x, i * squareSize - cameraPosition.y, squareSize, squareSize);

            if(layout[i][j + 1] == 'F' || layout[i][j - 1] == 'F') {
                batch.setColor(0, 0, 0, 1f);
                batch.draw(square, j * squareSize - cameraPosition.x, i * squareSize - cameraPosition.y, squareSize / 4, squareSize);
                batch.draw(square, j * squareSize + 0.75f * squareSize - cameraPosition.x, i * squareSize - cameraPosition.y, squareSize / 4, squareSize);
            } else {
                batch.setColor(0, 0, 0, 1f);
                batch.draw(square, j * squareSize - cameraPosition.x, i * squareSize - cameraPosition.y, squareSize, squareSize / 4);
                batch.draw(square, j * squareSize - cameraPosition.x, i * squareSize + 0.75f * squareSize - cameraPosition.y, squareSize, squareSize /4);
            }
        }
    }

    public float getAverageSkid(int i, int j){
        a = 0;
        n = 0;
        total = 0;

        a = getIntFromChar(i-1, j);
        if(a >= 0){
            n++;
            total += a;
        }
        a = getIntFromChar(i+1, j);
        if(a >= 0){
            n++;
            total += a;
        }
        a = getIntFromChar(i, j - 1);
        if(a >= 0){
            n++;
            total += a;
        }
        a = getIntFromChar(i, j + 1);
        if(a >= 0){
            n++;
            total += a;
        }

        a = getIntFromChar(i-1, j + 1);
        if(a >= 0){
            n++;
            total += a;
        }

        a = getIntFromChar(i-1, j - 1);
        if(a >= 0){
            n++;
            total += a;
        }

        a = getIntFromChar(i+1, j + 1);
        if(a >= 0){
            n++;
            total += a;
        }

        a = getIntFromChar(i+1, j - 1);
        if(a >= 0){
            n++;
            total += a;
        }
        if(n > 0) {
            return total / n;
        } else {
            return 0;
        }
    }

    private int getIntFromChar(int i, int j) {
        char c = layout[i][j];

        switch(c){
            case '0': return 0;
            case '1': return 1;
            case '2': return 2;
            case '3': return 3;
            case '4': return 4;
            case '5': return 5;
            case '6': return 6;
            case '7': return 7;
            case '8': return 8;
            case '9': return 9;
            default:  return -1;
        }
    }

    protected void sortColor(char c) {

        if(c == 'W'){
            indColor.set(wallColor);
            drawSkidMarks = false;
        } else if(c == 'F'){
            indColor.set(1f,1f,1f,1);
            skidColor.set(0.25f,0.25f,0.25f,1);
            drawSkidMarks = true;
        }
        else {
            indColor.set(trackColor);
            drawSkidMarks = true;
        }
    }

    public boolean checkHitWall(float x, float y) {
        int i = (int) (y / squareSize);
        int j = (int) (x / squareSize);

        try {
            if (layout[i][j] == 'W') {
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            return false;
        }
    }

    public char getSquare(Vector2 center) {
        try {
            return layout[(int) (center.y / squareSize)][(int) (center.x / squareSize)];
        } catch (Exception e){
            return '.';
        }
    }

    public DIRECTION getDirection() {
        return direction;
    }
}
