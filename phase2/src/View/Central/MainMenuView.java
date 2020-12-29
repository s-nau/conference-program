package View.Central;

// Contributors: Cara McNeil, Sarah Kronenfeld
// Last edit: December 2 2020

// Architecture Level - UI



import Presenter.Central.ConventionPlanningSystem;
import Presenter.Central.SubMenu;
import Presenter.PersonController.PersonController;
import View.Account.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuView implements ActionListener {
    JFrame frame;
    JComboBox<String> dropDownMenu;
    JPanel contentPane;
    JLabel introMessage, accountChoiceMessage, savedMessage;
    JButton startButton, continueButton, submitAccountChoiceButton, okayButton, saveButton;
    ConventionPlanningSystem cps;
    Account currAccount;

    /**
     * The view that displays information for the user to choose how to login, and calls the Account view if login is
     * successful
     */
    public MainMenuView() {
        cps = new ConventionPlanningSystem();
    }

    /**
     * Updates cps to a new controller with an updated Submenu
     * @param menu an menu with updated entity managers
     */
    public void update(SubMenu menu) {
        cps = new ConventionPlanningSystem(menu);
    }

    private void setUpIntro() {
        introMessage = new JLabel(cps.getIntroMessage());
        contentPane.add(introMessage);
        introMessage.setVisible(false);

        continueButton = newButton("continue");
        contentPane.add(continueButton);
        continueButton.setVisible(false);
    }

    private void setUpSaveScreen() {
        savedMessage = new JLabel(cps.getSaveMessage());
        contentPane.add(savedMessage);
        savedMessage.setVisible(false);

        okayButton = newButton("okay");
        contentPane.add(okayButton);
        okayButton.setVisible(false);
    }

    private void setUpAccountChoice() {
        dropDownMenu = new JComboBox<>(cps.getAccountOptions());// Generates dropdown menu
        dropDownMenu.setAlignmentX(JComboBox.LEFT_ALIGNMENT);
        dropDownMenu.setSelectedIndex(0);
        dropDownMenu.addActionListener(this);
        dropDownMenu.setToolTipText("select a type of account to login to");
        contentPane.add(dropDownMenu);
        dropDownMenu.setVisible(false);

        accountChoiceMessage = new JLabel(cps.getChooseAccountText());
        contentPane.add(accountChoiceMessage);
        accountChoiceMessage.setVisible(false);

        submitAccountChoiceButton = newButton("submit account choice");
        submitAccountChoiceButton.setToolTipText("confirm your selection");
        contentPane.add(submitAccountChoiceButton);
        submitAccountChoiceButton.setVisible(false);

        saveButton = newButton("save + exit");
        saveButton.setToolTipText("save your progress and exit the program safely");
        contentPane.add(saveButton);
        saveButton.setVisible(false);
    }


    /**
     * Sets up the frame, initializes panel components
     */
    public void run () {
        frame = new JFrame();// Create and set up the frame
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JPanel();// Create a content pane with a BoxLayout and empty borders
        contentPane.setBorder(BorderFactory.createEmptyBorder(300, 300, 300, 300));//Sets size of frame
        contentPane.setBackground(new Color(255, 255, 255));// Sets background colour to white
        contentPane.setLayout(new FlowLayout());

        startButton = newButton("start");
        contentPane.add(startButton);

        setUpIntro();
        setUpSaveScreen();
        setUpAccountChoice();

        frame.setContentPane(contentPane);// Add content pane to frame
        frame.pack();// Size and then display the frame.
        frame.setVisible(true);
    }

    /**
     * Hides every component stored in contentPane
     */
    private void hideAll() {
        for (Component item: contentPane.getComponents()) {
            item.setVisible(false);
        }
    }


    /**
     * Method that runs an intro for a user
     */
    private void intro() {
        hideAll();

        frame.setVisible(true);
        frame.setTitle(cps.getIntroTitle());

        introMessage.setVisible(true);
        continueButton.setVisible(true);
    }


    /**
     * Method to get a choice of account from the user
     */
    private void accountChoice() {
        continueButton.setVisible(false);
        introMessage.setVisible(false);

        accountChoiceMessage.setVisible(true);
        dropDownMenu.setVisible(true);
        submitAccountChoiceButton.setVisible(true);
        saveButton.setVisible(true);

        frame.setTitle(cps.getChooseAccountTitle());
    }

    /**
     * Method to log into and open a user account
     */
    private void openAccount() {
        submitAccountChoiceButton.setVisible(false);
        saveButton.setVisible(false);
        accountChoiceMessage.setVisible(false);

        String accountChoice = (String)dropDownMenu.getSelectedItem();

        assert accountChoice != null;
        PersonController controller = cps.getController(accountChoice);
        frame.setVisible(false);
        controller = login(controller);
        if (controller == null) {
            frame.setVisible(true);
            accountChoice();
        }
        else {
            currAccount = new Account(controller);
            currAccount.runFrom(this);
        }
    }

    private void saveInfo() {
        accountChoiceMessage.setVisible(false);
        dropDownMenu.setVisible(false);
        saveButton.setVisible(false);
        submitAccountChoiceButton.setVisible(false);

        cps.save();

        frame.setTitle(saveButton.getText());
        savedMessage.setVisible(true);
        okayButton.setVisible(true);
    }

    /**
     * Method to run a login program for the user
     * @param controller The current personcontroller
     * @return that controller, updated
     */
    private PersonController login(PersonController controller) {
        LoginView view = new LoginView(controller.getLogin());
        String id = view.run();
        if (!(id.equals("0"))) {
            controller.logIn(id);
            return controller;
        }
        else {
            return null;
        }
    }


    /**
     * Detects changes to component parts of the MainMenuView
     * @param event the event that's happened
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        String eventName = event.getActionCommand();

        if (eventName.equals("start")) {
            intro();
        }

        if (eventName.equals("continue")) {
            accountChoice();
        }

        if (eventName.equals("submit account choice")) {
            openAccount();
        }

        if (eventName.equals("okay")) {
            intro();
        }

        if (eventName.equals("save + exit")) {
            saveInfo();
        }

        if (eventName.equals("logout")) {
            update(currAccount.returnController());
            saveInfo();
            intro();
        }

    }

    private JButton newButton(String title) {
        JButton newButton = new JButton(title);
        newButton.setLocation(0, 0);
        newButton.setActionCommand(title);
        newButton.addActionListener(this);
        return newButton;
    }
}
