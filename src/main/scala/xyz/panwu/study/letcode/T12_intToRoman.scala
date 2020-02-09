package xyz.panwu.study.letcode

/**
 *
 * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
 *
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 *
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。
 * 数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。
 * 这个特殊的规则只适用于以下六种情况：
 *
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
 *
 * 示例 1:
 *
 * 输入: 3
 * 输出: "III"
 * 示例 2:
 *
 * 输入: 4
 * 输出: "IV"
 * 示例 3:
 *
 * 输入: 9
 * 输出: "IX"
 * 示例 4:
 *
 * 输入: 58
 * 输出: "LVIII"
 * 解释: L = 50, V = 5, III = 3.
 * 示例 5:
 *
 * 输入: 1994
 * 输出: "MCMXCIV"
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 *
 * 链接：https://leetcode-cn.com/problems/integer-to-roman
 */
object T12_intToRoman {

  /**
   *
   * 贪心算法
   *
   * 观察 发现 （1，500）， (3，50)  (5, 5) 三个位置需要特殊处理
   *
   * 这个三个位置i 需要  (i-1) - (i+1) 组成新的单位 例如 500 ： 900
   * 与 (i) - (i+1) 组成新单位 例如 500：400 得到两个新的单位， 900， 400
   *
   * 900这个单位需要确保  num / 900 = 1 表示  900 =< num < 1000 (必然小于上一个单位)
   * 400这个单位需要确保 num / 400 = 1 && num < 500  表示   400=< num < 500 需要确保小于单位
   *
   * @param num
   * @return
   */
  def intToRoman(num: Int): String = {
    val unit = Array(1000, 500, 100, 50, 10, 5, 1)
    val symbol = Array("M", "D", "C", "L", "X", "V", "I")
    var loopNum = num
    var unitIndex = 0
    val strBuild = new StringBuilder
    while (loopNum > 0) {
      val special = unitIndex % 2 != 0
      if (special && loopNum / (unit(unitIndex - 1) - unit(unitIndex + 1)) == 1) {
        strBuild.append(symbol(unitIndex + 1))
        strBuild.append(symbol(unitIndex - 1))
        loopNum -= unit(unitIndex - 1) - unit(unitIndex + 1)
      } else if (special && loopNum < unit(unitIndex) && loopNum / (unit(unitIndex) - unit(unitIndex + 1)) == 1) {
        strBuild.append(symbol(unitIndex + 1))
        strBuild.append(symbol(unitIndex))
        loopNum -= unit(unitIndex) - unit(unitIndex + 1)
      } else {
        val times = loopNum / unit(unitIndex)
        for (_ <- 0 until (times))
          strBuild.append(symbol(unitIndex))
        loopNum = loopNum % unit(unitIndex)
      }
      unitIndex += 1
    }

    strBuild.toString()
  }

  /**
   * 一种取巧的方式，把特例简化到计算中
   * 这里是因为取巧后的情况刚好不会越界，也就是新创造的单位
   * 采用标准的贪心算法计算即可
   * @param num
   * @return
   */
  def intToRoman2(num: Int): String = {
    val nums = Array(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
    val str = Array("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
    var temp = num
    var res = ""

    for (i <- 0 until nums.length) {
      while (temp >= nums(i)) {
        res += str(i)
        temp -= nums(i)
      }
    }
    res
  }

  def main(args: Array[String]): Unit = {
    println(intToRoman(3))
    println(intToRoman(4))
    println(intToRoman(9))
    println(intToRoman(58))
    println(intToRoman(1994))
  }

}
