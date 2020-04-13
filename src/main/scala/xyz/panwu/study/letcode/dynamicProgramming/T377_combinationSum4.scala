package xyz.panwu.study.letcode.dynamicProgramming

/**
 * 377. 组合总和 Ⅳ
 *
 * 给定一个由正整数组成且不存在重复数字的数组，找出和为给定目标正整数的组合的个数。
 *
 * 示例:
 *
 * nums = [1, 2, 3]
 * target = 4
 *
 * 所有可能的组合为：
 * (1, 1, 1, 1)
 * (1, 1, 2)
 * (1, 2, 1)
 * (1, 3)
 * (2, 1, 1)
 * (2, 2)
 * (3, 1)
 *
 * 请注意，顺序不同的序列被视作不同的组合。
 *
 * 因此输出为 7。
 *
 * 链接：https://leetcode-cn.com/problems/combination-sum-iv
 *
 */
object T377_combinationSum4 {
  /**
   * 动态规划”的两个步骤是思考“状态”以及“状态转移方程”。
   *
   * 1、状态
   *
   * 对于“状态”，我们首先思考能不能就用问题当中问的方式定义状态，上面递归树都画出来了。当然就用问题问的方式。
   *
   * dp[i] ：对于给定的由正整数组成且不存在重复数字的数组，和为 i 的组合的个数。
   *
   * 思考输出什么？因为状态就是问题当中问的方式而定义的，因此输出就是最后一个状态 dp[n]。
   *
   * 2、状态转移方程
   *
   * 由上面的树形图，可以很容易地写出状态转移方程：
   *
   * dp[i] = sum{dp[i - num] for num in nums and if i >= num}
   * 注意：在 00 这一点，我们定义 dp[0] = 1 的，它表示如果 nums 里有一个数恰好等于 target，它单独成为 11 种可能。
   *
   * 作者：liweiwei1419
   * 链接：https://leetcode-cn.com/problems/combination-sum-iv/solution/dong-tai-gui-hua-python-dai-ma-by-liweiwei1419/
   *
   * @param nums
   * @param target
   * @return
   */
  def combinationSum4(nums: Array[Int], target: Int): Int = {
    if (nums == null || nums.isEmpty) return 0
    val dp = Array.ofDim[Int](target + 1)
    dp(0) = 1

    (1 to target).foreach(i => {
      for (num <- nums) {
        if (num <= i)
          dp(i) += dp(i - num)
      }
    })
    dp(target)
  }

  def main(args: Array[String]): Unit = {
    println(combinationSum4(Array(1,2,3), 4))
  }
}
