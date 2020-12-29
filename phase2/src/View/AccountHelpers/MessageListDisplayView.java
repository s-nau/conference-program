package View.AccountHelpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MessageListDisplayView extends ChatListDisplayView{

    JButton checkButton;
    JButton archiveButton;
    JButton ReadButton;
    JButton UnreadButton;
    JButton submitButton;
    JDialog chatSerialNumber;
    JPanel dialogPanel;
    Container container;
    InputRequestView request;
    String chatId;

    public MessageListDisplayView(String title, String[] options, String chatId) {
        super(title, options);
        this.chatId = chatId;
        contentPane.setBackground(new Color(121, 193, 154));
        checkButton = new JButton("Send Message");
        checkButton.setBounds(100, 600, 80, 30);
        checkButton.setActionCommand("sendMessage");
        checkButton.addActionListener(this);
        checkButton.setToolTipText("click this button to Send Message in this chat");
        contentPane.add(checkButton);
        checkButton.setVisible(true);

        archiveButton = new JButton("Archive");
        archiveButton.setBounds(200, 600, 80, 30);
        archiveButton.setActionCommand("Archive");
        archiveButton.addActionListener(this);
        archiveButton.setToolTipText("click this button to Archive a message");
        contentPane.add(archiveButton);
        archiveButton.setVisible(true);

        UnreadButton = new JButton("Unread");
        UnreadButton.setBounds(300, 600, 80, 30);
        UnreadButton.setActionCommand("exitChat");
        UnreadButton.addActionListener(this);
        UnreadButton.setToolTipText("click this button to Exit and Delete Chat");
        contentPane.add(UnreadButton);
        UnreadButton.setVisible(true);

        ReadButton = new JButton("Read");
        ReadButton.setBounds(400, 600, 80, 30);
        ReadButton.setActionCommand("exitChat");
        ReadButton.addActionListener(this);
        ReadButton.setToolTipText("click this button to Exit and Delete Chat");
        contentPane.add(ReadButton);
        ReadButton.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("close")) {
            hide();
        }
        if (event.getActionCommand().equals("sendMessage")) {
            request = new InputRequestView("Input the Content of the Message", 4);
            request.setChatId(this.chatId);
        }
        if (event.getActionCommand().equals("Archive")) {
            request = new InputRequestView("Input the Serial Number of the Message", 5);
            request.setChatId(this.chatId);
        }
        if (event.getActionCommand().equals("Unread")) {
            request = new InputRequestView("Input the Serial Number of the Message", 6);
            request.setChatId(this.chatId);
        }
        if (event.getActionCommand().equals("Read")) {
            request = new InputRequestView("Input the Serial Number of the Message", 7);
            request.setChatId(this.chatId);
        }

    }
}
