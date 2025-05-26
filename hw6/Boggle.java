import java.util.*;

import edu.princeton.cs.algs4.In;

public class Boggle {
    // File path of dictionary file
    static String dictPath = "words.txt";
    private char[][] board;
    private boolean[][] visited;//标记已访问的格子
    Set<String> result=new HashSet<>();//存储找到的单词
    int width,height;//board的尺寸

    //读取文件，构建一个字母网格
    public Boggle(String boardPath) {
        In in = new In(boardPath);
        String[] strings = in.readAllLines();
        //System.out.println(width+","+height);
        this.width=strings[0].length();
        this.height=strings.length;//行数;
        //对board进行初始化
        board=new char[height][width];
        visited=new boolean[height][width];

        //把字符填进二维数组
        for (int i = 0; i < height; i++) {
            for (int j =0;j< width;j++) {
                board[i][j]=strings[i].charAt(j);
            }
        }
        /*
        for (char[] chars:board){
            for (char ch :chars){
                System.out.print(ch);
            }
            System.out.println();
        }

         */

    }

    /**
     * Solves a Boggle puzzle.
     *
     * @param k             The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     * The Strings are sorted in descending order of length.
     * If multiple words have the same length,
     * have them in ascending alphabetical order.
     */
    //按照单词的长度从高到低进行排序，返回k个最长的单词
    //如果单词长度一样，则按字母升序排列
    public static List<String> solve(int k, String boardFilePath) {
        // YOUR CODE HERE
        In in = new In(dictPath);
        //读取文件
        String[] strings = in.readAllLines();
        //创建前缀树
        Trie trie = new Trie();
        //往前缀树中插入单词
        for (String word : strings) {
            trie.insert(word.toLowerCase());
        }
        Boggle boggle=new Boggle(boardFilePath);

        for (int i=0;i< boggle.height;i++){
            for (int j=0;j<boggle.width;j++){
                boggle.dfs(i,j,trie,new StringBuilder());
            }
        }
        // 排序：先按长度降序，再按字母升序
        List<String> sorted = new ArrayList<>(boggle.result);
        sorted.sort((a, b) -> {
            if (b.length() != a.length())
                return b.length() - a.length(); // 长度降序
            return a.compareTo(b);              // 字典升序
        });
        System.out.println("sorted.length:"+boggle.result.size());

        return sorted.subList(0, Math.min(k, sorted.size()));

    }
    //深度优先搜索
    private void dfs(int i,int j,Trie trie,StringBuilder prefix){
        //判断是否坐标越界和是否被访问
        if (i < 0 || i >= height || j<0 || j < width || visited[i][j]){
            return;
        }
        char ch=board[i][j];
        prefix.append(ch);
        //转成字符串
        String word= prefix.toString().toLowerCase();
        //如果不是合法前缀
        if (!trie.startsWith(word)){
            prefix.deleteCharAt(prefix.length()-1);//删除刚才添加进去的字符
            return;
        }
        //判断是不是有效单词
        if (trie.search(word) && word.length() >= 3){
            result.add(word);
        }

        //把当前字符标记为已访问
        visited[i][j]=true;
        //朝八个方向递归
        for (int dx=-1;dx<=1;dx++){
            for (int dy=-1;dy<=1;dy++){
                //dx和dy不能为0
                if (dx != 0 || dy != 0){
                    dfs(dx+i,dy+j,trie,prefix);
                }
            }
        }
        visited[i][j]=false;
        prefix.deleteCharAt(prefix.length()-1);
    }

    public static void main(String[] args) {
        List<String> result=Boggle.solve(3,"exampleBoard.txt");
        System.out.println(result.toString());
    }
}
