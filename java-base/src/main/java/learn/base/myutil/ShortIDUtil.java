package learn.base.myutil;

/**
 * @author lujianrong
 * @date 2021/5/20
 */
public class ShortIDUtil {

    private static String[] chars = {"0","1","2","3","4","5","6","7","8","9",
            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};


    /**
     * 传入一个不大于 1000000000000000L 的数，返回 10 位的字符串，入参不重复则出参唯一
     *
     * 取值 1000000000000000 减去 value 后反转 再加上 1000000000000000 得到 1000000000000000 ~ 1999999999999999 之间的一个数
     * 的数转 36 进制
     *
     * @param value
     * @return
     */
    public static String getStringId(long value) {

        Long maxValue = 1000000000000000L;
        if (value > maxValue) {
            throw new RuntimeException("失败");
        }
        String newValueStr = new StringBuilder(Long.toString(maxValue - value)).reverse().toString();
        long newValue = Long.parseLong(newValueStr) + maxValue;

        int base = chars.length;
        StringBuilder stringBuffer = new StringBuilder();
        long left = newValue;
        while (left > 0){
            long longIndex = left%base;
            int index = (int) longIndex;
            stringBuffer.append(chars[index]);
            left = left/base;
        }
        return stringBuffer.toString();
    }


}
