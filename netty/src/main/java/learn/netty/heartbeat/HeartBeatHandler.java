package learn.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author lujianrong
 * @date 2021/5/31
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    /**
     * @param ctx 上下文
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (!(evt instanceof IdleStateEvent)) {
            return;
        }

        //将  evt 向下转型 IdleStateEvent
        IdleStateEvent event = (IdleStateEvent) evt;
        String eventType = null;
        switch (event.state()) {
            case READER_IDLE:
                eventType = "读空闲";
                break;
            case WRITER_IDLE:
                eventType = "写空闲";
                break;
            case ALL_IDLE:
                eventType = "全空闲";
                break;
            default:
                break;
        }
        System.out.println(ctx.channel().remoteAddress() + "====" + eventType);

        //如果发生空闲，我们关闭通道
        // ctx.channel().close();
    }
}
