package hw2;
import edu.princeton.cs.introcs.StdRandom;

import java.util.ArrayList;

public class PercolationStats {
    private ArrayList<Double> threshold;
    private double rate;
    // perform T independent experiments on an N-by-N grid
    //执行T次独立的计算
    public PercolationStats(int N, int T, PercolationFactory pf){
        if (N <= 0 || T <= 0){
            throw new java.lang.IllegalArgumentException("N and T should not be negetive");
        }
        threshold=new ArrayList<>();

        for (int i =0;i<T;i++){
            Percolation p=pf.make(N);
            while (!p.percolates()){
                //随机生成坐标
                int randomRow=StdRandom.uniform(N);
                int randomCol=StdRandom.uniform(N);

                //打开这个节点
                p.open(randomRow,randomCol);
            }
            int openNums=p.numberOfOpenSites();
            //System.out.println("openNums:"+openNums);
            rate=(openNums*1.0)/(N*N);
            threshold.add(rate);

            System.out.println(rate);

        }


    }
    // sample mean of percolation threshold
    //计算渗透率的平均值
    public double mean(){
        double sum=0.0;
        for (double r : threshold){
            sum+=r;
        }
        return sum/threshold.size();
    }
    // sample standard deviation of percolation threshold
    //样本标准差
    public double stddev(){
        double mean = mean();
        double sum = 0.0;
        for (double r : threshold) {
            sum += (r - mean) * (r - mean);
        }
        return Math.sqrt(sum / (threshold.size() - 1));
    }
    // low endpoint of 95% confidence interval
    //置信区间的下界
    public double confidenceLow(){
        return mean() - (1.96 * stddev() / Math.sqrt(threshold.size()));
    }
    // high endpoint of 95% confidence interval
    //置信区间的上界
    public double confidenceHigh(){
        return mean() + (1.96 * stddev() / Math.sqrt(threshold.size()));
    }

    public static void main(String[] args){
        PercolationFactory pf=new PercolationFactory();
        PercolationStats p=new PercolationStats(20,70,pf);
        System.out.println("mean = " + p.mean());
        System.out.println("stddev = " + p.stddev());
        System.out.println("95% confidence interval = [" + p.confidenceLow() + ", " + p.confidenceHigh() + "]");
    }

}
