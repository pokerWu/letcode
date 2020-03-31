package xyz.panwu.study.letcode.dynamicProgramming

/**
 * 53. 最大子序和
 *
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 *
 * 示例:
 *
 * 输入: [-2,1,-3,4,-1,2,1,-5,4],
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 *
 * 链接：https://leetcode-cn.com/problems/maximum-subarray
 */
object T53_maxSubArray {

  /**
   * 思路
   * 这道题用动态规划的思路并不难解决，比较难的是后文提出的用分治法求解，但由于其不是最优解法，所以先不列出来
   * 动态规划的是首先对数组进行遍历，当前最大连续子序列和为 sum，结果为 ans
   * 如果 sum > 0，则说明 sum 对结果有增益效果，则 sum 保留并加上当前遍历数字
   * 如果 sum <= 0，则说明 sum 对结果无增益效果，需要舍弃，则 sum 直接更新为当前遍历数字
   * 每次比较 sum 和 ans的大小，将最大值置为ans，遍历结束返回结果
   * 时间复杂度：O(n)
   *
   * 作者：guanpengchn
   * 链接：https://leetcode-cn.com/problems/maximum-subarray/solution/hua-jie-suan-fa-53-zui-da-zi-xu-he-by-guanpengchn/
   *
   * @param nums
   * @return
   */
  def maxSubArray(nums: Array[Int]): Int = {
    if (nums == null || nums.isEmpty) return 0
    if (nums.length < 2) return nums(0)
    var sum = 0
    var max = nums(0)

    nums.foreach(e => {
      if (sum > 0) {
        sum += e
      } else  sum = e
      max = Math.max(sum, max)
    })
    max
  }

  // TODO:  分治法求解
  def main(args: Array[String]): Unit = {
    println(maxSubArray(Array(-2,1,-3,4,-1,2,1,-5,4)))
  }
}
