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

    public String getPos() {
        Point point = new Point((int)this.x, (int)this.y);
        String toReturn = "";

        // cant use switch here coz of object passing :(
        if ((this.x == 500) && (this.y == 0)) { toReturn = "North"; }

        else if ((this.x == 500) && (this.y == 1000)) { toReturn = "South"; }

        else if ((this.x == 1000) && (this.y == 500)) { toReturn = "East"; }

        else if ((this.x == 0) && (this.y == 500)) { toReturn = "North"; }

        return toReturn;
    }

}
