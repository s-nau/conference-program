package Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Employee extends Person {

    // array list of announcement chats: announcements from events
    protected ArrayList<String> anChatList = new ArrayList<>();

    // map of user request to status of the request: currently pending of request fulfilled
    private final Map<String, Boolean> requestIdToStatus;

    //protected ArrayList<String> anChatIds = new ArrayList<>();


    public Employee (String fullName, String username, String password, String email){
        super(fullName, username, password, email);
        this.typePerson = 4;
        this.requestIdToStatus = new HashMap<>();
    }

    /**
     *
     * @return map of requests vs boolean value determining whether request is pending or has been fulfilled
     */
    public Map<String, Boolean> getRequestIdToStatus() {
        return requestIdToStatus;
    }


    /**
     *
     * @param requestId of the request entity
     * @return the status of the request
     */
    public String getRequestStatus(String requestId){
        if (!requestIdToStatus.get(requestId)){
            return "Pending";
        }
        else{
            return "Fulfilled";
        }
    }

}

