package learn.base.util.conturrent;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

/**
 * CompletableFuture 使用
 *
 * @author Madiosn
 */
public class CompletableFutureTest {

    /**
     * 自定义线程池，也可使用系统默认
     */
    public static ExecutorService executor = Executors.newFixedThreadPool(10);


    /**
     * 线程执行对象
     */
    public TaskRunner task = new TaskRunner();

    @Test
    public  void run() throws Exception {

        // runAsync 用于执行没有返回值的异步任务
        CompletableFuture<Void> runAsyncResp = CompletableFuture.runAsync(task::zero,executor)
                .exceptionally(e -> {
                    System.out.println("Zero出错！");
                    return null;
                });

        // supplyAsync方法用于执行带有返回值的异步任务
        CompletableFuture<String> supplyAsyncRes = CompletableFuture.supplyAsync(() -> task.onlyReturn(),executor)
                .exceptionally(e -> {
                    System.out.println("onlyReturn出错！");
                    return null;
                });

        CompletableFuture<String> supplyAsyncRes2 = CompletableFuture.supplyAsync(() -> task.inAndReturn("1"),executor)
                .exceptionally(e -> {
                    System.out.println("inAndReturn出错！");
                    return null;
                });

        // thenCompose方法用于连接两个CompletableFuture任务，如下代表supplyAsync结束后将执行结果交由另外一个CompletableFuture处理，然后将执行链路最终赋值给thenCompose
        CompletableFuture<String> thenComposeResp = supplyAsyncRes.thenCompose(re ->
                CompletableFuture.supplyAsync(() -> task.inAndReturn(re),executor)
        ).exceptionally(e -> {
            System.out.println("inAndReturn出错！");
            return null;
        });

        // thenAccept方法用于将一个任务的结果，传给需要该结果的任务，如下表示supplyAsync的执行需要supplyAsyncRes的结果，与thenApply不同的是，这个方法没有有返回值
        CompletableFuture<Void> thenAcceptResp = supplyAsyncRes.thenAccept(re ->
                CompletableFuture.supplyAsync(() -> task.inAndReturn(re),executor)
        ).exceptionally(e -> {
            System.out.println("inAndReturn出错！");
            return null;
        });

        // thenCombine方法用于连接多个异步任务的结果，如下ab方法需要futureA和futureB的执行结果，那么就可以使用thenCombine进行连接
        CompletableFuture<String> thenCombineResp = supplyAsyncRes.thenCombine(supplyAsyncRes2, (a, b) -> task.twoInAndReturn(a, b))
                .exceptionally(e -> {
                    System.out.println("twoInAndReturn出错！");
                    return null;
                });

        CompletableFuture.allOf(runAsyncResp, supplyAsyncRes, supplyAsyncRes2, thenComposeResp, thenAcceptResp, thenCombineResp).get();

        System.out.println("=======================");
        System.out.println(runAsyncResp.get());
        System.out.println(supplyAsyncRes.get());
        System.out.println(supplyAsyncRes2.get());
        System.out.println(thenComposeResp.get());
        System.out.println(thenAcceptResp.get());
        System.out.println(thenCombineResp.get());
        System.out.println("=======================");

        // executor.isTerminated(); 判断子线程任务是否执行完
        executor.shutdown();
    }


    static class TaskRunner {

        public void zero() {
            try {
                sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("zero方法触发！");
        }

        public String onlyReturn() {
            try {
                sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("onlyReturn方法触发！");
            return "onlyReturn";
        }

        public String inAndReturn(String a) {
            try {
                sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("inAndReturn方法触发！");
            return a;
        }

        public String twoInAndReturn(String a, String b) {
            try {
                sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("twoInAndReturn方法触发！");
            return a + "|" + b;
        }

        public void onlyIn(String a) {
            try {
                sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("onlyIn方法触发！");
        }
    }

}
