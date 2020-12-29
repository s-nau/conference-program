package Event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;
import java.time.LocalDateTime;

// Contributors: Sarah Kronenfeld, Eytan Weinstein
// Last edit: Dec 9 2020

// Architecture Level - Entity

public abstract class Event implements Serializable {

    // Event is an abstract class implemented by different types of Events.

    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private String ID;
    private String password;
    private String chatID;
    private String speakerID;
    private int capacity;
    private ArrayList<String> attendees;

    /**
     * Constructor for Event objects
     * @param name The Event's name
     * @param startTime The time when the Event starts
     * @param endTime The time when the Event ends
     * @param description A description for this Event
     * @param capacity The capacity of this Event
     */
    protected Event(String name, LocalDateTime startTime, LocalDateTime endTime, String description, int capacity) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.ID = UUID.randomUUID().toString();
        this.password = UUID.randomUUID().toString().replace("-", "");
        this.chatID = UUID.randomUUID().toString();
        this.capacity = capacity;
        this.attendees = new ArrayList<String>();
        this.speakerID = speakerID;
    }

    /**
     * Getter for the name of this Event
     * @return the name of the Event (as a String)
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for the name of this Event
     * @param name The new name of this Event
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the start time of this Event
     * @return the start time of this Event
     */
    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    /**
     * Setter for the start time of this Event
     * @param startTime The new start time of this Event
     */
    public void setStartTime(LocalDateTime startTime) {this.startTime = startTime;}

    /**
     * Getter for the end time of this Event
     * @return the end time of this Event
     */
    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    /**
     * Setter for the end time of this Event
     * @param endTime The new end time of this Event
     */
    public void setEndTime(LocalDateTime endTime) {this.endTime = endTime;}

    /**
     * A getter for the description of this Event
     * @return a description of this Event (as a String)
     */
    public String getDescription() {return this.description;};

    /**
     * Getter for the ID of this Event
     * @return the ID of the event
     */
    public String getID() {
        return this.ID;
    }

    /**
     * Getter for this Event's password
     * @return String representing the Event's password
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * Getter for this Event's chatID
     * @return String representing the Event's chatID
     */
    public String getChatID(){
        return this.chatID;
    }

    /**
     * Setter for this Event's chatID
     * @param ID The new chatID for this Event
     */
    public void setChatID(String ID){
        this.chatID = ID;
    }

    /**
     * Getter for the capacity of this Event
     * @return the capacity of this Event (as an int)
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Setter for the capacity of this Event
     * @param capacity The new capacity of this Event
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Getter for the IDs of the Attendees attending this Event
     * @return an array of these Attendee IDs
     */
    public ArrayList<String> getAttendeeIDs() {
        return this.attendees;
    }

    /**
     * Adds an Attendee to this Event's list of Attendees
     * @param ID The ID of the new Attendee
     */
    public void addAttendee(String ID) {
        this.attendees.add(ID);
    }

    /**
     * Removes an Attendee from this Event's list of Attendees
     * @param ID The ID of the Attendee being removed
     */
    public void removeAttendee(String ID) {
        this.attendees.remove(ID);
    }

    /**
     * Returns whether or not another Event conflicts with this one.
     * @param event The other Event. (NOTE: the other event is presumed to be in the same Room.)
     * @return True or false.
     */
    protected abstract boolean conflictsWith(Event event);

    /**
     * Returns the current occupancy of this Event (total Attendees and Speakers signed up).
     * @return The current occupancy.
     */
    protected abstract int getOccupancy();

    /**
     * A textual representation of this event
     * @return The name and description of this event (as a String)
     */
    @Override
    public String toString() {
        return "Title: " + getName() + "\n" + getDescription();
    }

}
