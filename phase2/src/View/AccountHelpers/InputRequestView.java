package View.AccountHelpers;

import Presenter.Exceptions.InvalidChoiceException;
import Presenter.PersonController.MessageController;
import Presenter.PersonController.MessageMenu;
import View.Account.MessageView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputRequestView implements ActionListener {

    JButton submitButton;
    JLabel promptViewChat;
    JLabel promptArchiveChat;
    JLabel promptExitChat;
    JFrame frame;
    JPanel contentPane;
    //JTextField textField;
    int operation;
    MessageController controller;
    MessageMenu presenter;
    JLabel dialogPrompt, dialogPrompt2;
    JButton sendMsg, chooseChat;
    ListDisplayView msgList;
    JTextField inputField;
    JTextArea messageField;
    String chatId;

    public InputRequestView(String title, int operation) {
        this.operation = operation;
        frame = new JFrame(title); // Create and set up the frame
        contentPane = new JPanel();// Create a content pane with a BoxLayout and empty borders
        contentPane.setPreferredSize(new Dimension(400,100));
        contentPane.setBackground(new Color(255, 255, 255));// Sets background colour to white
        contentPane.setLayout(new FlowLayout());

        JTextField inputField = new JTextField(16);
        contentPane.add(inputField);
        //initializeObject(inputField);

        submitButton = new JButton("Submit");
        submitButton.setActionCommand("submit");
        submitButton.addActionListener(this);
        submitButton.setToolTipText("click this button to submit the input text");
        contentPane.add(submitButton);
        submitButton.setVisible(true);

        frame.setContentPane(contentPane);// Add content pane to frame
        frame.pack();// Size and then display the frame.
        display();
    }

    public void setChatId(String Id) {
        this.chatId = Id;
    }

    /**
     * Create a new menu shows the formatted list of chats info.
     */
    private void viewMessagesInChat() throws InvalidChoiceException {
        int serialNumber = Integer.parseInt(inputField.getText());
        String chatID = presenter.getChatList().get(serialNumber - 1);
        msgList = new MessageListDisplayView("-- MESSAGES --", presenter.getMessagesInChat(chatID), chatID);
    }

    /**
     * Archive a Chat
     */
    private void archiveChosenChat() throws InvalidChoiceException {
        int serialNumber = Integer.parseInt(inputField.getText());
        String chatID = presenter.getChatList().get(serialNumber - 1);
        controller.archiveChat(chatID);
    }

    /**
     * exit a Chat
     */
    private void exitChosenChat() throws InvalidChoiceException {
        int serialNumber = Integer.parseInt(inputField.getText());
        String chatID = presenter.getChatList().get(serialNumber - 1);
        controller.deleteChat(chatID);
    }

    /**
     * Archive a message
     */
    private void archiveChosenMessage() throws InvalidChoiceException {
        int serialNumber = Integer.parseInt(inputField.getText());
        String msgId = presenter.getMessageIdsInChat(this.chatId).get(serialNumber - 1);
        controller.archiveMessage(msgId);
    }

    /**
     * mark a message as unread
     */
    private void unreadChosenMessage() throws InvalidChoiceException {
        int serialNumber = Integer.parseInt(inputField.getText());
        String msgId = presenter.getMessageIdsInChat(this.chatId).get(serialNumber - 1);
        controller.unreadMessage(msgId);
    }

    /**
     * mark a message as unread
     */
    private void readChosenMessage() throws InvalidChoiceException {
        int serialNumber = Integer.parseInt(inputField.getText());
        String msgId = presenter.getMessageIdsInChat(this.chatId).get(serialNumber - 1);
        controller.readMessage(msgId);
    }

    /**
     * send a message
     */
    private void sendMessage() throws InvalidChoiceException {
        String content = inputField.getText();
        String chatID = this.chatId;
        controller.sendMessage(chatID, content);
    }

    /**
     * Shows every component stored in contentPane
     */
    private void display() {
        for (Component item: contentPane.getComponents()) {
            item.setVisible(true);
        }
        frame.setVisible(true);
    }

    /**
     * Hides every component stored in contentPane
     */
    void hide() {
        for (Component item: contentPane.getComponents()) {
            item.setVisible(false);
        }
        frame.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("submit") && this.operation == 1) {
            hide();
            try {
                viewMessagesInChat();
            } catch (InvalidChoiceException e) {
                e.printStackTrace();
            }
        }
        if (event.getActionCommand().equals("submit") && this.operation == 2) {
            hide();
            try {
                archiveChosenChat();
            } catch (InvalidChoiceException e) {
                e.printStackTrace();
            }
        }
        if (event.getActionCommand().equals("submit") && this.operation == 3) {
            hide();
            try {
                exitChosenChat();
            } catch (InvalidChoiceException e) {
                e.printStackTrace();
            }
        }
        if (event.getActionCommand().equals("submit") && this.operation == 4) {
            hide();
            try {
                sendMessage();
            } catch (InvalidChoiceException e) {
                e.printStackTrace();
            }
        }
        if (event.getActionCommand().equals("submit") && this.operation == 5) {
            hide();
            try {
                archiveChosenMessage();
            } catch (InvalidChoiceException e) {
                e.printStackTrace();
            }
        }
        if (event.getActionCommand().equals("submit") && this.operation == 6) {
            hide();
            try {
                unreadChosenMessage();
            } catch (InvalidChoiceException e) {
                e.printStackTrace();
            }
        }
        if (event.getActionCommand().equals("submit") && this.operation == 7) {
            hide();
            try {
                readChosenMessage();
            } catch (InvalidChoiceException e) {
                e.printStackTrace();
            }
        }
    }
}
