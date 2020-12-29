package Presenter.EmployeeController;

import Presenter.Central.SubMenu;
import Presenter.Exceptions.InvalidChoiceException;
import Presenter.OrganizerController.OrgReqController;
import Presenter.OrganizerController.OrgReqMenu;
import Request.RequestManager;

public class EmpReqController extends OrgReqController {
    protected OrgReqMenu presenter;
    private String currentUserID;

    public EmpReqController(SubMenu subMenu, String currentUserID) {
        super(subMenu, currentUserID);
        presenter = new OrgReqMenu(requestManager, personManager, currentUserID);
        this.currentUserID = currentUserID;
    }

//    /**
//     * allows user to change/modify the request that is still pending
//     * @param reqUserId the user who is changing the request
//     * @param reqId of the request content to be brought into a string
//     * @return true
//     */
//    public boolean modifyRequest(String reqUserId, String reqId) {
//        RequestEntity req = getRequestEntity(reqId);
//        if(!req.getFulfilled()) {
//            String oldRequest = getStringOfRequest(reqId);
//            createRequest(oldRequest, reqUserId);
//        }
//        return true;
//
//    }

    @Override
    public String fulfillRequest(String reqId) throws InvalidChoiceException {
        if (requestManager.requestExists(reqId)) {
            requestManager.updateEntity(reqId, currentUserID);
            return presenter.fulfillRequestConfirmed(reqId);
        } else {
            throw new InvalidChoiceException("request");
        }
    }
}
