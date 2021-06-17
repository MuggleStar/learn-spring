package learn.netty.protocobuff;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import org.junit.Test;

/**
 * @author lujianrong
 * @date 2021/5/11
 */
public class ServerTest {

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
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("Encoder", new ProtobufEncoder());
                            pipeline.addLast("Decoder", new ProtobufDecoder(ProtoModel.MessageModel.getDefaultInstance()));
                            pipeline.addLast(new ServerHandler());
                        }
                    });

            System.out.println("Server is ready ...");
            // 绑定端口并同步，启动服务器
            ChannelFuture channelFuture = serverBootstrap.bind(4396).sync();

            //关闭通道
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
