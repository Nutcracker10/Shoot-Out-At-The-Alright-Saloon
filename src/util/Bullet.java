package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bullet extends GameObject {
    private double x, y; // current position of bullet
    private double angle, velocity; // an arrow with both direction and magnitude - Vector, Despicable Me
    private double dX, dY; // our scalars
    private BufferedImage image;
    private BufferedImage defaultImage;


    public Bullet (double mX, double mY, double x, double y) {

        super();

        this.dX = Math.cos(mX);
        this.dY = Math.sin(mY);
        this.x = x;
        this.y = y;

        //lets do some 'trigger'-nometry ;)
        this.angle = Math.atan2(mY, mX);

        try
        {
            defaultImage = ImageIO.read(new File("res/blankSprite.png") );
        } catch (IOException e) { e.printStackTrace(); }


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
