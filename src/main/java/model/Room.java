package model;

/**
 * Represents a cinema room with an ID, name, and seating capacity.
 */
public class Room {
    private String roomId;
    private String roomName;
    private int capacity;

    public Room(String roomId, String roomName, int capacity) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.capacity = capacity;
    }

    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    @Override
    public String toString() {
        return "Room{" + "roomId='" + roomId + '\'' + ", roomName='" + roomName + '\'' + ", capacity=" + capacity + '}';
    }
}