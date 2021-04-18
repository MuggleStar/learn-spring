package learn.base.nio;

import org.junit.Test;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 文件传输测试
 *
 * @author lujianrong
 * @date 2021/4/18
 */
public class NioFileTransportTest {


    /**
     * 服务端
     */
    @Test
    public void runServer()  throws Exception {

        InetSocketAddress address = new InetSocketAddress(4396);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(address);

        // 创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(8388608);

        long totalSize = 0;
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();

            int readCount = 0;
            while (-1 != readCount) {
                try {
                    readCount = socketChannel.read(byteBuffer);
                    totalSize += readCount;
                    System.out.println(totalSize);
                }catch (Exception ex) {
                    break;
                }
                // 倒带 position = 0; mark = -1;
                byteBuffer.rewind();
            }
        }
    }


    /**
     * 客户端
     */
    @Test
    public void runClient() throws Exception {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 4396));
        String filename = "D:\\download\\CentOS-7-x86_64-DVD-1804.iso";

        //得到一个文件channel
        FileChannel fileChannel = new FileInputStream(filename).getChannel();

        //准备发送
        long startTime = System.currentTimeMillis();
        //transferTo 底层使用到零拷贝
        //在linux下 transferTo 方法就可以完成传输，在windows 下一次只能发送8m , 就需要分段传输文件, 8388608
        long totalSize = fileChannel.size();
        System.out.println(totalSize);
        long sendSize = 0;
        while (sendSize < totalSize) {
            long size = Math.min(8388608, totalSize - sendSize);
            fileChannel.transferTo(sendSize, size , socketChannel);
            sendSize += size;
        }

        // 结束
        System.out.println("发送的总的字节数 =" + sendSize + " 耗时:" + (System.currentTimeMillis() - startTime));
        fileChannel.close();

    }


    /**
     * 阻塞IO客户端
     */
    @Test
    public void runOldClient() throws Exception {
        Socket socket = new Socket("localhost", 4396);

        InputStream inputStream = new FileInputStream("D:\\download\\CentOS-7-x86_64-DVD-1804.iso");

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[8388608];
        long readCount;
        long total = 0;

        long startTime = System.currentTimeMillis();

        while ((readCount = inputStream.read(buffer)) >= 0) {
            total += readCount;
            dataOutputStream.write(buffer);
        }

        System.out.println("发送总字节数： " + total + ", 耗时： " + (System.currentTimeMillis() - startTime));

        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }



}
