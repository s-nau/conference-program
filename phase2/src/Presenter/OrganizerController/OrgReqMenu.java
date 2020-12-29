package Presenter.OrganizerController;

import Person.PersonManager;
import Presenter.AttendeeController.AttReqMenu;
import Presenter.Exceptions.InvalidChoiceException;
import Presenter.Exceptions.NoDataException;
import Request.RequestManager;

import java.util.ArrayList;

// Programmers:
// Description:
// Date Created:
// Date Modified: 11/12/2020

public class OrgReqMenu extends AttReqMenu {

    /**
     * constructor
     * @param reqM a request manager
     */
    public OrgReqMenu(RequestManager reqM, PersonManager perM, String currentUserID){
        super(reqM, perM, currentUserID);
    }

    /**
     * getter for the menu options
     * @return the menu options
     */
    @Override
    public String[] getMenuOptions() {
        return new String[]{"Make a request", "View a specific request", "View your requests", "Fulfill a request",
                "View all requests"};
    }

    /**
     * Title for fulfilling a request
     */
    public String fulfillRequestTitle() {
        return "--FULFILL A REQUEST--";
    }

    /**
     * Prints a prompt prompting the user to enter the id of a request to be filled
     */
    public String fulfillRequestPrompt() {
        return "Please enter the ID of the request you would like to fill";
    }

    /**
     * fulfills request for reqId
     * @param reqId String
     * @return "The request with id" + reqId + "has been fulfilled"
     */
    public String fulfillRequestConfirmed(String reqId){
        return "The request with id" + reqId + "has been fulfilled";
    }

    /**
     * notified who is handling request for reqId
     * @param reqId String
     * @return "The request with id" + reqId + "has been fulfilled"
     */
    public String handlingRequestPrompt(String reqId){
        StringBuilder users = new StringBuilder();
        for (String id: reqM.getHandlers(reqId)) {
            users.append(perM.getCurrentUsername(id));
            users.append(", ");
        }
        users.delete(users.length()-3, users.length());
        return "The request with id " + reqId + " is being taken care of by " + users;
    }

    /**
     * all the requests for this user
     * @return A list of the user's requests, as formatted strings
     */
    public String[] allRequests() throws InvalidChoiceException {
        try {
            ArrayList<String> requests = reqM.getRequestIDs();
            return requestsList(requests);
        } catch (NullPointerException e) {
            throw new NoDataException("request");
        }
    }


}
