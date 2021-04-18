package learn.base.algorithm;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 回溯
 *
 * @author Madison.lu
 * @date 2020/8/19 16:34
 */
public class BacktrackingTest {


    /*
     * 数组 arrA、arrB 等长，从 arrA 取 amountA 个数，从 arrB 取 amountB 个数，
     * 其中 amountA + amountB <= arrA.length，并且不能取同一个位置的数
     * 如何取得的数的和最大
     */
    @Test
    public void test1() {

        int[] arrA = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        int[] arrB = {20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        int amountA = 10;
        int amountB = 10;
        int num = 10;
        while (num > 0) {
            System.out.println(num);
            normal(arrA, arrB, amountA, amountB);
            num -= 1;
        }
    }

    /**
     * 取 amountA、amountB 中较小的数，对该数组所有取值情况进行求值并排序
     * 遍历从另一个数据取不脚标不冲突的最大组合求值
     */
    public void normal(int[] arrA, int[] arrB, int amountA, int amountB) {

        if (arrA.length != arrB.length || amountA + amountB > arrA.length) {
            System.out.println("非法参数");
            return;
        }

        // 取小的数组进行排列组合
        int smallAmount;
        int[] smallAmountArr;
        int bigAmount;
        int[] bigAmountArr;

        if (amountA < amountB) {
            smallAmount = amountA;
            bigAmount = amountB;
            smallAmountArr = arrA;
            bigAmountArr = arrB;
        } else {
            smallAmount = amountB;
            bigAmount = amountA;
            smallAmountArr = arrB;
            bigAmountArr = arrA;
        }

        // 排列组合并排序
        List<NodeCompose> nodeComposeList = new ArrayList<>();
        permutations(smallAmountArr, smallAmount, nodeComposeList);
        nodeComposeList = nodeComposeList
                .stream()
                .sorted(Comparator.comparing(NodeCompose::getTotal).reversed())
                .collect(Collectors.toList());

        // 封装数据并排序
        List<Node> nodeList = new ArrayList<>();
        for (int i = 0; i < bigAmountArr.length; i++) {
            Node node = new Node();
            node.setIndex(i);
            node.setValue(bigAmountArr[i]);
            nodeList.add(node);
        }
        nodeList = nodeList
                .stream()
                .sorted(Comparator.comparing(Node::getValue).reversed())
                .collect(Collectors.toList());

        // 获取最大值
        NodeCompose max = null;
        outer:
        for (NodeCompose nodeCompose : nodeComposeList) {
            Set<Integer> indexSet = nodeCompose.getIndexSet();
            int left = bigAmount;
            boolean isLast = true;
            for (Node node : nodeList) {
                if (left <= 0) {
                    break;
                }
                if (!indexSet.contains(node.getIndex())) {
                    left -= 1;
                    nodeCompose.setAllTotal(nodeCompose.getAllTotal() + node.getValue());
                    if (max == null || max.getAllTotal() < nodeCompose.getAllTotal()) {
                        max = nodeCompose;
                    }
                } else {
                    isLast = false;
                }
                if (isLast & left <= 0) {
                    break outer;
                }
            }
        }
        System.out.println(max.getAllTotal());
    }

    /**
     * 排列组合
     */
    public void permutations(int[] arr, int level, List<NodeCompose> nodeComposeList) {

        if (level <= 0) {
            return;
        }
        int length = arr.length;

        List<NodeCompose> newList = new ArrayList<>();

        if (nodeComposeList.isEmpty()) {
            for (int i = 0; i < length; i++) {
                NodeCompose nodeCompose = new NodeCompose();
                Set<Integer> indexSet = new HashSet<>();
                indexSet.add(i);
                nodeCompose.setIndexSet(indexSet);
                nodeCompose.setTotal(arr[i]);
                nodeCompose.setAllTotal(arr[i]);
                nodeCompose.setLastIndex(i);
                newList.add(nodeCompose);
            }
        } else {
            for (NodeCompose currentCompose : nodeComposeList) {
                for (int i = currentCompose.getLastIndex(); i < length; i++) {
                    Set<Integer> currentIndexSet = currentCompose.getIndexSet();
                    if (!currentIndexSet.contains(i)) {
                        NodeCompose nodeCompose = new NodeCompose();
                        Set<Integer> indexSet = new HashSet<>(currentIndexSet);
                        indexSet.add(i);
                        nodeCompose.setIndexSet(indexSet);
                        nodeCompose.setTotal(currentCompose.getTotal() + arr[i]);
                        nodeCompose.setAllTotal(currentCompose.getTotal() + arr[i]);
                        nodeCompose.setLastIndex(i);
                        newList.add(nodeCompose);
                    }
                }

            }
        }
        nodeComposeList.clear();
        nodeComposeList.addAll(newList);
        permutations(arr, level - 1, nodeComposeList);
    }

    /**
     * 数组组合
     */
    class NodeCompose {
        Set<Integer> indexSet;
        int lastIndex;
        int total;
        int allTotal;

        public Set<Integer> getIndexSet() {
            return indexSet;
        }

        public void setIndexSet(Set<Integer> indexSet) {
            this.indexSet = indexSet;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getAllTotal() {
            return allTotal;
        }

        public void setAllTotal(int allTotal) {
            this.allTotal = allTotal;
        }

        public int getLastIndex() {
            return lastIndex;
        }

        public void setLastIndex(int lastIndex) {
            this.lastIndex = lastIndex;
        }
    }

    /**
     * 数组节点
     */
    class Node {

        int index;
        int value;

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

}
