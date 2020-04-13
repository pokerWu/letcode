package xyz.panwu.study.letcode.dynamicProgramming

/**
 * 给两个整数数组 A 和 B ，返回两个数组中公共的、长度最长的子数组的长度。
 *
 * 示例 1:
 *
 * 输入:
 * A: [1,2,3,2,1]
 * B: [3,2,1,4,7]
 * 输出: 3
 * 解释:
 * 长度最长的公共子数组是 [3, 2, 1]。
 *
 * 链接：https://leetcode-cn.com/problems/maximum-length-of-repeated-subarray
 */
object T718_findLength {
  /**
   *     3  2  1  4  7
   *  1  0  0  1  0  0
   *  2  0  1  0  0  0
   *  3  1  0  0  0  0
   *  2  0  2  0  0  0
   *  1  0  0  3  0  0
   * @param A
   * @param B
   * @return
   */
  def findLength(A: Array[Int], B: Array[Int]): Int = {
    //dp(i)(j) 表示到A i 与 B  j能达到的最长子数组
    // 如果 A(i) == A(j) 那么，到这个i,j的位置的最长的子数组长度就是+ 上一个位置 i -1, j-1
    val dp = Array.ofDim[Int](A.length, B.length)
    var maxLen = 0
    A.indices.foreach(i => {
      B.indices.foreach(j => {
        if (i == 0 || j == 0) dp(i)(j) = if (A(i) == B(j)) 1 else 0
        else if (A(i) == B(j)) dp(i)(j) = dp(i - 1)(j - 1) + 1

        maxLen = Math.max(maxLen, dp(i)(j))
      })
    })
    maxLen
  }

  def main(args: Array[String]): Unit = {
    println(findLength(Array(1,2,3,2,1), Array(3,2,1,4,7)))
  }
}
