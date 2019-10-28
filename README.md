1. 表、栈、队列。均可以由数组实现和链表实现。
1. 树。
- M叉树的表示法：
```
class TreeNode {
    Object element;
    TreeNode firstChild;
    TreeNode nextSibling;
}
```
- 二叉树是指每个节点都不能有多于两个儿子的树，其表示法如下：
```
class BinaryNode {
    Object element;
    BinaryNode left;
    BinaryNode right;
}
```
- 二叉查找树是指对于一棵二叉树中的每个节点X，其左子树中的所有项的值小于X中的项，其右子树中的所有项的值大于X中的项。
