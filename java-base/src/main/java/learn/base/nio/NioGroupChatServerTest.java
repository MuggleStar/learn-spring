package learn.base.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * nio群聊系统，服务端
 *
 * @author lujianrong
 * @date 2021/4/24
 */
public class NioGroupChatServerTest {

    /**
     * 启动
     */
    @Test
    public void run() throws IOException {

        Selector selector = Selector.open();
        ServerSocketChannel listenChannel = ServerSocketChannel.open();
        listenChannel.socket().bind(new InetSocketAddress(4396));
        listenChannel.configureBlocking(false);
        listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        this.listen(selector,listenChannel);

    }


    /**
     * 监听端口
     * @param selector
     * @param listenChannel
     */
    private void listen(Selector selector,ServerSocketChannel listenChannel) {
        try {
            while (true) {
                int count = selector.select();
                if (count > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress()+"上线");
                        }
                        if (key.isReadable()) {
                            this.readData(key,selector);
                        }
                        iterator.remove();
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 接收数据
     *
     * @param key
     */
    private void readData(SelectionKey key, Selector selector) {

        SocketChannel socketChannel;

       try {

            socketChannel  = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = socketChannel.read(buffer);
            if (count > 0){
                String msg = new String(buffer.array());
                System.out.println("from client:"+ msg);
                // 转发其他客户端
                sendInfoToOtherClients(msg, socketChannel,selector);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendInfoToOtherClients(String msg, SocketChannel self , Selector selector) throws  IOException{

        for (SelectionKey key : selector.keys()) {
            SelectableChannel channel = key.channel();
            if (channel instanceof SocketChannel && channel != self) {
                SocketChannel socketChannel = (SocketChannel) channel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                socketChannel.write(buffer);

            }
        }
    }
























}
