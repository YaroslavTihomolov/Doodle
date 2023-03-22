package com.example.doodle.model.classes;

public final class Position {
    private float x;
    private float y;

    public Position() {
        this.x = 0;
        this.y = 0;
    }

    public Position(float xValue, float yValue) {
        this.x = xValue;
        this.y = yValue;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public void setX(float value) {
        this.x = value;
    }

    public void setY(float value) {
        this.y = value;
    }
}
