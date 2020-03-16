package xyz.panwu.study.letcode.greedy

import scala.collection.mutable

/**
 * 1338. 数组大小减半
 *
 * 给你一个整数数组 arr。你可以从中选出一个整数集合，并删除这些整数在数组中的每次出现。
 *
 * 返回 至少 能删除数组中的一半整数的整数集合的最小大小。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：arr = [3,3,3,3,5,5,5,2,2,7]
 * 输出：2
 * 解释：选择 {3,7} 使得结果数组为 [5,5,5,2,2]、长度为 5（原数组长度的一半）。
 * 大小为 2 的可行集合有 {3,5},{3,2},{5,2}。
 * 选择 {2,7} 是不可行的，它的结果数组为 [3,3,3,3,5,5,5]，新数组长度大于原数组的二分之一。
 * 示例 2：
 *
 * 输入：arr = [7,7,7,7,7,7]
 * 输出：1
 * 解释：我们只能选择集合 {7}，结果数组为空。
 * 示例 3：
 *
 * 输入：arr = [1,9]
 * 输出：1
 * 示例 4：
 *
 * 输入：arr = [1000,1000,3,7]
 * 输出：1
 * 示例 5：
 *
 * 输入：arr = [1,2,3,4,5,6,7,8,9,10]
 * 输出：5
 *
 * 链接：https://leetcode-cn.com/problems/reduce-array-size-to-the-half
 */
object T1338_minSetSize {
  def minSetSize(arr: Array[Int]): Int = {
    if (arr == null) return 0
    val len = arr.length
    val center = len / 2
    val odd = len % 2 == 1
    val countMap = mutable.Map[Int, Int]()
    var left = 0
    var right = 0

    if (odd) {
      countMap += (center -> 1)
      left = center - 1
      right = center + 1
    } else {
      left = center - 1
      right = center
    }
    //优化一下，双向遍历介绍循环次数
    while (right < len) {
      countMap.put(arr(left), countMap.getOrElse(arr(left), 0) + 1)
      countMap.put(arr(right), countMap.getOrElse(arr(right), 0) + 1)
      right += 1
      left -= 1
    }

    //降序
    val sort = countMap.values.toList.sortWith((i, j) => i > j)
    var count = 0
    //次数越多，就满足最少条件
    for (i <- 0 until (center + 1)) {
        count += sort(i)
      if (odd && count >= center + 1) return i + 1
      if (!odd && count >= center) return i + 1
    }
    0
  }

  def main(args: Array[String]): Unit = {
   // println(minSetSize(Array(3, 3, 3, 3, 5, 5, 5, 2, 2, 7)))
    println(minSetSize(Array(1,9)))
  }
}
