package Person;

import java.util.ArrayList;
import java.util.Map;

public class AttendeeManager extends PersonManager{

    public AttendeeManager(Map<String, Person> usernameToPerson, Map<String, Person> idToPerson) {
        super(usernameToPerson, idToPerson);
    }

    /**
     * instantiates an Attendee object, and assigns it the attributes given by the client entering their info
     * @param name the full name of the person who would like to create an account
     * @param username the username that the person would like to use (will be checked if it exists already or not)
     * @param password the password that the person will secure their account with
     * @param email the email that the person wants to tie their account with
     * @return returns true if an account has been successfully created; returns false if not
     */
    @Override
    public boolean createAccount(String name, String username, String password, String email) {
        if(!usernameToPerson.containsKey(username)) {
            Attendee newAtt = new Attendee(name, username, password, email);
            idToPerson.put(newAtt.getID(), newAtt);
            usernameToPerson.put(username, newAtt);
            return true;
        }
        return false;
    }

    /**
     * adds the given eventID to the desired Attendee's "list of event IDs that they are attending"
     * @param userID the ID of the user (not the same as a username)
     * @param eventID the ID of the event the user wants to sign up for
     * @return returns true if the eventID has successfully been added to the user's list of eventIDs, false if not
     */
    public boolean signUpForEvent(String userID, String eventID) {
        Attendee curr = (Attendee)idToPerson.get(userID);
        if(!curr.getEventsSignedUp().contains(eventID)) {
            curr.signUp(eventID);
            return true;
        }
        return false;
    }

    /**
     * removes an eventID from the desired Attendee's list of eventIDs
     * @param userID the ID of the user
     * @param eventID the ID of the event the user wants to cancel their spot from
     * @return returns true if the desired eventID has been removed from the user's list of eventIDs; otherwise false
     */
    public boolean removeSpotFromEvents(String userID, String eventID) {
        Attendee curr = (Attendee)idToPerson.get(userID);
        if(curr.getEventsSignedUp().contains(eventID)) {
            curr.cancelSpot(eventID);
            return true;
        }
        return false;
    }

    /**
     * gets the desired user's list of contacts. Uses the given userID to search for the user
     * @param userID the user ID of the person whose contact list we want to get
     * @return returns a list of other people's userIDs (Strings) if the desired user is found
     */
    public ArrayList<String> getContactList(String userID) {
        return idToPerson.get(userID).getContactList();
    }

    /**
     * adds a contact to a user's list of contacts, by their ID
     * @param userID the user ID of the person whose contact list we want to add to
     * @param contactID the ID of the contact we want to add to the user's list of contacts
     * @return returns true if the contact has been successfully added; false if contact already exists inside
     * the user's list of contacts
     */
    public boolean addContact(String userID, String contactID) {
        Attendee currAtt = (Attendee)idToPerson.get(userID);
        if(!currAtt.getContactList().contains(contactID)) {
            currAtt.addContact(contactID);
            return true;
        }
        return false;
    }

    /**
     * accesses an Attendee object to retrieve a list of events (eventIDs) that the attendee has signed up for
     * @param userID the ID of the user whose list of events we would like to see
     * @return returns the list of eventIDs that represent the events the Attendee is signed up for
     */
    public ArrayList<String> getSignedUpEvents(String userID) {
        return ((Attendee)idToPerson.get(userID)).getEventsSignedUp(); //gets the list from Attendee
    }

    /**
     * Adds a chat into announcement chats for attendee with chatID set up by announcement for all those in events
     * @param currentID the user's ID
     * @param chatID the chatID of the event which has announcements issued about it
     */
    public void addAnChat(String currentID, String chatID) {
        Attendee attendee = (Attendee)getPerson(currentID);
        attendee.addAnChat(chatID);
    }

    /**
     * Removes a chat from announcement chats for attendee with chatID set up by announcement for all those in events
     * @param currentID the user's ID
     * @param chatID the chatID of the event which has announcements issued about it
     */
    public void removeAnChat(String currentID, String chatID) {
        Attendee attendee = (Attendee)getPerson(currentID);
        attendee.removeAnChat(chatID);
    }

    /**
     *
     * @param currentUserID of the attendee
     * @return ArrayList<String>,of chatID of messages received by the User; getAnCatList() is from PersonManager
     */

    public ArrayList<String> getAnChats(String currentUserID) {
        Attendee attendee = (Attendee)getPerson(currentUserID);
        return attendee.getAnChatList();

    }

}

