package xyz.panwu.study.letcode

object T2_addTwoNumbers {

  def addTwoNumbers(l1: ListNode, l2: ListNode): ListNode = {
    var item = l1
    var l1Num = 0L
    var index = 0
    while (item != null) {
      l1Num = l1Num + item._x * (10 ^ index)
      item = item.next
      index += 1
    }

    item = l2
    var l2Num = 0L
    index = 0

    while (item != null) {
      l2Num = l2Num + item._x * (10 ^ index)
      item = item.next
      index += 1
    }


  }

  def main(args: Array[String]): Unit = {

  }
}

//Definition for singly-linked list.
class ListNode(var _x: Int = 0) {
  var next: ListNode = null
  var x: Int = _x
}
