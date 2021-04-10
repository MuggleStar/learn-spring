package learn.leetcode.hard;

/**
 * hard-4
 * 寻找两个正序数组的中位数
 *
 * @author lujianrong
 * @date 2021/4/10
 */
public class Hard4 {


    public static void main(String[] args) {

        int[] nums1 = {1,3};
        int[] nums2 = {6,10};

        Hard4 hard4 = new Hard4();
        System.out.println(hard4.findMedianSortedArrays(nums1, nums2));
    }


    /**
     * 寻找两个正序数组的中位数
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        int length1 = nums1.length;
        int length2 = nums2.length;

        int totalLength = length1 + length2;
        int half1 = totalLength / 2 - 1;
        int half2;
        if (totalLength % 2 == 0) {
            half2 = half1 + 1;
        } else {
            half2 = half1 = half1 + 1;
        }

        int idx1 = 0;
        int idx2 = 0;

        int value1 = 0;
        int value2 = 0;
        for (int i = 0; i <= half2; i++) {
            int current;
            if (idx1 > length1 -1) {
                current = nums2[idx2];
                idx2 ++;
            } else if (idx2 > length2 -1) {
                current = nums1[idx1];
                idx1 ++;
            } else if (nums1[idx1] < nums2[idx2]) {
                current = nums1[idx1];
                idx1 ++;
            } else {
                current = nums2[idx2];
                idx2 ++;
            }

            if (i == half1) {
                value1 = current;
            }
            if (i == half2) {
                value2 = current;
            }
        }


        return (value1+value2)/2.0;
    }

}
