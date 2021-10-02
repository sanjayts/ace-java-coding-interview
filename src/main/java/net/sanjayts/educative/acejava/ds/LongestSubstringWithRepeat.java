package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;


/*
https://leetcode.com/problems/longest-substring-without-repeating-characters/
 */
@Slf4j
public class LongestSubstringWithRepeat {

    public static void main(String[] args) {
        LongestSubstringWithRepeat prg = new LongestSubstringWithRepeat();
        String s = "abcdabcd";
        int len = prg.lengthOfLongestSubstring(s);
        log.info("string={}; longest substring={}", s, len);
    }

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        Integer[] seen = new Integer[256];
        int len = 0, left = 0, right = 0;
        while (right < s.length()) {
            char ch = s.charAt(right);
            if (seen[ch] != null && seen[ch] >= left) {
                left = seen[ch] + 1;
            }
            seen[ch] = right;
            len = Math.max(len, right - left + 1);
            right++;
        }
        return len;
    }

}
