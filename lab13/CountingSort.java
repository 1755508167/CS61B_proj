/**
 * Class with 2 ways of doing Counting sort, one naive way and one "better" way
 *
 * @author Akhil Batra, Alexander Hwang
 **/
public class CountingSort {
    /**
     * Counting sort on the given int array. Returns a sorted version of the array.
     * Does not touch original array (non-destructive method).
     * DISCLAIMER: this method does not always work, find a case where it fails
     *
     * @param arr int array that will be sorted
     * @return the sorted array
     */
    public static int[] naiveCountingSort(int[] arr) {
        // find max,寻找arr中最大的数
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = Math.max(max, i);
        }

        // gather all the counts for each value
        //统计每个值的数量
        int[] counts = new int[max + 1];
        for (int i : arr) {
            counts[i]++;
        }

        // when we're dealing with ints, we can just put each value
        // count number of times into the new array
        //遍历 counts ，将各元素填入原数组 nums
        int[] sorted = new int[arr.length];
        int k = 0;
        for (int i = 0; i < counts.length; i += 1) {
            for (int j = 0; j < counts[i]; j += 1, k += 1) {
                sorted[k] = i;
            }
        }

        // however, below is a more proper, generalized implementation of
        // counting sort that uses start position calculation
        int[] starts = new int[max + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }

        int[] sorted2 = new int[arr.length];
        for (int i = 0; i < arr.length; i += 1) {
            int item = arr[i];
            int place = starts[item];
            sorted2[place] = item;
            starts[item] += 1;
        }

        // return the sorted array
        return sorted;
    }

    /**
     * Counting sort on the given int array, must work even with negative numbers.
     * Note, this code does not need to work for ranges of numbers greater
     * than 2 billion.
     * Does not touch original array (non-destructive method).
     *
     * @param arr int array that will be sorted
     */
    //这个方法可以处理复数
    public static int[] betterCountingSort(int[] arr) {
        // TODO make counting sort work with arrays containing negative numbers.
        //找出最小的数
        int min = arr[0];
        for (int num : arr) {
            if (num < min) {
                min = num;
            }
        }
        //对arr中的所有数进行平移，保证它们都是非负数
        //使用只能处理非负数的计数排序之后再平移回去
        int[] shiftarr=new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            shiftarr[i] = arr[i] - min;
        }
        //平移之后再找最大的数
        int max = 0;
        for (int num : shiftarr) {
            max = Math.max(max, num);
        }
        int[] counts = new int[max + 1];
        for (int num:shiftarr){
            counts[num]++;
        }
        //这是排序之后的数组
        int[] sorted = new int[arr.length];
        int k=0;
        for (int i =0;i< counts.length;i++){
            for (int j =0;j<counts[i];j++,k++){
                sorted[k]=i;
            }
        }
        //排序完成之后平移回去
        for (int i =0;i<sorted.length;i++){
            sorted[i]=sorted[i]+min;
        }
        //System.out.println("max:" + max);
        //System.out.println("min:" + min);

        return sorted;
    }

    public static void main(String[] args) {
        int[] array = new int[]{-3,-1, 1, 4, 3, 7, 5};

        int[] sorted=betterCountingSort(array);
        for (int i : sorted){
            System.out.println(i);
        }
    }
}
