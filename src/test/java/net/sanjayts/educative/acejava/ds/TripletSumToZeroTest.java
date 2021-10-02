package net.sanjayts.educative.acejava.ds;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


class TripletSumToZeroTest {

    @ParameterizedTest
    @MethodSource("testParams")
    void searchTripletsNoSortShouldFindTripletsWithoutSorting(int[] arr, List<List<Integer>> expected) {
        List<List<Integer>> actual = new TripletSumToZero().searchTripletsNoSort(arr);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("testParams")
    void searchTripletsTwoPointerShouldFindTriplets(int[] arr, List<List<Integer>> expected) {
        List<List<Integer>> actual = new TripletSumToZero().searchTripletsTwoPointer(arr);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("testParams")
    void searchTripletsWithBinarySearchShouldFindTriplets(int[] arr, List<List<Integer>> expected) {
        List<List<Integer>> actual = new TripletSumToZero().searchTripletsWithBinarySearch(arr);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testParams() {
        return Stream.of(
                Arguments.of(new int[]{4, 1, -5}, List.of(Arrays.asList(-5, 1, 4)))
        );
    }

}
