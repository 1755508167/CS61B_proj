package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Solver {
    //创建搜索节点
    private class SearchNode implements Comparable<SearchNode> {
        WorldState world;//一个world状态
        int number;//从初始状态到达此世界状态需要的次数
        SearchNode preNode;//指向上一个搜索节点
        int priority;

        private SearchNode(WorldState world, int number, SearchNode preNode) {
            this.world = world;
            this.number = number;
            this.preNode = preNode;
            this.priority = number + world.estimatedDistanceToGoal();
        }

        @Override
        public int compareTo(SearchNode other) {
            return Integer.compare(this.priority, other.priority);
        }
    }

    private SearchNode finalNode; // 记录最终目标节点

    public Solver(WorldState initial) {
        //搜索节点的优先队列
        MinPQ<SearchNode> minPQ = new MinPQ<>();
        //将初始搜索节点放入优先队列中
        minPQ.insert(new SearchNode(initial, 0, null));

        Set<WorldState> visited = new HashSet<>();

        while (!minPQ.isEmpty()) {
            SearchNode current = minPQ.delMin();

            if (current.world.isGoal()) {
                finalNode = current;
                break;
            }

            visited.add(current.world);

            for (WorldState neighbor : current.world.neighbors()) {
                if (current.preNode != null && neighbor.equals(current.preNode.world)) {
                    continue;//避免往回走
                }
                if (!visited.contains(neighbor)) {
                    minPQ.insert(new SearchNode(neighbor, current.number + 1, current));
                }
            }

        }
    }

    //从初始状态 到当前状态所需的最少步数
    public int moves() {
        return finalNode.number;
    }

    //返回一个从初始状态到目标状态的worldstate序列
    public Iterable<WorldState> solution() {
        LinkedList<WorldState> path = new LinkedList<>();
        SearchNode current = finalNode;
        while (current != null) {
            path.addFirst(current.world);
            current = current.preNode;
        }
        return path;
    }
}
