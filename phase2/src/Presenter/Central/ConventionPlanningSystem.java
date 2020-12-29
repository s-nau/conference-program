package Presenter.Central;
// Programmer: Cara McNeil, Sarah Kronenfeld
// Description: The central Controller of the Convention System. Calls all other Controllers.
// Date Created: 01/11/2020
// Date Modified: 18/11/2020

import Person.AttendeeManager;
import Person.EmployeeManager;
import Person.OrganizerManager;
import Person.SpeakerManager;
import Presenter.AttendeeController.AttendeeController;
import Presenter.EmployeeController.EmployeeController;
import Presenter.OrganizerController.OrganizerController;
import Presenter.PersonController.PersonController;
import Presenter.SpeakerController.SpeakerController;

public class ConventionPlanningSystem {
    ConventionSaver c;

    // DONE add requestManagers into controller instantiations
    // Further, Administrator has been taken out of the program

    public ConventionPlanningSystem() {
        c = new ConventionSaver();
    }

    public ConventionPlanningSystem(SubMenu subMenu) {
        c = subMenu.returnData();
    }

    public String getIntroTitle() {
        return "CONVENTION SYSTEM LOGIN";
    }

    public String getIntroMessage() {
        return "Welcome to convention system!";
    }

    public String getChooseAccountTitle() {
        return "User Account Selection";
    }

    public String getChooseAccountText() {
        return "Which type of account would you like to log into/create?";
    }

    public String[] getAccountOptions() {
        return new String[]{"Attendee", "Organizer", "Speaker", "Employee"};
    }

    public String getSaveMessage() {
        return "Your changes have been saved. Exit the program or click 'okay' to go back to " +
                "welcome screen.";
    }

    /**
     * Set the user's controller based on login selection.
     */
    public PersonController getController(String choice) {
        PersonController PC;
        String[] options = getAccountOptions();
        if (choice.equals(options[0])) {
            AttendeeManager am = new AttendeeManager(c.personByName, c.personByID);
            PC = new AttendeeController(am, c.rm, c.em, c.mm, c.cm, c.rqm);
        } else if (choice.equals(options[1])) {
            OrganizerManager om = new OrganizerManager(c.personByName, c.personByID);
            SpeakerManager sm = new SpeakerManager(c.personByName, c.personByID);
            AttendeeManager am = new AttendeeManager(c.personByName, c.personByID);
            EmployeeManager em = new EmployeeManager(c.personByName, c.personByID);
            PC = new OrganizerController(om, sm, c.rm, c.em, c.mm, c.cm, am, em, c.rqm);
        } else if (choice.equals(options[2])) {
            SpeakerManager sman = new SpeakerManager(c.personByName, c.personByID);
            PC = new SpeakerController(sman, c.rm, c.em, c.mm, c.cm, c.rqm);
        } else if (choice.equals(options[3])){
            EmployeeManager eman = new EmployeeManager(c.personByName, c.personByID);
            PC = new EmployeeController(eman, c.rm, c.em, c.mm, c.cm, c.rqm);
         } else {
            return null;
        }
        return PC;
    }

    public void save() {
        c.save();
    }


}
