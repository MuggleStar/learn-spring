package learn.netty.protocobuff;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Random;

/**
 * @author lujianrong
 * @date 2021/6/15
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当通道就绪就会触发该方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        int i = 0;
        while (i < 10) {
            ProtoModel.MessageModel messageModel = null;

            // 随机发送一个对象
            int randInt = new Random().nextInt(2);
            int y = randInt % 2;

            if (y == 0) {
                messageModel = ProtoModel.MessageModel.newBuilder()
                        .setDataType(ProtoModel.MessageModel.DataType.StudentType)
                        .setStudent(ProtoModel.Student.newBuilder().setId(i).setName("张三" + i).build())
                        .build();
            } else {
                messageModel = ProtoModel.MessageModel.newBuilder()
                        .setDataType(ProtoModel.MessageModel.DataType.WorkerType)
                        .setWorker(ProtoModel.Worker.newBuilder().setId(i).setName("李四" + i).setAge(20).build())
                        .build();
            }


            System.out.println("发送消息" + i);
            // 发送消息
            /**
             * 循环调用 writeAndFlush，会导致消息丢失。sleep一会可以解决
             */
            ctx.writeAndFlush(messageModel);
            i++;
        }
    }
}
