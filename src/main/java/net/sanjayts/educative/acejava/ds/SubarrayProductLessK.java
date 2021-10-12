package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
https://leetcode.com/problems/subarray-product-less-than-k/

https://www.educative.io/module/lesson/data-structures-in-java/R1XgYW2PZzK
 */
@Slf4j
public class SubarrayProductLessK {

    public static void main(String[] args) {
        int[] arr = new int[] {1, 2, 3, 4};
        int target = 7;
        List<List<Integer>> subArrays = new SubarrayProductLessK().findSubarrays(arr, target);
        int count = new SubarrayProductLessK().numSubarrayProductLessThanK(arr, target);
        log.info("Total {} subarrays, they are {}", count, subArrays);
    }

    /**
     * The idea here is to use a sliding window to maintain the window of elements whose product is lower than target.
     * As soon as we go over the threshold, we shrink the window. We keep growing the window as long as the constraint
     * holds true.
     *
     * The tricky part here is identifying all the sub arrays. We start adding the elements from the right to ensure
     * we never add the same element multiple times. We use a linked list to ensure we have the flexibilty to add
     * elements to the 0th index to maintain the order of elements.
     */
    public List<List<Integer>> findSubarrays(int[] arr, int target) {
        List<List<Integer>> arrays = new ArrayList<>();
        int left = 0, cp = 1;
        for (int right = 0; right < arr.length; ++right) {
            cp *= arr[right];
            while (cp >= target) cp /= arr[left++];
            List<Integer> nums = new LinkedList<>();
            for (int i = right; i >= left; --i) {
                nums.add(0, arr[i]);
                arrays.add(new ArrayList<>(nums));
            }
        }
        return arrays;
    }

    /**
     * This leetcode solution is a bit simpler than the educative one since the problem statement here doesn't require
     * us to provide the list of all sub arrays but just count them.
     */
    public int numSubarrayProductLessThanK(int[] nums, int target) {
        if (target <= 1) {
            return 0;
        }
        int left = 0, product = 1, sum = 0;
        for (int right = 0; right < nums.length; ++right) {
            product *= nums[right];
            while (product >= target && left <= right) {
                product /= nums[left++];
            }
            sum += (right - left + 1);
        }
        return sum;
    }

}

/*
[1, 2, 3, 4]
target = 7

[1], [2], [3], [4], [1, 2], [1, 2, 3], [2, 3]


 */
