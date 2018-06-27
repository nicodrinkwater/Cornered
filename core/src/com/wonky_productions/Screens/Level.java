package com.wonky_productions.Screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.wonky_productions.Constants;
import com.wonky_productions.Objects.*;
import com.wonky_productions.Touch;
import com.wonky_productions.Objects.Car;
import com.wonky_productions.Objects.FinalExplosionScrap;
import com.wonky_productions.Objects.Track;

public class Level extends Page {

    Car car;
    Track track;
    float XcamMovRange, YcamMovRange;
    int wallSquareSize, carSize;
    private Vector2 carGrid;
    Vector2 startPosition;
    Rectangle end;
    TrackCreater creater;
    TextureRegion triangle;
    Background backGround;
    float deadTimer, initialGrip;
    int cornerCount, totalCorners;
    Array<FinalExplosionScrap> finalExplosion;
    int number;
    private boolean swipeEnd;
    float swipeAmount;
    LevelData levelData;
    Color origBack;
    private boolean selectorComing;
    Array<Integer> corners;
    private float lapTime;


    public LEVEL_STATE getState() {
        return levelState;
    }

    public BitmapFont getMedFont() {
        return fMed;
    }

    public Color getFlashColor() {
        return track.getFlashColor();
    }


    public enum LEVEL_STATE {START, PLAYING, DEAD, FINISHED}
    LEVEL_STATE levelState;
    CornerCounter cornerCounter;
    int startSpeed;
    Pause pauseScreen;
    Color backCol1, wallCol1, wallCol2, wallCol3, carCol1, carCol2, scoreCol;

    int currentCorner;
    int percent, numberGoes;


    public Level(Master master, AssetManager manager, Touch touch, int number, Color background) {
        super(master, manager,touch, START_POS.DOWN, EXIT_POS.DOWN, background);

        atlas = manager.get("Grid.pack", TextureAtlas.class);
        square = atlas.findRegion("square");
        triangle = atlas.findRegion("Triangle");
        this.number = number;

        levelData = new LevelData("Learners");
        carGrid = new Vector2();
        createLevel(levelData);
        origBack = new Color(levelData.getBack());



        finalExplosion = new Array<FinalExplosionScrap>();

        sortLight(number);
        swipeAmount = 1;
        currentCorner = 0;
    }

    private void sortLight(int number) {
        backGround.setLight(levelData.getBackDarkness(), levelData.getBackIntensity(), levelData.getBackRange());
        track.setLight(levelData.getTrackIn(), levelData.getTrackOut(), levelData.getTrackShadow());
    }

    public void createLevel(LevelData data) {

        levelData = data;
        creater = new TrackCreater(data.getName());

        creater.printLayout();
        totalCorners = creater.getCornerCount();

        wallSquareSize = levelData.getSquareSize();
        carSize = levelData.getCarSize();
        startSpeed = levelData.getStartSpeed();

        carCol1 = levelData.getCar1();
        carCol2 = levelData.getCarCross();

        wallCol1 = levelData.getTrackGreyCol();
        wallCol2 = levelData.getTrackColor();
        wallCol3 = levelData.getWallFlashCol();

        backCol1 = levelData.getBack();
        backColor = levelData.getBackground();

        scoreCol = levelData.getScore();

        initialGrip = levelData.getInitialGrip();

        corners = creater.getCorners();

        lapTime = levelData.getTotalTime();

        startPosition = new Vector2(creater.getStartPos().x * wallSquareSize, creater.getStartPos().y * wallSquareSize);
        car = new Car(this, atlas, carSize, carCol1, carCol2, startSpeed, initialGrip);
        track = new Track(this, "Wall1", atlas, levelData.getLayoutWidth(), levelData.getLayoutHeight(), wallSquareSize, wallCol1, wallCol2, wallCol3, levelData.getTrackTile(), levelData.getWallColor(), levelData.getWallShadowColor());
        backGround = new Background(this, backCol1, atlas, levelData.getBackDepth(), levelData.getBackSize(), levelData.getBackNumber());

        end = creater.getEndRect();
        end.x *= wallSquareSize;
        end.y *= wallSquareSize;
        end.width *= wallSquareSize;
        end.height *= wallSquareSize;

        Constants.SQUARE_SIZE = wallSquareSize;
        levelState = LEVEL_STATE.START;

        if(cornerCounter != null) {
            cornerCounter.reset(scoreCol, levelData.getTotalTime(), car);
            pauseScreen.setColor(scoreCol);
        }

        finalExplosion = new Array<FinalExplosionScrap>();

        cornerCount = 0;

        car.setDirection(creater.getStartDir());

        cornerCounter  = new CornerCounter(this, atlas, car, scoreCol, levelData.getTotalTime(), new Color(0.8f, 0.8f, 0.8f, 1));

        pauseScreen = new Pause(this, touch, atlas, new Color(0.3f, 0.3f, 0.3f, 0.3f));



        resetEverything();
    }

    @Override
    protected void update(float delta) {

        percent = (int) (100  * car.getDistTravelled() / lapTime);
        if(percent > 100){
            percent = 100;
        }

        delta *= pauseScreen.getTimeSpeed();

        super.update(delta);

        if(selectorComing){
            if(master.selector.pageState == PAGE_STATE.ON){
                resetEverything();
                pageState = PAGE_STATE.OFF;
            }
        }

        moveCamera(delta);
        car.update(delta);
        track.update(delta);
        cornerCounter.update(delta);
        backGround.update(delta);
        pauseScreen.update(delta);
        carGrid.x =(int) car.getCenter().x / wallSquareSize;
        carGrid.y =(int) car.getCenter().y / wallSquareSize;


        for(FinalExplosionScrap  exp : finalExplosion){
            exp.update(delta);
        }


        if(car.isExploded()){
            backGround.darken(delta);
            track.darken(delta);
            goGrey(delta);
        }

        if(touch.getState(this) == Touch.STATE.JUST_TOUCHED){

            if(!swipeEnd && pauseScreen.checkButton()){

            } else {
                if (!car.isExploded() && levelState != LEVEL_STATE.START && car.getStartTimer() > 1) {
                    try {
                        car.changeDirection(corners.get(currentCorner));
                        currentCorner++;
                    } catch (Exception e){

                    }
                } else if (levelState == LEVEL_STATE.PLAYING || levelState == LEVEL_STATE.START) {
                    resetToStsrt();
                }
            }
        }

        if(levelState == LEVEL_STATE.DEAD){
            deadTimer += delta;
            if(deadTimer > 1.4f){
                swipeEnd = true;
            }
        } else if(levelState == LEVEL_STATE.FINISHED){
            deadTimer += delta;
            if(deadTimer > 2){
                master.complete.setColor(levelData.getCar1());
                master.complete.pageState = PAGE_STATE.COMING;
            }
        }

        if(swipeEnd){
            swipeAmount -= delta * 3;
            if(swipeAmount < 0 && levelState == LEVEL_STATE.DEAD){
                deadTimer = 0;
                resetToStsrt();
                levelState = LEVEL_STATE.PLAYING;
            }

            if(swipeAmount < -1){
                swipeEnd = false;
                swipeAmount = 1;
            }
        }

        if(end.contains(getCarCenter())){
            if (levelState != LEVEL_STATE.FINISHED) {
                master.selector.setIfToOpen();
                levelData.setPercent(100);
                writeData2();
                createFinalExplosion();
            }
            levelState = LEVEL_STATE.FINISHED;

            car.reachedEnd();
        }
    }

    private void goGrey(float delta) {
        backColor.a *= (1 - delta);
    }

    private void createFinalExplosion() {

        while(finalExplosion.size < 100){
            finalExplosion.add(new FinalExplosionScrap(car, track, atlas, Car.DIRECTION.DOWN, new Color(0,0,0,1), carSize));
        }
    }

    private void resetToStsrt() {

        car.reset();
        levelState = LEVEL_STATE.PLAYING;
        cornerCounter.reset(scoreCol, levelData.getTotalTime(), car);
        cornerCount = 0;
        deadTimer = 0;
        finalExplosion.clear();
        sortLight(number);
        track.restoreColor(wallCol1, wallCol2, wallCol3);
        backGround.restoreColor(backCol1);
        backColor.a = 1;
        currentCorner = 0;
        numberGoes++;


    }

    public void writeData() {
        levelData.setPercent(percent);
        levelData.write();
    }



    public void resetEverything() {
        resetToStsrt();
        car.resetAll();
        pauseScreen.reset();
        levelState = LEVEL_STATE.START;
        backColor.a = 1;
    }

    public void carHitWall(){
        levelState = LEVEL_STATE.DEAD;
        deadTimer = 0;
    }

    public void writeData2() {
        levelData.addAttempt();
        levelData.setPercent(percent);
        levelData.write();
    }

    public int getDirection(){

        return corners.get(currentCorner);
    }

    @Override
    protected void draw(SpriteBatch batch) {

        batch.setColor(backColor);
        batch.draw(square, 0, 0, scrWidth, scrHeight);
        backGround.draw(batch);
        track.draw(batch);
        for(FinalExplosionScrap  exp : finalExplosion){
            exp.draw(batch);
        }
        track.drawWall(batch);
        cornerCounter.draw(batch);
        car.draw(batch);
        if(!car.isExploded()) {
            car.drawBarCar(batch);
        }
        if(swipeEnd){
            drawResetSwipe(batch);
        }

        pauseScreen.draw(batch);
    }

    private void drawResetSwipe(SpriteBatch batch) {
        batch.setColor(0,0,0,1);
        batch.draw(square, swipeAmount * scrWidth, 0, scrWidth, scrHeight);
        int h = scrHeight/7;
        for (int i = 0; i < 4; i++) {
            batch.setColor(0,0,0,1);
            batch.draw(square, (1 + swipeAmount) * scrWidth, i * 2 *h, h, h);
            batch.draw(square, (swipeAmount) * scrWidth, i * 2 * h, -h, h);
        }
    }


    @Override
    public void goBackPage() {
        writeData();
        goBackToSelector();
    }


    public void moveCamera(float delta){

        cameraPos.x = car.getCameraPos().x;
        cameraPos.y = car.getCameraPos().y;
    }

    public void goBackToSelector() {
        master.selector.pageState = PAGE_STATE.COMING;
        selectorComing = true;
        master.selector.setRecords(numberGoes, percent);
    }

    public char[][] getLayout() {
        return creater.getLayout();
    }

    public Object getCarDir() {
        return car.getDirection();
    }

    public Vector2 getCarGrid() {
        return carGrid;
    }

    public Vector2 getCarCenter() {
        return car.getCenter();
    }

    public Track getTrack() {
        return track;
    }

    public void carSpeedUp() {
        car.speedUp();
    }

    public void carSlowDown() {
        car.slowDown();
    }

    public Vector2 getStartPos() {
        return startPosition;
    }

    public float getSquareSize() {
        return wallSquareSize;
    }

    public void addCornerToCount() {
        cornerCount++;
    }

    public Car getCar() {
        return car;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public int getCount() {
        return cornerCount;
    }

    public float getDistTravelled(){
        return car.getDistTravelled();
    }

    public float getBarCarY(){
        return cornerCounter.getY();
    }

}
