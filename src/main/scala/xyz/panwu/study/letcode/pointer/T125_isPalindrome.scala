package xyz.panwu.study.letcode.pointer

import scala.util.control.Breaks

/**
 * 125. 验证回文串
 *
 * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
 *
 * 说明：本题中，我们将空字符串定义为有效的回文串。
 *
 * 示例 1:
 *
 * 输入: "A man, a plan, a canal: Panama"
 * 输出: true
 * 示例 2:
 *
 * 输入: "race a car"
 * 输出: false
 *
 * 链接：https://leetcode-cn.com/problems/valid-palindrome
 */
object T125_isPalindrome {
  def isPalindrome(s: String): Boolean = {
    if (s == null || s.length < 1) return true
    var left = 0
    var right = s.length - 1

    val getChar = (c: Char) => c match {
      case e if (('0' <= e && e <= '9') || ('a' <= e && e <= 'z')) => Some(e)
      case e if (e >= 'A' && e <= 'Z') => Some(e + 32)
      case _ => None
    }

    while (left < right) {
      val lc = getChar(s.charAt(left))
      val rc = getChar(s.charAt(right))

      if (lc.isDefined && rc.isDefined) {
        if (lc.get == rc.get) {
          left += 1
          right -= 1
        } else return false
      }

      if (lc.isEmpty) left += 1
      if (rc.isEmpty) right -= 1
    }
    true
  }

  def main(args: Array[String]): Unit = {
    println('A' - 'a')
    println(isPalindrome("A man, a plan, a canal: Panama"))
  }
}
