package learn.base.nio;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * nio 文件读写
 *
 * @author lujianrong
 * @date 2021/4/18
 */
public class NioFileChannelTest {


    /**
     * 文件拷贝-01
     */
    @Test
    public void fileCopy01() throws IOException {

        FileInputStream fileInputStream = new FileInputStream("src/main/resources/file-01.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/file-02.txt");

        FileChannel inputChannel = fileInputStream.getChannel();
        FileChannel outputChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (true) {
            // 清空buffer：position = 0; limit = capacity; mark = -1;
            byteBuffer.clear();
            int read = inputChannel.read(byteBuffer);
            if (read == -1) {
                break;
            }
            // 输入输出反转：limit = position; position = 0; mark = -1;
            byteBuffer.flip();

            outputChannel.write(byteBuffer);
        }
        fileInputStream.close();
        fileOutputStream.close();

    }

    /**
     * 文件拷贝-02
     */
    @Test
    public void fileCopy02() throws IOException {

        FileInputStream fileInputStream = new FileInputStream("src/main/resources/file-01.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/file-03.txt");

        FileChannel inputChannel = fileInputStream.getChannel();
        FileChannel outputChannel = fileOutputStream.getChannel();

        // 使用 transferTo 完成拷贝
        //在linux下一个transferTo 方法就可以完成传输
        //在windows 下 一次调用 transferTo 只能发送8m , 就需要分段传输文件, 而且要主要
        //transferTo 底层使用到零拷贝
        long size = inputChannel.size();



        inputChannel.transferTo(0,inputChannel.size(),outputChannel);

        fileInputStream.close();
        fileOutputStream.close();

    }



}
