package net.sanjayts.educative.acejava.algo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Cyclesort {

    public static void main(String[] args) {
        int[] arr = new int[] {5, 4, 3, 2, 1, 7, 6};
        log.info("After cycle sort: {}", new Cyclesort().cycleSort(arr));
        Long l = null;
    }

    public int[] cycleSort(int[] arr) {
        int actual, expected;
        for (int i = 0; i < arr.length; ) {
            actual = arr[i];
            expected = i + 1;
            if (actual != expected) {
                arr[i] = arr[actual - 1];
                arr[actual - 1] = actual;
            } else {
                ++i;
            }
        }
        return arr;
    }

}
