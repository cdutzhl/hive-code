package cn.scu.imc.api.vo.netty;
/**
 * 类说明：消息实体类
 */
public class Message {

    private MessageHeader messageHeader;

    private Object body;

    public final MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public final void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    public final Object getBody() {
        return body;
    }

    public final void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Message [Header=" + messageHeader + "][body="+body+"]";
    }
}
