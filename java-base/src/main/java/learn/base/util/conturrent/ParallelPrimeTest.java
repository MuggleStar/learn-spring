package learn.base.util.conturrent;

import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * 并行流-寻找质数
 *
 * @author Madiosn
 */
public class ParallelPrimeTest {

    /**查找范围*/
    static final int COUNT = 100_0000;


    @Test
    public void test(){
        Date date = new Date();
        List<String> collect = IntStream.iterate(2, i -> i + 1)
                .parallel() //并行流，并行判断是否是质数
                .filter(ParallelPrimeTest::isPrime)
                .limit(COUNT)
                .mapToObj(Long::toString)
                .collect(Collectors.toList());

        Date date2 = new Date();
        System.out.println(collect.get(collect.size()-1));
        System.out.println(date.getTime() - date2.getTime());

    }

    public static boolean isPrime(long n) {
        // (long) Math.sqrt(n)，取n的平方根，减少noneMatch()函数计算量
        // 2到n的平方根，每个数分别除n，有一个余数为0则是偶数，否则质数
        return LongStream.rangeClosed(2, (long) Math.sqrt(n)).noneMatch(i -> n % i == 0);
    }
}
