package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

/*
https://leetcode.com/problems/maximum-swap/
 */
@Slf4j
public class MaximumSwap {

    public static void main(String[] args) {
        int num = 9786789;
        int out = new MaximumSwap().maximumSwap(num);
        log.info("Maximum swap for number {} is {}", num, out);
        out = new MaximumSwap().maximumSwapWithAuxSpace(num);
        log.info("Maximum swap with additional space for number {} is {}", num, out);
    }

    /**
     * The intuition here is that we want to start from the very end to find the maximum digit *and* at the same time
     * make note of the smaller digit which can be replaced by that maximum digit. This particular phrasing is very
     * important to avoid the case wherein we simply track a single maximum value and then replace it with a smaller digit.
     *
     * For e.g. with 9973, if we have a logic to replace the max with the first lower digit, we would end up getting an
     * output of 9793 instead of making no swaps.
     *
     * To "patch" the above logic, we might think that simply adding a condition which will ensure the digit being replaced
     * is always at a lower position than the position of the maximum digit. This patch will indeed fix the above issue
     * but fail another test case 98368. This is because our maximum digit is at index 0 and there is no other lower pos
     * we can replace it with.
     *
     * Hence, the correct solution is to maintain a 'global' maximum value and whenever we notice a smaller digit which
     * can be replaced with the maximum, we make note of the 'local' maximum at that point. This ensures that:
     *  1. We don't touch inputs which are sorted in descending order
     *  2. We easily handle cases like 98368 where the global maximum (9) should not be used for replacement, but we
     *     should use the local maximum (8) from position (n-1).
     *
     * Time complexity: O(n) where n is the number of digits in our passed in number. This is because we loop over all
     *                  the digits, convert a number to string and back to an integer.
     * Space complexity: O(n) which is taken up by
     *
     * Inspired by the post -- https://leetcode.com/problems/maximum-swap/discuss/107084/C%2B%2B-3ms-O(n)-time-O(n)-space-DP-solution
     */
    public int maximumSwap(int num) {
        char[] digits = Integer.toString(num).toCharArray();
        int localMaxPos = -1, lessThanMaxPos = -1;

        // Start from the back since the aim is to "maximize" the value of our number and hence find a digit with the
        // highest value at the lowest position.
        for (int i = digits.length - 1, maxPos = i; i >= 0; --i) {
            if (digits[i] > digits[maxPos]) {
                maxPos = i;
            }
            if (digits[i] < digits[maxPos]) {
                lessThanMaxPos = i;
                localMaxPos = maxPos;
            }
        }
        if (lessThanMaxPos == -1) {
            return num;
        } else {
            char lowerDigit = digits[lessThanMaxPos];
            digits[lessThanMaxPos] = digits[localMaxPos];
            digits[localMaxPos] = lowerDigit;
            return Integer.parseInt(new String(digits));
        }
    }

    /**
     * The below approach uses additional O(n) space to hold the index of the first maximum number till that particular
     * point. The intuition here is to maintain a separate array of positions of the maximum digit assuming we start
     * iteration from the right (or digits place).
     *
     * Let's consider an example of 1998. In this case, our maxPositions array would look like [2, 2, 2, 3]. This is
     * because starting from the last index, and only including the last index, the maximum digit is 8 so we save its
     * index in the array, which is 3. To visualize it a bit better:
     *
     * i = 3, maxPositions=[0, 0, 0, 3]
     * i = 2, maxPositions=[0, 0, 2, 3]
     * i = 1, maxPositions=[0, 2, 2, 3]
     * i = 0, maxPositions=[2, 2, 2, 3]
     *
     * Now all we need to do is start traversing the digits from the leftmost position and find the index which contains
     * a digit which is *not* the same as the maximum digit till that index.
     *
     * i = 0; digits[0] = 1; digits[maxPositions[0]] => digits[2] = 9; 1 != 9 so swap and return 9918
     *
     * Let's try another input which trips up a lot of implementations -- 8765
     *
     * i = 3, maxPositions=[0, 0, 0, 3]
     * i = 2, maxPositions=[0, 0, 2, 3]
     * i = 1, maxPositions=[0, 0, 2, 3]
     * i = 0, maxPositions=[0, 1, 2, 3]
     *
     * i = 0; digits[0] = 8; digits[maxPositions[0]] = 8 => no action taken
     * i = 1; digits[0] = 7; digits[maxPositions[0]] = 7 => no action taken
     * i = 2; digits[0] = 6; digits[maxPositions[0]] = 6 => no action taken
     * i = 3; digits[0] = 5; digits[maxPositions[0]] = 5 => no action taken
     *
     * As mentioned above, it's very important to understand that just maintaining a maximum is not enough; it needs to
     * be contextualized by the position it occurs at, which is taken care by the maxPositions array in this implementation.
     */
    public int maximumSwapWithAuxSpace(int num) {
        char[] digits = Integer.toString(num).toCharArray();
        int[] maxPositions = new int[digits.length]; // Holds the first index of the maximum number encountered this a given pos

        for (int i = digits.length - 1, maxPos = i; i >= 0; --i) {
            if (digits[i] > digits[maxPos]) {
                maxPos = i;
            }
            maxPositions[i] = maxPos;
        }
        for (int i = 0; i < digits.length; ++i) {
            if (digits[i] != digits[maxPositions[i]]) {
                char ch = digits[maxPositions[i]];
                digits[maxPositions[i]] = digits[i];
                digits[i] = ch;
                return Integer.parseInt(new String(digits));
            }
        }
        return num; // No swap needed so return the input
    }

}
