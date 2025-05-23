import java.io.Serializable;//用于标记一个类的实例可以被序列化。
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class BinaryTrie implements Serializable {
    Node root;
    //定义一个节点类，作为霍夫曼树中的节点
    private class Node implements Comparable<Node>,Serializable{
        char ch;//储存的字符
        int freq;//这个字符的频率
        Node left;//左子节点
        Node right;//右子节点

        //判断这个节点是不是leaf
        boolean isleaf(){
            if (left == null && right == null){
                return true;
            }else {
                return false;
            }
        }
        //构造函数
        //这个构造函数用于将每个字符生成单个的节点
        Node(char ch,int freq){
            this.ch=ch;
            this.freq=freq;
        }
        //这个构造函数用于将单个的节点拼成一个树，这时一棵树的根节点不一定有ch
        //这时根节点的freq等于左右两个子节点的freq之和
        Node(Node left,Node right){
            this.left=left;
            this.right=right;
            this.freq=left.freq+ right.freq;
        }

        @Override
        public int compareTo(Node o) {
            return this.freq-o.freq;
        }
    }
    //构造函数接受一个频率表，用于创建一个霍夫曼树
    public BinaryTrie(Map<Character, Integer> frequencyTable){
        //创建一个优先队列，按照Node的freq的大小排序，小的在最前面
        PriorityQueue<Node> queue=new PriorityQueue<>();
        //对map中的每一个字符生成一个节点并放入队列中
        //一个entry包含一对键值对
        for (Map.Entry<Character,Integer> entry:frequencyTable.entrySet()){
            queue.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (queue.size() >1){
            Node left=queue.poll();
            Node right=queue.poll();
            queue.add(new Node(left,right));
        }
        //保存根节点
        root=queue.poll();
    }
    //此方法接受一个 BitSequence 对象，返回与之匹配的最长前缀及其对应的字符。
    public Match longestPrefixMatch(BitSequence querySequence){
        int index=0;//用来遍历到querySequence的哪一个位置
        Node current=root;//用来记录当前的节点
        while (!current.isleaf()){
            if (querySequence.bitAt(index)==0){
                current=current.left;
            }
            if (querySequence.bitAt(index)==1){
                current=current.right;
            }
            index++;
        }
        //获取匹配上的前缀序列
        BitSequence seq=querySequence.firstNBits(index);
        Match match=new Match(seq,current.ch);
        return match;
    }
    //生成一个查找表，将每个字符映射到其对应的霍夫曼编码
    //通过对树进行深度优先遍历(DFS)实现
    public Map<Character, BitSequence> buildLookupTable(){
        Map<Character,BitSequence> table=new HashMap<>();
        buildLookUpTableHelper(root,"",table);
        return table;
    }
    //辅助函数，使用递归
    private void buildLookUpTableHelper(Node node,String path,Map<Character,BitSequence> table){
        //递归终止条件
        if (node.isleaf()){
            BitSequence sequence=new BitSequence(path);
            table.put(node.ch,sequence);
            return;
        }
        //向左遍历
        buildLookUpTableHelper(node.left,path+"0",table);

        //向右遍历
        buildLookUpTableHelper(node.right,path+"1",table);
    }

    //霍夫曼树的可视化
    public void printTree() {
        printTreeHelper(root, "", true);
    }

    private void printTreeHelper(Node node, String prefix, boolean isTail) {
        if (node == null) return;

        String nodeLabel = node.isleaf() ? "'" + node.ch + "'" : "*";
        System.out.println(prefix + (isTail ? "└── " : "├── ") + nodeLabel + " (" + node.freq + ")");

        if (!node.isleaf()) {
            printTreeHelper(node.left, prefix + (isTail ? "    " : "│   "), false);
            printTreeHelper(node.right, prefix + (isTail ? "    " : "│   "), true);
        }
    }

    public static void main(String[] args){
        BitSequence sequence = new BitSequence("01010");
        System.out.println(sequence.bitAt(1));//返回的是int
        System.out.println(sequence.length());

        Map<Character, Integer> frequencyTable = new HashMap<Character, Integer>();
        frequencyTable.put('a', 1);
        frequencyTable.put('b', 2);
        frequencyTable.put('c', 4);
        frequencyTable.put('d', 5);
        frequencyTable.put('e', 6);
        BinaryTrie trie = new BinaryTrie(frequencyTable);
        trie.printTree();

    }
}
