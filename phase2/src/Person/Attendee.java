package Person;

import java.util.ArrayList;

public class Attendee extends Person {

    protected ArrayList<String> eventsSignedUp = new ArrayList<>();
    protected ArrayList<String> anChatList = new ArrayList<>();

    public Attendee (String fullName, String username, String password, String email){
        super(fullName, username, password, email);
        this.typePerson = 1;
    }

    /**
     * adds an event ID to the attendee's list of signed up Event
     * @param eventID takes in the ID of the event
     */
    public void signUp(String eventID) {
        this.eventsSignedUp.add(eventID);
    }

    /**
     * removes an event ID from the attendee's list of signed up Event
     * @param eventID takes in the ID of the event
     */
    public void cancelSpot(String eventID) {
        this.eventsSignedUp.remove(eventID);
    }

    /**
     * returns the events that the attendee has signed up for
     * @return ArrayList<String> events SignedUp the events that the person has signed up for.
     */
    public ArrayList<String> getEventsSignedUp(){
        return this.eventsSignedUp;
    }
}
