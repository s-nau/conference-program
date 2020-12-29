package Message;

// Programmer: Ran Yi, Karyn Komatsu
// Description: Stores a list of Message objects inside. Could create and get info of Message obj.
// Date Modified: 18/11/2020

import Message.Message;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageManager implements Serializable {
    private final ArrayList<Message> messageList;

    public MessageManager() {
        messageList = new ArrayList<>();
    }

    /**
     * Create new Message.Message and add it to the MessageList.
     *
     * @return the messageId iff succeed.
     */
    public String createMessage(String senderId, String recipientId, String chatId, String content) {
        Message newMessage = new Message(senderId, recipientId, chatId, content);
        messageList.add(newMessage);
        return newMessage.getMessageId();
    }

    /**
     * this method is used for event messages.
     *
     * @param eventId an id representing the eventId
     * @param content an string reprsenting the content
     * @return ID of the newly created Message object
     */
    public String createMessage(String eventId, String chatId, String content) {
        Message newMessage = new Message(eventId, chatId, content);
        messageList.add(newMessage);
        return newMessage.getMessageId();

    }

    /**
     * Get all the messages stored in this MessageManager.
     *
     * @return the Message.Message.messageList.
     */
    public ArrayList<Message> getMessageList() {
        return this.messageList;
    }

    /**
     * Get Message.Message by the Id.
     *
     * @param MessageId the ID of Message object that we are trying to retrieve.
     * @return the Message.Message.
     */
    private Message getMessage(String MessageId) {
        Message curMessage = null;
        for (Message m : messageList) {
            if (m.getMessageId().equals(MessageId)) {
                curMessage = m;
                break;
            }
        }
        return curMessage;
    }

    /**
     * Checks if msgID is a valid message ID
     * @param msgID the ID of the message we're checking
     * @return true iff the message ID is vaild
     */
    public boolean checkMessageID(String msgID) {
        return getMessage(msgID) != null;
    }

    /**
     * @return the list of IDs of the messages stored in this MessageManager.
     */
    public ArrayList<String> getMessageIDs() {
        ArrayList<String> messageIDs = new ArrayList<>();
        for (Message m : messageList) {
            messageIDs.add(m.getMessageId());
        }
        return messageIDs;
    }

    /**
     * @return the senderID of a message by the messageID.
     */
    public String getSenderID(String messageID) {
        return getMessage(messageID).getSenderId();
    }

    /**
     * @return the recipientID of a message by the messageID.
     */
    public String getRecipientId(String messageID) {
        return getMessage(messageID).getRecipientId();
    }

    /**
     * @return the DateTime of a message by the messageID.
     */
    public String getDateTime(String messageID) {
        return getMessage(messageID).getDateTime().toString();
    }

    /**
     * @return the Content of a message by the messageID.
     */
    public String getContent(String messageID) {
        return getMessage(messageID).getContent();
    }

    /**
     * @return the status of this message, read or unread. By messageId.
     */
    public boolean getReadStatus(String messageID) {
        return getMessage(messageID).getReadStatus();
    }

    /**
     * change the status of this message to unread.
     */
    public void changeStatusUnread(String messageID) {
        getMessage(messageID).changeStatusUnread();
    }

    /**
     * change the status of this message to read.
     */
    public void changeStatusRead(String messageID) {
        getMessage(messageID).changeStatusRead();
    }

    /**
     * @return the chatID of this message. By messageId.
     */
    public String getChatId(String messageID) {
        return getMessage(messageID).getChatId();
    }

}
