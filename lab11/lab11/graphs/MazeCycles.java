package lab11.graphs;

/**
 * @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean hasCycle = false;
    private int[] parent;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        parent=new int[m.V()];
    }

    //环路检测算法的实现
    @Override
    public void solve() {
        // TODO: Your code here!
        for (int v = 0; v < maze.V(); v++) {
            if (!marked[v]) {
                dfs(v, -1);
                if (hasCycle) return;
            }
        }
    }

    //深度优先搜索
    private void dfs(int v,int from) {
        marked[v]=true;
        announce();

        for (int w:maze.adj(v)){
            if (hasCycle){
                return;
            }
            if (!marked[w]){
                edgeTo[w]=v;
                parent[w]=v;
                announce();
                dfs(w,v);
            } else if (w != from) {
                edgeTo[w]=v;
                reconstructCycle(v, w);
                hasCycle = true;
                return;
            }
        }
    }
    // Helper methods go here

    private void reconstructCycle(int v,int w){
        //回溯路径
        int x=v;
        while (x != w){
            edgeTo[w]=parent[x];
            x=parent[x];
        }
        edgeTo[w] = x;
        announce();  // Show the cycle
    }
}

