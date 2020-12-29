package Presenter.OrganizerController;

import Event.EventManager;
import Event.RoomManager;
import Person.PersonManager;
import Presenter.Exceptions.NoDataException;
import Presenter.PersonController.EventMenu;

public class OrgPersonMenu extends EventMenu{
    public OrgPersonMenu(RoomManager rooms, EventManager events, PersonManager persons) {
        super(rooms, events, persons);
    }


    @Override
    public String getMenuTitle() {
        return "----- Organizer Event Menu -----";
    }

    @Override
    public String[] getMenuOptions() {
        return new String[]{"Create a speaker account", "Create an attendee account", "Create an employee account",
                "Create an organizer account", "Cancel a speaker account", "Cancel an attendee account", "Cancel an employee account",
                "Cancel an organizer account"};
    }

    // OPTION 1


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
     * Prompts the user to choose a type of User to delete or add
     */
    public String printUserTypePrompt(){
        return "\nWhich type of user do you want to add or delete (Enter 0 for options)";
    }


    /**
     * Notifies the user that a Talk was not created because another is already scheduled
     */
    public String printActionForSpeakerPrompt(){
        return "\nDo you want to add speaker (Enter 1) or delete speaker (Enter 2).";
    }

    // OPTION 3

    /**
     * Prompts the user to add a SpeakerController account
     */
    public String printAddSpeakerPrompt(){
        return "\nTo create a new speaker account, please fill in the following information:";
    }

    /**
     * Prompts the user to enter a name for the SpeakerController
     */
    public String printAddSpeakerNamePrompt(){
        return "\nWhat is the speaker's full name?";
    }

    /**
     * Prompts the user to add a password for the SpeakerController
     */
    public String printAddSpeakerPasswordPrompt(){
        return "\nPlease enter a password for the speaker:";
    }

    /**
     * Prompts the user to add a username for the SpeakerController
     */
    public String printAddSpeakerUsernamePrompt(){
        return "\nPlease enter a username for the speaker:";
    }

    /**
     * Prompts the user to add an email for the SpeakerController
     */
    public String printAddSpeakerEmailPrompt(){
        return "\nWhat is the speaker's e-mail address?";
    }

    // OPTION 4

    // OPTION 3

    /**
     * Prompts the user to add a SpeakerController account
     */

    /**
     * Notifies the user that a Talk was not created because another is already scheduled
     */
    public String printActionForEmployeePrompt(){
        return "\nDo you want to add employee (Enter 1) or delete employee (Enter 2).";
    }

    public String printAddEmployeePrompt(){
        return "\nTo create a new employee account, please fill in the following information:";
    }

    /**
     * Prompts the user to enter a name for the SpeakerController
     */
    public String printAddEmployeeNamePrompt(){
        return "\nWhat is the employee's full name?";
    }

    /**
     * Prompts the user to add a password for the SpeakerController
     */
    public String printAddEmployeePasswordPrompt(){
        return "\nPlease enter a password for the employee:";
    }

    /**
     * Prompts the user to add a username for the SpeakerController
     */
    public String printAddEmployeeUsernamePrompt(){
        return "\nPlease enter a username for the employee:";
    }

    /**
     * Prompts the user to add an email for the SpeakerController
     */
    public String printAddEmployeeEmailPrompt(){
        return "\nWhat is the employee's e-mail address?";
    }




    /**
     * Notifies the user that a Talk was not created because another is already scheduled
     */
    public String printActionForAttendeePrompt(){
        return "\nDo you want to add attendee (Enter 1) or delete attendee (Enter 2).";
    }

    // OPTION 3

    /**
     * Prompts the user to add a SpeakerController account
     */
    public String printAddAttendeePrompt(){
        return "\nTo create a new attendee account, please fill in the following information:";
    }

    /**
     * Prompts the user to enter a name for the SpeakerController
     */
    public String printAddAttendeeNamePrompt(){
        return "\nWhat is the attendee's full name?";
    }

    /**
     * Prompts the user to add a password for the SpeakerController
     */
    public String printAddAttendeePasswordPrompt(){
        return "\nPlease enter a password for the attendee:";
    }

    /**
     * Prompts the user to add a username for the SpeakerController
     */
    public String printAddAttendeeUsernamePrompt(){
        return "\nPlease enter a username for the attendee:";
    }

    /**
     * Prompts the user to add an email for the SpeakerController
     */
    public String printAddAttendeeEmailPrompt(){
        return "\nWhat is the attendee's e-mail address?";
    }

    /**
     * Notifies the user that a Talk was not created because another is already scheduled
     */
    public String printActionForOrganizerPrompt(){
        return "\nDo you want to add organizer (Enter 1) or delete organizer (Enter 2).";
    }

    public String printAddOrganizerPrompt(){
        return "\nTo create a new organizer account, please fill in the following information:";
    }

    /**
     * Prompts the user to enter a name for the SpeakerController
     */
    public String printAddOrganizerNamePrompt(){
        return "\nWhat is the organizer's full name?";
    }

    /**
     * Prompts the user to add a password for the SpeakerController
     */
    public String printAddOrganizerPasswordPrompt(){
        return "\nPlease enter a organizer for the employee:";
    }

    /**
     * Prompts the user to add a username for the SpeakerController
     */
    public String printAddOrganizerUsernamePrompt(){
        return "\nPlease enter a organizer for the employee:";
    }

    /**
     * Prompts the user to add an email for the SpeakerController
     */
    public String printAddOrganizerEmailPrompt(){
        return "\nWhat is the organizer's e-mail address?";
    }

    public String printDeleteSpeakerAccountPrompt(){
        return "\nTo delete a new speaker account, please fill in the following information:";
    }

    public String printSpeakerIDPrompt(){
        return "\nPlease enter the userID for the speaker:";
    }

    public String printDeleteEmployeeAccountPrompt(){
        return "\nTo delete a new employee account, please fill in the following information:";
    }

    public String printEmployeeIDPrompt(){
        return "\nPlease enter the userID for the employee:";
    }

    public String printDeleteAttendeeAccount(){
        return "\nTo delete a new attendee account, please fill in the following information:";
    }

    public String printAttendeeIDPrompt(){
        return "\nPlease enter the userID for the attendee:";
    }

    public String printAttendeeUserNamePrompt(){
        return "\nPlease enter the username for the attendee:";
    }

    public String printDeleteOrganizerAccount(){
        return "\nTo delete a new organizer account, please fill in the following information:";
    }

    public String printOrganizerIDPrompt(){
        return "\nPlease enter the userID for the organizer:";
    }


}
