package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

@Slf4j
public class Permutations {

    public static void main(String[] args) {
        var arr = new int[]{1, 2, 3};
        var out = new Permutations().permute(arr);
        log.info("Permutations of array {} are {}", arr, out);
    }

    // https://cheonhyangzhang.gitbooks.io/leetcode-solutions/content/46_permutations__medium.html
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        boolean[] visited = new boolean[nums.length];
        dfs(nums, res, new LinkedList<>(), visited, 0);
        return res;
    }

    private void dfs(int[] nums, List<List<Integer>> res, List<Integer> curr, boolean[] visited, int level) {
        log.info("Invoked DFS with cur={}, level={}; visited={}", curr, level, visited);
        if (curr.size() == nums.length) {
            res.add(new LinkedList<>(curr));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (visited[i]) {
                continue;
            }
            visited[i] = true;
            curr.add(nums[i]);
            dfs(nums, res, curr, visited, level + 1);
            Integer item = curr.get(curr.size() - 1);
            curr.remove(curr.size() - 1);
            visited[i] = false;
            log.info("Removed from curr, item={}, level={}, visited={}", item, level, visited);
        }
    }

}
