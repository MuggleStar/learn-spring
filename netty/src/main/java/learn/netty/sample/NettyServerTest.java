package learn.netty.sample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import org.junit.Test;

/**
 * @author lujianrong
 * @date 2021/5/11
 */
public class NettyServerTest {

    @Test
    public void runServer(){

        //创建BossGroup 和 WorkerGroup
        //说明
        //1. 创建两个线程组 bossGroup 和 workerGroup
        //2. bossGroup 只是处理连接请求 , 真正的和客户端业务处理，会交给 workerGroup完成
        //3. 两个都是无限循环
        //4. bossGroup 和 workerGroup 含有的子线程(NioEventLoop)的个数。默认实际cpu核数 * 2

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            // 设置线程组、通道、线程队列连接个数、保持活动连接状态
            serverBootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ServerInitializerTest());

            System.out.println("Server is ready ...");

            // 绑定端口并同步，启动服务器
            ChannelFuture channelFuture = serverBootstrap.bind(4396).sync();

            // 注册监听器
            channelFuture.addListener(new ServerListenerTest());

            // 关闭通道
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 监听器
     */
    public static class ServerListenerTest implements ChannelFutureListener{

        /**
         * 对关闭通道进行监听
         * @param future
         * @throws Exception
         */
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            if(future.isSuccess()) {
                System.out.println("监听端口 6668 成功");
            } else {
                System.out.println("监听端口 6668 失败");
            }
        }
    }


    /**
     * 通道初始化对象
     */
    public static class ServerInitializerTest extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            System.out.println("initChannel");
            // 加入自己的handler
            ChannelPipeline pipeline = socketChannel.pipeline();
            pipeline.addLast("ServerHandlerTest2",new ServerHandlerTest());
            System.out.println("initChannel finish ...");
        }
    }


    /**
     * 自定义Handler
     */
    public static class ServerHandlerTest extends ChannelInboundHandlerAdapter {

        /**
         * 读取事件触发
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

            ByteBuf buf = (ByteBuf) msg;
            System.out.println("客户端消息:" + buf.toString(CharsetUtil.UTF_8));
            System.out.println("客户端地址： "+ ctx.channel().remoteAddress());

            // 回复
            ctx.writeAndFlush(Unpooled.copiedBuffer("Hello, client!" + buf.toString(CharsetUtil.UTF_8), CharsetUtil.UTF_8));
        }
    }


}
