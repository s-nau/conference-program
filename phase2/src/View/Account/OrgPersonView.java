package View.Account;

import Presenter.Central.SubMenu;
import Presenter.Exceptions.InvalidChoiceException;
import Presenter.Exceptions.OverwritingException;
import Presenter.OrganizerController.OrgPersonController;
import Presenter.OrganizerController.OrgPersonMenu;
import Event.CapacityException;
import Event.NotPanelException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class OrgPersonView extends AccountView {
    OrgPersonController controller;
    OrgPersonMenu presenter;
    JLabel dpMain, dp1, dp2, dp3, dp4;
    JButton submit;
    JTextField input1, input2, input3, input4;
    String[] menuOp;

    public OrgPersonView(SubMenu controller) {
        super(controller);
        this.controller = (OrgPersonController) controller;
        this.presenter = (OrgPersonMenu) controller.getPresenter();

        contentPane.setBackground(new Color(92, 195, 248));

        submit = new JButton("submit");
        initializeObject(submit);

        dp1 = new JLabel("");
        initializeObject(dp1);
        input1 = new JTextField(20);
        initializeObject(input1);

        dp2 = new JLabel("");
        initializeObject(dp2);
        input2 = new JTextField(20);
        initializeObject(input2);

        dp3 = new JLabel("");
        initializeObject(dp3);
        input3 = new JTextField(20);
        initializeObject(input3);

        dp4 = new JLabel("");
        initializeObject(dp4);
        input4 = new JTextField(20);
        initializeObject(input4);
    }

    private void clearInput() {
        input1.setText("");
        input1.setVisible(true);
        dp1.setVisible(true);
        input2.setText("");
        input2.setVisible(true);
        dp2.setVisible(true);
        input3.setText("");
        input3.setVisible(true);
        dp3.setVisible(true);
        input4.setText("");
        input4.setVisible(true);
        dp4.setVisible(true);
        submit.setVisible(true);
    }

    private void hideInput() {
        input1.setVisible(false);
        dp1.setVisible(false);
        input2.setVisible(false);
        dp2.setVisible(false);
        input3.setVisible(false);
        dp3.setVisible(false);
        input4.setVisible(false);
        dp4.setVisible(false);
    }

    private void clearOpenInputBox() {
        input1.setText("");
        input1.setVisible(true);
        dp1.setVisible(true);
    }


    private void showCreateSpe() {
        clearInput();
        dp1.setText(presenter.printAddSpeakerNamePrompt());
        dp2.setText(presenter.printAddSpeakerPasswordPrompt());
        dp3.setText(presenter.printAddSpeakerUsernamePrompt());
        dp4.setText(presenter.printAddSpeakerEmailPrompt());

        submit.setActionCommand("speakerCreate");
        submit.addActionListener(this);
    }

    private void createSpe() {
        try {
            controller.createSpeaker(input1.getText(), input3.getText(), input2.getText(), input4.getText());
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        } finally {
            showMainDropDownMenu();
        }
    }


    private void showCreateAtt() {
        clearInput();
        dp1.setText(presenter.printAddAttendeeNamePrompt());
        dp2.setText(presenter.printAddAttendeePasswordPrompt());
        dp3.setText(presenter.printAddAttendeeUsernamePrompt());
        dp4.setText(presenter.printAddAttendeeEmailPrompt());

        submit.setActionCommand("attendeeCreate");
        submit.addActionListener(this);
    }

    private void createAtt() {
        try {
            controller.createAttendee(input1.getText(), input3.getText(), input2.getText(), input4.getText());
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }finally {
            showMainDropDownMenu();
        }
    }


    private void showCreateEmp() {
        clearInput();
        dp1.setText(presenter.printAddEmployeeNamePrompt());
        dp2.setText(presenter.printAddEmployeePasswordPrompt());
        dp3.setText(presenter.printAddEmployeeUsernamePrompt());
        dp4.setText(presenter.printAddEmployeeEmailPrompt());

        submit.setActionCommand("employeeCreate");
        submit.addActionListener(this);
    }

    private void createEmp() {
        try {
            controller.createEmployee(input1.getText(), input3.getText(), input2.getText(), input4.getText());
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }finally {
            showMainDropDownMenu();
        }
    }


    private void showCreateOrg() {
        clearInput();
        dp1.setText(presenter.printAddOrganizerNamePrompt());
        dp2.setText(presenter.printAddOrganizerPasswordPrompt());
        dp3.setText(presenter.printAddOrganizerUsernamePrompt());
        dp4.setText(presenter.printAddOrganizerEmailPrompt());

        submit.setActionCommand("organizerCreate");
        submit.addActionListener(this);
    }

    private void createOrg() {
        try {
            controller.createOrganizer(input1.getText(), input3.getText(), input2.getText(), input4.getText());
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }finally {
            showMainDropDownMenu();
        }
    }


    private void showCancelSpe() {
        clearOpenInputBox();
        clearInput();
        dp1.setVisible(false);
        dp2.setVisible(false);
        dp4.setVisible(false);
        dp3.setText(presenter.printAddSpeakerUsernamePrompt());

        submit.setActionCommand("speakerCancel");
        submit.addActionListener(this);
    }

    private void cancelSpe() {
        try {
            controller.cancelSpeakerAccount(input3.getText());
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        } catch (NotPanelException e) {
            exceptionDialogBox(presenter.printException(e));
        } catch (CapacityException e) {
            exceptionDialogBox(presenter.printException(e));
        } finally {
            showMainDropDownMenu();
        }
    }


    private void showCancelAtt() {
        clearOpenInputBox();
        clearInput();
        dp1.setVisible(false);
        dp2.setVisible(false);
        dp4.setVisible(false);
        dp3.setText(presenter.printAddAttendeeUsernamePrompt());

        submit.setActionCommand("attendeeCancel");
        submit.addActionListener(this);
    }

    private void cancelAtt() {
        try {
            controller.cancelAttendeeAccount(input3.getText());
        } catch (OverwritingException e) {
            exceptionDialogBox(presenter.printException(e));
        }finally {
            showMainDropDownMenu();
        }
    }


    private void showCancelEmp() {
        clearOpenInputBox();
        clearInput();
        dp1.setVisible(false);
        dp2.setVisible(false);
        dp4.setVisible(false);
        dp3.setText(presenter.printAddEmployeeUsernamePrompt());

        submit.setActionCommand("employeeCancel");
        submit.addActionListener(this);
    }

    private void cancelEmp() {
        try {
            controller.cancelEmployeeAccount(input3.getText());
        } catch (OverwritingException e) {
            exceptionDialogBox(presenter.printException(e));
        }finally {
            showMainDropDownMenu();
        }
    }


    private void showCancelOrg() {
        clearOpenInputBox();
        clearInput();
        dp1.setVisible(false);
        dp2.setVisible(false);
        dp4.setVisible(false);
        dp3.setText(presenter.printAddOrganizerUsernamePrompt());

        submit.setActionCommand("organizerCancel");
        submit.addActionListener(this);
    }

    private void cancelOrg() {
        try {
            controller.cancelOrganizerAccount(input3.getText());
        } catch (OverwritingException e) {
            exceptionDialogBox(presenter.printException(e));
        }finally {
            showMainDropDownMenu();
        }
    }


    @Override
    public void actionPerformed(ActionEvent event) {
        super.actionPerformed(event);

        if(eventName.equals(presenter.getMenuOptions()[0])) { // create speaker acc
            hideMainDropDownMenu();
            showCreateSpe();
        }

        if(eventName.equals(presenter.getMenuOptions()[1])) { // create attendee acc
            hideMainDropDownMenu();
            showCreateAtt();
        }

        if(eventName.equals(presenter.getMenuOptions()[2])) { // create employee acc
            hideMainDropDownMenu();
            showCreateEmp();
        }

        if(eventName.equals(presenter.getMenuOptions()[3])) { // create organizer acc
            hideMainDropDownMenu();
            showCreateOrg();
        }

        if(eventName.equals(presenter.getMenuOptions()[4])) { // cancel speaker acc
            hideMainDropDownMenu();
            showCancelSpe();
        }

        if(eventName.equals(presenter.getMenuOptions()[5])) { // cancel attendee acc
            hideMainDropDownMenu();
            showCancelAtt();
        }

        if(eventName.equals(presenter.getMenuOptions()[6])) { // cancel employee acc
            hideMainDropDownMenu();
            showCancelEmp();
        }

        if(eventName.equals(presenter.getMenuOptions()[7])) { // cancel organizer acc
            hideMainDropDownMenu();
            showCancelOrg();
        }

        if(eventName.equals("speakerCreate")) { // create speaker acc
            createSpe();
        }

        if(eventName.equals("organizerCreate")) { // create attendee acc
            createOrg();
        }

        if(eventName.equals("attendeeCreate")) { // create employee acc
            createAtt();
        }

        if(eventName.equals("employeeCreate")) { // create organizer acc
            createEmp();
        }

        if(eventName.equals("speakerCancel")) { // cancel speaker acc
            cancelSpe();
        }

        if(eventName.equals("organizerCancel")) { // cancel attendee acc
            cancelOrg();
        }

        if(eventName.equals("attendeeCancel")) { // cancel employee acc
            cancelAtt();
        }

        if(eventName.equals("employeeCancel")) { // cancel organizer acc
            cancelEmp();
        }
    }
}
