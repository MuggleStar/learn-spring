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

    /**
     *  decode 会根据接收的数据，被调用多次, 直到确定没有新的元素被添加到list, 或者是ByteBuf 没有更多的可读字节为止
     * 如果list out 不为空，就会将list的内容传递给下一个 ChannelInboundHandler 处理, 该处理器的方法也会被调用多次
     *
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MessageDecoderLong===decode");
        out.add(in.readLong());
    }
}
