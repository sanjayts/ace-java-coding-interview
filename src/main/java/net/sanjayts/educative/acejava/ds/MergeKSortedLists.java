package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/*
https://leetcode.com/problems/merge-k-sorted-lists/
 */
@Slf4j
public abstract class MergeKSortedLists {

    public static void main(String[] args) {
        List.of(new BruteForce(), new MergeTwoAtATime(), new PQSolution(), new MergeWithDivideConquerWithQueue(),
                new MergeWithDivideConquerRecursive(), new MergeWithDivideConquerConstantSpace()).forEach(runner -> {
            var one = new ListNode(1, new ListNode(3, new ListNode(5)));
            var two = new ListNode(1, new ListNode(2, new ListNode(4)));
            var listNodes = new ListNode[]{one, two};
            ListNode newHead = runner.mergeKLists(listNodes);
            log.info("Final merged list using the strategy {} is {}",
                    runner.getClass().getSimpleName(), listAsString(newHead));
        });
    }

    public static String listAsString(ListNode head) {
        var buf = new StringBuilder();
        while (head != null) {
            buf.append(head.val).append("->");
            head = head.next;
        }
        buf.append("null");
        return buf.toString();
    }

    public ListNode mergeTwo(ListNode n1, ListNode n2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (n1 != null && n2 != null) {
            if (n1.val < n2.val) {
                tail.next = n1;
                n1 = n1.next;
            } else {
                tail.next = n2;
                n2 = n2.next;
            }
            tail = tail.next;
        }
        if (n1 == null) {
            tail.next = n2;
        }
        if (n2 == null) {
            tail.next = n1;
        }
        return dummy.next;
    }

    public abstract ListNode mergeKLists(ListNode[] lists);

}

/**
 * The idea here is very simple -- just save all the elements across multiple lists into a list and then
 * create a brand new ListNode which contains all those numbers in sorted order. This works but is not a
 * very good solution because of the high space/time requirements and also because it completely misses
 * the point of our problem statement.
 *
 * N => total number of nodes across multiple lists
 * Space complexity: O(N) for saving all numbers + O(N) for creating new ListNodes => O(N)
 * Time complexity: O(N) for looping over each node + O(NlogN) for sorting => O(NlogN)
 */
class BruteForce extends MergeKSortedLists {
    @Override
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        ListNode newHead = new ListNode(0);
        List<Integer> nums = new ArrayList<>();
        for (var cur : lists) {
            while (cur != null) {
                nums.add(cur.val);
                cur = cur.next;
            }
        }
        Collections.sort(nums);
        ListNode cur = newHead;
        for (var n : nums) {
            cur.next = new ListNode(n);
            cur = cur.next;
        }
        return newHead.next;
    }
}

/**
 * The idea here is to reuse the solution for merging two lists and tackle the problem, one listnode at a time. So
 * basically with list nodes [n1, n11, n21, n31] containing 10 elements each, we would first merge the initial empty list
 * with n1. Then the result will be merged with n11 and then with n21 and finally with n31. In terms of operations performed,
 * we get 10 + 20 + 30 + 40 = n(1 + 2 + .. + k) = n(k * (k+1)/2) = n * k^2
 *
 * Time complexity => O(nk^2) where `n` is the number of nodes in each listNode and k is the number of list nodes
 * Space complexity => O(1) no additional data structure used apart from the final list
 */
class MergeTwoAtATime extends MergeKSortedLists {

    @Override
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode newHead = null;
        for (var list : lists) {
            newHead = mergeTwo(newHead, list);
        }
        return newHead;
    }
}

/**
 * The intuition here is that as long as we know the list which currently holds the minimum element not yet traversed,
 * we can use that node to create our new sorted list. If we follow a brute force approach, we could, for each element,
 * iterate over all `k` lists. In which case, we would take O(Nk) time, where N => total number of nodes and k is the
 * list count.
 *
 * But we can do better! If we create a priority queue which stores the head pointer to `k` lists and "offers" us the
 * node which has the lowest value ever iteration, we can simply build up our new ListNode.
 *
 * Space complexity => O(k) for the space taken by priority queue
 * Time complexity => O(N log k) given that we are doing roughly N inserts into our priority queue, each of which take
 *                      around log k time.
 */
class PQSolution extends MergeKSortedLists {

    @Override
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        PriorityQueue<ListNode> pq = new PriorityQueue<>(lists.length, Comparator.comparingInt(n -> n.val));
        for (var list : lists) {
            if (list != null) {
                pq.offer(list);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        while (!pq.isEmpty()) {
            ListNode node = pq.poll();
            current.next = node;
            current = current.next;
            node = node.next;
            if (node != null) {
                pq.offer(node);
            }
        }
        return dummy.next;
    }
}

/**
 * In this approach, we will build upon our previous approach of merging one list at a time. The intuition here is that
 * we can tackle separate lists in pairs, one at a time to reduce the number of passes taken from K to log K.
 * So basically with list nodes [n1, n11, n21, n31] containing 10 elements each, we will first merge [n1, n11] and then
 * [n21, n31]. We would then merge the output of these two to form a final list.
 *
 * This divide and conquer approach can be tackled by using a queue which contains a reference
 * to all the head nodes for k lists. We pop two heads at a given time, merge them and push the final result back to the
 * queue. We continue this will we are left with a single head.
 *
 * Time complexity: O(nk log k) where n = number of nodes in each listnode and `k` is the total number of list nodes.
 * Space complexity: O(k) since our queue at a given time will contain at max `k` elements.
 */
class MergeWithDivideConquerWithQueue extends MergeKSortedLists {

    @Override
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        Queue<ListNode> nodeQ = new ArrayDeque<>();
        for (var list : lists) {
            if (list != null) nodeQ.offer(list);
        }
        while (nodeQ.size() > 1) {
            var n1 = nodeQ.poll();
            var n2 = nodeQ.poll();
            nodeQ.offer(mergeTwo(n1, n2));
        }
        return nodeQ.poll();
    }
}

/**
 * This approach follows the same idea from our previous solution with the difference being that instead of using a
 * queue we use a recursive strategy to merge our list nodes.
 *
 * Time complexity: O(nk logk) where n = number of nodes in each listnode and `k` is the total number of list nodes.
 * Space complexity: O(log k) given we will have log k call-depth when making recursive calls.
 *
 * A sample call trace would be as follows:
 *
 * (0, 7)
 *  - (0, 3)
 *      - (0, 1)
 *          - (0, 0)
 *          - (1, 1)
 *      - (2, 3)
 *          - (2, 2)
 *          - (3, 3)
 *  - (4, 7)
 *      - (4, 5)
 *          - (4, 4)
 *          - (5, 5)
 *      - (6, 7)
 *          - (6, 6)
 *          - (7, 7)
 *
 * As you can see, we have a call depth of 3 for 8 lists, hence log K
 */
class MergeWithDivideConquerRecursive extends MergeKSortedLists {

    @Override
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        return partition(lists, 0, lists.length - 1);
    }

    private ListNode partition(ListNode[] lists, int start, int end) {
        if (start == end) {
            return lists[start];
        }
        int mid = start + (end - start) / 2;
        ListNode n1 = partition(lists, start, mid);
        ListNode n2 = partition(lists, mid + 1, end);
        return mergeTwo(n1, n2);
    }

}


/**
 * This approach follows the same idea from our previous approach of divide and conquer with the only difference being
 * that we don't use any aux space (like PQ) or call stack but intelligently iterate over the original array to merge two
 * lists at a given time.
 *
 * Time complexity: O(nk log k) where n is the number of nodes per listnode and k is the total number of list nodes
 * Space complexity: O(1) no additional space
 */
class MergeWithDivideConquerConstantSpace extends MergeKSortedLists {

    @Override
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        int hi = lists.length - 1;
        while (hi != 0) {
            int lo = 0;
            while (lo < hi) {
                lists[lo] = mergeTwo(lists[lo++], lists[hi--]);
            }
        }
        return lists[0];
    }
}

class ListNode {
    public int val;
    ListNode next;

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}