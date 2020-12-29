package View.Account;

import Presenter.Central.SubMenu;
import Presenter.Exceptions.InvalidChoiceException;
import Presenter.Exceptions.NoDataException;
import Presenter.OrganizerController.OrgEventController;
import Presenter.OrganizerController.OrgEventMenu;
import Event.CapacityException;
import Event.NotPanelException;
import View.AccountHelpers.ListDisplayView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class OrgEventView extends AccountView {
    OrgEventController controller;
    OrgEventMenu presenter;
    String[] menuOp;
    JLabel dpMain, dp1, dp2, dp3, dp4, dp5, dp6, dp7, dp8; // dp = "dialogue prompt"
    JButton nextButton;
    //JButton createRoomButton, createEventButton, cancelEventButton, addSpeakerToPanelButton,
    //        removeSpeakerFromPanelButton, changeEventCapacityButton;
    JTextField input1, input2, input3, input4, input5, input6, input7, input8;
    JComboBox<String> allEvents;
    ListDisplayView allEventTypes, allRooms;
    JTextArea messageField;
    private JLabel dialogPrompt, nameOfEvent, nameOfChat;
    private final JTextField inputField = new JTextField(20);
    private final JTextField inputChatName = new JTextField(20);
    private JButton selectButton, announcementButton;

    /**
     * The view for organizer users to see their convention event options.
     * @param controller OrgEventController for handling user input
     */
    public OrgEventView(SubMenu controller) {
        super(controller);
        this.controller = (OrgEventController) controller;
        this.presenter = (OrgEventMenu) controller.getPresenter();

        menuOp = this.presenter.getMenuOptions();

        contentPane.setBackground(new Color(200, 10, 150));

        setupTextFields();

        nextButton = newButton("continue");

        nameOfChat = new JLabel("");
        initializeObject(nameOfChat);
        initializeObject(inputChatName);

        dialogPrompt = new JLabel("");
        initializeObject(dialogPrompt);

        messageField = new JTextArea(5, 20);
        messageField.setPreferredSize(new Dimension(20, 20));
        messageField.setLineWrap(true);
        messageField.setWrapStyleWord(true);
        initializeObject(messageField);

        nameOfEvent = new JLabel("");
        initializeObject(nameOfEvent);
        initializeObject(inputField);

        selectButton = newButton("select");
        announcementButton = newButton("send announcement");
    }

    private void showNext(String command) {
        nextButton.setActionCommand(command);
        nextButton.addActionListener(this);
        nextButton.setVisible(true);
    }

    private void setupTextFields() {
        dpMain = new JLabel("");
        initializeObject(dpMain);

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

        dp5 = new JLabel("");
        initializeObject(dp5);
        input5 = new JTextField(20);
        initializeObject(input5);

        dp6 = new JLabel("");
        initializeObject(dp6);
        input6 = new JTextField(20);
        initializeObject(input6);

        dp7 = new JLabel("");
        initializeObject(dp7);
        input7 = new JTextField(20);
        initializeObject(input7);

        dp8 = new JLabel("");
        initializeObject(dp8);
        input8 = new JTextField(20);
        initializeObject(input8);
    }

    private void showCreateRoom() {
        backButton.setVisible(true);

        dpMain.setText(presenter.addRoomPrompt());
        dpMain.setVisible(true);

        dp1.setText(presenter.roomNamePrompt());
        dp1.setVisible(true);
        input1.setVisible(true);

        dp2.setText(presenter.roomCapacityPrompt());
        dp2.setVisible(true);
        input2.setVisible(true);

        showNext("newroom");
    }

    private void createRoom() {
        String roomName = input1.getText();
        String roomCap = input2.getText();

        try {
            if(!roomCap.equals("")) {
                if(controller.addRoom(roomName, Integer.parseInt(roomCap))) {
                    JOptionPane.showConfirmDialog(null, "Room creation successful!",
                            "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showConfirmDialog(null, "Something went wrong. Please try again",
                            "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showConfirmDialog(null, "Please enter valid information.",
                        "Warning!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
            }
        } catch (InvalidChoiceException c) {
            exceptionDialogBox(presenter.printException(c));
        } catch (NumberFormatException e) {
            exceptionDialogBox("Please enter an integer!");
        }
    }

    private void showCreateEvent() {
        backButton.setVisible(true);

        dpMain.setText(presenter.printCreateEventPrompt());
        dpMain.setVisible(true);

        dp1.setText(presenter.printEventNamePrompt());
        dp1.setVisible(true);
        input1.setVisible(true);

        dp2.setText(presenter.printEventTypePrompt());
        dp2.setVisible(true);
        input2.setVisible(true);

        dp3.setText(presenter.printEventCapPrompt());
        dp3.setVisible(true);
        input3.setVisible(true);

        dp4.setText(presenter.printRoomNamePrompt());
        dp4.setVisible(true);
        input4.setVisible(true);

        dp5.setText(presenter.printSpeakerUsernamePrompt());
        dp5.setVisible(true);
        input5.setVisible(true);

        dp6.setText(presenter.printStartTimePrompt());
        dp6.setVisible(true);
        input6.setVisible(true);

        dp7.setText(presenter.printEndTimePrompt());
        dp7.setVisible(true);
        input7.setVisible(true);

        dp8.setText(presenter.printDescriptionPrompt());
        dp8.setVisible(true);
        input8.setVisible(true);

        showNext("newevent");
    }

    private void createEvent() {
        String nameOfEvent = input1.getText();
        String eventType = input2.getText();
        String eventCap = input3.getText();
        String roomName = input4.getText();
        String speakerUsername = input5.getText();
        String startTime = input6.getText();
        String endTime = input7.getText();
        String eventDesc = input8.getText();

        try {
            if(eventType.equals("0")) {
                allEventTypes = new ListDisplayView("All Event Types", presenter.eventTypes(eventType));
            } else if (roomName.equals("0")) {
                try {
                    allRooms = new ListDisplayView("All Rooms", presenter.getRoomList());
                } catch (NoDataException d) {
                    exceptionDialogBox(presenter.printException(d));
                }
            } else if(eventType.equals("") && roomName.equals("")) {
                JOptionPane.showConfirmDialog(null, presenter.exceptionTitle(), "Please enter information carefully",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
            } else if(controller.createEvent(nameOfEvent, speakerUsername, startTime, endTime, eventDesc, Integer.parseInt(eventCap),
                    controller.getEventType(eventType), roomName)){
                JOptionPane.showConfirmDialog(null, "Success", "Event has been created!",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showConfirmDialog(null, presenter.exceptionTitle(), "Please enter information carefully",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
            }
        } catch (InvalidChoiceException c) {
            exceptionDialogBox(presenter.printException(c));
        }
    }

    private void showCancelEvent() {
        backButton.setVisible(true);

        dpMain.setText(presenter.cancelEventPrompt());
        dpMain.setVisible(true);

        dp1.setText(presenter.printEventNameToCancelPrompt());
        dp1.setVisible(true);
        input1.setVisible(true);

        showNext("cancelevent");
    }

    private void cancelEvent() {
        String eventName = input1.getText();
        if(controller.cancelEvent(eventName)) {
                JOptionPane.showConfirmDialog(null, "Event cancellation successful!",
                        "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        } else {
                JOptionPane.showConfirmDialog(null, "Please enter valid information. An event "
                                + "of this name does not exist.",
                        "Warning!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
            }
    }

    private void showAddSpeakerToPanel() {
        backButton.setVisible(true);

        dpMain.setText(presenter.addSpeakerToPanelPrompt());
        dpMain.setVisible(true);

        dp1.setText(presenter.speakerToAddPrompt());
        dp1.setVisible(true);
        input1.setVisible(true);

        dp2.setText(presenter.panelToAddToPrompt());
        dp2.setVisible(true);
        input2.setVisible(true);

        showNext("addspeaker");
    }

    private void addSpeakerToPanel() {
        String speakerName = input1.getText();
        String eventName = input2.getText();

        try {
            if(controller.addSpeakerToPanel(speakerName, eventName)) {
                JOptionPane.showConfirmDialog(null, "Speaker sign-up successful!",
                        "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showConfirmDialog(null, "Please enter valid information.",
                        "Warning!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
            }
        } catch (InvalidChoiceException c) {
            exceptionDialogBox(presenter.printException(c));
        } catch (NotPanelException c) {
            exceptionDialogBox(presenter.printException(c));
        } catch (CapacityException c) {
            exceptionDialogBox(presenter.printException(c));
        }
    }

    private void showRemoveSpeakerFromPanel() {
        backButton.setVisible(true);

        dpMain.setText(presenter.removeSpeakerFromPanelPrompt());
        dpMain.setVisible(true);

        dp1.setText(presenter.speakerToRemovePrompt());
        dp1.setVisible(true);
        input1.setVisible(true);

        dp2.setText(presenter.panelToRemoveFromPrompt());
        dp2.setVisible(true);
        input2.setVisible(true);

        showNext("removespeaker");
    }

    private void removeSpeakerFromPanel() {
        String speakerName = input1.getText();
        String eventName = input2.getText();

        try {
            if(controller.removeSpeakerFromPanel(speakerName, eventName)) {
                JOptionPane.showConfirmDialog(null, "Speaker removal successful!",
                        "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showConfirmDialog(null, "Please enter valid information.",
                        "Warning!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
            }
        } catch (InvalidChoiceException c) {
            exceptionDialogBox(presenter.printException(c));
        } catch (NotPanelException c) {
            exceptionDialogBox(presenter.printException(c));
        }
    }

    private void showChangeEventCapacity() {
        backButton.setVisible(true);

        dpMain.setText(presenter.changeEventCapacityPrompt());
        dpMain.setVisible(true);

        dp1.setText(presenter.eventToChangePrompt());
        dp1.setVisible(true);
        input1.setVisible(true);

        dp2.setText(presenter.newCapacityPrompt());
        dp2.setVisible(true);
        input2.setVisible(true);

        showNext("changecap");
    }

    private void changeEventCapacity() {
        String eventName = input1.getText();
        String capacity = input2.getText();

        try {
            if(controller.changeCapacity(eventName, Integer.parseInt(capacity))) {
                JOptionPane.showConfirmDialog(null, "Speaker removal successful!",
                        "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showConfirmDialog(null, "Please enter valid information.",
                        "Warning!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
            }
        } catch (InvalidChoiceException c) {
            exceptionDialogBox(presenter.printException(c));
        }
    }

    private void showMakeAnnouncement() {
        selectButton.setVisible(false);

        nameOfChat.setText(presenter.printChatNamePrompt());
        nameOfChat.setVisible(true);
        inputChatName.setText("");
        inputChatName.setVisible(true);

        dialogPrompt.setText(presenter.printMessageContentPrompt());
        dialogPrompt.setVisible(true);
        messageField.setVisible(true);
        announcementButton.setVisible(true);
        nameOfEvent.setText(presenter.printEventNamePrompt());
        inputField.setText("");
        inputField.setVisible(true);
        nameOfEvent.setVisible(true);
    }

    private void sendAnnouncement() {
        hideAll();
        String specificEventName = inputField.getText();
        String nameOfChat = inputChatName.getText();
        String message = messageField.getText();
        try {
            controller.eventMessage(specificEventName, nameOfChat, message);
            hideAll();
            dialogPrompt.setText(presenter.printMessageSent());
            okayButton.setVisible(true);
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }
    }


    @Override
    public void actionPerformed(ActionEvent event) {
        super.actionPerformed(event);

        if(eventName.equals("newroom")) {
            createRoom();
        }

        if(eventName.equals("newevent")) {
            createEvent();
        }

        if(eventName.equals("cancelevent")) {
            cancelEvent();
        }

        if(eventName.equals("addspeaker")) {
            addSpeakerToPanel();
        }

        if(eventName.equals("removespeaker")) {
            removeSpeakerFromPanel();
        }

        if(eventName.equals("changecap")) {
            changeEventCapacity();
        }

        if(eventName.equals(menuOp[0])) {
            hideMainDropDownMenu();
            showCreateRoom();
        }

        if(eventName.equals(menuOp[1])) {
            hideMainDropDownMenu();
            showCreateEvent();
        }

        if(eventName.equals(menuOp[2])) {
            hideMainDropDownMenu();
            showCancelEvent();
        }

        if(eventName.equals(menuOp[3])) {
            hideMainDropDownMenu();
            showAddSpeakerToPanel();
        }

        if(eventName.equals(menuOp[4])) {
            hideMainDropDownMenu();
            showRemoveSpeakerFromPanel();
        }

        if(eventName.equals(menuOp[5])) {
            hideMainDropDownMenu();
            showChangeEventCapacity();
        }

        if(eventName.equals(menuOp[6])) {
            hideMainDropDownMenu();
            showMakeAnnouncement();
        }

        if(eventName.equals("send announcement")) {
            sendAnnouncement();
        }

    }
}
