package xyz.panwu.study.letcode.greedy

object T543_diameterOfBinaryTree {

  var res = 0
  def diameterOfBinaryTree(root: TreeNode): Int = {
    res = 0
    deepMaxLeaf(root)
    res
  }

  def deepMaxLeaf(node: TreeNode): Int = {
    if (node == null) return 0
    val left = deepMaxLeaf(node.left)
    val right = deepMaxLeaf(node.right)
    res = Math.max(res, left + right)
    Math.max(left, right) + 1
  }

  def main(args: Array[String]): Unit = {
    val root = new TreeNode(1)
    root.right = new TreeNode(3)

    val l2 = new TreeNode(2)
    root.left = l2

    l2.left = new TreeNode(4)
    l2.right = new TreeNode(5)
   // println(diameterOfBinaryTree(root))

    println(diameterOfBinaryTree(null))
  }
}


class TreeNode(var _value: Int) {
  var value: Int = _value
  var left: TreeNode = null
  var right: TreeNode = null
}
