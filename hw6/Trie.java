import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.Map;

public class Trie {
    private String[] strings;
    private Node root;

    private class Node{
        Map<Character,Node> children;
        boolean isEnd;
        //构造函数
        Node(){
            //初始化children
            children=new HashMap<>();
            isEnd=false;
        }
    }

    public Trie(){
        root=new Node();
    }

    //插入单词
    public void insert(String word){
        //把root的引用复制给另一个节点
        Node node=root;
        for (char ch:word.toCharArray()){
            //如果根节点的子节点中不包含这个字符
            if (!node.children.containsKey(ch)){
                node.children.put(ch,new Node());
            }
            //node始终为这个前缀树最底层的节点
            node=node.children.get(ch);
        }
        node.isEnd=true;
    }

    //搜索一个单词，检查是否存在
    public boolean search(String word){
        Node node=root;
        for (char ch:word.toCharArray()){
            if (!node.children.containsKey(ch)){
                return false;
            }
            node=node.children.get(ch);
        }
        return node.isEnd;
    }
    //检查前缀是否存在
    public boolean startsWith(String prefix){
        Node node=root;
        for (char ch:prefix.toCharArray()){
            if (!node.children.containsKey(ch)){
                return false;
            }
            node=node.children.get(ch);
        }
        return true;
    }
    // 可视化打印 Trie 树结构
    public void printTrie() {
        printTrieHelper(root, "", 0);
    }

    // 递归辅助函数
    private void printTrieHelper(Node node, String prefix, int depth) {
        for (Map.Entry<Character, Node> entry : node.children.entrySet()) {
            char ch = entry.getKey();
            Node child = entry.getValue();

            // 打印缩进 + 当前字符
            StringBuilder indent = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                indent.append("  "); // 每层两个空格缩进
            }

            System.out.println(indent + "|-- " + ch + (child.isEnd ? " (end)" : ""));
            printTrieHelper(child, prefix + ch, depth + 1);
        }
    }

    public static void main(String[] args){
        Trie trie = new Trie();
        trie.insert("apple");
        trie.insert("application");
        System.out.println(trie.search("apple"));   // 输出 true
        System.out.println(trie.search("app"));     // 输出 false
        System.out.println(trie.startsWith("app")); // 输出 true
        trie.insert("app");
        System.out.println(trie.search("app"));      // 输出 true
        //trie.printTrie();
    }

}
