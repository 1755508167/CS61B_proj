package hw4.puzzle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Board implements WorldState {
    //定义一个拼图板
    int[][] titles;
    int N;

    //创建一个N乘N的拼图板
    public Board(int[][] tiles) {
        //N×N
        int rows = tiles.length;
        this.N = rows;
        this.titles=new int[N][N];
        for (int i =0;i<N;i++){
            for (int j =0;j<N;j++){
                this.titles[i][j]=tiles[i][j];
            }
        }
        //判断数字是否都在正确的范围内
        for (int[] row : tiles) {
            for (int tile : row) {
                if (tile > (N*N-1) || tile < 0) {
                    throw new UnsupportedOperationException("数不在正确的范围内");
                }
            }
        }
    }

    //返回位于(i,j)的tile
    public int tileAt(int i, int j) {
        return titles[i][j];
    }

    //返回拼图的大小，即N乘N
    public int size() {
        return N;
    }

    //返回当前拼图状态的所有邻接状态
    //只需要找到0，然后将其上下左右移动即可得到4个邻接状态
    public Iterable<WorldState> neighbors() {
        Set<WorldState> neighbors = new HashSet<>();
        int blankRow=0;
        int blankCol=0;
        // 找到空格位置
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (titles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                    break;
                }
            }
        }
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            int newRow = blankRow + dir[0];
            int newCol = blankCol + dir[1];
            if (newRow >= 0 && newRow < N && newCol >= 0 && newCol < N) {
                int[][] newTiles = deepCopy(titles);
                // 交换空格与目标位置
                newTiles[blankRow][blankCol] = newTiles[newRow][newCol];
                newTiles[newRow][newCol] = 0;
                neighbors.add(new Board(newTiles));
            }
        }
        return neighbors;
    }

    private int[][] deepCopy(int[][] original) {
        int[][] copy = new int[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }

    //计算汉明距离
    public int hamming() {
        int distance = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (titles[i][j] != 0 && titles[i][j] != hammingHelper(i, j)) {
                    distance++;
                }
            }
        }
        return distance;
    }

    //计算tiles中某个位置的数应为多少，将坐标转换为index
    private int hammingHelper(int i, int j) {
        int index = i * N + j + 1;
        return index;
    }

    //计算曼哈顿距离
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int value = titles[i][j];
                if (value != 0) {
                    int expectedRow = (value - 1) / N;
                    int expectedCol = (value - 1) % N;
                    distance += Math.abs(i - expectedRow) + Math.abs(j - expectedCol);
                }
            }
        }
        return distance;
    }

    //返回(i,j)的曼哈顿距离
    private int manhattanHelper(int i, int j) {
        int distance = 0;
        //拼图中坐标为(i,j)对应的数
        int realNumber = titles[i][j];
        //未打乱拼图中坐标为(i,j)实际应为的数
        int idealNumber = hammingHelper(i, j);
        //realNumber在未打乱拼图中应为的坐标
        int[] numberLocation = indexToLocation(realNumber);
        //若不相等，计算曼哈顿距离
        if (idealNumber != realNumber) {
            distance = Math.abs(i - numberLocation[0]) + Math.abs(j - numberLocation[1]);
        }
        return distance;
    }

    //计算一个数在tiles中的坐标
    private int[] indexToLocation(int number) {
        int i = (number - 1) / N;
        int j = (number - 1) % N;
        return new int[]{i, j};
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    //判断两个拼图是否相等
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (this.N != that.N) return false;
        for (int i = 0; i < N; i++) {
            if (!Arrays.equals(this.titles[i], that.titles[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    //判断一个拼图是否是goal
    public boolean isGoal() {
        return hamming() == 0;
    }

    /**
     * Returns the string representation of the board.
     * Uncomment this method.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
