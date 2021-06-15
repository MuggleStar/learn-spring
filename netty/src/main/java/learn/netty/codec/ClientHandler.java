package learn.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * @author lujianrong
 * @date 2021/6/10
 */
public class ClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        // 客户端就绪后发10条消息
        for (int i = 0; i < 10; i++) {
            String message = "Hello world! ===" +i;
            byte[] content = message.getBytes(StandardCharsets.UTF_8);
            int length = content.length;

            // 创建协议包对象
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(content);
            ctx.writeAndFlush(messageProtocol);
        }
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

        int len = msg.getLen();
        byte[] content = msg.getContent();

        System.out.println("客户端接收到消息长度====" + len);
        System.out.println("客户端接收到消息内容====" + new String(content, StandardCharsets.UTF_8));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常消息=" + cause.getMessage());
        ctx.close();
    }
}
