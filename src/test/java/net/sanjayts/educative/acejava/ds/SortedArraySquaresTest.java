package net.sanjayts.educative.acejava.ds;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class SortedArraySquaresTest {

    @ParameterizedTest
    @MethodSource("makeSquaresArgs")
    void makeSquares_ShouldReturnSquaresOfInputArraySorted(int[] input, int[] expected) {
        int[] actual = new SortedArraySquares().makeSquares(input);
        Assertions.assertThat(expected).isEqualTo(actual);
    }

    static Stream<Arguments> makeSquaresArgs() {
        return Stream.of(
                Arguments.of(new int[]{-8, -6, 1, 4}, new int[] {1, 16, 36, 64}),
                Arguments.of(new int[]{}, new int[] {}),
                Arguments.of(new int[]{-1}, new int[] {1}),
                Arguments.of(new int[]{-4, -3, -2, -1}, new int[] {1, 4, 9, 16})
        );
    }

}