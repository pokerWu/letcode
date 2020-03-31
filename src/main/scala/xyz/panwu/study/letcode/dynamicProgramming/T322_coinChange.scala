package xyz.panwu.study.letcode.dynamicProgramming

/**
 * 322. 零钱兑换
 *
 * 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
 *
 * 示例 1:
 *
 * 输入: coins = [1, 2, 5], amount = 11
 * 输出: 3
 * 解释: 11 = 5 + 5 + 1
 *
 *
 * 示例 2:
 *
 * 输入: coins = [2], amount = 3
 * 输出: -1
 *
 * 链接：https://leetcode-cn.com/problems/coin-change
 */
object T322_coinChange {

  var count: Array[Int] = _

  /**
   * 方法一：动态规划
   * 思路：
   *
   * 看题目的问法，只问最优值是多少，没有要我们求最优解，一般情况下就是「动态规划」可以解决的问题；
   * 最优子结构其实比较明显，根据示例 1：
   * 输入: coins = [1, 2, 5], amount = 11
   * 凑成面值为 11 的最小硬币数可以由以下 33 者的最小值得到：
   *
   * 1、凑成面值为 10 的最小硬币数 + 面值为 1 的这一枚硬币；
   *
   * 2、凑成面值为 9 的最小硬币数 + 面值为 2 的这一枚硬币；
   *
   * 3、凑成面值为 6 的最小硬币数 + 面值为 5 的这一枚硬币；
   *
   * 即 dp[11] = min (dp[10] + 1, dp[9] + 1, dp[6] + 1)。
   *
   * 可以直接把题目的问法设计成状态。
   * 第 1 步：定义「状态」
   * dp[i] ：凑齐总价值 i 需要的最少硬币数，状态就是问的问题。
   *
   * 第 2 步：写出「状态转移方程」
   * 根据对具体例子的分析：
   *
   * dp[amount] = min(1 + dp[amount - coin[i]]) for i in [0, len - 1] if coin[i] <= amount
   * 注意的是：
   *
   * 1、首先硬币的面值首先要小于等于当前要凑出来的面值；
   *
   * 2、剩余的那个面值应该要能够凑出来，例如：求 dp[11] 需要参考 dp[10] ，如果不能凑出来的话，dp[10] 应该等于一个不可能的值，可以设计为 11 + 1，也可以设计为 -1 ，它们的区别只是在具体的代码编写细节上不一样而已。
   *
   * 再强调一次：新状态的值要参考的值以前计算出来的「有效」状态值。这一点在编码的时候需要特别注意。
   *
   * 因此，不妨先假设凑不出来，因为比的是小，所以设置一个不可能的数。
   *
   * 作者：liweiwei1419
   * 链接：https://leetcode-cn.com/problems/coin-change/solution/dong-tai-gui-hua-shi-yong-wan-quan-bei-bao-wen-ti-/
   *
   * 动态规划  自上而下
   * @param coins
   * @param amount
   * @return
   */
  def coinChange(coins: Array[Int], amount: Int): Int = {
    count = new Array[Int](amount + 1)
    getMin(coins, amount)
  }

  def getMin(coins: Array[Int], remain: Int): Int = {
    if (remain < 0) return -1
    if (remain == 0) return 0
    if (count(remain) != 0) return count(remain)

    var min = Int.MaxValue
    //求每个remain在币种中的最小值
    for (c <- coins) {
      val sub = getMin(coins, remain - c)
      if (sub >= 0 && sub < min) min = sub + 1
    }
    count(remain) = if (min == Int.MaxValue) -1 else min
    count(remain)
  }

  /**
   * 和上面的自上而下的方式比较就是 上面要计算amount要不断的往下递归
   * 自下而上的方式就是直接从自小逐步到目标值
   *
   * 我们采用自下而上的方式进行思考。仍定义 F(i） 为组成金额 i所需最少的硬币数量，假设在计算 F(i) 之前，我们已经计算出 F(0) ～ F(i-1) 的答案
   * F(3)=min(F(3−c1)(3−c2),F(3−c3))+1
   * =min(F(3−1),F(3−2),F(3−3))+1
   * =min(F(2),F(1),F(0))+1
   * =min(1,1,0)+1
   * =1
   *
   * @param coins
   * @param amount
   * @return
   */
  def dpDownToUp(coins: Array[Int], amount: Int): Int = {
    //用hash，用数组都一样，下标就是金额
    //ps: index = 5  金额为 5的最优值
    val dp = Array.fill(amount + 1)(Int.MaxValue / 2) // 让数字很大，但是不能是max,因为Max+1就变成负了
    //这里必要的，保证最小的有基础累加值而不是 我们设置的很大的数字
    dp(0) = 0
    for (sub <- 1 to(amount)) {
      for (c <- coins) {
        if (sub >= c) {
          dp(sub) = Math.min(dp(sub), dp(sub - c) + 1)
        }
      }
    }
    if (dp(amount) == Int.MaxValue / 2) -1 else dp(amount)
  }

  def main(args: Array[String]): Unit = {
    println(coinChange(Array(1, 2, 5), 11))
    println(dpDownToUp(Array(1, 2, 5), 11))
  }
}
