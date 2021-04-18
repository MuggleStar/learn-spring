package learn.base.algorithm;

import org.junit.Test;

import java.util.Arrays;

/**
 * 分治
 *
 * @author Madison.lu
 * @date 2020/8/18 16:31
 */
public class DivideConquerTest {


    int[] intArr = {98, 87, 74, 45, 56, 63, 32, 21, 12, 23, 36, 65, 54, 47, 78, 89};

    @Test
    public void test() {

        mergeSort(intArr, new int[intArr.length], 0, intArr.length - 1);
        for (int i : intArr) {
            System.out.print(i + ",");
        }
        Arrays.sort(intArr);

    }

    /**
     * 归并排序
     */
    public void mergeSort(int[] intArr, int[] tempArr, int low, int high) {

        if (low == high) return;
        int mid = (low + high) / 2;
        mergeSort(intArr, tempArr, low, mid);
        mergeSort(intArr, tempArr, mid + 1, high);

        int i = low, j = mid + 1;
        for (int k = low; k <= high; k++) {
            int ra, rb;
            if (i <= mid) {
                ra = intArr[i];
            } else {
                ra = Integer.MAX_VALUE;
            }
            if (j <= high) {
                rb = intArr[j];
            } else {
                rb = Integer.MAX_VALUE;
            }
            if (ra < rb) {
                tempArr[k] = ra;
                i++;
            } else {
                tempArr[k] = rb;
                j++;
            }
        }
        for (int k = low; k <= high; k++) {
            intArr[k] = tempArr[k];
        }
    }

}
