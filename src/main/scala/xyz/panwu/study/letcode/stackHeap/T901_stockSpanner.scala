package xyz.panwu.study.letcode.stackHeap

import scala.collection.mutable

/**
 * 901. 股票价格跨度
 *
 * * 编写一个 StockSpanner 类，它收集某些股票的每日报价，并返回该股票当日价格的跨度。
 * *
 * * 今天股票价格的跨度被定义为股票价格小于或等于今天价格的最大连续日数（从今天开始往回数，包括今天）。
 * *
 * * 例如，如果未来7天股票的价格是 [100, 80, 60, 70, 60, 75, 85]，那么股票跨度将是 [1, 1, 1, 2, 1, 4, 6]。
 * *
 * *  
 * *
 * * 示例：
 * *
 * * 输入：["StockSpanner","next","next","next","next","next","next","next"], [[],[100],[80],[60],[70],[60],[75],[85]]
 * * 输出：[null,1,1,1,2,1,4,6]
 * * 解释：
 * * 首先，初始化 S = StockSpanner()，然后：
 * * S.next(100) 被调用并返回 1，
 * * S.next(80) 被调用并返回 1，
 * * S.next(60) 被调用并返回 1，
 * * S.next(70) 被调用并返回 2，
 * * S.next(60) 被调用并返回 1，
 * * S.next(75) 被调用并返回 4，
 * * S.next(85) 被调用并返回 6。
 * *
 * * 注意 (例如) S.next(75) 返回 4，因为截至今天的最后 4 个价格
 * * (包括今天的价格 75) 小于或等于今天的价格。
 * *  
 * *
 * * 提示：
 * *
 * * 调用 StockSpanner.next(int price) 时，将有 1 <= price <= 10^5。
 * * 每个测试用例最多可以调用  10000 次 StockSpanner.next。
 * * 在所有测试用例中，最多调用 150000 次 StockSpanner.next。
 * * 此问题的总时间限制减少了 50%。
 * *
 * * 链接：https://leetcode-cn.com/problems/online-stock-span
 **/
object T901_stockSpanner {


  /**
   * 这道题利用了 单调增栈每次插入新数据时，会一次性将比自己小的元素全部排出 的特性
   * 这个特性正好和题目里的 价格小于或等于今天价格的最大连续日 是刚好吻合的
   *
   * 换句话说，我们单次插入某值时抛弃的元素组，就是该值前的一段连续日
   * 所以每次插入新数据，都会将栈顶 折叠 一次，你可以想象成俄罗斯方块消层那种感觉
   *
   * 问题在于，因为单调栈是要把元素都丢弃的，状态都被“折叠”了，我们会丢失长度，所以容易想到，我们需要cache一下之前栈内元素被折叠的长度
   * cache有很多种方式，可以用hash表等数据结构，也可以用动态规划
   * 但这题的更优解是，使用另一个同步栈来缓存，读者可以根据下面第一版的代码，动笔推导一下折叠过程，就能体会同步栈工作的原理了。
   *
   * 都说编程旷世难题是取名字，名字取好了，问题也就解决了一半（才怪
   * 我们可以将两个栈分别命名为 prices 和 cache
   *
   * <hr/>
   *
   * 我们如果发现插入元素满足本身栈的递减需求，则折叠栈直接插入1，因为该值前一个值是比它大的，他们间隔1天
   * 如果不满足，则开始折叠，并将折叠栈中，值比它小的所有段落的折叠长度都累计起来，再重新插入折叠栈中保存
   *
   */
  class StockSpanner() {
    val pricesStack = new mutable.Stack[Int]()
    val cacheStack = new mutable.Stack[Int]()

    def next(price: Int): Int = {
      var res = 1
      while (pricesStack.nonEmpty && pricesStack.top <= price) {
        pricesStack.pop()
        res += cacheStack.pop()
      }
      cacheStack.push(res)
      pricesStack.push(price)
      res
    }

  }

  def main(args: Array[String]): Unit = {
    val s = new StockSpanner

    println(s.next(100))
    println(s.next(80))
    println(s.next(60))
    println(s.next(70))
    println(s.next(60))
    println(s.next(75))
    println(s.next(85))
  }

}
