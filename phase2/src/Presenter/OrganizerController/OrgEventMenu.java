package Presenter.OrganizerController;

// Programmers: Cara McNeil, Eytan Weinstein
// Description: Prints information pertaining to an OrganizerController's Event planning
// Date Created: 13/11/2020
// Date Modified: 10/12/2020

import Event.EventManager;
import Event.EventType;
import Event.RoomManager;
import Person.PersonManager;
import Presenter.Exceptions.InvalidChoiceException;
import Presenter.Exceptions.NoDataException;
import Presenter.PersonController.EventMenu;
//import com.sun.org.apache.xpath.internal.operations.String;

public class OrgEventMenu extends EventMenu {

    public OrgEventMenu(RoomManager rooms, EventManager events, PersonManager persons) {
        super(rooms, events, persons);
    }

    @Override
    public String getMenuTitle() {
        return "----- Organizer Event Menu -----";
    }

    @Override
    public String[] getMenuOptions() {
        return new String[]{"Create a new room", "Create a new event", "Cancel an existing event",
                "Add a speaker to a panel", "Remove a speaker from a panel", "Change the capacity of an existing event",
                "Add an announcement to an event"};
    }


    // Prompts for addRoom


    /**
     * Prompts the user to input the name of the Room they wish to add
     */
    public String addRoomPrompt() {
        return "To create a new room, please fill in the following information:";
    }

    /**
     * Prompts the user to input the name of the Room they wish to add
     */
    public String roomNamePrompt() {
        return "What is the name of the room you want to create?";
    }

    /**
     * Prompts the user to input the capacity of the Room they wish to add
     */
    public String roomCapacityPrompt() {
        return "What is the capacity of the room you want to create?";
    }


    // Prompts for createEvent


    /**
     * Prompts the user to create an Event
     */
    public String printCreateEventPrompt(){
        return "To create a new event, please fill in the following information:";
    }

    /**
     * Prompts the user to choose the type of Event they wish to create
     */
    public String printEventTypePrompt(){
        return "What kind of event do you want to create? (Enter 0 for options)";
    }

    /**
     * Prompts the user to choose a type for the Event they wish to add
     * @return the type of event they have chosen (as an EventType object)
     */
    public String[] eventTypes(String type) {
        EventType[] values = EventType.values();
        String[] types = new String[values.length];
        for (int i = 0; i < types.length; i++) {
            types[i] = values[i].toString();
        }
        return types;
    }

    @Override
    public String[] getRoomList() throws NoDataException {
        try {
            return rooms.getRoomNames();
        }
        catch (NullPointerException e) {
            throw new NoDataException("room");
        }
    }

    /**
     * Prompts the user to choose a Room for an Event (by name)
     */
    public String printRoomNamePrompt(){
        return "Which room is this event held in? (Enter 0 for options)";
    }

    /**
     * Prompts the user to name the new Event
     */
    public String printEventNamePrompt(){
        return "What is the name of this event?";
    }

    public String printChatNamePrompt(){
        return "What is the name of this chat?";
    }

    /**
     * Prompts the user to enter a description for the new Event
     */
    public String printDescriptionPrompt(){
        return "Please type a brief description for this new event:";
    }

    /**
     * Prompts the user to enter a start time for the new Event
     */
    public String printStartTimePrompt(){
        return "At what time will the event start?. \nPlease enter the exact date and time in 24-hour clock " +
                "notation. \nFor example, to schedule an event at 11:30 pm on March 4, 2020, you would type the date," +
                "followed by a space, followed by the 24-hour time, as follows: '2020-03-04 23:30'. \nBe slow and " +
                "careful!";
    }

    public String printEndTimePrompt(){
        return "\nAt what time will the event end?. \nPlease enter the exact date and time in 24-hour clock " +
                "notation. \nFor example, to schedule an event at 11:30 pm on March 4, 2020, you would type the date," +
                "followed by a space, \nfollowed by the 24-hour time, as follows: '2020-03-04 23:30'. \nBe slow and " +
                "careful!";
    }

    /**
     * Prompts the user to add the username of the SpeakerController at this new Event
     */
    public String printSpeakerUsernamePrompt(){
        return "\nWhat is the username of the speaker speaking at this event?";
    }

    /**
     * Prompts the user to type in the number of people that this new Event can fit
     */
    public String printEventCapPrompt(){
        return "\nWhat is the capacity of the event you want to create?";
    }


    // Prompts for CancelEvent


    public String cancelEventPrompt() {
        return "\nTo cancel an existing room, please fill in the following information:";
    }

    public String printEventNameToCancelPrompt(){
        return "\nWhat is the name of the event you would like to cancel?";
    }


    // Prompts for AddSpeakerToPanel


    public String addSpeakerToPanelPrompt() {
        return "\nTo sign up a speaker for an existing panel, please fill in the following information:";
    }

    public String speakerToAddPrompt() {
        return "\nWhat is the username of the speaker you wish to sign up?";
    }

    public String panelToAddToPrompt() {
        return "\nWhat is name of the panel you wish to sign up this speaker for?";
    }


    // Prompts for RemoveSpeakerFromPanel


    public String removeSpeakerFromPanelPrompt() {
        return "\nTo remove a speaker from an existing panel, please fill in the following information:";
    }

    public String speakerToRemovePrompt() {
        return "\nWhat is the username of the speaker you wish to remove?";
    }

    public String panelToRemoveFromPrompt() {
        return "\nWhat is name of the panel you wish to remove this speaker from?";
    }


    // Prompts for ChangeEventCapacity


    public String changeEventCapacityPrompt() {
        return "\nTo change the capacity of an existing event, please fill in the following information:";
    }

    public String eventToChangePrompt() {
        return "\nWhat is the name of the event for which you wish to change the capacity?";
    }

    public String newCapacityPrompt() {
        return "\nWhat should the new capacity of this event be?";
    }


    /**
     * Prompts user to update the capacity of an Event
     * @return
     */
    public String printUpdateCapacity(){return "To update the capacity of an Event, please fill in the " +
            "following information:";}

    /**
     * Prompts user to enter the new capacity of the event represented by its ID
     * @param eventId ID of event that will have updated capacity
     * @return
     */
    public String printChangeCapacity(String eventId){return "Please enter the new capacity of event: " +
            events.getEventName(eventId);}


    /**
     * Prompts the user to add a SpeakerController account
     */
    public String printAddSpeakerPrompt(){
        return "\nTo create a new speaker account, please fill in the following information:";
    }

    /**
     * Prompts the user to enter a name for the SpeakerController
     */
    public String printAddNamePrompt(){
        return "What is the speaker's full name?";
    }

    /**
     * Prompts the user to add a password for the SpeakerController
     */
    public String printAddPasswordPrompt(){
        return "Please enter a password for the speaker:";
    }

    /**
     * Prompts the user to add a username for the SpeakerController
     */
    public String printAddUsernamePrompt(){
        return "Please enter a username for the speaker:";
    }

    /**
     * Prompts the user to add an email for the SpeakerController
     */
    public String printAddEmailPrompt(){
        return "What is the speaker's e-mail address?";
    }

    // OPTION 4

    /**
     * Prompts the user to make an announcement about an Event
     */
    public String printEventMessageIntro(){
        return "To make an announcement about an event, please fill in the following information:";
    }

    /**
     * Tell the User the message is sent.
     */
    public String printMessageSent() {
        return"Announcement successfully sent!";
    }

    /**
     * Prompts the user to enter the content of the announcement
     * @return
     */
    public String printMessageContentPrompt(){
        return "Please enter your announcement below";
    }



}
