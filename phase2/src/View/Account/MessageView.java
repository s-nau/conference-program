package View.Account;

// Programmer: Sarah Kronenfeld, Ran Yi
// Description: All the methods that take user input in the Message Menu
// Date Created: 01/11/2020
// Date Modified: 08/12/2020

import Presenter.Central.SubMenu;
import Presenter.Exceptions.InvalidChoiceException;
import Presenter.PersonController.MessageController;
import Presenter.PersonController.MessageMenu;
import View.AccountHelpers.ListDisplayView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MessageView extends AccountView {
    MessageController controller;
    MessageMenu presenter;
    JLabel dialogPrompt, dialogPrompt2;
    JButton sendMsg, chooseChat, archiveChatButton, leaveButton, archiveMsgButton, unreadMsgButton, readMsgButton;
    ListDisplayView msgList;
    JTextField inputField;
    JTextArea messageField;

    /**
     * The view for users to see their convention message options.
     * @param controller MessageController for handling user input
     */
    public MessageView(SubMenu controller) {
        super(controller);
        this.controller = (MessageController) controller;
        presenter = (MessageMenu) controller.getPresenter();

        dialogPrompt = new JLabel("");
        initializeObject(dialogPrompt);

        inputField = new JTextField(20);
        initializeObject(inputField);

        dialogPrompt2 = new JLabel("");
        initializeObject(dialogPrompt2);

        messageField = new JTextArea(5, 20);
        messageField.setPreferredSize(new Dimension(20, 20));
        messageField.setLineWrap(true);
        messageField.setWrapStyleWord(true);
        initializeObject(messageField);

        sendMsg = newButton("Send message");
        sendMsg.setToolTipText("send the entered message to entered user(s)");
        chooseChat = newButton("choose chat");
        chooseChat.setToolTipText("choose a chat to view");

        archiveChatButton = newButton("Archive");
        leaveButton = newButton("Leave");
        archiveMsgButton = newButton("Archive Message");
        unreadMsgButton = newButton("Unread Msg");
        readMsgButton = newButton("Read Msg");
    }

    private void showCheckInbox() {
        try {
            msgList = new ListDisplayView(presenter.getInboxTitle(), presenter.getInBox());
            showMainDropDownMenu();
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }
    }

    private void showCheckSentBox() {
        try {
            msgList = new ListDisplayView(presenter.getOutboxTitle(), presenter.getOutBox());
            showMainDropDownMenu();
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }
    }

    private void showViewChats() {
        try {
            msgList = new ListDisplayView(presenter.getChatListTitle(), presenter.getChats(presenter.getChatList()));
            showMainDropDownMenu();
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }
    }

    protected void showOpenChat() {
        dialogPrompt.setText(presenter.printChatIdPrompt());
        dialogPrompt.setVisible(true);

        inputField.setVisible(true);

        chooseChat.setText("choose chat");
        chooseChat.setActionCommand("choose chat");
        chooseChat.addActionListener(this);
        chooseChat.setVisible(true);
        backButton.setVisible(true);
    }

    private void showViewMsgsInChat() {
        String chatName = inputField.getText();
        inputField.setText("");
        try {
            msgList = new ListDisplayView(presenter.getChatTitle(chatName), presenter.getChat(chatName));
            showMainDropDownMenu();
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }
    }

    private void showSendMsg() {
        dialogPrompt.setText(presenter.printChatNameMessagePrompt());
        dialogPrompt.setVisible(true);

        inputField.setVisible(true);

        dialogPrompt2.setText(presenter.printContentPrompt());
        dialogPrompt2.setVisible(true);

        messageField.setVisible(true);

        sendMsg.setVisible(true);
        backButton.setVisible(true);
    }

    private void sendMsg() {
        String chatName = inputField.getText();
        inputField.setText("");
        String msg = messageField.getText();
        messageField.setText("");

        try {
            JOptionPane.showConfirmDialog(null, controller.sendMessage(chatName, msg),
                    "Message", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidChoiceException e){
            exceptionDialogBox(presenter.printException(e));
        }
    }

    private void showArchiveChat() {
        dialogPrompt.setText(presenter.printArchiveChatPrompt());
        dialogPrompt.setVisible(true);

        inputField.setVisible(true);

        archiveChatButton.setVisible(true);
        backButton.setVisible(true);
    }

    private void archiveChat() {
        try {
            controller.archiveChat(inputField.getText());
            inputField.setText("");

            JOptionPane.showConfirmDialog(null, "This chat has been archived",
                    "Message", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }
    }

    private void showLeaveChat() {
        dialogPrompt.setText("Enter the name of the chat you would like to delete");
        dialogPrompt.setVisible(true);

        inputField.setVisible(true);

        leaveButton.setVisible(true);
        backButton.setVisible(true);
    }

    private void leave() {
        try {
            controller.deleteChat(inputField.getText());
            inputField.setText("");

            JOptionPane.showConfirmDialog(null, "You have left this chat",
                    "Message", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }
    }

    private void showArchiveMsg() {
        dialogPrompt.setText("Enter the ID of the message you would like to archive");
        dialogPrompt.setVisible(true);

        inputField.setVisible(true);

        archiveMsgButton.setVisible(true);
        backButton.setVisible(true);
    }

    private void archiveMsg() {
        try {
            controller.archiveMessage(inputField.getText());
            inputField.setText("");

            JOptionPane.showConfirmDialog(null, "This message has been archived",
                    "Message", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }
    }

    private void showMsgUnread() {
        dialogPrompt.setText("Enter the ID of the message you would like to mark as unread");
        dialogPrompt.setVisible(true);

        inputField.setVisible(true);

        unreadMsgButton.setVisible(true);
        backButton.setVisible(true);
    }

    private void msgUnread() {
        try {
            controller.unreadMessage(inputField.getText());
            inputField.setText("");

            JOptionPane.showConfirmDialog(null, "This message has been marked as unread",
                    "Message", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }
    }

    private void showMsgRead() {
        dialogPrompt.setText("Enter the ID of the message you would like to mark as read");
        dialogPrompt.setVisible(true);

        inputField.setVisible(true);

        readMsgButton.setVisible(true);
        backButton.setVisible(true);
    }

    private void msgRead() {
        try {
            controller.readMessage(inputField.getText());
            inputField.setText("");

            JOptionPane.showConfirmDialog(null, "This message has been marked as read",
                    "Message", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }

    }


    @Override
    public void actionPerformed(ActionEvent event) {
        super.actionPerformed(event);

        if(eventName.equals(this.menuOp[0])) {
            hideMainDropDownMenu();
            showCheckInbox();
        }

        if(eventName.equals(this.menuOp[1])) {
            hideMainDropDownMenu();
            showCheckSentBox();
        }

        if(eventName.equals(this.menuOp[2])) {
            hideMainDropDownMenu();
            showViewChats();
        }

        if(eventName.equals(this.menuOp[3])) {
            hideMainDropDownMenu();
            showOpenChat();
        }

        if(eventName.equals(this.menuOp[4])) {
            hideMainDropDownMenu();
            showSendMsg();
        }

        if(eventName.equals(menuOp[5])) { // archive a chat
            hideMainDropDownMenu();
            showArchiveChat();
        }

        if(eventName.equals(menuOp[6])) { // leave a chat
            hideMainDropDownMenu();
            showLeaveChat();
        }

        if(eventName.equals(menuOp[7])) { // archive a msg
            hideMainDropDownMenu();
            showArchiveMsg();
        }

        if(eventName.equals(menuOp[8])) {
            hideMainDropDownMenu();
            showMsgUnread();
        }

        if(eventName.equals(menuOp[9])) {
            hideMainDropDownMenu();
            showMsgRead();
        }

        if (eventName.equals(chooseChat.getActionCommand())) {
            showViewMsgsInChat();
            showMainDropDownMenu();
        }

        if (eventName.equals(sendMsg.getActionCommand())) {
            sendMsg();
            showMainDropDownMenu();
        }

        if(eventName.equals(archiveChatButton.getActionCommand())) {
            archiveChat();
            showMainDropDownMenu();
        }

        if(eventName.equals(leaveButton.getActionCommand())) {
            leave();
            showMainDropDownMenu();
        }

        if(eventName.equals(archiveMsgButton.getActionCommand())) {
            archiveMsg();
            showMainDropDownMenu();
        }

        if(eventName.equals(unreadMsgButton.getActionCommand())) {
            msgUnread();
            showMainDropDownMenu();
        }

        if(eventName.equals(readMsgButton.getActionCommand())) {
            msgRead();
            showMainDropDownMenu();
        }

    }



}
