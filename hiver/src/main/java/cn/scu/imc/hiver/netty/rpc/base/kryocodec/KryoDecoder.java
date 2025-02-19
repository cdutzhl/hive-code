package cn.scu.imc.hiver.netty.rpc.base.kryocodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 类说明：反序列化的Handler
 */
public class KryoDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)  {
        Object obj = KryoSerializer.deserialize(in);
        System.out.println(obj);
        out.add(obj);
    }
}
