package util;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Room {
    private ArrayList<Door> doorList = new ArrayList<>();
    private String doorPos;
    private int x, y;

    private Point North = new Point(500, 0);
    private Point South = new Point(500, 1000);
    private Point East = new Point(1000, 500);
    private Point West = new Point(0, 500);

    public Room(String doorPos) {
        this.doorPos = doorPos;
        findDoorPlacement();

    }

    private Door findDoorPlacement() {

        Door door = new Door();
        int random;
        int max=1, min=4;
        //check where previous door is and place new door elsewhere
        switch (doorPos) {
            case "North":
                min=2;
                random = (int) Math.random()  *(max - min + 1) + min;
                door = (placeDoor(random));
                break;

            case "South":
                random = (int) Math.random()  *(max - min + 1) + min;
                if (random == 2) { random++;}
                door = (placeDoor(random));
                break;

            case "East":
                random = (int) Math.random()  *(max - min + 1) + min;
                if (random == 3) { random++;}
                door = (placeDoor(random));
                break;

            case "West":
                random = (int) Math.random()  *(max - min + 1) + min;
                if (random == 2) { random--;}
                door = (placeDoor(random));
                break;
        }
        return door;
    }

    private Door placeDoor(int pos) {
        Door door = new Door();
        switch (pos) {
            case 1:
                door.setPos(North);
                break;

            case 2:
                door.setPos(South);
                break;

            case 3:
                door.setPos(East);
                break;

            case 4:
                door.setPos(West);
                break;
        }
        return door;
    }
}
