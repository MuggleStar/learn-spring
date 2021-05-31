package learn.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * 处理业务逻辑
 *
 * TextWebSocketFrame 类型，表示一个文本帧(frame)
 *
 * @author lujianrong
 * @date 2021/5/28
 */
public class TextWebSocketFrameHandlerTest extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("channelRead0 触发");
        System.out.println("服务器收到消息：" + msg.text());
        ctx.channel().writeAndFlush(new TextWebSocketFrame(LocalDateTime.now() +":"+ msg.text()));
    }

    /**
     * 当web客户端连接后 触发
     * @param ctx
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        // id表示唯一值，LongText唯一 ShortText不唯一
        System.out.println("handlerAdded 触发");
        System.out.println(ctx.channel().id().asLongText());
        System.out.println(ctx.channel().id().asShortText());
    }


    /**
     * 连接被移除时 触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
     public void handlerRemoved(ChannelHandlerContext ctx) throws Exception{
        System.out.println("handlerRemoved触发");
        System.out.println(ctx.channel().id().asLongText());
     }

    /**
     * 异常处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
     @Override
     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
         System.out.println("exceptionCaught触发");
         System.out.println(cause.getMessage());
         ctx.close();
     }












}
