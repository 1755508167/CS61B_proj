import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;
    int[][] edgeTo;//用来记录路径
    double[][] memo;//在递归版本中用作缓存
    double[][] distTo;//最小能量累计值

    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.width = this.picture.width();
        this.height = this.picture.height();
        edgeTo = new int[height][width];
        memo = new double[height][width];
        distTo = new double[height][width];
    }

    // current picture
    public Picture picture() {
        return new Picture(this.picture);
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    //计算位于x列y行的像素的能量
    public double energy(int x, int y) {
        if (x < 0 || x >= width) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (y < 0 || y >= height) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return calcX(x, y) + calcY(x, y);
    }

    //计算X方向上的能量
    private double calcX(int x, int y) {
        int left = modular(x - 1, width);
        int right = modular(x + 1, width);

        Color leftColor = picture.get(left, y);
        Color rightColor = picture.get(right, y);

        double Rx = leftColor.getRed() - rightColor.getRed();
        double Gx = leftColor.getGreen() - rightColor.getGreen();
        double Bx = leftColor.getBlue() - rightColor.getBlue();
        double xEnergy = Rx * Rx + Gx * Gx + Bx * Bx;

        return xEnergy;
    }

    //计算Y方向上的能量
    private double calcY(int x, int y) {
        int upper = modular(y - 1, height);
        int down = modular(y + 1, height);

        Color upColor = picture.get(x, upper);
        Color downColor = picture.get(x, down);

        double Ry = upColor.getRed() - downColor.getRed();
        double Gy = upColor.getGreen() - downColor.getGreen();
        double By = upColor.getBlue() - downColor.getBlue();
        double yEnergy = Ry * Ry + Gy * Gy + By * By;

        return yEnergy;
    }

    //辅助函数
    public int modular(int number, int modulus) {
        return (number + modulus) % modulus;
    }

    //计算终点为(x,y)的最小成本路径的成本
    //递归版本，可以计算出最小的能量，但是很难给出能量最小的路径
    public double minimumCost(int x, int y) {
        double result;
        if (x < 0 || x >= width) {
            return Double.MAX_VALUE;
        }
        if (memo[y][x] > 0) {
            return memo[y][x];
        }
        if (y == 0) {
            result = energy(x, y);
            memo[y][x] = result;//把计算结果写入缓存，避免重复计算
            return result;
        }
        // 递归获取上一行三个方向的最小能量
        double left = minimumCost(x - 1, y - 1);
        double mid = minimumCost(x, y - 1);
        double right = minimumCost(x + 1, y - 1);

        double minPrev = left;
        int minX = x - 1;
        if (mid < minPrev) {
            minPrev = mid;
            minX = x;
        }
        if (right < minPrev) {
            minPrev = right;
            minX = x + 1;
        }
        result = energy(x, y) + minPrev;
        memo[y][x] = result;

        return result;
    }

    //使用动态规划
    public double dynamicProgramming() {
        //初始化最顶上的一行
        for (int x = 0; x < width; x++) {
            distTo[0][x] = energy(x, 0);
        }
        //自顶向下开始计算
        for (int y = 1; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double minEnergy = distTo[y - 1][x];
                int prevX = x;
                //跟左上角的比较
                if (x > 0 && distTo[y - 1][x - 1] < minEnergy) {
                    minEnergy = distTo[y - 1][x - 1];
                    prevX = x - 1;
                }
                if (x < width - 1 && distTo[y - 1][x + 1] < minEnergy) {
                    minEnergy = distTo[y - 1][x + 1];
                    prevX = x + 1;
                }
                distTo[y][x] = energy(x, y) + minEnergy;//计算以(x,y)为终点的路径的最小能量值
                edgeTo[y][x] = prevX;
            }
        }
        return 0;
    }

    // sequence of indices for horizontal seam
    //寻找一条水平的最短路线，使得这条路线的像素的能量之和最小
    public int[] findHorizontalSeam() {
        return null;
    }

    // sequence of indices for vertical seam
    //寻找一条垂直的最短路线，返回一个长度为H的数组，其中x位置的元素是该图像第x行要移除的元素
    public int[] findVerticalSeam() {
        dynamicProgramming();
        int[] answer = new int[height];
        //寻找最后一行中能量最小的位置
        double minTotalEnergy = distTo[height - 1][0];
        int minIndex = 0;
        for (int i = 1; i < width; i++) {
            if (distTo[height - 1][i] < minTotalEnergy) {
                minIndex = i;
                minTotalEnergy = distTo[height-1][i];
            }
        }
        //回溯路径
        answer[height-1]=minIndex;
        for (int y = height - 1; y > 0; y--){
            answer[y-1]=edgeTo[y][answer[y]];
        }
        return answer;
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam.length != width) {
            throw new IllegalArgumentException();
        }

        SeamRemover.removeHorizontalSeam(this.picture, seam);
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
        if (seam.length != height){
            throw new IllegalArgumentException();
        }
        SeamRemover.removeHorizontalSeam(this.picture,seam);
    }

    public static void main(String[] args) {
        SeamCarver sc = new SeamCarver(new Picture("images/4x6.png"));
        int result = sc.modular(1, 5);

        for (int i = 0; i < sc.width; i++) {
            System.out.println(sc.minimumCost(i, 5));
        }

        System.out.println();

        sc.dynamicProgramming();
        for (double i : sc.distTo[sc.height() - 1]) {
            System.out.println(i);
        }
        int[] answer=sc.findVerticalSeam();
        for (int i :answer){
            System.out.print(i+"->");
        }
    }
}
