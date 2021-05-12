package learn.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.net.URI;

/**
 * @author lujianrong
 * @date 2021/5/11
 */
public class NettyHttpServerTest {

    @Test
    public void runServer(){

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            // 设置线程组、通道、线程队列连接个数、保持活动连接状态
            serverBootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializerTest());

            System.out.println("Server is ready ...");

            // 绑定端口并同步，启动服务器
            ChannelFuture channelFuture = serverBootstrap.bind(4396).sync();

            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
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
            pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
            pipeline.addLast("ServerHandlerTest",new ServerHandlerTest());
            System.out.println("initChannel finish ...");
        }
    }

    /**
     * 自定义Handler
     */
    public static class ServerHandlerTest extends SimpleChannelInboundHandler<HttpObject> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

            System.out.println("ctx 类型="+ctx.getClass());
            System.out.println("客户端地址" + ctx.channel().remoteAddress());

            if (! (msg instanceof HttpRequest)) {
                System.out.println("非httpRequest");
                return;
            }

            // 过滤请求
            HttpRequest httpRequest = (HttpRequest) msg;
            URI uri = new URI(httpRequest.uri());
            if("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求了 favicon.ico, 不做响应");
                return;
            }

            //回复信息给浏览器
            ByteBuf content = Unpooled.copiedBuffer("hello, 我是服务器", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/json");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            ctx.writeAndFlush(response);

        }

    }
}
