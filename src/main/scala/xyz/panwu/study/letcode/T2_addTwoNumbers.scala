package xyz.panwu.study.letcode

/**
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 *
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 *
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * 示例：
 *
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 *
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 */
object T2_addTwoNumbers {

  /**
   * 我第一次实现的时候，考虑的是将两个数还原出来，但是这里会涉及到大数的问题，超过long
   *    2 | 4 | 3 | null
   *  + 5 | 6 | 4 | null
   *  ----------------------
   *  = 7 | 0 | 8 | null
   *
   *  按照小学生计算加法的方式计算，然后控制进位进位
   *  如果要方向数据，只需要在构建链表时完成
   *
   *  这是一个倾向传统计算的方式，一点不scala
   * @param l1
   * @param l2
   * @return
   */
  def addTwoNumbers(l1: ListNode, l2: ListNode): ListNode = {
    var carry = 0
    var l1Pre = l1
    var l2Pre = l2
    var head: ListNode = null
    var tail: ListNode = null
    while (l1Pre != null || l2Pre != null) {
      val x1 = if (l1Pre == null) 0 else l1Pre._x
      val x2 = if (l2Pre == null) 0 else l2Pre._x
      val sum = x1 + x2 + carry
      carry = sum / 10
      val node = new ListNode(sum % 10)
      if (head == null) {
        head = node
        tail = head
      } else {
        tail.next = node
        tail = node
      }
      l1Pre = if (l1Pre != null) l1Pre.next else null
      l2Pre = if (l2Pre != null) l2Pre.next else null
    }
    if (carry != 0) {
      val node = new ListNode(carry)
      tail.next = node
      tail = node
    }
    head
  }

  def main(args: Array[String]): Unit = {
    val head = new ListNode(2)
    var tail = head
    val node2 = new ListNode(4)
    tail.next = node2
    tail = node2
    val node3 = new ListNode(3)
    tail.next = node3
    tail = node3

    val head2 = new ListNode(5)
    var tail2 = head2
    val n2 = new ListNode(6)
    tail2.next = n2
    tail2 = n2
    val n3 = new ListNode(4)
    tail2.next = n3
    tail2 = n3

    var res = addTwoNumbers(head, head2)
    while (res != null) {
      println(res._x)
      res = res.next
    }


    val h1 = new ListNode(9)


    val h2 = new ListNode(1)
    var t2 = h2
    val ln2 = new ListNode(9)
    t2.next = ln2
    t2 = ln2
    val ln3 = new ListNode(9)
    t2.next = ln3
    t2 = ln3
    val ln4 = new ListNode(9)
    t2.next = ln4
    t2 = ln4
    val ln5 = new ListNode(9)
    t2.next = ln5
    t2 = ln5
    val ln6 = new ListNode(9)
    t2.next = ln6
    t2 = ln6
    val ln7 = new ListNode(9)
    t2.next = ln7
    t2 = ln7
    val ln8 = new ListNode(9)
    t2.next = ln8
    t2 = ln8
    val ln9 = new ListNode(9)
    t2.next = ln9
    t2 = ln9
    val ln10 = new ListNode(9)
    t2.next = ln10
    t2 = ln10
    val ln11 = new ListNode(9)
    t2.next = ln11
    t2 = ln11

    var r = addTwoNumbers(h1, h2)
    while (r != null) {
      println(r._x)
      r = r.next
    }

  }
}

//Definition for singly-linked list.
class ListNode(var _x: Int = 0) {
  var next: ListNode = null
  var x: Int = _x
}
