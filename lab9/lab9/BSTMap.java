package lab9;
import java.util.Iterator;
import java.util.Set;
//K是key的类型,V是value的类型
//Map 是一个把 key 映射到 value 的集合，key 不能重复，value 可以重复。
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    //定义一个节点类
    private class Node{
        private K key;
        private V value;
        private Node left;
        private Node right;

        private Node(K key,V value){
            this.key=key;
            this.value=value;
        }
    }
    //定义根节点
    private Node root;
    //用来存储键值对的数量
    private int count;

    //构造函数
    public BSTMap(){
        root=null;
    }
    //移除所有元素
    @Override
    public void clear() {
        root=null;
        count=0;
    }

    @Override
    public boolean containsKey(K key) {
        return search(root,key)!=null;
    }
    //定义一个search函数
    private Node search(Node node,K key){
        if (node==null){
            return null;
        }
        if (key.equals(node.key)){
            return node;
        }
        //如果key>node.key
        if (key.compareTo(node.key)>0){
            return search(node.right,key);
        } else {
            return search(node.left,key);
        }
    }

    @Override//返回key对应的value
    public V get(K key) {
        if (root==null){
            return null;
        }else {
            Node node=search(root,key);
            if (node==null){
                throw new NullPointerException("key doesn't exists.");
            }else {
                return node.value;
            }
        }

    }

    @Override//返回键值对的数量
    public int size() {
        return count;
    }

    //插入键值对
    @Override
    public void put(K key, V value) {
        root=insert(root,key,value);
    }

    //定义一个辅助函数
    private Node insert(Node node,K key,V value){
        if (node==null) {
            node = new Node(key, value);
            count++;
            return node;
        }
        //如果value>node.value
        if (key.compareTo(node.key)>0){
            node.right=insert(node.right,key,value);
        } else if (key.compareTo(node.key)<0) {
            node.left=insert(node.left,key,value);
        }//当value==node.value时不用插入
        return node;
    }

    @Override//返回一个集合 ，包含所有的key
    public Set<K> keySet() {
        throw new UnsupportedOperationException("Unsupported.");
    }

    @Override
    public V remove(K key) {
        Node node=search(root,key);
        if (node==null){
            throw new NullPointerException("key doesn't exist.");
        }
        root=delete(root,key,node.value);
        count--;
        return node.value;
    }

    @Override
    public V remove(K key, V value) {
        throw new  UnsupportedOperationException("doesn't finish it");
    }
    //定义一个辅助函数delete
    private Node delete(Node node,K key,V value){
        if (containsKey(key)){
            if (node==null){
                return null;
            }
            //value<node.value
            if (key.compareTo(node.key)<0){
                node.left=delete(node.left,key,value);
                count--;
            } else if (key.compareTo(node.key)>0) {
                node.right=delete(node.right,key,value);
            }else {//当value==node.value时，有三种情况
                //case1:当node没有子节点时
                if (node.left==null && node.right==null){
                }
                //case2:当node只有一个子节点时
                if (node.left==null){
                    count--;
                    return node.right;
                }
                if (node.right==null){
                    count--;
                    return node.left;
                }
                //case3:当node有两个节点
                Node minNode=findMin(node.right);//找到右子树的最小节点
                node.value=minNode.value;//用最小值来替换当前节点的值
                node.right = delete(node.right, minNode.key, minNode.value);//然后删除右子树中的最小节点
            }
            return node;
        }else {
            throw new RuntimeException("Key doesn't exists.");
        }
    }
    //找到一个树中的最小值
    private Node findMin(Node node){
        while (node.left!=null){
            node=node.left;
        }
        return node;
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("Unsupported.");
    }

    //按照key的递增顺序打印出值
    public void printInOrder() {
        inOrder(root);
        System.out.println();
    }
    //中序遍历
    private void inOrder(Node node){
        if (node==null){
            return;
        }
        //先遍历左子树
        inOrder(node.left);
        System.out.print(node.value+" ");
        //再遍历右子树
        inOrder(node.right);
    }
    //以树状结构打印二叉树
    public void printTree(){
        prettyPrint(root,"",true);
    }
    // 辅助函数：递归打印每一层
    // 辅助递归函数
    private void prettyPrint(Node node, String prefix, boolean isTail) {
        if (node == null) return;

        if (node.right != null) {
            prettyPrint(node.right, prefix + (isTail ? "│   " : "    "), false);
        }

        System.out.println(prefix + (isTail ? "└── " : "┌── ") + node.value);

        if (node.left != null) {
            prettyPrint(node.left, prefix + (isTail ? "    " : "│   "), true);
        }
    }
}
