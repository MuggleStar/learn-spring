package learn.base.io;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 同步阻塞IO客户端
 *
 * @author lujianrong
 * @date 2021/4/18
 */
public class BioServerTest {


    /**
     * 启动
     */
    @Test
    public void run() throws IOException {

        // 如果有客户端连接，就创建一个线程，与之通讯
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(4396);

        while (true) {
            System.out.println("等待连接。。。");
            Socket socket = serverSocket.accept();
            cachedThreadPool.execute(() -> {
                this.handle(socket);
            });
        }
    }

    public void handle(Socket socket) {

        try {
            System.out.println(Thread.currentThread().getId() + "连接上。。。");

            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[4096];
            while (true) {
                int length = inputStream.read(buffer);
                if (length != -1) {
                    System.out.println(Thread.currentThread().getId() + "say:" +new String(buffer, 0, length));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getId() + "connect close 。。。");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
