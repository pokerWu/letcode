package xyz.panwu.study.letcode.dynamicProgramming

/**
 * 714. 买卖股票的最佳时机含手续费
 *
 * 给定一个整数数组 prices，其中第 i 个元素代表了第 i 天的股票价格 ；非负整数 fee 代表了交易股票的手续费用。
 *
 * 你可以无限次地完成交易，但是你每次交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
 *
 * 返回获得利润的最大值。
 *
 * 示例 1:
 *
 * 输入: prices = [1, 3, 2, 8, 4, 9], fee = 2
 * 输出: 8
 * 解释: 能够达到的最大利润:
 * 在此处买入 prices[0] = 1
 * 在此处卖出 prices[3] = 8
 * 在此处买入 prices[4] = 4
 * 在此处卖出 prices[5] = 9
 * 总利润: ((8 - 1) - 2) + ((9 - 4) - 2) = 8.
 *
 * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee
 */
object T714_maxProfit {

  /**
   * 第 1 步：状态定义
   * dp[i][j] 表示 [0, i] 区间内，到第 i 天（从 0 开始）状态为 j 时的最大收益。
   *
   * 这里 j 取两个值：
   *
   * 0 表示不持股；
   * 1 表示持股。
   * 第 2 步：状态转移方程
   * dp[i][0]：当天不持股，可以由昨天不持股和昨天持股转换而来。
   *
   * 昨天不持股，今天仍然不持股，则说明今天什么都没做。
   * 昨天持股，今天不持股，则说明今天卖出了一股。思考到这里，这题有一个手续费，我们都规定，手续费在买入股票的时候，一起扣掉。也可以规定在卖出一股的时候，扣除手续费，前后统一即可。
   *
   * dp[i][0] = max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
   *
   *
   *
   * dp[i][1]：当天持股，也可以由昨天不持股和昨天持股转换而来。
   *
   * 昨天不持股，今天持股，则说明今天买了一股，并且扣除了手续费。
   * 昨天持股，今天仍然持股，则说明今天什么都没做。
   *
   * dp[i][1] = max(dp[i - 1][1], dp[i - 1][0] - prices[i] - fee);
   * 第 3 步：思考初始化
   * 在第 0 天，不持股的初始化值为 0，持股的初始化值为 -prices[0] - fee（表示购买了一股且扣除了手续费）。
   *
   * 第 4 步：思考输出
   * 每一天都由前面几天的状态转换而来，最优值在最后一天，并且是不持股的状态。
   *
   * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/solution/dong-tai-gui-hua-by-liweiwei1419-6/
   *
   * @param prices
   * @param fee
   * @return
   */
  def maxProfit(prices: Array[Int], fee: Int): Int = {
    if (prices == null || prices.length < 2) return 0
    val dp = Array.ofDim[Int](prices.length, 2)
    //不持股， 卖
    dp(0)(0) = 0
    //买
    dp(0)(1) = -prices(0)
    (1 until(prices.length)).foreach(i => {
      //卖，昨天不持股，今天就不需要操作。 昨天有仓位，看看以今天的价格卖，获利是不是最大的
      // dp(i - 1)(1) + prices(i) - fee
      dp(i)(0) = Math.max(dp(i -1)(0), dp(i - 1)(1) + prices(i) - fee)
      //买，
      //有仓位，不操作。当前价格越高，持仓成本越高，max找出是是最低持仓成本
      dp(i)(1) = Math.max(dp(i - 1)(1), dp(i - 1)(0) - prices(i))
    })

    dp(prices.length - 1)(0)

  }


  def maxProfit2(prices: Array[Int], fee: Int): Int = {
    if (prices == null || prices.length < 2) return 0
    val dp = Array.ofDim[Int](prices.length, 2)
    //不持股， 卖
    var cash = 0
    //买
    var hold = -prices(0)
    (1 until(prices.length)).foreach(i => {
      //卖，昨天不持股，今天就不需要操作。 昨天有仓位，看看以今天的价格卖，获利是不是最大的
      // dp(i - 1)(1) + prices(i) - fee
      cash = Math.max(cash, hold + prices(i) - fee)
      //买，
      //有仓位，不操作。当前价格越高，持仓成本越高，max找出是是最低持仓成本
      hold = Math.max(hold, cash - prices(i))
    })

   cash

  }
  def main(args: Array[String]): Unit = {
    println(maxProfit2(Array(1, 3, 2, 8, 4, 9), 2))
  }
}
