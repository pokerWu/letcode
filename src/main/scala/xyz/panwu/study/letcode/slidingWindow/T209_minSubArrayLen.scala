package xyz.panwu.study.letcode.slidingWindow

/**
 * 给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的连续子数组。如果不存在符合条件的连续子数组，返回 0。
 *
 * 示例: 
 *
 * 输入: s = 7, nums = [2,3,1,2,4,3]
 * 输出: 2
 * 解释: 子数组 [4,3] 是该条件下的长度最小的连续子数组。
 * 进阶:
 *
 * 如果你已经完成了O(n) 时间复杂度的解法, 请尝试 O(n log n) 时间复杂度的解法。
 *
 */
object T209_minSubArrayLen {

  /**
   * 1,2,3,4,5
   *
   * 1    sum = 1  不满足 右扩大
   * 1, 2  sum = 3  不满足 右扩大
   * 1,2,3  sum = 6  不满足 右扩大
   * 1234  sum = 10  不满足 右扩大
   * 12345 sum =15 满足 缩小
   * 2345 sum = 14  满足 缩小
   * 345 sum = 13   满足 缩小
   *
   * 双指针，窗口内的数值没满足，就扩大窗口
   * 当窗口内满足条件，就要尽量找到最小满足情况， 从左缩小窗口，直到不满足
   * @param s
   * @param nums
   * @return
   */
  def minSubArrayLen(s: Int, nums: Array[Int]): Int = {
    if (nums == null || nums.length < 1) return 0

    var windowL = 0
    var windowSum = 0
    var size = Int.MaxValue

    nums.indices.foreach(i => {
      windowSum += nums(i)
      while (windowSum >= s) {
        size = math.min(i - windowL + 1, size)
        windowSum -= nums(windowL)
        windowL += 1
      }
    })
    if (size == Int.MaxValue) 0 else size
  }

  def main(args: Array[String]): Unit = {
    println(minSubArrayLen(11, Array(1,2,3,4,5)))
  }

}
