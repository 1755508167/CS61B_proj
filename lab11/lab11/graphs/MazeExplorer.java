package lab11.graphs;

import java.util.Observable;

/**
 * @author Josh Hug
 */
public abstract class MazeExplorer extends Observable {
    protected int[] distTo;//从起点到各个位置的距离
    protected int[] edgeTo;//路径信息，即每个点是通过哪个点走到的。记录路径上的前一个点
    protected boolean[] marked;//用于标记哪些位置已被访问 —— 在迷宫中以蓝色显示这些位置。
    protected Maze maze;

    /**
     * Notify all Observers of a change.
     */
    protected void announce() {
        setChanged();
        notifyObservers();
    }

    public MazeExplorer(Maze m) {
        maze = m;

        distTo = new int[maze.V()];
        edgeTo = new int[maze.V()];
        marked = new boolean[maze.V()];
        for (int i = 0; i < maze.V(); i += 1) {
            distTo[i] = Integer.MAX_VALUE;
            edgeTo[i] = Integer.MAX_VALUE;
        }
        addObserver(maze);
    }

    /**
     * Solves the maze, modifying distTo and edgeTo as it goes.
     */
    public abstract void solve();

    /* Getters for AG. */
    public int[] getDistTo() {
        return distTo;
    }

    public int[] getEdgeTo() {
        return edgeTo;
    }
}
