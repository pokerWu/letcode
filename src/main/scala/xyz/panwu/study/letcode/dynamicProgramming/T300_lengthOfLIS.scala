package xyz.panwu.study.letcode.dynamicProgramming

/**
 * 300. 最长上升子序列
 *
 *
 * 给定一个无序的整数数组，找到其中最长上升子序列的长度。
 *
 * 示例:
 *
 * 输入: [10,9,2,5,3,7,101,18]
 * 输出: 4
 * 解释: 最长的上升子序列是 [2,3,7,101]，它的长度是 4。
 * 说明:
 *
 * 可能会有多种最长上升子序列的组合，你只需要输出对应的长度即可。
 * 你算法的时间复杂度应该为 O(n2) 。
 * 进阶: 你能将算法的时间复杂度降低到 O(n log n) 吗?
 *
 * 链接：https://leetcode-cn.com/problems/longest-increasing-subsequence
 */
object T300_lengthOfLIS {

  /**
   * 解题思路：
   * 状态定义：
   *
   * dp[i]dp[i] 的值代表 nums 前 ii 个数字的最长子序列长度。
   * 转移方程： 设 j∈[0,i)j∈[0,i)，考虑每轮计算新 dp[i]dp[i] 时，遍历 [0,i)[0,i) 列表区间，做以下判断：
   *
   * 当 nums[i] > nums[j] 时： nums[i] 可以接在 nums[j] 之后（此题要求严格递增），此情况下最长上升子序列长度为 dp[j] + 1 ；
   * 当 nums[i] <= nums[j] 时： nums[i] 无法接在 nums[j] 之后，此情况上升子序列不成立，跳过。
   * 上述所有 1. 情况 下计算出的 dp[j] + 1 的最大值，为直到 i 的最长上升子序列长度（即 dp[i] ）。实现方式为遍历 j 时，每轮执行 dp[i] = max(dp[i], dp[j] + 1)。
   * 转移方程： dp[i] = max(dp[i], dp[j] + 1) for j in [0, i)。
   * 初始状态：
   *
   * dp[i]dp[i] 所有元素置 11，含义是每个元素都至少可以单独成为子序列，此时长度都为 11。
   * 返回值：
   *
   * 返回 dpdp 列表最大值，即可得到全局最长上升子序列长度。
   *
   * 链接：https://leetcode-cn.com/problems/longest-increasing-subsequence/solution/zui-chang-shang-sheng-zi-xu-lie-dong-tai-gui-hua-2/
   *
   * @param nums
   * @return
   */
  def lengthOfLIS(nums: Array[Int]): Int = {
    //初始化 dp[i]包含本身1个长度
    val dp = Array.fill(nums.length)(1)
    var max = 0
    for (head <- nums.indices) {
      for (pre <- 0 until(head)) {
        //当当前大与pre时就可以追加到pre后面构成新的升序
        if (nums(head) > nums(pre)) {
          //要求最长
          dp(head) = Math.max(dp(head), dp(pre) + 1)
        }
      }
      max = Math.max(max, dp(head))
    }
    max
  }

  // TODO: dp + 二分查找 ： https://leetcode-cn.com/problems/longest-increasing-subsequence/solution/dong-tai-gui-hua-er-fen-cha-zhao-tan-xin-suan-fa-p/

  def main(args: Array[String]): Unit = {
    println(lengthOfLIS(Array(10,9,2,5,3,7,101,18)))
  }
}
