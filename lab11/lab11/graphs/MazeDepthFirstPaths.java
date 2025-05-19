package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeDepthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;//起点的编号
    private int t;//终点的编号
    private boolean targetFound = false;
    private Maze maze;


    public MazeDepthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        //把坐标转化为编号
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;//自己到自己的距离为0
        edgeTo[s] = s;//路径起点没有前驱，就记为自己
    }
    //深度优先搜索的实现
    private void dfs(int v) {
        //标记当前节点被访问
        marked[v] = true;
        announce();
        //检查是否到达终点
        if (v == t) {
            targetFound = true;
        }
        //如果找到目标就返回
        if (targetFound) {
            return;
        }
        //遍历所有邻接的点
        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;//记录路径
                announce();
                distTo[w] = distTo[v] + 1;//记录距离
                dfs(w);//递归搜索
                if (targetFound) {
                    return;
                }
            }
        }
    }

    @Override
    public void solve() {
        dfs(s);
    }
}

