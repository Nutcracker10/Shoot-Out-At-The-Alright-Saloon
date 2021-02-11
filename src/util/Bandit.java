package util;

import java.awt.*;
import java.util.Random;

public class Bandit {
    private boolean hitStatus = false;
    private double x,y, dX, dY;
    private double velocity;
    private int difficulty;
    private Point move = new Point(); // where to move to

    public Bandit(double x, double y, int difficulty) {
        this.x = x;
        this.y = y;
        this.difficulty = difficulty;

        switch (difficulty) { // difficulty affects velocity
            case 1: this.velocity = 2.5; break;
            case 2: this.velocity = 5.0; break;
            case 3: this.velocity = 10.0; break;
        }
    }

    public void findMove() {
        //bounds for number generation
        int max = 5;
        double min = 25;

        double destX, destY;
        Random rand = new Random();

        destX = (double) rand.nextInt(max+1);
        destY = (double) rand.nextInt(max+1);

        //get our X coord
        if (destX == 0.0 || destX >= min) {
            if (destY == 0.0 ) {
                destX += min;
            }
            //decide if its a positive or negative coord
            if (rand.nextInt(1) == 0){
                destX = (destX * -1.0) ;
            }
        }
        // ensure we move min distance
        else if (destX < min) {
            destX += (min -1 ) ;
        }


        if (destY == 0.0 || destY >= min) {
            if (destX == 0.0) {
                destY += min;
            }

            if (rand.nextInt(1) == 0) {
                destY = (destY * -1);
            }
        } else if (destY < min) {
            destY += (min - 1) ;
        }

        // make coords relative to bandit
        move.setLocation((destX + this.x), (destY + this.y));
        calcAngle(); // figure out how to move
    }

    public void calcAngle() {
        double angle = Math.atan2((move.getX() - x ), (move.getY() - y ));

        dX = Math.cos(angle);
        dY = Math.sin(angle);
    }

    public void update() {
        // make a new move if point is reached
        if (this.x == move.getX() && this.y == move.getY() ) {
            findMove();
            return;
        }
        // move the bandit
        this.x += (dX * 3);
        this.y += (dY * 3);
    }

    public double calcAngleToPlayer(double pX, double pY) {
        return 0.0;
    }

//    public Bullet fireBullet() {
//        return  ( new Bullet(this.x, this.y, this.angle, this.velocity) );
//    }

    public void setX(double x) { this.x = x; }

    public double getX() {return this.x; }

    public void setY(double y) { this.y = y; }

    public double getY() { return this.y; }

    public void setMove(Point point) { this.move = point; }

    public Point getMove() { return move; }

    public void setHitStatus(boolean hit) { hitStatus = hit; }

    public boolean isHitStatus() { return hitStatus; }
}
