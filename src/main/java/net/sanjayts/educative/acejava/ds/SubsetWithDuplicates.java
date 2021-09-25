package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * https://leetcode.com/problems/subsets-ii/solution/
 *
 * https://www.educative.io/module/lesson/data-structures-in-java/YMRglkP1K50
 */
@Slf4j
public class SubsetWithDuplicates {

    public static void main(String[] args) {
        var nums = new int[] {1, 2, 3, 3};
        List<List<Integer>> subsets = new SubsetWithDuplicates().findSubsets(nums);
        log.info("Subsets for array {} are {}", nums, subsets);
    }

    public List<List<Integer>> findSubsets(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> subsets = new ArrayList<>();
        List<Integer> curSubset = new ArrayList<>();
        addSubsets(subsets, curSubset, nums, 0);
        return subsets;
    }

    private void addSubsets(List<List<Integer>> subsets, List<Integer> curSub, int[] nums, int idx) {
        subsets.add(curSub);
        for (int i = idx; i < nums.length; ++i) {
            if (i > idx && nums[i] == nums[i - 1]) {
                continue;
            } else {
                var newSubset = new ArrayList<>(curSub);
                newSubset.add(nums[i]);
                addSubsets(subsets, newSubset, nums, i + 1);
            }
        }
    }

}
