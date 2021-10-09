package CarFuel;
import java.util.PriorityQueue;
public class CarFuel {
    public static void main(String[] args) {
        // Add boundary condition.
        // Set prices[-1] = 0 since there is no cost to add our mug at city A.
        // Set distance[0] = 0 and distance[n + 1] = dist(A, B) according to problem.
        int res = findMinPrice(new int[]{0, 2, 3, 0, 1, 0, 2, 1, 2}, new int[]{0, 2, 4, 5, 6, 7, 9, 11, 14, 15}, 2);
        System.out.println("" + res);
    }
    public  static int findMinPrice(int[] prices, int[] distance, int d){
        int[] cost = new int[prices.length + 1];
        cost[0] = 0;
        int k = 0;
        int price;
        PriorityQueue pq = new PriorityQueue<Integer>();
        for(int i = 0; i < prices.length + 1 ; i ++){
            while(distance[i] - distance[k] > d && i > k) {
                pq.remove(cost[k]);
                k ++;
            }
            if(k >= i && k != 0){
                return -1;
            }
            price = i < prices.length ? prices[i] : 0;
            if(pq.size() > 0){
                cost[i] = (Integer)pq.peek() + price;
            }
            else{
                cost[i] = price;
            }
            pq.add(cost[i]);
        }
        return cost[prices.length];
    }
}
