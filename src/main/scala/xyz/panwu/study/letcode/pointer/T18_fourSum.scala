package xyz.panwu.study.letcode.pointer

import scala.collection.mutable
import util.control.Breaks._

/**
 * 18. 四数之和
 *
 * 给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d ，使得 a + b + c + d 的值与 target 相等？
 * 找出所有满足条件且不重复的四元组。
 *
 * 注意：
 *
 * 答案中不可以包含重复的四元组。
 *
 * 示例：
 *
 * 给定数组 nums = [1, 0, -1, 0, -2, 2]，和 target = 0。
 *
 * 满足要求的四元组集合为：
 * [
 * [-1,  0, 0, 1],
 * [-2, -1, 1, 2],
 * [-2,  0, 0, 2]
 * ]
 *
 * 链接：https://leetcode-cn.com/problems/4sum
 */
object T18_fourSum {

  /**
   * 在three sum的外层再套一层循环
   *
   * @param nums
   * @param target
   * @return
   */
  def fourSum(nums: Array[Int], target: Int): List[List[Int]] = {
    if (nums == null || nums.length < 4) return List.empty

    val size = nums.length
    //排序，排序的目的是介绍遍历次数。因为到某些情况下就不需要继续了。不排序就要全部遍历
    val sortNums = nums.sorted
    val res = new mutable.ListBuffer[List[Int]]
    //第一层 i
    for (i <- 0 until (size - 3)) {
      breakable {
        //当前 + 最左三个已经超过 target了，就没必要进行了
        val min = sortNums(i) + sortNums(i + 1) + sortNums(i + 2) + sortNums(i + 3)
        if (min > target) break
        //当前 + 最右边三个还不足target了，也没必要进行了
        val max = sortNums(i) + sortNums(size - 1) + sortNums(size - 2) + sortNums(size - 3)
        if (max < target) break
        breakable {
          //与上一个一样，跳过。避免重复结果
          if (i > 0 && sortNums(i) == sortNums(i - 1)) break
          //第二层 k
          for (k <- i + 1 until (size - 2)) {
            breakable {
              val min = sortNums(i) + sortNums(k) + sortNums(k + 1) + sortNums(i + 2)
              if (min > target) break
              //当前 + 最右边三个还不足target了，也没必要进行了
              val max = sortNums(i) + sortNums(k) + sortNums(size - 1) + sortNums(size - 2)
              if (max < target) break
              //与上一个一样，skip
              if (k > i + 1 && sortNums(k) == sortNums(k - 1)) break
              //left pointer, right pointer
              var left = k + 1
              var right = size - 1
              while (left < right) {
                if (sortNums(i) + sortNums(k) + sortNums(left) + sortNums(right) == target) {
                  res += List(sortNums(i), sortNums(k), sortNums(left), sortNums(right))
                  left += 1
                  right -= 1
                  var loop = true
                  //跳过左右指针下一样的数据，避免重复结果
                  while (loop && left < right) {
                    val ll = if (sortNums(left) == sortNums(left - 1)) {
                      left += 1
                      true
                    } else false
                    val rl = if (sortNums(right) == sortNums(right + 1)) {
                      right -= 1
                      true
                    } else false
                    loop = ll || rl
                  }
                } else if (sortNums(i) + sortNums(k) + sortNums(left) + sortNums(right) < target)
                  left += 1
                else right -= 1
              }
            }
          }
        }
      }
    }
    res.toList
  }


  def main(args: Array[String]): Unit = {
    fourSum(Array(-3, -2, -1, 0, 0, 1, 2, 3), 0).map(list => list.mkString(",")).foreach(println)
  }
}
