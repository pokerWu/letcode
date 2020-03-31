package xyz.panwu.study.letcode.dynamicProgramming

/**
 * 120. 三角形最小路径和
 *
 *
 * 给定一个三角形，找出自顶向下的最小路径和。每一步只能移动到下一行中相邻的结点上。
 * *
 * 例如，给定三角形：
 * *
 * [
 * [2],
 * [3,4],
 * [6,5,7],
 * [4,1,8,3]
 * ]
 * 自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
 * *
 * 说明：
 * *
 * 如果你可以只使用 O(n) 的额外空间（n 为三角形的总行数）来解决这个问题，那么你的算法会很加分。
 *
 */
object T120_minimumTotal {
  /**
   * 状态定义：dp[i][j]表示包含第i行第j列元素的最小路径和
   * 状态分析
   *
   * 初始化：
   * dp[0][0]=triangle[0][0]
   *
   * 常规：
   * triangle[i][j]一定会经过triangle[i-1][j]或者triangle[i-1][j-1],
   * 所以状态dp[i][j]一定等于dp[i-1][j]或者dp[i-1][j-1]的最小值+triangle[i][j]
   *
   * 特殊：
   * triangle[i][0]没有左上角 只能从triangle[i-1][j]经过
   * triangle[i][row[0].length]没有上面 只能从triangle[i-1][j-1]经过
   * 转换方程：dp[i][j]=min(dp[i-1][j],dp[i-1][j-1])+triangle[i][j]
   *
   * 作者：ggb2312
   * 链接：https://leetcode-cn.com/problems/triangle/solution/javadong-tai-gui-hua-si-lu-yi-ji-dai-ma-shi-xian-b/
   *
   * 实现时对空间做了优化，思想是一样的
   * @param triangle
   * @return
   */
  def minimumTotal(triangle: List[List[Int]]): Int = {
    if (triangle == null || triangle.isEmpty) return 0
    //按照这个三角形的属性，我们只需要保存上一行的数据就行，最大也就是最后一行的数据
    val dp = new Array[Int](triangle.last.size)
    triangle.indices.foreach(i => {
      var cur = 0
      var pre = 0
      triangle(i).indices.foreach(j => {
        cur = dp(j)
        //第一行
        if (i == 0 && j == 0) dp(j) = triangle(i)(j)
        //最后一列，直接累加
        //非第一行每行遍历的最后一个， 需要加上上一行的结果，此时的pre就是上一行的自后一个
        else if (j == i) {
          dp(j) = pre + triangle(i)(j)
        }
        //第一列， 直接累积
        //非第一行每次遍历一行必然先执行这个句
        //此时的dp保留就是就是上一个行的数据
        else if (j == 0) {
          dp(j) = cur + triangle(i)(j)
        }
        // 因为dp需要覆盖，而我们只需要上一行j位置的 和 j-1位置的结果，所以需要用变量保留
        else {
          dp(j) = Math.min(cur, pre) + triangle(i)(j)
        }
        pre = cur
      })

    })
    dp.min
  }

  def main(args: Array[String]): Unit = {
    println(minimumTotal(List(
      List(2),
      List(3,4),
      List(6,5,7),
      List(4,1,8,3)
    )))
  }
}
