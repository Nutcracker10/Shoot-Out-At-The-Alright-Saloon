package util;

import java.util.ArrayList;

public class Layer {
    private ArrayList<Room> roomList = new ArrayList<>();

    public Layer () {
        int roomLimit = 5;

        while (roomList.size() < roomLimit) {

            if (roomList.size() == 0) {
                Room room = new Room();
                roomList.add(room);
            } else {
                //find previous door and create new room relative entry
                Room room = new Room(roomList.get(roomList.size()-1).getLastDoor());
                roomList.add(room);
            }
        }
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }

    public void addRoomAtIndex(Room room, int index) {
        roomList.add(index, room);
    }
}
