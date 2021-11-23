package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/*
https://leetcode.com/problems/subarray-sum-equals-k/
 */
@Slf4j
public class SubarraySumEqualsK {

    public static void main(String[] args) {
        int[] nums = new int[] {1, 1, 1, 2, 3};
        int k = 3;
        SubarraySumEqualsK runner = new SubarraySumEqualsK();
        int c1 = runner.subarraySumBrute(nums, k);
        log.info("For array {} and k={} the count of subarrays adding up to k is {}", nums, k, c1);
        int c2 = runner.subarraySum(nums, k);
        log.info("For array {} and k={} the count of subarrays adding up to k is {}", nums, k, c2);
    }

    /**
     * This is a brute force solution with O(N^2) time complexity and O(1) space complexity. The idea here is to iterate
     * n times over the array, starting at the index `i` which goes from 0 to N-1.
     */
    public int subarraySumBrute(int[] nums, int k) {
        int cnt = 0;
        for (int i = 0; i < nums.length; ++i) {
            int sum = 0;
            for (int j = i; j < nums.length; ++j) {
                sum += nums[j];
                if (sum == k) ++cnt;
            }
        }
        return cnt;
    }

    /**
     * This is an optimized version of the above algorithm which trades in space for improving time complexity. The idea
     * here is that whenever we encounter a sum which has a diff of `k` against an existing sum, we know we have found
     * a match. As an example, let's consider:
     *  nums=[1, 1, 1, 1, 1, 1] and k=2
     *  cumulative=[0, 1, 2, 3, 4, 5, 6]
     *  As we can see from the above cumulative list, we see a diff of `k` 5 times (0-2, 1-3, 2-4, 3-5, 4-6).
     *
     * When we encounter the first element, we check if this particular sum exists already. If it does, increment it and
     * if not we put a count of 1 against it. We then check whether `sum - k` exists in the cumulative map. If it does,
     * we have found our matching subarray and need to increment the count with the count against the given sum.
     *
     * A good explanation can be found at -- https://leetcode.com/problems/subarray-sum-equals-k/discuss/341399/Python-clear-explanation-with-code-and-example
     */
    public int subarraySum(int[] nums, int k) {
        int sum = 0, cnt = 0;
        HashMap<Integer, Integer> sumToCnt = new HashMap<>();
        sumToCnt.put(0, 1); // special case to ensure we handle single element occurrences of `k`
        for (int i = 0; i < nums.length; ++i) {
            sum += nums[i];
            cnt += sumToCnt.getOrDefault(sum - k, 0);
            sumToCnt.put(sum, sumToCnt.getOrDefault(sum, 0) + 1);
        }
        return cnt;
    }

}
