package xyz.panwu.study.letcode.dynamicProgramming

/**
 * 221. 最大正方形
 *
 * 在一个由 0 和 1 组成的二维矩阵内，找到只包含 1 的最大正方形，并返回其面积。
 *
 * 示例:
 *
 * 输入:
 *
 * 1 0 1 0 0
 * 1 0 1 1 1
 * 1 1 1 1 1
 * 1 0 0 1 0
 *
 * 输出: 4
 *
 *
 * 链接：https://leetcode-cn.com/problems/maximal-square
 */
object T221_maximalSquare {

  /**
   * 我们用 0 初始化另一个矩阵 dp，维数和原始矩阵维数相同；
   * dp(i,j) 表示的是由 1 组成的最大正方形的边长；
   * 从 (0,0) 开始，对原始矩阵中的每一个 1，我们将当前元素的值更新为
   * dp(i, j)=min(dp(i−1, j), dp(i−1, j−1), dp(i, j−1))+1
   *
   * 我们还用一个变量记录当前出现的最大边长，这样遍历一次，找到最大的正方形边长 maxsqlen，那么结果就是 maxsqlen^2
   *
   * 链接：https://leetcode-cn.com/problems/maximal-square/solution/zui-da-zheng-fang-xing-by-leetcode/
   *
   * @param matrix
   * @return
   */
  def maximalSquare(matrix: Array[Array[Char]]): Int = {
    if (matrix == null || matrix.isEmpty) return 0
    val dp = Array.fill(matrix.length, matrix.head.length)(0)
    var maxSideLen = 0
    matrix.indices.foreach(i => {
      matrix.head.indices.foreach(j => {
        (i, j) match {
          case (0, _) | (_, 0) => {
            dp(i)(j) = matrix(i)(j) - 48
            maxSideLen = Math.max(dp(i)(j), maxSideLen)
          }
          case _ =>
            if (matrix(i)(j) == '0') dp(i)(j) = 0
            else {
              //当前位置能组成正方形的边长取决与 左边，上边， 左上角三个数的最小值+1
              dp(i)(j) = Math.min(Math.min(dp(i - 1)(j - 1), dp(i - 1)(j)), dp(i)(j - 1)) + 1
              maxSideLen = Math.max(dp(i)(j), maxSideLen)
            }
        }
      })
    })
    maxSideLen * maxSideLen
  }

  def main(args: Array[String]): Unit = {
    println(maximalSquare(Array(
      Array('1', '0', '1', '0', '0'),
      Array('1', '0', '1', '1', '1'),
      Array('1', '1', '1', '1', '1'),
      Array('1', '0', '0', '1', '0')
    )))
  }
}
