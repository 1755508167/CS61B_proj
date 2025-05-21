import java.util.Arrays;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     * @return String[] the sorted array
     */
    //Least Significant Digit radix sort，就是从最低位(个位)开始排序
    //Most Significant Digit，就是从最高位开始排序
    //这个方法是非破坏性的
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        //最长字符串的长度
        int maxLength=0;
        for (String s : asciis) {
            maxLength = Math.max(maxLength, s.length());
        }

        String[] result=new String[asciis.length];
        result=asciis.clone();
        for (int i=0;i<maxLength;i++){
            result=stableSortOnChar(result,i);
        }

        return result;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     *
     * @param asciis Input array of Strings
     * @param index  The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        String[] temp;
        return;
    }

    //计数排序，按照某一个字符进行排序
    private static String[] stableSortOnChar(String[] strings, int index) {
        int[] counts = new int[256];
        String[] aux=new String[strings.length];// 辅助数组，用于存放排好序的字符串
        //统计各个字母出现的次数
        for (String string : strings) {
            //将字符转换为数字
            int number = (int) digit(string,index);
            counts[number]++;
        }
        //构建起始位置数组
        int[] starts=new int[256];
        for (int i=1;i<256;i++){
            starts[i]=starts[i-1]+counts[i-1];
        }//starts[i] 表示字符 i 在结果数组中的起始索引。

        //将字符串按字符放入正确位置
        for (String string:strings){
            int c=(int) digit(string,index);
            aux[starts[c]]=string;// 根据起始位置放入字符串
            starts[c]++;//起始位置后移，确保下个相同字符放在下一个位置
        }
        return aux;
    }

    //获取一个字符串的第index位,最右侧是第0位
    private static char digit(String string, int index) {
        int order=string.length() - index - 1;
        if (order < 0 ){
            return 0;
        }
        return string.charAt(order);
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start  int for where to start sorting in this method (includes String at start)
     * @param end    int for where to end sorting in this method (does not include String at end)
     * @param index  the index of the character the method is currently sorting on
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

    public static void main(String[] args) {
        String[] strings = new String[]{"ab","abe", "cf", "ad", "da", "ce"};
        String[] result = sort(strings);

        System.out.println(RadixSort.digit("abc", 0));
        int i = (int) "Apple".charAt(0);
        char a = (char) 97;
        //System.out.println(i);

        /*
        String[] result2= RadixSort.stableSortOnChar(strings, 0);
        String[] result3=RadixSort.stableSortOnChar(result2,1);
        System.out.println(result2);
        for (String string:result2){
            System.out.println(string);
        }
        System.out.println();
        for (String string:result3){
            System.out.println(string);
        }

         */
        String[] result2=sort(strings);
        for (String s:result2){
            System.out.println(s);
        }

    }
}
