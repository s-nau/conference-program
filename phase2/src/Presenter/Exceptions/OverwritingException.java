package Presenter.Exceptions;

/**
 * Exception to be thrown for when the User tries to create an object that's already within the system
 */
public class OverwritingException extends InvalidChoiceException {

    public OverwritingException(String type) {
        super(type);
    }

    @Override
    public String getMessage() {
        return "That " + type + " already exists.";
    }

    public void printErrorMessage() {
        System.out.println(getMessage());
    }

}
