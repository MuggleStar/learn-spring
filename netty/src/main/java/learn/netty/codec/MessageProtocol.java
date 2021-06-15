package learn.netty.codec;

/**
 * 协议包，把数据转换成二进制传输
 * @author lujianrong
 * @date 2021/6/10
 */
public class MessageProtocol {

    private int len;
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
