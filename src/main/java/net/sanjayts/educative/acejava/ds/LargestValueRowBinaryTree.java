package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;
import net.sanjayts.educative.acejava.util.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LargestValueRowBinaryTree {

    public static void main(String[] args) {
        TreeNode tree = TreeNode.sampleTree();

        List<Integer> dfsOut = new DFS().largestValues(tree);
        log.info("DFS output for largest values is {}", dfsOut);

        List<Integer> bfsOut = new BFS().largestValues(tree);
        log.info("BFS output for largest values is {}", bfsOut);
    }

}

class BFS {

    /**
     * The intuition here is that we can get the node count for a given tree when iterating over it. For e.g. when processing
     * the root node, the queue size would be 1 (the root itself). When processing its children, the size would be 2 (L+R)
     * and when processing the 3rd level, we would have 4 nodes (2 * L + R) and so on. The tricky bit here is to ensure
     * we don't compute the queue size again and again in the loop since that would completely mess up our logic.
     */
    public List<Integer> largestValues(TreeNode root) {
        var out = new ArrayList<Integer>();
        ArrayDeque<TreeNode> dq = new ArrayDeque<>();
        dq.offer(root);

        while (!dq.isEmpty()) {
            int max = Integer.MIN_VALUE;
            for (int i = 0, sz = dq.size(); i < sz; ++i) {
                TreeNode lvlNode = dq.poll();
                max = Math.max(max, lvlNode.val);
                if (lvlNode.left != null) dq.offer(lvlNode.left);
                if (lvlNode.right != null) dq.offer(lvlNode.right);
            }
            out.add(max);
        }
        return out;
    }

}

class DFS {

    public List<Integer> largestValues(TreeNode root) {
        var out = new ArrayList<Integer>();
        dfs(out, root, 0);
        return out;
    }

    /**
     * Basically we start with a default depth of 0 and then determine the maximum at each
     * depth per node / recursive call. The trick/tip here is knowing when to add values to
     * our resulting arraylist. We do this by relying on the depth parameter and the size of
     * the arraylist. If the size of the arraylist is the same as the depth, we know that this
     * is the first time we have encountered a node for the given depth. For e.g. when list is
     * empty, the size is 0 and the default depth is 0. So we know we need to add the value from
     * our root node to the list.
     *
     * For all subsequent nodes, we need to ensure we *overwrite* the maximum value for that depth
     * position. Basically we need to internalize the fact that the position of elements in our
     * resulting list correspond to the maximum value for that particular depth (starting with 0).
     *
     * Time complexity is O(n) since we iterate over `n` nodes in the tree.
     *
     * Space complexity is O(n). To support our recursive calls, in the worst case wherein our tree is a linked list.
     * Otherwise, it would usually take O(log(n)) space.
     */
    private void dfs(List<Integer> out, TreeNode node, int depth) {
        if (node == null) return;
        if (out.size() == depth) {
            out.add(node.val);
        } else {
            out.set(out.size() - 1, Math.max(node.val, out.get(out.size() - 1)));
        }
        dfs(out, node.left, depth + 1);
        dfs(out, node.right, depth + 1);
    }

}