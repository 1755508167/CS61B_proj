public class ArrayDeque<T> {
    //使用数组作为核心数据结构，利用循环数组实现
    //get和size方法必须在常数时间内完成
    private int size; //已有的元素数量
    private T[] data;
    private int first; //指向第一个元素，
    private int last; //指向最后一个元素
    private int capacity = 8; //初始化的容量为8
    private double usage; //使用率，不得低于25%

    //构造函数，创建一个空的ArrayList
    public ArrayDeque() {
        //this.capacity=capacity;
        this.data = (T[]) new Object[this.capacity];
        this.first = 0;  //索引为0,直接在index为0的地方插入元素
        this.last = 0;
        this.size = 0;
    }

    //在首端添加一个元素
    public void addFirst(T item) {
        if (size >= capacity) {
            System.out.println("Deque is full.Then I will enlarge the capacity.");
            //resize();  //扩大队列的容量
        } else {
            //先把指针往前移，再插入元素
            first = (first - 1 + capacity) % capacity; //把first向前移动一位，同时保证first在[0,capacity-1]范围内
            data[first] = item;
        }
        size++;
    }

    //在末端添加一个元素
    public void addLast(T item) {
        if (size >= capacity) {
            System.out.println("Deque is full.Then I will enlarge the capacity.");
            resize(capacity*2);
        }
        data[last] = item; //先插入元素
        last = (last + 1 +capacity) % capacity;
        size++;
    }

    //删除第一个元素
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T element = data[first];  //获取第一个元素
        data[first] = null;
        first = (first + 1 + capacity) % capacity;  //first往后移
        size--;
        return element;
    }

    //删除最后一个元素
    public T removeLast() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        last = (last - 1 + capacity) % capacity;
        T element = data[last];
        data[last] = null;  //把最后一个元素设置为null
        size--;
        return element;
    }

    //获取第i个元素
    public T get(int i) {
        if (i < 0 || i >= size) {
            //throw new IndexOutOfBoundsException("Index out of bounds: " + i);
            return null;
        }
        int actualIndex = (first + i  + capacity) % capacity; //获取第i个元素的实际索引
        T element = data[actualIndex];
        return element;
    }

    //判断是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    //返回元素数量
    public int size() {
        return size;
    }

    //扩大队列的容量

    private void resize(int newcapacity){
        //capacity=capacity*2;
        T[] newdata=(T[]) new Object[newcapacity];
        for (int i = 0; i < size; i++) {
            int oldindex=((first+i)%capacity);
            newdata[i]=data[oldindex];
        }
        //System.arraycopy(data,0,a,0, data.length);
        data=newdata;
        first=0;
        last=size;
        capacity=newcapacity;
    }

    //打印队列
    public void printDeque() {
        for (int i = first + 1; i != last; i = (i + 1) % data.length) {
            System.out.print(data[i] + " ");
        }
    }
}
