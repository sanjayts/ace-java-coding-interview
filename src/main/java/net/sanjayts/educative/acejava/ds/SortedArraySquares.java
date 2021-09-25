package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

/*
https://www.educative.io/module/lesson/data-structures-in-java/JExP2ZwMVwv

https://leetcode.com/problems/squares-of-a-sorted-array/
 */
@Slf4j
public class SortedArraySquares {

    public static void main(String[] args) {
        var arr = new int[] {-9, -5, -2, 1, 3, 6, 10};
        int[] squares = new SortedArraySquares().makeSquares(arr);
        log.info("Square of sorted array {} is {}", arr, squares);
    }

    public int[] makeSquares(int[] arr) {
        int[] squares = new int[arr.length];
        for (int s = 0, e = arr.length - 1, tidx = arr.length - 1; tidx >= 0; --tidx) {
            if (Math.abs(arr[s]) > Math.abs(arr[e])) {
                squares[tidx] = arr[s] * arr[s];
                ++s;
            } else {
                squares[tidx] = arr[e] * arr[e];
                --e;
            }
        }
        return squares;
    }

}
