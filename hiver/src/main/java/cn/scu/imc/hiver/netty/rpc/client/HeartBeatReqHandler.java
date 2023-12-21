package cn.scu.imc.hiver.netty.rpc.client;

import cn.scu.imc.api.vo.netty.Message;
import cn.scu.imc.api.vo.netty.MessageHeader;
import cn.scu.imc.api.vo.netty.MessageType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 类说明：心跳请求处理
 */
public class HeartBeatReqHandler extends ChannelInboundHandlerAdapter {

    private static final Log LOG = LogFactory.getLog(HeartBeatReqHandler.class);

    private volatile ScheduledFuture<?> heartBeat;

    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        Message message = (Message) msg;
        // 握手或者说登录成功，主动发送心跳消息
        if (message.getMessageHeader() != null
                && message.getMessageHeader().getType() == MessageType.LOGIN_RESP
                .value()) {
            heartBeat = ctx.executor().scheduleAtFixedRate(
                    new HeartBeatTask(ctx), 0,
                    5000,
                    TimeUnit.MILLISECONDS);
            ReferenceCountUtil.release(msg);
        //如果是心跳应答
        } else if (message.getMessageHeader() != null
                && message.getMessageHeader().getType() == MessageType.HEARTBEAT_RESP
                .value()) {
//            LOG.info("Client receive server heart beat message : ---> ");
            ReferenceCountUtil.release(msg);
        //如果是其他报文，传播给后面的Handler
        } else
            ctx.fireChannelRead(msg);
    }

    private class HeartBeatTask implements Runnable {
        private final ChannelHandlerContext ctx;
        //心跳计数，可用可不用，已经有超时处理机制
        private final AtomicInteger heartBeatCount;

        public HeartBeatTask(final ChannelHandlerContext ctx) {
            this.ctx = ctx;
            heartBeatCount = new AtomicInteger(0);
        }

        @Override
        public void run() {
            Message heatBeat = buildHeatBeat();
//            LOG.info("Client send heart beat messsage to server : ---> "
//                            + heatBeat);
            ctx.writeAndFlush(heatBeat);
        }

        private Message buildHeatBeat() {
            Message message = new Message();
            MessageHeader messageHeader = new MessageHeader();
            messageHeader.setType(MessageType.HEARTBEAT_REQ.value());
            message.setMessageHeader(messageHeader);
            return message;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        if (heartBeat != null) {
            heartBeat.cancel(true);
            heartBeat = null;
        }
        ctx.fireExceptionCaught(cause);
    }
}
