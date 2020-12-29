package Presenter.PersonController;

import Presenter.Central.SubMenu;
import Presenter.Central.SubMenuPrinter;
import Presenter.Exceptions.InvalidChoiceException;
import Presenter.Exceptions.InvalidFormatException;
import Presenter.Exceptions.OverwritingException;

public class UserInfoController extends SubMenu {
    String currentUserID;
    UserInfoMenu presenter;

    public UserInfoController(SubMenu subMenu, String currentUserID) {
        super(subMenu);
        this.currentUserID = currentUserID;
        presenter = new UserInfoMenu(currentUserID, personManager);

    }

    @Override
    public SubMenuPrinter getPresenter() {
        return presenter;
    }

    private boolean verifyPword(String pword) {
        return personManager.confirmPassword(personManager.getCurrentUsername(currentUserID), pword);
    }

    public String changePassword(String newPword, String oldPword) throws InvalidChoiceException {
        if (verifyPword(oldPword)) {
            personManager.changePassword(currentUserID, newPword);
            return presenter.printChangeSuccessful("password");
        } else {
            throw new InvalidChoiceException("password");
        }
    }

    public String changeUsername(String newUsername, String pword) throws InvalidChoiceException {
        if (verifyPword(pword)) {
            if (personManager.changeUsername(currentUserID, newUsername)) {
                return presenter.printChangeSuccessful("username");
            } else {
                throw new OverwritingException("username");
            }
        } else {
            throw new InvalidChoiceException("username");
        }
    }

    public String changeEmail(String newEmail, String pword) throws InvalidChoiceException {
        if(!newEmail.matches("[a-z,A-Z]*@[a-z,A-Z]*[.][a-z]*")) {
            throw new InvalidFormatException("email", "an email of the form [address]@[domain].[something]");
        }
        if (verifyPword(pword)) {
            personManager.changeEmail(currentUserID,newEmail);
            return presenter.printChangeSuccessful("email");
        } else {
            throw new InvalidChoiceException("email");
        }
    }
}
