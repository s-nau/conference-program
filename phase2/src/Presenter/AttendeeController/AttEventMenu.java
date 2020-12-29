package Presenter.AttendeeController;

// Programmers: Cara McNeil, Allen Kim, Eytan Weinstein, Sarah Kronenfeld
// Description: Returns information pertaining to a user's attending Event information
// Date Created: 11/11/2020
// Date Modified: 02/12/2020


import Event.EventManager;
import Event.RoomManager;
import Person.AttendeeManager;
import Presenter.Exceptions.InvalidChoiceException;
import Presenter.Exceptions.NoDataException;
import Presenter.PersonController.EventMenu;

public class AttEventMenu extends EventMenu {
    AttendeeManager attendeeManager;
    String currentUserID;

    public AttEventMenu(String currentUserID, RoomManager rooms, EventManager events, AttendeeManager persons) {
        super(rooms, events, persons);
        attendeeManager = persons;
        this.currentUserID = currentUserID;
    }

    @Override
    public String getMenuTitle() {
        return "----- Attendee Event Menu -----";
    }

    @Override
    public String[] getMenuOptions() {
        return new String[]{"View convention events list", "Sign up for an event",
                "Cancel your spot in an event", "View events you've signed up for"};
    }

    // Option 1

    public String printRoomChoicePrompt() {
        return "\n Which room's schedule do you want to see? \n (Press 0 for options, or press 1 to see " +
                "all events in the convention.)";
    }

    /**
     * Prints the list of Events happening in a given room
     * @param roomName the name of the room in which the Events are being held
     */
    public String roomEventListTitle(String roomName) {
        return getEventListTitle("IN ROOM " +
                roomName.toUpperCase());
    }

    // Option 2

    /**
     * Prompts the user to enter the name of the Event they want to sign up for
     */
    public String printAddEventPrompt() {
        return "Enter the exact name of the event that you would like to sign up for: ";
    }

    /**
     * Prints a confirmation that the user has been signed up for an Event
     */
    public String printEventAdded() {
        return "Event sign-up successful.";
    }

    /**
     * Prints a notice to the user that the Event they intended to sign up is full
     */
    public String printEventFull() {
        return "Event sign-up unsuccessful. This event is full.";
    }

    // Option 3

    /**
     * Prompts the user to enter the name of the Event they wish to remove from their Event list
     */
    public String printRemoveEventPrompt() {
        return "Enter the exact name of the event that you would like to cancel your spot from: ";
    }

    /**
     * Prints a confirmation that the user has removed an Event from their Event list
     */
    public String printEventRemoved() {
        return "Event spot cancellation successful.";
    }

    // Option 4

    /**
     * Prints the list of Events this AttendeeController user has signed up for
     */
    public String ownEventListTitle() {
        return getEventListTitle("you have signed up for");
    }

}
