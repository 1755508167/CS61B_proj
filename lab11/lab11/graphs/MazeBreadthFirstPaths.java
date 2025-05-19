package lab11.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;//起点的编号
    private int t;//目标点的编号
    private boolean targetFound = false;
    private Maze maze;

    //广度优先搜索
    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        // Add more variables here!
    }

    //广度优先搜索的实现
    /**
     * Conducts a breadth first search of the maze starting at the source.
     */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        Queue<Integer> queue = new LinkedList<>();//创建一个队列
        marked[s]=true;
        queue.offer(s);//把起点放入队列
        announce();

        if (s == t ){
            targetFound=true;
        }
        if (targetFound){
            return;
        }
        //遍历s的所有邻接的点
        while(!queue.isEmpty()){
            int u=queue.poll();//移除并返回队列头部的元素
            for (int v : maze.adj(u)){
                if (!marked[v]){
                    marked[v]=true;
                    distTo[v]=distTo[u]+1;
                    edgeTo[v]=u;
                    announce();
                    queue.offer(v);//v入队
                }

                if (v == t){
                    break;
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

