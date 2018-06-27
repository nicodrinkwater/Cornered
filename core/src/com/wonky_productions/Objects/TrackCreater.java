package com.wonky_productions.Objects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class TrackCreater {
    char layout[][];
    int startX, startY;
    int lastLeft, lastTop, lastRight, lastBottom, layoutLength, layoutHeight, skidStart;
    int width, height;
    Rectangle endRect;
    Vector2 startPos;
    int cornerCount;
    private Array<Integer> corners;

    public Array<Integer> getCorners() {
        return corners;
    }

    enum DIRECTION {LEFT, RIGHT, UP, DOWN}
    DIRECTION lastDir, startDir;

    public TrackCreater(String name) {
        cornerCount = 0;
        corners = new Array<Integer>();
        createTrack(name);
    }

    private void createTrack(String name) {

        if(name == "ONE"){
            lastLeft = 100;
            lastRight = 100;
            lastBottom = 10;
            lastTop = 20;
            skidStart = 0;
            lastDir = DIRECTION.UP;
            ceateLearners();
        } else if(name == "TWO"){
            lastLeft = 100;
            lastRight = 100;
            lastBottom = 10;
            lastTop = 20;
            skidStart = 0;
            lastDir = DIRECTION.UP;
            ceateSimple();
        }else if(name == "THREE"){
            lastLeft = 100;
            lastRight = 100;
            lastBottom = 10;
            lastTop = 20;
            skidStart = 0;
            lastDir = DIRECTION.UP;
            createThree();
        } else if(name == "FOUR"){
            lastLeft = 100;
            lastRight = 100;
            lastBottom = 10;
            lastTop = 20;
            skidStart = 0;
            lastDir = DIRECTION.UP;
            createFour();
        } else if(name == "FIVE"){
            lastLeft = 100;
            lastRight = 100;
            lastBottom = 10;
            lastTop = 20;
            skidStart = 0;
            lastDir = DIRECTION.UP;
            createFive();
        } else if(name == "SIX"){
            lastLeft = 100;
            lastRight = 100;
            lastBottom = 10;
            lastTop = 20;
            skidStart = 0;
            lastDir = DIRECTION.UP;
            createSix();
        } else if(name == "SEVEN"){
            lastLeft = 100;
            lastRight = 100;
            lastBottom = 10;
            lastTop = 20;
            skidStart = 0;
            lastDir = DIRECTION.UP;
            createSeven();
        } else if(name == "EIGHT"){
            lastLeft = 100;
            lastRight = 100;
            lastBottom = 10;
            lastTop = 20;
            skidStart = 0;
            lastDir = DIRECTION.UP;
            createEight();
        } else if(name == "NINE"){
            lastLeft = 100;
            lastRight = 100;
            lastBottom = 10;
            lastTop = 20;
            skidStart = 0;
            lastDir = DIRECTION.UP;
            createNine();
        } else if(name == "TEN"){
            lastLeft = 100;
            lastRight = 100;
            lastBottom = 10;
            lastTop = 20;
            skidStart = 0;
            lastDir = DIRECTION.UP;
            createTen();
        } else if(name == "REDWOOD"){
            lastLeft = 100;
            lastRight = 100;
            lastBottom = 10;
            lastTop = 20;
            skidStart = 0;
            lastDir = DIRECTION.LEFT;
            createRedwood();
        } else if(name == "SNOW"){
            lastLeft = 100;
            lastRight = 100;
            lastBottom = 10;
            lastTop = 20;
            skidStart = 0;
            lastDir = DIRECTION.LEFT;
            createSnow();
        } else if(name == "ROYAL"){
            lastLeft = 2;
            lastRight = 2;
            lastBottom = 2;
            lastTop = 2;
            skidStart = 0;
            lastDir = DIRECTION.LEFT;
            ceateRoyal();
        } else if(name == "RIOT"){
            lastLeft = 100;
            lastRight = 100;
            lastBottom = 10;
            lastTop = 20;
            skidStart = 7;
            lastDir = DIRECTION.UP;
            ceateRiot();
        } else {
            lastLeft = 10;
            lastRight = 10;
            lastBottom = 10;
            lastTop = 13;
            skidStart = 0;
            lastDir = DIRECTION.UP;
            ceateTrack0();
        }

        sortGrip();
    }

    private void createSnow() {
        layoutLength = 240;
        layoutHeight = 240;
        layout = new char[layoutHeight][layoutLength];
        startX = 100;
        startY = 10;

        for (int i = 0; i < layoutHeight; i++) {
            for (int j = 0; j < layoutLength; j++) {
                layout[i][j] = '.';
            }
        }
        startPos = new Vector2(103, 13);

        startDir = DIRECTION.RIGHT;
        addTrack(10, DIRECTION.RIGHT, 60, 4, "start");
        addTrack(10, DIRECTION.UP, 20, 4);
        addTrack(10, DIRECTION.LEFT, 30, 4);
        addTrack(10, DIRECTION.UP, 20, 4);
        addTrack(8, DIRECTION.LEFT, 20, 4);
        addTrack(8, DIRECTION.DOWN, 15, 4);
        addTrack(12, DIRECTION.LEFT, 20, 4);
        addTrack(7, DIRECTION.UP, 15, 4);
        addTrack(10, DIRECTION.LEFT, 20, 4);
        addTrack(12, DIRECTION.UP, 24, 4);
        addTrack(12, DIRECTION.LEFT, 18, 4);
        addTrack(10, DIRECTION.UP, 25, 4);
        addTrack(10, DIRECTION.RIGHT, 40, 4);
        addTrack(10, DIRECTION.UP, 15, 4);
        addTrack(10, DIRECTION.LEFT, 15, 4);
        addTrack(10, DIRECTION.UP, 15, 4);
        addEnd(14);
    }

    private void createRedwood() {
        layoutLength = 300;
        layoutHeight = 300;
        layout = new char[layoutHeight][layoutLength];
        startX = 100;
        startY = 10;

        for (int i = 0; i < layoutHeight; i++) {
            for (int j = 0; j < layoutLength; j++) {
                layout[i][j] = '.';
            }
        }


        startPos = new Vector2(101.9f, 23f);

        startDir = DIRECTION.UP;
        addTrack(6, DIRECTION.UP, 30, 2, "start");
        addTrack(5, DIRECTION.RIGHT, 20, 2);
        addTrack(5, DIRECTION.UP, 20, 2);
        addTrack(5, DIRECTION.RIGHT, 40, 2);
        addTrack(5, DIRECTION.DOWN, 14, 2);
        addTrack(6, DIRECTION.LEFT, 8, 2);
        addTrack(7, DIRECTION.DOWN, 10, 3);
        addTrack(6, DIRECTION.RIGHT, 10, 3);
        addTrack(6, DIRECTION.UP, 6, 3);
        addTrack(5, DIRECTION.RIGHT, 10, 3);
        addTrack(5, DIRECTION.UP, 10, 3);
        addTrack(6, DIRECTION.RIGHT, 10, 3);
        addTrack(5, DIRECTION.DOWN, 10, 3);
        addTrack(5, DIRECTION.RIGHT, 10, 3);
        addTrack(5, DIRECTION.UP, 10, 3);
        addTrack(6, DIRECTION.RIGHT, 10, 3);
        addTrack(7, DIRECTION.DOWN, 10, 3);
        addTrack(7, DIRECTION.RIGHT, 30, 3);
        addTrack(5, DIRECTION.UP, 10, 3);
        addTrack(5, DIRECTION.LEFT, 8, 3);
        addTrack(5, DIRECTION.UP, 8, 3);
        addTrack(5, DIRECTION.LEFT, 6, 3);
        addTrack(5, DIRECTION.UP, 30, 3);
        addTrack(7, DIRECTION.LEFT, 15, 3);
        addTrack(4, DIRECTION.DOWN, 10, 3);
        addTrack(4, DIRECTION.LEFT, 10, 3);
        addTrack(6, DIRECTION.UP, 22, 3);
        addTrack(4, DIRECTION.RIGHT, 20, 3);
        addTrack(6, DIRECTION.UP, 7, 3);
        addTrack(4, DIRECTION.RIGHT, 4, 3);
        addTrack(6, DIRECTION.UP, 7, 3);
        addTrack(4, DIRECTION.LEFT, 4, 3);
        addTrack(6, DIRECTION.UP, 7, 3);
        addTrack(4, DIRECTION.RIGHT, 4, 3);
        addTrack(6, DIRECTION.UP, 7, 3);
        addTrack(4, DIRECTION.LEFT, 4, 3);
        addTrack(6, DIRECTION.UP, 7, 3);
        addTrack(4, DIRECTION.RIGHT, 4, 3);
        addTrack(6, DIRECTION.UP, 7, 3);
        addTrack(4, DIRECTION.LEFT, 4, 3);
        addEnd(10);
    }

    private void createTen() {
        layoutLength = 300;
        layoutHeight = 300;
        layout = new char[layoutHeight][layoutLength];
        startX = 100;
        startY = 10;

        for (int i = 0; i < layoutHeight; i++) {
            for (int j = 0; j < layoutLength; j++) {
                layout[i][j] = '.';
            }
        }

        startPos = new Vector2(94.8f, 23f);


        startDir = DIRECTION.UP;
        addTrack(8, DIRECTION.UP, 30, 1, "start");
        addTrack(6, DIRECTION.RIGHT, 10, 4);
        addTrack(8, DIRECTION.UP, 20, 1);
        addTrack(7, DIRECTION.RIGHT, 10, 4);
        addTrack(6, DIRECTION.UP, 30, 4);
        addTrack(6, DIRECTION.RIGHT, 12, 1);
        addTrack(6, DIRECTION.UP, 14, 1);
        addTrack(7, DIRECTION.LEFT, 50, 0);
        addTrack(6, DIRECTION.DOWN, 13, 9);
        addTrack(10, DIRECTION.LEFT, 14, 5);
        addTrack(6, DIRECTION.UP, 30, 8);
        addTrack(10, DIRECTION.RIGHT, 12, 1);
        addTrack(5, DIRECTION.UP, 14, 2);
        addTrack(10, DIRECTION.LEFT, 50, 5);
        addTrack(10, DIRECTION.UP, 13, 9);
        addTrack(10, DIRECTION.RIGHT, 13, 3);
        addTrack(8, DIRECTION.UP, 10, 0);
        addTrack(5, DIRECTION.RIGHT, 40, 0);
        addTrack(5, DIRECTION.DOWN, 6, 0);
        addTrack(5, DIRECTION.RIGHT, 6, 0);
        addTrack(5, DIRECTION.DOWN, 6, 0);
        addTrack(5, DIRECTION.RIGHT, 40, 0);
        addEnd(8);
    }

    private void createNine() {
        layoutLength = 200;
        layoutHeight = 200;
        layout = new char[layoutHeight][layoutLength];
        startX = 100;
        startY = 10;

        for (int i = 0; i < layoutHeight; i++) {
            for (int j = 0; j < layoutLength; j++) {
                layout[i][j] = '.';
            }
        }

        startPos = new Vector2(94.8f, 23f);


        startDir = DIRECTION.UP;
        addTrack(8, DIRECTION.UP, 40, 1, "start");
        addTrack(6, DIRECTION.RIGHT, 10, 4);
        addTrack(6, DIRECTION.UP, 30, 8);
        addTrack(10, DIRECTION.RIGHT, 12, 1);
        addTrack(6, DIRECTION.UP, 14, 2);
        addTrack(10, DIRECTION.LEFT, 50, 5);
        addTrack(10, DIRECTION.DOWN, 13, 9);
        addTrack(10, DIRECTION.RIGHT, 13, 3);
        addTrack(8, DIRECTION.DOWN, 10, 0);
        addTrack(4, DIRECTION.LEFT, 20, 0);
        addEnd(8);
    }

    private void createEight() {
        layoutLength = 200;
        layoutHeight = 200;
        layout = new char[layoutHeight][layoutLength];
        startX = 100;
        startY = 10;

        for (int i = 0; i < layoutHeight; i++) {
            for (int j = 0; j < layoutLength; j++) {
                layout[i][j] = '.';
            }
        }
        startPos = new Vector2(103, 15f);

        startDir = DIRECTION.RIGHT;
        addTrack(8, DIRECTION.RIGHT, 40, 1, "start");
        addTrack(6, DIRECTION.UP, 10, 2);
        addTrack(6, DIRECTION.LEFT, 10, 4);
        addTrack(6, DIRECTION.UP, 30, 2);
        addTrack(6, DIRECTION.RIGHT, 8, 1);
        addTrack(6, DIRECTION.UP, 10, 2);
        addTrack(6, DIRECTION.LEFT, 50, 5);
        addTrack(6, DIRECTION.DOWN, 10, 7);
        addTrack(6, DIRECTION.RIGHT, 10, 8);
        addTrack(6, DIRECTION.DOWN, 10, 8);
        addTrack(6, DIRECTION.LEFT, 20, 1);
        addTrack(6, DIRECTION.DOWN, 10, 8);

        addEnd(8);
    }

    private void createSeven() {
        layoutLength = 200;
        layoutHeight = 200;
        layout = new char[layoutHeight][layoutLength];
        startX = 100;
        startY = 10;

        for (int i = 0; i < layoutHeight; i++) {
            for (int j = 0; j < layoutLength; j++) {
                layout[i][j] = '.';
            }
        }
        startPos = new Vector2(103, 15f);

        startDir = DIRECTION.RIGHT;
        addTrack(8, DIRECTION.RIGHT, 40, 1, "start");
        addTrack(6, DIRECTION.UP, 16, 2);
        addTrack(6, DIRECTION.LEFT, 40, 4);
        addTrack(6, DIRECTION.UP, 16, 2);
        addTrack(6, DIRECTION.RIGHT, 10, 1);
        addTrack(6, DIRECTION.UP, 40, 2);
        addTrack(6, DIRECTION.LEFT, 50, 3);
        addTrack(6, DIRECTION.DOWN, 16, 1);
        addTrack(6, DIRECTION.RIGHT, 10, 1);

        addEnd(8);
    }

    private void createSix() {
        layoutLength = 200;
        layoutHeight = 200;
        layout = new char[layoutHeight][layoutLength];
        startX = 100;
        startY = 10;

        for (int i = 0; i < layoutHeight; i++) {
            for (int j = 0; j < layoutLength; j++) {
                layout[i][j] = '.';
            }
        }
        startPos = new Vector2(94.8f, 23f);

        startDir = DIRECTION.UP;
        addTrack(8, DIRECTION.UP, 30, 0, "start");
        addTrack(10, DIRECTION.RIGHT, 20, 0);
        addTrack(10, DIRECTION.UP, 16, 2);
        addTrack(10, DIRECTION.LEFT, 10, 4);
        addTrack(10, DIRECTION.UP, 16, 5);
        addTrack(10, DIRECTION.RIGHT, 10, 6);
        addTrack(10, DIRECTION.UP, 16, 7);
        addTrack(10, DIRECTION.LEFT, 10, 8);
        addTrack(10, DIRECTION.UP, 16, 9);
        addTrack(10, DIRECTION.RIGHT, 10, 9);

        addEnd(12);
    }

    private void createFive() {
        layoutLength = 200;
        layoutHeight = 200;
        layout = new char[layoutHeight][layoutLength];
        startX = 100;
        startY = 10;

        for (int i = 0; i < layoutHeight; i++) {
            for (int j = 0; j < layoutLength; j++) {
                layout[i][j] = '.';
            }
        }
        startPos = new Vector2(94.8f, 23f);

        startDir = DIRECTION.UP;
        addTrack(8, DIRECTION.UP, 30, 0, "start");
        addTrack(8, DIRECTION.LEFT, 10, 0);
        addTrack(8, DIRECTION.UP, 10, 1);
        addTrack(6, DIRECTION.LEFT, 10, 2);
        addTrack(6, DIRECTION.UP, 10, 3);
        addTrack(6, DIRECTION.LEFT, 10, 2);
        addTrack(6, DIRECTION.UP, 10, 1);
        addTrack(8, DIRECTION.LEFT, 10, 3);
        addTrack(8, DIRECTION.UP, 10, 2);
        addTrack(8, DIRECTION.LEFT, 10, 2);

        addEnd(12);
    }

    private void createFour() {
        layoutLength = 200;
        layoutHeight = 200;
        layout = new char[layoutHeight][layoutLength];
        startX = 100;
        startY = 10;

        for (int i = 0; i < layoutHeight; i++) {
            for (int j = 0; j < layoutLength; j++) {
                layout[i][j] = '.';
            }
        }
        startPos = new Vector2(90, 14.6f);

        startDir = DIRECTION.LEFT;
        addTrack(8, DIRECTION.LEFT, 30, 0, "start");
        addTrack(8, DIRECTION.UP, 20, 2);
        addTrack(10, DIRECTION.LEFT, 10, 7);
        addTrack(10, DIRECTION.UP, 20, 9);
        addTrack(10, DIRECTION.RIGHT, 14, 8);
        addTrack(10, DIRECTION.DOWN, 12, 7);
        addTrack(8, DIRECTION.RIGHT, 12, 9);
        addTrack(8, DIRECTION.UP, 12, 7);
        addTrack(8, DIRECTION.RIGHT, 12, 2);

        addEnd(12);
    }

    private void createThree() {
        layoutLength = 240;
        layoutHeight = 240;
        layout = new char[layoutHeight][layoutLength];
        startX = 100;
        startY = 10;

        for (int i = 0; i < layoutHeight; i++) {
            for (int j = 0; j < layoutLength; j++) {
                layout[i][j] = '.';
            }
        }
        startPos = new Vector2(95, 23);

        startDir = DIRECTION.UP;
        addTrack(8, DIRECTION.UP, 60, 0, "start");
        addTrack(8, DIRECTION.LEFT, 12, 2);
        addTrack(6, DIRECTION.UP, 8, 1);
        addTrack(5, DIRECTION.LEFT, 20, 4);
        addTrack(5, DIRECTION.DOWN, 8, 5);
        addTrack(5, DIRECTION.RIGHT, 8, 6);
        addTrack(6, DIRECTION.DOWN, 8, 2);

        addEnd(8);
    }

    private void ceateSimple() {
        layoutLength = 240;
        layoutHeight = 240;
        layout = new char[layoutHeight][layoutLength];
        startX = 100;
        startY = 10;

        for (int i = 0; i < layoutHeight; i++) {
            for (int j = 0; j < layoutLength; j++) {
                layout[i][j] = '.';
            }
        }
        startPos = new Vector2(103, 16);

        startDir = DIRECTION.RIGHT;
        addTrack(6, DIRECTION.RIGHT, 50, 0, "start");
        addTrack(6, DIRECTION.UP, 12, 2);
        addTrack(6, DIRECTION.LEFT, 14, 3);
        addTrack(6, DIRECTION.UP, 12, 4);
        addTrack(6, DIRECTION.LEFT, 12, 4);

        addEnd(10);
    }

    private void ceateLearners() {
        layoutLength = 240;
        layoutHeight = 240;
        layout = new char[layoutHeight][layoutLength];
        startX = 100;
        startY = 10;

        for (int i = 0; i < layoutHeight; i++) {
            for (int j = 0; j < layoutLength; j++) {
                layout[i][j] = '.';
            }
        }
        startPos = new Vector2(104, 14.5f);

        startDir = DIRECTION.RIGHT;
        addTrack(8, DIRECTION.RIGHT, 60, 0, "start");
        addTrack(8, DIRECTION.UP, 20, 0);
        addTrack(6, DIRECTION.LEFT, 30, 0);
        addEnd(10);

    }

    private void ceateTrack0() {
        layoutLength = 400;
        layoutHeight = 50;
        layout = new char[layoutHeight][layoutLength];
        startX = 10;
        startY = 10;

        for (int i = 0; i < layoutHeight; i++) {
            for (int j = 0; j < layoutLength; j++) {
                layout[i][j] = '.';
            }
        }
        startPos = new Vector2(10, 20);

        startDir = DIRECTION.RIGHT;
        addTrack(4, DIRECTION.RIGHT, 300, 0);
        addEnd(10);
    }

    private void sortGrip() {

        for (int i = 0; i < layoutHeight; i++) {
            for (int j = 0; j < layoutLength; j++) {
                if(layout[i][j] != '.' && layout[i][j] != 'W' && layout[i][j] != 'F' && layout[i][j] != 'L') {
                    layout[i][j] = getCharFromFloat(getAverageSkid(i, j));
                }
            }
        }
    }

    private char getCharFromFloat(float a) {
        if(a < 1) return '0';
        if(a < 2) return '1';
        if(a < 3) return '2';
        if(a < 4) return '3';
        if(a < 5) return '4';
        if(a < 6) return '5';
        if(a < 7) return '6';
        if(a < 8) return '7';
        if(a < 9) return '8';
        return '9';
    }

    private void ceateRiot() {
        layoutLength = 240;
        layoutHeight = 240;
        layout = new char[layoutHeight][layoutLength];
        startX = 100;
        startY = 10;

        for (int i = 0; i < layoutHeight; i++) {
            for (int j = 0; j < layoutLength; j++) {
                layout[i][j] = '.';
            }
        }
        startPos = new Vector2(103, 13);

        startDir = DIRECTION.RIGHT;
        addTrack(10, DIRECTION.RIGHT, 60, 4, "start");
        addTrack(10, DIRECTION.UP, 20, 4);
        addTrack(10, DIRECTION.LEFT, 30, 5);
        addTrack(10, DIRECTION.UP, 20, 2);
        addTrack(8, DIRECTION.LEFT, 20, 3);
        addTrack(8, DIRECTION.DOWN, 15, 4);
        addTrack(12, DIRECTION.LEFT, 20, 1);
        addTrack(7, DIRECTION.UP, 15, 2);
        addTrack(10, DIRECTION.LEFT, 20, 3);
        addTrack(12, DIRECTION.UP, 24, 4);
        addTrack(12, DIRECTION.LEFT, 18, 5);
        addTrack(10, DIRECTION.UP, 25, 4);
        addTrack(10, DIRECTION.RIGHT, 40, 3);
        addTrack(10, DIRECTION.UP, 15, 3);
        addTrack(10, DIRECTION.LEFT, 15, 4);
        addTrack(10, DIRECTION.UP, 15, 5);
        addEnd(14);
    }


    public float getAverageSkid(int i, int j){
        int a = 0;
        int n = 0;
        int total = 0;

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

        return  total / n;
    }

    public DIRECTION getStartDir() {
        return startDir;
    }

    private void ceateRoyal() {
        layoutLength = 200;
        layoutHeight = 200;
        layout = new char[layoutHeight][layoutLength];
        startX = 10;
        startY = 10;

        for (int i = 0; i < layoutHeight; i++) {
            for (int j = 0; j < layoutLength; j++) {
                layout[i][j] = '.';
            }
        }

        startPos = new Vector2(4, 4);

        startDir = DIRECTION.UP;

        addTrack(6, DIRECTION.UP, 30, 1, "start");
        addTrack(4, DIRECTION.RIGHT, 20, 0);
        addTrack(4, DIRECTION.UP, 20, 2);
        addTrack(4, DIRECTION.RIGHT, 40, 8);
        addTrack(4, DIRECTION.DOWN, 14, 3);
        addTrack(6, DIRECTION.LEFT, 8, 7);
        addTrack(7, DIRECTION.DOWN, 10, 4);
        addTrack(6, DIRECTION.RIGHT, 10, 2);
        addTrack(6, DIRECTION.UP, 6, 5);
        addTrack(4, DIRECTION.RIGHT, 10, 9);
        addTrack(5, DIRECTION.UP, 10, 1);
        addTrack(6, DIRECTION.RIGHT, 10, 7);
        addTrack(4, DIRECTION.DOWN, 10, 3);
        addTrack(4, DIRECTION.RIGHT, 10, 9);
        addTrack(4, DIRECTION.UP, 10, 5);
        addTrack(6, DIRECTION.RIGHT, 10, 9);
        addTrack(7, DIRECTION.DOWN, 10, 9);
        addTrack(7, DIRECTION.RIGHT, 30, 3);
        addTrack(4, DIRECTION.UP, 10, 2);
        addTrack(4, DIRECTION.LEFT, 8, 1);
        addTrack(4, DIRECTION.UP, 8, 1);
        addTrack(4, DIRECTION.LEFT, 6, 1);
        addTrack(4, DIRECTION.UP, 30, 7);
        addTrack(7, DIRECTION.LEFT, 15, 4);
        addTrack(4, DIRECTION.DOWN, 10, 1);
        addTrack(4, DIRECTION.LEFT, 10, 1);
        addTrack(6, DIRECTION.UP, 22, 7);
        addTrack(4, DIRECTION.RIGHT, 20, 8);
        addTrack(6, DIRECTION.UP, 7, 7);
        addTrack(4, DIRECTION.RIGHT, 4, 8);
        addTrack(6, DIRECTION.UP, 7, 7);
        addTrack(4, DIRECTION.LEFT, 4, 9);
        addTrack(6, DIRECTION.UP, 7, 7);
        addTrack(4, DIRECTION.RIGHT, 4, 9);
        addTrack(6, DIRECTION.UP, 7, 7);
        addTrack(4, DIRECTION.LEFT, 4, 8);
        addTrack(6, DIRECTION.UP, 7, 7);
        addTrack(4, DIRECTION.RIGHT, 4, 9);
        addTrack(6, DIRECTION.UP, 7, 7);
        addTrack(4, DIRECTION.LEFT, 4, 1);
        addEnd(10);

    }

    private void addEnd(int depth) {


        int length = depth;
        switch (lastDir) {
            case DOWN:
                width = depth;
                height = length;
                startY = lastBottom - length ;
                startX = lastLeft + (lastRight - lastLeft - depth) / 2;
                System.out.println("sx = " + startX + ", sy = " + startY);

                for (int i = startY - 1; i < height + startY + 1; i++) {
                    for (int j = startX - 1; j < width + startX + 1; j++) {
                        layout[i][j] = 'W';
                    }
                }

                for (int i = startY; i < height + startY; i++) {
                    for (int j = startX; j < width + startX; j++) {
                        layout[i][j] = 'F';
                    }
                }


                for (int j = startX; j < width + startX; j++) {
                    layout[startY + 7][j] = 'F';
                }

                for (int i = lastLeft; i < lastRight; i++) {
                    layout[lastBottom][i] = 'L';
                }
                endRect = new Rectangle(lastLeft, lastBottom - (lastRight - lastLeft), lastRight - lastLeft,lastRight - lastLeft);
                break;
            case UP:
                width = depth;
                height = length;
                startY = lastTop;
                startX = lastLeft + (lastRight - lastLeft - depth) / 2;

                for (int i = startY - 1; i < height + startY + 1; i++) {
                    for (int j = startX - 1; j < width + startX + 1; j++) {
                        layout[i][j] = 'W';
                    }
                }

                for (int i = startY; i < height + startY; i++) {
                    for (int j = startX; j < width + startX; j++) {
                        layout[i][j] = 'F';
                    }
                }


                for (int j = startX; j < width + startX; j++) {
                    layout[startY + 3][j] = 'F';
                }
                for (int i = lastLeft; i < lastRight; i++) {
                    layout[lastTop - 1][i] = 'L';
                }

                endRect = new Rectangle(lastLeft, lastTop, lastRight - lastLeft,lastRight - lastLeft);
                break;
            case RIGHT:
                width = length;
                height = depth;
                startX = lastRight;
                startY = lastBottom + (lastTop - lastBottom - height) / 2;

                for (int i = startY - 1; i < height + startY + 1; i++) {
                    for (int j = startX - 1; j < width + startX + 1; j++) {
                        layout[i][j] = 'W';
                    }
                }

                for (int i = startY; i < height + startY; i++) {
                    for (int j = startX; j < width + startX; j++) {
                        layout[i][j] = 'F';
                    }
                }

                for (int j = startY; j < height + startY; j++) {
                    layout[j][startX + 3] = 'F';
                }

                for (int i = lastBottom; i < lastTop; i++) {
                    layout[i][lastRight - 1] = 'L';
                }
                endRect = new Rectangle(lastRight, lastBottom, lastTop - lastBottom, lastTop - lastBottom);
                break;
            case LEFT:
                width = length;
                height = depth;
                startX = lastLeft - width;
                startY = lastBottom + (lastTop - lastBottom - height) / 2;

                for (int i = startY - 1; i < height + startY + 1; i++) {
                    for (int j = startX - 1; j < width + startX + 1; j++) {
                        layout[i][j] = 'W';
                    }
                }

                for (int i = startY; i < height + startY; i++) {
                    for (int j = startX; j < width + startX; j++) {
                        layout[i][j] = 'F';
                    }
                }

                for (int j = startY; j < height + startY; j++) {
                    layout[j][startX + 7] = 'F';
                }

                for (int i = lastBottom; i < lastTop; i++) {
                    layout[i][lastLeft] = 'L';
                }
                endRect = new Rectangle(lastLeft - (lastTop - lastBottom), lastBottom, lastTop - lastBottom, lastTop - lastBottom);
                break;
        }
    }

    private void addTrack(int depth, DIRECTION direction, int length, int skidFinish) {

        cornerCount++;

        if (direction == DIRECTION.UP) {
            corners.add(1);
        } else if (direction == DIRECTION.RIGHT) {
            corners.add(2);
        } else if (direction == DIRECTION.DOWN) {
            corners.add(3);
        } else {
            corners.add(4);
        }

        int grip = 0;
        switch (direction) {
            case DOWN:
                width = depth;
                height = length;
                startY = lastBottom - height;
                if(lastDir == DIRECTION.LEFT){
                    startX = lastLeft;
                } else {
                    startX = lastRight - depth;
                }
                for (int i = startY; i < height + startY; i++) {
                    for (int j = startX; j < width + startX; j++) {
                        grip = (int) (skidStart + ((height + startY - i - 1) / (float) height) * (skidFinish + 1 - skidStart));
                        layout[i][j] = getCharFronInt(grip);
                    }
                }


                break;
            case UP:
                width = depth;
                height = length;
                startY = lastTop;
                if(lastDir == DIRECTION.LEFT){
                    startX = lastLeft;
                } else {
                    startX = lastRight - depth;
                }
                for (int i = startY; i < height + startY; i++) {
                    for (int j = startX; j < width + startX; j++) {
                        grip = (int) (skidStart + ((i - startY) / (float) height) * (skidFinish + 1 - skidStart));
                        layout[i][j] = getCharFronInt(grip);
                    }
                }
                break;
            case RIGHT:
                width = length;
                height = depth;
                startX = lastRight;
                if(lastDir == DIRECTION.UP){
                    startY = lastTop - depth;
                } else {
                    startY = lastBottom;
                }
                for (int i = startY; i < height + startY; i++) {
                    for (int j = startX; j < width + startX; j++) {
                        grip = (int) (skidStart + ((j - startX) / (float) width) * (skidFinish + 1 - skidStart));
                        layout[i][j] = getCharFronInt(grip);
                    }
                }
                break;
            case LEFT:
                width = length;
                height = depth;
                startX = lastLeft - width;
                if(lastDir == DIRECTION.UP){
                    startY = lastTop - depth;
                } else {
                    startY = lastBottom;
                }
                for (int i = startY; i < height + startY; i++) {
                    for (int j = startX; j < width + startX; j++) {
                        grip = (int) (skidStart + ((width + startX - j - 1) / (float) width) * (skidFinish + 1 - skidStart));
                        layout[i][j] = getCharFronInt(grip);
                    }
                }
                break;
        }
        addWall(direction);

        lastRight = startX + width;
        lastLeft = startX;
        lastTop = startY + height;
        lastBottom = startY;

        switch (direction){
            case UP: lastDir = DIRECTION.UP;
                break;
            case LEFT: lastDir = DIRECTION.LEFT;
                break;
            case RIGHT: lastDir = DIRECTION.RIGHT;
                break;
            case DOWN: lastDir = DIRECTION.DOWN;
                break;
        }

        skidStart = skidFinish;
    }

    private void addTrack(int depth, DIRECTION direction, int length, int skidFinish, String type) {
        cornerCount++;
        int grip = 0;
        switch (direction) {
            case DOWN:
                width = depth;
                height = length;
                startY = lastBottom - height;
                if(lastDir == DIRECTION.LEFT){
                    startX = lastLeft;
                } else {
                    startX = lastRight - depth;
                }
                for (int i = startY; i < height + startY; i++) {
                    for (int j = startX; j < width + startX; j++) {
                        grip = (int) (skidStart + ((height + startY - i - 1) / (float) height) * (skidFinish + 1 - skidStart));
                        layout[i][j] = getCharFronInt(grip);
                    }
                }
                break;
            case UP:
                width = depth;
                height = length;
                startY = lastTop;
                if(lastDir == DIRECTION.LEFT){
                    startX = lastLeft;
                } else {
                    startX = lastRight - depth;
                }
                for (int i = startY; i < height + startY; i++) {
                    for (int j = startX; j < width + startX; j++) {
                        grip = (int) (skidStart + ((i - startY) / (float) height) * (skidFinish + 1 - skidStart));
                        layout[i][j] = getCharFronInt(grip);
                    }
                }
                break;
            case RIGHT:
                width = length;
                height = depth;
                startX = lastRight;
                if(lastDir == DIRECTION.UP){
                    startY = lastTop - depth;
                } else {
                    startY = lastBottom;
                }
                for (int i = startY; i < height + startY; i++) {
                    for (int j = startX; j < width + startX; j++) {
                        grip = (int) (skidStart + ((j - startX) / (float) width) * (skidFinish + 1 - skidStart));
                        layout[i][j] = getCharFronInt(grip);
                    }
                }
                break;
            case LEFT:
                width = length;
                height = depth;
                startX = lastLeft - width;
                if(lastDir == DIRECTION.UP){
                    startY = lastTop - depth;
                } else {
                    startY = lastBottom;
                }
                for (int i = startY; i < height + startY; i++) {
                    for (int j = startX; j < width + startX; j++) {
                        grip = (int) (skidStart + ((width + startX - j - 1) / (float) width) * (skidFinish + 1 - skidStart));
                        layout[i][j] = getCharFronInt(grip);
                    }
                }
                break;
        }
        addWall(direction);



        lastRight = startX + width;
        lastLeft = startX;
        lastTop = startY + height;
        lastBottom = startY;

        switch (direction){
            case UP: lastDir = DIRECTION.UP;
                addWall(DIRECTION.DOWN);
                break;
            case LEFT: lastDir = DIRECTION.LEFT;
                addWall(DIRECTION.RIGHT);
                break;
            case RIGHT: lastDir = DIRECTION.RIGHT;
                addWall(DIRECTION.LEFT);
                break;
            case DOWN: lastDir = DIRECTION.DOWN;
                addWall(DIRECTION.UP);
                break;
        }

        skidStart = skidFinish;
    }

    private int getIntFromChar(int i, int j) {
        try {
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
        } catch (Exception e){
            return -1;
        }
    }

    private void addWall(DIRECTION dir) {

        if(dir == DIRECTION.UP){
            for (int i = 0 ; i < width + 2; i++) {
                layout[startY + height][startX - 1 + i] = 'W';
            }
            for (int i = 0 ; i < height + 1; i++) {
                layout[startY + i][startX - 1] = 'W';
            }
            for (int i = 0 ; i < height + 1; i++) {
                layout[startY + i][startX + width] = 'W';
            }
        }

        if(dir == DIRECTION.DOWN){
            for (int i = 0 ; i < width + 2; i++) {
                layout[startY - 1][startX - 1 + i] = 'W';
            }
            for (int i = 0 ; i < height + 1; i++) {
                layout[startY + i - 1][startX - 1] = 'W';
            }
            for (int i = 0 ; i < height + 1; i++) {
                layout[startY + i - 1][startX + width] = 'W';
            }
        }

        if(dir == DIRECTION.LEFT){
            for (int i = 0 ; i < height + 2; i++) {
                layout[startY - 1 + i][startX - 1] = 'W';
            }
            for (int i = 0 ; i < width + 1; i++) {
                layout[startY - 1][startX - 1 + i] = 'W';
            }
            for (int i = 0 ; i < width + 1; i++) {
                layout[startY + height][startX - 1 + i] = 'W';
            }
        }

        if(dir == DIRECTION.RIGHT){
            for (int i = 0 ; i < height + 2; i++) {
                layout[startY - 1 + i][startX + width] = 'W';
            }
            for (int i = 0 ; i < width + 1; i++) {
                layout[startY - 1][startX + i] = 'W';
            }
            for (int i = 0 ; i < width + 1; i++) {
                layout[startY + height][startX + i] = 'W';
            }
        }
    }

    private char getCharFronInt(int grip) {
        switch(grip){
            case 0 : return '0';
            case 1 : return '1';
            case 2 : return '2';
            case 3 : return '3';
            case 4 : return '4';
            case 5 : return '5';
            case 6 : return '6';
            case 7 : return '7';
            case 8 : return '8';
            case 9 : return '9';
            default: return 'x';
        }
    }

    public void printLayout(){
        for (int i = layoutHeight - 1; i >= 0; i--) {
            for (int j = 0; j < layoutLength; j++) {
                System.out.print(layout[i][j]);
            }
            System.out.println("");
        }
    }

    public Rectangle getEndRect() {
        return endRect;
    }

    public int getCornerCount(){
        return cornerCount;
    }

    public Vector2 getStartPos() {
        return startPos;
    }

    public char[][] getLayout() {
        return layout;
    }
}
