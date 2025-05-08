
package sobi.vo.mypage;

import java.sql.Timestamp;

public class MessageVO {
    private int messageId;
    private String senderId;
    private String receiverId;
    private String messageTitle;
    private String messageContent;
    private Timestamp messageSendDate;
    private String messageIsRead;
    private String deletedBySender;
    private String deletedByReceiver;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Timestamp getMessageSendDate() {
        return messageSendDate;
    }

    public void setMessageSendDate(Timestamp messageSendDate) {
        this.messageSendDate = messageSendDate;
    }

    public String getMessageIsRead() {
        return messageIsRead;
    }

    public void setMessageIsRead(String messageIsRead) {
        this.messageIsRead = messageIsRead;
    }

    public String getDeletedBySender() {
        return deletedBySender;
    }

    public void setDeletedBySender(String deletedBySender) {
        this.deletedBySender = deletedBySender;
    }

    public String getDeletedByReceiver() {
        return deletedByReceiver;
    }

    public void setDeletedByReceiver(String deletedByReceiver) {
        this.deletedByReceiver = deletedByReceiver;
    }
}