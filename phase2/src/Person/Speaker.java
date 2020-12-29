package Person;

import java.util.ArrayList;
import java.util.UUID;

// There are no events for which a Speaker can sign for in the manner of an Attendee; nor can a Speaker register
// himself or herself for an Event. This can be done exclusively by an Organizer.

public class Speaker extends Person {

    /** A list of the IDs for all Events where this Speaker is speaking. */
    protected ArrayList<String> EventIDs = new ArrayList<>();

    Speaker (String fullName, String username, String password, String email){
        super(fullName, username, password, email);
        this.typePerson = 3;
    }

    /**
     * Adds the ID of an Event to the Speaker's list of Events
     * @param eventID the ID of the Event
     */
    public void signUp(String eventID) {
        this.EventIDs.add(eventID);
    }

    /**
     * Removes the ID of an Event from the Speaker's list of Events
     * @param eventID the ID of the Event
     */
    public void cancelSpot(String eventID) {
        this.EventIDs.remove(eventID);
    }

    /**
     * Returns the Events that the Speaker has been signed up for by the Organizer
     * @return the IDs of the Events that the Speaker has signed up for
     */
    public ArrayList<String> getEventIDs(){
        return EventIDs;
    }

}



