package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Door {
    private double x, y;
    private int height =80, width = 80;
    private boolean isOpen = false;
    private BufferedImage image;

    public Door() {
        try {
            image = ImageIO.read(new File("res/Closed_door.png"));
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void openDoor() {
        try {
            image = ImageIO.read(new File("res/Open_door.png"));
        } catch (IOException e) { e.printStackTrace(); }
        isOpen = true;
    }

    public boolean playerEnters(GameObject Player) {
        // TODO check if player is colliding with open door, load new room
        if  (Math.abs(Player.getCentre().getX() - this.x) < Player.getWidth()
                && Math.abs(Player.getCentre().getY()- this.y) < Player.getHeight() ) {
            return true;
        } else
        return false;
    }

    public void draw(Graphics g) {
        g.drawImage(image, (int)x, (int)y, width, height, null);
    }

    public void setOpen(boolean isOpen) { this.isOpen = isOpen ;}

    public boolean isOpen() { return this.isOpen; }

    public void setPos(Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    public void setX(double x) { this.x = x; }

    public void setY(double y) { this.y = y; }

}
