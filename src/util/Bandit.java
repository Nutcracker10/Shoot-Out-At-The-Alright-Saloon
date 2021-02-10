package util;

import java.util.Random;

public class Bandit {
    private boolean isHit = false;
    double x,y, mX, mY;

    public Bandit(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void findMove() {
        double upper = 50.0, lower = 25.0; // bounds for random generation
        double randomX = (Math.random() * (upper - lower + 1 ) + lower);
        double randomY = (Math.random() * (upper - lower + 1 ) + lower);


    }

    public void setX(double x) { this.x = x; }

    public double getX() {return this.x; }

    public void setY(double y) { this.y = y; }

    public double getY() { return this.y; }

    public void setmX(double mX) { this.mX = mX; }

    public double getmX() { return this.mX; }

    public void setmY(double mY) { this.mY = mY; }

    public double getmY() { return mY; }

}
