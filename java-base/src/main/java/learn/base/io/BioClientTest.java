package learn.base.io;

import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * 同步阻塞IO服务端
 *
 * @author lujianrong
 * @date 2021/4/18
 */
public class BioClientTest {

    /**
     * 启动
     */
    @Test
    public void run () throws IOException {

        Socket socket = new Socket("127.0.0.1", 4396);

        Scanner scanner = new Scanner(System.in);
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());

        while (true) {
            if (scanner.hasNext()) {
                socketOut.write(scanner.nextLine());
                socketOut.flush();
            }
        }
    }



}
