package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Door {
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

    public void setOpen(boolean isOpen) { this.isOpen = isOpen ;}

    public boolean isOpen() { return this.isOpen; }

}
