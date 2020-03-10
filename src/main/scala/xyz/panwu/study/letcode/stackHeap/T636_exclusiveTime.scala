package xyz.panwu.study.letcode.stackHeap

import scala.collection.mutable
import scala.collection.mutable.Stack

/**
 * 636. 函数的独占时间
 *
 * 给出一个非抢占单线程CPU的 n 个函数运行日志，找到函数的独占时间。
 *
 * 每个函数都有一个唯一的 Id，从 0 到 n-1，函数可能会递归调用或者被其他函数调用。
 *
 * 日志是具有以下格式的字符串：function_id：start_or_end：timestamp。例如："0:start:0" 表示函数 0 从 0 时刻开始运行。"0:end:0" 表示函数 0 在 0 时刻结束。
 *
 * 函数的独占时间定义是在该方法中花费的时间，调用其他函数花费的时间不算该函数的独占时间。你需要根据函数的 Id 有序地返回每个函数的独占时间。
 *
 * 示例 1:
 *
 * 输入:
 * n = 2
 * logs =
 * ["0:start:0",
 * "1:start:2",
 * "1:end:5",
 * "0:end:6"]
 * 输出:[3, 4]
 * 说明：
 * 函数 0 在时刻 0 开始，在执行了  2个时间单位结束于时刻 1。
 * 现在函数 0 调用函数 1，函数 1 在时刻 2 开始，执行 4 个时间单位后结束于时刻 5。
 * 函数 0 再次在时刻 6 开始执行，并在时刻 6 结束运行，从而执行了 1 个时间单位。
 * 所以函数 0 总共的执行了 2 +1 =3 个时间单位，函数 1 总共执行了 4 个时间单位。
 * 说明：
 *
 * 输入的日志会根据时间戳排序，而不是根据日志Id排序。
 * 你的输出会根据函数Id排序，也就意味着你的输出数组中序号为 0 的元素相当于函数 0 的执行时间。
 * 两个函数不会在同时开始或结束。
 * 函数允许被递归调用，直到运行结束。
 * 1 <= n <= 100
 *
 * 链接：https://leetcode-cn.com/problems/exclusive-time-of-functions
 */
object T636_exclusiveTime {

  /**
   * 方法一：栈
   * 我们可以使用栈来模拟函数的调用，即在遇到一条包含 start 的日志时，我们将对应的函数 id 入栈；
   * 在遇到一条包含 end 的日志时，我们将对应的函数 id 出栈
   * 。在每一个时刻，栈中的所有函数均为被调用的函数，而栈顶的函数为正在执行的函数。
   *
   * 在每条日志的时间戳后，栈顶的函数会独占执行，直到下一条日志的时间戳，因此我们可以根据相邻两条日志的时间戳差值，来计算函数的独占时间。
   * 我们依次遍历所有的日志，对于第 i 条日志，如果它包含 start，那么栈顶函数从其时间戳 time[i] 开始运行，即 prev = time[i]；
   * 如果它包含 end，那么栈顶函数从其时间戳 time[i] 的下一个时间开始运行，即 prev = time[i] + 1。
   * 对于第 i + 1 条日志，如果它包含 start，那么在时间戳 time[i + 1] 时，有新的函数被调用，因此原来的栈顶函数的独占时间为 time[i + 1] - prev；
   * 如果它包含 end，那么在时间戳 time[i + 1] 时，原来的栈顶函数执行结束，独占时间为 time[i + 1] - prev + 1。在这之后，我们更新 prev 并遍历第 i + 2 条日志。
   * 在遍历结束后，我们就可以得到所有函数的独占时间。
   *
   *
   * @param n
   * @param logs
   * @return
   */
  def exclusiveTime(n: Int, logs: List[String]): Array[Int] = {
    if (logs == null || logs.isEmpty) return Array.empty
    val res = new Array[Int](n)
    val stack = mutable.Stack[Int]()
    var pre = 0
    logs.foreach(log => {
      val splitLog = log.split(":")
      val tid = splitLog(0).toInt
      val time = splitLog(2).toInt
      if ("start".equals(splitLog(1))) {
        if (stack.nonEmpty){
          val top = stack.top
          res(top) += time - pre
        }
        stack.push(tid)
        pre = time
      } else {
        val endLog = stack.pop()
        res(endLog) += time - pre + 1
        pre = time + 1
      }
    })
    res
  }

  def main(args: Array[String]): Unit = {
    exclusiveTime(2, List("0:start:0", "1:start:2", "1:end:5", "0:end:6")).foreach(println)
  }


}
