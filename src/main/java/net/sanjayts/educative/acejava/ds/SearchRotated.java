package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * Search for a given number in a sorted array that has been rotated by some arbitrary number.
 *
 * https://www.educative.io/module/lesson/data-structures-in-java/g7PnVJAqR6D
 */
@Slf4j
public class SearchRotated {

    public static void main(String... args) {
        int[] arr = new int[] {4, 5, 6, 1, 2, 3};
        int ans = binarySearchRotated(arr, 1);
        log.info("Answer is " + ans);
    }

    static int binarySearchRotated(int[] arr,int key) {
        int start = 0;
        int end = arr.length - 1;
        int mid;

        while (start <= end) {
            log.info("Start={}, end={}", start, end);
            mid = start + (end - start) / 2;
            log.info("Mid is " + mid + " for arr: " + Arrays.toString(arr));

            if (arr[mid] == key) {
                return mid;
            } else if (arr[mid] < arr[end]) {
                log.info("arr[mid] < arr[end]");
                // The second half is sorted
                if (key >= arr[mid] && key <= arr[end]) {
                    log.info("start = mid + 1");
                    start = mid + 1;
                } else {
                    log.info("end = mid - 1");
                    end = mid - 1;
                }
            } else {
                log.info("arr[mid] > arr[end]");
                // The first half is sorted
                if (key <= arr[mid] && key >= arr[start]) {
                    log.info("end = mid - 1");
                    end = mid - 1;
                } else {
                    log.info("start = mid + 1");
                    start = mid + 1;
                }
            }
        }
        return -1;
    }

}
