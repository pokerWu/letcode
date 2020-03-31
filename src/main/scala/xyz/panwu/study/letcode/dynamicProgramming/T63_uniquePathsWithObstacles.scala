package xyz.panwu.study.letcode.dynamicProgramming

/**
 * 63. 不同路径 II
 *
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
 *
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
 *
 * 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
 *
 * 网格中的障碍物和空位置分别用 1 和 0 来表示。
 *
 *
 * 说明：m 和 n 的值均不超过 100。
 *
 * 示例 1:
 *
 * 输入:
 * [
 *   [0,0,0],
 *   [0,1,0],
 *   [0,0,0]
 * ]
 * 输出: 2
 * 解释:
 * 3x3 网格的正中间有一个障碍物。
 * 从左上角到右下角一共有 2 条不同的路径：
 * 1. 向右 -> 向右 -> 向下 -> 向下
 * 2. 向下 -> 向下 -> 向右 -> 向右
 *
 *
 * 链接：https://leetcode-cn.com/problems/unique-paths-ii
 */
object T63_uniquePathsWithObstacles {

  /**
   * 如果第一个格点 obstacleGrid[0,0] 是 1，说明有障碍物，那么机器人不能做任何移动，我们返回结果 0。
   *
   * 否则，如果 obstacleGrid[0,0] 是 0，我们初始化这个值为 1 然后继续算法。
   *
   * 遍历第一行，如果有一个格点初始值为 1 ，说明当前节点有障碍物，没有路径可以通过，设值为 0 ；否则设这个值是前一个节点的值 obstacleGrid[i,j] = obstacleGrid[i,j-1]。
   *
   * 遍历第一列，如果有一个格点初始值为 1 ，说明当前节点有障碍物，没有路径可以通过，设值为 0 ；否则设这个值是前一个节点的值 obstacleGrid[i,j] = obstacleGrid[i-1,j]。
   *
   * 现在，从 obstacleGrid[1,1] 开始遍历整个数组，如果某个格点初始不包含任何障碍物，就把值赋为上方和左侧两个格点方案数之和 obstacleGrid[i,j] = obstacleGrid[i-1,j] + obstacleGrid[i,j-1]。
   *
   * 如果这个点有障碍物，设值为 0 ，这可以保证不会对后面的路径产生贡献。
   *
   * 作者：LeetCode
   * 链接：https://leetcode-cn.com/problems/unique-paths-ii/solution/bu-tong-lu-jing-ii-by-leetcode/
   *
   * @param obstacleGrid
   * @return
   */
  def uniquePathsWithObstacles(obstacleGrid: Array[Array[Int]]): Int = {
    var brokeRow = false
    var brokeColumn = false
    obstacleGrid.indices.foreach(i => {
      obstacleGrid(0).indices.foreach(j => {
        //出发位置
        if (i == 0 && j == 0) {
          //出发位置为1 就不能继续了
          if (obstacleGrid(i)(j) != 0) return 0 else obstacleGrid(i)(j) = 1
        }
        //第一行： 如果遇到1了，后面的的都不能通过
        else if (i == 0) {
          obstacleGrid(i)(j) = if (obstacleGrid(i)(j) != 0 || brokeRow) {
            brokeRow = true
            0
          } else 1
        }
        //第一列，同第一行，遇到1了后面都不能通过了
        else if (j == 0) {
          obstacleGrid(i)(j) = if (obstacleGrid(i)(j) != 0 || brokeColumn) {
            brokeColumn = true
            0
          } else 1
        } else
        //当前是不可继续的块，直接为0，否则等于同行的前一个（i, j-1） + 同列的上一个 （i - 1, j）
          obstacleGrid(i)(j) = if (obstacleGrid(i)(j) != 0) 0 else obstacleGrid(i - 1)(j) + obstacleGrid(i)(j - 1)
      })
    })
    obstacleGrid(obstacleGrid.length - 1)(obstacleGrid(0).length - 1)
  }

  def main(args: Array[String]): Unit = {
    println(uniquePathsWithObstacles(Array(
      Array(0, 0, 0),
      Array(0, 1, 0),
      Array(0, 0, 0)
    )))
  }
}
