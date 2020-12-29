package View.AccountHelpers;

import Presenter.Central.SubMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListDisplayView implements ActionListener {
    JButton returnButton;
    JLabel printedList;
    JFrame frame;
    JPanel contentPane;

    /**
     * A view that displays a bullet point list of Strings
     * @param title the title of the frame
     * @param options the array of Strings to be on the list
     */
    public ListDisplayView(String title, String[] options) {
        frame = new JFrame(title); // Create and set up the frame
        contentPane = new JPanel();// Create a content pane with a BoxLayout and empty borders
        contentPane.setPreferredSize(new Dimension(600,800));
        contentPane.setBackground(new Color(255, 255, 255));// Sets background colour to white
        //contentPane.setLayout(null);
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.weightx = GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.gridy = 0;
        contentPane.setLayout(layout);

        StringBuilder list = new StringBuilder();
        for (String option: options) {
            list.append("<li>");
            list.append(option);
            list.append("<br><br>");
        }

        JEditorPane editorPane = new JEditorPane("text/html", "<html>" + list.toString() + "<html/>");
        editorPane.setEditable(false);
        JScrollPane editorScrollPane = new JScrollPane(editorPane);
        editorScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setPreferredSize(new Dimension(400, 600));
        editorScrollPane.setMinimumSize(new Dimension(300, 100));
        editorScrollPane.setBackground(null);
        editorScrollPane.setBorder(null);
        layout.addLayoutComponent(editorScrollPane, c);
        contentPane.add(editorScrollPane);


        c.gridy = GridBagConstraints.BELOW_BASELINE + 1;
        returnButton = new JButton("return");
        returnButton.setBounds(10, 600, 80, 30);
        returnButton.setActionCommand("close");
        returnButton.addActionListener(this);
        returnButton.setToolTipText("click this button to return to previous screen");
        layout.addLayoutComponent(returnButton, c);
        contentPane.add(returnButton);

        frame.setContentPane(contentPane);// Add content pane to frame
        frame.pack();// Size and then display the frame.
        display();
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
        if (event.getActionCommand().equals("close")) {
            hide();
        }
    }
}
