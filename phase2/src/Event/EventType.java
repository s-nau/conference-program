package Event;

public enum EventType {
    TALK, WORKSHOP, PANEL, PARTY;

    public static String convertToString(EventType event){
        String eventType = event.toString();
        return eventType;
    }
}
