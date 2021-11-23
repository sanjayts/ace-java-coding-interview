package net.sanjayts.educative.acejava.ds;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sanjayts.educative.acejava.util.TreeNode;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

@Slf4j
public class SerDeserBinaryTree {

    public static final TreeNode NUL = new TreeNode(0);

    public static final String NVAL = "n";

    public static final String SEP = ",";

    public static void main(String[] args) {
        dfsInorder();
        bfsNaiveMain();
        bfsOptimizedMain();
    }

    public static void dfsInorder() {
        DFSInorder dfsInorder = new DFSInorder();
        TreeNode n = TreeNode.sampleTree();
        String data = dfsInorder.serialize(n);
        log.info("DFS Inorder - Binary representation of the tree is as follows: {}", data);
        TreeNode out = dfsInorder.deserialize(data);
        log.info("{}", dfsInorder.treeEquals(n, out));
    }

    public static void bfsOptimizedMain() {
        BFSSpaceOptimized bfsSpaceOptimized = new BFSSpaceOptimized();
        TreeNode n = TreeNode.sampleTree();
        String data = bfsSpaceOptimized.serialize(n);
        log.info("BFS Optimal - Binary representation of the tree is as follows: {}", data);
        TreeNode out = bfsSpaceOptimized.deserialize(data);
        log.info("{}", bfsSpaceOptimized.treeEquals(n, out));
    }

    public static void bfsNaiveMain() {
        BFSNaive bfsNaive = new BFSNaive();
        TreeNode n = TreeNode.sampleTree();
        String data = bfsNaive.serialize(n);
        log.info("BFS Naive - Binary representation of the tree is as follows: {}", data);
        TreeNode out = bfsNaive.deserialize(data);
        log.info("{}", bfsNaive.treeEquals(n, out));
    }

    public boolean treeEquals(TreeNode n1, TreeNode n2) {
        if (n1 == null && n2 == null) {
            return true;
        }
        if (n1 == null || n2 == null || n1.val != n2.val) {
            return false;
        }
        return treeEquals(n1.left, n2.left) && treeEquals(n1.right, n2.right);
    }

}

class DFSInorder extends SerDeserBinaryTree {

    public String serialize(TreeNode root) {
        StringBuilder buf = serInternal(root, new StringBuilder());
        return buf.toString();
    }

    private StringBuilder serInternal(TreeNode n, StringBuilder buf) {
        if (n == null) {
            return buf.append(NVAL).append(SEP);
        } else {
            buf.append(n.val).append(SEP);
            serInternal(n.left, buf);
            serInternal(n.right, buf);
            return buf;
        }
    }

    public TreeNode deserialize(String data) {
        String[] parts = data.split(SEP);
        State state = new State(parts, 0);
        return deserInternal(state);
    }

    private TreeNode deserInternal(State state) {
        String p = state.partAtIdx();
        state.idx++;
        if (p.startsWith(NVAL)) {
            return null;
        } else {
            TreeNode n = new TreeNode(Integer.parseInt(p));
            n.left = deserInternal(state);
            n.right = deserInternal(state);
            return n;
        }
    }

}

@AllArgsConstructor
class State {
    public String[] parts;
    public int idx;

    public String partAtIdx() {
        return parts[idx];
    }
}

class BFSSpaceOptimized extends SerDeserBinaryTree {

    public String serialize(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        StringBuilder buf = new StringBuilder();
        while (!q.isEmpty()) {
            TreeNode n = q.poll();
            if (n == null) {
                buf.append(NVAL);
            } else {
                buf.append(n.val);
                q.offer(n.left);
                q.offer(n.right);
            }
            buf.append(SEP);
        }
        buf.deleteCharAt(buf.length() - 1);
        return buf.toString();
    }

    public TreeNode deserialize(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        int i = 1;
        String[] parts = data.split(SEP);
        TreeNode root = new TreeNode(Integer.parseInt(parts[0]));
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode n = q.poll();
            if (parts[i].startsWith(NVAL)) {
                n.left = null;
            } else {
                n.left = new TreeNode(Integer.parseInt(parts[i]));
                q.offer(n.left);
            }
            if (parts[i + 1].startsWith(NVAL)) {
                n.right = null;
            } else {
                n.right = new TreeNode(Integer.parseInt(parts[i+1]));
                q.offer(n.right);
            }
            i += 2;
        }
        return root;
    }

}


class BFSNaive extends SerDeserBinaryTree {

    public String serialize(TreeNode root) {
        if (root == null)   return null;
        Queue<TreeNode> q = new ArrayDeque<>();
        StringBuilder buf = new StringBuilder("" + root.val);
        TreeNode cur;
        q.offer(root);
        while (!q.isEmpty()) {
            cur = q.poll();
            handleNode(cur.left, buf, q);
            handleNode(cur.right, buf, q);
        }
        return buf.toString();
    }

    private void handleNode(TreeNode n, StringBuilder buf, Queue<TreeNode> q) {
        buf.append(SEP);
        if (n == null) {
            buf.append('n');
        } else {
            buf.append(n.val);
            q.offer(n);
        }
    }

    public TreeNode deserialize(String data) {
        if (data == null)   return null;
        Queue<TreeNode> nodeQ = new ArrayDeque<>();
        Queue<TreeNode> parentQ = new ArrayDeque<>();
        for (var s : data.split(",")) {
            if (s.trim().equals("")) continue;
            if (s.trim().startsWith("n")) {
                nodeQ.offer(NUL);
                continue;
            }
            TreeNode n = new TreeNode(Integer.parseInt(s.trim()));
            nodeQ.offer(n);
        }

        TreeNode parent = nodeQ.poll();
        parentQ.offer(parent);
        while (!parentQ.isEmpty()) {
            TreeNode n = parentQ.poll();
            TreeNode c1 = nodeQ.poll();
            TreeNode c2 = nodeQ.poll();

            if (c1 != NUL) {
                n.left = c1;
                parentQ.offer(c1);
            }
            if (c2 != NUL) {
                n.right = c2;
                parentQ.offer(c2);
            }
        }

        return parent;
    }
}

