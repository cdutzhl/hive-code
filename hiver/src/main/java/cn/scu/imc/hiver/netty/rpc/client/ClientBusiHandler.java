package cn.scu.imc.hiver.netty.rpc.client;


import cn.scu.imc.hiver.netty.vo.Message;
import cn.scu.imc.hiver.netty.vo.MessageHeader;
import cn.scu.imc.hiver.netty.vo.MessageType;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 新增的客户端业务处理Handler类
 * 并提供了发送数据到服务器的send方法
 * 采用阻塞队列，将异步调用转同步调用
 * 交给Spring 托管
 */
@Service
@ChannelHandler.Sharable
public class ClientBusiHandler extends SimpleChannelInboundHandler<Message> {

    private static final Log LOG = LogFactory.getLog(ClientBusiHandler.class);
    private ChannelHandlerContext ctx;
    private final ConcurrentHashMap<Long, BlockingQueue<Object>> responseMap = new ConcurrentHashMap<Long, BlockingQueue<Object>>();

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        this.ctx = ctx;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        LOG.info("Server: " + msg);
        if (msg.getMessageHeader() != null
                && msg.getMessageHeader().getType() == MessageType.SERVICE_RESP.value()) {
            long sessionId = msg.getMessageHeader().getSessionID();
            Object result = msg.getBody();
            if (msg.getMessageHeader().isLastMessage()) {
                BlockingQueue<Object> msgQueue = responseMap.get(sessionId);
                msgQueue.put(result);
            }

        } else{
            ctx.fireChannelRead(msg);
        }
    }

    public Object send(Object message) throws InterruptedException {
        if(ctx.channel() ==null || !ctx.channel().isActive()){
            throw new IllegalStateException("和服务器还未未建立起有效连接！" +
                    "请稍后再试！！");
        }
        Message msg = new Message();
        MessageHeader messageHeader = new MessageHeader();
        Random r = new Random();
        long sessionId = r.nextLong()+1;
        messageHeader.setSessionID(sessionId);
        messageHeader.setType(MessageType.SERVICE_REQ.value());
        msg.setMessageHeader(messageHeader);
        msg.setBody(message);
        BlockingQueue<Object> msgQueue = new ArrayBlockingQueue<>(1);
        responseMap.put(sessionId,msgQueue);
        ctx.writeAndFlush(msg);
        Object result =  msgQueue.take();
        LOG.info("获取到服务端的处理结果"+result);
        return result;
    }
}
