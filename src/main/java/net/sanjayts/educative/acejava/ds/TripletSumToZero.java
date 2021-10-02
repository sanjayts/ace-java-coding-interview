package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/*
https://www.educative.io/module/lesson/data-structures-in-java/39150A84KQA

https://leetcode.com/problems/3sum/solution/
 */
@Slf4j
public class TripletSumToZero {

    public static void main(String[] args) {
        int[] arr = new int[]{-3, 0, 1, 2, -1, 1, -2};
        var triplets = new TripletSumToZero().searchTripletsNoSort(arr);
        log.info("Triplets for array {} are {}", arr, triplets);
    }

    /**
     * This solution is different from the hashset based and two pointer based approaches below because it assumes
     * the input can't be sorted. In this case, we can't help but rely on another hashset to keep track of duplicate
     * triplets. The overall strategy is the same -- we iterate over the array and pick the index of the outermost
     * iteration as our pivot. The difference here is that we employ a hashset to keep track of outer loop items
     * which we have already encountered. We then run an inner loop starting with i + 1 where we try to locate the
     * other two components of the triplet (the first being arr[i]).
     *
     * The way we keep track of duplicates in this case is to maintain a Hashset of our triplets. Sorting is requried
     * before pushing the triplets in the set to ensure we are able to distinguish duplicates.
     */
    public List<List<Integer>> searchTripletsNoSort(int[] arr) {
        int n = arr.length;
        Set<List<Integer>> triplets = new HashSet<>();
        Set<Integer> seenNums = new HashSet<>();

        for (int i = 0; i < n; ++i) {
            // We lose the opportunity to optimize here since our array is unsorted and we can't assume that everything
            // that follows this number will be a positive number, hence no arr[i] > 0 condition in this case.
            if (!seenNums.add(arr[i])) {
                continue;
            }
            int target = -arr[i];
            Set<Integer> nums = new HashSet<>();
            for (int j = i + 1; j < n; ++j) {
                int missing = target - arr[j];
                if (nums.contains(missing)) {
                    List<Integer> list = Arrays.asList(arr[i], arr[j], missing);
                    Collections.sort(list); // We sort to ensure our set is able to distinguish duplicate triplets
                    triplets.add(list);
                }
                nums.add(arr[j]);
            }
        }
        return new ArrayList<>(triplets);
    }



    /**
     * The below solution follows a similar approach to the two-pointer solution with the difference being we utilize
     * a set to capture number pairs which add up to the target (arr[i])
     *
     * Time complexity
     *      sort => O(N * logN)
     *      main loop => 1, 2, ..., N-1 => N * (N - 1) / 2 => O(N^2)
     *      total => O(N^2)
     * Space complexity
     *      sort => O(logN)
     *      main loop => O(N) for maintaining the set of visited items per iteration of `i`
     *      total => O(N)
     */
    public List<List<Integer>> searchTripletsUsingMap(int[] arr) {
        int n = arr.length;
        List<List<Integer>> triplets = new ArrayList<>();
        Arrays.sort(arr);

        for (int i = 0; i < n && arr[i] <= 0; ++i) {
            if (i > 0 && arr[i - 1] == arr[i]) {
                continue;
            }
            Set<Integer> numToIdx = new HashSet<>();
            int target = -arr[i];
            for (int j = i + 1; j < n; ++j) {
                int missing = target - arr[j];
                if (numToIdx.contains(missing)) {
                    triplets.add(Arrays.asList(arr[i], arr[j], missing));
                    while (j + 1 < n && arr[j + 1] == arr[j]) {
                        ++j;
                    }
                } else {
                    numToIdx.add(arr[j]);
                }
            }
        }
        return triplets;
    }

    /**
     * At a very high level, this solution uses the two pointer solution with a twist to avoid duplicates since we will
     * have multiple matches and we ony need unique triplets. We sort the array to ensure that it's easy for us to skip
     * duplicates and at the same time apply a two-pointer technique as opposed to using sets to check for uniqueness.
     *
     * Time complexity
     *      sort => O(N * logN)
     *      main loop => 1, 2, ..., N-1 => N * (N - 1) / 2 => O(N^2)
     * Space complexity
     *      sort => auxiliary space used => O(logN)
     */
    public List<List<Integer>> searchTripletsTwoPointer(int[] arr) {
        if (arr == null) {
            return new ArrayList<>();
        }

        Arrays.sort(arr);

        int n = arr.length;
        List<List<Integer>> triplets = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            if (arr[i] > 0 || (i > 0 && arr[i - 1] == arr[i])) {
                continue;
            }
            int target = -arr[i];
            for (int s = i + 1, e = n - 1; s < e; ) {
                int sum = arr[s] + arr[e];
                if (sum > target) {
                    --e;
                } else if (sum < target) {
                    ++s;
                } else {
                    triplets.add(Arrays.asList(arr[i], arr[s++], arr[e]));
                    while (s < e && arr[s] == arr[s - 1]) {
                        ++s;
                    }
                }
            }
        }
        return triplets;
    }


    public List<List<Integer>> searchTripletsWithBinarySearch(int[] nums) {
        int n = nums.length;
        if (n < 3) {
            return new ArrayList<>();
        }
        Arrays.sort(nums);
        List<List<Integer>> triplets = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            if (i > 0 && nums[i - 1] == nums[i]) {
                continue;
            }
            int target = -1 * nums[i];
            for (int j = i + 1; j < n;) {
                int v = nums[j];
                int idx = Arrays.binarySearch(nums, j + 1, n, target - v);
                if (idx >= 0) {
                    triplets.add(Arrays.asList(nums[i], nums[j++], nums[idx--]));
                    while (j < n && nums[j] == nums[j - 1]) {
                        ++j;
                    }
                } else {
                    ++j;
                }
            }
        }
        return triplets;
    }


}