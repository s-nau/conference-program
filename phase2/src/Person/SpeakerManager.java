package Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpeakerManager extends PersonManager {

    /** A mapping of Speaker IDs to a list of IDs for those Events at which that Speaker is speaking. */
    protected Map<String, ArrayList<String>> allEventsBySpeaker = new HashMap<>();

    /** A mapping of Speaker IDs to a list of IDs for those Panels at which that Speaker is speaking. */
    protected Map<String, ArrayList<String>> allPanelsBySpeaker = new HashMap<>();

    /** A mapping of Speaker IDs to a list of IDs for those Talks/Workshops at which that Speaker is speaking. */
    protected Map<String, ArrayList<String>> allNonPanelsBySpeaker = new HashMap<>();

    public SpeakerManager(Map<String, Person> usernameToPerson, Map<String, Person> idToPerson) {
        super(usernameToPerson, idToPerson);
    }

    @Override
    public boolean createAccount(String name, String username, String password, String email) {
        if (!usernameToPerson.containsKey(username)) {
            Speaker newSpeaker = new Speaker(name, username, password, email);
            usernameToPerson.put(username, newSpeaker);
            idToPerson.put(newSpeaker.getID(), newSpeaker);
            ArrayList<String> event_list = new ArrayList<>();
            ArrayList<String> panel_list = new ArrayList<>();
            ArrayList<String> non_panel_list = new ArrayList<>();
            allEventsBySpeaker.put(newSpeaker.getID(), event_list);
            allPanelsBySpeaker.put(newSpeaker.getID(), panel_list);
            allNonPanelsBySpeaker.put(newSpeaker.getID(), non_panel_list);
            return true;
        }
        return false;
    }

    /** Returns a list of all Speakers at this convention
     * @return an ArrayList of the IDs for all Speakers at this convention
     */
    public ArrayList<String> getSpeakers() {
        return new ArrayList<>(allEventsBySpeaker.keySet());
    }

    /** Returns a list of all Events at which the Speaker with the inputted speakerID is speaking
     * @param speakerID ID of the Speaker
     * @return an ArrayList of all Events at which the Speaker with the inputted speakerID is speaking
     */
    public ArrayList<String> getSpeakerInEvents(String speakerID) {
        return this.allEventsBySpeaker.get(speakerID);
    }

    /** Returns a list of all Panels at which the Speaker with the inputted speakerID is speaking
     * @param speakerID ID of the Speaker
     * @return an ArrayList of all Panels at which the Speaker with the inputted speakerID is speaking
     */
    public ArrayList<String> getSpeakerInPanels(String speakerID) {
        return this.allPanelsBySpeaker.get(speakerID);
    }

    /** Returns a list of all Talks or Workshops at which the Speaker with the inputted speakerID is speaking
     * @param speakerID ID of the Speaker
     * @return an ArrayList<String> of all Talks or Workshops at which the Speaker with the inputted speakerID is speaking
     */
    public ArrayList<String> getSpeakerInNonPanels(String speakerID) {
        return this.allNonPanelsBySpeaker.get(speakerID);
    }

    /** Adds a Panel to a Speaker's list of Panels
     * @param speakerID ID of the Speaker
     * @param eventID ID of the Panel
     */
    public void addPanel(String speakerID, String eventID) {
        if (!(this.getSpeakerInEvents(speakerID).contains(eventID))) {
            this.allEventsBySpeaker.get(speakerID).add(eventID);
            this.allPanelsBySpeaker.get(speakerID).add(eventID);
            Speaker curr = (Speaker)idToPerson.get(speakerID);
            if(!curr.getEventIDs().contains(eventID)) {
                curr.signUp(eventID);
            }
        }
    }

    /** Adds a Talk or Workshop to a Speaker's list of Panels
     * @param speakerID ID of the Speaker
     * @param eventID ID of the Talk or Workshop
     */
    public void addNonPanel(String speakerID, String eventID) {
        if (!(this.getSpeakerInEvents(speakerID).contains(eventID))) {
            this.allEventsBySpeaker.get(speakerID).add(eventID);
            this.allNonPanelsBySpeaker.get(speakerID).add(eventID);
            Speaker curr = (Speaker)idToPerson.get(speakerID);
            if(!curr.getEventIDs().contains(eventID)) {
                curr.signUp(eventID);
            }
        }
    }

    /**
     * Deletes an Event from a particular Speaker's list of Events
     * @param speakerID The ID of the Speaker
     * @param eventID The ID of the Event
     * @return whether the Event has been successfully deleted
     */
    public boolean deleteEventFromSpeaker(String speakerID, String eventID) {
        try {
            if (!(this.getSpeakerInPanels(speakerID).contains(eventID))) {
                this.allPanelsBySpeaker.get(speakerID).remove(eventID);
            }
            if (!(this.getSpeakerInNonPanels(speakerID).contains(eventID))) {
                this.allNonPanelsBySpeaker.get(speakerID).remove(eventID);
            }
            this.allEventsBySpeaker.get(speakerID).remove(eventID);
            Speaker curr = (Speaker)idToPerson.get(speakerID);
            if(curr.getEventIDs().contains(eventID)) {
                curr.cancelSpot(eventID);
            }
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Deletes an Event from all Speakers' lists of Events
     * @param eventID The ID of the Event
     * @return whether the Event has been successfully deleted
     */
    public boolean deleteEvent(String eventID) {
        try {
            for (Object speaker : this.getSpeakers()) {
                String speakerID = (String) speaker;
                if (!(this.getSpeakerInPanels(speakerID).contains(eventID))) {
                    this.allPanelsBySpeaker.get(speakerID).remove(eventID);
                }
                if (!(this.getSpeakerInNonPanels(speakerID).contains(eventID))) {
                    this.allNonPanelsBySpeaker.get(speakerID).remove(eventID);
                }
                this.allEventsBySpeaker.get(speakerID).remove(eventID);
                Speaker curr = (Speaker)idToPerson.get(speakerID);
                if(curr.getEventIDs().contains(eventID)) {
                    curr.cancelSpot(eventID);
                }
            }
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Adds announcement chats for Events created by Organizer; puts chatID in Speaker's list of announcementChatIDs
     * @param speakerID The ID of the Speaker
     * @param announcementChatID The ID of the announcementChat
     * @return True iff chatID was added
     */
    public boolean addAnnouncementChats(String speakerID, String announcementChatID) {
        Speaker individual = (Speaker) idToPerson.get(speakerID);
        if(!individual.getAnChatList().contains(announcementChatID)) {
            individual.addAnChat(announcementChatID);
            return true;
        }
        return false;
    }

    /**
     * Gets the Speaker's list of contacts.
     * @param speakerID The ID of the the Speaker whose contact list we are retrieving
     * @return return a list of other people's personIds (Strings) if the desired user is found
     */
    public ArrayList<String> getContactList(String speakerID) {
        return idToPerson.get(speakerID).getContactList();
    }

    /**
     * Adds a contact to a Person's list of contacts by ID
     * @param personID The ID of the Person to whose contact list the new contact will be added
     * @param contactID the ID of the new contact to be added to the Person's contact list
     * @return return True iff the contact has been added to the contact list
     */
    public boolean addContact(String personID, String contactID) {
        Speaker individual = (Speaker) idToPerson.get(personID);
        if(!(individual.getContactList().contains(contactID))) {
            individual.addContact(contactID);
            return true;
        }
        return false;
    }

}

















