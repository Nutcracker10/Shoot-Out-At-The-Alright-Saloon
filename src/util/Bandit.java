package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Bandit {
    private boolean hitStatus = false;
    private double x,y, dX, dY;
    private double velocity;
    private int difficulty;
    private Point move = new Point(); // where to move to
    private BufferedImage image;
    private double range = 250.0; // movement range
    private double distMoved = 0.0;

    public Bandit(double x, double y, int difficulty) {
        this.x = x;
        this.y = y;
        this.difficulty = difficulty;

        switch (difficulty) { // difficulty affects velocity
            case 1: this.velocity = 2.5; break;
            case 2: this.velocity = 5.0; break;
            case 3: this.velocity = 10.0; break;
        }

        try {
            image = ImageIO.read(new File("res/Bandit.png") );
        } catch (IOException e) { e.printStackTrace(); }

        this.findMove();
    }

    public void draw(Graphics g) { g.drawImage(image, (int)x, (int)y, 75, 75, null); }

    public void findMove() {
        //bounds for number generation
        int max = 5;
        double min = 2;

        double destX, destY;
        Random rand = new Random();

        destX = (double) rand.nextInt(max+1);
        destY = (double) rand.nextInt(max+1);

        //get our X coord
        if (destX == 0.0 || destX >= min) {
            // 0 allows for horizontal / vertical only movement
            if (destY == 0.0 ) {
                destX += min;
            }
        }
        // ensure we move min distance
        else if (destX < min) {
            destX += (min -1 ) ;
        }

        //decide if its a positive or negative coord
        if (rand.nextInt(1) == 0){
            destX = (destX * -1.0) ;
        }

        // Repeat for Y
        if (destY == 0.0 || destY >= min) {
            if (destX == 0.0) {
                destY += min;
            }
        } else if (destY < min) {
            destY += (min - 1) ;
        }

        if (rand.nextInt(1) == 0) {
            destY = (destY * -1);
        }

        // make coords relative to bandit
        move.setLocation((destX + this.x), (destY + this.y));
        calcAngle(); // figure out how to move

        System.out.println("Moving to " + move);
    }

    public void calcAngle() {
        double angle = Math.atan2((move.getX() - x ), (move.getY() - y ));

        dX = Math.cos(angle);
        dY = Math.sin(angle);
    }

    public void update() {

        double oldX = this.x, oldY = this.y;

        // move the bandit
        this.x += (dX * 2.);
        this.y += (dY * 2.);

        //check if bandit has moved enough
        if (distMoved > range) {
            distMoved = 0.0;
            System.out.println("distMoved outpaced range");

            findMove();
        } else {
            distMoved += ( ( (this.x - oldX) + (this.y - oldY) )  *-1) / 2.0;
            //System.out.println("Moved: " + distMoved);
        }
    }

      // Bandit Bullet BS
//    public double calcAngleToPlayer(double pX, double pY) {
//      double angle = Math.atan2((move.getX() - x ), (move.getY() - y ));
//        return 0.0;
//    }
//
//    public Bullet fireBullet() {
//        return  ( new Bullet(this.x, this.y, this.angle, this.velocity, "Bandit") );
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
