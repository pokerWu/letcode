package xyz.panwu.study.letcode.dynamicProgramming

/**
 * 91. 解码方法
 *
 * 一条包含字母 A-Z 的消息通过以下方式进行了编码：
 *
 * 'A' -> 1
 * 'B' -> 2
 * ...
 * 'Z' -> 26
 * 给定一个只包含数字的非空字符串，请计算解码方法的总数。
 *
 * 示例 1:
 *
 * 输入: "12"
 * 输出: 2
 * 解释: 它可以解码为 "AB"（1 2）或者 "L"（12）。
 * 示例 2:
 *
 * 输入: "226"
 * 输出: 3
 * 解释: 它可以解码为 "BZ" (2 26), "VF" (22 6), 或者 "BBF" (2 2 6)
 *
 * 链接：https://leetcode-cn.com/problems/decode-ways
 *
 */
object T91_numDecodings {

  /**
   * 解决这道题一个比较高校的解法便是动态规划。
   *
   * 首先，大家还记得爬楼梯问题么，一共有n个台阶，一次只能向上走一阶或者两阶，问一共有多少种走法？
   * 这是个很经典的动态规划问题，用dp[n]表示爬完n个台阶的方法总数，那么最后一步可能是走了一阶，也可能是走了两阶，而走了一阶的方法总数等于爬完n-1个台阶的方法综述，
   * 最后一步走两阶的方法综述等于爬完n-2阶方法总数，因此不考虑边界的话可以有公式：
   * dp[n] = dp[n-1]+dp[n-2]
   *
   * 再回来考虑这道题目，实际上也可以看作爬楼梯问题，每次解码一个数字或者两个数字。我们用dp[i]表示解码字符串s[:i]（包含s[0],s[1],...,s[i-1])的方法总数,考虑dp[i+1]的计算，即解码s[:i+1]的可能情形：
   *
   * s[i]存在字典中，即s[i] ！= '0'（因为测试都是数码串，因此可以这么写），此时解码s[:i+1]可以通过解码字符子串s[:i]和最后一个字符s[i]得到
   * s[i-1:i+1] （即s[i-1]和s[i]构成的字符串）在字典中，在python中即 s[i-1:i+1]>='10 and s[i-1:i+1]<= '26'，此时解码s[:i+1]也可以通过解码字符子串s[:i-1]和最后两个个字符s[i-1:i+1]得到
   *
   * 作者：wdmm
   * 链接：https://leetcode-cn.com/problems/decode-ways/solution/dong-tai-gui-hua-cong-jian-dan-de-pa-lou-ti-wen-ti/
   *
   * @param s
   * @return
   */
  def numDecodings(s: String): Int = {
    if (s == null || s.isEmpty) return 0
    val dp = new Array[Int](s.length)
    s.indices.foreach(i => {
      if (i == 0) {
        if (s(i) == '0') return 0
        dp(i) = 1
      }
      //出现了0，他就不能单独存在，必须要和前一位结合
      else if (s(i) == '0') {
        if (s(i -1) != '1' && s(i - 1) != '2') return 0
        //这里对第二位数做了特殊处理，也可以在loop外面先把前两位处理好在来loop
        dp(i) = if (i== 1) dp(i - 1) else dp(i - 2)
      }
      // s(i-1) 是 '1' 或者 s(i-1) = '2' && s(i) > '0' s(i) < 7
      //这表示两个可以结合 15 ，21这样结果
      else if (s(i-1) == '1' || (s(i - 1) == '2' && s(i) > '0' && s(i) < '7'))
        //在第二个数的时候需要特殊处理一下
        dp(i) = if (i > 1) dp(i - 1) + dp(i - 2) else dp(i - 1) + 1
      //不能结合用
      else dp(i) = dp(i - 1)
    })

    dp(s.length - 1)
  }

  def main(args: Array[String]): Unit = {
    println(numDecodings("226"))
  }
}
