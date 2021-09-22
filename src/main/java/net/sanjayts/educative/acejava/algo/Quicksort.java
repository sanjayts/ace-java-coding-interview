package net.sanjayts.educative.acejava.algo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Quicksort {

    public static void main(String[] args) {
        int[] a = new int[] {2, 10, 5, 8, 6, 15, 7};
        new Quicksort().qSort(a);
        log.info("Array after sorting with qSort: {}", a);
    }

    public void qSort(int[] arr) {
        qSort(arr, 0, arr.length - 1);
    }

    private void qSort(int[] arr, int start, int end) {
        if (start >= 0 && end >= 0 && start < end) {
            int pIdx = partition(arr, start, end);
            qSort(arr, start, pIdx - 1);
            qSort(arr, pIdx + 1, end);
        }
    }

    /**
     * Shuffle the elements for the given array range and return the pivot used for shuffling
     */
    private int partition(int[] arr, int start, int end) {
        int pVal = arr[end], tmp;
        int i = start - 1;
        for (int j = start; j <= end; ++j) {
            if (arr[j] <= pVal) {
                ++i;
                if (i != j) {
                    tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
        return i;
    }


}
