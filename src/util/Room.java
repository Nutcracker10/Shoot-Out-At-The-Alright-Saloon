package util;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Room {
    private ArrayList<Door> doorList = new ArrayList<>();
    private String doorPos;
    private int x, y;
    private boolean hostileRoom;

    private Point North = new Point(500, 0);
    private Point South = new Point(500, 1000);
    private Point East = new Point(1000, 500);
    private Point West = new Point(0, 500);

    public  Room() {
        //Add a door to the north wall if no specification given
        Door door = new Door();
        door.setPos(North);
        doorList.add(door);
        hostileRoom = false;
    }

    public Room(String doorPos) {
        this.doorPos = doorPos;
        Door door1 = new Door();

        switch (doorPos) {
            case "North":
                door1.setPos(North);
                break;

            case "South":
                door1.setPos(South);
                break;

            case "East":
                door1.setPos(East);
                break;

            case "West":
                door1.setPos(West);
                break;
        }
        doorList.add(door1);
        doorList.add(findOtherDoorPlacement());
        hostileRoom = true;

    }

    private Door findOtherDoorPlacement() {

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

    public ArrayList<Door> getDoorList() { return this.doorList; }

    public String getLastDoor() {
        return this.doorList.get(doorList.size() - 1 ).getPos();
    }
}
