package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;


/*
https://www.educative.io/module/lesson/data-structures-in-java/JEZqQgJwRGl
https://leetcode.com/problems/move-zeroes/
 */
@Slf4j
public class MoveZeroesLeftRight {

    public static void main(String... args) {
        int[] v = new int[]{1, 10, 20, 0, 59, 63, 0, 88, 0};
        log.info("Original Array: {}", v);
        moveZerosToLeft(v);
        log.info("After Moving Zeroes to Left: {}", v);
        moveZeroesToRight(v);
        log.info("After Moving Zeroes to Right: {}", v);
    }

    /**
     * Here we basically maintain two indices -- one to facilitate reading and other to facilitate writing.
     * We start our iterations from the very end of the array, so that we don't sweep over the array multiple
     * times which  would happen if we moved from the start. For each iteration, if we enounter a zero, ignore
     * it but decrement the read index. If we encounter a non-zero, we make the swap between read and write
     * indices and decrement the write index.
     * <p>
     * This approach needlessly ends up touching the whole array even if we have zeros only at the beginning
     * or worst case scenario an array full of non-zeros. An alternative solution would be to maintain a count
     * of zeroes and use them to swap around data, writing out only when a zero has been found. This is what
     * we try to achieve in the method `moveZeroesToLeft`.
     * <p>
     * We follow roughly the same strategy when moving zeroes to flush right except that in this case we need
     * to start from the left.
     */
    static void moveZerosToLeftNaive(int[] A) {
        int ri, wi;
        ri = wi = A.length - 1;

        while (ri >= 0) {
            if (A[ri] != 0) {
                A[wi] = A[ri];
                --wi;
            }
            --ri;
        }
        while (wi >= 0) {
            A[wi] = 0;
            --wi;
        }
    }

    // Credit -- https://leetcode.com/problems/move-zeroes/discuss/72011/Simple-O(N)-Java-Solution-Using-Insert-Index/187996
    static void moveZerosToLeft(int[] A) {
        int c = 0;
        for (int ri = A.length - 1; ri >= 0; --ri) {
            if (A[ri] == 0) {
                ++c;
            } else if (c != 0) {
                A[ri + c] = A[ri];
                A[ri] = 0;
            }
        }
    }

    static void moveZeroesToRight(int[] A) {
        int c = 0;
        for (int ri = 0; ri < A.length; ++ri) {
            if (A[ri] == 0) {
                ++c;
            } else if (c != 0) {
                A[ri - c] = A[ri];
                A[ri] = 0;
            }
        }
    }

}
