import edu.princeton.cs.algs4.Queue;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     *
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /** Returns a random item from the given queue. */
    //从指定的队列中返回一个随机的元素
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param less      An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are less than the given pivot.
     * @param equal     An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are equal to the given pivot.
     * @param greater   An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are greater than the given pivot.
     */
    //接受一个未排序的队列，一个用于比较的基准元素，以及三个空队列
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {
        // Your code here!
        for (Item item : unsorted){
            //item > pivot
            if (item.compareTo(pivot)>0){
                greater.enqueue(item);
            } else if (item.compareTo(pivot)<0) {
                less.enqueue(item);
            }else {
                equal.enqueue(item);
            }
        }
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {
        // Your code here!
        //获取基准元素
        if (items.size()==1 || items.size()==0){
            return items;
        }
        Item pivot=getRandomItem(items);
        Queue<Item> less=new Queue<>();
        Queue<Item> greater=new Queue<>();
        Queue<Item> equal=new Queue<>();
        //调用partion
        partition(items,pivot,less,equal,greater);
        //递归调用
        less=quickSort(less);
        greater=quickSort(greater);

        Queue<Item> temp=catenate(less,equal);
        temp=catenate(temp,greater);

        return temp;
    }

    public static void main(String[] args){
        Queue<String> students = new Queue<String>();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");
        students.enqueue("Hank");
        students.enqueue("Norland");
        students.enqueue("Guass");

        Queue<String> less=new Queue<>();
        Queue<String> greater=new Queue<>();
        Queue<String> equal=new Queue<>();

        //partition(students,"Hank",less,equal,greater);

        //System.out.println(less.toString());
        //System.out.println(greater.toString());
        Queue<String> result=quickSort(students);
        System.out.println(result.toString());
    }
}
