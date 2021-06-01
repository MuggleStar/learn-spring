package learn.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.junit.Test;

import java.util.Scanner;

/**
 * @author lujianrong
 * @date 2021/6/1
 */
public class GroupChatClientTest {



    @Test
    public void runClient() throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();

        try {

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    //得到pipeline
                    ChannelPipeline pipeline = ch.pipeline();
                    //加入相关handler
                    pipeline.addLast("decoder", new StringDecoder());
                    pipeline.addLast("encoder", new StringEncoder());
                    //加入自定义的handler
                    pipeline.addLast(new GroupChatClientHandler());
                }
            });

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 4396).sync();

            //得到channel
            Channel channel = channelFuture.channel();
            System.out.println("====" + channel.localAddress() + "====");

            //通过channel 发送到服务器端
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                channel.writeAndFlush(msg);
            }
        } finally {
            group.shutdownGracefully();
        }
    }





}




