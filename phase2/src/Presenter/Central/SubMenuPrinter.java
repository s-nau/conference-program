package Presenter.Central;

public interface SubMenuPrinter {

    /**
     * Getter for menu option text
     * @return The options for this menu, in the form of an array
     */
    String[] getMenuOptions();


    /**
     * Getter for menu title test
     * @return The title for this menu, in the form of a string
     */
    String getMenuTitle();

    /**
     * Writes out a default title for any exception message
     * @return The expression, in printed form
     */
    default String exceptionTitle() {
        return "Whoops!";
    }

    /**
     * Writes out an Exception thrown by the program to the user
     * @param e The exception
     * @return The expression, in printed form
     */
    default String printException(Exception e) {
        return "That didn't work.\n" + e.getMessage() + "\nPlease try again!\n\n";
    }
}
