package hw4.puzzle;

public interface WorldState {
    /** Provides an estimate of the number of moves to reach the goal.
     * Must be less than or equal to the correct distance. */
    //计算当前状态到目前状态的距离，用于A*算法中的启发函数h(x)
    int estimatedDistanceToGoal();

    /** Provides an iterable of all the neighbors of this WorldState. */
    //返回所有相邻对象
    Iterable<WorldState> neighbors();

    default boolean isGoal() {
        return estimatedDistanceToGoal() == 0;
    }
}
