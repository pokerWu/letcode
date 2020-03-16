package xyz.panwu.study.letcode.greedy

/**
 * 55. 跳跃游戏
 *
 * 给定一个非负整数数组，你最初位于数组的第一个位置。
 *
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 *
 * 判断你是否能够到达最后一个位置。
 *
 * 示例 1:
 *
 * 输入: [2,3,1,1,4]
 * 输出: true
 * 解释: 我们可以先跳 1 步，从位置 0 到达 位置 1, 然后再从位置 1 跳 3 步到达最后一个位置。
 * 示例 2:
 *
 * 输入: [3,2,1,0,4]
 * 输出: false
 * 解释: 无论怎样，你总会到达索引为 3 的位置。但该位置的最大跳跃长度是 0 ， 所以你永远不可能到达最后一个位置。
 *
 *
 * 链接：https://leetcode-cn.com/problems/jump-game
 *
 */
object T55_canJump {


  /**
   * 瞄着最远的条，跳得越越好
   * 能跳到最远的先到最远去，左边的也就都能到
   *
   *            2  3  1  1  4
   *
   * index 0    ｜            最远是 index 2， 那就可以跳 index 0， 1， 2
   * index 1       |          最远是 index 1 + 3= 4   已经够了
   *
   * 逐个遍历最远，出现 i 比最远的大了，表示前面是不可能跳过来了，结束
   *
   * @param nums
   * @return
   */
  def canJump(nums: Array[Int]): Boolean = {
    if (nums == null || nums.length == 0) return false
    if (nums.length < 2) return true
    var maxDistance = 0
    for (i <- nums.indices) {
      if (i > maxDistance) return false // 前面的最远最远也不能打到这个位置，那就是不能继续了
      maxDistance = Math.max(maxDistance, i + nums(i))
      if (maxDistance >= nums.length - 1) return true
    }
    true
  }

  def main(args: Array[String]): Unit = {
    println(canJump(Array(2,3,1,1,4)))
    println(canJump(Array(3,2,1,0,4)))
  }

}
