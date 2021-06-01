package learn.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.junit.Test;

/**
 * @author lujianrong
 * @date 2021/6/1
 */
public class GroupChatServerTest {


    @Test
    public void runServer() throws Exception {

        //创建两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG,128);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);

            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {

                    //获取到pipeline
                    ChannelPipeline pipeline = ch.pipeline();
                    //向pipeline加入解码器
                    pipeline.addLast("decoder", new StringDecoder());
                    //向pipeline加入编码器
                    pipeline.addLast("encoder", new StringEncoder());

                    pipeline.addLast(new GroupChatServerHandler());
                }
            });

            System.out.println("netty 服务器启动");
            ChannelFuture channelFuture = bootstrap.bind(4396).sync();
            //监听关闭
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
