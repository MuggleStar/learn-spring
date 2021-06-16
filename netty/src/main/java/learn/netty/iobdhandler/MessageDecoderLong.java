package learn.netty.iobdhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Long 解码器
 * @author lujianrong
 * @date 2021/6/10
 */
public class MessageDecoderLong extends ReplayingDecoder<Long> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MessageDecoderLong===decode");
        out.add(in.readLong());
    }
}
