package Presenter.PersonController;

// Programmers: Cara McNeil,
// Description: Prints information pertaining to a user's contact information
// Date Created: 11/11/2020
// Date Modified: 11/11/2020

import Person.PersonManager;
import Presenter.Central.SubMenuPrinter;
import Presenter.Exceptions.NoDataException;

import java.util.ArrayList;

// Programmers:
// Description:
// Date Created:
// Date Modified: 02/12/2020

public class ContactMenu implements SubMenuPrinter {
    PersonManager personManager;
    String currentUserID;

    public ContactMenu(PersonManager personManager, String currentUserID) {
        this.personManager = personManager;
        this.currentUserID = currentUserID;
    }

    @Override
    public String getMenuTitle() {
        return "----- Contact Menu -----";
    }

    @Override
    public String[] getMenuOptions() {
        return new String[]{"View contact list", "Add a contact"};
    }

    /**
     * Prints a list of contacts
     */
    private String[] printContactList(ArrayList<String> contactList) {
        contactList.toArray();
        String[] clist = {};
        clist = contactList.toArray(clist);
        return clist;
    }

    /**
     * Prints a list of the user's contacts
     */
    public String[] getContactList() throws NoDataException{
        ArrayList<String> contactList = personManager.getContactList(currentUserID);
        if (contactList == null || contactList.size() == 0) {
            throw new NoDataException("contact");
        }
        return printContactList(contactList);
    }

    /**
     * Prints a title for the contact list
     */
    public String getContactListTitle() {
        return "---YOUR CONTACTS---";
    }

    /**
     * Prompts user to enter contact information
     */
    public String printAddContactPrompt() {
        return "Enter the username of the person you would like to add as a contact: ";
    }

    /**
     * Prints that a contact was successfully added
     */
    public String printContactAdded() {
        return "Contact has been successfully added.";
    }
}
