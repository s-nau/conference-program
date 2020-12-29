package View.Account;

import Presenter.Central.SubMenu;
import Presenter.Exceptions.InvalidChoiceException;
import Presenter.OrganizerController.OrgReqController;
import Presenter.OrganizerController.OrgReqMenu;
import View.AccountHelpers.ListDisplayView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OrgReqView extends AttReqView {
    OrgReqController controller;
    OrgReqMenu presenter;

    /**
     * The view for organiser users to see their request options.
     * @param controller OrgReqController for handling user input
     */
    public OrgReqView(SubMenu controller) {
        super(controller);
        this.controller =  (OrgReqController) controller;
        this.presenter = (OrgReqMenu) controller.getPresenter();

        contentPane.setBackground(blueBG);
    }

    // Option 2

    private void showReq() {
        inputField.setVisible(false);
        submit.setVisible(false);

        String reqID = inputField.getText();

        try {
            backButton.setVisible(true);

            dialogPrompt.setText(presenter.seeRequest(reqID) + "\n" + presenter.handlingRequestPrompt(reqID));
            dialogPrompt.setBounds(10, 10, 200, 300);

            frame.setTitle(presenter.specificRequestTitle(reqID));
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
            dialogPrompt.setVisible(false);
            frame.setTitle(presenter.getMenuTitle());
        }
    }

    // Option 4

    private void showFulfillReqPrompt() {
        submit.setActionCommand("fulfill");
        submit.addActionListener(this);
        submit.setVisible(true);

        frame.setTitle(presenter.fulfillRequestTitle());
        dialogPrompt.setText(presenter.fulfillRequestPrompt());

        inputField = new JTextField(30);
        initializeObject(inputField);
        inputField.setVisible(true);
    }

    private void showReqFulfilled() {
        inputField.setVisible(false);
        submit.setVisible(false);
        try {
            dialogPrompt.setText(controller.fulfillRequest(inputField.getText()));
            dialogPrompt.setVisible(true);

            backButton.setVisible(true);
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }
    }

    // Option 5

    private void showAllReqs() {
        try {
            reqsList = new ListDisplayView(presenter.viewRequestTitle(), presenter.allRequests());
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        super.actionPerformed(event);

        // [3] = fulfill a request
        // [4] = see all requests

        if(eventName.equals(menuOp[3])) {
            hideMainDropDownMenu();
            showFulfillReqPrompt();
        }

        if(eventName.equals(menuOp[4])) {
            showAllReqs();
        }

        if (eventName.equals("fulfill")) {
            showReqFulfilled();
        }

    }
}
