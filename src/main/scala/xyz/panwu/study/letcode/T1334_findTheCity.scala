package xyz.panwu.study.letcode

/**
 *
 * 1334. 阈值距离内邻居最少的城市
 *
 * 有 n 个城市，按从 0 到 n-1 编号。给你一个边数组 edges，其中 edges[i] = [fromi, toi, weighti] 代表 fromi 和 toi 两个城市之间的双向加权边，距离阈值是一个整数 distanceThreshold。
 *
 * 返回能通过某些路径到达其他城市数目最少、且路径距离 最大 为 distanceThreshold 的城市。如果有多个这样的城市，则返回编号最大的城市。
 *
 * 注意，连接城市 i 和 j 的路径的距离等于沿该路径的所有边的权重之和。
 *
 *  输入：n = 4, edges = [[0,1,3],[1,2,1],[1,3,4],[2,3,1]], distanceThreshold = 4
 * 输出：3
 * 解释：城市分布图如上。
 * 每个城市阈值距离 distanceThreshold = 4 内的邻居城市分别是：
 * 城市 0 -> [城市 1, 城市 2] 
 * 城市 1 -> [城市 0, 城市 2, 城市 3] 
 * 城市 2 -> [城市 0, 城市 1, 城市 3] 
 * 城市 3 -> [城市 1, 城市 2] 
 * 城市 0 和 3 在阈值距离 4 以内都有 2 个邻居城市，但是我们必须返回城市 3，因为它的编号最大。
 *
 *
 */
object T1334_findTheCity {

  def findTheCity(n: Int, edges: Array[Array[Int]], distanceThreshold: Int): Int = {
    val distance = Array.fill[Int](edges.length, edges.length)(Int.MaxValue)
    edges.foreach(edge => {
      distance(edge(0))(edge(1)) = edge(2)
      distance(edge(1))(edge(0)) = edge(2)
    })
    val path = Array.fill[Int](edges.length, edges.length)(-1)
    for (k <- edges.indices) {
      for (i <- edges.indices) {
        for (j <- edges.indices) {
          if (distance(i)(j) != Int.MaxValue && distance(i)(k) != Int.MaxValue && distance(k)(j)!= Int.MaxValue
            && distance(i)(k) + distance(k)(j) <= distance(i)(j))
            path(i)(j) = k
        }
      }
    }

    var cities = 0
    path.zipWithIndex.foreach(p => {
      for (e <- p._1) {
        if (e != -1 &&  e < distanceThreshold) cities += 1
      }
    })
    -1
  }

  def main(args: Array[String]): Unit = {
    println(findTheCity(4, Array(
      Array(0,1,3),
      Array(1,2,1),
      Array(1,3,4),
      Array(2,3,1)
    ), 4))
  }

}
