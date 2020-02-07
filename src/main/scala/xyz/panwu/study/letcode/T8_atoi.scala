package xyz.panwu.study.letcode

import scala.util.control.Breaks._

object T8_atoi {

  def atoi(s: String): Int = {
    if (s == null || s.length < 1) return 0
    //flag 1 + , -1 -
    var flag = 1
    var start = 0
    var end = 0
    val str = s.trim
    if (str.length < 1) return 0
    str.charAt(0) match {
      case '-' =>
        flag = -1
        start = 1
        end = 1
      case '+' =>
        flag = 1
        start = 1
        end = 1
      case c if c < '0' && c > '9' => return 0
      case _ =>
    }
    //确定数值的位置
    breakable {
      for (i <- start until (str.length)) {
        val c = str.charAt(i)
        if (c <= '9' && c >= '0') {
          end += 1
        } else break()
      }
    }
    //遍历数值
    var res: Int = 0
    for (i <- start until (end)) {
      val c = str.charAt(i)
      // 确保下一步进位时 res小于 MAX 前九位： 314748364 已经超过了214748364 下一步进位必然越界
      if (res > Int.MaxValue / 10) return if (flag == -1) Int.MinValue else Int.MaxValue
      val tmp: Int = res * 10
      //这里就确保最后一位的值，+ 2147483647 ； -2147483648
      if (tmp == Int.MaxValue - 7 && flag == 1 && c - 48 > 7) return Int.MaxValue
      if (tmp == Int.MaxValue - 7 && flag == -1 && c - 48 > 8) return Int.MinValue
      res = tmp + (c - 48)
    }
    flag * res
  }

  def main(args: Array[String]): Unit = {
    println(atoi("2147483648"))
  }
}
