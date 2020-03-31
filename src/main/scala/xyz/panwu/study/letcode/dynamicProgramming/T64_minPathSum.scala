package xyz.panwu.study.letcode.dynamicProgramming

/**
 * 64. 最小路径和
 *
 * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 *
 * 说明：每次只能向下或者向右移动一步。
 *
 * 示例:
 *
 * 输入:
 * [
 *   [1,3,1],
 * [1,5,1],
 * [4,2,1]
 * ]
 * 输出: 7
 * 解释: 因为路径 1→3→1→1→1 的总和最小。
 *
 *
 * 链接：https://leetcode-cn.com/problems/minimum-path-sum
 */
object T64_minPathSum {
  /**
   * 状态定义：
   *
   * 设 dpdp 为大小 m \times nm×n 矩阵，其中 dp[i][j]dp[i][j] 的值代表直到走到 (i,j)(i,j) 的最小路径和。
   *
   *
   * 转移方程：
   *
   * 题目要求，只能向右或向下走，换句话说，当前单元格 (i,j)(i,j) 只能从左方单元格 (i-1,j)(i−1,j) 或上方单元格 (i,j-1)(i,j−1) 走到，因此只需要考虑矩阵左边界和上边界。
   *
   * 走到当前单元格 (i,j)(i,j) 的最小路径和 == “从左方单元格 (i-1,j)(i−1,j) 与 从上方单元格 (i,j-1)(i,j−1) 走来的 两个最小路径和中较小的 ” ++ 当前单元格值 grid[i][j]grid[i][j] 。具体分为以下 44 种情况：
   * 当左边和上边都不是矩阵边界时： 即当i != 0 j != 0时，dp[i][j] = min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j]；
   * 当只有左边是矩阵边界时： 只能从上面来，即当i = 0, j != 0， dp[i][j] = dp[i][j - 1] + grid[i][j]；
   * 当只有上边是矩阵边界时： 只能从左面来，即当i !=0,j == 0时， dp[i][j] = dp[i - 1][j] + grid[i]；
   * 当左边和上边都是矩阵边界时： 即当i = 0, j = 0i=0,j=0时，其实就是起点， dp[i][j] = grid[i][j]；
   * 初始状态：
   * dpdp 初始化即可，不需要修改初始 00 值。
   *
   *
   * 返回值：
   *
   * 返回 dpdp 矩阵右下角值，即走到终点的最小路径和。
   * 其实我们完全不需要建立 dpdp 矩阵浪费额外空间，直接遍历 grid[i][j] 修改即可。这是因为：grid[i][j] = min(grid[i - 1][j], grid[i][j - 1]) + grid[i][j]；
   * 原 gridgrid 矩阵元素中被覆盖为 dpdp 元素后（都处于当前遍历点的左上方），不会再被使用到。
   *
   * @param grid
   * @return
   */
  def minPathSum(grid: Array[Array[Int]]): Int = {
    grid.indices.foreach(i => {
      grid(0).indices.foreach(j => {
        if (i == 0 && j == 0) {}
        else if (i == 0) grid(i)(j) = grid(i)(j) + grid(i)(j - 1)
        else if (j == 0) grid(i)(j) = grid(i)(j) + grid(i - 1)(j)
        else grid(i)(j) = grid(i)(j) + Math.min(grid(i - 1)(j), grid(i)(j - 1))
      })
    })
    grid(grid.length - 1)(grid(0).length - 1)
  }
}
