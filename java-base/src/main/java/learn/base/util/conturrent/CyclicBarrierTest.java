package learn.base.util.conturrent;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * 线程栅栏
 *
 * @author MuggleStar
 * @date 2020/9/3 1:51
 */
public class CyclicBarrierTest {


    @Test
    public void test() {

        int number = 10;

        CyclicBarrier barrier = new CyclicBarrier(number + 1, () -> System.out.println("开始后续任务"));

        ExecutorService executorService =  Executors.newFixedThreadPool(number);

        for (int i=0;i<number;i++) {
            executorService.execute(()->{
                runTask();
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("结束");
    }

    public void runTask(){

        long time = (long) (Math.random() * 1000);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }

}
