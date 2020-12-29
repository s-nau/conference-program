package View.Account;

import Presenter.Central.SubMenu;
import Presenter.Exceptions.InvalidChoiceException;
import Presenter.SpeakerController.SpeEventController;
import Presenter.SpeakerController.SpeEventMenu;
import View.AccountHelpers.ListDisplayView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SpeEventView extends AccountView {
    SpeEventController controller;
    SpeEventMenu presenter;
    private JLabel dialogPrompt, nameOfEvent, nameOfChat;
    private final JTextField inputField = new JTextField(20);
    private final JTextField inputChatName = new JTextField(20);
    private JButton selectButton, announcementButton;
    private JTextArea messageField;
    JComboBox<String> eventOptions;
    private final String[] menuOp;
    String eventType;

    /**
     * The view for speaker users to see their convention event options.
     * @param controller SpeEventController for handling user input
     */
    public SpeEventView(SubMenu controller) {
        super(controller);
        this.controller = (SpeEventController) controller;
        this.presenter = (SpeEventMenu) controller.getPresenter();

        contentPane.setBackground(new Color(255, 10, 190));
        menuOp = presenter.getMenuOptions();

        setupPane();

    }

    private void setupPane() {

        nameOfChat = new JLabel("");
        initializeObject(nameOfChat);
        initializeObject(inputChatName);

        dialogPrompt = new JLabel("");
        initializeObject(dialogPrompt);

        eventOptions = new JComboBox<>(presenter.getEventOptions());
        initializeObject(eventOptions);

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

    private void showConventionEvents() {
        showMainDropDownMenu();
    }

    private void showEventsSpeakingAt() {
        try {
            new ListDisplayView(presenter.getOwnEventsTitle(), controller.getFormattedEvents());
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        } finally {
            showMainDropDownMenu();
        }
    }

    private void showChooseEvents() {
        dialogPrompt.setText(presenter.printEventTypePrompt());
        dialogPrompt.setVisible(true);
        eventOptions.setVisible(true);
        selectButton.setVisible(true);
        selectButton.setToolTipText("select what event(s) the announcement is for");
        backButton.setVisible(true);
    }

    private void showMakeMultipleAnnouncements() {
        eventOptions.setVisible(false);
        selectButton.setVisible(false);
        
        nameOfChat.setText(presenter.printChatNamePrompt());
        nameOfChat.setVisible(true);
        inputChatName.setText("");
        inputChatName.setVisible(true);
        
        dialogPrompt.setText(presenter.printContentPrompt());
        dialogPrompt.setVisible(true);
        messageField.setVisible(true);
        announcementButton.setVisible(true);
    }

    private void showMakeSingleAnnouncement() {
        showMakeMultipleAnnouncements();
        nameOfEvent.setText(presenter.printEventNamePrompt());
        inputField.setText("");
        inputField.setVisible(true);
        nameOfEvent.setVisible(true);
    }

    private void sendAnnouncement() {
        hideAll();
        String[] eventList;
        String nameOfChat = inputChatName.getText();
        String message = messageField.getText();
        try {
            if (eventType.equals(presenter.getEventOptions()[0])) { // All Events
                eventList = controller.getEvents();
                controller.multipleEventsAnnouncement(eventList, nameOfChat, message);
            }
            if (eventType.equals(presenter.getEventOptions()[1])) { // Panel
                eventList = controller.getPanels();
                controller.multipleEventsAnnouncement(eventList, nameOfChat, message);
            }
            if (eventType.equals(presenter.getEventOptions()[2])) { // Non-Panel
                eventList = controller.getNonPanels();
                controller.multipleEventsAnnouncement(eventList, nameOfChat, message);
            }
            if (eventType.equals(presenter.getEventOptions()[3])) { // Specific
                String specificEventName = inputField.getText();
                controller.eventMessage(specificEventName, nameOfChat, message);
            }
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

        if (eventName.equals(menuOp[0])) { // View convention Event list
            hideMainDropDownMenu();
            showConventionEvents();
        }
        if (eventName.equals(menuOp[1])) { // View user events
            hideMainDropDownMenu();
            showEventsSpeakingAt();
        }
        if (eventName.equals(menuOp[2])) { // Make announcement
            hideMainDropDownMenu();
            showChooseEvents();
        }

        if (eventName.equals("select")) {
            eventName = (String) eventOptions.getSelectedItem();
            assert eventName != null;
            eventType = eventName;
            if (eventName.equals(presenter.getEventOptions()[0]) || eventName.equals(presenter.getEventOptions()[1]) 
                    || eventName.equals(presenter.getEventOptions()[2])) { 
                showMakeMultipleAnnouncements();
            }
            
            if (eventName.equals(presenter.getEventOptions()[3])) { // Specific Event
                showMakeSingleAnnouncement();
            }
        }

        if (eventName.equals("send announcement")) {
            sendAnnouncement();
        }


    }
}
