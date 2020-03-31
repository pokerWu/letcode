package xyz.panwu.study.letcode.dynamicProgramming

/**
 * 70. 爬楼梯
 *
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 *
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 *
 * 注意：给定 n 是一个正整数。
 *
 * 示例 1：
 *
 * 输入： 2
 * 输出： 2
 * 解释： 有两种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶
 * 2.  2 阶
 * 示例 2：
 *
 * 输入： 3
 * 输出： 3
 * 解释： 有三种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶 + 1 阶
 * 2.  1 阶 + 2 阶
 * 3.  2 阶 + 1 阶
 *
 * 链接：https://leetcode-cn.com/problems/climbing-stairs
 */
object T70_climbStairs {


  /**
   * 标签：动态规划
   * 本问题其实常规解法可以分成多个子问题，爬第n阶楼梯的方法数量，等于 2 部分之和
   *
   * 爬上 n-1 阶楼梯的方法数量。因为再爬1阶就能到第n阶
   * 爬上 n-2 阶楼梯的方法数量，因为再爬2阶就能到第n阶
   * 所以我们得到公式 dp[n] = dp[n-1] + dp[n-2]
   * 同时需要初始化 dp[0]=1 和 dp[1]=1d
   * 时间复杂度：O(n)
   *
   * 链接：https://leetcode-cn.com/problems/climbing-stairs/solution/hua-jie-suan-fa-70-pa-lou-ti-by-guanpengchn/
   *
   * @param n
   * @return
   */
  def climbStairs(n: Int): Int = {
    if (n < 3) return n
    val dp = new Array[Int](n)
    dp(0) = 1
    dp(1) = 2

    for (step <- 2 until(n)) {
      dp(step) = dp(step - 1) + dp(step - 2)
    }

    dp(n - 1)
  }

  def main(args: Array[String]): Unit = {
    println(climbStairs(4))
  }
}