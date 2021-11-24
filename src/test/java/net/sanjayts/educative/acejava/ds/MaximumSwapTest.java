package net.sanjayts.educative.acejava.ds;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class MaximumSwapTest {

    @ParameterizedTest
    @MethodSource("testParams")
    void maximumSwapShouldAlwaysReturnMaximumValue(int num, int expected) {
        int out = new MaximumSwap().maximumSwap(num);
        Assertions.assertThat(out).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("testParams")
    void maximumSwapWithAuxStorageShouldAlwaysReturnMaximumValue(int num, int expected) {
        int out = new MaximumSwap().maximumSwapWithAuxSpace(num);
        Assertions.assertThat(out).isEqualTo(expected);
    }

    private static Stream<Arguments> testParams() {
        return Stream.of(
                Arguments.of(9973, 9973),
                Arguments.of(2736, 7236),
                Arguments.of(9819, 9918),
                Arguments.of(1998, 9918),
                Arguments.of(98368, 98863),
                Arguments.of(9876, 9876)
        );
    }

}
