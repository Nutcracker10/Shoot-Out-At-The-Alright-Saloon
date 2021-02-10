package util;

import java.awt.*;
import java.util.Random;

public class Bandit {
    private boolean hitStatus = false;
    double x,y;
    Point move = new Point(); // where to move to

    public Bandit(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void findMove() {
        double upper = 50.0, lower = 25.0; // bounds for random generation
        double randomX = (Math.random() * (upper - lower + 1 ) + lower);
        double randomY = (Math.random() * (upper - lower + 1 ) + lower);

        move.setLocation(randomX, randomY);

    }

    public void setX(double x) { this.x = x; }

    public double getX() {return this.x; }

    public void setY(double y) { this.y = y; }

    public double getY() { return this.y; }

    public void setMove(Point point) { this.move = point; }

    public Point getMove() { return move; }

    public void setHitStatus(boolean hit) { hitStatus = hit; }

    public boolean isHitStatus() { return hitStatus; }
}
