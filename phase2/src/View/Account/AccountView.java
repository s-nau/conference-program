package View.Account;

import Presenter.Central.SubMenu;
import Presenter.Central.SubMenuPrinter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

/**
 * A view that is instantiated with a Controller and builds frame based on said Controller
 */
public abstract class AccountView extends Observable implements ActionListener {
    public JFrame frame = new JFrame();
    public JPanel contentPane = new JPanel();// Create a content pane with a BoxLayout and empty borders
    public JButton okayButton = newButton("okay");
    public JButton continueButton = newButton("continue");
    public JButton backButton = newButton("back");
    public JButton closeButton = newButton("save and close");
    JComboBox<String> dropDownMenu;
    public String eventName;
    protected SubMenu controller;
    protected SubMenuPrinter presenter;
    public final String[] menuOp;

    public static final Color whiteBG = new Color(255, 255, 200);
    public static final Color yellowBG = new Color(255, 200, 0);
    public static final Color pinkBG = new Color(255, 100, 200);
    public static final Color blueBG = new Color(100, 200, 232);
    public static final Color greenBG = new Color(100, 200, 100);

    /**
     * Abstract class; A view that is instantiated and run by the Account Class.
     * Initializes basic frame with dropDownMenu and navigation buttons.
     * @param controller the string container for this class
     */
    public AccountView(SubMenu controller) {
        this.controller = controller;
        this.presenter = controller.getPresenter();
        frame.setTitle(presenter.getMenuTitle()); // Create and set up the frame
        JFrame.setDefaultLookAndFeelDecorated(true);

        menuOp = presenter.getMenuOptions();

        contentPane.setBorder(BorderFactory.createEmptyBorder(300, 300, 300, 300));//Sets size of frame

        continueButton.setToolTipText("click this button to navigate to the chosen menu");
        backButton.setToolTipText("click this button to go back to the previous menu");

        makeDropDownMenu(presenter);
        initializeObject(closeButton);

        frame.setContentPane(contentPane);
        frame.pack();
        frame.setVisible(true);
        showMainDropDownMenu();
    }

    /**
     * Hides every component stored in contentPane
     */
    public void hideAll() {
        for (Component item: contentPane.getComponents()) {
            item.setVisible(false);
        }
    }

    /**
     * Shows dropDownMenu
     */
    public void showMainDropDownMenu() {
        hideAll();
        dropDownMenu.setVisible(true);
        continueButton.setVisible(true);
        closeButton.setVisible(true);
        okayButton.setText("okay");
        okayButton.setActionCommand("okay");
    }

    /**
     * Hides dropDownMenu
     */
    public void hideMainDropDownMenu() {
        dropDownMenu.setVisible(false);
        continueButton.setVisible(false);
        closeButton.setVisible(false);
    }

    /**
     * Builds drop down menu
     * @param presenter the view's presenter menu class
     */
    public void makeDropDownMenu(SubMenuPrinter presenter) {
        dropDownMenu = new JComboBox<>(presenter.getMenuOptions());// Generates dropdown menu
        dropDownMenu.setAlignmentX(JComboBox.LEFT_ALIGNMENT);
        dropDownMenu.setSelectedIndex(0);
        dropDownMenu.addActionListener(this);
        dropDownMenu.setToolTipText("select a menu to navigate to");
        initializeObject(dropDownMenu);
    }

    /**
     * Creates a new button component with an ActionListener
     * @param title the text and action listener of the button
     * @return a button with text and action listener 'title'
     */
    public JButton newButton(String title) {
        JButton newButton = new JButton(title);
        newButton.setLocation(0, 0);
        newButton.setActionCommand(title);
        newButton.addActionListener(this);
        initializeObject(newButton);
        return newButton;
    }

    /**
     * Opens a JOptionPane box that displays the exception message
     * @param exceptionText The text the box should display
     */
    public void exceptionDialogBox(String exceptionText) {
        JOptionPane.showConfirmDialog(null, exceptionText, presenter.exceptionTitle(),
                JOptionPane.DEFAULT_OPTION);
        showMainDropDownMenu();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        eventName = event.getActionCommand();
        assert eventName != null;

        if(eventName.equals(backButton.getActionCommand()) ||
                eventName.equals(okayButton.getActionCommand())) {
            showMainDropDownMenu();
        }

        if (eventName.equals("okay")) {
            showMainDropDownMenu();
        }

        if (eventName.equals(continueButton.getActionCommand())) {
            eventName = (String)dropDownMenu.getSelectedItem();
        }

        assert eventName != null;
        if (eventName.equals(closeButton.getActionCommand())) {
            notifyObservers();
            frame.setVisible(false);
        }
    }

    protected void initializeObject(JComponent object) {
        contentPane.add(object);
        object.setVisible(false);
    }

    /**
     * Returns the controller used by this AccountView
     * @return The controller
     */
    public SubMenu unpack() {
        return controller;
    }
}
