package cn.scu.imc.hiver.netty.rpc.client;


import cn.scu.imc.hiver.netty.vo.*;
import cn.scu.imc.hiver.utils.HiveUtil;
import cn.scu.imc.hiver.utils.PropertiesUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

    private final static String FILESEPARATOR = System.getProperty("file.separator");
    private final static String LOGFILE = "build.log";
    private final static String BLANK = "        ";

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
            CommandResponse commandResponse = (CommandResponse)msg.getBody();
            String workspace = (String) PropertiesUtils.getValueByKey("hive.hive.workspace");
            String logDir= workspace + FILESEPARATOR + commandResponse.getProjectName() + FILESEPARATOR + commandResponse.getVersion() + FILESEPARATOR + LOGFILE;
            File logFile = new File(logDir);
            // 创建用于写入日志的文件
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
            if (msg.getMessageHeader().isFirstMessage()) {
                writer.write("【Stage Name】: " + commandResponse.getStageName() + "\n");
            } else {
                writer.write( BLANK + HiveUtil.now() + "  " +commandResponse.getContent());
            }

            if (msg.getMessageHeader().isLastMessage()) {
                BlockingQueue<Object> msgQueue = responseMap.get(sessionId);
                msgQueue.put(commandResponse);
                writer.write( "\n");
            }
            // 确保所有数据都写入文件
            writer.flush();
            writer.close();
        } else{
            ctx.fireChannelRead(msg);
        }
    }

    public Object send(Command message) throws InterruptedException {
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
