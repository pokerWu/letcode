package xyz.panwu.study.letcode.greedy

import java.util

import scala.util.control.Breaks._

/**
 * 1094. 拼车
 *
 * 假设你是一位顺风车司机，车上最初有 capacity 个空座位可以用来载客。由于道路的限制，车 只能 向一个方向行驶（也就是说，不允许掉头或改变方向，你可以将其想象为一个向量）。
 *
 * 这儿有一份行程计划表 trips[][]，其中 trips[i] = [num_passengers, start_location, end_location] 包含了你的第 i 次行程信息：
 *
 * 必须接送的乘客数量；
 * 乘客的上车地点；
 * 以及乘客的下车地点。
 * 这些给出的地点位置是从你的 初始 出发位置向前行驶到这些地点所需的距离（它们一定在你的行驶方向上）。
 *
 * 请你根据给出的行程计划表和车子的座位数，来判断你的车是否可以顺利完成接送所用乘客的任务（当且仅当你可以在所有给定的行程中接送所有乘客时，返回 true，否则请返回 false）。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：trips = [[2,1,5],[3,3,7]], capacity = 4
 * 输出：false
 * 示例 2：
 *
 * 输入：trips = [[2,1,5],[3,3,7]], capacity = 5
 * 输出：true
 * 示例 3：
 *
 * 输入：trips = [[2,1,5],[3,5,7]], capacity = 3
 * 输出：true
 * 示例 4：
 *
 * 输入：trips = [[3,2,7],[3,7,9],[8,3,9]], capacity = 11
 * 输出：true
 *
 * 链接：https://leetcode-cn.com/problems/car-pooling
 */
object T1094_carPooling {

  def carPooling(trips: Array[Array[Int]], capacity: Int): Boolean = {
    if (trips == null || trips.isEmpty) return true
    if (capacity <= 0) return false
    val sortTrips = trips.sortBy(e => e(1))
    val offLink = new util.LinkedList[Array[Int]]()
    var remain = capacity
    for (e <- sortTrips) {
      //先把已经下车的捏走，哈哈哈
      var head = offLink.peek()
      while (head != null && head(2) <= e(1)) {
        remain += head(0)
        offLink.pop()
        head = offLink.peek()
      }
      //还有剩余
      if (remain >= e(0)) {
        //安排一下，按下车地排序，先下的放前面
        breakable {
          for (i <- 0 until offLink.size()) {
            val item = offLink.get(i)
            if (e(2) <= item(2)) {
              offLink.add(i, e)
              remain -= e(0)
              break()
            }
          }
          //没有乘车或者是最远的，就放在链表尾巴上
          offLink.add(offLink.size(), e)
          remain -= e(0)
        }
      } else return false
    }
    true
  }

  def main(args: Array[String]): Unit = {
    println(carPooling(Array(Array(3, 2, 7), Array(3, 7, 9), Array(8, 3, 9)), 11))
  }

}
