package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private boolean[][] system;
    private WeightedQuickUnionUF uf;
    //这是两个虚拟节点
    private int virtualTop;//这个节点与第一行的所有节点相连
    private int virtualBottom;//这个节点与最后一行的所有节点相连
    //系统中处于open状态的数量
    private int count=0;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N){
        if (N <= 0){
            throw new java.lang.IndexOutOfBoundsException("Num should not be negetive");
        }

        size=N;
        int totalSites=N*N;
        uf=new WeightedQuickUnionUF(totalSites+2);//多两个虚拟节点
        virtualTop = totalSites;
        virtualBottom = totalSites + 1;
        //默认值就是flase，无需再初始化
        system=new boolean[size][size];
    }
    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!system[row][col]) {
            system[row][col] = true;
            count++;
            //转换坐标
            int index = getIndex(row, col);
            //如果打开的是第一行的节点，那么把这个节点连接到虚拟顶部
            if (row == 0) {
                uf.union(index, virtualTop);
            }
            //如果打开的是最后一行的节点，那么把这个节点连接到虚拟顶部
            if (row == size - 1) {
                uf.union(index, virtualBottom);
            }
            //然后合并此节点上下左右中处于open状态的节点
            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                if (validate(newRow, newCol) && isOpen(newRow, newCol)) {
                    int neighbroIndex = getIndex(newRow, newCol);
                    uf.union(neighbroIndex, index);
                }
            }
        } else {
            if ((row < 0 || row > size) && (col < 0 || col > size)) {
                throw new java.lang.IndexOutOfBoundsException("index is out of range");
            }

        }
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if (validate(row,col)){
            return system[row][col]==true;
        }else {
            throw new IndexOutOfBoundsException("Invalid row or column index");
        }

    }
    //判断一个节点是否与顶部相连
    public boolean isFull(int row, int col){
        if (validate(row,col)){
            if (!isOpen(row,col)){
                return false;
            }else {
                return uf.connected(getIndex(row,col),virtualTop);
            }
        }else {
            throw new IndexOutOfBoundsException("Invalid row or column index");
        }

    }  // is the site (row, col) full?
    // number of open sites
    public int numberOfOpenSites(){
        return count;
    }

    // does the system percolate?
    public boolean percolates(){
        return uf.connected(virtualTop,virtualBottom);
    }
    //确保行列坐标在有效范围内
    private boolean validate(int row,int col){
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return false;
            //throw new IndexOutOfBoundsException("Invalid row or column index");
        }else {
            return true;
        }
    }

    //把二维坐标转化为一维的索引
    private int getIndex(int row,int col){
        return row*size+col;
    }

}
