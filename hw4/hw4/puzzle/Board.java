package hw4.puzzle;

public class Board implements WorldState{
    //定义一个拼图板
    int[][] titles;
    int N;

    //创建一个N乘N的拼图板
    public Board(int[][] tiles) {
        this.titles = tiles;
        //N×N
        int rows = tiles.length;
        this.N = rows;
        //判断数字是否都在正确的范围内
        for (int[] row : tiles) {
            for (int tile : row) {
                if (tile < (rows - 1) && tile > 0) {
                    continue;
                } else {
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
        return N * N;
    }

    //返回当前拼图状态的所有邻接状态
    public Iterable<WorldState> neighbors() {
        return null;
    }

    //计算汉明距离
    public int hamming() {
        int distance = 0;
        for (int i = 0; i < N - 1; i++) {
            for (int j = 0; j < N - 1; j++) {
                if (titles[i][j] == hammingHelper(i, j)) {
                    distance++;
                }
            }
        }
        return distance;
    }

    //计算tiles中某个位置的数应为多少，将坐标转换为index
    private int hammingHelper(int i, int j) {
        int index = i * N + j;
        return index + 1;
    }

    //计算曼哈顿距离
    public int manhattan() {
        int distnace=0;
        for (int i =0;i<N-1;i++){
            for (int j =0;j<N-1;j++){
                int temp=manhattanHelper(i,j);
                distnace=distnace+temp;
            }
        }
        return distnace;
    }

    //返回(i,j)的曼哈顿距离
    private int manhattanHelper(int i, int j) {
        int distance=0;
        //拼图中坐标为(i,j)对应的数
        int realNumber=titles[i][j];
        //未打乱拼图中坐标为(i,j)实际应为的数
        int idealNumber=hammingHelper(i,j);
        //realNumber在未打乱拼图中应为的坐标
        int[] numberLocation=indexToLocation(realNumber);
        //若不相等，计算曼哈顿距离
        if (idealNumber != realNumber){
            distance=Math.abs(i-numberLocation[0])+Math.abs(j-numberLocation[1]);
        }
        return distance;
    }
    //计算一个数在tiles中的坐标
    private int[] indexToLocation(int number){
        int i=0;
        int j=0;
        int[] location =new int[2];
        j=number%N;
        i=(number-j)/N;
        location[0]=i;
        location[1]=j;
        return location;
    }
    @Override
    public int estimatedDistanceToGoal() {
        return hamming();
    }

    //判断两个拼图是否相等
    public boolean equals(Object y) {
        return false;
    }
    @Override
    //判断一个拼图是否是goal
    public boolean isGoal() {
        return hamming()==0;
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
