package learn.netty.protoco;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lujianrong
 * @date 2021/6/15
 */
public class ServerHandler extends SimpleChannelInboundHandler<ProtoModel.MessageModel> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtoModel.MessageModel msg) throws Exception {


        if (ProtoModel.MessageModel.DataType.StudentType.equals(msg.getDataType())) {
            ProtoModel.Student student = msg.getStudent();
            System.out.println("name ==== " + student.getName());
        } else if (ProtoModel.MessageModel.DataType.WorkerType.equals(msg.getDataType())) {
            ProtoModel.Worker worker = msg.getWorker();
            System.out.println("name ==== " + worker.getName());
        } else {
            System.out.println("XXXXXXXXXXXXXXXXX");
        }


    }
}
