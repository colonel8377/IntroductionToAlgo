package MedianNumber;

import java.util.HashMap;
import java.util.Map;

/**
 * Java : find median number in two sorted array
 *
 * @author YE, Qiming
 * @date 2021/09/17
 */
public class MedianNumber {
    public static void main(String[] args) {
        Map<String, Test> m = new HashMap();
        m.put("a", new Test(1, 2));
        m.put("b", new Test(3, 4));
        Test t = m.get("a");
        m.remove("a");
        try {
            Thread.sleep(1000l);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(t.a);

    }
    //num1 and nums2 are all in increasing order
    public static double findMedianNumber(int[] nums1, int[] nums2, int n) {
        return (findKth(nums1, 0, nums2, 0, n) + findKth(nums1, 0, nums2, 0, n + 1)) / 2.0;
    }

    //i: nums1 initial position
    //j: nums2 initial position
    public static double findMedian(int[] num1, int[] num2, int k){
        int left = 0;
        int right = k;
        int median1 = 0;
        int median2 = 0;
        while (left <= right) {
            int i = (left + right) / 2;
            int j = (2 * k + 1) / 2 - i;
            int nums_med1 = (i == 0 ? Integer.MIN_VALUE : num1[i - 1]);
            int nums_i = (i == k ? Integer.MAX_VALUE : num1[i]);
            int nums_med2 = (j == 0 ? Integer.MIN_VALUE : num2[j - 1]);
            int nums_j = (j == k ? Integer.MAX_VALUE : num2[j]);

            if (nums_med1 <= nums_j) {
                median1 = Math.max(nums_med1, nums_med2);
                median2 = Math.min(nums_i, nums_j);
                left = i + 1;
            } else {
                right = i - 1;
            }
        }
        return (median1 + median2) / 2.0;
    }


    public static int findKth(int[] nums1, int i, int[] nums2, int j, int k){
        if( i >= nums1.length) return nums2[j + k - 1];//nums1 is null
        if( j >= nums2.length) return nums1[i + k - 1];//nums2 is null
        if(k == 1){
            //recursion boundary
            //RECURSION BOUNDARY OF THE ARR LENGTH THAT EQUAL TO 1.
            return Math.min(nums1[i], nums2[j]);
        }
        int midVal1 = (i + k / 2 - 1 < nums1.length) ? nums1[i + k / 2 - 1] : Integer.MAX_VALUE;
        int midVal2 = (j + k / 2 - 1 < nums2.length) ? nums2[j + k / 2 - 1] : Integer.MAX_VALUE;
        if(midVal1 < midVal2){
            return findKth(nums1, i + k / 2, nums2, j , k - k / 2);
        }else{
            return findKth(nums1, i, nums2, j + k / 2 , k - k / 2);
        }
    }
    public  static int search(int[] nums){
        return -1;
    }
}

class Test{
    int a;
    int b;
    Test(int a, int b) {
        this.a = a;
        this.b = b;
    }
}