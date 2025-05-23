package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int[] bucketSizes = new int[M];
        int N = oomages.size();

        // 第一步：把每个 Oomage 按照哈希值分配到对应桶中
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            bucketSizes[bucketNum]++;
        }

        // 第二步：检查每个桶的元素数量是否合理
        for (int count : bucketSizes) {
            if (count < N / 50 || count > N / 2.5) {
                return false;
            }
        }

        return true;
    }
}
