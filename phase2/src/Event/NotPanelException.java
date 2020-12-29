package Event;

public class NotPanelException extends Exception {

    @Override
    public String getMessage() {
        return "A speaker cannot be added/removed to/from an event which is not a panel!";
    }

}
