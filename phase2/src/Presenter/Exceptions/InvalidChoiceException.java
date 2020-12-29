package Presenter.Exceptions;

/**
 * Exception to be thrown for when the User inputs an option that's not within the system
 */
public class InvalidChoiceException extends Exception {
    protected String type;

    public InvalidChoiceException(String type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "No such " + type + " exists.";
    }

    public void printErrorMessage() {
        System.out.println(getMessage());
    }
}
