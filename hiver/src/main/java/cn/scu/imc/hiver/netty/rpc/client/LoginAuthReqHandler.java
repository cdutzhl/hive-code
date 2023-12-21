package cn.scu.imc.hiver.netty.rpc.client;

import cn.scu.imc.api.vo.netty.Message;
import cn.scu.imc.api.vo.netty.MessageHeader;
import cn.scu.imc.api.vo.netty.MessageType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 类说明：发起登录请求
 */
public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter {

    private static final Log LOG = LogFactory.getLog(LoginAuthReqHandler.class);

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buildLoginReq());
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        Message message = (Message) msg;

        // 如果是握手应答消息，需要判断是否认证成功
        if (message.getMessageHeader() != null
                && message.getMessageHeader().getType() == MessageType.LOGIN_RESP
                .value()) {
            byte loginResult = (byte) message.getBody();
            if (loginResult != (byte) 0) {
                // 握手失败，关闭连接
                ctx.close();
            } else {
                LOG.info("Login is ok : " + message);
                ctx.fireChannelRead(msg);
            }
        } else
            ctx.fireChannelRead(msg);
    }

    private Message buildLoginReq() {
        Message message = new Message();
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setType(MessageType.LOGIN_REQ.value());
        message.setMessageHeader(messageHeader);
        return message;
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
