package xyz.panwu.study.letcode.slidingWindow

import java.util


/**
 * 给定一个数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 *
 * 返回滑动窗口中的最大值。
 *
 *  
 *
 * 示例:
 *
 * 输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
 * 输出: [3,3,5,5,6,7]
 * 解释:
 *
 * 滑动窗口的位置                最大值
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 * 1 [3  -1  -3] 5  3  6  7       3
 * 1  3 [-1  -3  5] 3  6  7       5
 * 1  3  -1 [-3  5  3] 6  7       5
 * 1  3  -1  -3 [5  3  6] 7       6
 * 1  3  -1  -3  5 [3  6  7]      7
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sliding-window-maximum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
object T239_maxSlidingWindow {

  // time out
  //暴力的一种进化，多了一个指针来表示上一次最大，可以优化一点点比较
  def maxSlidingWindow(nums: Array[Int], k: Int): Array[Int] = {
    if (nums == null || nums.length < 0 || k == 0) return Array()
    if (k == 1) return nums

    val res = new Array[Int](nums.length - k + 1)
    var lastMaxIndex = -1
    for (i <- 0 until (nums.length - k + 1)) {
      if (lastMaxIndex >= i) {
        if (nums(lastMaxIndex) < nums(i + k - 1)) {
          lastMaxIndex = i + k - 1
          res(i) = nums(i + k - 1)
        } else res(i) = nums(lastMaxIndex)
      } else {
        var max = nums(i)
        for (j <- i + 1 until i + k) {
          max = Math.max(max, nums(j))
          if (max == nums(j)) lastMaxIndex = j
        }
        res(i) = max
      }
    }
    res
  }

  // memory out
  /**
   * 算法非常直截了当：
   *
   * 处理前 k 个元素，初始化双向队列。
   *
   * 遍历整个数组。在每一步 :
   *
   * 清理双向队列 :
   *
   *   - 只保留当前滑动窗口中有的元素的索引。
   *
   *   - 移除比当前元素小的所有元素，它们不可能是最大的。
   * 将当前元素添加到双向队列中。
   * 将 deque[0] 添加到输出中。
   * 返回输出数组。
   *
   * @param nums
   * @param k
   * @return
   *
   * 这种方式的java过了，scala没过，应该和Int有关，这是一个包装对象，不是java原始的int
   */
  def dequeue(nums: Array[Int], k: Int): Array[Int] = {
    if (nums == null || nums.length < 0 || k == 0) return Array()
    if (k == 1) return nums
    val res = new Array[Int](nums.length - k + 1)
    val queue = new util.ArrayDeque[Int]();
    // 遍历nums数组
    for (i <- nums.indices) {
      // 保证从大到小 如果前面数小则需要依次弹出，直至满足要求
      while (!queue.isEmpty && nums(queue.peekLast()) <= nums(i)) {
        queue.pollLast();
      }
      // 添加当前值对应的数组下标
      queue.addLast(i);
      // 判断当前队列中队首的值是否有效
      if (queue.peek() <= i - k) {
        queue.poll();
      }
      // 当窗口长度为k时 保存当前窗口中最大值
      if (i + 1 >= k) {
        res(i + 1 - k) = nums(queue.peek());
      }
    }
    res
  }

  def dynamicPrograming(nums: Array[Int], k: Int): Array[Int] = {
    if (nums == null || nums.length < 0 || k == 0) return Array()
    if (k == 1) return nums
    val left = new Array[Int](nums.length)
    val right = new Array[Int](nums.length)
    val res = new Array[Int](nums.length - k + 1)
    val len = nums.length

    nums.indices.foreach(i => {
      //left
      if (i % k == 0) left(i) = nums(i)
      else left(i) = Math.max(left(i - 1), nums(i))

      //right
      if ((len - i - 1) % k == 0 || i == 0) right(len - i - 1) = nums(len - i - 1)
      else right(len - i - 1) = Math.max(nums(len - i - 1), left(len - i))
    })

    for (i <- 0 until (nums.length - k + 1)) {
      res(i) = Math.max(right(i), left(i + k - 1))
    }
    res
  }

  def main(args: Array[String]): Unit = {
    //dynamicPrograming(Array(1, 3, -1, -3, 5, 3, 6, 7), 3).foreach(print)
    dynamicPrograming(Array(9,10,9,-7,-4,-8,2,-6), 5).foreach(print)
  }
}
