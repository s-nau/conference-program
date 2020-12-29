package Event;

import java.time.LocalDateTime;

// Contributors: Eytan Weinstein
// Last edit: Dec 9 2020

// Architecture Level - Entity

public class Party extends Event {

    // A Party is any large Event without a Speaker (usually a party; might also be a concert, meet-and-greet, etc.). It
    // cannot cannot take place in the same Room as any other Event while that Event is occurring.

    /**
     * Constructor for Party objects
     * @param name The Party's name
     * @param startTime The time when the Party starts
     * @param endTime The time when the Party ends
     * @param description The description of this Party
     * @param capacity The capacity of this Party
     */
    public Party (String name, LocalDateTime startTime, LocalDateTime endTime, String description, int capacity) {
        super(name, startTime, endTime, description, capacity);
    }

    /**
     * Checks if the inputted Event conflicts in time with this Party
     * @param event The Event which is being checked for conflicts with this Party
     * @return whether or not there is a conflict (true or false)
     */
    public boolean conflictsWith(Event event) {
        return this.getStartTime().isBefore(event.getEndTime()) && event.getStartTime().isBefore(this.getEndTime());
    }

    /**
     * Returns the current occupancy of this Party (total Attendees signed up).
     * @return The current occupancy.
     */
    public int getOccupancy() {
        return this.getAttendeeIDs().size();
    }

}
