package Presenter.Central;

// Programmer: Cara McNeil, Sarah Kronenfeld
// Description: Classes that the Main Menu refers to
// Date Created: 01/11/2020
// Date Modified: 29/11/2020

import Event.EventManager;
import Event.RoomManager;
import Message.ChatManager;
import Message.MessageManager;
import Person.PersonManager;
import Request.RequestManager;

public abstract class SubMenu {

    protected RoomManager roomManager;
    protected EventManager eventManager;
    protected PersonManager personManager;
    protected MessageManager messageManager;
    protected ChatManager chatManager;
    protected RequestManager requestManager;

    // removed AdminManager from the class. Administrator is no longer a user in the system.

    public SubMenu(RoomManager roomManager, EventManager eventManager, PersonManager personManager,
                   MessageManager messageManager, ChatManager chatManager, RequestManager requestManager) {
        this.roomManager = roomManager;
        this.eventManager = eventManager;
        this.personManager = personManager;
        this.messageManager = messageManager;
        this.chatManager = chatManager;
        this.requestManager = requestManager;
    }

    public SubMenu(SubMenu otherMenu) {
        update(otherMenu);
    }


    public void update(SubMenu otherMenu) {
        this.roomManager = otherMenu.roomManager;
        this.eventManager = otherMenu.eventManager;
        this.personManager = otherMenu.personManager;
        this.messageManager = otherMenu.messageManager;
        this.chatManager = otherMenu.chatManager;
        this.requestManager = otherMenu.requestManager;
    }

    public ConventionSaver returnData() {
        return new ConventionSaver(roomManager, eventManager, messageManager, chatManager, requestManager,
                personManager);
    }

    public abstract SubMenuPrinter getPresenter();

}
