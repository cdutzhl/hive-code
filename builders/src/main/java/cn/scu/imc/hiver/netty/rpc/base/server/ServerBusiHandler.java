package cn.scu.imc.hiver.netty.rpc.base.server;

import cn.scu.imc.hiver.vo.netty.Command;
import cn.scu.imc.hiver.vo.netty.Message;
import cn.scu.imc.hiver.vo.netty.MessageHeader;
import cn.scu.imc.hiver.vo.netty.MessageType;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 类说明：业务处理类
 * channelRead0方法中有了实际的业务处理，负责具体的业务方法的调用
 *
 */
@Service
@ChannelHandler.Sharable
public class ServerBusiHandler
        extends SimpleChannelInboundHandler<Message> {
    private static final Log LOG = LogFactory.getLog(ServerBusiHandler.class);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) {
        LOG.info(msg);
        if (msg.getMessageHeader() != null && msg.getMessageHeader().getType() == MessageType.SERVICE_REQ.value()) {
            Command command = (Command) msg.getBody();
            if (StringUtil.isNullOrEmpty(command.getCommands())) {
                Message message = getResponseMsg(msg, "No command need to exec!");
                ctx.writeAndFlush(message);;
            } else {
                Arrays.stream(command.getCommands().split(";")).forEach(commandStr -> {
                    try {
                        Runtime runtime = Runtime.getRuntime();
                        Process process = runtime.exec(commandStr);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
                        String line;
                        while ((line = reader.readLine()) != null){
                            Message message = getResponseMsg(msg, line + "\n");
                            ctx.writeAndFlush(message).addListener((ChannelFutureListener) future -> {
                                if(!future.isSuccess()) {
                                    future.cause().printStackTrace();
                                }
                            });
                        }
                        reader.close();
                    } catch (Exception e) {
                        Message message = getResponseMsg(msg, "Exec command: " + commandStr + " error: " + e.getMessage());
                        ctx.writeAndFlush(message);
                    }
                } );
                Message lastMessage = getResponseMsg(msg, "客户端信息发送完毕！");
                MessageHeader messageHeader = lastMessage.getMessageHeader();
                messageHeader.setLastMessage(true);
                ctx.writeAndFlush(lastMessage);
            }
        } else {
            ctx.fireChannelRead(msg);
        }

    }



    private Message getResponseMsg(Message msg, String logLine) {
        Message message = new Message();
        MessageHeader header = new MessageHeader();
        header.setSessionID(msg.getMessageHeader().getSessionID());
        header.setType(MessageType.SERVICE_RESP.value());
        message.setMessageHeader(header);
        message.setBody(logLine);
        return message;
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx)
            throws Exception {
        LOG.info(ctx.channel().remoteAddress()+" 主动断开了连接!");
    }

}
