package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

/*
https://leetcode.com/problems/search-in-rotated-sorted-array/
 */
@Slf4j
public class SearchRotatedArray {

    public static void main(String[] args) {
        var arr = new int[] {6, 7, 8, 1, 2, 3, 4, 5};
        var target = 7;
        var out = new SearchRotatedArray().search(arr, target);
        log.info("Target {} found in rotated array {} at zero-indexed position {}", target, arr, out);
    }

    /**
     * The idea here is to use a modified binary search. The crux of the solution is to understand that at any given point
     * in time after having calculated the mid, the array would be split in two parts -- one of which will be sorted.
     * It is this property which we will utilize to understand where to move our lo/hi pointers.
     */
    public int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) return mid;

            // If the left part of the array is sorted; ensure we are doing <= instead of < to account for a corner case
            // of a two element array like [3, 1] when the target is 1.
            if (nums[lo] <= nums[mid]) {
                // If the target lies in the left sorted part, then all we need to do is move to the left
                if (target >= nums[lo] && target < nums[mid]) {
                    hi = mid - 1;
                } else {
                    // in case, the target doesn't lie in the sorted part, we simply search in the other part
                    lo = mid + 1;
                }
            } else {
                // Else if the right part of the array is sorted
                if (target > nums[mid] && target <= nums[hi]) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
        }
        return -1;
    }

}
