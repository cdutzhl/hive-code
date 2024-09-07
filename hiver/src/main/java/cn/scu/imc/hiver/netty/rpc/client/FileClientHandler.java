package cn.scu.imc.hiver.netty.rpc.client;


import cn.scu.imc.hiver.netty.vo.FileUpload;
import cn.scu.imc.hiver.netty.vo.Message;
import cn.scu.imc.hiver.netty.vo.MessageHeader;
import cn.scu.imc.hiver.netty.vo.MessageType;
import cn.scu.imc.hiver.utils.HiveUtil;
import io.netty.channel.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Random;

/**
 * 新增文件业务处理Handler类
 * 交给Spring 托管
 */
@Service
@ChannelHandler.Sharable
public class FileClientHandler extends SimpleChannelInboundHandler<Message> {

    private final static String BLANK = "        ";

    private static final Log LOG = LogFactory.getLog(FileClientHandler.class);

//    private final ConcurrentHashMap<Long, BlockingQueue<Object>> responseMap = new ConcurrentHashMap<Long, BlockingQueue<Object>>();

    private ChannelHandlerContext ctx;


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        this.ctx = ctx;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx,  Message msg) throws InterruptedException {
        LOG.info("Client : " + msg);
//        if (msg.getMessageHeader() != null
//                && msg.getMessageHeader().getType() == MessageType.SERVICE_FILE_UPLOAD_REQ.value()) {
//            long sessionId = msg.getMessageHeader().getSessionID();
//            Object result = msg.getBody();
//            LOG.info("sessionId: " + sessionId + "===== " + result);
//        } else if (msg.getMessageHeader() != null
//                && msg.getMessageHeader().getType() == MessageType.SERVICE_FILE_DONE_RESP
//                .value()) {
//            long sessionId = msg.getMessageHeader().getSessionID();
//            BlockingQueue<Object> msgQueue = responseMap.get(msg.getMessageHeader().getSessionID());
//            LOG.info("END: " + sessionId + "===== " + msgQueue.take());
//        } else {
//            ctx.fireChannelRead(msg);
//        }
    }

    public void uploadFile( File logFile, File file, String projectName, Integer version, Integer stageNo) throws IOException {
        if(ctx.channel() == null || !ctx.channel().isActive()){
            throw new IllegalStateException("和服务器还未未建立起有效连接！" +
                    "请稍后再试！！");
        }
        if (!file.exists()) {
            throw new RuntimeException("源文件不存在");
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
        writer.write("【Stage" + stageNo + "】: 执行Stage" + stageNo + "命令 \n");
        writer.write(BLANK + BLANK + "【开始向客户端传输文件】\n");
        String fileName = file.getName();
        Random r = new Random();
        long sessionId = r.nextLong()+1;
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        int total = 0;
        if ((randomAccessFile.length() % 1024) != 0) {
            total = (int)(randomAccessFile.length() / 1024 + 1);
        } else {
            total = (int)(randomAccessFile.length() / 1024);
        }
        for (int i = 0; i < total; i++) {
            Message msg = new Message();
            MessageHeader messageHeader = new MessageHeader();
            messageHeader.setSessionID(sessionId);
            messageHeader.setType(MessageType.SERVICE_FILE_UPLOAD_REQ.value());
            msg.setMessageHeader(messageHeader);
            FileUpload fileUpload = new FileUpload();
            fileUpload.setProjectName(projectName);
            fileUpload.setFileName(fileName);
            fileUpload.setVersion(version);
            byte[] bytes = null;
            if ( i + 1 == total ) {
                int readLength = (int)(randomAccessFile.length() - i * 1024);
                bytes = new byte[readLength];
            } else {
                bytes = new byte[1024];
            }
            randomAccessFile.seek(i * 1024);
            randomAccessFile.read(bytes);
            fileUpload.setBytes(bytes);
            fileUpload.setTotal(total);
            fileUpload.setIndex(i);
            msg.setBody(fileUpload);
            ctx.writeAndFlush(msg).addListener((ChannelFutureListener) future -> {
                if(!future.isSuccess()) {
                    future.cause().printStackTrace();
                }
            });
            writer.write(BLANK + BLANK + HiveUtil.now() + "    第"+ (i+1) +"块文件传输完成！File: " + fileName );
            writer.write(BLANK + HiveUtil.now() + "    文件总块数：【 "+ total +" 】, 剩余块数：【 " +  (total - (i + 1)) +" 】" + "\n");
        }
        randomAccessFile.close();
        writer.flush();
        writer.close();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        if(channel.isActive()){
            ctx.close();
        }
    }
}
