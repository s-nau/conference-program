package Message;

// Programmer: Karyn Komatsu, Ran Yi
// Description: Chat Entity, has ID, the messageIDs of the Messages sent in this Chat. and the Users' ID.
// Date Modified: 18/11/2020

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class Chat implements Serializable {
    protected String Id;
    protected String name;
    protected String readStatus;
    protected ArrayList<String> messageIds;
    protected ArrayList<String> personIds;
    protected String password;
    protected boolean announcementOrNot; // 1 is announcement which cannot reply and 0 for general chat.

    //Chat constructor. Input is arraylist of ID(s) of guest(s) that you (owner) want to form group chat with.
    public Chat(String ownerId, String guestId, String name){
        messageIds = new ArrayList<>();
        personIds = new ArrayList<>();
        personIds.add(ownerId);
        personIds.add(guestId);
        this.Id = UUID.randomUUID().toString();
        this.readStatus = "unread";
        this.password = UUID.randomUUID().toString();
        this.announcementOrNot = false;
        this.name = name;
    }
    public Chat(String ownerId, ArrayList <String> guestIds, String name){
        messageIds = new ArrayList<>();
        personIds = new ArrayList<>();
        personIds.add(ownerId);
        personIds.addAll(guestIds);
        this.name = name;
        this.Id = UUID.randomUUID().toString();
        this.readStatus = "unread";
        this.password = UUID.randomUUID().toString();
        this.announcementOrNot = false;
    }

    public Chat(String ownerId, String[] guestIds, String name) {
        messageIds = new ArrayList<>();
        personIds = new ArrayList<>();
        personIds.add(ownerId);
        Collections.addAll(personIds,guestIds);
        this.name = name;
        this.Id = UUID.randomUUID().toString();
        this.readStatus = "unread";
        this.password = UUID.randomUUID().toString();
        this.announcementOrNot = false;
    }


    /**
     * Add the Id of the Message to messageIds list.
     * @param messageId - the Id of the Message object that we want to add.
     * @return True iff messageId was successfully added to messageIds.
     */
    public boolean addMessageIds(String messageId){
        this.messageIds.add(messageId);
        return true;
    }

    /**
     * Add the Id of the Person to personIds list.
     * @param personId - the Id of the Message object that we want to add.
     * @return True iff messageId was successfully added to messageIds.
     *
     */
    public boolean addPersonIds(String personId) {
        this.personIds.add(personId);
        return true;
    }

    /**
     * Remove the Id of the Person from personIds list.
     * @param personId - the Id of the Message object that we want to remove.
     * @return True iff personId was successfully removed from personIds.
     *
     */
    public boolean removePersonIds(String personId) {
        this.personIds.remove(personId);
        return true;
    }

    /**
     * Reset personIds list to empty Arraylist
     * @return True iff
     */
    public boolean removeAllPersonIds() {
        this.personIds.removeAll(this.personIds = new ArrayList<>());
        return true;
    }

    /**
     * Gets ID of Chat.
     * @return the Id of this Chat.
     */
    public String getId(){
        return this.Id;
    }

    /**
     * set ID of Chat to null.
     */
    public void nullThisId(){
        this.Id = null;
    }

    /**
     * Gets all Message IDs that are stored in this Chat.
     * @return ArrayList of strings (Message IDs) stored in this Chat.
     */
    public ArrayList<String> getMessageIds() {
        return this.messageIds;
    }

    /**
     * Gets all Person IDs that are stored in this Chat.
     * @return ArrayList of strings (Person IDs) stored in this Chat.
     */
    public ArrayList<String> getPersonIds(){return this.personIds;}

    /**
     *  a getter for the password
     * @return a String represent the announcement Chat's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * a getter for readStatus
     * @return readStatus "read" or "unread"
     */
    public String getReadStatus(){return readStatus;}

    /**
     * Checks if the password entered is correct
     * @param pass inputted password
     * @return true iff the inputted password is correct
     */
    public boolean checkPassword(String pass) {
        return pass.equals(password);
    }

    /**
     * get the type of this chat.
     * @return 1 for announcement 0 for others.
     */
    public boolean getAnnouncementOrNot() {
        return this.announcementOrNot;
    }

    public void changeToAnnouncement() {
        this.announcementOrNot = true;
    }

    public void changeToChat() {
        this.announcementOrNot = false;
    }

    /**
     * @return get the name of this chat.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Mark the chat as "read"
     * @return true iff Chat was marked "read"
     */
    public boolean markAsRead(){
        this.readStatus = "read";
        return true;
    }

    /**
     * Mark the chat as "unread"
     * @return true iff Chat was marked "unread"
     */
    public boolean markAsUnread(){
        this.readStatus = "unread";
        return true;
    }
}
