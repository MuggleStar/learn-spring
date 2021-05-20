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

        // 进制
        int base = chars.length;

        Long max = 1L;
        for (int i=0; i< 10; i++){
            max = max*base;
        }
        System.out.println(max);

        // 3656158440062976 = 36*10
        // 取值 1000000000000000 防止-i反转后大于36*10
        max = 1000000000000000L;
        ShortIDUtil util = new ShortIDUtil();
        int i = 0;
        while (true){
            i++;
            Long value =  max - i;
            Long newValue = Long.parseLong(new StringBuffer(value.toString()).reverse().toString());

            String id1 = util.change(newValue);
            System.out.println(i+"=="+id1);
        }

    }


    public String change(long value) {

        int base = chars.length;

        String result = "";

        long left = value;
        while (left > 0){
            long longindex = left%base;
            int index = (int) longindex;
            result += chars[index];
            left = left/base;
        }
        return result;
    }




}
