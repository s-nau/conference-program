package Presenter.AttendeeController;

import Person.PersonManager;
import Presenter.Central.SubMenuPrinter;
import Presenter.Exceptions.InvalidChoiceException;
import Presenter.Exceptions.NoDataException;
import Request.RequestManager;

import java.util.ArrayList;

public class AttReqMenu implements SubMenuPrinter {
    protected RequestManager reqM;
    protected PersonManager perM;
    protected String currentUserID;

    /**
     * constructor for request menu parent
     * @param reqM request manager object
     */
    public AttReqMenu(RequestManager reqM, PersonManager perM, String currentUserID){
        this.reqM = reqM;
        this.perM = perM;
        this.currentUserID = currentUserID;
    }

    /**
     * getter for the menu title
     * @return String
     */
    @Override
    public String getMenuTitle() {
        return "----- Request Menu -----";
    }

    /**
     * allows you to see a format request for request with reqid
     * @param reqId String
     * @return String of formatted request
     */
    public String seeRequest(String reqId) throws InvalidChoiceException {
        if (reqM.requestExists(reqId)) {
            return formatRequestString(reqId);
        } else {
            throw new InvalidChoiceException("request");
        }
    }

    public String viewRequestTitle() {
        return "-- VIEW REQUESTS --";
    }

    public String makeRequestTitle() {
        return "-- MAKE A REQUEST --";
    }

    /**
     * all the requests for this user
     * @return A list of the user's requests, as formatted strings
     */
    public String[] myRequests() throws InvalidChoiceException{
        try {
            ArrayList<String> requests = reqM.getRequestsByUser(currentUserID);
            return requestsList(requests);
        } catch (NullPointerException e) {
            throw new NoDataException("request");
        }
    }

    /**
     * Prints out a list of requests in proper format
     * @return the list of requests
     */
    public String[] requestsList(ArrayList<String> requests) throws InvalidChoiceException{
        try {
            String[] r = new String[requests.size()];
            if (requests != null) {
                for (int i = 0; i < requests.size(); i++) {
                    r[i] = formatRequestString(requests.get(i));
                }
                return r;
            } else {
                throw new NoDataException("such request");
            }
        } catch (NullPointerException e) {
            throw new NoDataException("request");
        }
    }


    /**
     * prompt for request content
     */
    public String makeRequestPrompt(){
        return "Please enter the content of the Request.";
    }


    /**
     * prompt the get request Id for a request
     */
    public String seeRequestPrompt() {
        return "Please enter the id of the request you would like to view";
    }


    public String specificRequestTitle(String reqId) {
        return "This is the request with id" + reqId;
    }

    private String formatPersonList(ArrayList<String> persons) {
        StringBuilder s = new StringBuilder();
        if (persons != null) {
            for (String person: persons) {
                s.append(perM.getCurrentUsername(person));
                s.append(", ");
            }
            s.delete(persons.size()-3, persons.size());
            return s.toString();
        }
        else {
            return "";
        }
    }



    /**
     * To string method for request entity
     * comes in the format of requestId     requestContent      pending/fulfilled       requestingUserID
     * @return string
     */
    private String formatRequestString(String reqID) {
        StringBuilder sb = new StringBuilder();
        sb.append(reqID);
        sb.append("\n Request: ");
        sb.append(reqM.getContent(reqID));
        sb.append("\n Requested by: ");
        sb.append(perM.getCurrentUsername(reqM.getRequesterID(reqID)));
        sb.append("\n Status: ");
        if(!reqM.getRequestFulfilled(reqID)){
            sb.append("Pending");
        }
        else{
            sb.append("Fulfilled");
        }
        //sb.append("\t");
        //sb.append("Current handlers: ");
        //sb.append(formatPersonList(reqM.getHandlers(reqID)));
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Prints dialog for the creation of a request
     */
    public String printRequestCreated() {
        return "Your request has been submitted.";
    }

    /**
     * gets the manu options
     * @return the menu options
     */
    @Override
    public String[] getMenuOptions() {
        return new String[]{"Make a request", "View information about a request", "See all your requests"
                /*, "Modify a request"*/};
    }


}
