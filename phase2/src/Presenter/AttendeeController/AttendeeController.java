package Presenter.AttendeeController;

// Programmer: Cara McNeil
// Description: Main account page for AttendeeController users.
// Date Created: 01/11/2020
// Date Modified: 16/11/2020

import Presenter.PersonController.ContactController;
import Presenter.PersonController.PersonController;
import Presenter.Central.SubMenu;
import Event.EventManager;
import Event.RoomManager;
import Message.ChatManager;
import Message.MessageManager;
import Person.AttendeeManager;
import Presenter.PersonController.UserInfoController;
import Request.RequestManager;

public class AttendeeController extends PersonController {
    private final AttendeeManager manager;

    public AttendeeController(AttendeeManager manager, RoomManager rooms, EventManager events, MessageManager messages,
                              ChatManager chats, RequestManager requests) {
        super(manager, rooms, events, messages, chats, requests, 1);
        this.manager = manager;
    }

    public AttendeeController(AttendeeManager manager, SubMenu subMenu) {
        super(subMenu, 1);
        this.manager = manager;
    }

    /**
     * Creates the next controller according to the user's menu choice
     */
    public SubMenu createController(String choice) {
        String[] options = getMenuOptions();
        if (super.loggedIn) {
            if (choice.equals(options[0])) {
                return new UserInfoController(this, currentUserID);
            }
            else if (choice.equals(options[1])) {
                return new ContactController(this, currentUserID);
            }
            else if (choice.equals(options[2])) {
                return new AttMessageController(this, currentUserID, manager);
            }
            else if (choice.equals(options[3])) {
                return new AttEventController(this, currentUserID, manager);
            }
            else if (choice.equals(options[4])){
                return new AttReqController(this, currentUserID);
            }
        }
        return null;
    }

    @Override
    public String[] getMenuOptions() {
        String[] attendeeOptions  = {"View your event information", "View your requests"};
        String[] options = new String[5];
        System.arraycopy(super.getMenuOptions(), 0, options, 0, 3);
        System.arraycopy(attendeeOptions, 0, options, 3, 2);
        return options;
    }
}
