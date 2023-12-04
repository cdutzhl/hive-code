package com.scu.imc.builder.rpc.base.kryocodec;

import com.scu.imc.builder.rpc.base.vo.MyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 *
 * 类说明：序列化的Handler
 */
public class KryoEncoder  extends MessageToByteEncoder<MyMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MyMessage message,
                          ByteBuf out) throws Exception {
        KryoSerializer.serialize(message, out);
        ctx.flush();
    }
}
