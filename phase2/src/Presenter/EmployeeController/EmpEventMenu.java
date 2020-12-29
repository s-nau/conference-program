package Presenter.EmployeeController;

import Event.EventManager;
import Event.RoomManager;
import Person.PersonManager;
import Presenter.PersonController.EventMenu;

// Programmers:
// Description:
// Date Created:
// Date Modified: 02/12/2020
//

public class EmpEventMenu extends EventMenu {
    String currentUserID;


    public EmpEventMenu(RoomManager rooms, EventManager events, PersonManager persons) {
        super(rooms, events, persons);
        //this.currentUserID = currentUserID;
    }

    @Override
    public String getMenuTitle() {
        return "----- Employee Event Menu -----";
    }

    @Override
    public String[] getMenuOptions() {
        String[] options = {"View convention events list", "View list of rooms",};
        return options;
    }


    /**
     * Tell the User the message is sent.
     */
    public String printMessageSent() {
        return"Message successfully sent!";
    }

    /**
     *
     * @param content Content of the message
     * @return Content following with the sentence: ["Contact me using this username:"]\newline
     *                                              [username of the SpeakerController]
     */
    protected String addSpeUsername (String content){
        return content + "\n" + "Contact me using this username:\n"
                + persons.getCurrentUsername(currentUserID);
    }


}
