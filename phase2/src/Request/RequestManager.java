package Request;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestManager implements Serializable {
    private final ArrayList<RequestEntity> requestsList;
    private final Map<String, RequestEntity> idToRequest;

    /**
     * constructor for the request manager
     */
    public RequestManager() {
        this.requestsList = new ArrayList<>();
        idToRequest = new HashMap<>();

    }

    public void addRequest(RequestEntity req) {
        requestsList.add(req);
    }

    public void removeRequest(RequestEntity req) {
        requestsList.remove(req);
    }

    /**
     * creates a request
     *
     * @param reqUserId  the user who is making the request
     * @param reqContent string fro the content of the request
     * @return true
     */
    public boolean createRequest(String reqUserId, String reqContent) {
        RequestEntity req = new RequestEntity(reqContent, reqUserId);
        updateMap(req.getRequestID(), req);
        addRequest(req);
        return true;
    }

    /**
     * allows user to change/modify the request that is still pending
     *
     * @param reqUserId the user who is changing the request
     * @param reqId     of the request content to be brought into a string
     * @return true
     */
    public boolean modifyRequest(String reqUserId, String reqId) {
        RequestEntity req = getRequestEntity(reqId);
        if (!req.getFulfilled()) {
            //String oldRequest = getStringOfRequest(reqId);
            //createRequest(oldRequest, reqUserId);
        }
        return true;

    }

    private void handleRequest(String reqID, String employeeID, String employeeUsername) {
        RequestEntity request = getRequestEntity(reqID);
        Boolean handlingRequest = true;
        request.getEmployeeHandlingRequest().add(0, reqID);
        request.getEmployeeHandlingRequest().add(1, handlingRequest);
        request.getEmployeeHandlingRequest().add(2, employeeID);
        request.getEmployeeHandlingRequest().add(3, employeeUsername);

    }

    private void updateMap(String str, RequestEntity req) {
        idToRequest.put(str, req);
    }

    public RequestEntity getRequestEntity(String reqId) {
        RequestEntity rq = idToRequest.get(reqId);
        return rq;
    }

    protected ArrayList<RequestEntity> getAllRequests() {
        return requestsList;
    }

    /**
     * Getter for the content of this request
     * @param reqID The request's ID
     * @return The request's content
     */
    public String getContent(String reqID) {
        RequestEntity rq = getRequestEntity(reqID);
        return rq.getRequestContent();
    }

    /**
     * Getter for the ID of the person who made this request
     * @param reqID The request's ID
     * @return The userID
     */
    public String getRequesterID(String reqID) {
        return getRequestEntity(reqID).getRequestingUserId();
    }

    /**
     * Getter for the state of the request
     * @param reqID The request's ID
     * @return True if it's fulfilled; False if not
     */
    public boolean getRequestFulfilled(String reqID) {
        return getRequestEntity(reqID).getFulfilled();
    }

    /**
     * Returns the list of employees handling this request
     * @param reqID The request id
     * @return An arraylist of the employees handling the request
     */
    public ArrayList<String> getHandlers(String reqID) {
        return getRequestEntity(reqID).getEmployeeHandlingRequest();
    }

    /**
     * returns all requests made by a particular user
     * @param userID The user's ID
     * @return an arraylist of the user's requests
     */
    public ArrayList<String> getRequestsByUser(String userID) throws NullPointerException {
        ArrayList<String> userRequestIDs = new ArrayList<>();
        for (RequestEntity request : requestsList) {
            if (request.getRequestingUserId().equals(userID)) {
                userRequestIDs.add(request.getRequestID());
            }

        }
        return userRequestIDs;
    }

    /**
     * returns all requests
     * @return an arraylist of the IDs of the convention's requests
     */
    public ArrayList<String> getRequestIDs() throws NullPointerException {
        ArrayList<String> userRequestIDs = new ArrayList<>();
        for (RequestEntity request : requestsList) {
            userRequestIDs.add(request.getRequestID());
        }
        return userRequestIDs;
    }


    /**
     * to update a request when it is filled
     * @param reqId string for request id
     */
    public void updateEntity(String reqId){
        RequestEntity req = getRequestEntity(reqId);
        req.setFulfilled();
    }

    /**
     * to update a request when it is filled
     * @param reqId string for request id
     */
    public void updateEntity(String reqId, String userID){
        RequestEntity req = getRequestEntity(reqId);
        req.setFulfilled();
        req.addEmployeeHandler(userID);
    }

    /**
     * way to get all the requesting user id's
     * @return ArrayList of requesting user id's
     */
    public ArrayList<String> getAllRequestUserIds(){
        ArrayList<String> lst = new ArrayList<>();
        for(RequestEntity req: getAllRequests()){
            lst.add(req.getRequestingUserId());
        }
        return lst;
    }

    /**
     * lets you know if a request with reqId exists
     * @param reqID String
     * @return true iff the requests sexists
     */
    public boolean requestExists(String reqID){
        return idToRequest.containsKey(reqID);
    }

}




