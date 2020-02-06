package xyz.panwu.study.letcode

/**
 * 将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
 *
 * 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：
 *
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："LCIRETOESIIGEDHN"。
 *
 * 请你实现这个将字符串进行指定行数变换的函数：
 *
 * string convert(string s, int numRows);
 * 示例 1:
 *
 * 输入: s = "LEETCODEISHIRING", numRows = 3
 * 输出: "LCIRETOESIIGEDHN"
 * 示例 2:
 *
 * 输入: s = "LEETCODEISHIRING", numRows = 4
 * 输出: "LDREOEIIECIHNTSG"
 * 解释:
 *
 * L,0          D,6           R,12
 * E,1     O,5  E,7     I,11  I,13
 * E,2  C,4     I,8  H,10     N,14
 * T,3          S,9           G,15
 *
 * 链接：https://leetcode-cn.com/problems/zigzag-conversion
 */
object T6_zConvert {

  /**
   * 传统做法
   *  circle = rowNum + (rowNum-2) 作为一个循环， n个循环
   *  第0行的位置是 n' * circle
   *  最后一行是 n' * circle + (rowNum - 1)
   *  第i行 n' * circle + i 和 n' * circle + circle - i
   *
   *  需要确保这些位置都没越界
   * @param s
   * @param row
   * @return
   */
  def convert(s: String, row: Int): String = {
    if (s == null || s.length < 2) return s
    if (row == 1) return s
    val strBuild = new StringBuilder(s.length)
    val circle = 2 * row - 2
    val turn = if (s.length % circle == 0) s.length / circle else s.length / circle + 1
    for (i <- 0 until (row)) {
      if (i == 0) {
        for (j <- 0 until turn)
          strBuild.append(s.charAt(j * circle))
      } else if (i == row - 1) {
        for (j <- 0 until turn)
          if (j * circle + row - 1 < s.length)
            strBuild.append(s.charAt(j * circle + row - 1))
      } else {
        for (j <- 0 until turn) {
          if (j * circle + i < s.length)
            strBuild.append(s.charAt(j * circle + i))
          if (j * circle + circle - i < s.length)
            strBuild.append(s.charAt(j * circle + circle - i))
        }
      }
    }
    strBuild.toString()
  }

  def main(args: Array[String]): Unit = {
    val res = convert("LEETCODEISHIRING", 4)
    println(res)
  }
}
