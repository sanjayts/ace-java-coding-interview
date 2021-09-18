package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

/**
 * Search for the high/low index for a given 'key' in the sorted array.
 * <p>
 * https://www.educative.io/module/lesson/data-structures-in-java/q2LzZqV9603
 */
@Slf4j
public class HiLoInSortedArray {

    public static void main(String[] args) {
        List<Integer> array = Arrays.asList(1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 6, 6, 6, 6, 6, 6);
        int key = 5;
        int low = findLowIndex(array, key);
        int high = findHighIndex(array, key);
        log.info("Low Index of " + key + ": " + low);
        log.info("High Index of " + key + ": " + high);

        key = -2;
        low = findLowIndex(array, key);
        high = findHighIndex(array, key);
        log.info("Low Index of " + key + ": " + low);
        log.info("High Index of " + key + ": " + high);
    }

    /*
    The idea here is to keep seeking the mid till we break out of our pre-requisite for the loop (start <= end). Once
    we are out, [end for findHi] / [start for findLo] would either be:
    1. out of bounds
    2. a valid index without a matching key
    3. a valid index with a matching key
     */

    static int findHighIndex(List<Integer> arr, int key) {
        int start = 0, end = arr.size() - 1, mid;
        while (start <= end) {
            mid = start + (end - start) / 2;
            if (arr.get(mid) <= key) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        if (end >= 0 && end < arr.size() && arr.get(end) == key) {
            return end;
        } else {
            return -1;
        }
    }

    static int findLowIndex(List<Integer> arr, int key) {
        int start = 0, end = arr.size() - 1, mid;
        while (start <= end) {
            mid = start + (end - start) / 2;
            if (arr.get(mid) >= key) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        if (start >= 0 && start < arr.size() && arr.get(start) == key) {
            return start;
        } else {
            return -1;
        }
    }

}
