import java.util.*;

//霍夫曼编码的流程
/*
1.按8字节读取文件，因为一个字符就是8字节
2.统计每个字符出现的频率，生成一个频率表
3.利用这个频率表生成一个霍夫曼树
4.使用对象序列化把霍夫曼树写入文件
4.5记录一共写入了多少个字符，这样有助于解码
5.用霍夫曼树创建一个lookup table(查找表)，用于把字符转换成对应的编码
  创建一个列表来储存Sequence
6.创建一个BitSequence，用来保存字符编码后的数据
7.把所有BitSequence组装成一个大的Sequence
 */
//霍夫曼编码器
public class HuffmanEncoder {
    //接收一个字符数组，返回一个映射，把一个字符映射到它的在数组中的数量
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> map = new HashMap<>();
        Set<Character> set=new HashSet<>();
        for (char ch:inputSymbols){
            set.add(ch);
        }
        int count=0;
        for (char ch1:set){
            for (char ch2:inputSymbols){
                if (ch1 == ch2){
                    count++;
                }
            }
            map.put(ch1,count);
            count=0;
        }
        return map;
    }
    //main函数接收args[0]作为参数，对其进行霍夫曼编码，然后把编码的结果写入文件名为args[0]+".huf"
    public static void main(String[] args) {
        String fileName="watermelonsugar.txt";
        //读取文件，得到一个字符数组
        char[] ch= FileUtils.readFile(fileName);
        //统计此数组的字符数量，返回一个map
        Map<Character,Integer> map=buildFrequencyTable(ch);
        //生成一个霍夫曼树
        BinaryTrie binaryTrie=new BinaryTrie(map);
        //把霍夫曼树写入文件
        ObjectWriter ow=new ObjectWriter(fileName+".huf");
        ow.writeObject(binaryTrie);
        //创建一个查找表
        Map<Character,BitSequence> lookUpTable=binaryTrie.buildLookupTable();
        //创建一个BitSequence，把从文件中读取到的字符数组ch，按照这个查找表，把其对应的编码写入这个BitSequence
        ArrayList<BitSequence> list=new ArrayList<>();
        for (char c:ch){
            BitSequence code=lookUpTable.get(c);
            list.add(code);
        }
        //将每个字符编码得到的Sequence拼接成一个大的Sequence
        BitSequence sequence=BitSequence.assemble(list);
        //将这个Sequence写入文件
        ow.writeObject(sequence);


        /*
        System.out.println(ch.length);
        for (int i=0;i<100;i++){
            System.out.print(ch[i]);
        }

         */
    }
}
