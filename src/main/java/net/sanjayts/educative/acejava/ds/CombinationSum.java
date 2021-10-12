package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/*
https://leetcode.com/problems/combination-sum/
 */
@Slf4j
public class CombinationSum {

    public static void main(String[] args) {
        int[] arr = new int[] {2,3,5};
        int target = 8;
        List<List<Integer>> sums = new CombinationSum().combinationSum(arr, target);
        log.info("Combination sum for target {} and candidates {} is {}", target, arr, sums);
    }

    /**
     * The idea here is to start with an element/candidate and explore its combinations with all other candidates,
     * ensuring that we don't repeat the same combination (e.g. (2,3,3) (3,2,3), (3,3,2) )
     *
     * We ensure this by always moving the index forward when sampling the candidate array before diving into our
     * recursive call. This is indicated by passing the `idx` variable in our recursive procedure which is the index
     * of the candidate array from which our recursive procedure should start.
     *
     * As usual for all backtracking problems, we have to ensure we revert the modified state after our DFS calls. This
     * is actioned by doing a removeLast from our buffer once the recursive calls are done.
     *
     * Time complexity
     *      O(N^(h + 1)) where h = T/M (T = target sum and M = minimum candidate value)
     * Space complexity
     *      The number of recursive calls is the same as the height of the tree which is basically T/M so O(T/M) => O(h)
     *
     * To explain a bit more about how we arrived at the time complexity, let's start with the relation between target
     * and the minimum value. Since we are allowed to repeat the same candidate multiple times, the maximum height of
     * our recursive call chain would be the max target divided by the smallest value of our candidates. For e.g. if
     * our candidate list is {1,2,3} and our target = 3, we can expect our call stack to go to a depth of 3 (3/1).
     * The DFS calls would follow a N-ary tree structure visually wherein N is the number of candidates. So for e.g.
     * using the above example, we would start off with an empty array which will then branch off to 3 recursive calls,
     * one each for a given candidate. This would in turn fork off N more calls for each candidate and so on till we hit
     * our target (at height h).
     *
     * https://www.urch.com/forums/gre-computer-science/71680-q-what-maximum-number-nodes-k-ary-tree-height-h.html
     * Based on our mathematical formula, the total number of nodes in a N-ary tree of height `h` is:
     *      N^(h + 1) - 1 / (N - 1). If we drop off insignificant terms it simply becomes
     *      N^(h+1) / N
     *      N^h * N / N
     *      N^h
     *      O(N^h) or O(N^(T/M))
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        final List<List<Integer>> result = new ArrayList<>();
        if (candidates == null || candidates.length < 1) {
            return result;
        }
        Arrays.sort(candidates);
        recur(new LinkedList<>(), result, candidates, target, 0, 0);
        return result;
    }

    private void recur(LinkedList<Integer> buf, List<List<Integer>> result, int[] candidates, int target, int sum, int idx) {
        for (int i = idx; i < candidates.length; ++i) {
            if (sum + candidates[i] == target) {
                // match found, add to result and break
                buf.add(candidates[i]);
                result.add(new ArrayList<>(buf));
                buf.removeLast();
                break;
            } else if (sum + candidates[i] < target) {
                buf.add(candidates[i]);
                recur(buf, result, candidates, target, sum + candidates[i], i);
                buf.remove(buf.size() - 1);
            } else {
                break;
            }
        }
    }


}
