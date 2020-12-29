package Presenter.PersonController;

import Presenter.Central.SubMenuPrinter;
import Presenter.Exceptions.InvalidChoiceException;
import Presenter.Exceptions.NoDataException;
import Event.EventManager;
import Message.ChatManager;
import Message.MessageManager;
import Person.PersonManager;

import java.util.ArrayList;

// Programmers: Cara McNeil, Karyn Komatsu, Ran Yi, Sarah Kronenfeld
// Description: Prints information pertaining to a user's message information
// Date Created: 11/11/2020
// Date Modified: 02/12/2020

public class MessageMenu implements SubMenuPrinter {

    protected PersonManager personManager;
    protected ChatManager chatManager;
    protected MessageManager messageManager;
    protected EventManager eventManager;
    protected String currentUserID;

    public MessageMenu(PersonManager personManager, MessageManager messageManager, ChatManager chatManager,
                       EventManager eventManager, String currentUserID) {
        this.eventManager = eventManager;
        this.personManager = personManager;
        this.messageManager = messageManager;
        this.chatManager = chatManager;
        this.currentUserID = currentUserID;
    }

    @Override
    public String getMenuTitle() {
        return "----- Message Menu -----";
    }

    @Override
    public String[] getMenuOptions() {
        return new String[]{"Check your inbox", "Check your sent box", "View the chat list",
                "View the messages in a chat", "Send a message", "Archive a chat", "Leave a chat",
                "Archive a message", "Mark a message as unread", "Mark a message as read"};
    }




    // Option 1 -----------------INBOX-----------------

    public String getInboxTitle() {
        return "-INBOX-";
    }

    /**
     * Show all the messages this user received in presenter, **sorted by datetime.
     */
    public String[] getInBox() throws NoDataException{
        try {
            ArrayList<String> receivedMessages = new ArrayList<>();
            for (String message : messageManager.getMessageIDs()) {
                if (messageManager.getRecipientId(message).equals(currentUserID)) {
                    receivedMessages.add(message);
                }
            }
            return formatMessages(receivedMessages);
        } catch (NullPointerException e) {
                throw new NoDataException("message");
            }
    }


    // Option 2 ----------------SENTBOX--------------------

    public String getOutboxTitle() {
        return "-OUTBOX-";
    }

    /**
     * Show all the messages this user sent in presenter, **sorted by datetime.
     */
    public String[] getOutBox() throws NoDataException{
        try {
            ArrayList<String> sentMessages = new ArrayList<>();
            for (String message: messageManager.getMessageIDs()){
                if (messageManager.getSenderID(message).equals(currentUserID)){
                    sentMessages.add(message);
                }
            }

            return formatMessages(sentMessages);
        } catch (NullPointerException e) {
            throw new NoDataException("message");
        }
    }


    // Option 3 ---------------------VIEW CHAT LIST------------------

    public String getChatListTitle() {
        return "-CHATS-";
    }

    /**
     * Get chat formatted as:     [Name]: [Name of the chat]\new line
     *                            [Participants]: [Username of the Participants]\newline
     * @param chatID The ID of the Chat that is to be formatted
     * @return Formatted string representation of the chat.
     */
    protected String formatChatString(String chatID) {
        StringBuilder participants = new StringBuilder();
        for (String participantID : chatManager.getPersonIds(chatID)){
            participants.append("\n").append(personManager.getCurrentUsername(participantID));
        }
        for (String msgId : chatManager.getMessageIds(chatID)) {
            if (messageManager.getReadStatus(msgId)) {
                return "[Unread]" + "<br>" +
                        "ChatName: " + chatManager.getChatName(chatID) + "<br>" +
                        "Participants: " + participants.toString() + "<br>";
            }
        }
        return  "ChatName: " + chatManager.getChatName(chatID)  + "<br>" +
                "Participants: " + participants.toString()  + "<br>";
    }

    /**
     * Returns a list of formatted chat summaries
     * @param chatIDs The list of IDs the chats to print out
     * @throws InvalidChoiceException if the list is empty or the chat IDs are invalid
     */
    public String[] getChats(ArrayList<String> chatIDs) throws InvalidChoiceException {
        String[] chatList = new String[chatIDs.size()];
        for (int i = 0; i < chatList.length; i++) {
            String chat = chatIDs.get(i);
            chatList[i] = formatChatString(chat);
        }
        return chatList;
    }

    /**
     * Returns a list of chatIds of the current User.
     * @throws InvalidChoiceException if the list is empty or the chat IDs are invalid
     * @return a list of chatIds of the current User.
     */
    public ArrayList<String> getChatList() throws InvalidChoiceException {
        ArrayList<String> unReadChatList = new ArrayList<>();
        ArrayList<String> chatList = new ArrayList<>();
        for (String chatId : personManager.getChats(currentUserID)) {
            if (!chatManager.getChatType(chatId)) {
                for (String msgId : chatManager.getMessageIds(chatId)) {
                    if (messageManager.getReadStatus(msgId)) {
                        unReadChatList.add(chatId);
                    }
                }
                chatList.add(chatId);
            }
        }
        chatList.addAll(unReadChatList);
        return chatList;
    }



    // Option 4 ----------------- VIEW MESSAGES IN A CHAT (NOT ANNOUNCEMENT) ---------------------

    public String getChatTitle(String chatName) {
        String chatID = chatManager.findChatByName(chatName);
        StringBuilder participants = new StringBuilder();
        ArrayList<String> personIDs = chatManager.getPersonIds(chatID);
        participants.append(personManager.getCurrentUsername(personIDs.get(0)));
        if (personIDs.size() > 1) {
            for (int i = 0; i < personIDs.size(); i++) {
                participants.append(", ").append(personManager.getCurrentUsername(personIDs.get(i)));
            }
        }
        return chatID + " (" + participants.toString() + ")" ;
    }

    /**
     * Prompts user to enter ID of the chat.
     */
    public String printChatIdPrompt() {
        return "Which Chat do you want to check? Enter the chatName.";
    }

    /**
     * Show the messages in one chat by chatID.
     * For Phase 1 we also use this to view Announcements in AnnouncementChat.
     */
    public String[] getChat(String chatName) throws InvalidChoiceException {
        String chatID = chatManager.findChatByName(chatName);
        if (chatManager.isChatIDNull(chatID)) {
            throw new InvalidChoiceException("chat");
        }
        return formatMessages(chatManager.getMessageIds(chatID));
    }


    // Option 5 -------------------- SENT MESSAGE -------------------------

    /**
     * Prompts user to enter chatName of the chat want to send message in.
     */
    public String printChatNameMessagePrompt(){
        return "Which chat do you want to send message to? Enter the chatName.";
    }

    /**
     * Prompts user to enter content of the message.
     */
    public String printContentPrompt(){
        return "Please enter the content of your message.";
    }

    protected String messageSent() {
        return "Message sent!";
    }


    // ---------------- FEATURES with location not decided. (in MessageMenu or Att/Org/Spe) ----------------

    // -------option 99 see messages in a chat
    /**
     * Show all the messages in a chat
     */
    public String[] getMessagesInChat(String chatID) throws NoDataException{
        try {
            ArrayList<String> UnreadMessages = new ArrayList<>();
            for (String message : chatManager.getMessageIds(chatID)) {
                if (messageManager.getReadStatus(message)) {
                    UnreadMessages.add(message);
                }
            }
            ArrayList<String> Messages = new ArrayList<>();
            for (String message : chatManager.getMessageIds(chatID)) {
                if (!messageManager.getReadStatus(message)) {
                    Messages.add(message);
                }
            }
            Messages.addAll(UnreadMessages);
            return formatMessages(Messages);
        } catch (NullPointerException e) {
            throw new NoDataException("message");
        }
    }

    /**
     * Show all the messages in a chat
     */
    public ArrayList<String> getMessageIdsInChat(String chatID) throws NoDataException{
        try {
            ArrayList<String> UnreadMessageIds = new ArrayList<>();
            for (String message : chatManager.getMessageIds(chatID)) {
                if (messageManager.getReadStatus(message)) {
                    UnreadMessageIds.add(message);
                }
            }
            ArrayList<String> MessageIds = new ArrayList<>();
            for (String message : chatManager.getMessageIds(chatID)) {
                if (!messageManager.getReadStatus(message)) {
                    MessageIds.add(message);
                }
            }
            MessageIds.addAll(UnreadMessageIds);
            return MessageIds;
        } catch (NullPointerException e) {
            throw new NoDataException("message");
        }
    }


    // Option 100 -------------------- Archive chat -------------------
    // method in MessageController
    /**
     * Prompts user to enter chatname of the Chat that they want to archive
     */
    public String printArchiveChatPrompt(){ return("Which chat do you want to archive? Enter the chatName.");}

    protected String chatArchived(){return "Chat archived!";}

    // Option 101 -------------------- Remove archived chat --------------------
    // method in MessageController

    /**
     * Prompts user to enter chatName of the Chat that they want to de-archive chat
     */
    public String printUnarchiveChatPrompt(){
        return("Which archived chat do you want to restore to the original location? Enter the chatName.");}

    protected String chatUnarchived(){return "Archived chat restored!";}

    // Option 102 -------------------- Archive Message --------------------
    // method in MessageController

    public String printArchiveMessagePrompt(){return "Select the message that you would like to archive: ";}

    public String printArchivedMessagePrompt(){return "Message archived!";}

    // Option 103 -------------------- Remove archived message ---------------------
    // method in MessageController

    public String printUnarchiveMessagePrompt(){return "Select the archived message that you would like to restore: ";}

    public String printUnarchivedMessagePrompt(){return "Archived message restored!";}

    // Option 104 =--------------------delete Chat----------------------------------
    // method in MessageController

    public String printDeleteChatPrompt(){
        return ("Which chat do you want to delete from your account? Enter the chatName.");}

    public String printDeletedChat(){return "Chat is successfully deleted from your account.";}

    // ----------------------------- searchChatByUsernames ---------------------------------

    public String printSearchChatByUsernamesPrompt(){return "Enter the usernames of users in the Chat you are looking for." +
            " Separate by commas, no space. (example: userA,userB,userC,userD";}

    public String printSearchedChats(){return "Here are the results of Chats lookup by usernames: ";}

    // ----------------------------- Mark Chat as READ ---------------------------------

    public String printMarkChatRead(){return "Which chat do you want to mark as \"read\"? Enter the chatName.";}

    public String printMarkedChatAsRead(String chatName){return "The chat: " + chatName + " is marked as \"read\".";}

    // ----------------------------- Mark Chat as UNREAD ---------------------------------

    public String printMarkChatUnread(){return "Which chat do you want to mark as \"unread\"? Enter the chatName.";}

    public String printMarkedChatAsUnread(String chatName){return "The chat: " + chatName + " is marked as \"unread\".";}

    // -----------------------------  ---------------------------------



    // -----------------------------  ---------------------------------


    // ----------------------------- Helpers ---------------------------------
    // method in MessageController


    // ============================== Message formatting ================================

    /**
     * Get message formatted as: "[From]: [Username of the sender\Name of the Event]\new line
     *                            [To]: [Username of the receiver/null]
     *                            [Time Sent]: [time that was sent]\newline
     *                            [Message]: [the content of the message]\newline"
     * @param messageId of the message that is to be formatted.
     * @return Formatted string representation of the message.
     */
    protected String formatMessage(String messageId) {
        String sender = personManager.getCurrentUsername(messageManager.getSenderID(messageId));
        String receiver = personManager.getCurrentUsername(messageManager.getRecipientId(messageId));
        String time = messageManager.getDateTime(messageId);
        String message = messageManager.getContent(messageId);
        if (messageManager.getReadStatus(messageId)) {
            return  "[Unread]" + "<br>" +
                    "[ID]: " + messageId + "<br>" +
                    "From: " + sender + "[Username]" + "<br>" +
                    "To: " + receiver + "<br>" +
                    "Time sent:" + time + "<br>" +
                    "Message:" + message + "<br>";
        } else {
            return "From: " + sender + "[Username]" + "<br>" +
                    "To: " + receiver + "<br>" +
                    "Time sent:" + time + "<br>" +
                    "Message:" + message + "<br>";
        }
    }

    protected String[] formatMessages(ArrayList<String> messageIDs) throws NoDataException {
        if (messageIDs == null || messageIDs.size() == 0) {
            throw new NoDataException("message");
        }
        ArrayList<String> messageInChat = new ArrayList<>();
        for (String mID : messageIDs) {
            messageInChat.add(formatMessage(mID));
        }
        String[] messages = {};
        return messageInChat.toArray(messages);
    }

    /**
     * Prints that a message was successfully sent
     */
    public String printMessageSent() {
        return "Message has been successfully sent.";
    }

    /**
     * Prints that the user has no messages received.
     */
    public String printNoMessageReceived() {
        return "Inbox is EMPTY!";
    }
}
