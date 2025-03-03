package HandsOn;
import java.util.*;

enum Feature {
    PROJECTOR, VIDEO_CONFERENCING, WHITEBOARD, CONFERENCE_PHONE, AIR_CONDITIONING
}

class MeetingRoom {
    private String roomId;
    private String roomName;
    private int capacity;
    private EnumSet<Feature> features;

    public MeetingRoom(String roomId, String roomName, int capacity, EnumSet<Feature> features) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.capacity = capacity;
        this.features = features;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public EnumSet<Feature> getFeatures() {
        return features;
    }

    public boolean hasFeatures(EnumSet<Feature> requiredFeatures) {
        return features.containsAll(requiredFeatures);
    }

    @Override
    public String toString() {
        return roomName + " (ID: " + roomId + ")";
    }
}

class RoomScheduler {
    private Map<String, MeetingRoom> rooms = new HashMap<>();

    public void addMeetingRoom(MeetingRoom room) {
        rooms.put(room.getRoomId(), room);
        System.out.println("***Room added***\nRoom Name: " + room.getRoomName() + "\nRoom ID: " + room.getRoomId()+"\n");
    }

    public void bookRoom(String roomId, EnumSet<Feature> requiredFeatures) {
        MeetingRoom room = rooms.get(roomId);
        if (room != null && room.hasFeatures(requiredFeatures)) {
        	 System.out.println("***Booking Successfully***");
            System.out.println("Room No " + roomId+" Booked\n");
        } else {
            System.out.println("!Room " + roomId + " does not meet the required features\n");
        }
    }

    public void listAvailableRooms(EnumSet<Feature> requiredFeatures) {
        List<String> availableRooms = new ArrayList<>();
        for (MeetingRoom room : rooms.values()) {
            if (room.hasFeatures(requiredFeatures)) {
                availableRooms.add(room.getRoomName());
            }
        }
        System.out.println("***Availability***\n" + requiredFeatures + "\n" + availableRooms);
    }
}

public class ManageMeetingRoom {
    public static void main(String[] args) {
        RoomScheduler scheduler = new RoomScheduler();
        scheduler.addMeetingRoom(new MeetingRoom("101", "Boardroom", 20, 
            EnumSet.of(Feature.PROJECTOR, Feature.CONFERENCE_PHONE, Feature.AIR_CONDITIONING)));

        scheduler.addMeetingRoom(new MeetingRoom("102", "Strategy Room", 10, 
            EnumSet.of(Feature.WHITEBOARD, Feature.AIR_CONDITIONING)));
        
        scheduler.bookRoom("101", EnumSet.of(Feature.PROJECTOR, Feature.CONFERENCE_PHONE));

        scheduler.bookRoom("001", EnumSet.of(Feature.PROJECTOR, Feature.CONFERENCE_PHONE));
        scheduler.listAvailableRooms(EnumSet.of(Feature.AIR_CONDITIONING));
    }
}
