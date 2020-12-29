package View.Account;

import Presenter.Central.SubMenu;
import Presenter.EmployeeController.EmpEventController;
import Presenter.EmployeeController.EmpEventMenu;
import Presenter.Exceptions.InvalidChoiceException;
import Presenter.Exceptions.NoDataException;
import View.AccountHelpers.ListDisplayView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EmpEventView extends AccountView {
    EmpEventController controller;
    EmpEventMenu presenter;
    String[] menuOp;
    ListDisplayView eventList;
    JLabel dp1;
    JTextField input1;
    JButton viewRoomEventsB, viewRoomsB;

    /**
     * The view for employee users to see their convention event options.
     * @param controller EmpEventController for handling user input
     */
    public EmpEventView(SubMenu controller) {
        super(controller);
        this.controller = (EmpEventController) controller;
        this.presenter = (EmpEventMenu) controller.getPresenter();

        contentPane.setBackground(new Color(255, 140, 190));
        //contentPane.setBackground(new Color(130, 255, 140));

        menuOp = this.presenter.getMenuOptions();

        dp1 = new JLabel("");
        initializeObject(dp1);
        input1 = new JTextField(20);
        initializeObject(input1);

        viewRoomEventsB = newButton("View events in this room!");
    }

    private void showViewEvents() {
        dp1.setText("Enter the name of the room whose events you would like to see");
        dp1.setVisible(true);
        input1.setVisible(true);

        okayButton.setVisible(true);
    }

    private void viewRoomEvents() {
        String roomInput = dp1.getText();

        try {
            eventList = new ListDisplayView(presenter.getEventListTitle(), presenter.printEventsInRoom(roomInput));
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        } finally {
            showMainDropDownMenu();
        }
        //okayButton.setVisible(true);
    }

    private void viewRooms() {
        try {
            eventList = new ListDisplayView("ALL ROOMS", presenter.getRoomList());
        } catch(NoDataException d) {
            exceptionDialogBox(presenter.printException(d));
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        super.actionPerformed(event);

        // [0] = view convention events list
        // [1] = view list of rooms
        if(eventName.equals(menuOp[0])) {
            hideMainDropDownMenu();
            showViewEvents();
        }

        if(eventName.equals(viewRoomEventsB.getActionCommand())) {
            viewRoomEvents();
        }

        if(eventName.equals(menuOp[1])) {
            hideMainDropDownMenu();
            viewRooms();
        }
    }
}


