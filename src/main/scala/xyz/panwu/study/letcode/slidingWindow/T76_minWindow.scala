package xyz.panwu.study.letcode.slidingWindow

import scala.collection.mutable

/**
 * 76. 最小覆盖子串
 *
 * 给你一个字符串 S、一个字符串 T，请在字符串 S 里面找出：包含 T 所有字母的最小子串。
 *
 * 示例：
 *
 * 输入: S = "ADOBECODEBANC", T = "ABC"
 * 输出: "BANC"
 * 说明：
 *
 * 如果 S 中不存这样的子串，则返回空字符串 ""。
 * 如果 S 中存在这样的子串，我们保证它是唯一的答案。
 *
 * 链接：https://leetcode-cn.com/problems/minimum-window-substring
 */
object T76_minWindow {

  /**
   * 动态窗口
   * A D O B E C O D E B A N C   "ABC"
   *
   * A                   window: A: 1              还需 2
   * A D                 window: A: 1              还需 2
   * A D O               window: A: 1              还需 2
   * A D O B             window: A: 1, B: 1        还需 1
   * A D O B E           window: A: 1, B: 1        还需 2
   * A D O B E C         window: A: 1, B: 1, C: 1  满足
   * 匹配所需字符了，开始缩小 移除最左边 A , 此时window： B: 1, C: 1， 不满足了，记录当前满足的位置
   * D O B E C O         window: B: 1, C: 1        还需 1
   * D O B E C O D       window: B: 1, C: 1        还需 1
   * D O B E C O D E     window: B: 1, C: 1        还需 1
   * D O B E C O D E B   window: B: 2, C: 1        还需 1
   * D O B E C O D E B A window: A:1 B: 2, C: 1    满足
   * 匹配所需字符了，开始缩小 移除最左边 D , 此时window： A:1 B: 2, C: 1
   * 同理直接移除左边，
   * C O D E B A         window: A:1 B: 1, C: 1    满足
   * 直到影响window不满足 need停止，比较是不是最小举例，是就记录，不是继续滑动
   * O D E B A N         window: A:1 B: 1          还需 1
   * O D E B A N C       window: A:1 B: 1, C: 1    满足
   * 匹配所需字符了，开始缩小 移除最左边 O , 此时window： A:1 B: 1, C: 1
   * 同理直接移除左边，
   * B A N C            window: A:1 B: 1, C: 1     满足， 比较是不是最小
   * 结束
   * @param s
   * @param t
   * @return
   */
  def minWindow(s: String, t: String): String = {
    if (s == null || s.length() == 0 || t == null || t.length == 0) return ""

    val need = new mutable.HashMap[Char, Int]()
    val window = new mutable.HashMap[Char, Int]()
    t.foreach(c => need += (c -> (need.getOrElse(c, 0) + 1)))
    var left = 0
    var minLeft = 0
    var minRight = Int.MaxValue - 1
    //flag, 标记当前窗口内所需字符是否都满足了
    var total = t.length

    s.zipWithIndex.foreach(p => {
      if (need.contains(p._1)) {
        //在window记录已经出现的所需字符的个数
        window += (p._1 -> (window.getOrElse(p._1, 0) + 1))
        //判断是否达到了所需个数，如果没达到，匹配到的所需总量就要增加，还需就减少
        //如果已经超过所需了，不处理
        if (window(p._1) <= need(p._1)) total -= 1
      }

      if (total == 0) {
        //缩小窗口找到最小满足的窗口
        while (total == 0) {
          val lChar = s.charAt(left)
          if (need.contains(lChar)) {
            if (window(lChar) <= need(lChar))
              total += 1
            window += (lChar -> (window(lChar) - 1))
          }
          left += 1
        }
        if (p._2 - (left - 1) + 1 < minRight - minLeft + 1) {
          minLeft = left - 1
          minRight = p._2
        }
      }
    })

    if (minRight - minLeft + 1 == Int.MaxValue) "" else s.substring(minLeft, minRight + 1)
  }

  def main(args: Array[String]): Unit = {
    println(minWindow("ADOBECODEBANC", "ABC"))
  }
}
