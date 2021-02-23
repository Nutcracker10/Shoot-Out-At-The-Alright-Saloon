package util;

import java.util.ArrayList;

public class Layer {
    private ArrayList<Room> roomList = new ArrayList<>();

    public Layer () {
        int roomLimit = 5;

        int index = 0;
        while (roomList.size() < roomLimit) {

            if (roomList.size() == 0) {
                Room room = new Room();
            } else {
                //find previous door and create new room relative entry
                Room room = new Room(roomList.get(roomList.size()-1).getLastDoor());
            }
        }
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }

    public void addRoom(Room room, int index) {
        roomList.add(index, room);
    }
}
