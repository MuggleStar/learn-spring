package learn.base.myutil;

import com.beust.jcommander.internal.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.commons.compress.utils.IOUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Map;

/**
 * 二维码生成器
 *
 * @author lujianrong
 * @date 2021/5/29
 */
public class QRCodeUtil {


    private static QRCodeWriter writer = new QRCodeWriter();


    /**
     * 生成二维码
     * @param content
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage createCodeImageBuffered(String content, int width, int height) {
        Map<EncodeHintType, Object> hints = Maps.newHashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        try {
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 生成二维码
     * @param content
     * @param width
     * @param height
     * @return
     */
    public static String createCodeImageBase64(String content, int width, int height) {
        Map<EncodeHintType, Object> hints = Maps.newHashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        try {

            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageOutputStream imageOutput = ImageIO.createImageOutputStream(byteArrayOutputStream);
            ImageIO.write(bufferedImage, "png", imageOutput);
            InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

            byte[] bytes = IOUtils.toByteArray(inputStream);
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
