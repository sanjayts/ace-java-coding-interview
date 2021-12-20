package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/*
https://leetcode.com/problems/rotate-image/solution/
 */
@Slf4j
public class RotateImage {

    public static void main(String[] args) {
        var clockMat = sampleMatrix();
        new RotateImage().rotate(clockMat);
        log.info("The clockwise rotated image is {}", Arrays.deepToString(clockMat));

        var antiClockMat = sampleMatrix();
        new RotateImage().rotateAntiClock(antiClockMat);
        log.info("The anticlockwise rotated image is {}", Arrays.deepToString(antiClockMat));
    }

    private static int[][] sampleMatrix() {
        return new int[][] {
                new int[]{1, 2, 3},
                new int[]{4, 5, 6},
                new int[]{7, 8, 9}
        };
    }

    // Rotate the image clockwise
    public void rotate(int[][] matrix) {
        horizontalFlip(matrix);
        transposeMajorDiagonal(matrix);
    }

    public void rotateAntiClock(int[][] mat) {
        horizontalFlip(mat);
        transposeMinorDiagonal(mat);
    }

    /*
    Basically flip the entire matrix from the horizontal line i.e. reverse the rows. In simple terms when explained
    using a 3x3 array -- [ [1, 2, 3], [4, 5, 6], [7, 8, 9] ] becomes [ [7, 8, 9], [4, 5, 6], [1, 2, 3] ]
     */
    private void horizontalFlip(int[][] matrix) {
        int lo = 0, hi = matrix.length - 1;
        while (lo < hi) {
            int[] tmp = matrix[lo];
            matrix[lo] = matrix[hi];
            matrix[hi] = tmp;
            ++lo;
            --hi;
        }
    }

    // transpose along the diagonal line running from top left to bottom right
    private void transposeMajorDiagonal(int[][] mat) {
        for (int i = 0; i < mat.length - 1; ++i) {
            for (int j = i + 1; j < mat.length; ++j) {
                int tmp = mat[i][j];
                mat[i][j] = mat[j][i];
                mat[j][i] = tmp;
            }
        }
    }

    // transpose along the diagonal line running from top right to bottom left
    private void transposeMinorDiagonal(int[][] mat) {
        int sz = mat.length - 1;
        for (int i = 0; i < sz; ++i) {
            for (int j = 0; j < sz - i; ++j) {
                int tmp = mat[i][j];
                mat[i][j] = mat[sz - j][sz - i];
                mat[sz - j][sz - i] = tmp;
            }
        }
    }

}
