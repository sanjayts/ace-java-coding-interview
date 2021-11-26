package net.sanjayts.educative.acejava.ds;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class BestTimeToBuySellOneTest {

    @ParameterizedTest
    @MethodSource("testParams")
    void maxProfitOnePassReturnsTheCorrectProfit(int[] prices, int expected) {
        int out = new BestTimeToBuySellOne().maxProfitOnePass(prices);
        Assertions.assertThat(out).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("testParams")
    void maxProfitKadaneReturnsTheCorrectProfit(int[] prices, int expected) {
        int out = new BestTimeToBuySellOne().maxProfitKadane(prices);
        Assertions.assertThat(out).isEqualTo(expected);
    }

    private static Stream<Arguments> testParams() {
        return Stream.of(
                Arguments.of(new int[]{1, 5, 10, 2, 6}, 9),
                Arguments.of(new int[]{5, 4, 3, 2, 1}, 0),
                Arguments.of(new int[]{1, 2, 3, 4, 5}, 4),
                Arguments.of(new int[]{20, 25, 5, 1}, 5)
        );
    }

}