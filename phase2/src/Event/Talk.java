package Event;

import java.time.LocalDateTime;

// Contributors: Sarah Kronenfeld, Eytan Weinstein
// Last edit: Dec 9 2020

// Architecture Level - Entity

public class Talk extends Event {

    // A Talk is a presentation given by one Speaker, and cannot take place in the same Room as any other Event while
    // that Event is occurring.

    private String speakerID;

    /**
     * Constructor for Talk objects
     * @param name The Talk's name
     * @param speakerID The ID of the Speaker at this Talk
     * @param startTime The time when the Talk starts
     * @param endTime The time when the Talk ends
     * @param description The description of this Talk
     * @param capacity The capacity of this Talk
     */
    public Talk (String name, String speakerID, LocalDateTime startTime, LocalDateTime endTime, String description,
                 int capacity)
    {
        super(name, startTime, endTime, description, capacity);
        this.speakerID = speakerID;
    }

    /**
     * Checks if the inputted Event conflicts in time with this Talk
     * @param event The Event which is being checked for conflicts with this Talk
     * @return whether or not there is a conflict (true or false)
     */
    public boolean conflictsWith(Event event) {
        return this.getStartTime().isBefore(event.getEndTime()) && event.getStartTime().isBefore(this.getEndTime());
    }

    /**
     * Returns the current occupancy of this Talk (total Attendees and Speakers signed up).
     * @return The current occupancy.
     */
    public int getOccupancy() {
        return (this.getAttendeeIDs().size() + 1);
    }

    /**
     * Getter for the ID of the Speaker at this Talk
     * @return the ID of the Speaker at this Talk (as a String)
     */
    public String getSpeakerID() {
        return this.speakerID;
    }

    /**
     * Setter for the ID of the Speaker at this Talk
     * @param speakerID The new ID of the Speaker at this Talk (as a String)
     */
    public void setSpeakerID(String speakerID) {
        this.speakerID = speakerID;
    }

}

