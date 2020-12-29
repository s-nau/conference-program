package View.Account;

import Presenter.AttendeeController.AttEventController;
import Presenter.AttendeeController.AttMessageController;
import Presenter.AttendeeController.AttReqController;
import Presenter.Central.SubMenu;
import Presenter.EmployeeController.EmpEventController;
import Presenter.EmployeeController.EmpReqController;
import Presenter.OrganizerController.OrgEventController;
import Presenter.OrganizerController.OrgPersonController;
import Presenter.OrganizerController.OrgReqController;
import Presenter.PersonController.ContactController;
import Presenter.PersonController.MessageController;
import Presenter.PersonController.UserInfoController;
import Presenter.SpeakerController.SpeEventController;


public class AccountViewFactory {

    /**
     * Constructs a View based on the type of controller
     * @param controller a SubMenu controller
     */
    public AccountView construct(SubMenu controller) {
        AccountView view;
        if (controller instanceof ContactController) {
            view = new ContactView(controller);
        }
        else if (controller instanceof AttMessageController) {
            view = new AttMessageView(controller);
        }
        else if (controller instanceof AttEventController) {
            view = new AttEventView(controller);
        }
        else if (controller instanceof OrgEventController) {
            view = new OrgEventView(controller);
        }
        else if (controller instanceof OrgPersonController) {
            view = new OrgPersonView(controller);
        }
        else if (controller instanceof OrgReqController) {
            view = new OrgReqView(controller);
        }
        else if (controller instanceof MessageController) {
            view = new MessageView(controller);
        }
        else if (controller instanceof SpeEventController) {
            view = new SpeEventView(controller);
        }
        else if (controller instanceof EmpEventController) {
            view = new EmpEventView(controller);
        }
        else if (controller instanceof UserInfoController) {
            view = new UserInfoView(controller);
        }
        else if (controller instanceof EmpReqController) {
            view = new OrgReqView(controller);
        }
        else {
            view = new AttReqView(controller);
        }
        return view;
    }
}
