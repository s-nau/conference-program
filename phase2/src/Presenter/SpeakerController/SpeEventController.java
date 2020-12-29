package Presenter.SpeakerController;

// Programmer: Karyn Komatsu,
// Description: Event Menu for SpeakerController users.
// Date Created: 01/11/2020
// Date Modified: 19/11/2020

import Presenter.Exceptions.InvalidChoiceException;
import Presenter.Exceptions.NoDataException;
import Presenter.Central.SubMenu;
import Person.SpeakerManager;

import java.util.InputMismatchException;
import java.util.ArrayList;

public class SpeEventController extends SubMenu {

    private final String currentUserID;
    private final SpeEventMenu presenter;
    private final SpeakerManager speakerManager;

    public SpeEventController(SubMenu subMenu, SpeakerManager speakerManager, String currentUserID) {
        super(subMenu);
        this.currentUserID = currentUserID;
        this.speakerManager = speakerManager;
        presenter = new SpeEventMenu(roomManager, eventManager, speakerManager, currentUserID);
    }

    /**
     * Gets all the Events a speaker is speaking at and formats them for display
     * @return An array of formatted events
     */
    public String[] getFormattedEvents() throws InvalidChoiceException{
        String[] events = {};
        if (speakerManager.getSpeakerInEvents(currentUserID) != null) {
            events = speakerManager.getSpeakerInEvents(currentUserID).toArray(events);
            return presenter.getEventList(events);
        } else {
            throw new NoDataException("such event");
        }
    }

    /**
     * Gets all the Events a speaker is speaking at
     * @return An array of eventIDs
     */
    public String[] getEvents() throws InvalidChoiceException{
        String[] events = {};
        if (speakerManager.getSpeakerInEvents(currentUserID) != null) {
            events = speakerManager.getSpeakerInEvents(currentUserID).toArray(events);
            return events;
        } else {
            throw new NoDataException("such event");
        }
    }

    /**
     * Gets all the Panels a speaker is speaking at
     * @return An array of Event IDs
     */
    public String[] getPanels() throws InvalidChoiceException{
        String[] events = {};
        if (speakerManager.getSpeakerInPanels(currentUserID) != null) {
            events = speakerManager.getSpeakerInPanels(currentUserID).toArray(events);
            return events;
        } else {
            throw new NoDataException("such event");
        }
    }

    /**
     * Gets all the Non-Panel Events a speaker is speaking at
     * @return An array of Event IDs
     */
    public String[] getNonPanels() throws InvalidChoiceException{
        String[] events = {};
        if (speakerManager.getSpeakerInNonPanels(currentUserID) != null) {
            events = speakerManager.getSpeakerInNonPanels(currentUserID).toArray(events);
            return events;
        } else {
            throw new NoDataException("such event");
        }
    }

    /**
     * Sends a Message to every user signed up for an Event
     * @param eventName The name of the Event
     * @param messageContent Content of the Message to be sent
     */
    public boolean eventMessage(String eventName, String chatName, String messageContent) throws InvalidChoiceException{
        String eID = eventManager.getEventID(eventName);
        if (eID != null) {
            String messageID = messageManager.createMessage(eID, chatName, presenter.addSpeUsername(messageContent));
            String acID = eventManager.getEventChat(eID);
            chatManager.addMessageIds(acID, messageID);
            return true;
        }
        throw new InvalidChoiceException("event");
    }

    /**
     * This is to send a message to all attendees of all of SpeakerController's events. For example, if SpeakerController wanted to
     * announce, "download so and so application for the our talk today" to all talks, this method proves useful. This
     * uses eventMessageForAttendees from above
     * @param events ArrayList of Event Names hosted by the speaker
     * @param messageContent String representing content of the message
     */
    public void multipleEventsAnnouncement (String[] events, String chatName, String messageContent) throws
        InvalidChoiceException, InputMismatchException {
        for (String event : events) {
            if (!eventMessage(event, chatName, messageContent)) {
                throw new NoDataException("event");
            }
        }
    }

    @Override
    public SpeEventMenu getPresenter() {
        return this.presenter;
    }
}
