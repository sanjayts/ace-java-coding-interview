package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

/*
https://www.educative.io/module/lesson/data-structures-in-java/JY07DBl9Nqg

https://leetcode.com/problems/minimum-size-subarray-sum/solution/
 */
@Slf4j
public class SmallestSubarrayWithSum {

    public static void main(String[] args) {
        int[] arr = new int[] {2, 1, 5, 2, 3, 2};
        int s = 7;
        int cnt = new SmallestSubarrayWithSum().findMinSubArray(s, arr);
        log.info("Smallest subarray with sum {} count is {} for array {}", s, cnt, arr);
    }

    /**
     * The idea here is to go over the array one item at a time and keep track of our goal `S`. When we exceed S,
     * we need to compare the diff between the (end - start) of our window to the current minimum subarray size
     * which is initialized to Integer.MAX_VALUE. If it's lower, then we update it and start shrinking the window.
     *
     * Keep shrinking the window till we have the sum >= the reference value (S)
     */
    public int findMinSubArray(int S, int[] arr) {
        int sum = 0, finalCnt = Integer.MAX_VALUE;
        for (int i = 0, j = 0; i < arr.length; ++i) {
            sum += arr[i];
            while (sum >= S) {
                finalCnt = Math.min(finalCnt, i - j+ 1);
                sum -= arr[j];
                ++j;
            }

        }
        return finalCnt == Integer.MAX_VALUE ? 0 : finalCnt;
    }

}
