package Request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;


// This class will allow attendees, speakers and employees (at the conference) to make requests to the
// organizers (sent to the request board) to be either tagged or addressed. The request will be made via
// a message object.
// While attendees and speakers can be thought to be making requests, employees can requests and recommendations

// the parameters will be

public class RequestEntity implements Serializable {
    private String requestId;
    private String requestContent;
    private boolean fulfilled;
    private final String requestingUserId;
    private ArrayList<String> employeeHandlingRequest;
    private String eventsConcerned;

    /*  Helper class for making this class as an observable  */

    /**
     * contructor for request entity
     * @param requestContent string representing hte request content
     * @param requestingUserId String representing the requesting user Id.
     */

    public RequestEntity(String requestContent, String requestingUserId) {
        this.requestContent = requestContent;
        this.requestingUserId = requestingUserId;
        this.employeeHandlingRequest = new ArrayList<>();
        this.fulfilled = false;
        this.requestId = UUID.randomUUID().toString();

    }


    /**
     * getter for fulfilled status
     * @return True if fulfilled, False otherwise
     */
        public boolean getFulfilled () {
            return this.fulfilled;
        }

    /**
     * Get the list of employees handling a request
     * @return That list of employees
     */

    public ArrayList getEmployeeHandlingRequest() {
            return this.employeeHandlingRequest;
        }

    /**
     * Add an employee to the list of those handling a request
      * @param userID The ID of the employee
     */
    public void addEmployeeHandler(String userID) {
        employeeHandlingRequest.add(userID);
    }

    /**
     * getter for the requesting user id
     * @return String representing the requesting user Id
     */
    public String getRequestingUserId () {
            return requestingUserId;
        }

        public String getRequestID(){
        return requestId;
        }

    /**
     * getter for request content
     * @return string representing the request content
     */
    public String getRequestContent () {
            return requestContent;
        }

    /**
     * getter for events concerned
     * @return String
     */
    public String getEventsConcerned () {
            return this.eventsConcerned;
        }

    /**
     * sets this.fulfilled to true, and notifies observers
     */
    public void setFulfilled () {
            this.fulfilled = true;
        }




    }

