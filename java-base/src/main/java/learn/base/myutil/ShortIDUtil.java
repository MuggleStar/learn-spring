package learn.base.myutil;

/**
 * @author lujianrong
 * @date 2021/5/20
 */
public class ShortIDUtil {

    public static String[] chars = {"0","1","2","3","4","5","6","7","8","9",
            // "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
            "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};


    public static void main(String[] args) {

        // 3656158440062976 = 36*10
        // 取值 1000000000000000 减去 i 后反转 再加上 1000000000000000 得到 1000000000000000 ~ 1999999999999999 之间的一个数
        Long max = 1000000000000000L;
        Long add = 1000000000000000L;
        ShortIDUtil util = new ShortIDUtil();
        long i = 99999999999998L;
        while (true){
            Long value =  i > max ? i - max : max - i;
            StringBuffer stringBuffer = new StringBuffer(value.toString());
            stringBuffer.reverse();
            Long newValue = Long.parseLong(stringBuffer.toString());
            newValue = newValue + add;
            StringBuffer idBuffer = util.change(newValue);
            System.out.println(i+"=="+newValue+"=="+idBuffer.toString());
            i++;
        }

    }


    public StringBuffer change(long value) {

        int base = chars.length;

        StringBuffer stringBuffer = new StringBuffer();

        long left = value;
        while (left > 0){
            long longindex = left%base;
            int index = (int) longindex;
            stringBuffer.append(chars[index]);
            left = left/base;
        }
        return stringBuffer;
    }




}
