package cn.scu.imc.hiver.netty.rpc.base.kryocodec;


import cn.scu.imc.hiver.bo.vo.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 类说明：序列化的Handler
 */
public class KryoEncoder  extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message message,
                          ByteBuf out) throws Exception {
        KryoSerializer.serialize(message, out);
        ctx.flush();
    }
}
