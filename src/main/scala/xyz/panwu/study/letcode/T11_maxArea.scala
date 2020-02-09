package xyz.panwu.study.letcode

/**
 * 给定 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 * 说明：你不能倾斜容器，且 n 的值至少为 2。
 * 9
 * 8     *              *
 * 7     +  +  +  +  +  +  +  +
 * 6     +  *           *     +
 * 5     +  *     *     *     +
 * 4     +  *     *  *  *     +
 * 3     +  *     *  *  *  *  +
 * 2     +  *  *  *  *  *  *  +
 * 1  *  +  *  *  *  *  *  *  +
 * ----------------------------------------------------------------
 * 0  1  2  3  4  5  6  7  8  9  10
 * 图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
 *
 *
 * 示例:
 *
 * 输入: [1,8,6,2,5,4,8,3,7]
 * 输出: 49
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/container-with-most-water
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
object T11_maxArea {

  /**
   * 双指针法
   * 算法
   *
   * 这种方法背后的思路在于，两线段之间形成的区域总是会受到其中 较短那条长度  的限制。此外，
   *   两线段距离越远，得到的面积就越大。
   *
   * 我们在由线段长度构成的数组中使用两个指针，一个放在开始，一个置于末尾。 此外，我们会使用变量 maxareamaxarea 来持续存储到目前为止所获得的最大面积。
   * 在每一步中，我们会找出指针所指向的两条线段形成的区域，更新 maxareamaxarea，并将指向较短线段的指针向较长线段那端移动一步。
   *
   * 链接：https://leetcode-cn.com/problems/container-with-most-water/solution/sheng-zui-duo-shui-de-rong-qi-by-leetcode/
   *
   * @param height
   * @return
   */
  def maxArea(height: Array[Int]): Int = {
    if (height == null || height.length < 2) return 0
    var maxArea = 0
    var left = 0
    var right = height.length - 1
    while (left < right) {
      maxArea = Math.max(maxArea, (right - left) * Math.min(height(left), height(right)))
      if (height(left) > height(right)) right -=1 else left += 1
    }
    maxArea
  }

  def main(args: Array[String]): Unit = {
    println(maxArea(Array(1,8,6,2,5,4,8,3,7)))
  }
}
