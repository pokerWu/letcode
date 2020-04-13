package xyz.panwu.study.letcode.dynamicProgramming

/**
 * 给你一棵以 root 为根的二叉树和一个 head 为第一个节点的链表。
 *
 * 如果在二叉树中，存在一条一直向下的路径，且每个点的数值恰好一一对应以 head 为首的链表中每个节点的值，那么请你返回 True ，否则返回 False 。
 *
 * 一直向下的路径的意思是：从树中某个节点开始，一直连续向下的路径。
 *
 * 链接：https://leetcode-cn.com/problems/linked-list-in-binary-tree
 */
object T1367_isSubPath {
  def isSubPath(head: ListNode, root: TreeNode): Boolean = {
    if (head == null) return true
    if (root == null) return false

    isSub(head, root) || isSubPath(head, root.left) || isSubPath(head, root.right)
  }

  def isSub(head: ListNode, node: TreeNode): Boolean = {
    if (head == null) return true
    if (node == null) return false

    if (head.x != node.value) return false
    isSub(head.next, node.left) || isSub(head.next, node.right)
  }

  def main(args: Array[String]): Unit = {

  }
}


class ListNode(var _x: Int = 0) {
  var next: ListNode = _
  var x: Int = _x
}


class TreeNode(var _value: Int) {
  var value: Int = _value
  var left: TreeNode = _
  var right: TreeNode = _
}
