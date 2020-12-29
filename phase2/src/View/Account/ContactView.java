package View.Account;

import Presenter.Central.SubMenu;
import Presenter.Exceptions.InvalidChoiceException;
import Presenter.Exceptions.NoDataException;
import Presenter.PersonController.ContactController;
import Presenter.PersonController.ContactMenu;
import View.AccountHelpers.ListDisplayView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ContactView extends AccountView {
    ContactController controller;
    ContactMenu presenter;
    JLabel enterUsernameMsg, contactAdded;
    JButton submitButton;
    JTextField inputAddContact;
    ListDisplayView allContacts;

    /**
     * The view for users to see contact options.
     * @param controller ContactController for handling user input
     */
    public ContactView(SubMenu controller) {
        super(controller);
        this.controller = (ContactController) controller;
        this.presenter = (ContactMenu) controller.getPresenter();

        contentPane.setBackground(yellowBG);

        setupAddContact();
    }

    /**
     * Adds addContact components to contentPane
     */
    private void setupAddContact() {
        enterUsernameMsg = new JLabel(this.presenter.printAddContactPrompt());
        initializeObject(enterUsernameMsg);

        inputAddContact = new JTextField(20);
        initializeObject(inputAddContact);

        submitButton = newButton("submit");
        submitButton.setToolTipText("submit entered text");

        contactAdded = new JLabel(this.presenter.printContactAdded());
        initializeObject(contactAdded);
    }

    /**
     * Displays a list of the user's contacts
     */
    private void showViewContacts() {
        try {
            String[] myContacts = presenter.getContactList();
            allContacts = new ListDisplayView(presenter.getContactListTitle(), myContacts);
            showMainDropDownMenu();
        } catch (NoDataException e) {
            exceptionDialogBox(presenter.printException(e));
        }
    }

    /**
     * Shows components that prompt a user to add contact information
     */
    private void showAddContact() {
        enterUsernameMsg.setVisible(true);
        submitButton.setVisible(true);
        backButton.setVisible(true);
        inputAddContact.setVisible(true);
    }

    /**
     * Calls the Contact Controller to try to add a contact to the user's contact list
     */
    private void addContact() {
        enterUsernameMsg.setVisible(false);
        submitButton.setVisible(false);
        inputAddContact.setVisible(false);
        backButton.setVisible(false);

        String contactUsername = inputAddContact.getText();

        try {
            controller.addContact(contactUsername);
            contactAdded.setVisible(true);
            okayButton.setVisible(true);
            backButton.setVisible(false);
        } catch (InvalidChoiceException e) {
            JOptionPane.showConfirmDialog(null, presenter.exceptionTitle(), presenter.printException(e),
                    JOptionPane.DEFAULT_OPTION);
            showMainDropDownMenu();
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        super.actionPerformed(event);

        // [0] = View Contacts
        // [1] = Add Contact
        if (eventName.equals(menuOp[0])) {
            hideMainDropDownMenu();
            showViewContacts();
        }

        if (eventName.equals(menuOp[1])) {
            hideMainDropDownMenu();
            showAddContact();
        }

        if(eventName.equals("submit")) {
            addContact();
        }
    }
}
