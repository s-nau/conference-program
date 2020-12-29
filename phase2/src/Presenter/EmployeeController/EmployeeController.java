package Presenter.EmployeeController;

 //Description: Main account page for EmployeeController users.

import Event.EventManager;
import Event.RoomManager;
import Message.ChatManager;
import Message.MessageManager;
import Person.EmployeeManager;
import Presenter.Central.SubMenu;
import Presenter.PersonController.ContactController;
import Presenter.PersonController.MessageController;
import Presenter.PersonController.PersonController;
import Presenter.PersonController.UserInfoController;
import Request.RequestManager;

import java.util.ArrayList;

/*
// RETURN controllers; no void methods

// create request
// work with constraints
*/

public class EmployeeController extends PersonController {

    private EmployeeManager manager;

    public EmployeeController(EmployeeManager manager, RoomManager rooms, EventManager events,
                              MessageManager messages, ChatManager chats, RequestManager requests) {
        super(manager, rooms, events, messages, chats, requests, 4);
        this.manager = manager;
    }

    @Override
    public SubMenu createController(String choice){
        String[] options = getMenuOptions();
        if (super.loggedIn) {
            if (choice.equals(options[0])) {
                return new UserInfoController(this, currentUserID);
            }
            else if (choice.equals(options[1])) {
                return new ContactController(this, currentUserID);
            }
            else if (choice.equals(options[2])) {
                return new MessageController(this, currentUserID);
            }
            else if (choice.equals(options[3])){
                return new EmpEventController(this, currentUserID, manager);
            }
            else if (choice.equals(options[4])){
                return new EmpReqController(this, currentUserID);


            }
        }
        return null;
    }

    @Override
    public String[] getMenuOptions() {
        String[] employeeOptions  = {"View event information", "View the request board"};
        String[] options = new String[5];
        System.arraycopy(super.getMenuOptions(), 0, options, 0, 3);
        System.arraycopy(employeeOptions, 0, options, 3, 2);
        return options;
    }

}

