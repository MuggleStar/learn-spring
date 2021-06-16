package learn.netty.iobdhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Long 编码器
 * @author lujianrong
 * @date 2021/6/10
 */
public class MessageEncoderLong extends MessageToByteEncoder<Long> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MessageEncoderLong===encode");
        out.writeLong(msg);
    }
}
