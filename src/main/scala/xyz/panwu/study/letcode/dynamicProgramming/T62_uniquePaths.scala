package xyz.panwu.study.letcode.dynamicProgramming

/**
 *  | s |   |   |
 *  |   |   |   |
 *  |   |   | e |
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
 *
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
 *
 * 问总共有多少条不同的路径？
 *
 * 示例 1:
 *
 * 输入: m = 3, n = 2
 * 输出: 3
 * 解释:
 * 从左上角开始，总共有 3 条路径可以到达右下角。
 * 1. 向右 -> 向右 -> 向下
 * 2. 向右 -> 向下 -> 向右
 * 3. 向下 -> 向右 -> 向右
 * 示例 2:
 *
 * 输入: m = 7, n = 3
 * 输出: 28
 *
 *
 */
object T62_uniquePaths {

  /**
   * 解这道题我们首先要分一下几个步骤来看：
   *
   * 1、确定状态方程
   * 我们使用一个二维数组 dp 来存储答案，dp[i][j]的值是从起始点（也就是(0,0)）走到(i, j)的路径数。
   *
   * 2、确定状态转移方程
   * 上面也说到，dp[i][j]的值就是从起始点（也就是(0,0)）走到(i, j)的路径数，那么如何求出这个值，我们就需要确定状态转移方程，
   * 我们思考一下，假设我们全都知道dp[i][j]的值，题目中说到，小机器人只能往右或者往下，那么dp[i][j]的值就是第 i 行第 j 列这个格子的上面那个格子的值加上左边那个格子的值，
   * 也就是dp[i][j] = dp[i-1][j] + dp[i][j-1]，因为这两个格子都可以走到dp[i][j]这个格子，那么他们的路径数之和就是dp[i][j]的值。
   *
   * 3、确定边界条件
   * 上面说到状态转移方程是dp[i][j] = dp[i-1][j] + dp[i][j-1]，
   * 那么当 i == 0 或者 j == 0 的时候会越界，
   * 而我们想一下，当 i == 0 或者 j == 0 的时候无外乎就是最上一行或者最左一列，我们在最上一行的路径数只能是一条（因为只能一直往左走），所以 dp[0][j]的值全为 1，同理最左一列的值也是1（因为只能一直往下走），
   * 其余的值按照状态转移方程就可以填满了，最后返回最右下角的值（dp[n-1][m-1]）就可以了。
   *
   * 链接：https://leetcode-cn.com/problems/unique-paths/solution/kan-liao-jue-dui-dong-de-dong-tai-gui-hua-by-stree/
   *
   * @param m
   * @param n
   * @return
   */
  def uniquePaths(m: Int, n: Int): Int = {
    val dp = Array.fill(m, n)(1)
    for (i <- 1 until(m)) {
      for (j <- 1 until(n)) {
        dp(i)(j) = dp (i - 1)(j) + dp (i)(j - 1)
      }
    }

    dp(m - 1)(n - 1)
  }

  def main(args: Array[String]): Unit = {
    println(uniquePaths(7, 3))
  }
}
