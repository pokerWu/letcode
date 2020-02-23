package xyz.panwu.study.letcode

import java.util

import scala.collection.mutable


/**
 * 220. 存在重复元素 III
 *
 * 给定一个整数数组，判断数组中是否有两个不同的索引 i 和 j，使得 nums [i] 和 nums [j] 的差的绝对值最大为 t，并且 i 和 j 之间的差的绝对值最大为 ķ。
 *
 * 示例 1:
 *
 * 输入: nums = [1,2,3,1], k = 3, t = 0
 * 输出: true
 * 示例 2:
 *
 * 输入: nums = [1,0,1,1], k = 1, t = 2
 * 输出: true
 * 示例 3:
 *
 * 输入: nums = [1,5,9,1,5,9], k = 2, t = 3
 * 输出: false
 *
 * 链接：https://leetcode-cn.com/problems/contains-duplicate-iii
 *
 */
object T220_containsNearbyAlmostDuplicate {

  //1. 暴力解题，分为不同的逐个比较，包 k这个窗口内的数据都遍历一遍
  //2。窗口，用一个有序的窗口，可以不必将k内的数据都遍历一遍，只需要用大于当前的最小值与小于当前的最大值即可
  def containsNearbyAlmostDuplicate(nums: Array[Int], k: Int, t: Int): Boolean = {
    val bTree = new util.TreeSet[Int]()
    for (i <- nums.indices) {
      val e = nums(i)
      val greater = Option(bTree.ceiling(e))
      if (greater.isDefined && greater.get <= e + t) return true

      val less = Option(bTree.floor(e))
      if (less.isDefined && e <= t + less.get) return true

      bTree.add(e)
      if (bTree.size() > k) bTree.remove(nums(i - k))
    }
    false
  }

  def bucketSearch(nums: Array[Int], k: Int, t: Int): Boolean = {
    if (t < 0) return false
    //bucketId -> one bucket value, 仅需要一个值就够了，因为如果出现两个，答案就出来了
    //这个map的size为 k,表示一个window
    val bucketValue = mutable.HashMap[Int, Int]()
    //桶长度
    //例如 t = 2 bucket:(0, 1, 2) 里面的任意两个都是满足t
    val bucketLen = t + 1
    // 计算bucket id, -1 算在 -1这个bucket
    val getBucketId = (e: Int) => if (e < 0) e / bucketLen - 1 else e / bucketLen
    nums.indices.foreach(i => {
      val bucketId = getBucketId(nums(i))
      //同一个桶，那么差值一定 <= t
      if (bucketValue.isDefinedAt(bucketId)) return true
      //查看上一个桶是否满足，这里需要注意，一个桶必然最多有一个值，不能覆盖，因为一旦出现第二个就满足上一个条件了
      if (bucketValue.isDefinedAt(bucketId - 1) && Math.abs(nums(i) - bucketValue(bucketId - 1)) < bucketLen) return true
      //同理检查右边桶
      if (bucketValue.isDefinedAt(bucketId + 1) && Math.abs(bucketValue(bucketId + 1) - nums(i)) < bucketLen) return true

      bucketValue += (bucketId -> nums(i))
      //保证窗口长度为 k
      if (bucketValue.size > k) bucketValue.remove(getBucketId(nums(i - k)))
    })

    false
  }

  def main(args: Array[String]): Unit = {
    println(containsNearbyAlmostDuplicate(Array(1,2,3,1), 3, 0))
    println(containsNearbyAlmostDuplicate(Array(1,0,1,1), 1, 2))
    println(containsNearbyAlmostDuplicate(Array(1,5,9,1,5,9), 2, 3))
    println(bucketSearch(Array(1,2,3,1), 3, 0))
    println(bucketSearch(Array(1,0,1,1), 1, 2))
    println(bucketSearch(Array(1,5,9,1,5,9), 2, 3))
  }
}
