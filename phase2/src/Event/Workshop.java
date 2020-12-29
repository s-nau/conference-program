package Event;

import java.time.LocalDateTime;

// Contributors: Eytan Weinstein
// Last edit: Dec 9 2020

// Architecture Level - Entity

public class Workshop extends Event {

    // A Workshop is a session on a particular topic delivered to a group of Attendees by one Speaker. It can take place
    // in the same Room as other Workshops (but not Talks, Parties, or Panels!) so long as the number of its attendees
    // does not exceed the capacity of that Room.

    private String speakerID;

    /**
     * Constructor for Workshop objects
     * @param name The Workshop's name
     * @param speakerID The ID of the speaker at this Workshop
     * @param startTime The time when the Workshop starts
     * @param endTime The time when the Workshop ends
     * @param description The description of this Workshop
     * @param capacity The capacity of this Workshop
     */
    public Workshop (String name, String speakerID, LocalDateTime startTime, LocalDateTime endTime, String description,
                     int capacity)
    {
        super(name, startTime, endTime, description, capacity);
        this.speakerID = speakerID;
    }

    /**
     * Checks if the inputted Event conflicts in time with this Workshop
     * @param event The event which is being checked for conflicts with this Workshop
     * @return whether or not there is a conflict (true or false)
     */
    public boolean conflictsWith(Event event) {
        if (!(event instanceof Workshop)) {
            if (this.getStartTime().isBefore(event.getEndTime())) {
                if (this.getEndTime().isAfter(event.getStartTime()) || this.getStartTime().isAfter(event.getStartTime())
                ) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the current occupancy of this Workshop (total Attendees and Speakers signed up).
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
