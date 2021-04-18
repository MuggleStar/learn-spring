package learn.base.myutil;

import java.math.BigDecimal;

/**
 * 常用工具
 *
 * @author MuggleStar
 * @date 2020/12/7 23:22
 */
public class CommUtil {

    /**
     * 空值判断
     *
     * @param st
     * @return
     */
    public static boolean isEmpty(String st) {
        if (null == st || "".equals(st)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 强转int
     *
     * @param obj
     * @return
     */
    public static int toInt(Object obj) {
        try {
            return obj != null && !obj.toString().equals("") ? Integer.parseInt(obj.toString().trim()) : 0;
        } catch (Exception var2) {
            return 0;
        }
    }

    /**
     * 强转long
     *
     * @param obj
     * @return
     */
    public static long toLong(Object obj) {
        try {
            return obj != null && !obj.toString().equals("") ? Long.parseLong(obj.toString().trim()) : 0L;
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * 强转double
     *
     * @param obj
     * @return
     */
    public static double toDouble(Object obj) {
        try {
            return obj != null && !obj.toString().equals("") ? Double.parseDouble(obj.toString().trim()) : 0.0D;
        } catch (Exception e) {
            return 0.0D;
        }
    }



    /**
     * 判断字符串是否是数字，
     * 支持格式如下：
     * "33" "+33" "033.30" "-.33" ".33" " 33." "000.000 "
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        int begin = 0;
        boolean once = true;
        if (str == null || str.trim().equals("")) {
            return false;
        }
        str = str.trim();
        if (str.startsWith("+") || str.startsWith("-")) {
            if (str.length() == 1) {
                // "+" "-"
                return false;
            }
            begin = 1;
        }
        for (int i = begin; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                if (str.charAt(i) == '.' && once) {
                    // '.' can only once
                    once = false;
                } else {
                    return false;
                }
            }
        }
        if (str.length() == (begin + 1) && !once) {
            // "." "+." "-."
            return false;
        }
        return true;
    }

    /**
     * 判断是否时整数
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        int begin = 0;
        if (str == null || str.trim().equals("")) {
            return false;
        }
        str = str.trim();
        for (int i = begin; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Double加法运算
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double addDouble(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * Double减法运算
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double subDouble(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * Double乘法运算
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double mulDouble(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * Double除法运算
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double divideDouble(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2).doubleValue();
    }

}
