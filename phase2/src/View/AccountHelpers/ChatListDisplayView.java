package View.AccountHelpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ChatListDisplayView extends ListDisplayView{

    JButton checkButton;
    JButton archiveButton;
    JButton exitButton;
    JButton submitButton;
    JDialog chatSerialNumber;
    JPanel dialogPanel;
    Container container;
    InputRequestView request;

    public ChatListDisplayView(String title, String[] options) {
        super(title, options);
        contentPane.setBackground(new Color(121, 193, 154));
        checkButton = new JButton("Check");
        checkButton.setBounds(100, 600, 80, 30);
        checkButton.setActionCommand("checkMessages");
        checkButton.addActionListener(this);
        checkButton.setToolTipText("click this button to Exit and Delete Chat");
        contentPane.add(checkButton);
        checkButton.setVisible(true);

        archiveButton = new JButton("Archive");
        archiveButton.setBounds(200, 600, 80, 30);
        archiveButton.setActionCommand("Archive");
        archiveButton.addActionListener(this);
        archiveButton.setToolTipText("click this button to Exit and Delete Chat");
        contentPane.add(archiveButton);
        archiveButton.setVisible(true);

        exitButton = new JButton("Delete");
        exitButton.setBounds(300, 600, 80, 30);
        exitButton.setActionCommand("exitChat");
        exitButton.addActionListener(this);
        exitButton.setToolTipText("click this button to Exit and Delete Chat");
        contentPane.add(exitButton);
        exitButton.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("close")) {
            hide();
        }
        if (event.getActionCommand().equals("checkMessages")) {
            request = new InputRequestView("Input the Serial Number of the Chat", 1);
        }
        if (event.getActionCommand().equals("Archive")) {
            request = new InputRequestView("Input the Serial Number of the Chat", 2);
        }
        if (event.getActionCommand().equals("exitChat")) {
            request = new InputRequestView("Input the Serial Number of the Chat", 3);
        }

    }
}
