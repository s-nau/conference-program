package Presenter.PersonController;

// Programmer: Cara McNeil
// Description: All the methods that take user input in the Contact Menu
// Date Created: 01/11/2020
// Date Modified: 14/11/2020

import Presenter.Central.SubMenu;
import Presenter.Central.SubMenuPrinter;
import Presenter.Exceptions.InvalidChoiceException;

import java.util.ArrayList;

public class ContactController extends SubMenu {

    private final String currentUserID;
    private final ContactMenu presenter;

    public ContactController(SubMenu subMenu, String currentUserID) {
        super(subMenu);
        this.currentUserID = currentUserID;
        presenter = new ContactMenu(personManager, currentUserID);
    }

    /**
     * Add a contact to the current user's contactList
     * @param contactUsername the username of the contact the current user wants to add to their contactList
     * @return a String confirming the contact was added if successful; otherwise return null
     */
    public String addContact(String contactUsername) throws InvalidChoiceException{
        String contactID = personManager.getCurrentUserID(contactUsername);
        if (contactID == null) {
            throw new InvalidChoiceException("user");
        }
        boolean a = personManager.addContactToPerson(currentUserID, contactID);
        boolean b = personManager.addContactToPerson(contactID, currentUserID);

        if(a && b) {
            return presenter.printContactAdded();
        }
     return null;
    }

    @Override
    public ContactMenu getPresenter() {
        return this.presenter;
    }
}
