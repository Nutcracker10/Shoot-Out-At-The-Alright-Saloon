package util;

import java.util.ArrayList;

public class Layer {
    private ArrayList<Room> roomList = new ArrayList<>();

    public Layer () {
        int roomLimit = 5;

        while (roomList.size() < roomLimit) {

        }
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }

    public void addRoom(Room room, int index) {
        roomList.add(index, room);
    }
}
