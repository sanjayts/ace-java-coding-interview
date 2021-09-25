package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Queue;

// https://www.educative.io/module/lesson/data-structures-in-java/mE3AxO4loxE
@Slf4j
public class MaximumSumSubarray {

    public static void main(String[] args) {
        int[] a = new int[] {1, 3, 5, 7, 10, 8, 6, 4};
        log.info("Array={}, maximum with DQ={}", a, new MaximumSumSubarray().findMaxSumSubArrayWithDq(2, a));
        log.info("Array={}, maximum with window={}", a, new MaximumSumSubarray().findMaxSumSubArray(2, a));
    }

    public int findMaxSumSubArrayWithDq(int k, int[] arr) {
        if (arr == null || arr.length < k) {
            return -1;
        }
        Queue<Integer> dq = new ArrayDeque<>();
        int localSum = 0;
        for (int i = 0; i < k; ++i) {
            dq.offer(arr[i]);
            localSum += arr[i];
        }
        int finalSum = localSum;
        for (int i = k; i < arr.length; ++i) {
            int head = dq.poll();
            localSum = localSum - head + arr[i];
            finalSum = Math.max(localSum, finalSum);
            dq.offer(arr[i]);
        }
        return finalSum;
    }

    public int findMaxSumSubArray(int k, int[] arr) {
        int max = 0, wSum = 0, wStart = 0;
        for (int i = 0; i < arr.length; ++i) {
            wSum += arr[i];
            if (i >= k - 1) {
                max = Math.max(max, wSum);
                wSum = wSum - arr[wStart];
                ++wStart;
            }
        }
        return max;
    }

}
