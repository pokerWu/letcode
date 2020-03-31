package xyz.panwu.study.letcode.hot100

import scala.collection.mutable

/**
 * 621. 任务调度器
 *
 * 给定一个用字符数组表示的 CPU 需要执行的任务列表。其中包含使用大写的 A - Z 字母表示的26 种不同种类的任务。任务可以以任意顺序执行，并且每个任务都可以在 1 个单位时间内执行完。CPU 在任何一个单位时间内都可以执行一个任务，或者在待命状态。
 *
 * 然而，两个相同种类的任务之间必须有长度为 n 的冷却时间，因此至少有连续 n 个单位时间内 CPU 在执行不同的任务，或者在待命状态。
 *
 * 你需要计算完成所有任务所需要的最短时间。
 *
 *  
 *
 * 示例 ：
 *
 * 输入：tasks = ["A","A","A","B","B","B"], n = 2
 * 输出：8
 * 解释：A -> B -> (待命) -> A -> B -> (待命) -> A -> B.
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/task-scheduler
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
object T621_leastInterval {


  /**
   *
   * A A A A B B B C C D E  n = 3
   *
   * maxTaskTypeCount = 4
   *
   * bucket = 4
   *
   *  A | B | C | D |
   *  A | B | C | E |
   *  A | B | - | - |
   *  A |
   *  //最后一个bucket是可能放不满的
   *  bucket * (maxTaskTypeCount - 1) + 1 = 13
   *
   * -----------------------
   * A A A B B B C C D E  n = 4
   *
   * bucket = 5
   *
   * A | B | C | D | E |
   * A | B | C | - | — ｜
   * A | B |
   *
   * @param tasks
   * @param n
   * @return
   */

  def leastInterval(tasks: Array[Char], n: Int): Int = {
    if (n == 0) return tasks.length
    val taskMap = new mutable.HashMap[Char, Int]()
    var maxTaskCount = 0
    tasks.foreach(t => {
      taskMap += (t -> (taskMap.getOrElse(t, 0) + 1))
      maxTaskCount = Math.max(maxTaskCount, taskMap(t))
    })

    val lastBucket = taskMap.values.count(_ == maxTaskCount)

    val minTime = (n + 1) * (maxTaskCount - 1) + lastBucket

    Math.max(minTime, tasks.length)
  }

  def main(args: Array[String]): Unit = {
    println(leastInterval(Array('A','A','A','B','B','B'), 2))
  }
}
