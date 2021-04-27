package learn.base.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * nio群聊系统，客户端
 *
 * @author lujianrong
 * @date 2021/4/24
 */
public class NioGroupChatClientTest {


    /**
     * 启动
     */
    @Test
    public void run() throws IOException {

        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",4396));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        String userName = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(userName + "init ...");


        ExecutorService threadPool = Executors.newCachedThreadPool();

        threadPool.execute(() -> {
            while (true) {
                this.readInfo(selector);
            }
        });


        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            this.sendInfo(socketChannel,input);
        }


    }

    /**
     * 发送消息
     * @param socketChannel
     */
    private void sendInfo(SocketChannel socketChannel,String input) {

        try {
            socketChannel.write(ByteBuffer.wrap(input.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取服务器回复消息
     *
     * @param selector
     */
    private void readInfo(Selector selector) {

        try {
            int readChannels = selector.select();
            if (readChannels > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.read(buffer);
                        System.out.println(new String(buffer.array()));
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
