分支限界法：采用广度优先的策略，依次搜索当前扩展节点的所有分支，即依次搜索当前扩展节点的所有相邻节点，抛弃不满足约束条件的节点(
约束条件)以及非最优解的节点(限定条件)，将其余节点加入活结点表，然后从表中选取一个节点作为当前扩展节点，继续搜索。
常用的分支限界法：
1、FIFO分支限界法：按照先入先出原则选择下一个节点作为当前扩展节点
2、LC(Least Cost)分支限界法/优先队列式分支限界法：使用优先队列，每次选择优先级最高的节点作为当前扩展节点
与回溯法的不同：
1、求解目标：回溯法的求解目标是找出解空间树中满足约束条件的所有解；分支限界法的求解目标是找出解空间树中满足约束条件的一个解，或
是在满足约束条件的解中使某一目标函数值达到极大或者极小的解，即找出在某种意义下的最优解。
2、搜索方式：回溯法使用深度优先搜索解空间树；分支限界法使用广度优先或者最小耗费优先搜索解空间树