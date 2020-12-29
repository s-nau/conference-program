package View.Account;

import Presenter.AttendeeController.AttReqController;
import Presenter.AttendeeController.AttReqMenu;
import Presenter.Central.SubMenu;
import Presenter.Exceptions.InvalidChoiceException;
import View.AccountHelpers.ListDisplayView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AttReqView extends AccountView {
    AttReqController controller;
    AttReqMenu presenter;
    String[] menuOp;
    JLabel dialogPrompt;
    JTextField inputField;
    ListDisplayView reqsList;
    JButton submit;

    /**
     * The view for attendee users to see their request options.
     * @param controller AttReqController for handling user input
     */
    public AttReqView(SubMenu controller) {
        super(controller);
        this.controller = (AttReqController) controller;
        this.presenter = (AttReqMenu) controller.getPresenter();

        menuOp = this.presenter.getMenuOptions();

        contentPane.setBackground(greenBG);

        dialogPrompt = new JLabel("");
        initializeObject(dialogPrompt);

        submit = new JButton("submit");
        initializeObject(submit);
    }


    // Option 1

    private void showMakeReq() {
        submit.setActionCommand("make");
        submit.addActionListener(this);
        submit.setVisible(true);

        frame.setTitle(presenter.makeRequestTitle());
        dialogPrompt.setText(presenter.makeRequestPrompt());

        inputField = new JTextField(50);
        initializeObject(inputField);
        inputField.setVisible(true);
    }

    private void makeReq() {
        inputField.setVisible(false);
        submit.setVisible(false);

        controller.createRequest(inputField.getText());

        dialogPrompt.setText(presenter.printRequestCreated());
        dialogPrompt.setVisible(true);

        backButton.setVisible(true);
    }

    // Option 2

    private void showChooseReqPrompt() {
        dialogPrompt.setText(presenter.seeRequestPrompt());
        dialogPrompt.setVisible(true);

        inputField = new JTextField(30);
        initializeObject(inputField);
        inputField.setVisible(true);

        dialogPrompt.setText(presenter.seeRequestPrompt());

        frame.setTitle(presenter.viewRequestTitle());

        submit.setActionCommand("show");
        submit.addActionListener(this);
        submit.setVisible(true);
    }

    private void showReq() {
        inputField.setVisible(false);
        submit.setVisible(false);

        String reqID = inputField.getText();

        try {
            backButton.setVisible(true);

            dialogPrompt.setText(presenter.seeRequest(reqID));
            dialogPrompt.setBounds(10, 10, 200, 300);

            frame.setTitle(presenter.specificRequestTitle(reqID));
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
            dialogPrompt.setVisible(false);
            frame.setTitle(presenter.getMenuTitle());
        }
    }

    // Option 3

    private void showMyReqs() {
        try {
            reqsList = new ListDisplayView(presenter.viewRequestTitle(), presenter.myRequests());
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }
    }

    /*private void modifyReq() {
        backButton.setVisible(true);

    }*/


    @Override
    public void actionPerformed(ActionEvent event) {
        super.actionPerformed(event);

        // [0] = make a request
        // [1] = view info about a request
        // [2] = show my requests

        if(eventName.equals(menuOp[0])) {
            hideMainDropDownMenu();
            showMakeReq();
        }

        if(eventName.equals(menuOp[1])) {
            hideMainDropDownMenu();
            showChooseReqPrompt();
        }

        if(eventName.equals(menuOp[2])) {
            showMyReqs();
        }

        if (eventName.equals("make")) {
            makeReq();
        }

        if (eventName.equals("show")) {
            showReq();
        }

        if (eventName.equals("back")) {
            showMainDropDownMenu();
            frame.setTitle(presenter.getMenuTitle());
        }

        /*if(eventName.equals(menuOp[3])) {
            hideMainDropDownMenu();
            modifyReq();
        }*/
    }
}
// ignore this comment