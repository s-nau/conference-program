package Presenter.OrganizerController;

import Event.CapacityException;
import Event.EventPermissions;
import Event.NotPanelException;
import Event.Panel;
import Person.AttendeeManager;
import Person.EmployeeManager;
import Person.OrganizerManager;
import Person.SpeakerManager;
import Presenter.Central.SubMenu;
import Presenter.Central.SubMenuPrinter;
import Presenter.Exceptions.InvalidChoiceException;
import Presenter.Exceptions.OverwritingException;
import Request.RequestEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

// Contributors: Eytan Weinstein, Paul-John Thomas
// Last edit: Dec 11 2020

// Architecture Level - Controller

public class OrgPersonController extends SubMenu {

    // OrgPersonController implements methods that add/remove user accounts.

    private String currentUserID;
    private AttendeeManager attendeeManager;
    private EmployeeManager employeeManager;
    private OrganizerManager organizerManager;
    private SpeakerManager speakerManager;
    private EventPermissions eventPermissions;
    private OrgEventController orgEventController;
    private OrgPersonMenu presenter;

    /**
     * Constructor for OrgPersonController objects
     * @param subMenu         The submenu which implements options
     * @param currentUserID   The ID for the current user of this controller
     * @param attendeeManager The AttendeeManager which manages the Attendees at this convention
     * @param employeeManager The EmployeeManager which manages the Employees at this convention
     * @param speakerManager  The SpeakerManager which manages the Speakers at this convention
     */
    public OrgPersonController(SubMenu subMenu, String currentUserID, AttendeeManager attendeeManager, EmployeeManager
            employeeManager, SpeakerManager speakerManager) {
        super(subMenu);
        this.currentUserID = currentUserID;
        this.attendeeManager = attendeeManager;
        this.employeeManager = employeeManager;
        this.organizerManager = (OrganizerManager) personManager;
        this.speakerManager = speakerManager;
        this.eventPermissions = new EventPermissions(roomManager, eventManager);
        this.orgEventController = new OrgEventController(subMenu, currentUserID, speakerManager, employeeManager);
        this.presenter = new OrgPersonMenu(roomManager, eventManager, personManager);
    }

    // Methods for creating user accounts

    /**
     * Creates a new Attendee account and adds it to the system
     * @param name     The name of the Attendee
     * @param username The username of the Attendee
     * @param password The password of the Attendee
     * @param email    The email of the Attendee
     */
    public void createAttendee(String name, String username, String password, String email) throws
            OverwritingException {
        if (!attendeeManager.findPerson(username)) {
            attendeeManager.createAccount(name, username, password, email);
        } else {
            throw new OverwritingException("account");
        }
    }

    /**
     * Creates a new Employee account and adds it to the system
     * @param name     The name of the Employee
     * @param username The username of the Employee
     * @param password The password of the Employee
     * @param email    The email of the Employee
     */
    public void createEmployee(String name, String username, String password, String email) throws
            OverwritingException {
        if (!employeeManager.findPerson(username)) {
            employeeManager.createAccount(name, username, password, email);
        } else {
            throw new OverwritingException("account");
        }
    }

    /**
     * Creates a new Organizer account and adds it to the system
     * @param name     The name of the Organizer
     * @param username The username of the Organizer
     * @param password The password of the Organizer
     * @param email    The email of the Organizer
     */
    public void createOrganizer(String name, String username, String password, String email) throws
            OverwritingException {
        if (!organizerManager.findPerson(username)) {
            organizerManager.createAccount(name, username, password, email);
        } else {
            throw new OverwritingException("account");
        }
    }

    /**
     * Creates a new Speaker account and adds it to the system
     * @param name     The name of the Speaker
     * @param username The username of the Speaker
     * @param password The password of the Speaker
     * @param email    The email of the Speaker
     */
    public void createSpeaker(String name, String username, String password, String email) throws
            OverwritingException {
        if (!speakerManager.findPerson(username)) {
            speakerManager.createAccount(name, username, password, email);
        } else {
            throw new OverwritingException("account");
        }
    }

    // Methods for deleting user accounts

    /**
     * Deletes an Attendee account from the system; also deletes this Attendee from all Event Attendee lists, all chats,
     * and all other users' contact lists. In addition, sends a Message to all their contacts to let them know that they
     * can no longer contact this user.
     * @param username The username of the Attendee whose account is to be deleted
     */
    public void cancelAttendeeAccount(String username) throws OverwritingException {
        if (personManager.findPerson(username)) {
            String userID = personManager.getCurrentUserID(username);
            this.deleteUserFromEvent(userID);
            this.deleteUserFromChatGroups(userID);
            this.removeFromOtherUsersContactLists(userID);
            this.deleteRequests(userID);
            attendeeManager.cancelAccount(userID);
        } else {
            throw new OverwritingException("while deleting account");
        }
    }

    /**
     * Deletes an Employee account from the system; also deletes this Employee from all chats, and all other users'
     * contact lists. In addition, sends a Message to all their contacts to let them know that they can no longer
     * contact this user.
     * @param username The username of the Attendee whose account is to be deleted
     */
    public void cancelEmployeeAccount(String username) throws OverwritingException {
        if (personManager.findPerson(username)) {
            String userID = personManager.getCurrentUserID(username);
            this.deleteUserFromChatGroups(userID);
            this.removeFromOtherUsersContactLists(userID);
            this.deleteRequests(userID);
            employeeManager.cancelEmployeeAccount(userID);
        } else {
            throw new OverwritingException("while deleting account");
        }
    }

    /**
     * Deletes an Organizer account from the system; also deletes this Organizer from all Event Attendee lists, all
     * chats, and all other users' contact lists. In addition, sends a Message to all their contacts to let them know
     * that they can no longer contact this user.
     * @param username The username of the Organizer whose account is to be deleted
     */

    // replaced InvalidChoiceException  with OverWritingException : the bug in OrgPersonView is gone
    public void cancelOrganizerAccount(String username) throws OverwritingException {
        if(!personManager.findPerson(username)){
            String userID = personManager.getCurrentUserID(username);
            this.deleteUserFromEvent(userID);
            this.deleteUserFromChatGroups(userID);
            this.removeFromOtherUsersContactLists(userID);
            this.deleteRequests(userID);
            organizerManager.cancelAccount(userID);}
        else {
            throw new OverwritingException("while deleting account");
        }
    }

    /**
     * Deletes a Speaker account from the system; also deletes this Speaker from all Event Speaker lists, all
     * chats, and all other users' contact lists. In addition, sends a Message to all their contacts to let them know
     * that they can no longer contact this user, and deletes those non-Panels at which this Speaker is
     * scheduled to speak (though not those currently in progress.
     * @param username The username of the Speaker whose account is to be deleted
     */
    public void cancelSpeakerAccount(String username) throws InvalidChoiceException, NotPanelException,
            CapacityException {
        if(personManager.findPerson(username)) {
            String speakerID = personManager.getCurrentUserID(username);
            this.deleteUserFromChatGroups(speakerID);
            this.removeFromOtherUsersContactLists(speakerID);
            this.deleteRequests(speakerID);
            ArrayList<String> panels = speakerManager.getSpeakerInPanels(speakerID);
            ArrayList<String> nonPanels = speakerManager.getSpeakerInNonPanels(speakerID);
            if (!panels.isEmpty()) {
                for (String eventId : panels) {
                    removeSpeakerFromPanelEvent(speakerID, eventId);
                }
            }
            if (!nonPanels.isEmpty()) {
                for (String eventId : nonPanels) {
                    removeSpeakerFromNonPanelEvent(speakerID, eventId);
                }
            }
            speakerManager.getSpeakerInNonPanels(speakerID).clear();
            speakerManager.getSpeakerInPanels(speakerID).clear();
            speakerManager.getContactList(speakerID).clear();
            speakerManager.getChats(speakerID).clear();
            informOrganizersSpeakerDeletion(this.currentUserID, speakerID);
        }
        else {
            throw new OverwritingException("while deleting account");
        }
    }

    // Helper methods for deleting user accounts

    /**
     * This is a helper method for the account deletion methods above; removes the user from all Events for which
     * he/she is signed up.
     * @param userID The ID of the user whose account is to be deleted
     */
    public void deleteUserFromEvent(String userID) {
        ArrayList<String> eventList = personManager.getEventList(userID);
        for (String e : eventList) {
            eventPermissions.removeAttendeeFromEvent(userID, e);
        }
    }

    /**
     * This is a helper method for the account deletion methods above; deletes the user from all his/her chat groups.
     * @param userID The ID of the user whose account is to be deleted
     */
    public void deleteUserFromChatGroups(String userID) {
        ArrayList<String> userChatList = personManager.getChats(userID);
        if (!userChatList.isEmpty()) {
            for (String c : userChatList) {
                ArrayList<String> personList = chatManager.getPersonIds(c);
                for (String p : personList) {
                    this.sendMessageAboutChatDeletion(userID, p, c);
                }
                chatManager.removePersonIds(c, userID);
            }
        }
    }

    /** This is a helper method that sends a message to all contacts in a user's chat group that they can no longer
     * send/receive messages to/from that user.
     * @param userID The ID of the user
     * @param recipientID The ID of the recipient
     * @param chatID The ID of the chat
     */
    public void sendMessageAboutChatDeletion(String userID, String recipientID, String chatID) {
        String userName = personManager.getCurrentUsername(userID);
        String messageContent = "The user with username: " + userName + "is now deleted from your chat group. You " +
                "cannot send messages to or receive messages from this person.";
        messageManager.createMessage(userID, recipientID, chatID, messageContent);
    }

    /** This is a helper method that removes this user from all other users' contact lists after their account has been
     * deleted.
     * @param userID The ID of the user
     */
    public void removeFromOtherUsersContactLists(String userID) {
        ArrayList<String> userContactList = personManager.getContactList(userID);
        for (String contactID : userContactList) {
            if (personManager.getContactList(contactID).contains(userID)) {
                personManager.getContactList(contactID).remove(userID);
            }
        }
    }

    /** This is a helper method that empties requests for a user whose account is to be deleted.
     * @param userID The ID of the user
     */
    public void deleteRequests(String userID) {
        ArrayList requestIDs = requestManager.getRequestsByUser(userID);
        ArrayList requestEntities = new ArrayList<>();
        for (Object requestID : requestIDs) {
            String request = (String) requestID;
            RequestEntity entity = requestManager.getRequestEntity(request);
            requestEntities.add(entity);
        }
        for (Object request: requestEntities) {
            RequestEntity object = (RequestEntity) request;
            requestManager.removeRequest(object);
        }
    }

    /** This is a helper method that informs all Organizers that a Speaker account has been deleted.
     * @param organizerID The ID of the Organizer
     * @param speakerID The ID of the Speaker
     */
    public void informOrganizersSpeakerDeletion(String organizerID, String speakerID) {
        String speakerName = personManager.getCurrentUsername(speakerID);
        String messageContentToOrganizers = "Account of speaker with username: " + speakerName + " and userID: " +
                speakerID + " has been deleted";
        ArrayList<String> fellowOrganizers = organizerManager.getOrganizerOnlyMapByID(organizerID);
        if (!fellowOrganizers.isEmpty()) {
            for (String fellowOrg : fellowOrganizers) {
                if (!fellowOrg.equals(organizerID) && chatManager.existChat(organizerID, fellowOrg)) {
                    String existingChatID = chatManager.findChat(organizerID, fellowOrg);
                    messageManager.createMessage(organizerID, fellowOrg, existingChatID, messageContentToOrganizers);
                } else {
                    String newChatID = chatManager.createChat(organizerID, fellowOrg,
                            "Speaker Deletion Notification");
                    personManager.addChat(organizerID, newChatID);
                    messageManager.createMessage(organizerID, fellowOrg, newChatID, messageContentToOrganizers);
                }
            }
        }
    }

    /** This is a helper method that removes a Speaker from all Events that are not Panels, and cancels those non-Panels
     * (though not those currently in progress).
     * @param speakerID The ID of the Speaker
     * @param eventID The ID of the Event
     */
    public void removeSpeakerFromNonPanelEvent(String speakerID, String eventID) {
        LocalDateTime now = LocalDateTime.now();
        int dayHour = now.getHour();
        int dayMinute = now.getMinute();
        LocalDateTime startTime = getStartTime(eventID);
        int eventHour = startTime.getHour();
        int eventMinute = startTime.getMinute();
        if (eventHour < dayHour && eventMinute < dayMinute) {
            eventManager.removeEvent(eventID);
            String name = eventManager.getEventName(eventID);
            this.orgEventController.cancelEvent(name);
        }
    }

    /** This is a helper method that removes a Speaker from all Panels, and cancels those Panels which have no Speakers
     * as a result (though not those currently in progress).
     * @param speakerID The ID of the Speaker
     * @param eventID The ID of the Event
     */
    public boolean removeSpeakerFromPanelEvent(String speakerID, String eventID) throws NotPanelException,
            InvalidChoiceException, CapacityException {
        Boolean isPanel = eventManager.getEventType(eventID).equals("PANEL");
        Panel panel = (Panel) eventManager.getEvent(eventID);
        Boolean isEmpty = panel.isPanelEmpty(eventID);
        Boolean isSpeaker = panel.isSpeakerInPanel(speakerID);
        LocalDateTime now = LocalDateTime.now();
        int dayHour = now.getHour();
        int dayMinute = now.getMinute();
        LocalDateTime startTime = getStartTime(eventID);
        int eventHour = startTime.getHour();
        int eventMinute = startTime.getMinute();
        if ((eventHour < dayHour) && (eventMinute < dayMinute) && isPanel && !isEmpty && isSpeaker) {
            if (panel.numberPanelists(eventID) == 1) {
                String name = eventManager.getEventName(eventID);
                orgEventController.removeSpeakerFromPanel(speakerID, name);
                orgEventController.cancelEvent(eventID);
            } else {
                String name = eventManager.getEventName(eventID);
                orgEventController.removeSpeakerFromPanel(speakerID, name);
            }
            return true;

        }
        return false;
    }

        /**
         * Chooses a valid start time for the new Event
         * @param time The start time as a String
         * @return The start time as a LocalDateTime object
         */
        private LocalDateTime getStartTime (String time) throws DateTimeParseException {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(time, formatter);
        }

        @Override
        public SubMenuPrinter getPresenter () {
            return presenter;
        }
    }




