package net.sanjayts.educative.acejava.ds;


import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/*
https://www.educative.io/module/lesson/data-structures-in-java/mEO2Xjk2Kyp

https://leetcode.com/problems/3sum-smaller/
 */
@Slf4j
public class TripletWithSmallerSum {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 1, 2, 3, 3, 3, 4};
        int target = 7;
        int count = new TripletWithSmallerSum().searchTriplets(arr, target);
        log.info("Count of triplets with sum less than target is {}", count);
    }

    /**
     * As with all 3sum problems, the trick here is to use a combination of sorting and two pointer search. The variation
     * in this case is to identify triplets without moving both lo and hi pointers at the same time. So for e.g. let's
     * consider the array [0, 1, 2, 3, 4, 5, 6] with target as 6.
     *
     * arr[i] = 0
     *  lo = 1, hi = 6 => sum(0+1+6) >= 6 so skip
     *  lo = 1, hi = 5 => sum(0+1+5) >= 6 so skip
     *  lo = 1, hi = 4 => sum(0+1+4) < 6 so we have hit our criteria. But note here that since (0,1,4) is a valid combo
     *                                   so we already know of other valid combinations starting with (0, 1, x). These
     *                                   being (0,1,2) and (0,1,3). THis is where the (hi - lo) comes in for the below
     *                                   solution. Once we are done with this combination (0,1) we move on to the next `lo`.
     *                                   count = 3
     *  lo = 2, hi = 4 => sum(0+2+4) >= 6 so skip and decrement hi
     *  lo = 2, hi = 3 => sum(0+2+3) < 6 so add this and count now becomes 4 which is the final answer.
     *
     */
    public int searchTriplets(int[] arr, int target) {
        int count = 0, n = arr.length;
        Arrays.sort(arr);

        for (int i = 0; i < n - 2; ++i) {
            int lo = i + 1, hi = n - 1;
            while (lo < hi) {
                int sum = arr[i] + arr[lo] + arr[hi];
                if (sum < target) {
                    count += (hi - lo);
                    ++lo;
                } else {
                    --hi;
                }
            }
        }
        return count;
    }

}
