package Message;

// Programmer: Karyn Komatsu, Ran Yi, Sarah Kronenfeld
// Description: Stores Chat objects
// Date Modified: 19/11/2020

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.Serializable;
import java.util.Objects;

public class ChatManager implements Serializable {
    private ArrayList<Chat> chatsList; // list for storing a collection of all Message.Message.Chat objects
    private ArrayList<Chat> aChatsList; // list for storing a collection of all AnnouncementChat objects
    public ChatManager(){
        chatsList = new ArrayList<>();
        aChatsList = new ArrayList<>();
    }

    /**
     * Tells the user whether or not there exist chats in the system
     * @return Whether there exist chats in the system
     */
    public boolean isEmpty() {
        return chatsList.isEmpty();
    }

    //MODIFIED createChat (both of the two below: one taking String and String, other taking String and ArrayList<String>
    // so that it returns null iff inputted "guest" is the owner (ie the program user is trying to create a Chat by itself)
    // or when the Chat with same members already exist
    /**
     * Create new Message.Message.Chat object between user and a contact, and add to the ChatsList
     * @param ownerId ID of the user owning this Chat object
     * @param guestId IDs of guest
     * @param chatName the name of this chat, set up by who create it.
     * @return the chatID iff new Message.Message.Chat object was successfully created and added to ChatList
     */
    public String createChat(String ownerId, String guestId, String chatName){
        if ((ownerId.equals(guestId))||(this.existChat(ownerId, guestId))){return null;}
        Chat newC = new Chat(ownerId, guestId, chatName);
        chatsList.add(newC);
        return newC.getId();
    }

    /**
     * Create new Message.Message.Chat object among user and a group of friends, and add to the ChatsList
     * @param ownerId ID of the user owning this Chat object
     * @param guestIds Collection of IDs of (one or more) guests
     * @param chatName the name of this group chat, set up by who create it.
     * @return the chatID iff new Message.Message.Chat object was successfully created and added to ChatList
     *         null iff (ArrayList guestIds only contain ownerId) OR (a group Chat with same member exists)
     */
    public String createChat(String ownerId, ArrayList <String> guestIds, String chatName){
        if (((guestIds.size()==1) && guestIds.contains(ownerId))||(this.existChat(ownerId, guestIds))){return null;}
        Chat newC = new Chat(ownerId, guestIds, chatName);
        chatsList.add(newC);
        return newC.getId();}


    /**
     * creates and returns an announcement chat with eventId and attendeeIds
     * @param eventId id represent the event for the announcement
     * @param attendeeIds the id's of the attendees
     * @param chatName the name of this group chat, set up by who create it.
     * @return the chatID of announcementChat made
     */
    public String createAnnouncementChat(String eventId, ArrayList<String> attendeeIds, String chatName){
        Chat ac = new Chat(eventId, attendeeIds, chatName);
        aChatsList.add(ac);
        return ac.getId();
    }

    /**
     * Add Message ID to Chat, where Chat is referred by the Chat ID
     * @param chatId of the Chat object that we want to add the messageID to
     * @param messageId of the Message object to be added to the list in Chat object
     * @return true iff the message ID was added to the Chat
     *         false iff no Chat in ChatList has the inputted chatId
     */
    public boolean addMessageIds(String chatId, String messageId){
        Chat chat = getUnknownTypeChat(chatId);
        if(chat != null) {
            chat.addMessageIds(messageId);
            return true;
        }
        return false;
    }

    /**
     * Get ArrayList of messageIDs of the Messages storedin the Chat or AnnouncementChat object
     * represented by the inputted chatId
     * @param chatId of Chat or Announcement Chat object
     * @return ArrayList of messageIDs stored in the Message.Message.Chat with inputted chatID
     *         null iff no Message.Message.Chat in ChatList has the inputted chatId
     */
    public ArrayList<String> getMessageIds(String chatId) {
        Chat chat = getUnknownTypeChat(chatId);
        if(chat != null) {
            return chat.getMessageIds();
        }
        return null;
    }

    /**
     * Update senders (personIds) list of Message.Message.Chat when a new Person.Person is added
     * @param chatId the ID of the Message.Message.Chat that is adding new Person.Person
     * @param personId the ID of the new Person.Person being added to the Message.Message.Chat
     * @return true iff senders (personIds) list of Message.Message.Chat is successfully updated
     */
    //The senders list and related methods in Message.Message.Chat class must be fixed so that it contains ID instead of Person.Person
    public boolean addPersonIds(String chatId, String personId) {
        Chat chat = getUnknownTypeChat(chatId);
        if(chat != null) {
            chat.addPersonIds(personId);
            return true;
        }
        return false;
    }

    /**
     * @return the name of this chat, by its Id.
     */
    public String getChatName(String chatId) {
        return Objects.requireNonNull(getUnknownTypeChat(chatId)).getName();
    }

    /**
     * Remove an ID of a Person from personId of a Chat
     *
     * @param chatId   the ID of the Chat
     * @param personId the ID of the Person object to be removed from the Chat's personIds list
     * @return true iff personId is successfully removed form the personIds list of Chat
     */
    public boolean removePersonIds(String chatId, String personId) {
        Chat chat = getUnknownTypeChat(chatId);
        if (chat != null) {
            chat.removePersonIds(personId);
            return true;
        }
        return false;
    }

    /**
     * Remove all person Ids from personId list of a Chat
     *
     * @param chatId Id of Chat
     * @return True iff all Person Id removed from personIds list
     */
    public boolean removeAllPersonIds(String chatId) {
        Chat chat = getUnknownTypeChat(chatId);
        if (chat != null) {
            chat.removeAllPersonIds();
            return true;
        }
        return false;
    }

    /**
     * Get personIds list of Chat corresponding to the inputted chatId
     * @param chatId Id of the target Chat object
     * @return ArrayList of Strings representing Person IDs contained in the Chat
     */
    public ArrayList<String> getPersonIds(String chatId){
        Chat chat = getUnknownTypeChat(chatId);
        if(chat != null){return chat.getPersonIds();}
        return null;
    }

    /**
     * Get class of the Chat item corresponding to the inputted chat ID
     * @param chatID ID of the Chat object
     * @return Class type, 1 for announcement, 0 for chat
     */
    public boolean getChatType(String chatID){
        return Objects.requireNonNull(getUnknownTypeChat(chatID)).getAnnouncementOrNot();
    }

    public void setChatTypeToAnn(String chatID) {
        Objects.requireNonNull(getUnknownTypeChat(chatID)).changeToAnnouncement();
    }

    public void setChatTypeToChat(String chatID) {
        Objects.requireNonNull(getUnknownTypeChat(chatID)).changeToChat();
    }

    /**
     * Figures if chat ID corresponds to null object (i.e. There is no Chat object associated with the inputted ID)
     * @param chatID ID of the chat
     * @return true iff chat ID corresponds to null object.
     */
    public boolean isChatIDNull(String chatID) {
        try {
            return getUnknownTypeChat(chatID) == null;
        } catch (NullPointerException n) {
            return true;
        }
    }

    /**
     * Return collection of all Chats where the inputted person ID is part of the member.
     * @param personId the ID of the person
     * @return ArrayList of Chats containing the inputted person ID
     */
    public ArrayList <Chat> searchChatsContaining(String personId){
        ArrayList <Chat> chats = new ArrayList<>();
        for (Chat c: chatsList){
            if (c.getPersonIds().contains(personId)){
                chats.add(c);
            }
        }
        return chats;
    }

    /**
     * Return collection of chatNames of all Chats where the inputted person ID is part of the member.
     * @param personIds the IDs of the persons
     * @return ArrayList of chatNames of all Chats containing the inputted person ID
     */
    public ArrayList <String> searchChatsContaining(ArrayList <String> personIds){
        ArrayList <String> chats = new ArrayList<>();
        for (Chat c: chatsList){
            int num = 0;
            for (String personId: personIds){
                if (c.getPersonIds().contains(personId)){
                    num = num + 1; }
            }if (num == personIds.size()){chats.add(c.getName());}
        }
        return chats;
    }

    /**
     * Finds the Message.Message.Chat object with input Message.Message.Chat ID
     * @param chatId of the Message.Message.Chat object we are trying to find
     * @return Message.Message.Chat object corresponding to the Message.Message.Chat ID inputted
     *         null if ChatID invalid
     */
    private Chat getChat(String chatId){
        for(Chat c: chatsList){
            if (c.getId().equals(chatId)){
                return c;
            }
        }
        return null;
    }

    /**
     * Finds the AnnouncementChat object with input aChatId
     * @param aChatId of the AnnouncementChat object we are trying to find
     * @return Chat object corresponding to the aChatId inputted
     *         null if aChatId invalid
     */
    private Chat getAnChat(String aChatId){
        for(Chat ac: aChatsList){
            if (ac.getId().equals(aChatId)){
                return ac;
            }
        }
        return null;
    }

    /**
     * Finds the Chat object with the inputted chatID
     * @param chatId ID of a Chat object that may be Chat or AnnouncementChat
     * @return Chat object corresponding to the chatId. Null is returned if the ID is invalid.
     */
    private Chat getUnknownTypeChat(String chatId){
        for(Chat c: chatsList){
            if(c.getId().equals(chatId)){
                return c;
            }
        }
        for(Chat ac: aChatsList){
            if(ac.getId().equals(chatId)){
                return ac;
            }
        }
        return null;
    }

    /**
     * @return the list of IDs of the chats stored in this ChatManager.
     */
    public ArrayList<String> getChatIDs(){
        ArrayList<String> chatIDs = new ArrayList<>();
        for (Chat c : chatsList){
            chatIDs.add(c.getId());
        }
        return chatIDs;
    }


    /**
     * @return the list of IDs of the AnnouncementChats stored in this ChatManager.
     */
    public ArrayList<String> getAnnouncementChatIDs(){
        ArrayList<String> aChatIDs = new ArrayList<>();
        for (Chat c : aChatsList){
            aChatIDs.add(c.getId());
        }
        return aChatIDs;
    }

    /**
     * Checks if there already exists a Chat object with same group members inputted
     * @param currentId ID of the user
     * @param guestId ID of the other member of the Chat
     * @return True iff there exists a Chat with the exact same group members inputted
     *         False iff there does not exist a Chat with the exact same group members inputted
     */
    public boolean existChat(String currentId, String guestId) {
        return findChat(currentId, guestId) != null;
    }

    /**
     * Checks if there already exists a Chat object with same group members inputted
     * @param currentId ID of the user
     * @param guestsId ID of the chat members of the Chat
     * @return True iff there exists a Chat with the exact same group members inputted
     *         False iff there does not exist a Chat with the exact same group members inputted
     */
    public boolean existChat(String currentId, ArrayList<String> guestsId) {
        return findChat(currentId, guestsId) != null;
    }

    /**
     * Find and return the chat ID of the Chat that has the exact same chat members inputted
     * @param currentId ID of the user
     * @param guestId ID of the chat member of the Chat
     * @return chat ID of the Chat with exact same group members inputted.
     *         null otherwise.
     */
    public String findChat(String currentId, String guestId) {
        ArrayList<String> personIds = new ArrayList<>();
        personIds.add(guestId);
        return findChat(currentId, personIds);
    }

    /**
     * Find and return the chat ID of the Chat that has the exact same chat members inputted
     *
     * @param currentId ID of the user
     * @param guestsId  ID of the chat members of the Chat
     * @return chat ID of the Chat with exact same group members inputted.
     * null otherwise.
     */
    public String findChat(String currentId, ArrayList<String> guestsId) {
        ArrayList<String> personIds = new ArrayList<>(guestsId);
        personIds.add(currentId);
        Collections.sort(personIds);
        for (Chat c : chatsList) {
            ArrayList<String> members = c.getPersonIds();
            Collections.sort(members);
            if (members.equals(personIds)) {
                return c.getId();
            }
        }
        return null;
    }

    public String findChat(ArrayList<String> userIds){
        ArrayList<String> users = new ArrayList<>(userIds);
        Collections.sort(users);
        for (Chat c : chatsList) {
            ArrayList<String> members = c.getPersonIds();
            Collections.sort(members);
            if (members.equals(users)) {
                return c.getId();
            }
        }
        return null;

    }

    /**
     * Find chat Id by chatName
     * @param chatName Name of Chat
     * @return ID of chat corresponding to chatName
     */
    public String findChatByName(String chatName) {
        for (Chat c : chatsList) {
            if (c.getName().equals(chatName)) {
                return c.getId();
            }
        }
        for (Chat c : aChatsList) {
            if (c.getName().equals(chatName)) {
                return c.getId();
            }
        }
        return null;
    }

    public void nullifyChatID(String chatId) {
        for (Chat c : chatsList) {
            if (c.getId().equals(chatId)) {
                c.nullThisId();
            }
        }
    }

    public String readStatusChat(String chatId){return Objects.requireNonNull(getUnknownTypeChat(chatId)).getReadStatus(
    );}

    public void readChat(String chatId) {
        Objects.requireNonNull(getUnknownTypeChat(chatId)).markAsRead();}

    public void unreadChat(String chatId) {
        Objects.requireNonNull(getUnknownTypeChat(chatId)).markAsUnread();}

}

// CRC Card Definition
// Stores a collection of all Message.Message.Chat objects - DONE: ChatsList
//Creates new Message.Message.Chat objects - DONE: createChat method
//Can add Message.Message IDs to Chats - DONE: addMessage
//Must update Person.Person list if new Person.Person is added to Message.Message.Chat - DONE: addPerson
//Get ArrayList of all Chats containing Person.Person ID - DONE: searchChats
//Getter for Message.Message list by Message.Message.Chat ID (Add to CRC Card maybe?) - DONE: getMessageIds

