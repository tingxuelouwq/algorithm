package com.kevin.leetcode;

/**
 * 类名: Ques21<br/>
 * 包名：com.kevin.leetcode<br/>
 * 作者：kevin<br/>
 * 时间：2018/11/30 9:55<br/>
 * 版本：1.0<br/>
 * 描述：
 */
public class Ques21 {

    private static class ListNode {
        private int val;
        private ListNode next;

        public ListNode(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public ListNode getNext() {
            return next;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }
    }

    /**
     * Time complexity: O(S), where S is the number of all elements in the lists.
     * In the worst case this algorithm performs O(S) recursive call.
     * Space complexity: O(S). Three are O(S) recursive calls, each store need 1
     * space to store the result, so space complexity is O(S).

     */
    public static ListNode mergeTwoLists1(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(-1);
        ListNode head = result;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                result.next = l1;
                l1 = l1.next;
            } else {
                result.next = l2;
                l2 = l2.next;
            }
            result = result.next;
        }
        if (l1 == null) {
            result.next = l2;
        } else if (l2 == null) {
            result.next = l1;
        }
        return head.next;
    }

    public static ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        if (l1.val < l2.val) {
            l1.next = mergeTwoLists2(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists2(l2.next, l1);
            return l2;
        }
    }

    public static void main(String[] args) {
        ListNode l11 = new ListNode(1);
        ListNode l12 = new ListNode(2);
        ListNode l14 = new ListNode(4);
        l11.setNext(l12);
        l12.setNext(l14);

        ListNode l21 = new ListNode(1);
        ListNode l22 = new ListNode(3);
        ListNode l23 = new ListNode(4);
        l21.setNext(l22);
        l22.setNext(l23);

//        ListNode result = mergeTwoLists1(l11, l21);
        ListNode result = mergeTwoLists2(l11, l21);
        while (result != null) {
            System.out.println(result.val);
            result = result.next;
        }
    }
}
