package Event;

import java.io.Serializable;
import java.util.UUID;

// Contributors: Sarah Kronenfeld, Eytan Weinstein
// Last edit: Dec 6 2020

// Architecture Level - Entity

class Room implements Serializable {

    private int capacity;
    private String ID;
    private String name;

    /**
     * Constructor for a Room object, with ID automatically assigned
     * @param name The Room's name
     * @param capacity The Room's capacity
     */
    public Room (String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        ID = UUID.randomUUID().toString();
    }

    /**
     * Getter for the name of this Room
     * @return the name of this Room (as a String)
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the capacity of this Room
     * @return the capacity of the Room (as an int)
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Getter for the ID of this Room
     * @return the ID of this Room
     */
    public String getID() {
        return ID;
    }

}


