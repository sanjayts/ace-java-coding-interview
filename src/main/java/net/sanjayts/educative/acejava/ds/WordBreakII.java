package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class WordBreakII {

    public static void main(String[] args) {
        var s = "aaaaa";
        var words = Arrays.asList("a", "aa", "aaa", "aaaa", "aaaaa", "aaaaaa");
        List<String> breaks = new WordBreakII().wordBreak(s, words);
        log.info("{} output word breaks are {}", breaks.size(), breaks);
    }

    public List<String> wordBreak(String s, List<String> words) {
        List<String> res = new ArrayList<>();
        recur(s, new HashSet<>(words), new LinkedList<>(), 0, res);
        return res;
    }

    private void recur(String s, Set<String> words, LinkedList<String> buf, int start, List<String> res) {
        if (start == s.length()) {
            res.add(String.join(" ", buf));
            return;
        }
        for (int e = start + 1; e <= s.length(); ++e) {
            String sub = s.substring(start, e);
            if (words.contains(sub)) {
                buf.add(sub);
                recur(s, words, buf, e, res);
                buf.removeLast();
            }
        }
    }

}
