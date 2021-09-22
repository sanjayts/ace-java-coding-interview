package net.sanjayts.educative.acejava.algo;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.assertj.core.api.Assertions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use property based testing for our sort implementation since that's the best way to verify the authenticity of
 * our implementation without getting burdened by trying to craft variety of inputs to test out the invariants.
 */
class QuicksortTest {

    @Property(tries = 10_000)
    void allIntegersWillBeSortedAfterQsort(@ForAll int[] arr) {
        List<Integer> originalSorted = Arrays.stream(arr).sorted().boxed().collect(Collectors.toList());
        new Quicksort().qSort(arr);
        Assertions.assertThat(arr).isSorted();
        List<Integer> afterSort = Arrays.stream(arr).sorted().boxed().collect(Collectors.toList());
        Assertions.assertThat(originalSorted).isEqualTo(afterSort);
    }

}
