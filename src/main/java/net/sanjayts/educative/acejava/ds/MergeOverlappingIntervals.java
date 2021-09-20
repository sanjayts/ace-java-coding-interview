package net.sanjayts.educative.acejava.ds;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/*
https://leetcode.com/problems/merge-intervals/
https://www.educative.io/module/lesson/data-structures-in-java/JP8nQWG5Q4P
 */
@Slf4j
public class MergeOverlappingIntervals {

    public static void main(String[] args) {
        ArrayList<Pair> v = new ArrayList<Pair>();

        v.add(new Pair(1, 5));
        v.add(new Pair(3, 7));
        v.add(new Pair(4, 6));
        v.add(new Pair(6, 8));
        v.add(new Pair(10, 12));
        v.add(new Pair(11, 15));

        ArrayList<Pair> result = mergeIntervals(v);

        for (int i = 0; i < result.size(); i++) {
            log.info("({}, {})", result.get(i).first, result.get(i).second);
        }
    }

    static ArrayList<Pair> mergeIntervals(ArrayList<Pair> v) {
        if (v == null || v.size() == 0) {
            return null;
        }
        // For the leetcode solution, we simply sort the input since that's not part of the problem statement
        // Like so: Arrays.sort(intervals, (a1, a2) -> Integer.compare(a1[0], a2[0]));
        // The time complexity in this case would be O(nlogn)

        // For the educative solution the inputs are already sorted based on the start time so time complexity is O(n)
        ArrayList<Pair> result = new ArrayList<>();
        Pair fst = v.get(0), p = null;
        int s = fst.first, e = fst.second;

        for (int i = 1; i < v.size(); ++i) {
            p = v.get(i);
            if (p.first <= e) {
                if (p.second > e) {
                    e = p.second;
                }
            } else {
                result.add(new Pair(s, e));
                s = p.first;
                e = p.second;
            }
        }
        result.add(new Pair(s, e));
        return result;
    }

}


// Public fields to make educative grader happy
@AllArgsConstructor
class Pair {
    public int first;
    public int second;
}
