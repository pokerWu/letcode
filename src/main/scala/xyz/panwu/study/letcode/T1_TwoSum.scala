package xyz.panwu.study.letcode

import scala.collection.mutable

/**
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 *
 * 示例:
 *
 * 给定 nums = [2, 7, 11, 15], target = 9
 *
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 *  https://leetcode-cn.com/problems/two-sum/solution/liang-shu-zhi-he-by-leetcode-2/
 */
object T1_TwoSum {

  def twoSum(nums: Array[Int], target: Int): Array[Int] = {
    for (i <- 0 until nums.length) {
      for (
        j <- i + 1 until nums.length
        if nums(i) + nums(j) == target
      ) {
        return Array(i, j)
      }
    }
    null
  }

  def bestSolution(nums: Array[Int], target: Int): Array[Int] = {
    val map = mutable.Map[Int, Int]()
    nums.zipWithIndex.foreach(pair => {
      map.get(target - pair._1).foreach(j => return Array(j, pair._2))
      map += (pair)
    })
    null
  }

  def main(args: Array[String]): Unit = {
    val res = twoSum(Array(2, 7, 11, 15), 9)
    println(s"position ${res(0)}, ${res(1)}")

    val res2 = bestSolution(Array(2, 7, 11, 15), 9)
    println(s"position ${res2(0)}, ${res2(1)}")
  }
}
