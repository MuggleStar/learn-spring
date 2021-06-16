package learn.netty.iobdhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author lujianrong
 * @date 2021/6/10
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println("initChannel");
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new MessageDecoderLong());
        pipeline.addLast(new ServerHandler());

    }
}
