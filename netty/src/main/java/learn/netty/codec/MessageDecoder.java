package learn.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * 解码器
 * @author lujianrong
 * @date 2021/6/10
 */
public class MessageDecoder extends ReplayingDecoder<Void> {

    /**
     * // 二进制数据 ==> MessageProtocol 数据包(对象)
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        System.out.println("MessageDecoder===decode");

        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);

        // 封装成 MessageProtocol 对象，放入 out， 传递下一个handler业务处理
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(length);
        messageProtocol.setContent(content);

        out.add(messageProtocol);
    }
}
