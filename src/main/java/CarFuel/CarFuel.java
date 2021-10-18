package CarFuel;

import java.util.Arrays;
import java.util.PriorityQueue;

public class CarFuel {
    public static void main(String[] args) {
        // Add boundary condition.
        // Set p[-1] = 0 since there is no dp to add our mug at city A.
        // Set c[0] = 0 and c[n + 1] = dist_ab(A, B) according to problem.
        int res = findMinPrice2(new int[]{3, 0, 0, 0, 0, 0, 1, 2, 4, 2, 2, 12, 0, 2, 0}, new int[]{1, 3, 5, 7, 9, 11, 13, 15, 17, 14, 16, 17, 18, 20, 21}, 1, 22);
        System.out.println("" + res);
    }

    public static int findMinPrice1(int[] p, int[] c, int d) {
        int[] dp = new int[p.length + 1];
        dp[0] = 0;
        int k = 0;
        int price;
        PriorityQueue pq = new PriorityQueue<Integer>();
        for (int i = 0; i < p.length + 1; i++) {
            while (c[i] - c[k] > d && i > k) {

                pq.remove(dp[k]);
                k++;
            }
            if (k >= i && k != 0) {
                return -1;
            }
            price = i < p.length ? p[i] : 0;
            if (pq.size() > 0) {
                dp[i] = (Integer) pq.peek() + price;
            } else {
                dp[i] = price;
            }
            pq.add(dp[i]);
        }
        return dp[p.length];
    }

    public static int findMinPrice2(int[] p, int[] c, int d, int dist_ab) {
        int[] dp = new int[p.length];
        Arrays.fill(dp, Integer.MIN_VALUE);
        dp[0] = 0;
        int pointer = 0;
        int count = 0;

        for (int i = 1; i < p.length; i++) {
            if (c[i - 1] + d < c[i]) {
                System.out.println("==============" + count++ + "==============");
                return -1;
            } else if (d >= c[i]) {
                dp[i] = 0;
                System.out.println("==============" + count++ + "==============");
            } else {
                for (int j = pointer; j < i; j++) {
                    if (c[j] + d >= c[i]) {
                        System.out.println("==============" + count++ + "==============");
                        dp[i] = dp[j] + p[j];
                        pointer = j;
                        break;
                    } else {
                        System.out.println("==============" + count++ + "==============");
                        pointer += 1;
                    }
                }
            }
            if (dp[i] + p[i] < dp[i - 1] + p[i - 1]) {
                System.out.println("==============" + count++ + "==============");
                pointer = i;
            }
        }
        if (c[c.length - 1] + d < dist_ab) {
            return -1;
        }
        for (int j = pointer; j < p.length; j++) {
            if(c[j] + d >= dist_ab){
                return dp[j] + p[j];
            }
        }
        return -1;
    }
}
