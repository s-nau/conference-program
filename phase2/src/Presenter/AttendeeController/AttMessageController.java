package Presenter.AttendeeController;

// Programmer: Ran Yi, Sarah Kronenfeld, Karyn Komatsu
// Description: For current AttendeeController (or OrganizerController) to view chat and message, create chat and send message.
// Date Modified: 29/11/2020


import Presenter.Central.SubMenu;
import Presenter.Exceptions.InvalidChoiceException;
import Presenter.Exceptions.InvalidFormatException;
import Presenter.Exceptions.NoDataException;
import Presenter.Exceptions.OverwritingException;
import Presenter.PersonController.MessageController;
import Person.AttendeeManager;

import java.util.ArrayList;

public class AttMessageController extends MessageController {
    private AttendeeManager attendeeManager;
    private AttMessageMenu presenter;

    public AttMessageController(SubMenu subMenu, String currentUserID, AttendeeManager attendeeManager) {
        super(subMenu, currentUserID);
        this.attendeeManager = attendeeManager;
        presenter = new AttMessageMenu(attendeeManager, messageManager, chatManager, eventManager, currentUserID);
    }

    public AttMessageMenu getAnChatPresenter() {
        return new AnnouncementMessageMenu(personManager, messageManager, chatManager, eventManager, currentUserID);
    }

    // Option 8

    /**
     * Creates a new Chat
     * @param username
     * @param chatName Name of the Chat being created
     */
    /*private String createChatChoice(String username, String chatName) {
        try {
            String chatID = createChat(username, chatName);
            if(chatID!=null){return presenter.printChatCreated(chatID);}
        } catch (InvalidChoiceException e) {
            return presenter.printChatNotCreated(e);
        }
    }*/


    /**
     * Creates new Chat if contact is on contactList
     * @param contactUsername the username of the contact the current user wants create a Chat with
     * @param chatName Name of the Chat being created
     * @return A message that contains the chat ID if the chat was created or already exists;
     * an error message, otherwise
     */

    public String createChat(String contactUsername, String chatName) {
        String contactID = attendeeManager.getCurrentUserID(contactUsername);
        if (contactID == null) {
            return presenter.printChatNotCreated(new InvalidChoiceException("user"));
            //throw new InvalidChoiceException("user");
        }
        if (chatManager.existChat(currentUserID, contactID)){
            String chatID = chatManager.findChat(currentUserID, contactID);
            return presenter.printChatExists(chatID);
        }
        else if (currentUserID.equals(contactID)){
            return presenter.printChatNotCreated(new
                    InvalidFormatException("recipients", "You cannot create a chat with yourself!"));
            //throw new InvalidFormatException("recipients", "You cannot create a chat with yourself!");
        }else {
            if (!this.chatNameTaken(contactID, chatName)){
            String chatID = chatManager.createChat(currentUserID, contactID, chatName);
            attendeeManager.addChat(contactID, chatID);
            attendeeManager.addChat(currentUserID, chatID);
            return presenter.printChatCreated(chatID);}
            else{return presenter.printChatNotCreated(new
                    InvalidFormatException("chatName", "That chatName already exists for you or for your Chat members!"));}
        }
    }

    // Option 9

    /**
     * Creates a new GroupChat
     */
    /*private void createGroupchatChoice() {
        presenter.printContactUsernamesPrompt();
        String contacts = SubMenu.readInput(input);
        String[] a = contacts.split(",");
        ArrayList<String> contactlist = new ArrayList<>(Arrays.asList(a));
        if (contactlist.isEmpty()) {
            presenter.printException(new InvalidChoiceException("user"));
            return;
        }
        try {
            ArrayList<String> cs = new ArrayList<String>();
            for (String contact: contactlist) {
                cs.add(attendeeManager.getCurrentUserID(contact));
            }
            String groupChatID = createGroupChat(cs);
            if  (groupChatID != null){
                for (String contact: cs) {
                    attendeeManager.addChat(contact, groupChatID);
                }
                presenter.printJobDone();
                presenter.printChatCreated();
                presenter.printID(groupChatID);
            }
        } catch (NoDataException e) {
            presenter.printChatNotCreated();
            presenter.printSoloChatNotAllowed();
        } catch (NullPointerException e) {
            presenter.printException(new InvalidChoiceException("user"));
        } catch (InvalidChoiceException e) {
            presenter.printChatNotCreated();
            presenter.printException(e);
        }
    }*/

    /**
     * Create a new group chat if contacts are in this user's contactlist.
     * @param contactUsernames the ArrayList of contacts' IDs.
     * @param chatName Name of the Chat being created
     * @return A message that contains the chat ID if the chat was created or already exists;
     * an error message, otherwise
     */
    public String createGroupChat(ArrayList<String> contactUsernames, String chatName) {
        ArrayList<String> contactIDs = new ArrayList<>();
        for (String username: contactUsernames){ contactIDs.add(personManager.getCurrentUserID(username));}
        if (contactIDs == null || contactIDs.size() == 0) {
            return presenter.printChatNotCreated(new InvalidChoiceException("user"));
        }
        else if (contactIDs.size() == 1 && currentUserID.equals(contactIDs.get(0))){
            return presenter.printChatNotCreated(new
                    InvalidFormatException("recipients", "You cannot create a chat with yourself!"));
        }
        else if (this.chatManager.existChat(currentUserID, contactIDs)) { //if there already exist a desired Chat
            String chatID = chatManager.findChat(currentUserID, contactIDs);
            return presenter.printChatExists(chatID);
        }
        else {
            if (!this.chatNameTaken(contactIDs, chatName)){
            String chatID = chatManager.createChat(currentUserID, contactIDs, chatName);
            personManager.addChat(currentUserID, chatID);
            for (String contact: contactIDs) {
                personManager.addChat(contact, chatID);
            }
            return presenter.printChatCreated(chatID);}
            else{return presenter.printChatNotCreated(new
                    InvalidFormatException("chatName", "That chatName already exists for you or for your Chat members!"));}
        }
    }

    @Override
    public AttMessageMenu getPresenter() {
        return this.presenter;
    }

}
