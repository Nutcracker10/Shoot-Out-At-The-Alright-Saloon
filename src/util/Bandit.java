package util;

import java.awt.*;
import java.util.Random;

public class Bandit {
    private boolean hitStatus = false;
    private double x,y, dX, dY;
    private double angle, velocity;
    private int difficulty;
    private Point move = new Point(); // where to move to

    public Bandit(double x, double y, int difficulty) {
        this.x = x;
        this.y = y;
        this.difficulty = difficulty;

        switch (difficulty) {
            case 1: this.velocity = 2.5; break;
            case 2: this.velocity = 5.0; break;
            case 3: this.velocity = 10.0; break;
        }
    }

    public void findMove() {
        double upper = 50.0, lower = 25.0; // bounds for random generation
        double randomX = (Math.random() * (upper - lower + 1 ) + lower);
        double randomY = (Math.random() * (upper - lower + 1 ) + lower);

        move.setLocation(randomX, randomY);
        calcAngle(); // figure out how to move
    }

    public void calcAngle() {
        angle = Math.atan2((move.getX() - x ), (move.getY() - y ));

        dX = Math.cos(angle);
        dY = Math.sin(angle);
    }

    public void update() {
        // make a new move if point is reached
        if (this.x == move.getX() && this.y == move.getY() ) {
            findMove();
            return;
        }

        this.x += (dX * velocity);
        this.y += (dY * velocity);
    }

    public Bullet fireBullet() {
        return  ( new Bullet(this.x, this.y, this.angle, this.velocity) );
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
