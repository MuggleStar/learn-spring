package learn.base.algorithm;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 动态规划
 *
 * @author Madison.lu
 * @date 2020/8/18 11:08
 */
public class DynamicProgrammingTest {

    /*
     * 数组 arrA、arrB 等长，从 arrA 取 amountA 个数，从 arrB 取 amountB 个数，
     * 其中 amountA + amountB <= arrA.length，并且不能取同一个位置的数
     * 如何取得的数的和最大
     *
     * fun(m,n) = max(fun(m-1,n)+a , fun(m,n-1)+b)
     *
     * fun(3,2)
     * = max(fun(2,2)+a,fun(3,1)+b)
     * = max(max(fun(1,2)+a,fun(2,1)+b)+a,max(fun(2,1)+a,fun(3,0)+b)+b)
     * = max(max(max(fun(0,2)+a,fun(1,1)+b)+a,max(fun(1,1)+a,fun(2,0)+b)+b)+a,max(max(fun(1,1)+a,fun(2,0)+b)+a,fun(3,0)+b)+b)
     * = max(max(max(fun(0,2)+a,max(fun(0,1)+a,fun(1,0)+b)+b)+a,max(max(fun(0,1)+a,fun(1,0)+b)+a,fun(2,0)+b)+b)+a,max(max(max(fun(0,1)+a,fun(1,0)+b)+a,fun(2,0)+b)+a,fun(3,0)+b)+b)
     *
     */


    @Test
    public void test2() {

        int[] arrA = {1, 2, 50, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        int[] arrB = {20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        int amountA = 10;
        int amountB = 10;

        // 封装与排序
        List<Node> nodeListA = new ArrayList<>();
        List<Node> nodeListB = new ArrayList<>();
        for (int i = 0; i < arrA.length; i++) {
            Node nodeA = new Node(i, arrA[i]);
            Node nodeB = new Node(i, arrB[i]);
            nodeListA.add(nodeA);
            nodeListB.add(nodeB);
        }
        nodeListA = nodeListA.stream().sorted(Comparator.comparing(Node::getValue).reversed()).collect(Collectors.toList());
        nodeListB = nodeListB.stream().sorted(Comparator.comparing(Node::getValue).reversed()).collect(Collectors.toList());

        int num = 1;
        Result result = null;
        System.out.println("开始");
        while (num > 0) {
            System.out.println(num);
            result = new Result();

            Map<CacheKey,Result> cacheMap = new HashMap<>();

            getMaxValueResult(nodeListA, nodeListB, amountA, amountB, result, cacheMap);
            num -= 1;
        }
        System.out.println("结束");

        System.out.println();
        System.out.println(result.getValue());
        System.out.println(result.getIndexSet());
        System.out.println(result.getIndexSetA());
        System.out.println(result.getIndexSetB());

    }

    /*
     0 1 a
     1 0 b

     0 2 c = 32
     1 1 d a+x b+y
     2 0 e = 29

     0 3 f
     1 2 g c+x d+y
     2 1 h d+x e+y
     3 0 i

     0 4 j
     1 3 k f+x g+y
     2 2 l g+x h+y
     3 1 m
     4 0 n

     0 5 o
     1 4 p
     2 3 q k+x l+y
     3 2 r
     4 1 s
     5 0 t
    * */
    public void getMaxValueResult(List<Node> nodeListA, List<Node> nodeListB, int amountA, int amountB, Result result,Map<CacheKey,Result> cacheMap) {

        int size = nodeListA.size();

        if (amountA + amountB > size) {
            throw new RuntimeException("参数错误");
        }

        // 增加缓存提升效率
        CacheKey cacheKey = new CacheKey(amountA,amountB);
        Result cacheResult = cacheMap.get(cacheKey);
        if (cacheMap.get(cacheKey) != null) {
            result.setIndexSet(new HashSet<>(cacheResult.getIndexSet()));
            result.setValue(cacheResult.getValue());
            result.setIndexSetA(cacheResult.getIndexSetA());
            result.setIndexSetB(cacheResult.getIndexSetB());
            return;
        }

        Set<Integer> indexSet = result.getIndexSet();
        if (amountA == 0) {
            int amount = amountB;
            for (int i = 0; i < size && amount > 0; i++) {
                amount--;
                Node node = nodeListB.get(i);
                indexSet.add(node.getIndex());
                result.getIndexSetB().add(node.getIndex());
                result.setValue(result.getValue() + node.getValue());
            }
            cacheMap.put(cacheKey,new Result(result));
            return;
        }
        if (amountB == 0) {
            int amount = amountA;
            for (int i = 0; i < size && amount > 0; i++) {
                amount--;
                Node node = nodeListA.get(i);
                indexSet.add(node.getIndex());
                result.getIndexSetA().add(node.getIndex());
                result.setValue(result.getValue() + node.getValue());
            }
            cacheMap.put(cacheKey,new Result(result));
            return;
        }

        Result resultA = new Result();
        Result resultB = new Result();
        getMaxValueResult(nodeListA, nodeListB, amountA - 1, amountB, resultA, cacheMap);
        getMaxValueResult(nodeListA, nodeListB, amountA, amountB - 1, resultB, cacheMap);

        Node nodeA = this.getMaxValueNode(nodeListA, resultA);
        Node nodeB = this.getMaxValueNode(nodeListB, resultB);
        if (nodeA.getValue() + resultA.getValue() >= nodeB.getValue() + resultB.getValue()) {
            getMaxValueResult(nodeListA, nodeListB, amountA - 1, amountB, result, cacheMap);
            result.getIndexSet().add(nodeA.getIndex());
            result.setValue(result.getValue() + nodeA.getValue());
            result.getIndexSetA().add(nodeA.getIndex());
        } else {
            getMaxValueResult(nodeListA, nodeListB, amountA, amountB - 1, result, cacheMap);
            result.getIndexSet().add(nodeB.getIndex());
            result.setValue(result.getValue() + nodeB.getValue());
            result.getIndexSetB().add(nodeB.getIndex());

        }
        cacheMap.put(cacheKey,new Result(result));
    }

    public Node getMaxValueNode(List<Node> nodeList, Result result) {

        Set<Integer> indexSet = result.getIndexSet();
        for (Node node : nodeList) {
            if (!indexSet.contains(node.getIndex())) {
                return node;
            }
        }
        throw new RuntimeException("参数错误");
    }

    static class CacheKey{
        private int amountA;
        private int amountB;

        public CacheKey(int amountA, int amountB) {
            this.amountA = amountA;
            this.amountB = amountB;
        }

        public int getAmountA() {
            return amountA;
        }

        public void setAmountA(int amountA) {
            this.amountA = amountA;
        }

        public int getAmountB() {
            return amountB;
        }

        public void setAmountB(int amountB) {
            this.amountB = amountB;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CacheKey cacheKey = (CacheKey) o;
            return amountA == cacheKey.amountA &&
                    amountB == cacheKey.amountB;
        }

        @Override
        public int hashCode() {
            return Objects.hash(amountA, amountB);
        }
    }

    /**
     * 封装数组数据
     */
    static class Node {
        private int index;
        private int value;

        public Node(int index, int value) {
            this.index = index;
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    /**
     * 封装结果
     */
    static class Result {

        Result() {
            this.indexSet = new HashSet<>();
            this.indexSetA = new HashSet<>();
            this.indexSetB = new HashSet<>();
            this.value = 0;
        }

        Result(Result result) {
            this.indexSet = new HashSet<>(result.getIndexSet());
            this.indexSetA = new HashSet<>(result.getIndexSetA());
            this.indexSetB = new HashSet<>(result.getIndexSetB());
            this.value = result.getValue();
        }

        private Set<Integer> indexSet;
        private int value;

        private Set<Integer> indexSetA;
        private Set<Integer> indexSetB;

        public Set<Integer> getIndexSet() {
            return indexSet;
        }

        public void setIndexSet(Set<Integer> indexSet) {
            this.indexSet = indexSet;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Set<Integer> getIndexSetA() {
            return indexSetA;
        }

        public void setIndexSetA(Set<Integer> indexSetA) {
            this.indexSetA = indexSetA;
        }

        public Set<Integer> getIndexSetB() {
            return indexSetB;
        }

        public void setIndexSetB(Set<Integer> indexSetB) {
            this.indexSetB = indexSetB;
        }
    }


    // ====================================================================
    /* 钢材 1-10米的价格，10米钢材怎和切割价值最大
     * max(m) = max(m-1)+x = max(max(m-1-1)+x1)+x2
     */
    int[] priceArr = {1, 5, 7, 9, 10, 17, 17, 20, 24, 25};

    @Test
    public void test1() {
        System.out.println(recursion(priceArr, priceArr.length));
        System.out.println(memorandum(priceArr));
        System.out.println(dynamicProgramming(priceArr));
    }

    /**
     * 递归
     */
    public int recursion(int[] priceArr, int length) {
        if (length <= 0) {
            return 0;
        }

        int value = 0;
        for (int x = 1; x <= length; x++) {
            value = Math.max(value, priceArr[x - 1] + recursion(priceArr, length - x));
        }
        return value;
    }

    /**
     * 备忘录版本
     */
    public int memorandum(int[] priceArr) {

        int[] priceCache = new int[priceArr.length + 1];
        Arrays.fill(priceCache, -1);
        return memorandumCue(priceArr, priceArr.length, priceCache);
    }

    /**
     * 备忘录版本，递归计算
     */
    public int memorandumCue(int[] priceArr, int length, int[] priceCache) {
        int value = 0;
        if (priceCache[length] > 0) {
            return priceCache[length];
        }
        if (length > 0) {
            for (int x = 1; x <= length; x++) {
                value = Math.max(value, priceArr[x - 1] + memorandumCue(priceArr, length - x, priceCache));
            }
        }
        priceCache[length] = value;
        return value;
    }

    /**
     * 自底向上动态规划
     */
    public int dynamicProgramming(int[] priceArr) {

        int length = priceArr.length;
        int[] priceCache = new int[length + 1];

        for (int x = 1; x <= length; x++) {
            int value = 0;
            for (int y = 1; y <= x; y++) {
                value = Math.max(value, priceArr[y - 1] + priceCache[x - y]);
            }
            priceCache[x] = value;
        }
        return priceCache[length];
    }


}
