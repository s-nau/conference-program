package View.Account;

import Presenter.AttendeeController.AttEventController;
import Presenter.AttendeeController.AttEventMenu;
import Presenter.Central.SubMenu;
import Presenter.Exceptions.InvalidChoiceException;
import Event.CapacityException;
import View.AccountHelpers.ListDisplayView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class AttEventView extends AccountView {
    AttEventController controller;
    AttEventMenu presenter;

    JButton signupButton, cancelSpotButton, chooseRoomButton;
    JTextField inputField;
    ListDisplayView eventList;
    JComboBox<String> roomChoice;
    JLabel dialoguePrompt;

    /**
     * The view for attendee users to see their convention event options.
     * @param controller AttendeeController for handling user input
     */
    public AttEventView(SubMenu controller) {
        super(controller);
        this.controller = (AttEventController) controller;
        this.presenter = (AttEventMenu) controller.getPresenter();

        contentPane.setBackground(new Color(255, 170, 130));// Sets background colour to white

        dialoguePrompt = new JLabel("");
        initializeObject(dialoguePrompt);

        signupButton = newButton("Sign up");
        signupButton.setToolTipText("click this button to signup for selected event");
        cancelSpotButton = newButton("Cancel spot");
        cancelSpotButton.setToolTipText("click this button to cancel spot in selected event");
        chooseRoomButton = newButton("Choose room");
        chooseRoomButton.setToolTipText("choose selected room");

        // this has to be initialized first with all of the different room options!!!
        roomChoice = new JComboBox<>();
        initializeObject(roomChoice);

        inputField = new JTextField(50);
        initializeObject(inputField);
    }

    private void showSignUp() {
        dialoguePrompt.setText(this.presenter.printAddEventPrompt());
        dialoguePrompt.setVisible(true);
        inputField.setVisible(true);
        signupButton.setVisible(true);
        backButton.setVisible(true);
    }

    private void showCancelSpot() {
        dialoguePrompt.setText(this.presenter.printRemoveEventPrompt());
        dialoguePrompt.setVisible(true);
        inputField.setVisible(true);
        cancelSpotButton.setVisible(true);
        backButton.setVisible(true);
    }

    private void showRoomChoice() {
        dialoguePrompt.setText(this.presenter.printRoomChoicePrompt());
        roomChoice.setVisible(true);
        chooseRoomButton.setVisible(true);
        backButton.setVisible(true);
    }


    /**
     * Uses the controller to try and sign up the user for an event (add event to their event list)
     */
    private void signup() {
        String event = inputField.getText();

        try {
            if (controller.signupForEvent(event)) {
                dialoguePrompt.setText(this.presenter.printEventAdded());
            }
            else {
                JOptionPane.showConfirmDialog(null, presenter.exceptionTitle(), "Unexpected Error",
                        JOptionPane.DEFAULT_OPTION);
            }

        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        } catch (CapacityException c) {
            exceptionDialogBox(presenter.printEventFull());
        }
    }

    /**
     * Uses the controller to try and cancel the user's spot in an event (add event to their event list)
     */
    private void cancelSpot() {
        String event = inputField.getText();

        try {
            if (controller.cancelSpotFromEvent(event)) {
                dialoguePrompt.setText(this.presenter.printEventRemoved());
            }
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }
    }

    /**
     * Opens up a new window showing the convention's event list
     */
    private void viewEventList(String room) {
        try {
            if (room.equals("See all events")) {
                eventList = new ListDisplayView(presenter.getEventListTitle(), presenter.printAllEvents());
            } else {
                eventList = new ListDisplayView(presenter.getEventListTitle(), presenter.printEventsInRoom(room));
            }
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        } finally {
            showMainDropDownMenu();
        }
    }

    /**
     * Opens up a new window showing the user's event list
     */
    private void viewOwnEvents() {
        try {
            eventList = new ListDisplayView(presenter.ownEventListTitle(), controller.getEventsSignedUpFor());
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        } finally {
            showMainDropDownMenu();
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        super.actionPerformed(event);

        if(eventName.equals(signupButton.getActionCommand())) {
            signup();
        }

        if(eventName.equals(cancelSpotButton.getActionCommand())) {
            cancelSpot();
        }

        if(eventName.equals(chooseRoomButton.getActionCommand())) {
            viewEventList((String) Objects.requireNonNull(roomChoice.getSelectedItem()));
        }

        if(eventName.equals(menuOp[0])) { // view list of all Events
            hideMainDropDownMenu();
            showRoomChoice();
        }

        if(eventName.equals(menuOp[1])) { // sign up for an Event
            hideMainDropDownMenu();
            showSignUp();
        }

        if(eventName.equals(menuOp[2])) { // cancel your spot from an Event
            hideMainDropDownMenu();
            showCancelSpot();
        }

        if(eventName.equals(menuOp[3])) { // get list of your signed up events
            hideMainDropDownMenu();
            viewOwnEvents();
        }
    }
}
