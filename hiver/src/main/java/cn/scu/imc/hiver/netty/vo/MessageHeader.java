package cn.scu.imc.hiver.netty.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * 类说明：消息头
 */
public final class MessageHeader  implements Serializable {

    private static final long serialVersionUID = 912376183;

    private int crcCode = 0xabef0101;

    private int length;// 消息长度

    private long sessionID;// 会话ID

    private byte type;// 消息类型

    private byte priority;// 消息优先级

    private boolean firstMessage = false;//第一个消息

    private boolean lastMessage = false;//最后一个消息

    private Map<String, Object> attachment = new HashMap<String, Object>(); // 附件

    public final int getCrcCode() {
        return crcCode;
    }

    public final void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public final int getLength() {
        return length;
    }

    public final void setLength(int length) {
        this.length = length;
    }

    public final long getSessionID() {
        return sessionID;
    }

    public final void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }

    public final byte getType() {
        return type;
    }

    public final void setType(byte type) {
        this.type = type;
    }

    public final byte getPriority() {
        return priority;
    }

    public final void setPriority(byte priority) {
        this.priority = priority;
    }

    public final Map<String, Object> getAttachment() {
        return attachment;
    }

    public final void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }
    public final boolean isLastMessage() {
        return lastMessage;
    }

    public final void setLastMessage(boolean lastMessage) {
        this.lastMessage = lastMessage;
    }

    public boolean isFirstMessage() {
        return firstMessage;
    }

    public void setFirstMessage(boolean firstMessage) {
        this.firstMessage = firstMessage;
    }

    @Override
    public String toString() {
        return "MessageHeader{" +
                "crcCode=" + crcCode +
                ", length=" + length +
                ", sessionID=" + sessionID +
                ", type=" + type +
                ", priority=" + priority +
                ", firstMessage=" + firstMessage +
                ", lastMessage=" + lastMessage +
                ", attachment=" + attachment +
                '}';
    }
}

