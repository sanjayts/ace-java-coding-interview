package net.sanjayts.educative.acejava.ds;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/*
https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
https://www.educative.io/module/lesson/data-structures-in-java/3w8WPRE2xo9
 */
@Slf4j
public class StockBuySell {

    /**
     * We basically have two variations to this problem --
     * 1. The leetcode variant which requires us to return a maximum profit or 0 if profit not possible.
     * 2. The educative variant which requires us to return a tuple of (buy,sell) price.
     */
    public static void main(String[] args) {
        log.info("EDUCATIVE VARIANT");
        int[] array1 = {1, 2, 3, 4, 3, 2, 1, 2, 5};
        Tuple<Integer, Integer> result = findBuySellStockPrices(array1);
        log.info("Buy Price: {}, Sell Price: {}}", result.getX(), result.getY());

        int[] array2 = {8, 6, 5, 4, 3, 2, 1};
        result = findBuySellStockPrices(array2);
        log.info("Buy Price: {}, Sell Price: {}}", result.getX(), result.getY());

        log.info("LEETCODE VARIANT");
        int profit = maxProfit(array1);
        log.info("Maximum profit for prices {} is {}", array1, profit);

        profit = maxProfit(array2);
        log.info("Maximum profit for prices {} is {}", array2, profit);
    }


    // Leetcode
    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock/discuss/39062/My-jave-accepted-solution-with-O(N)-time-and-O(1)-space
    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int buy = Integer.MAX_VALUE, profit = 0;
        for (int p : prices) {
            buy = Math.min(buy, p);
            profit = Math.max(p - buy, profit);
        }
        return profit;
    }

    // Educative
    public static Tuple<Integer, Integer> findBuySellStockPrices(int[] prices) {
        if (prices == null || prices.length < 2) {
            return null;
        }
        int globalProfit = Integer.MIN_VALUE;
        int profit, buy = prices[0], globalSell = prices[1];
        for (int i = 1; i < prices.length; ++i) {
            profit = prices[i] - buy;
            if (profit > globalProfit) {
                globalProfit = profit;
                globalSell = prices[i];
            }
            if (prices[i] < buy) {
                buy = prices[i];
            }
        }
        return new Tuple<>(globalSell - globalProfit, globalSell);
    }

}

@Data
@AllArgsConstructor
class Tuple<X, Y> {
    private X x;
    private Y y;
}