package hw4.puzzle;

public class Board {
    //创建一个N乘N的拼图板
    public Board(int[][] tiles) {

    }
    //返回位于(i,j)的tile
    public int tileAt(int i, int j) {
        return 0;
    }
    //返回拼图的大小，即N乘N
    public int size() {
        return 0;
    }

    public Iterable<WorldState> neighbors() {
        return null;
    }

    public int hamming() {
        return 0;
    }

    public int manhattan() {
        return 0;
    }

    public int estimatedDistanceToGoal() {
        return 0;
    }

    public boolean equals(Object y) {
        return false;
    }
    public boolean isGoal(){
        return false;
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
