package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bullet extends GameObject {
    private double x, y; // current position of bullet
    private double velocity; // an arrow with both direction and magnitude - Vector, Despicable Me
    private double dX, dY; // our scalars
    private BufferedImage image;

    public Bullet (double x, double y, double angle, double velocity) {

        super();

        this.velocity = velocity;

        this.dX = Math.cos(angle);
        this.dY = Math.sin(angle);
        this.x = x;
        this.y = y;

        try {
            image = ImageIO.read(new File("res/bullet.png") );
        } catch (IOException e) { e.printStackTrace(); }

    }


    public void draw(Graphics g) {
        g.drawImage(image, (int)x, (int)y, 25, 25, null);
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
}
