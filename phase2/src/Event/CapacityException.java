package Event;

public class CapacityException extends Exception {

    @Override
    public String getMessage() {
        return "That event is over capacity!";
    }

}
