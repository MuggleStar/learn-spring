package learn.netty.iobdhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * @author lujianrong
 * @date 2021/6/10
 */
public class ClientHandler extends SimpleChannelInboundHandler<Long> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端channelActive");
        // 客户端就绪后发消息
        ctx.writeAndFlush(4396L);
        Thread.sleep(100L);
        ctx.writeAndFlush(4397L);

    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {



    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("message ===== " + msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常消息=" + cause.getMessage());
        ctx.close();
    }
}
