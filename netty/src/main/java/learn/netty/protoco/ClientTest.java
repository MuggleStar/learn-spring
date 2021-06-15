package learn.netty.protoco;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.util.Scanner;

/**
 * @author lujianrong
 * @date 2021/5/11
 */
public class ClientTest {


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
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("Encoder", new ProtobufEncoder());
                            pipeline.addLast("Decoder", new ProtobufDecoder(ProtoModel.MessageModel.getDefaultInstance()));
                            pipeline.addLast(new ClientHandler());
                        }
                    });

            System.out.println("Client is OK ...");
            // 启动客户端去连接服务器
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 4396);

            // 关闭通道
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            loopGroup.shutdownGracefully();
        }
    }


}
