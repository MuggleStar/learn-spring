package learn.netty.sample;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.util.Scanner;

/**
 * @author lujianrong
 * @date 2021/5/11
 */
public class NettyClientTest {


    @Test
    public void runClient(){

        // 客户端事件线程组
        EventLoopGroup loopGroup = new NioEventLoopGroup();

        try {
            // 客户端启动对象
            Bootstrap bootstrap = new Bootstrap();

            // 设置线程组、通道实现类、初始化对象
            bootstrap
                    .group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientInitializerTest());

            System.out.println("Client is OK ...");

            // 启动客户端去连接服务器
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 4396);
            int i =1;
            while (i<5) {
                i++;
                Scanner scanner = new Scanner(System.in);
                channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer(scanner.nextLine(), CharsetUtil.UTF_8));
            }

            // 关闭通道
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            loopGroup.shutdownGracefully();
        }
    }

    /**
     * 通道初始化对象
     */
    public static class ClientInitializerTest extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            System.out.println("initChannel ...");
            // 加入自己的handler
            socketChannel.pipeline().addLast(new ClientHandlerTest());
        }
    }


    /**
     * 自定义Handler
     */
    public static class ClientHandlerTest extends ChannelInboundHandlerAdapter {

        /**
         * 通道就绪触发
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("client == " + ctx);
            ctx.writeAndFlush(Unpooled.copiedBuffer("Hello, server!", CharsetUtil.UTF_8));
        }

        /**
         * 读取事件触发
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = (ByteBuf) msg;
            System.out.println("服务器回复的消息:" + buf.toString(CharsetUtil.UTF_8));
            System.out.println("服务器的地址： "+ ctx.channel().remoteAddress());
        }

        /**
         * 异常触发
         */
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

}
