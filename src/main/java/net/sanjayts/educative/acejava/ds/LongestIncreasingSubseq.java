package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
https://leetcode.com/problems/longest-increasing-subsequence
 */
@Slf4j
public class LongestIncreasingSubseq {

    public static void main(String[] args) {
        int[] nums = new int[] {10,9,2,5,3,7,101,18};
        LongestIncreasingSubseq prog = new LongestIncreasingSubseq();
        int c1 = prog.greedySol(nums);
        int c2 = prog.dpSol(nums);
        log.info("Longest subsequence count using Greedy solution is {} for {}", c1, nums);
        log.info("Longest subsequence count using DP solution is {} for {}", c2, nums);
    }

    /**
     * The idea behind a greedy solution is to take note of any number which helps us increase the subsequence count.
     * In case multiple solutions are possible, we should maintain multiple lists and then return the list which has
     * the biggest size.
     *
     * This approach is not very good in terms of runtime since we are now talking about maintaining possibly N lists.
     * This is explained in much more detail at the below link:
     *
     * https://leetcode.com/problems/longest-increasing-subsequence/discuss/1326308/C%2B%2BPython-DP-Binary-Search-BIT-Solutions-Picture-explain-O(NlogN)
     */
    public int greedySol(int[] nums) {
        ArrayList<Integer> seq = new ArrayList<>();
        for (int n : nums) {
            if (seq.isEmpty() || seq.get(seq.size() -1) < n) {
                seq.add(n);
            } else {
                int idx = search(n, seq);
                seq.set(idx, n);
            }
        }
        return seq.size();
    }

    private int search(int n, List<Integer> nums) {
        int lo = 0, hi = nums.size() - 1, mid;
        while (lo <= hi) {
            mid = lo + (hi - lo) / 2;
            if (nums.get(mid) == n) {
                return mid;
            } else if (nums.get(mid) < n) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return lo;
    }


    /**
     * The idea here is to incrementally solve the by defining the problem correctly and setting up a recurrence
     * relation in a way that it makes sense. Our very first reaction is to set up something wherein each dp cell
     * denotes the length of a subsequence till that point. But this framing is invalid since it doesn't cover cases
     * like [10, 30, 50, 1, 3, 5, 40, 60]
     *
     * More details on the solution can be found at the link below
     *
     * https://classroom.udacity.com/courses/ud401/lessons/9752571100/concepts/98009391080923
     */
    public int dpSol(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        for (int i = 1; i < nums.length; ++i) {
            for (int j = 0; j < i; ++j) {
                if (nums[j] < nums[i] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                }
            }
        }
        return Arrays.stream(dp).max().orElse(-1);
    }

}
