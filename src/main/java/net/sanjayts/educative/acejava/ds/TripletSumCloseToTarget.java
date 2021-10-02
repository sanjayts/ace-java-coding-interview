package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/*
https://www.educative.io/module/lesson/data-structures-in-java/qZnZ6l3zVv7
https://leetcode.com/problems/3sum-closest/
 */
@Slf4j
public class TripletSumCloseToTarget {

    public static void main(String[] args) {
        int[] arr = {-3,-2,-5,3,-4};
        int target = -1;
        int out = new TripletSumCloseToTarget().searchTriplet(arr, target);
        log.info("Closest sum is {} for the array {} and target {}", out, arr, target);
    }

    /**
     * The idea here is to follow the same process as we have done for other 3sum exercises. We start with an outer
     * loop which iterates over the elements which have already been sorted. We then try to locate two elements and
     * sum them up with the arr[i]. The only difference here being that we are not working towards a target by working
     * towards finding something which is *closest* to the target.
     *
     * We do this again by using our two pointer technique with the tweak being that we compute the sum of all elements
     * at every step and compare it against the closeness to our target. If the new sum if closer to our target then
     * our old sum, we update the final sum to the new sum.
     *
     * Once we are done with both the outer and the inner loops, we simply return back the sum which should be the sum
     * which is closest to our target.
     *
     * Time complexity
     *      sort => O(NlogN)
     *      main loop => N (N - 1) / 2 => O(N^2)
     *
     * Space complexity
     *      sort => varies from constant space to O(logN)
     *      main loop => O(1), non-variable storage used
     */
    public int searchTriplet(int[] arr, int targetSum) {
        if (arr == null) {
            return -1;
        }

        int n = arr.length, sum = Integer.MAX_VALUE;
        Arrays.sort(arr);

        for (int i = 0; i < n; ++i) {
            for (int s = i + 1, e = n - 1; s < e;) {
                int localSum = arr[i] + arr[s] + arr[e];
                int localDiff = targetSum - localSum;
                if (localDiff < 0) {
                    --e;
                } else if(localDiff > 0) {
                    ++s;
                } else {
                    return localSum;
                }

                if (sum == Integer.MAX_VALUE) {
                    sum = localSum;
                }
                if (Math.abs(targetSum - localSum) < Math.abs(targetSum - sum)) {
                    sum = localSum;
                }
            }
        }
        return sum;
    }

}