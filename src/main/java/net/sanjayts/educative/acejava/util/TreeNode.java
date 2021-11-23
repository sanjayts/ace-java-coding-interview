package net.sanjayts.educative.acejava.util;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public static TreeNode sampleTree() {
        return new TreeNode(1,
                new TreeNode(2),
                new TreeNode(3, null,
                        new TreeNode(4)));
    }
}