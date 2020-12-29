// get schedule of all talks
// get request board

// contact organizers .. set up that check in the EmployeeManager
// contact employees  ... set up that check in the EmployeeManager -- check with message people

//  do a test exceptions .. try sending to speaker and/or attendee -- and get back message
// set up a request board of tally for each room - whether it is an event or not.
// which organizers and employees and administrator can see.

package Presenter.EmployeeController;

import Event.EventManager;
import Event.EventPermissions;
import Event.RoomManager;
import Person.AttendeeManager;
import Person.EmployeeManager;
import Person.PersonManager;
import Presenter.Central.SubMenu;

public class EmpEventController extends SubMenu {

    private String currentUserID;
    private AttendeeManager attendeeManager;
    private EventPermissions eventPermissions;
    private EventManager eventManager;
    private PersonManager personManager;
    private EmployeeManager employeeManager;
    private RoomManager roomManager;


    private final EmpEventMenu presenter;

    public EmpEventController(SubMenu subMenu, String currentUserID, EmployeeManager employeeManager) {
        super(subMenu);
        this.currentUserID = currentUserID;
        this.employeeManager = employeeManager;
        eventPermissions = new EventPermissions(roomManager, eventManager);
        presenter = new EmpEventMenu(roomManager, eventManager, personManager);
        //currentUserID, roomManager, eventManager, attendeeManager

    }

    @Override
    public EmpEventMenu getPresenter() {
        return this.presenter;
    }

}





