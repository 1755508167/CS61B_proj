import java.util.ArrayList;
import java.util.List;

/*
霍夫曼解码的过程
1.读取霍夫曼树
2.读取BitSequence
3.进行解码，把比特还原成字符

 */
public class HuffmanDecoder {
    public static void main(String[] args) {
        String fileName = "watermelonsugar.txt.huf";
        ObjectReader or = new ObjectReader(fileName);
        //读取霍夫曼树
        Object trie0 = or.readObject();
        //读取编码后的比特序列
        Object seq = or.readObject();
        BinaryTrie trie = (BinaryTrie) trie0;
        BitSequence sequence = (BitSequence) seq;
        //开始解码
        //这个列表用来存储解码出来的字符
        List<Character> list=new ArrayList<>();
        while (sequence.length() > 0) {
            Match match = trie.longestPrefixMatch(sequence);
            int length=match.getSequence().length();
            list.add(match.getSymbol());
            sequence=sequence.lastNBits(sequence.length()-length);
        }
        char[] ch=new char[list.size()];
        for (int i =0;i<list.size();i++){
            ch[i]=list.get(i);
        }
        FileUtils.writeCharArray("test.txt",ch);
        /*
        for (int i =0;i <list.size();i++){
            System.out.print(list.get(i));
        }

         */
    }
}
