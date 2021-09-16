package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmallestCommonInThree {

    public static void main(String[] args) {
        int[] arr1 = new int[] { 1, 4, 6, 7, 8, 10, 14 };
        int[] arr2 = new int[] { 1, 4, 5, 6, 7, 8, 50 };
        int[] arr3 = new int[] { 0, 6, 7, 8, 10, 25, 30, 40 };
        Integer lc1 = findLeastCommonNumber(arr1, arr2, arr3);
        log.info("Least common number for {}, {} and {} is {}", arr1, arr2, arr3, lc1);

        int[] arr4 = new int[] { 1, 4, 60};
        int[] arr5 = new int[] { 2, 5, 50 };
        int[] arr6 = new int[] { 0, 6, 40 };
        Integer lc2 = findLeastCommonNumber(arr4, arr5, arr6);
        log.info("Least common number for {}, {} and {} is {}", arr4, arr5, arr6, lc2);
    }

    static Integer findLeastCommonNumber(int[] arr1, int[] arr2, int[] arr3) {
        int p1 = 0, p2 = 0, p3 = 0, sz1 = arr1.length, sz2 = arr2.length, sz3 = arr3.length;
        while (p1 < sz1 && p2 < sz2 && p3 < sz3) {
            int v1 = arr1[p1];
            int v2 = arr2[p2];
            int v3 = arr3[p3];

            if (v1 == v2 && v2 == v3) {
                return v1;
            }
            if (v1 < v2 || v1 < v3) {
                p1++;
            }
            if (v2 < v1 || v2 < v3) {
                p2++;
            }
            if (v3 < v1 || v3 < v2) {
                p3++;
            }
        }
        return -1;
    }


    // nlog(n) naive solution which loops over the first array and performs binary search over the other arrays
    static Integer findLeastCommonNumberNaive(int[] arr1, int[] arr2, int[] arr3) {
        for (int i = 0; i < arr1.length; i++) {
            int needle = arr1[i];
            int p2 = search(arr2, needle);
            if (p2 == -1) {
                continue;
            }
            int p3 = search(arr3, needle);
            if (p3 == -1) {
                continue;
            }
            return needle;
        }
        return -1;
    }

    static Integer search(int[] arr, int key) {
        int start = 0;
        int end = arr.length - 1;
        int mid;

        while (start <= end) {
            mid = start + (end - start) / 2;
            if (arr[mid] == key) {
                return mid;
            } else if (arr[mid] < key) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return -1;
    }


}
