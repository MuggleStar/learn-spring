package learn.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author lujianrong
 * @date 2021/6/1
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {


    /**
     * 定义一个channel 组，管理所有的channel
     * GlobalEventExecutor.INSTANCE) 是全局的事件执行器，是一个单例
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    /**
     * 消息读取
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        Channel currentChannel = ctx.channel();
        // 消息分发处理
        for (Channel channel : channelGroup) {
            if (channel.equals(currentChannel)) {
                channel.writeAndFlush("[自己]发送了消息" + msg);
            } else {
                channel.writeAndFlush("[客户]" + channel.remoteAddress() + " 发送了消息" + msg);
            }
        }
    }

    /**
     * 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.close();
    }


    /**
     * channel 处于活动状态, 提示 xx上线
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线了~");
    }

    /**
     * channel 处于不活动状态, 提示 xx离线了
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 离线了~");
    }


    /**
     * 断开连接, 将xx客户离开信息推送给当前在线的客户
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 离开了");
        System.out.println("channelGroup size" + channelGroup.size());
    }

    /**
     * 表示连接建立，一旦连接，第一个被执行。将当前channel 加入到  channelGroup
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        //将该客户加入聊天的信息推送给其它在线的客户端
        // writeAndFlush 方法会将 channelGroup 中所有的 channel 遍历，并发送消息
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 加入聊天");
        channelGroup.add(channel);

    }

}
