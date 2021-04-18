package learn.base.algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 贪心算法
 *
 * @author Madison.lu
 * @date 2020/8/18 9:21
 */
public class GreedyTest {


    static class Treasure{

        /**
         * 价值
         */
        private int value;
        /**
         * 体积
         */
        private int volume;
        /**
         * 单价
         */
        private int price;

        public Treasure(int value, int volume, int price) {
            this.value = value;
            this.volume = volume;
            this.price = price;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getVolume() {
            return volume;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "Treasure{" +
                    "value=" + value +
                    ", volume=" + volume +
                    ", price=" + price +
                    '}';
        }
    }

    // 物品列表
    List<Treasure> treasureList = new ArrayList<>();
    {
        treasureList.add(new Treasure(18,2,9));
        treasureList.add(new Treasure(24,4,6));
        treasureList.add(new Treasure(42,6,7));
        treasureList.add(new Treasure(24,8,3));
        treasureList.add(new Treasure(50,10,5));
    }

    /**
     *
     * 可分割时，单价最高贪心策略能获得最优解
     * 不可分割时，不一定能获得最优解
     */
    @Test
    public void test(){

        int packageVolume = 10;

        // 价值最大策略
        List<Treasure> valueList = treasureList
                .stream()
                .sorted(Comparator.comparing(Treasure::getValue).reversed())
                .collect(Collectors.toList());
        // 体积最小策略
        List<Treasure> volumeList = treasureList
                .stream()
                .sorted(Comparator.comparing(Treasure::getVolume))
                .collect(Collectors.toList());
        // 单价最大策略
        List<Treasure> priceList = treasureList
                .stream()
                .sorted(Comparator.comparing(Treasure::getPrice).reversed())
                .collect(Collectors.toList());

        divisible(packageVolume,valueList);
        System.out.println("=============================");
        divisible(packageVolume,volumeList);
        System.out.println("=============================");
        divisible(packageVolume,priceList);
        System.out.println("===================================================");
        unDivisible(packageVolume,valueList);
        System.out.println("=============================");
        unDivisible(packageVolume,volumeList);
        System.out.println("=============================");
        unDivisible(packageVolume,priceList);
        System.out.println("=============================");

    }

    /**
     * 可分割
     */
    public void divisible(int packageVolume ,List<Treasure> treasureList){
        int totalValue = 0;
        for (Treasure treasure : treasureList) {

            int currentVolume = Math.min(packageVolume, treasure.getVolume());
            int currentValue = currentVolume * treasure.getPrice();
            totalValue += currentValue;
            packageVolume -= currentVolume;

            System.out.print(treasure);
            System.out.println("：：currentVolume = " + currentVolume + "：：totalValue = " + totalValue + "：：packageVolume = " + packageVolume);
            if (packageVolume <= 0) {break;}
        }
    }

    /**
     * 不可分割
     */
    public void unDivisible(int packageVolume ,List<Treasure> treasureList){
        int totalValue = 0;
        for (Treasure treasure : treasureList) {
            int currentVolume;
            if (packageVolume < treasure.getVolume()) {
                continue;
            } else {
                currentVolume = treasure.getVolume();
            }
            int currentValue = currentVolume * treasure.getPrice();
            totalValue += currentValue;
            packageVolume -= currentVolume;

            System.out.print(treasure);
            System.out.println("：：currentVolume = " + currentVolume + "：：totalValue = " + totalValue + "：：packageVolume = " + packageVolume);
            if (packageVolume <= 0) {break;}
        }
    }

}
