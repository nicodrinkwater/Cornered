package com.wonky_productions.Objects;


import com.badlogic.gdx.graphics.Color;
import com.wonky_productions.FileManager;

import java.io.IOException;

public class LevelData {

    Color trackGreyCol, trackColor, wallFlashCol, back, car1, carCross, score, background, wallColor, wallShadowColor;

    int squareSize, carSize, startSpeed, trackTile;

    int trackIn, trackOut, backNumber, backSize;

    int percent, numberGames, numberTotal;

    int layoutWidth, layoutHeight;
    float trackShadow, backDarkness, backIntensity, backRange, backDepth, totalTime;
    float speedRating, gripRating, lengthRating;
    String name;

    FileManager fileManager;

    public int getNumberTotal() {
        return fileManager.getTotalNumb();
    }

    public void setNumberTotal(int numberTotal) {
        this.numberTotal = numberTotal;
    }

    public int getNumberGames() {
        return numberGames;
    }

    public void setNumberGames(int numberGames) {
        this.numberGames = numberGames;
    }

    public int getPercent() {
        return fileManager.getPercent();
    }

    public void setPercent(int percent) {

        fileManager.setPercent(percent);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getWallColor() {
        return wallColor;
    }

    public Color getWallShadowColor() {
        return wallShadowColor;
    }

    public Color getBackground() {
        return background;
    }

    public Color getTrackGreyCol() {
        return trackGreyCol;
    }

    public Color getTrackColor() {
        return trackColor;
    }

    public Color getWallFlashCol() {
        return wallFlashCol;
    }

    public Color getBack() {
        return back;
    }

    public Color getCar1() {
        return car1;
    }

    public Color getCarCross() {
        return carCross;
    }

    public Color getScore() {
        return score;
    }

    public int getSquareSize() {
        return squareSize;
    }

    public int getCarSize() {
        return carSize;
    }

    public int getStartSpeed() {
        return startSpeed;
    }

    public int getBackNumber() {
        return backNumber;
    }

    public int getBackSize() {
        return backSize;
    }

    public float getBackDepth() {
        return backDepth;
    }

    public float getInitialGrip() {
        return initialGrip;
    }

    float initialGrip;

    public LevelData(String name) {
        sortData(name);
    }

    public float getTrackShadow() {
        return trackShadow;
    }

    public float getBackDarkness() {
        return backDarkness;
    }

    public float getBackIntensity() {
        return backIntensity;
    }

    public float getBackRange() {
        return backRange;
    }

    public int getTrackIn() {
        return trackIn;
    }

    public int getTrackOut() {
        return trackOut;
    }

    public float getTotalTime() {
        return totalTime;
    }

    public float getSpeedRating() {
        return speedRating;
    }

    public float getGripRating() {
        return gripRating;
    }

    public int getLayoutWidth() {
        return layoutWidth;
    }

    public int getLayoutHeight() {
        return layoutHeight;
    }

    public void sortData(String name) {

        if (name == "ONE") {
            squareSize = 60;
            carSize = (int) (2.3f * squareSize);
            startSpeed = squareSize * 8;

            trackGreyCol = new Color(0.3f, 0.3f, 0.3f, 0.6f);
            trackColor = new Color(0.f, 0.4f, 0.6f, 0.6f);
            wallFlashCol = new Color(1f, 1f, 0.5f, 1);

            car1 = new Color(0.4f, 0.8f, 0.4f, 1f);
            carCross = new Color(1f, 1f, 1f, 1);

            back = new Color(0.2f, 0.3f, 0.2f, 0.8f);
            background = new Color(0.1f, 0.1f, 0.1f, 1);

            score = new Color(0.7f, 0f, .7f, 1);

            initialGrip = 5f;

            trackIn = 800;
            trackOut = 1800;
            trackShadow = 1;

            backDarkness = 0.3f;
            backIntensity = 0.3f;
            backRange = 0.6f;

            wallColor = new Color(0f, 0f, 0, 1);
            wallShadowColor = new Color(0.2f, 0.7f, 0f, 1);

            backNumber = 2;
            backDepth = 2f;
            backSize = 3 * squareSize;

            totalTime = 13f;

            trackTile = 3;

            speedRating = 0.1f;
            gripRating = 0.99f;
            lengthRating = 0.1f;

            layoutWidth = 200;
            layoutHeight = 200;

            this.name = "ONE";
        } else if (name == "TWO") {
            squareSize = 60;
            carSize = (int) (2.3f * squareSize);
            startSpeed = squareSize * 10;

            car1 = new Color(0f, 0.6f, 0.2f, 1f);
            carCross = new Color(1f, 1f, 1f, 1);

            trackGreyCol = new Color(0f, 0.5f, 0.5f, 0.6f);
            trackColor = new Color(0.3f, 0.5f, 0.3f, 0.6f);
            wallFlashCol = new Color(0.8f, 0.6f, 0f, 1);

            back = new Color(0.5f, 0.5f, 0f, 0.5f);
            background = new Color(0, 0, 0f, 1);

            score = new Color(0.6f, 0f, 0.3f, 1);

            initialGrip = 1f;

            trackIn = 800;
            trackOut = 1800;
            trackShadow = 1f;

            backDarkness = 0.6f;
            backIntensity = 0.1f;
            backRange = 0.3f;

            wallColor = new Color(0.2f, 0, 0, 1);
            wallShadowColor = new Color(0.4f, 0.8f, 0f, 0.5f);

            backNumber = 4;
            backDepth = 2f;
            backSize = 4 * squareSize;

            totalTime = 9.2f;

            trackTile = 1;

            gripRating = 0.9f;
            speedRating = 0.2f;
            lengthRating = 0.25f;

            layoutWidth = 200;
            layoutHeight = 200;

            this.name = "TWO";
        } else if (name == "THREE") {
            squareSize = 60;
            carSize = (int) (2.3f * squareSize);
            startSpeed = squareSize * 10;

            car1 = new Color(1f, 0.4f, 0f, 1f);
            carCross = new Color(1f, 1f, 1f, 1);

            trackGreyCol = new Color(0.6f, 0.36f, 0.1f, 0.6f);
            trackColor = new Color(0.6f, 0.6f, 0.1f, 0.6f);
            wallFlashCol = new Color(1f, 1f, 1f, 1);

            back = new Color(0.4f, 0.2f, 0.2f, 0.5f);
            background = new Color(0, 0, 0f, 1);

            score = new Color(0.6f, 0f, 0.3f, 1);

            initialGrip = 2f;

            trackIn = 800;
            trackOut = 1800;
            trackShadow = 1f;

            backDarkness = 0.6f;
            backIntensity = 0.1f;
            backRange = 0.3f;

            wallColor = new Color(0.2f, 0, 0, 1);
            wallShadowColor = new Color(1f, 0.4f, 0f, 0.5f);

            backNumber = 2;
            backDepth = 2f;
            backSize = 4 * squareSize;

            totalTime = 11.5f;

            trackTile = 1;

            gripRating = 0.6f;
            speedRating = 0.23f;
            lengthRating = 0.3f;

            layoutWidth = 200;
            layoutHeight = 200;

            this.name = "THREE";

        }  else if (name == "FOUR") {
            squareSize = 60;
            carSize = (int) (2.3f * squareSize);
            startSpeed = squareSize * 10;

            car1 = new Color(1f, 0f, 0f, 1f);
            carCross = new Color(1f, 1f, 0f, 1);

            trackGreyCol = new Color(1f, 0.6f, 0.2f, 0.4f);
            trackColor = new Color(1f, 0.6f, 0.2f, 0.4f);
            wallFlashCol = new Color(1f, 1f, 0f, 1);

            back = new Color(0.6f, 0.2f, 0.2f, 0.6f);
            background = new Color(0, 0, 0f, 1);

            score = new Color(0.4f, 0f, 0.8f, 1);

            initialGrip = 1f;

            trackIn = 800;
            trackOut = 1800;
            trackShadow = 1f;

            backDarkness = 0.6f;
            backIntensity = 0.2f;
            backRange = 0.3f;

            wallColor = new Color(0.f, 0, 0, 1);
            wallShadowColor = new Color(1f, 0f, 0f, 0.5f);

            backNumber = 5;
            backDepth = 2f;
            backSize = 4 * squareSize;

            totalTime = 11.5f;

            trackTile = 1;

            gripRating = 0.3f;
            speedRating = 0.2f;
            lengthRating = 0.25f;

            layoutWidth = 200;
            layoutHeight = 200;

            this.name = "FOUR";

        } else if (name == "FIVE") {
            squareSize = 60;
            carSize = (int) (2.3f * squareSize);
            startSpeed = squareSize * 14;

            car1 = new Color(1f, 0f, 1f, 1f);
            carCross = new Color(0.6f, 0.6f, 1f, 1);

            trackGreyCol = new Color(0.65f, 0.65f, 1f, 0.8f);
            trackColor = new Color(0.65f, 0.65f, 1f, 0.8f);
            wallFlashCol = new Color(0.4f, 0.4f, 1f, 1);

            back = new Color(0f, 0.2f, 0.6f, 0.6f);
            background = new Color(0, 0, 0f, 1);

            score = new Color(0.4f, 0f, 0.8f, 1);

            initialGrip = 1f;

            trackIn = 400;
            trackOut = 1200;
            trackShadow = 1f;

            backDarkness = 0.6f;
            backIntensity = 0.2f;
            backRange = 0.3f;

            wallColor = new Color(0.f, 0, 0, 1);
            wallShadowColor = new Color(1f, 0f, 1f, 1f);

            backNumber = 5;
            backDepth = 2.4f;
            backSize = 3 * squareSize;

            totalTime = 7.4f;

            trackTile = 3;

            gripRating = 0.9f;
            speedRating = 0.7f;
            lengthRating = 0.3f;

            layoutWidth = 200;
            layoutHeight = 200;

            this.name = "FIVE";

        } else if (name == "SIX") {
            squareSize = 60;
            carSize = (int) (2.3f * squareSize);
            startSpeed = squareSize * 14;

            car1 = new Color(0f, 1f, 1f, 1f);
            carCross = new Color(0.8f, 0.6f, 1f, 1);

            trackGreyCol = new Color(0.4f, 0.6f, 1f, 0.2f);
            trackColor = new Color(0.4f, 0.6f, 1f, 0.4f);
            wallFlashCol = new Color(0.4f, 0.8f, 1f, 1);

            back = new Color(0.3f, 0.2f, 0.6f, 0.6f);
            background = new Color(0, 0, 0f, 1);

            score = new Color(0.4f, 0f, 0.8f, 1);

            initialGrip = 0.6f;

            trackIn = 200;
            trackOut = 1100;
            trackShadow = 1f;

            backDarkness = 0.7f;
            backIntensity = 0.2f;
            backRange = 0.3f;

            wallColor = new Color(0.f, 0, 0, 1);
            wallShadowColor = new Color(0f, 1f, 1f, 1f);

            backNumber = 5;
            backDepth = 2.4f;
            backSize = 3 * squareSize;

            totalTime = 8.8f;

            trackTile = 1;

            gripRating = 0.4f;
            speedRating = 0.44f;
            lengthRating = 0.2f;

            layoutWidth = 200;
            layoutHeight = 200;

            this.name = "SIX";

        }  else if (name == "SEVEN") {
            squareSize = 60;
            carSize = (int) (2.3f * squareSize);
            startSpeed = squareSize * 18;

            car1 = new Color(1f, 0.8f, 1f, 1f);
            carCross = new Color(0.8f, 0.6f, 1f, 1);

            trackGreyCol = new Color(0.4f, 0.4f, 0.4f, 0.6f);
            trackColor = new Color(0.5f, 0.5f, 0.5f, 0.6f);
            wallFlashCol = new Color(1f, 0.8f, 1f, 1);

            back = new Color(0.3f, 0.2f, 0.4f, 0.6f);
            background = new Color(0, 0, 0f, 1);

            score = new Color(0.4f, 0f, 0.8f, 1);

            initialGrip = 2f;

            trackIn = 700;
            trackOut = 1900;
            trackShadow = 1f;

            backDarkness = 0.7f;
            backIntensity = 0.2f;
            backRange = 0.7f;

            wallColor = new Color(0.f, 0, 0, 1);
            wallShadowColor = new Color(0.8f, 0.8f, 0.8f, 1f);

            backNumber = 2;
            backDepth = 3.4f;
            backSize = 2 * squareSize;

            totalTime = 12.5f;

            trackTile = 2;

            gripRating = 0.9f;
            speedRating = 0.85f;
            lengthRating = 0.32f;

            layoutWidth = 200;
            layoutHeight = 200;

            this.name = "SEVEN";

        } else if (name == "EIGHT") {
            squareSize = 60;
            carSize = (int) (2.3f * squareSize);
            startSpeed = squareSize * 14;

            car1 = new Color(0.5f, 1f, 1f, 1f);
            carCross = new Color(0.4f, 0.6f, 1f, 1);

            trackGreyCol = new Color(0.7f, 0.2f, 0.4f, 0.6f);
            trackColor = new Color(0.7f, 0.2f, 0.5f, 0.6f);
            wallFlashCol = new Color(1f, 0.8f, 1f, 1);

            back = new Color(0.4f, 0.2f, 0.3f, 0.6f);
            background = new Color(0, 0, 0f, 1);

            score = new Color(0.8f, 0f, 0.4f, 1);

            initialGrip = 2f;

            trackIn = 700;
            trackOut = 1900;
            trackShadow = 1f;

            backDarkness = 0.7f;
            backIntensity = 0.2f;
            backRange = 0.7f;

            wallColor = new Color(0.f, 0, 0, 1);
            wallShadowColor = new Color(0.6f, 0.8f, 0.8f, 1f);

            backNumber = 4;
            backDepth = 4f;
            backSize = (int)(1.8f * squareSize);

            totalTime = 14.5f;

            trackTile = 2;

            gripRating = 0.7f;
            speedRating = 0.6f;
            lengthRating = 0.3f;

            layoutWidth = 200;
            layoutHeight = 200;

            this.name = "EIGHT";

        }  else if (name == "NINE") {
            squareSize = 60;
            carSize = (int) (2.3f * squareSize);
            startSpeed = squareSize * 14;

            car1 = new Color(0.3f, 0.8f, 0.7f, 1f);
            carCross = new Color(0.9f, 0.6f, 0.7f, 1);

            trackGreyCol = new Color(0.2f, 0.4f, 0.45f, 0.6f);
            trackColor = new Color(0.2f, 0.4f, 0.45f, 0.6f);
            wallFlashCol = new Color(0.6f, 0.8f, 1f, 1);

            back = new Color(0.9f, 0.6f, 0.7f, 0.4f);
            background = new Color(0, 0, 0f, 1);

            score = new Color(0.8f, 0f, 0.4f, 1);

            initialGrip = 1f;

            trackIn = 400;
            trackOut = 1500;
            trackShadow = 1f;

            backDarkness = 0.2f;
            backIntensity = 0.1f;
            backRange = 0.4f;

            wallColor = new Color(0.f, 0, 0, 1);
            wallShadowColor = new Color(0.6f, 0.8f, 0.4f, 1f);

            backNumber = 1;
            backDepth = 2f;
            backSize = (int)(3f * squareSize);

            totalTime = 13.3f;

            trackTile = 2;

            gripRating = 0.5f;
            speedRating = 0.6f;
            lengthRating = 0.3f;

            layoutWidth = 200;
            layoutHeight = 200;

            this.name = "NINE";

        }  else if (name == "TEN") {
            squareSize = 60;
            carSize = (int) (2.3f * squareSize);
            startSpeed = squareSize * 14;

            car1 = new Color(1f, 0.4f, 0.4f, 1f);
            carCross = new Color(0.9f, 0.6f, 0.7f, 1);

            trackGreyCol = new Color(0.2f, 0.4f, 0f, 0.6f);
            trackColor = new Color(0.2f, 0.4f, 0f, 0.6f);
            wallFlashCol = new Color(1f, 0.4f, 0.4f, 1);

            back = new Color(0.4f, 0.1f, 0f, 0.8f);
            background = new Color(0, 0, 0f, 1);

            score = new Color(0.8f, 0f, 0.4f, 1);

            initialGrip = 0.9f;

            trackIn = 200;
            trackOut = 1000;
            trackShadow = 1f;

            backDarkness = 0.5f;
            backIntensity = 0.2f;
            backRange = 0.3f;

            wallColor = new Color(0.f, 0, 0, 1);
            wallShadowColor = new Color(0.6f, 0f, 0.4f, 1f);

            backNumber = 1;
            backDepth = 2f;
            backSize = (int)(3f * squareSize);

            totalTime = 28.3f;

            trackTile = 2;

            gripRating = 0.5f;
            speedRating = 0.7f;
            lengthRating = 0.6f;

            layoutWidth = 200;
            layoutHeight = 200;

            this.name = "TEN";

        } else if (name == "REDWOOD") {
            squareSize = 60;
            carSize = (int) (2.3f * squareSize);
            startSpeed = squareSize * 14;

            car1 = new Color(0.8f, 0.3f, 0.3f, 1f);
            carCross = new Color(1f, 1f, 0.8f, 1);

            trackGreyCol = new Color(0.55f, 0.45f, 0.45f, 0.6f);
            trackColor = new Color(0.55f, 0.45f, 0.45f, 0.6f);
            wallFlashCol = new Color(1f, 1f, 0.5f, 1);

            back = new Color(0.4f, 0.3f, 0.2f, 0.7f);
            background = new Color(0, 0, 0f, 1);

            score = new Color(0.6f, 0f, 0.3f, 1);

            initialGrip = 2f;

            trackIn = 800;
            trackOut = 1800;
            trackShadow = 1f;

            backDarkness = 0.6f;
            backIntensity = 0.1f;
            backRange = 0.3f;

            wallColor = new Color(0f, 0, 0, 1);
            wallShadowColor = new Color(0.8f, 0.3f, 0.3f, 0.7f);

            backNumber = 1;
            backDepth = 2f;
            backSize = 4 * squareSize;

            totalTime = 20f;

            trackTile = 1;

            gripRating = 0.8f;
            speedRating = 0.4f;
            lengthRating = 0.5f;

            layoutWidth = 300;
            layoutHeight = 300;

            this.name = "REDWOOD";
        } else if (name == "SNOW") {
            squareSize = 60;
            carSize = (int) (2.3f * squareSize);
            startSpeed = squareSize * 20;

            car1 = new Color(0.6f, 0.6f, 0.7f, 1f);
            carCross = new Color(1f, 1f, 1f, 1);

            trackGreyCol = new Color(0.6f, 0.6f, 0.7f, 1f);
            trackColor = new Color(0.6f, 0.6f, 0.7f, 1f);
            wallFlashCol = new Color(0.7f, 0.7f, 1f, 1);

            back = new Color(0.3f, 0.3f, 0.4f, 0.8f);
            background = new Color(0, 0, 0f, 1);

            score = new Color(0.3f, 0f, 0.4f, 1);

            initialGrip = 0.5f;

            trackIn = 400;
            trackOut = 1800;
            trackShadow = 1f;

            backDarkness = 0.4f;
            backIntensity = 0.1f;
            backRange = 0.3f;

            wallColor = new Color(0f, 0, 0.3f, 1);
            wallShadowColor = new Color(0.4f, 0.4f, 0.4f, 0.5f);

            backNumber = 4;
            backDepth = 3f;
            backSize = 3 * squareSize;

            totalTime = 14.5f;

            trackTile = 1;

            gripRating = 0.1f;
            speedRating = 0.6f;
            lengthRating = 0.45f;

            layoutWidth = 200;
            layoutHeight = 200;

            this.name = "SNOW";
        } else if (name == "RIOT") {
            squareSize = 40;
            carSize = (int) (2.3f * squareSize);
            startSpeed = squareSize * 20;

            car1 = new Color(0.5f, 0f, 0.8f, 1f);
            carCross = new Color(0.2f, 0.8f, 0.8f, 1);

            trackGreyCol = new Color(0.3f, 0.5f, 0.3f, 0.6f);
            trackColor = new Color(0f, 0.5f, 0.1f, 0.6f);
            wallFlashCol = new Color(0.8f, 0.3f, 1f, 1);

            back = new Color(0.2f, 0.5f, 0.5f, 0.5f);
            background = new Color(0, 0, 0f, 1);

            score = new Color(0.3f, 0f, 0.4f, 1);

            initialGrip = 0.5f;

            trackIn = 200;
            trackOut = 1000;
            trackShadow = 1f;

            backDarkness = 0.4f;
            backIntensity = 0.1f;
            backRange = 0.3f;

            wallColor = new Color(0.2f, 0, 0, 1);
            wallShadowColor = new Color(0.4f, 0.4f, 0.4f, 0.5f);

            backNumber = 4;
            backDepth = 3f;
            backSize = 3 * squareSize;

            totalTime = 24;

            trackTile = 1;

            gripRating = 0.01f;
            speedRating = 0.8f;
            lengthRating = 0.4f;

            layoutWidth = 200;
            layoutHeight = 200;

            this.name = "RIOT";
        } else if (name == "ROYAL") {
            squareSize = 60;
            carSize = (int) (2.3f * squareSize);
            startSpeed = squareSize * 12;

            trackGreyCol = new Color(0.3f, 0.4f, 0.3f, 0.6f);
            trackColor = new Color(0.1f, 0.6f, 0.2f, 0.6f);
            wallFlashCol = new Color(1f, 1f, 0.5f, 1);

            car1 = new Color(0.9f, 0f, 0f, 1f);
            carCross = new Color(1f, 1f, 0.7f, 1);

            back = new Color(0.5f, 0.5f, 0f, 0.3f);
            background = new Color(0.4f, 0.4f, 0f, 1);

            score = new Color(0.7f, 0f, 0f, 1);

            initialGrip = 5f;

            trackIn = 800;
            trackOut = 1400;
            trackShadow = 1;

            backDarkness = 0.1f;
            backIntensity = 0.3f;
            backRange = 0.8f;

            wallColor = new Color(0, 0, 0, 1);
            wallShadowColor = new Color(0.9f, 0f, 0f, 1f);

            backNumber = 3;
            backDepth = 1.5f;
            backSize = 2 * squareSize;

            totalTime = 36.5f;

            trackTile = 2;

            speedRating = 0.3f;
            gripRating = 0.99f;
            lengthRating = 0.6f;

            layoutWidth = 200;
            layoutHeight = 200;

            this.name = "ROYAL";
        } else if (name == "EASY") {
            squareSize = 40;
            carSize = (int) (2.3f * squareSize);
            startSpeed = squareSize * 15;

            car1 = new Color(0f, 0.8f, 0.4f, 1f);
            carCross = new Color(1f, 0.8f, 0.4f, 1);

            trackGreyCol = new Color(0.5f, 0.3f, 0.8f, 0.8f);
            trackColor = new Color(0.3f, 0.4f, 0.4f, 0.8f);
            wallFlashCol = new Color(0.7f, 0.7f, 0.4f, 1);

            back = new Color(0.4f, 0.6f, 0.5f, 0.5f);
            background = new Color(0, 0, 0f, 1);

            score = new Color(0.3f, 0f, 0.4f, 1);

            initialGrip = 1f;

            trackIn = 900;
            trackOut = 1800;
            trackShadow = 1f;

            backDarkness = 0.5f;
            backIntensity = 0.2f;
            backRange = 0.2f;

            wallColor = new Color(0.f, 0f, 0, 1);
            wallShadowColor = new Color(0f, 0.4f, 0f, 1f);

            backNumber = 2;
            backDepth = 3f;
            backSize = 3 * squareSize;

            totalTime = 24;

            trackTile = 2;

            gripRating = 0.01f;
            speedRating = 0.8f;
            lengthRating = 0.7f;

            layoutWidth = 200;
            layoutHeight = 200;

            this.name = "EASY";
        } else if (name == "TRICKY") {
            squareSize = 40;
            carSize = (int) (2.3f * squareSize);
            startSpeed = squareSize * 23;

            car1 = new Color(0.8f, 0f, 0.4f, 1f);
            carCross = new Color(0.5f, 0.5f, 1f, 1);

            trackGreyCol = new Color(0.3f, 0.5f, 0.8f, 0.8f);
            trackColor = new Color(0.5f, 0.3f, 0.4f, 0.8f);
            wallFlashCol = new Color(0.6f, 0.2f, 0.6f, 1);

            back = new Color(0.6f, 0.4f, 0.5f, 0.5f);
            background = new Color(0, 0, 0f, 1);

            score = new Color(0.3f, 0f, 0.4f, 1);

            initialGrip = 1f;

            trackIn = 400;
            trackOut = 1500;
            trackShadow = 1f;

            backDarkness = 0.5f;
            backIntensity = 0.15f;
            backRange = 0.5f;

            wallColor = new Color(0.f, 0f, 0, 1);
            wallShadowColor = new Color(0.4f, 0f, 0f, 1f);

            backNumber = 1;
            backDepth = 3f;
            backSize = 3 * squareSize;

            totalTime = 24;

            trackTile = 3;

            gripRating = 0.01f;
            speedRating = 0.8f;
            lengthRating = 0.4f;

            layoutWidth = 200;
            layoutHeight = 200;

            this.name = "TRICKY";
        } else if (name == "FAST") {
            squareSize = 40;
            carSize = (int) (2.3f * squareSize);
            startSpeed = squareSize * 28;

            car1 = new Color(0.4f, 0f, 0.8f, 1f);
            carCross = new Color(1f, 0.5f, 1f, 1);

            trackGreyCol = new Color(0.f, 0.4f, 0.8f, 0.8f);
            trackColor = new Color(0.2f, 0.3f, 0.7f, 0.8f);
            wallFlashCol = new Color(0.8f, 0.2f, 0.4f, 1);

            back = new Color(0.6f, 0f, 0.5f, 0.5f);
            background = new Color(0, 0, 0f, 1);

            score = new Color(0.3f, 0f, 0.4f, 1);

            initialGrip = 1f;

            trackIn = 0;
            trackOut = 1100;
            trackShadow = 1f;

            backDarkness = 0.5f;
            backIntensity = 0.1f;
            backRange = 0.3f;

            wallColor = new Color(0.f, 0f, 0, 1);
            wallShadowColor = new Color(0, 0f, 0.4f, 1f);

            backNumber = 4;
            backDepth = 3f;
            backSize = 3 * squareSize;

            totalTime = 24;

            trackTile = 3;

            gripRating = 0.01f;
            speedRating = 0.8f;
            lengthRating = 0.4f;

            layoutWidth = 200;
            layoutHeight = 200;

            this.name = "FAST";
        } else {
            squareSize = 60;
            carSize = (int) (2.3f * squareSize);
            startSpeed = squareSize * 6;

            trackGreyCol = new Color(0.3f, 0.4f, 0.3f, 0.6f);
            trackColor = new Color(0.5f, 0.5f, 0.5f, 0.6f);
            wallFlashCol = new Color(1f, 1f, 1f, 1);

            car1 = new Color(0.7f, 0f, 0f, 1f);
            carCross = new Color(1f, 1f, 0.7f, 1);

            back = new Color(0.3f, 0.3f, 0.3f, 0.3f);
            background = new Color(0.4f, 0.4f, 0.4f, 1);

            score = new Color(0.7f, 0f, 0f, 1);

            initialGrip = 5f;

            trackIn = 800;
            trackOut = 1400;
            trackShadow = 1;

            backDarkness = 0.1f;
            backIntensity = 0.3f;
            backRange = 0.8f;

            wallColor = new Color(0, 0, 0, 1);
            wallShadowColor = new Color(0f, 0f, 0f, 1);

            backNumber = 2;
            backDepth = 2f;
            backSize = 3 * squareSize;

            totalTime = 36.5f;

            trackTile = 3;

            speedRating = 0.1f;
            gripRating = 0.99f;
            lengthRating = 0.1f;

            layoutWidth = 200;
            layoutHeight = 200;

            this.name = "BackUp";
        }

        fileManager = new FileManager(name);
        numberTotal = fileManager.getTotalNumb();
        percent = fileManager.getPercent();
        numberGames = fileManager.getNumGoes();
    }


    public void write(){
        fileManager.setPercent(percent);
        fileManager.setTotalNumb(numberTotal);
        fileManager.setNumGoes(numberGames);
        fileManager.write();
    }

    public void addAttempt(){
        numberTotal++;
        numberGames++;
    }

    public int getTrackTile() {
        return trackTile;
    }

    public float getLengthRating() {
        return lengthRating;
    }

    public void readFile() {
        try {
            fileManager.getDataFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readData() {
        numberTotal = fileManager.getTotalNumb();
        percent = fileManager.getPercent();
        numberGames = fileManager.getNumGoes();

    }

    public void makeGoesZero() {
        fileManager.setNumGoes(0);
        fileManager.write();
    }
}
