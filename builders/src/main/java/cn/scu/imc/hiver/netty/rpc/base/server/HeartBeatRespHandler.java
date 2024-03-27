package cn.scu.imc.hiver.netty.rpc.base.server;


import cn.scu.imc.hiver.vo.netty.Message;
import cn.scu.imc.hiver.vo.netty.MessageHeader;
import cn.scu.imc.hiver.vo.netty.MessageType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 类说明：心跳
 */
public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter {

	private static final Log LOG
			= LogFactory.getLog(HeartBeatRespHandler.class);

    public void channelRead(ChannelHandlerContext ctx, Object msg)
	    throws Exception {
		Message message = (Message) msg;
		// 返回心跳应答消息
		if (message.getMessageHeader() != null
			&& message.getMessageHeader().getType() == MessageType.HEARTBEAT_REQ
				.value()) {
//			LOG.info("Receive client heart beat message : ---> "+ message);
			Message heartBeat = buildHeatBeat();
//			LOG.info("Send heart beat response message to ---> client");
			ctx.writeAndFlush(heartBeat);
			ReferenceCountUtil.release(msg);
		} else
			ctx.fireChannelRead(msg);
    }

    private Message buildHeatBeat() {
		Message message = new Message();
		MessageHeader header = new MessageHeader();
		header.setType(MessageType.HEARTBEAT_RESP.value());
		message.setMessageHeader(header);
		return message;
    }

}
