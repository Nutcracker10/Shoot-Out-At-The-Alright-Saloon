package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Bandit {
    private boolean hitStatus = false;
    private double x,y, dX, dY;
    private double velocity;
    private int difficulty;
    private Point move = new Point(); // where to move to
    private BufferedImage image;
    private double range = 200.0; // movement range
    private double distMoved = 0.0;
    private double banditSpeed = 2.0;

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
        int max = 100;
        double min = 25.0;

        double destX, destY;
        Random rand = new Random();

        destX = (double) rand.nextInt(max+1);
        destY = (double) rand.nextInt(max+1);

        //get our X coord
        if (destX == 0.0 && destY == 0.0 ) {
            // 0 allows for horizontal / vertical only movement
            destX += min;
        }
        // ensure we move min distance
        else if (destX < min) {
            destX += (min -1 ) ;
        }

        //decide if its a positive or negative coord
        if (rand.nextInt(2) == 0){
            destX = (destX * -1.0) ;
        }

//        // check if we're in the boundary
//        if (destX < 0 || destX > 900) {
//            destX = (destX * -1.0) ;
 //       }

        // Repeat for Y
        if (destY == 0.0 || destY >= min) {
            if (destX == 0.0) {
                destY += min;
            }
        } else if (destY < min) {
            destY += (min - 1) ;
        }

        if (rand.nextInt(2) == 0) {
            destY = (destY * -1);
        }
        //System.out.println("Y: " + destY + "X: " + destX);

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

        double oldX = this.x, oldY = this.y;
        // move the bandit
        if (this.outOfBounds() == true) {
            findMove();
        }
        this.x += (dX * banditSpeed);
        this.y += (dY * banditSpeed);

        //check if bandit has moved enough
        if (distMoved > range) {
            distMoved = 0.0;
            findMove();
        } else { // get distance from last move
            distMoved += Math.sqrt( Math.pow( (this.x - oldX), 2.0) + Math.pow( (this.y - oldY), 2.0 )  );
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


    public boolean outOfBounds () {
        if ( (this.x + (dX*banditSpeed) ) < 0.0 || (this.x + (dX*banditSpeed) ) > 900.0 || (this.y + (dY*banditSpeed) ) < 0.0 || ( this.y + (dY*banditSpeed) ) > 900.0) {
            return true;
        } else
            return false;
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
