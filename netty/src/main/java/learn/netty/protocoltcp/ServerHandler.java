package learn.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * @author lujianrong
 * @date 2021/6/10
 */
public class ServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

        //接收到数据，并处理
        int len = msg.getLen();
        byte[] content = msg.getContent();
        String strMsg = new String(content, StandardCharsets.UTF_8);

        System.out.println("接收到数据长度====" + len);
        System.out.println("接收到数据内容====" + strMsg);

        // 回复消息
        byte[]  returnContent = strMsg.getBytes(StandardCharsets.UTF_8);
        int returnLen = returnContent.length;

        // 创建协议包对象
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(returnLen);
        messageProtocol.setContent(returnContent);

        ctx.writeAndFlush(messageProtocol);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常消息=" + cause.getMessage());
        ctx.close();
    }

}
