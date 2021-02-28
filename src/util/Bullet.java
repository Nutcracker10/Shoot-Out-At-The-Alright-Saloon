package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bullet {
    private double x, y; // current position of bullet
    private double velocity; // an arrow with both direction and magnitude - Vector, Despicable Me
    private double dX, dY; // our scalars
    private int height =25, width =25;
    private BufferedImage image;
    private String origin; // what created the bullet

    public Bullet (double x, double y, double angle, double velocity, String origin) {

        this.velocity = velocity;
        this.origin = origin;
        //calculate our movement directions
        this.dX = Math.cos(angle);
        this.dY = Math.sin(angle);

        double centre = ((height + width) / 2);

        this.x = x;
        this.y = y;

        try {
            image = ImageIO.read(new File("res/bullet.png") );
        } catch (IOException e) { e.printStackTrace(); }

    }


    public void draw(Graphics g) {
        g.drawImage(image, (int)x, (int)y, width, height, null);
    }

    public void update() {
        this.x += (dX*velocity);
        this.y += (dY*velocity);
    }

    //for testing purposes
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getHeight() { return height; }

    public int getWidth() { return width; }

    public String getOrigin() { return origin; }
}
