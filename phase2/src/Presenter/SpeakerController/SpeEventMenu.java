package Presenter.SpeakerController;

import Event.EventManager;
import Event.RoomManager;
import Person.SpeakerManager;
import Presenter.Exceptions.InvalidChoiceException;
import Presenter.Exceptions.NoDataException;
import Presenter.PersonController.EventMenu;

// Programmers:
// Description:
// Date Created:
// Date Modified: 02/12/2020

public class SpeEventMenu extends EventMenu {
    String currentUserID;

    public SpeEventMenu(RoomManager rooms, EventManager events, SpeakerManager speakers, String currentUserID) {
        super(rooms, events, speakers);
        this.currentUserID = currentUserID;
    }

    @Override
    public String getMenuTitle() {
        return "----- Speaker Event Menu -----";
    }

    @Override
    public String[] getMenuOptions() {
        return new String[]{"View convention events list", "View your events", "Make an announcement"};
    }

    public String[] getEventOptions() {
        return new String[]{"All Events", "Panel Events", "Non-Panel Events", "Specific Event"};
    }

    /**
     * Prompts user to enter eventName of the event want to send message to.
     */
    public String printEventTypePrompt() {
        return "What would you like to make an announcement for?";
    }

    /**
     * Prompts user to enter eventName of the event want to send message to.
     */
    public String printChatNamePrompt() {
        return "What is the name of the announcement?";
    }

    /**
     * Prompts user to enter an ArrayList of eventName(s) of the one or more events
     * that they want to send the message to.
     */
    public String printEventNamePrompt(){
        return "Enter the name of Event that you want to send an announcement to.";
    }

    /**
     * Prompts user to enter CONTENT of the message.
     */
    public String printContentPrompt() {
        return "Enter the content of the announcement.";
    }

    /**
     * Tell the User the message is sent.
     */
    public String printMessageSent() {
        return"Announcement successfully sent!";
    }

    /**
     *
     * @param content Content of the message
     * @return Content following with the sentence: ["Contact me using this username:"]\newline
     *                                              [username of the SpeakerController]
     */
    protected String addSpeUsername(String content){
        return content + "\n" + "Contact me using this username:\n"
                + persons.getCurrentUsername(currentUserID);
    }


    /**
     * Get a title for the list of talks the user is scheduled to speak at
     * @return The title
     */
    public String getOwnEventsTitle() {
        return getEventListTitle("you speak at");
    }


    /**
     * Get the list of talks the user is scheduled to speak at
     * @return String chunk of formatted Talks
     */
    protected String[] getOwnEventsList() throws InvalidChoiceException {
        try {
            String[] events = {};
            events =  persons.getEventList(currentUserID).toArray(events);
            return printEventList(events);
        } catch (NullPointerException e) {
            throw new NoDataException("event");
        }
    }


}




