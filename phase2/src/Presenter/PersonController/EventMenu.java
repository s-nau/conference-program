package Presenter.PersonController;

import Presenter.Central.SubMenuPrinter;
import Presenter.Exceptions.InvalidChoiceException;
import Presenter.Exceptions.NoDataException;
import Event.EventManager;
import Event.RoomManager;
import Person.PersonManager;

// Programmers: Sarah Kronenfeld
// Description: Abstract class to hold methods all EventMenus will need
// Date Created:
// Date Modified: 02/12/2020

public abstract class EventMenu implements SubMenuPrinter {
    protected RoomManager rooms;
    protected EventManager events;
    protected PersonManager persons;

    public EventMenu(RoomManager rooms, EventManager events, PersonManager persons) {
        this.rooms = rooms;
        this.events = events;
        this.persons = persons;
    }

    public String[] getRoomList() throws NoDataException{
        try {
            String[] opt1  = {"See all events"};
            String[] options = new String[rooms.getRoomNames().length+1];
            System.arraycopy(opt1, 0, options, 0, 1);
            System.arraycopy(rooms.getRoomNames(), 0, options, 1, rooms.getRoomNames().length);
            return options;
        }
        catch (NullPointerException e) {
            throw new NoDataException("room");
        }
    }

    public String getEventListTitle(){
        return "-EVENTS-";
    }

    protected String getEventListTitle(String condition){
        return "\n-EVENTS " + condition.toUpperCase() + "-";
    }

    protected String[] printEventList(String[] eventIDList) throws InvalidChoiceException {
        try {
            String[] events = new String[eventIDList.length];
            for (int i  = 0; i < eventIDList.length; i++) {
                events[i] = formatEvent(eventIDList[i]);
            }
            return events;
        }
        catch (NullPointerException e) {
            throw new InvalidChoiceException("event");
        }
    }

    public String[] printEventsInRoom(String name) throws InvalidChoiceException {
        try {
            if (name.equals("0")) {
                return printEventList(events.getEventIDs());
            }

            String id = rooms.getRoomID(name);
            if(id != null) {
                return printEventList(rooms.getEventIDs(id));
            }
            else {
                throw new InvalidChoiceException("room");
            }
        }
        catch (NullPointerException e) {
            throw new NoDataException("room");
        }
    }

    public String[] printAllEvents() throws InvalidChoiceException {
        try {
            return printEventList(events.getEventIDs());
        }
        catch (NullPointerException e) {
            throw new NoDataException("event");
        }
    }

    private String formatEvent(String eventID) throws InvalidChoiceException  {
        try {
            StringBuilder e = new StringBuilder();
            e.append(events.getEventType(eventID).toString());
            e.append(": ");
            e.append(events.getEventName(eventID));
            e.append("\nby ");
            e.append(persons.getName(events.getSpeakerID(eventID)));
            e.append(" in room ");
            e.append(rooms.getEventRoom(eventID));
            e.append("\nfrom ");
            e.append(events.getStartTime(eventID));
            e.append(" to ");
            e.append(events.getEndTime(eventID));
            e.append("\n");
            e.append(events.getDescription(eventID));
            return e.toString();
        } catch (NullPointerException n) {
            throw new InvalidChoiceException("event");
        }
    }

    /**
     * Gets a list of formatted events to be displayed
     * @param eventIDs the IDs of the list of events
     * @return a String array of event information
     * @throws InvalidChoiceException thrown if event list is empty
     */
    public String[] getEventList(String[] eventIDs) throws InvalidChoiceException {
        if (eventIDs.length != 0) {
            return printEventList(eventIDs);
        } else {
            throw new NoDataException("such event");
        }
    }

}
