package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/*
https://leetcode.com/problems/longest-palindromic-substring/

https://leetcode.com/problems/longest-palindromic-substring/discuss/151144/Bottom-up-DP-Two-pointers
 */
@Slf4j
public abstract class LongestPalindromicSubstr {

    public static void main(String[] args) {
        List.of(new DPStrategy(), new ConstantSpaceStrategy()).forEach(runner -> {
            final String s = "abbadda";
            String out = runner.longestPalindrome(s);
            log.info("Longest palindromic substring using strategy {} for {} is {}",
                    runner.getClass().getSimpleName(), s, out);
        });
    }

    public abstract String longestPalindrome(String s);

}

/**
 * The intuition here is that palindrome is a string which is symmetric around its center so if we can go over all the
 * focal touch points and expand out checking the character equality, we should be able to find the longest palindrome.
 * For e.g. with the string 'abba', the central touchpoint is between the characters 'bb'. In this strategy, we basically
 * go over the string, one character at a time and try to "expand" out the string with that char as the center. We
 * capture the start and end of all such center characters and keep the ones which have the maximum difference.
 */
class ConstantSpaceStrategy extends LongestPalindromicSubstr {

    @Override
    public String longestPalindrome(String s) {
        int start = 0, end = 0;
        int n = s.length();
        for (int i = 0; i < n; ++i) {
            int right = i;
            // For string abba when we are at the first b, this will basically put the right pointer at the last 'a'
            while (right < n && s.charAt(i) == s.charAt(right)) {
                ++right;
            }
            int left = i - 1;
            while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
                --left; ++right;
            }
            if (end - start < (right - 1 - left - 1)) {
                start = left + 1;
                end = right - 1;
            }
        }
        return s.substring(start, end + 1);
    }

}


/**
 * The idea here is that we can compute the feasibility of a given slice of a string being a palindrome using
 * prior information. For e.g. if we know that a substring 'aba' is palindrome, we can easily derive whether the string
 * 'qabaq' is palindrome or not by checking the newly added extremities and verifying it against the existing palindrome.
 * In this case, q == q and 'aba' is already a palindrome. So 'qabaq' is also a palindrome.
 *
 * We can implement this using a DP array wherein DP(i, j) determines the palindrome status for a given substring
 * s(i, j) both inclusive. To decide whether DP(i, j) is a palindrome, we simply need to check:
 *
 * s(i) == s(j) && DP(i + 1, j - 1) (assuming we start from the lower right end of our DP matrix)
 *
 * Space complexity: O(N^2) where n is the length of the string. This is because we need to maintain our DP array.
 * Time complexity: O(N^2) since we perform N(N+1) / 2 iterations.
 */
class DPStrategy extends LongestPalindromicSubstr {

    @Override
    public String longestPalindrome(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int start = 0, end = 0;

        for (int i = n - 1; i >= 0; --i) {
            for (int j = i; j < n; ++j) {
                if (s.charAt(i) != s.charAt(j)) continue;
                if (j - i <= 1 || dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                    if (end - start < j - i) {
                        start = i;
                        end = j;
                    }
                }
            }
        }
        return s.substring(start, end + 1);
    }

}