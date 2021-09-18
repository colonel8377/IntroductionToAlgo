package MedianNumber;

/**
 * Java : find median number in two sorted array
 *
 * @author YE, Qiming
 * @date 2021/09/17
 */
public class MedianNumber {
    public static void main(String[] args) {
        double res = findMedianNumber(new int[]{1, 2, 3, 5, 15}, new int[]{5, 6, 7, 8, 15}, 4);
        System.out.println(res);
    }
    //num1 and nums2 are all in increasing order
    public static double findMedianNumber(int[] nums1, int[] nums2, int n) {
        return (findKth(nums1, 0, nums2, 0, n) + findKth(nums1, 0, nums2, 0, n + 1)) / 2.0;
    }

    //i: nums1 initial position
    //j: nums2 initial position
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