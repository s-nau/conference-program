package Message;

// Programmer: Ran Yi
// Description: Message Entity, has ID, sender's ID, recipient's ID and content.
// Date Modified: 15/11/2020

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Message implements Serializable {
    private String Id;
    private String senderId;  // a sender is a person or an event.
    private String recipientId;
    private LocalDateTime dateTime;
    private String content;
    private boolean readStatus; // read is false, unread is true
    private String chatId; // the chat this message belongs to.

    public Message(String senderId, String recipientId, String chatId, String content){
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
        this.Id = UUID.randomUUID().toString();
        this.dateTime = LocalDateTime.now();
        this.readStatus = true;
        this.chatId = chatId;
    }
    // this is for event announcements.
    public Message(String eventId, String chatId, String content){
        this.senderId = eventId;
        this.content = content;
        this.Id = UUID.randomUUID().toString();
        this.dateTime = LocalDateTime.now();
        this.recipientId = null;
        this.readStatus = true;
        this.chatId = chatId;
    }

    /**
     * This gives access to the Id of this Message.Message.
     *
     * @return the Id of this Message.Message.
     */
    public String getMessageId(){
        return this.Id;
    }

    /**
     * This gives access to the senderId of this Message.Message, or the eventId.
     *
     * @return the sender of this Message.Message.
     */
    public String getSenderId(){
        return this.senderId;
    }

    /**
     * This gives access to the recipient of this Message.Message.
     *
     * @return the recipient of this Message.Message.
     */
    public String getRecipientId(){
        return this.recipientId;
    }

    /**
     * This gives access to the datetime of this Message.Message.
     *
     * In case we need the function of searching messages by datetime in the future.
     *
     * @return the datetime of this Message.Message.
     */
    public LocalDateTime getDateTime(){
        return this.dateTime;
    }

    /**
     * This gives access to the content of this Message.Message.
     *
     * In case we need the function of searching messages by keywords in the future.
     *
     * @return the content of this Message.Message.
     */
    public String getContent(){
        return this.content;
    }

    /**
     * @return the status of the message. true is unread, false is read.
     */
    public boolean getReadStatus() {
        return this.readStatus;
    }

    /**
     * change the status of this message to unread.
     */
    public void changeStatusUnread() {
        this.readStatus = true;
    }

    /**
     * change the status of this message to read.
     */
    public void changeStatusRead() {
        this.readStatus = false;
    }

    /**
     * getter for the chatId of this message.
     *
     * @return String of the chat name.
     */
    public String getChatId() {
        return this.chatId;
    }
}
