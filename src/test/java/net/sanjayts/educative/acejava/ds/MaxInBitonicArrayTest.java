package net.sanjayts.educative.acejava.ds;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class MaxInBitonicArrayTest {

    @ParameterizedTest
    @MethodSource("args")
    void findMaxShouldFindMaximumInBitonicArray(int[] input, int expected) {
        int actual = new MaxInBitonicArray().findMax(input);
        Assertions.assertThat(expected).isEqualTo(actual);
    }

    private static Stream<Arguments> args() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 3, 4}, 4),
                Arguments.of(new int[]{4}, 4),
                Arguments.of(new int[]{4, 3, 2, 1}, 4),
                Arguments.of(new int[]{1, 2, 3, 4, 3, 2, 1}, 4)
        );
    }

}
