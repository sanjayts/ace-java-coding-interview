package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

/*
https://www.educative.io/module/lesson/data-structures-in-java/g7O2GrxYo9Y


 */
@Slf4j
public class MaxInBitonicArray {

    public static void main(String[] args) {
        var arr = new int[]{1, 3, 7, 14, 2};
        var max = new MaxInBitonicArray().findMax(arr);
        log.info("Max in bitonic array {} is {}", arr, max);
    }


    public int findMax(int[] arr) {
        int lo = 0, hi = arr.length - 1, mid, max = Integer.MIN_VALUE;
        while (lo <= hi) {
            mid = lo + (hi - lo) / 2;
            max = Math.max(max, arr[mid]);
            if (arr[lo] > arr[mid]) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return max;
    }


    /**
     * Naive solution wherein we loop over the entire array
     */
    public int findMaxNaive(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; ++i) {
            max = Math.max(max, arr[i]);
            if (i > 0 && arr[i] < arr[i - 1]) {
                break;
            }
        }
        return max;
    }

}
