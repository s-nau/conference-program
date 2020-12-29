package Presenter.AttendeeController;

import Presenter.Central.SubMenu;
import Presenter.Central.SubMenuPrinter;
import Request.RequestManager;

public class AttReqController extends SubMenu {
    protected AttReqMenu presenter;
    private String currentUserID;

    /**
     * constructor for AttReqController
     * @param subMenu SubMenu
     * @param currentUserID string
     */
    public AttReqController(SubMenu subMenu, String currentUserID) {
        super(subMenu);
        this.presenter = new AttReqMenu(requestManager, personManager, currentUserID);
        this.currentUserID = currentUserID;
    }

    public void createRequest(String content) {
        requestManager.createRequest(currentUserID, content);
    }

    /**
     * getter for the presenter
     * @return this.presenter (AttReqMenu)
     */
    @Override
    public SubMenuPrinter getPresenter() {
        return this.presenter;
    }
}




