package net.sanjayts.educative.acejava.algo;

import lombok.extern.slf4j.Slf4j;
import net.jqwik.api.*;
import org.assertj.core.api.Assertions;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
class CyclesortTest {

    private static final int MAXIMUM_OBJECT_COUNT = 10_000;

    @Property(tries = 1000)
    void arrayOf1ToNShouldBeSorted(@ForAll("uniqueSequential") int[] arr) {
        int[] expected = IntStream.rangeClosed(1, arr.length).toArray();
        new Cyclesort().cycleSort(arr);
        Assertions.assertThat(arr).isEqualTo(expected);
        log.trace("\nActual: {}\nExpected: {}", arr, expected);
    }

    @Provide
    Arbitrary<int[]> uniqueSequential() {
        return Arbitraries.integers().between(1, MAXIMUM_OBJECT_COUNT).map(c -> {
                List<Integer> nums = IntStream.rangeClosed(1, c).boxed().collect(Collectors.toList());
                Collections.shuffle(nums);
                return nums.stream().mapToInt(Integer::intValue).toArray();
        });
    }

}