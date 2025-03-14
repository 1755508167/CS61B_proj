public class LinkedListDeque<T> implements Deque<T>{
    //该类基于链表实现
    //add和remove操作不得涉及任何循环和递归，且单个操作必须为常数时间。
    /*
    内存使用量必须与队列元素数量成比例。
    例如，如果您向双端队列中添加 10,000 个项目，然后删除 9,999 个项目，则结果大小应该更接近于包含 1 个项目的双端队列，
    而不是 10,000 个。不要保留对双端队列中不再存在的项目的引用。
     */
    private int size;
    private DLList<T> l;

    //创建一个空的链表双端队列
    public LinkedListDeque() {
        l = new DLList<>();
    }

    //添加一个元素到最前端
    @Override
    public void addFirst(T item) {
        l.addFirst(item);
    }

    //添加一个元素到队列的最末尾
    @Override
    public void addLast(T item) {
        l.addLast(item);
    }

    //返回 deque 是否为空，为空则返回 true，否则返回 false。
    @Override
    public boolean isEmpty() {
        return l.isEmpty();
    }

    //返回这个双端队列的元素数量
    //size必须是常数时间
    @Override
    public int size() {
        return l.size();
    }

    //从第一个到最后一个打印 deque 中的项目，项目之间用空格分隔。
    @Override
    public void printDeque() {
        l.printList();
    }

    //移除并返回双端队列前端的项。如果不存在此类项，则返回 null
    @Override
    public T removeFirst() {
        return l.removeFirst();
    }

    //移除并返回双端队列末尾的元素。如果不存在此类元素，则返回 null。
    @Override
    public T removeLast() {
        return l.removeLast();
    }

    //获取给定索引处的项目，其中 0 是头部，1 是下一个项目，依此类推。如果不存在此类项目，则返回 null。不得修改双端队列！
    //get方法必须使用迭代
    @Override
    public T get(int index) {
        return l.get(index);
    }

    //与get方法一样的功能，但是使用递归实现

    public T getRecursive(int index) {
        return l.getRecursive(index);
    }
}
