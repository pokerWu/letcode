package xyz.panwu.study.letcode.slidingWindow

import scala.collection.mutable

/**
 * 438. 找到字符串中所有字母异位词
 *
 * 给定一个字符串 s 和一个非空字符串 p，找到 s 中所有是 p 的字母异位词的子串，返回这些子串的起始索引。
 *
 * 字符串只包含小写英文字母，并且字符串 s 和 p 的长度都不超过 20100。
 *
 * 说明：
 *
 * 字母异位词指字母相同，但排列不同的字符串。
 * 不考虑答案输出的顺序。
 * 示例 1:
 *
 * 输入:
 * s: "cbaebabacd" p: "abc"
 *
 * 输出:
 * [0, 6]
 *
 * 解释:
 * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的字母异位词。
 * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的字母异位词。
 *  示例 2:
 *
 * 输入:
 * s: "abab" p: "ab"
 *
 * 输出:
 * [0, 1, 2]
 *
 * 解释:
 * 起始索引等于 0 的子串是 "ab", 它是 "ab" 的字母异位词。
 * 起始索引等于 1 的子串是 "ba", 它是 "ab" 的字母异位词。
 * 起始索引等于 2 的子串是 "ab", 它是 "ab" 的字母异位词。
 *
 */
object T438_findAnagrams {

  /**
   *  b a a c b a b c      "abc"
   *
   *  b                    window b: 1         还需 2
   *  b a                  window b: 1, a : 1  还需 1
   *  b a a                window b: 1, a: 2   还需 1
   *  长度达到window len， left 移动   window: a: 2  还需2
   *  a a c                window: a: 2, c: 1  还需 1
   *  长度达到window len， left 移动   window: a: 1, c: 1 还需1
   *  a c b                window: a: 1, c: 1, b: 1 还需 0 ， 加入结果
   *  长度达到window len, left 移动   window: c: 1, b: 1  还需 1
   *  c b a                window: c: 1, b: 1, a: 1  还需 0 ， 加入结果
   *  长度达到window len, left 移动   window: b: 1, a: 1 还需 1
   *  b a b                window: b: 2, a: 1  还需 1
   *  长度达到window len, left 移动  window: a: 1, b:1  还需 1
   *  a b c                window: a: 1, b: 1, c: 1  还需 0, 加入结果
   *
   * @param s
   * @param p
   * @return
   */
  def findAnagrams(s: String, p: String): List[Int] = {
    if (s == null || s.length() == 0) return List[Int]()
    // 记录p需要的各个字符的次数
    val need = Array.fill[Int](26)(0)
    //记录s 中，以p.length为窗口的字符串的情况
    var window = Array.fill[Int](26)(0)
    //初始化need
    p.foreach(c => need(c - 'a') += 1)

    // total这个flag非常重要，标记了当前window是否满足条件
    var total = p.length

    var left = 0
    var right = 0
    val res = mutable.ListBuffer[Int]()

    while (right < s.length) {
      val rightPosition = s.charAt(right) - 'a'
      val needNum = need(rightPosition)
      //是需要的字符的时候先加入窗口统计
      if (needNum > 0) {
        window(rightPosition) += 1
        //当满足小于需求就减少总计，这里不要怕有重复之类的，我们后面会修正
        if (window(rightPosition) <= needNum) {
          total -= 1
        }
      }
      //达到窗口大小了，需要对窗口内的统计进行判断或者修正了
      if (right - left + 1 == p.length) {
        //当前窗口满足了
        if (total == 0) {
          res += left
        }
        //左移窗口
        val leftPosition = s.charAt(left) - 'a'
        if (need(leftPosition) > 0) {
          //这里要判断左移后时候影响了total的统计，影响了就需要加回来。
          if (window(leftPosition) <= need(leftPosition))
            total += 1
          window(leftPosition) -= 1
        }
        left += 1
      }

      right += 1
    }
    res.toList
  }

  def main(args: Array[String]): Unit = {
    findAnagrams("cbaebabacd", "abc").foreach(print)
  }
}
