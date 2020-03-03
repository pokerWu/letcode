package xyz.panwu.study.letcode.pointer

import scala.collection.mutable

/**
 * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 *
 *  
 *
 * 示例：
 *
 * 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 *
 * 满足要求的三元组集合为：
 * [
 * [-1, 0, 1],
 * [-1, -1, 2]
 * ]
 *
 * 链接：https://leetcode-cn.com/problems/3sum
 */
object T15_threeSum {

  /**
   * 使用先排序+ 双指针做法
   *
   * 双连表法就是参考这道题的解题思路了
   *
   * 如果使用hash表记录的做法，不好做去重
   * @param nums
   * @return
   */
  def threeSum(nums: Array[Int]): List[List[Int]] = {
    if (nums == null || nums.length < 3) return List.empty;
    val sort = nums.sorted

    val res = new mutable.ListBuffer[List[Int]]()

    for (i <- 0 until (nums.length - 2)) {
      var left = i + 1
      var right = nums.length - 1
      //跳过和上一个一样的值，因为会造成重复的结果
      //i的值超过0就没必要继续进行了了
      if ((i == 0 || (i != 0 && sort(i) != sort(i - 1))) && sort(i) <= 0) {
        while (left < right) {
          if (-sort(i) == sort(left) + sort(right)) {
            res += List(sort(i), sort(left), sort(right))
            var loop = true
            left += 1
            right -= 1
            while (loop && left < right) {
              val lLoop = if (sort(left) == sort(left - 1)) {
                left += 1
                true
              } else false
              val rLoop = if (sort(right) == sort(right + 1)) {
                right -= 1
                true
              } else false
              loop = rLoop || lLoop
            }
            //sort(i) 一定小于= 0，此时说明left + right是一个更小的
          } else if (-sort(i) > sort(left) + sort(right)) {
            left += 1
            //同理需要更小的数
          } else right -= 1
        }
      }
    }
    res.toList
  }

  def main(args: Array[String]): Unit = {
    //println(Array(-1, 0, 1, 2, -1, -4).sorted.toList)
    println(threeSum(Array(1,-1,-1,0)))
  }
}
