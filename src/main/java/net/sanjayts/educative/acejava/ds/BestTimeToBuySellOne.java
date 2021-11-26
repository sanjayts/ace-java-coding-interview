package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

/*
https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
 */
@Slf4j
public class BestTimeToBuySellOne {

    /**
     * To understand the intuition here, we need to restate the problem definition. It's all about tracking
     * the lowest buy price followed by the highest possible sell price. Basically keep searching for a buy
     * price which is lower than the existing one and track the corresponding profit which is higher than the
     * existing profit.
     */
    public int maxProfitOnePass(int[] prices) {
        int profit = 0;
        if (prices == null || prices.length < 2) {
            return profit;
        }
        int bp = prices[0];
        for (int i = 1; i < prices.length; ++i) {
            if (prices[i] - bp > profit) {
                profit = prices[i] - bp;
            } else if (prices[i] < bp) {
                bp = prices[i];
            }
        }
        return profit;
    }

    /**
     * The intuition here is that we can have a local running profit at any given point in time and a maximum
     * profit which is the maximum our local profit ever has been.
     *
     * The profit till a given date is the sum of the previous local profit and the difference
     * between the current and the previous stock price. *But* we can get into situations wherein our difference
     * goes below 0 which should not be possible. This usually implies we need to recalibrate our starting point
     * and hence set 0 as our local profit. As an example:
     *
     * [20, 25, 5]
     * When we are at 25 or day 2, our local profit is 5 and the global profit is also 5. WHen we reach 25, we now
     * have our local profit as -15 which is less than 0. As we can see from the prices, this implies that we need
     * to start calculating our profits from scratch starting with the price 5. This will continue till we encounter
     * a price which gives us non-zero positive profit. So for e.g. if we have 2 follow the 5, we would have to
     * restart our profit calculations from 2.
     *
     * The intuition behind: Math.max(0, localProfit + (prices[i] - prices[i-1])) can be understood using the
     * input [1, 5, 10, 15]
     *
     * As we eyeball the input above, we can see that the maximum profit would come from buying at 1 and selling
     * at 15. But since we are iterating only *once* over our data/prices, we need to build up this difference.
     * This is where adding the local profit comes in.
     *
     * Good explanations at:
     *
     * https://medium.com/@rsinghal757/kadanes-algorithm-dynamic-programming-how-and-why-does-it-work-3fd8849ed73d
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/discuss/39038/Kadane's-Algorithm-Since-no-one-has-mentioned-about-this-so-far-%3A)-(In-case-if-interviewer-twists-the-input)
     */
    public int maxProfitKadane(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int localProfit = 0, maxProfit = 0;
        for (int i = 1; i < prices.length; ++i) {
            localProfit = Math.max(0, localProfit + prices[i] - prices[i - 1]);
            maxProfit = Math.max(maxProfit, localProfit);
        }
        return maxProfit;
    }

}
