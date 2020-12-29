package Event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

// Contributors: Sarah Kronenfeld, Eytan Weinstein
// Last edit: Dec 6 2020

// Architecture Level - Use Class

abstract class RoomAccess {

    // RoomAccess is an abstract class representing an object that can find Rooms at this convention by RoomID.

    protected abstract Room getRoom(String roomID);
    abstract String[] getEventIDs(String roomID);

}

public class RoomManager extends RoomAccess implements Serializable {

    /** A mapping of Room IDs to the lists of IDs for Events taking place in those Rooms. */
    private final Map<String, ArrayList<String>> roomEventList;

    /** A mapping of Room IDs to their respective Room objects. */
    private final Map<String, Room> roomList;

    /** A mapping of Room names to their respective IDs. */
    private final Map<String, String> roomsByName;

    /**
     * Constructor for RoomManager objects
     */
    public RoomManager() {
        roomEventList = new TreeMap<>();
        roomList = new TreeMap<>();
        roomsByName = new TreeMap<>();
    }

    /**
     * Returns the Room associated with this RoomID
     * @param roomID The Room's ID
     * @return The Room
     */
    protected Room getRoom(String roomID) {
        try {
            return this.roomList.get(roomID);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Finds the ID of a specific Room (by name)
     * @param name The Room's name
     * @return The Room's ID
     */
    public String getRoomID (String name) {
        try {
            return this.roomsByName.get(name);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Finds the name of a specific Room (by ID)
     * @param ID The Room's ID
     * @return The Room's name
     */
    public String getRoomName (String ID) {
        try {
            return this.roomList.get(ID).getName();
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Returns a list of the names of all Rooms currently established at this convention
     * @return An array of the Room names at this convention
     */
    public String[] getRoomNames() {
        String[] names = {};
        try {
            return this.roomsByName.keySet().toArray(names);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Gets the Room an Event is held in
     * @param ID the ID of the Event
     * @return the name of the Room this Event is in
     */
    public String getEventRoom(String ID) {
        try {
            for (String room : getRoomNames()) {
                ArrayList<String> events = this.roomEventList.get(roomsByName.get(room));
                if (events.contains(ID)) {
                    return room;
                }
            }
            return null;
        } catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Finds the list of IDs of the Events taking place in a specific Room
     * @param roomID The ID of the Room
     * @return The list of IDs of the Events taking place in that Room
     */
    public String[] getEventIDs(String roomID) {
        try {
            String[] evList = {};
            return this.roomEventList.get(roomID).toArray(evList);
        } catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Returns the number of Rooms currently in this convention
     * @return the number of Rooms currently in this convention
     */
    public int numRooms() {
        return this.roomList.size();
    }

    /**
     * Creates a new Room in this RoomManager with the inputted capacity
     * @param name The name of the new Room
     * @param capacity The capacity of the new Room
     * @return The ID of the new Room
     */
    public String addRoom(String name, int capacity) {
        Room thisRoom = new Room(name, capacity);
        this.roomList.put(thisRoom.getID(), thisRoom);
        this.roomsByName.put(thisRoom.getName(), thisRoom.getID());
        this.roomEventList.put(thisRoom.getID(), new ArrayList<>());
        return thisRoom.getID();
    }

    /**
     * Deletes a Room in this RoomManager
     * @param name The name of the Room to be removed
     * @return Whether or not the Room has been removed (true or false)
     */
    public boolean removeRoom(String name) {
        try {
            roomEventList.remove(this.getRoomID(name));
            roomList.remove(this.getRoomID(name));
            roomsByName.remove(name);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Adds an Event to a certain Room's list of Event IDs
     * @param roomID The Room's ID
     * @param eventID The Event's ID
     * @return True if successful, false if unsuccessful
     */
    public boolean addEvent(String roomID, String eventID) {
        try {
            ArrayList<String> events = roomEventList.get(roomID);
            events.add(eventID);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Deletes an Event in this RoomManager
     * @param eventID The ID of the Event to be removed
     * @return Whether or not the Event has been removed (true or false)
     */
    public boolean removeEvent(String eventID) {
        try {
            String room = this.getEventRoom(eventID);
            roomEventList.get(this.getRoomID(room)).remove(eventID);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Checks to see if this convention contains a Room of a certain name
     * @param name The name
     * @return True if an Room with this name exists; false if not
     */
    public boolean contains(String name) {
        if (getRoomID(name)!= null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        if (obj.getClass() != RoomManager.class){
            System.out.println("Wrong class!");
            return false;
        }
        RoomManager rooms2 = (RoomManager) obj;
        if (rooms2.numRooms() == this.numRooms()) {
            if(rooms2.getRoomNames().equals(this.getRoomNames())) {
                for (String room : this.getRoomNames()) {
                    String id = this.getRoomID(room);
                    if (rooms2.getEventIDs(id).equals(this.getEventIDs(id))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}

