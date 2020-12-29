package Event;

import java.time.LocalDateTime;

// Contributors: Sarah Kronenfeld, Eytan Weinstein
// Last edit: Dec 9 2020

// Architecture Level - Use Class

public class EventPermissions {

    private RoomAccess roomAccess;
    private EventAccess eventAccess;

    /**
     * Constructor for an EventPermissions object
     */
    public EventPermissions (RoomAccess roomAccess, EventAccess eventAccess) {
        this.roomAccess = roomAccess;
        this.eventAccess = eventAccess;
    }

    /**
     * Signs an individual Attendee up for an Event
     * @param personID The ID of the Attendee
     * @param eventID The ID of the Event
     * @return Whether the Attendee was signed up
     */
    public boolean signAttendeeUpForEvent(String personID, String eventID) throws CapacityException {
        try {
            if (checkEventCapacity(eventID)) {
                Event event = eventAccess.getEvent(eventID);
                event.addAttendee(personID);
                return true;
            }
            throw new CapacityException();
        } catch (NullPointerException n) {
            return false;
        }
    }

    /**
     * Takes an individual Attendee off an Event's list of attendees
     * @param personID The ID of the Attendee to be removed
     * @param eventID The ID of the Event to remove the Attendee from
     * @return whether the Attendee was removed from the Event (true or false)
     */
    public boolean removeAttendeeFromEvent(String personID, String eventID) {
        Event event = eventAccess.getEvent(eventID);
        if (event != null) {
            event.removeAttendee(personID);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Signs an individual Speaker up for a Panel
     * @param speakerID The ID of the Speaker
     * @param eventID The ID of the Panel
     * @return Whether the Speaker was signed up
     */
    public boolean signSpeakerUpForPanel(String speakerID, String eventID) throws CapacityException, NotPanelException {
        try {
            if (!checkEventCapacity(eventID)) {
                throw new CapacityException();
            }
            else if (!(eventAccess.getEvent(eventID).getClass().equals(Panel.class))) {
                throw new NotPanelException();
            }
            else {
                Panel panel = (Panel) eventAccess.getEvent(eventID);
                panel.addSpeaker(speakerID);
                return true;
            }
        } catch (NullPointerException n) {
            return false;
        }
    }

    /**
     * Takes an individual Speaker off a Panel's list of attendees
     * @param speakerID The ID of the Speaker to be removed
     * @param eventID The ID of the Panel to remove the Speaker from
     * @return whether the Speaker was removed from the Panel (true or false)
     */
    public boolean removeSpeakerFromPanel(String speakerID, String eventID) throws NotPanelException {
        try {
            if (!(eventAccess.getEvent(eventID).getClass().equals(Panel.class))) {
                throw new NotPanelException();
            }
            else {
                Panel panel = (Panel) eventAccess.getEvent(eventID);
                panel.removeSpeaker(speakerID);
                return true;
            }
        } catch (NullPointerException n) {
            return false;
        }
    }

    /**
     * Checks if there is still space for more Attendees or Speakers to be added to this Event
     * @param eventID The ID of the Event
     * @return True if there is still space for more Attendees or Speakers to be added to this Event
     */
    private boolean checkEventCapacity(String eventID) {
        return ((eventAccess.getEvent(eventID).getCapacity() - eventAccess.getEvent(eventID).getOccupancy()) > 0);
    }

    /** Checks if an Event with the inputted capacity can be added to the inputted Room. It cannot be added if its
     * capacity exceeds the capacity of the Room it is in at any time, or if adding it in addition to other Events
     * happening at the same time would exceed the capacity of the Room.
     * @param startTime The start time of the Event
     * @param endTime The end time of the Event
     * @param capacity The capacity of the Event
     * @param roomID The ID of the Room
     * @return True if the Event can be added
     */
    public boolean checkRoomCapacity(LocalDateTime startTime, LocalDateTime endTime, int capacity, String roomID) {
        try {
            int room_capacity = roomAccess.getRoom(roomID).getCapacity();
            int current_occupancy = 0;
            for (String eventID : roomAccess.getEventIDs(roomID)) {
                Talk talk = new Talk("", "", startTime, endTime, "", 0);
                if (this.eventAccess.getEvent(eventID).conflictsWith(talk)) {
                    current_occupancy = current_occupancy + eventAccess.getEvent(eventID).getCapacity();
                }
                if ((room_capacity - current_occupancy) >= capacity) {return true;}
                else {
                    return false;
                }
            }
        }catch (NullPointerException n) {
            return false;
        }
        return false;
    }

    /**
     * Checks if the inputted Event conflicts with multiple other inputted Events
     * @param startTime The time at which the Event will start
     * @param endTime The time at which the Event will end
     * @param type The type of Event, as an EventType
     * @param roomID The the ID of the room the Event will be held in
     * @return True if the Event does not conflict with any existing events
     */
    public boolean checkConflicts(LocalDateTime startTime, LocalDateTime endTime, EventType type, String roomID) {
        try {
            String[] eventIDs = roomAccess.getEventIDs(roomID);
            return checkConflicts(startTime, endTime, type, eventIDs);
        } catch (NullPointerException n) {
            return true;
        }
    }

    /**
     * Checks if the inputted Event conflicts with any other Events in its Room and time slot
     * @param startTime The time at which the inputted Event will start
     * @param endTime The time at which the inputted Event will end
     * @param type The type of the inputted Event, as an EventType
     * @param events The IDs of the Events with which the new Event might conflict
     * @return True if the event does not conflict with any existing Events
     */
    public boolean checkConflicts(LocalDateTime startTime, LocalDateTime endTime, EventType type, String[] events) {
        Event event;
        if (type.equals(EventType.WORKSHOP)) {
            event = new Workshop("", "", startTime, endTime, "", 0);
        } else if (type.equals(EventType.TALK)) {
            event = new Talk("", "", startTime, endTime, "", 0);
        } else if (type.equals(EventType.PARTY)) {
            event = new Party("", startTime, endTime, "", 0);
        } else if (type.equals(EventType.PANEL)) {
            event = new Panel("", startTime, endTime, "", 0);
        } else {
            return false;
        }
        try {
            for (String eventID : events) {
                if (this.eventAccess.getEvent(eventID).conflictsWith(event)) {
                    return false;
                }
                return true;
            }
        } catch (NullPointerException n) {
            return true;
        }
        return true;
    }

}

