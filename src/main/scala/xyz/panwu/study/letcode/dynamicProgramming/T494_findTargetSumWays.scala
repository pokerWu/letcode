package xyz.panwu.study.letcode.dynamicProgramming

import scala.collection.mutable

/**
 * 494. 目标和
 *
 * 给定一个非负整数数组，a1, a2, ..., an, 和一个目标数，S。现在你有两个符号 + 和 -。对于数组中的任意一个整数，你都可以从 + 或 -中选择一个符号添加在前面。
 *
 * 返回可以使最终数组和为目标数 S 的所有添加符号的方法数。
 *
 * 示例 1:
 *
 * 输入: nums: [1, 1, 1, 1, 1], S: 3
 * 输出: 5
 * 解释:
 *
 * -1+1+1+1+1 = 3
 * +1-1+1+1+1 = 3
 * +1+1-1+1+1 = 3
 * +1+1+1-1+1 = 3
 * +1+1+1+1-1 = 3
 *
 * 一共有5种方法让最终目标和为3。
 *
 * 链接：https://leetcode-cn.com/problems/target-sum
 */
object T494_findTargetSumWays {

  /**
   * 这是一个纯动态规划的方法，比较烧脑，运算时间也没有第二种少。
   *
   * 设置一个哈希表（字典），键是一个元祖，元祖第一位是目前的和，第二位是目前的位数。值是这个元祖推导到最后能有多少个解。
   * 例如d[(4,5)] = 1 代表读到4位的时候，正好有一个解符合条件（那么在这个例子中符合条件的S就是5），然后倒导d([3,5]) = 2 ......(在这种情况下，第4位是0，总共就4位)
   * 因为符号要么全正，要么全负，所以元祖第二位的取值范围是 -sum(nums) ~ sum(nums)
   * 状态转移公式：
   * dp[(i,j)] = dp.get((i - 1, j - nums[i]), 0) + dp.get((i - 1, j + nums[i]), 0)
   *
   * 其实本质上就是没有使用递归的dfs。
   *
   * 链接：https://leetcode-cn.com/problems/target-sum/solution/python-dfs-xiang-jie-by-jimmy00745/
   *
   * @param nums
   * @param S
   * @return
   */
  def findTargetSumWays(nums: Array[Int], S: Int): Int = {
    if (nums == null || nums.isEmpty) return 0
    val dp = mutable.Map[(Int, Int), Int]()
    //这里初始化第一个num的原因是表示只用第一个数
    //那么只用第一个数 能组成满足s' 的只可能有两个 + -
    //这是背包问题，表示只用前几个能否组成一个和为s'

    //要注意 第一位为0的时候，0+0 =0 0-0+0 所以 dp((0,0)) = 2
    dp((0, nums(0))) = dp.getOrElse((0, nums(0)), 0) + 1
    dp((0, -nums(0))) = dp.getOrElse((0, -nums(0)), 0) + 1

    val sum = nums.sum

    (1 until (nums.length)).foreach(i => {
      (-sum to sum).foreach(j => {
        dp((i, j)) = dp.getOrElse((i - 1, j - nums(i)), 0) + dp.getOrElse((i - 1, j + nums(i)), 0)
      })
    })

    dp.getOrElse((nums.length - 1, S), 0)
  }

  /**
   * 原问题是给定一些数字，加加减减，使得它们等于targert。例如，1 - 2 + 3 - 4 + 5 = target(3)。如果我们把加的和减的结合在一起，可以写成
   *
   * (1+3+5)  +  (-2-4) = target(3)
   * -------     ------
   * -> 正数    -> 负数
   * 所以，我们可以将原问题转化为： 找到nums一个正子集和一个负子集，使得总和等于target，统计这种可能性的总数。
   *
   * 我们假设P是正子集，N是负子集。让我们看看如何将其转换为子集求和问题：
   *
   * sum(P) - sum(N) = target
   * （两边同时加上sum(P)+sum(N)）
   * sum(P) + sum(N) + sum(P) - sum(N) = target + sum(P) + sum(N)
   * (因为 sum(P) + sum(N) = sum(nums))
   * 2 * sum(P) = target + sum(nums)
   * 因此，原来的问题已转化为一个求子集的和问题： 找到nums的一个子集 P，使得
   *
   * sum(P) = \frac{target + sum(nums)}{2}
   * sum(P)=
   * 2
   * target+sum(nums)
   * ​
   *
   *
   * 根据公式，若target + sum(nums)不是偶数，就不存在答案，即返回0个可能解。
   *
   * 因此题目转化为01背包，也就是能组合成容量为sum(P)的方式有多少种,一种组合中每个数字只能取一次。解决01背包问题使用的是动态规划的思想。
   *
   * 方法是
   *
   * 开辟一个长度为P+1的数组，命名为dp
   * dp的第x项，代表组合成数字x有多少方法。比如说,dp[0] = 1，代表组合成0只有1中方法，即什么也不取。比如说dp[5] = 3 ，代表使总和加到5总共有三种方法。
   * 所以最后返回的就是dp[P]，代表组合成P的方法有多少种
   * 问题是
   *
   * 怎么更新dp数组呢？
   *
   * 遍历nums，遍历的数记作num
   * 再逆序遍历从P到num，遍历的数记作j
   * 更新dp[j] = dp[j - num] + dp[j]
   * 这样遍历的含义是，对每一个在nums数组中的数num而言，dp在从num到P的这些区间里，都可以加上一个num，来到达想要达成的P。
   * 举例来说，对于数组[1,2,3,4,5]，想要康康几种方法能组合成4,那么设置dp[0]到dp[4]的数组
   * 假如选择了数字2,那么dp[2:5]（也就是2到4）都可以通过加上数字2有所改变，而dp[0:2]（也就是0到1）加上这个2很明显就超了，就不管它。
   * 以前没有考虑过数字2,考虑了会怎么样呢？就要更新dp[2:5]，比如说当我们在更新dp[3]的时候，就相当于dp[3] = dp[3] + dp[1],即本来有多少种方法，加上去掉了2以后有多少种方法。因为以前没有考虑过2,
   * 现在知道，只要整到了1,就一定可以整到3。
   * 为什么以这个顺序来遍历呢？
   * 假如给定nums = [num1,num2,num3]，我们现在可以理解dp[j] = dp[j-num1] + dp[j-num2] + dp[j-num3]。
   *
   * 但是如何避免不会重复计算或者少算？要知道，我们的nums并不是排序的，我们的遍历也不是从小到大的。
   *
   * 我们不妨跟着流程走一遍
   *
   * 第一次num1，仅仅更新了dp[num1] = 1，其他都是0+0都是0啊都是0
   * 第二次num2，更新了dp[num2] = 1和dp[num1+num2] = dp[num1+num2] + dp[num1] = 1,先更新后者。
   * 第三次num3，更新了dp[num3] = 1和dp[num1+num3] += 1和dp[num2+num3] += 1和dp[num1+num2+num3] += 1。按下标从大到小顺序来更新。
   * ......
   * 由此可见，这种顺序遍历能得到最后的答案。这里可以跟着IDE的debug功能走一遍，加深理解。
   *
   * 作者：qsctech-sange
   *
   * @param args
   */
  def oneZeroBag(nums: Array[Int], S: Int): Int = {
    val sum = nums.sum
    if (((S + sum) & 1) == 1 || S > sum) return 0

    val target = (S + sum) / 2
    val dp = Array.ofDim[Int](target + 1)
    dp(0) = 1
    for (num <- nums) {
      for (i <- target to(num, -1)) {
        dp(i) += dp(i - num)
      }
    }
    dp(target)
  }

  def main(args: Array[String]): Unit = {
   // println(findTargetSumWays(Array(0, 0, 0, 0, 0, 0, 0, 0, 1), 1))
    println(oneZeroBag(Array(0, 0, 0, 0, 0, 0, 0, 0, 1), 1))
  }
}
