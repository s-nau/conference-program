package Presenter.Exceptions;


/**
 * Exception to be thrown for when the User inputs an option that's incorrectly formatted
 */
public class InvalidFormatException extends InvalidChoiceException {
    protected String formatError;

    public InvalidFormatException(String type, String formatError) {
        super(type);
        this.formatError = formatError;
    }

    @Override
    public String getMessage() {
            return "Invalid " + type + ". Please try again using " + formatError + ".";
            }

    public void printErrorMessage() {
            System.out.println(getMessage());
            }
}
