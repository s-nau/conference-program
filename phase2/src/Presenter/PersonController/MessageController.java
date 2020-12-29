package Presenter.PersonController;

import Presenter.Central.SubMenu;
import Presenter.Central.SubMenuPrinter;
import Presenter.Exceptions.InvalidChoiceException;
import Presenter.Exceptions.NoDataException;

import java.util.ArrayList;

// Programmer: Ran Yi, Sarah Kronenfeld
// Description: For current User to view chat and message, create chat and send message.
// Date Modified: 19/11/2020

public class MessageController extends SubMenu {
    protected String currentUserID;
    private MessageMenu presenter;
    protected int currentRequest;

    public MessageController (SubMenu subMenu, String currentUserID) {
        super(subMenu);
        presenter = new MessageMenu(personManager, messageManager, chatManager, eventManager, currentUserID);
        this.currentUserID = currentUserID;
    }



    // Option 1

    /**
     * Show all the messages this user received in presenter, **sorted by datetime.
     */
    public String[] inBox() throws NoDataException {
        return presenter.getInBox();
    }

    // Option 2

    /**
     * Show all the messages this user sent in presenter, **sorted by datetime.
     */
    public String[] sentBox() throws NoDataException {
        return presenter.getOutBox();
    }

    // Option 3

    /**
     * Show all the chats this user sent in presenter, **sorted by datetime.
     */
    protected String[] seeChats() throws InvalidChoiceException {
        return presenter.getChats(personManager.getChats(currentUserID));
    }

    // Option 4

    /**
     * Show the messages in a chat by chatName.
     */
    protected String[] seeMessages(String chatName) throws InvalidChoiceException {
        return presenter.getChat(chatManager.findChatByName(chatName));
    }

    // Option 5

    /**
     * Creates new Message for existing Chat (1 to 1 chat or group chat both use this.)
     * @param chatName The chatID of the Chat the current user want's to send a Message to
     * @param messageContent The contents of the message the current user wants to send
     */
    public String sendMessage(String chatName, String messageContent) throws InvalidChoiceException {
        String chatID = chatManager.findChatByName(chatName);
        if (chatManager.isEmpty()) {
            throw new NoDataException("chat");
        }
        if (chatManager.isChatIDNull(chatID)) {
            throw new InvalidChoiceException("chat");
        }
        ArrayList<String> personIDs = chatManager.getPersonIds(chatID);
        for (String receiverID : personIDs){
            if (!receiverID.equals(currentUserID)){
                String messageID = messageManager.createMessage(currentUserID, receiverID, chatID, messageContent);
                chatManager.addMessageIds(chatID,messageID);
                presenter.printMessageSent();
            }
        }
        return presenter.messageSent();
    }

    /**
     * mark messages in a chat as read after OPTION 4 -- VIEW MESSAGES IN CHAT --
     */
    protected void chatViewed (String chatName) {
        for (String msgId : chatManager.getMessageIds(chatManager.findChatByName(chatName))) {
            messageManager.changeStatusRead(msgId);
        }
    }

    //Currently remove the ID of this Chat from the chatList of all Person in Chat, let personIds list of this Chat be
    // new empty arraylist, and make the chatID become null.
    /**
     * Allows the user to exit from a Chat.
     * If the Chat contains <=2 people, the Chat disappears from both user's chatList, and Chat is permanently deleted.
     * If Chat contains 3 people, permanently delete Chat if there already exists private chat among rest of members, or
     * set the Chat type to Chat (not announcement Chat) otherwise.
     * If Chat contains more than 3 people, simply let the user exit Chat.
     * @param chatName Name of the Chat the user wants to exit from
     */
    public void deleteChat(String chatName) throws InvalidChoiceException{
        String chatId = chatManager.findChatByName(chatName);
        if (chatId == null) {
            throw new InvalidChoiceException("chat");
        }
        ArrayList<String> personIds = chatManager.getPersonIds(chatId);
        if (personIds.size() <= 2){
            for(String personId: personIds) {
                personManager.removeChat(personId, chatId);
            }
            chatManager.removeAllPersonIds(chatId);
            chatManager.nullifyChatID(chatId);
        } else if (personIds.size() == 3) {
            boolean existsPrivate = false;
            ArrayList <String> original = new ArrayList<>(personIds);
            original.remove(currentUserID);
            if (chatManager.findChat(original)!=null){existsPrivate = true;}
            personManager.removeChat(currentUserID, chatId);
            chatManager.removePersonIds(chatId, currentUserID);
            if (existsPrivate) {this.deleteChat(chatId);}
            else {chatManager.setChatTypeToChat(chatId);}
        } else {
            personManager.removeChat(currentUserID, chatId);
            chatManager.removePersonIds(chatId, currentUserID);
        }
    }

    /**
     * Archive chat corresponding to input chatName
     * @param chatName Name of the chat
     */

    public void archiveChat(String chatName) throws InvalidChoiceException{
        String chatId = chatManager.findChatByName(chatName);
        if (chatId == null) {
            throw new InvalidChoiceException("chat");
        }

        for (String cId : personManager.getChats(currentUserID)) {
            if (chatId.equals(cId)) {
                personManager.archiveChatByPersonId(currentUserID, chatId);
                personManager.removeChat(currentUserID, chatId);
            }
        }
    }

    /**
     * Restore archived chat corresponding to chatName inputted
     * @param chatName Name of the archived chat
     */
    public void unArchiveChat(String chatName){
        String chatId = chatManager.findChatByName(chatName);
        for (String cId : personManager.getCurrentArchivedChatList(currentUserID)) {
            if (chatId.equals(cId)) {
                personManager.removeArchiveChatByPersonId(currentUserID, chatId);
                personManager.addChat(currentUserID, chatId);
            }
        }
    }

    public void archiveMessage(String msgId) throws InvalidChoiceException{
        if (messageManager.checkMessageID(msgId)) {
            personManager.addCurrentFavorites(currentUserID, msgId);
        }
        throw new InvalidChoiceException("message");
    }

    public void unArchiveMessage(String msgId){
        personManager.removeCurrentFavorites(currentUserID, msgId);
    }

    public void unreadMessage(String msgId) throws InvalidChoiceException{
        if (messageManager.checkMessageID(msgId)) {
            messageManager.changeStatusUnread(msgId);
        }
        throw new InvalidChoiceException("message");
    }

    public void readMessage(String msgId) throws InvalidChoiceException{
        if (messageManager.checkMessageID(msgId)) {
            messageManager.changeStatusRead(msgId);
        }
        throw new InvalidChoiceException("message");
    }

    /**
     * Gives chatNames of all Chats containing all usernames inputted
     * @param usernames Usernames contained in the target Chat that is being searched for
     * @return Arraylist of chatNames of all Chats containing usernames inputted
     */
    protected ArrayList<String> searchChatByUsernames(ArrayList<String> usernames){
        ArrayList<String> personIds = new ArrayList<>();
        for (String user: usernames){personIds.add(personManager.getCurrentUserID(user));}
        return chatManager.searchChatsContaining(personIds);}

    /**
     * Tells if the chatName is already taken (true) or not taken (false).
     * @param personID ID of the user that are going to be checked if they have a Chat with same name in their ChatList
     * @param chatName Name of the Chat
     * @return true iff chatName is taken by an existing Chat in chatList of the this user Person object.
     * Else, return false.
     */

    public boolean chatNameTaken(String personID, String chatName){
        ArrayList <String> personIDs = new ArrayList<>();
        personIDs.add(personID);
        return this.chatNameTaken(personIDs, chatName);
    }

    public boolean chatNameTaken(ArrayList<String> personIDs, String chatName){
        ArrayList <String> userIDs = new ArrayList<>(personIDs);
        userIDs.add(currentUserID);
        for (String userID: userIDs){
            ArrayList <String> userChatIDs = personManager.getChats(userID);
            for (String chatID: userChatIDs){
                if (chatManager.getChatName(chatID).equals(chatName)) {return true;}
            }
        }
        return false;}

    /*
    protected void addPersonToChats(String chatname, ArrayList<String> usernames){

        String chatId = chatManager.findChatByName(chatname);
        for (String username: usernames){chatManager.addPersonIds(chatId, personManager.getCurrentUserID(username));}
        for (String personId: chatManager.getPersonIds(chatId)){personManager.addChat(personId, chatId);}}
    */
    @Override
    public MessageMenu getPresenter() {
        return this.presenter;
    }
}
