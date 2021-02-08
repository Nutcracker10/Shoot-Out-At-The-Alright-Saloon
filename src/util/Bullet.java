package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bullet extends GameObject {
    private double x, y; // current position of bullet
    private double mX, mY; //position of mouse
    private double angle, velocity; // an arrow with both direction and magnitude - Vector, Despicable Me
    private double dX, dY; // our scalars
    private BufferedImage image;

    public Bullet() { }

    public Bullet (float mX, float mY, float pX, float pY) {
        this.mX = mX;
        this.mY = mY;
        this.x = pX;
        this.y = pY;

        //lets do some 'trigger'-nometry ;)
        this.angle = Math.atan2(mY, mX);
        this.dX = Math.cos(mX);
        this.dY = Math.sin(mY);

        try {
            image = ImageIO.read(new File("res/bullet.png") );
        } catch (IOException e) { e.printStackTrace(); }
    }


    public void draw(Graphics g) {
        g.drawImage(image, (int)x, (int)y, 16, 32, null);
    }

    public void update() {
        this.x += dX;
        this.y += dY;
    }
}
