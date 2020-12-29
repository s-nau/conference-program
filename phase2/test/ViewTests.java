import View.AccountHelpers.ListDisplayView;

public class ViewTests {

    public static void main(String[] args) {
        String[] items = {"hi", "hey", "yo"};
        ListDisplayView list = new ListDisplayView("title", items);
    }

    /*
    static String testOptionBox(iViewPane view) {
        String[] args = {"Molly", "Also Molly", "Still Molly", "Molly, of Course"};
        try {
            return view.getStringInput("Test", "Choose your favourite cat", args);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    static String testIntegerInput(iViewPane view) {
        try {
            return view.getIntegerInput("Test", "Choose an integer").toString();
        } catch (InvalidChoiceException e) {
            return "Exception thrown!";
        }
    }

    static String testStringInput(iViewPane view) {
        return view.getStringInput("Test", "What's your name?");
    }

    static void testMessagePrint(iViewPane view) {
        view.printMessage("Test", "WOAH! A TEST!");
    }*/
}
