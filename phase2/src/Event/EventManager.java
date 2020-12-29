package Event;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

// Contributors: Sarah Kronenfeld, Eytan Weinstein
// Last edit: Dec 9 2020

// Architecture Level - Use Class

abstract class EventAccess {

    // EventAccess is an abstract class representing an object that can find Events at this convention by ID.

    protected abstract Event getEvent(String eventID);
}

public class EventManager extends EventAccess implements Serializable {

    /**
     * A mapping of event names to their respective IDs.
     */
    private Map<String, String> eventsByName;

    /**
     * A mapping of IDs to the respective events they represent.
     */
    private Map<String, Event> events;

    /**
     * Creates a new empty EventManager
     */
    public EventManager() {
        events = new TreeMap<String, Event>();
        eventsByName = new TreeMap<String, String>();
    }

    // Methods for accessing Events in EventManager

    /**
     * Returns an Event, given its ID
     * @param eventID The ID of the Event
     * @return The Event
     */
    public Event getEvent(String eventID) {
        try {
            return events.get(eventID);
        } catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Returns all the Events from a list of IDs
     * @param IDs The IDs of the Events you want to get
     * @return The Events, as an array of their String representations
     */
    protected Event[] getEventList(String[] IDs) {
        try {
            if (IDs != null && IDs.length > 0) {
                Event[] eventInfoArray = new Event[IDs.length];
                for (int i = 0; i < IDs.length; i++) {
                    eventInfoArray[i] = events.get(IDs[i]);
                }
                return eventInfoArray;
            }
            return null;
        } catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Returns the ID of an Event given its name
     * @param name The Event's name
     * @return The ID of that Event
     */
    public String getEventID(String name) {
        try {
            return eventsByName.get(name);
        } catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Helper getter for the all the Events in this EventManager
     * @return an array of all Events in this EventManager
     */
    public String[] getEventIDs() {
        String[] eventArray = {};
        if (events.size() > 0) {
            return events.keySet().toArray(eventArray);
        }
        return null;
    }

    /**
     * Returns the type of an Event stored in this EventManager, given its ID
     * @param eventID The ID of the Event
     * @return The type of the Event, as an EventType
     */
    public EventType getEventType(String eventID) {
        try {
            Event event = getEvent(eventID);
            if (event.getClass().equals(Talk.class)) {
                return EventType.TALK;
            } else if (event.getClass().equals(Workshop.class)) {
                return EventType.WORKSHOP;
            } else if (event.getClass().equals(Panel.class)) {
                return EventType.PANEL;
            } else {
                return EventType.PARTY;
            }
        } catch (NullPointerException n) {
            return null;
        }
    }

    // Getters and setters for Events stored in EventManager

    /**
     * Returns the name of an Event stored in this EventManager, given its ID
     * @param eventID The ID of the Event
     * @return The name of the Event, as a String
     */
    public String getEventName(String eventID) {
        try {
            return events.get(eventID).getName();
        } catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Returns the description of an Event stored in this EventManager, given its ID
     * @param eventID The ID of the Event
     * @return The description of the Event, as a String
     */
    public String getDescription(String eventID){
        try {
            return events.get(eventID).getDescription();
        } catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Returns the ID of speaker of Event in this EventManager, given event's ID - for OrgEventController
     * @param eventID The ID of the Event
     * @return The ID of the speaker, as a String
     */
    public String getSpeakerID(String eventID) {
        try {
            return getSpeakerID(eventID);
        }
        catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Getter for the start time of the Event of inputted ID
     * @param eventID The ID of the Event
     * @return The start time of the Event (as a LocalDateTime object)
     */
    public LocalDateTime getStartTime(String eventID) {
        try {
            return this.getEvent(eventID).getStartTime();
        } catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Getter for the end time of the Event of inputted ID
     * @param eventID The ID of the Event
     * @return The end time of the Event (as a LocalDateTime object)
     */
    public LocalDateTime getEndTime(String eventID) {
        try {
            return this.getEvent(eventID).getEndTime();
        } catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Getter for this password of the Event with the inputted eventID
     * @param eventID The ID of the Event
     * @return The Event's password (as a String)
     */
    public String getEventPassword(String eventID) {
        try {
            return this.getEvent(eventID).getPassword();
        } catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Getter for the chatID of the Event of inputted ID
     * @param eventID The ID of the Event
     * @return The Event's chatID
     */
    public String getEventChat(String eventID) {
        try {
            return this.getEvent(eventID).getChatID();
        } catch (NullPointerException n) {
            return null;
        }
    }

    /**
     * Setter for the chatID of the Event of inputted ID
     * @param eventID The ID of the Event
     * @param chatID  The new chatID
     */
    public void setEventChat(String eventID, String chatID) {
        try {
            this.getEvent(eventID).setChatID(chatID);
        } catch (NullPointerException n) {
        }
    }

    /**
     * Setter for the capacity of the Event of inputted ID
     * @param eventID  The ID of the Event
     * @param capacity The new capacity of the Event
     */
    public void setCapacity(String eventID, int capacity) {
        try {
            this.getEvent(eventID).setCapacity(capacity);
        } catch (NullPointerException n) {
        }
    }

    /**
     * Getter for the IDs of the Attendees attending the Event of inputted ID
     * @param eventID The ID of the Event
     * @return an array of the IDs of the Attendees at that Event
     */
    public ArrayList<String> getAttendeeIDs(String eventID) {
        try {
            return this.getEvent(eventID).getAttendeeIDs();
        } catch (NullPointerException n) {
            return null;
        }
    }

    // Methods for adding/removing Events to/from this EventManager

    /**
     * Adds an Event to this EventManager
     * @param name        The name of the Event to be created
     * @param speakerID   The ID of the Speaker of the Event to be created, or "" if there is no Speaker
     * @param startTime   The start time of the Event to be created, as a LocalDateTime object
     * @param endTime     The end time of the Event to be created, as a LocalDateTime object
     * @param description The description for the Event to be created
     * @param capacity    The capacity of the Event to be created
     * @param type        The type of the Event to be created, as an EventType
     * @return The new Event's ID
     */
    public String addEvent(String name, String speakerID, LocalDateTime startTime, LocalDateTime endTime, String
            description, int capacity, EventType type) {
        Event event;
        if (type.equals(EventType.TALK)) {
            event = new Talk(name, speakerID, startTime, endTime, description, capacity);
        } else if (type.equals(EventType.WORKSHOP)) {
            event = new Workshop(name, speakerID, startTime, endTime, description, capacity);
        } else if (type.equals(EventType.PANEL)) {
            event = new Panel(name, startTime, endTime, description, capacity);
        } else if (type.equals(EventType.PARTY)) {
            event = new Party(name, startTime, endTime, description, capacity);
        } else {
            return null;
        }
        events.put(event.getID(), event);
        eventsByName.put(event.getName(), event.getID());
        return event.getID();
    }

    /**
     * Deletes an Event in this EventManager
     * @param ID The Event's ID
     * @return whether the Event has been successfully deleted
     */
    public boolean removeEvent(String ID) {
        try {
            eventsByName.remove(events.get(ID).getName());
            events.remove(ID);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    // Methods to compare Events in EventManager

    /**
     * Checks to see if this EventManager contains an Event of a certain name
     * @param name The name of the Event
     * @return True if an Event with this name exists; false if not
     */
    public boolean contains(String name) {
        if (getEventID(name) != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != EventManager.class) {
            return false;
        }
        EventManager events2 = (EventManager) obj;
        try {
            if (events2.getEventList(this.getEventIDs()).length > this.getEventIDs().length) {
                return false;
            }
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

}
