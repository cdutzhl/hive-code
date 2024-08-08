package cn.scu.imc.hiver.netty.vo;

import java.io.Serializable;
/**
 * 类说明：消息实体类
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 3128763218762131L;

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
