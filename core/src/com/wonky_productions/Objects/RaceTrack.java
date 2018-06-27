package com.wonky_productions.Objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class RaceTrack {

    int wallSize, trackWidth, skidStart, skidFinish, length;
    Car.DIRECTION direction;


    public RaceTrack(int wallSize, int trackWidth, int skidStart, int skidFinish, int length, Car.DIRECTION direction) {
        this.wallSize = wallSize;
        this.trackWidth = trackWidth;
        this.skidStart = skidStart;
        this.skidFinish = skidFinish;
        this.length = length;
        this.direction = direction;
    }


}
