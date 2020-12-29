package Presenter.AttendeeController;

// Programmers: Cara McNeil, Allen Kim, Eytan Weinstein
// Description: All the methods that take user input in the AttendeeController Event Menu
// Date Created: 01/11/2020
// Date Modified: 09/12/2020

import Presenter.Exceptions.InvalidChoiceException;
import Presenter.Central.SubMenu;
import Event.CapacityException;
import Event.EventPermissions;
import Person.AttendeeManager;

public class AttEventController extends SubMenu {

    private final String currentUserID;
    private final AttendeeManager attendeeManager;
    private final EventPermissions eventPermissions;
    private final AttEventMenu presenter;

    public AttEventController(SubMenu subMenu, String currentUserID, AttendeeManager attendeeManager) {
        super(subMenu);
        this.currentUserID = currentUserID;
        this.attendeeManager = attendeeManager;
        eventPermissions = new EventPermissions(roomManager, eventManager);
        presenter = new AttEventMenu(currentUserID, roomManager, eventManager, attendeeManager);
    }

    /**
     * Getter for currentUserID
     * @return currentUserID
     */
    public String getCurrentUserID() {
        return currentUserID;
    }

    /**
     * Tries to sign user up for an Event
     * @param eventName The name of the Event the current user requested to sign up for
     */
    public boolean signupForEvent(String eventName) throws InvalidChoiceException, CapacityException  {
        String event = eventManager.getEventID(eventName);
        if (event == null) {
            throw new InvalidChoiceException("event");
        }
        eventPermissions.signAttendeeUpForEvent(currentUserID, event);
        boolean eventAddedToPerson = attendeeManager.signUpForEvent(currentUserID, event);
        attendeeManager.addAnChat(currentUserID, eventManager.getEventChat(event));
        return eventAddedToPerson;
    }

    /**
     * Remove this user from Event
     * @param eventName The name of the Event the current user requested to cancel
     */
    public boolean cancelSpotFromEvent(String eventName) throws InvalidChoiceException {
        String event = eventManager.getEventID(eventName);
        if (event == null) {
            throw new InvalidChoiceException("event");
        }

        boolean personRemovedFromEvent = eventPermissions.removeAttendeeFromEvent(currentUserID, event);
        boolean eventRemovedFromPerson = attendeeManager.removeSpotFromEvents(currentUserID, event);
        attendeeManager.removeAnChat(currentUserID, eventManager.getEventChat(event));
        return personRemovedFromEvent && eventRemovedFromPerson;
    }

    public String[] getEventsSignedUpFor() throws InvalidChoiceException{
        String [] eventIDs = {};
        eventIDs = attendeeManager.getEventList(currentUserID).toArray(eventIDs);
        return presenter.getEventList((eventIDs));
    }

    @Override
    public AttEventMenu getPresenter() {
        return this.presenter;
    }
}
