package View.Account;

import Presenter.AttendeeController.AttMessageController;
import Presenter.AttendeeController.AttMessageMenu;
import Presenter.Central.SubMenu;
import Presenter.Exceptions.InvalidChoiceException;
import View.AccountHelpers.ListDisplayView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;


public class AttMessageView extends MessageView {
    AttMessageController controller;
    AttMessageMenu presenter;
    AttMessageMenu announcementPresenter;
    JButton createChatButton, createGroupChatButton;
    JLabel dialogPrompt, dialogPrompt2;
    JTextField inputField, inputField2;

    /**
     * The view for attendee users to see their message options.
     * @param controller AttMessageController for handling user input
     */
    public AttMessageView(SubMenu controller) {
        super(controller);
        this.controller = (AttMessageController) controller;
        this.presenter = (AttMessageMenu) controller.getPresenter();
        this.announcementPresenter = ((AttMessageController) controller).getAnChatPresenter();

        contentPane.setBackground(new Color(255, 240, 150));// Sets background colour

        dialogPrompt = new JLabel("");
        initializeObject(dialogPrompt);
        inputField = new JTextField(20);
        initializeObject(inputField);

        dialogPrompt2 = new JLabel("");
        initializeObject(dialogPrompt2);
        inputField2 = new JTextField(20);
        initializeObject(inputField2);

        createChatButton = newButton("Create chat");
        createGroupChatButton = newButton("Create group chat");
        createChatButton.setToolTipText("create chat between you and entered user");
        createGroupChatButton.setToolTipText("create chat between you and entered users");
    }

    private void showViewAnnouncementChannels() {
        try {
            msgList = new ListDisplayView(announcementPresenter.getChatListTitle(),
                    announcementPresenter.getChats(announcementPresenter.getChatList()));
            showMainDropDownMenu();
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }
    }

    private void showOpenAnChat() {
        showOpenChat();
        dialogPrompt.setText(announcementPresenter.printChatIdPrompt());
        chooseChat.setText("choose announcement chat");
        chooseChat.setActionCommand("choose announcement chat");
    }

    private void showAnnouncementChat() {
        String chatID = inputField.getText();

        try {
            msgList = new ListDisplayView(announcementPresenter.getChatTitle(chatID),
                    announcementPresenter.getChat(chatID));
            showMainDropDownMenu();
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }
    }

    private void showCreateChat() {
        dialogPrompt.setText(presenter.printContactUsernamePrompt());
        dialogPrompt.setVisible(true);

        inputField.setVisible(true);

        dialogPrompt2.setText("Enter the name of the chat you would like to create");
        dialogPrompt2.setVisible(true);

        inputField2.setVisible(true);

        createChatButton.setVisible(true);
        backButton.setVisible(true);
    }

    private void createChat() {
        String participantUsername = inputField.getText();
        String chatName = inputField2.getText();

        JOptionPane.showConfirmDialog(null, controller.createChat(participantUsername, chatName),
                "Message", JOptionPane.DEFAULT_OPTION);

        //try catch block here

        /*
        try {
            if(controller.createChat(participantID).equals(presenter.printChatExists())) {

            }
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.exceptionTitle(), presenter.printChatNotCreated(e));
            //presenter.printChatNotCreated(new InvalidChoiceException("user"));
        } catch (InvalidFormatException f) {
            exceptionDialogBox(presenter.exceptionTitle(), presenter.printException(f));
            //presenter.printChatNotCreated(new
            //        InvalidFormatException("recipients", "You cannot create a chat with yourself!"));
        }*/




    }

    private void showCreateGroupChat() {
        dialogPrompt.setText(presenter.printContactUsernamesPrompt());
        dialogPrompt.setVisible(true);

        new JTextField(50);
        inputField.setVisible(true);

        dialogPrompt2.setText("Enter the name of the group chat you would like to create");
        dialogPrompt2.setVisible(true);

        new JTextField(50);
        inputField2.setVisible(true);

        createGroupChatButton.setVisible(true);
        backButton.setVisible(true);
    }


    private void createGroupChat(){ //DONE: convert participantIDs into an array of Strings
        String commaSeparated = inputField.getText();
        ArrayList<String> participantUsernames = new ArrayList<>(Arrays.asList(commaSeparated.split(",")));
        String groupName = inputField2.getText();

        JOptionPane.showConfirmDialog(null, controller.createGroupChat(participantUsernames, groupName),
                "Message", JOptionPane.DEFAULT_OPTION);
    }

    private void showViewChat() {
        dialogPrompt.setText(presenter.printContactUsernamesPrompt());
        dialogPrompt.setVisible(true);

        new JTextField(50);
        inputField.setVisible(true);

        dialogPrompt2.setText("Enter the name of the chat you would like to view");
        dialogPrompt2.setVisible(true);

        new JTextField(50);
        inputField2.setVisible(true);

        createGroupChatButton.setVisible(true);
        backButton.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        super.actionPerformed(event);

        if(eventName.equals(menuOp[10])) {
            hideMainDropDownMenu();
            showViewAnnouncementChannels();
        }

        if(eventName.equals(menuOp[11])) {
            hideMainDropDownMenu();
            showOpenAnChat();
        }

        if(eventName.equals(menuOp[12])) {
            hideMainDropDownMenu();
            showCreateChat();
        }

        if(eventName.equals(menuOp[13])) {
            hideMainDropDownMenu();
            showCreateGroupChat();
        }

        if (eventName.equals("choose announcement chat")) {
            showAnnouncementChat();
            showMainDropDownMenu();
        }

        if (eventName.equals(createChatButton.getActionCommand())) {
            createChat();
            showMainDropDownMenu();
        }

        if(eventName.equals(createGroupChatButton.getActionCommand())) {
            createGroupChat();
            showMainDropDownMenu();
        }

    }
}
