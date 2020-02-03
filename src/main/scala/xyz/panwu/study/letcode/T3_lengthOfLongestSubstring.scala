package xyz.panwu.study.letcode

import scala.collection.mutable
import scala.util.control.Breaks._

/**
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * 示例 1:
 *
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 *
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 *
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *
 * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
 */
object T3_lengthOfLongestSubstring {

  def lengthOfLongestSubstring(s: String): Int = {
    val set = mutable.Set[Char]()
    var maxSize = 0
    for (i <- 0 until s.length) {
      breakable {
        for (j <- i until s.length) {
          if (set.contains(s.charAt(j))) {
            maxSize = Math.max(set.size, maxSize)
            set.clear()
            break
          } else
            set.add(s.charAt(j))
        }
      }
      if (set.nonEmpty) maxSize = Math.max(maxSize, set.size)
    }
    maxSize
  }

  /**
   *  ----
   * | a | b, c, a, b, c
   *  ----
   *
   * --------
   * | a, b | c, a, b, c
   * --------
   * ...
   *     --------
   * a, | b, c, a| b, c
   *     --------
   *     出现一次重复就记录一下当前的长度
   *     最后取最长的值
   * @param s
   * @return
   */
  def bestSolution(s: String): Int = {
    var window = mutable.ListBuffer[Char]()
    var lenSet = mutable.Set[Int]()
    s.foreach(c => {
      if (window.contains(c)) {
        lenSet.add(window.size)
        window.remove(0, window.indexOf(c) + 1)
      }
      window.append(c)
    })
    lenSet.add(window.size)

    lenSet.max
  }

  /**
   * 思路同上一个解题思路
   * 但是这里不单独用集合存储数据了
   * 用指针来表示窗口的开始与结束
   * 利用 string charAt(char, startIndex) 来替代额外的窗口中查找
   * @param s
   * @return
   */
  def bestSolution2(s: String): Int = {
    var windowEnd = 0
    var windowStart = 0
    var len = 0
    var max = 0
    while (windowEnd < s.length) {
      val pos = s.indexOf(s.charAt(windowEnd), windowStart)
      if (pos < windowEnd) {
        max = Math.max(max, len)
        len = windowEnd - pos - 1
        windowStart = pos + 1
      }
      windowEnd += 1
      len += 1
    }
    Math.max(max, len)
  }

  def main(args: Array[String]): Unit = {
    var len = lengthOfLongestSubstring("abcabc")
    println(len)
    len = bestSolution("abcabc")
    println(len)
  }

}
