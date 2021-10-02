package net.sanjayts.educative.acejava.ds;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class TripletSumCloseToTargetTest {

    @ParameterizedTest
    @MethodSource("args")
    void searchTripletShouldReturnSumClosestToTarget(int[] arr, int target, int expected) {
        int actual = new TripletSumCloseToTarget().searchTriplet(arr, target);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> args() {
        return Stream.of(
                Arguments.of(new int[]{-3, -2, -5, 3, -4}, -1, -2),
                Arguments.of(new int[]{-1, 2, 1, -4}, 1, 2),
                Arguments.of(new int[]{-5, -3, 1, 3, 5, 9}, 6, 5)
        );
    }

}
