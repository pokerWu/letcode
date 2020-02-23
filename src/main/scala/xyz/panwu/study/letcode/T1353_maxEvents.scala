package xyz.panwu.study.letcode

import scala.collection.mutable
import scala.util.control.Breaks._

/**
 * 最多可以参加的会议数目
 *
 * 给你一个数组 events，其中 events[i] = [startDayi, endDayi] ，表示会议 i 开始于 startDayi ，结束于 endDayi 。
 *
 * 你可以在满足 startDayi <= d <= endDayi 中的任意一天 d 参加会议 i 。注意，一天只能参加一个会议。
 *
 * 请你返回你可以参加的 最大 会议数目。
 *
 * 示例 1：
 *
 * 输入：events = [[1,2],[2,3],[3,4]]
 * 输出：3
 * 解释：你可以参加所有的三个会议。
 * 安排会议的一种方案如上图。
 * 第 1 天参加第一个会议。
 * 第 2 天参加第二个会议。
 * 第 3 天参加第三个会议。
 * 示例 2：
 *
 * 输入：events= [[1,2],[2,3],[3,4],[1,2]]
 * 输出：4
 * 示例 3：
 *
 * 输入：events = [[1,4],[4,4],[2,2],[3,4],[1,1]]
 * 输出：4
 * 示例 4：
 *
 * 输入：events = [[1,100000]]
 * 输出：1
 * 示例 5：
 *
 * 输入：events = [[1,1],[1,2],[1,3],[1,4],[1,5],[1,6],[1,7]]
 * 输出：7
 *  
 *
 * 提示：
 *
 * 1 <= events.length <= 10^5
 * events[i].length == 2
 * 1 <= events[i][0] <= events[i][1] <= 10^5
 *
 * 链接：https://leetcode-cn.com/problems/maximum-number-of-events-that-can-be-attended
 */
object T1353_maxEvents {

  def maxEvents(events: Array[Array[Int]]): Int = {
    val visit = mutable.Set[Int]()
    //排序的作用是：会议结束越早的其它可选择的会议就越多
    val sort = events.sortBy(f => f(1))
    var res = 0
    for (e <- sort) {
      breakable(
        //重点在这个地方，也就是说在 start-end这段时间内可以任意一天参加
        //参加了就标记一下，如果后面还有会议在当前的start-end里面，就可以离开参加下一场
        for (day <- e(0) to e(1)) {
          if (!visit.contains(day)) {
            res += 1
            visit += day
            break
          }
        }
      )
    }
    res
  }

  def main(args: Array[String]): Unit = {
    println(maxEvents(Array(Array(1, 2), Array(2, 3), Array(3, 4), Array(1, 2))))

    println(maxEvents(Array(Array(1, 4), Array(4, 4), Array(2, 2), Array(3, 4), Array(1, 1))))
  }
}
