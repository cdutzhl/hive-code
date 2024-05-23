package cn.scu.imc.hiver.netty.rpc.base.server;

import cn.scu.imc.hiver.utils.PropertiesUtils;
import cn.scu.imc.hiver.vo.netty.FileUpload;
import cn.scu.imc.hiver.vo.netty.Message;
import cn.scu.imc.hiver.vo.netty.MessageHeader;
import cn.scu.imc.hiver.vo.netty.MessageType;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * 新增文件业务处理Handler类
 * 交给Spring 托管
 */
@Service
@ChannelHandler.Sharable
public class FileServerHandler extends SimpleChannelInboundHandler<Message> {
    private static final Log LOG = LogFactory.getLog(FileServerHandler.class);
    private static final String workSpace = String.valueOf(PropertiesUtils.getValueByKey("hive.netty.local.workspace"));

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        if (msg.getMessageHeader() != null && msg.getMessageHeader().getType() == MessageType.SERVICE_FILE_UPLOAD_REQ.value()) {
            LOG.info(msg);
            LOG.info("客户端开始接收文件");
            FileUpload fileUpload = (FileUpload) msg.getBody();
            String fileName = fileUpload.getFileName();
            String projectName = fileUpload.getProjectName();
            long version = fileUpload.getVersion();
            String projectDirName =  workSpace + File.separator + projectName + File.separator + version;
            File directory = new File(projectDirName);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String path = projectDirName + File.separator + fileName;
            int index = fileUpload.getIndex();
            File file = new File(path);
            if (index == 0) {
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
            }
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(fileUpload.getIndex() * 1024);
            randomAccessFile.write(fileUpload.getBytes());
            randomAccessFile.close();
            if (fileUpload.getTotal() == fileUpload.getIndex()) {
                Message messageResp = new Message();
                MessageHeader messageHeader = new MessageHeader();
                messageHeader.setSessionID(msg.getMessageHeader().getSessionID());
                messageHeader.setType(MessageType.SERVICE_FILE_DONE_RESP.value());
                msg.setMessageHeader(messageHeader);
                ctx.writeAndFlush(messageResp);
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
