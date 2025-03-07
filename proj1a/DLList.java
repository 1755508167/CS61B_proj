public class DLList<T> {
    private int size;
    private Node sentinel; //哨兵节点
    private Node first; //指向第一个元素的指针
    private Node last; //指向最后一个元素的指针

    //定义一个节点类
    public class Node {
        private Node prev;  //指向前一个节点的指针
        private Node next;  //指向后一个节点的指针
        private T item;  //包含的数据

        //构造函数，初始化一个节点
        public Node(Node prev, T item, Node next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    //创建一个空的双链表
    public DLList() {
        sentinel = new Node(null, null, null);  //prev指向最后一个元素
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    //创建一个非空的双向链表
    public DLList(T x) {
        sentinel = new Node(null, null, null);  //prev指向最后一个元素
        sentinel.next = new Node(sentinel, x, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    //返回链表的size
    public int size() {
        return size;
    }

    //添加一个元素到链表的首端
    public void addFirst(T x) {
        Node newNode = new Node(sentinel, x, sentinel.next);
        //不用改变sentinel的prev指针
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size = size + 1;
    }

    //添加一个元素到链表的末端
    public void addLast(T x) {
        Node newNode = new Node(sentinel.prev, x, sentinel);  //环形链表没有设置last指针，sentinel.prev就是指向最后一个节点
        sentinel.prev.next = newNode;  //把之前的最后一个节点指向newNode
        sentinel.prev = newNode;  //把prev指针更新到最后一个节点上
        size = size + 1;
    }

    //获取首端的元素
    public T getFirst() {
        //要判断链表是否为空
        if (sentinel.next == null) {
            return null;
        }
        return sentinel.next.item;
    }

    //获取末端的元素
    public T getLast() {
        return sentinel.prev.item;
    }

    //获取某个特定位置i的元素，使用迭代
    public T get(int i) {
        Node current = sentinel;
        for (int j = 0; j <= i; j++) {
            current = current.next;
        }
        return current.item;
    }

    //获取某个特定位置i的元素，使用递归
    public T getRecursiveHelper(Node node, int i) {
        if (i == 0) {
            return node.item;
        }
        return getRecursiveHelper(node.next, i - 1);
    }

    public T getRecursive(int i) {
        return getRecursiveHelper(sentinel.next, i);
    }

    //删除最后一个节点的元素，并返回值
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node lastNode = sentinel.prev;
        sentinel.prev = lastNode.prev; //把sentinel的prev指向倒数第二个节点
        lastNode.prev.next = sentinel; //把倒数第二个节点指向sentinel
        size = size - 1;
        return lastNode.item;
    }

    //删除第一个节点的元素，并返回值
    public T removeFirst() {
        if (sentinel.prev == sentinel) {
            return null;
        }
        Node firstNode = sentinel.next;  //这是第一个节点
        firstNode.next.prev = sentinel;  //第二个节点指向sentinel
        sentinel.next = firstNode.next;  //第二个节点变成第一个节点
        size = size - 1;
        return firstNode.item;
    }

    //判断这个链表是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    //打印链表
    public void printList() {
        if (isEmpty()) {
            System.out.println();  //如果为空，直接打印换行
        } else {
            Node current = sentinel.next;
            for (int i = 0; i < size; i++) {
                System.out.print(current.item);
                current = current.next;

                if (i < size - 1) {
                    System.out.print(" ");  //输出空格
                }
            }
        }

    }
}
