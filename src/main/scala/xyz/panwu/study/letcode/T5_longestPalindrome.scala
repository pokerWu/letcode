package xyz.panwu.study.letcode

/**
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 *
 * 示例 1：
 *
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba" 也是一个有效答案。
 * 示例 2：
 *
 * 输入: "cbbd"
 * 输出: "bb"
 *
 * "abccba"
 *
 * 链接：https://leetcode-cn.com/problems/longest-palindromic-substring
 */
object T5_longestPalindrome {

  /**
   * 中心拓展法
   *
   * a     b     c     c     b     a
   * C  C  C  C  C  C  C  C  C  C  C
   *
   * n + (n-1) 个中心点
   *
   * @param s
   * @return
   */
  def longestPalindrome(s: String): String = {
    if (s == null || s.length < 2) return s

    def explodeCenter(left: Int, right: Int): Int = {
      var l = left
      var r = right
      while (l >= 0 && r < s.length && s.charAt(l) == s.charAt(r)) {
        l -= 1
        r += 1
      }
      r - l - 1
    }

    var startPos = 0
    var endPos = 0

    for (i <- 0 until s.length) {
      val len1 = explodeCenter(i, i)
      val len2 = explodeCenter(i, i + 1)
      val len = Math.max(len1, len2)
      if (len > endPos - startPos) {
        startPos = i - (len - 1) / 2
        endPos = i + len / 2
      }
    }
    s.substring(startPos, endPos + 1)
  }

  //---------------

  def preProcess(s: String): String = {
    val n = s.length()
    if (n == 0) {
      return "^$"
    }
    var ret = "^"
    for (i <- 0 until (n))
      ret = ret + "#" + s.charAt(i);
    ret += "#$"
    ret
  }

  // 马拉车算法
  // 详细介绍： https://blog.crimx.com/2017/07/06/manachers-algorithm/
  def manacher(s: String): String = {
    var newStr = preProcess(s)
    var center = 0
    var right = 0
    val p = new Array[Int](newStr.length)

    for (i <- 1 until (newStr.length - 1)) {
      if (i < right) {
        val iMirror = 2 * center - i
        p(i) = Math.min(p(iMirror), right - i)
      } else {
        p(i) = 0
      }
      while (newStr.charAt(i - 1 - p(i)) == newStr.charAt(i + 1 + p(i))) {
        p(i) += 1
      }
      if (i + p(i) > right) {
        center = i
        right = i + p(i)
      }
      //}
    }

    var maxlen = 0
    var index = 0
    for (i <- 1 until (newStr.length - 1)) {
      if (p(i) > maxlen) {
        maxlen = p(i)
        index = i
      }
    }
    val start = (index - maxlen) / 2
    s.substring(start, start + maxlen)
  }

  def main(args: Array[String]): Unit = {
    val res1 = longestPalindrome("bacab")
    println(res1)

    val res2 = manacher("ccc")
    println(res2)
  }
}
