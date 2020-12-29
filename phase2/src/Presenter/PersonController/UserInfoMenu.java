package Presenter.PersonController;

import Person.Person;
import Person.PersonManager;
import Presenter.Central.SubMenuPrinter;

public class UserInfoMenu implements SubMenuPrinter {
    PersonManager personManager;
    String currentUserId;

    public UserInfoMenu(String currentUserId, PersonManager personManager) {
        this.personManager = personManager;
        this.currentUserId = currentUserId;
    }

    /**
     * Prints a title that says the user is logging in
     */
    @Override
    public String getMenuTitle() {
        return personManager.getCurrentUsername(currentUserId) + "'s account settings";
    }

    /**
     * Prints the options for this menu.
     */
    @Override
    public String[] getMenuOptions() {
        String[] options = {"Change your username", "Change your password", "Change your email"};
        return options;
    }

    /**
     * Prints a title that says the user is changing their password
     */
    public String changePwordTitle() {
        return "Changing password";
    }

    /**
     * Prints a title that says the user is changing their username
     */
    public String changeUsernameTitle() {
        return "Changing username";
    }

    /**
     * Prints a title that says the user is changing their email
     */
    public String changeEmailTitle() {
        return "Changing email";
    }

    /**
     * Prompts user to enter username
     */
    public String printNewUsernamePrompt() {
        return "Please enter new username: ";
    }

    /**
     * Prompts user to enter password
     */
    public String printNewPasswordPrompt() {
        return "Please enter new password: ";
    }

    /**
     * Prompts user to enter password
     */
    public String printNewEmailPrompt() {
        return "Please enter new email: ";
    }

    /**
     * Prompts user to enter password
     */
    public String printEnterPassword() {
        return "Please enter your current password: ";
    }

    /**
     * Returns a written confirmation that Login was successful
     */
    public String printChangeSuccessful(String field) {
        return "Congratulations! Your " + field + " has been successfully changed";
    }
}
