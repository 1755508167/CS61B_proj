import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     * <p>
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable<Item>> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable<Item> q1Min = q1.peek();
            Comparable<Item> q2Min = q2.peek();
            if (q1Min.compareTo((Item) q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /**
     * Returns a queue of queues that each contain one item from items.
     */
    //接受 "(Alice" > "Vanessa" > "Ethan")，返回(("Alice") > ("Vanessa") > ("Ethan"))
    private static <Item extends Comparable<Item>> Queue<Queue<Item>>
    makeSingleItemQueues(Queue<Item> items) {
        Queue<Queue<Item>> result = new Queue<>();
        if (items == null) {
            throw new IllegalArgumentException("Input queue cannot be null");
        }

        for (Item item : items) {
            Queue<Item> temp = new Queue<>();
            temp.enqueue(item);
            result.enqueue(temp);
        }
        return result;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     * <p>
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return A Queue containing all of the q1 and q2 in sorted order, from least to
     * greatest.
     */
    private static <Item extends Comparable<Item>> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        // Your code here!
        Queue<Item> result = new Queue<>();
        while (!q1.isEmpty() || !q2.isEmpty()) {
            Item item = getMin(q1, q2);
            result.enqueue(item);
        }
        return result;
    }

    /**
     * Returns a Queue that contains the given items sorted from least to greatest.
     */
    //最小的在头部，可以最先出列；最大的在尾部，最后出列
    public static <Item extends Comparable<Item>> Queue<Item> mergeSort(
            Queue<Item> items) {
        if (items == null || items.size() <=1){
            return items;
        }

        //第一步：将每个元素放进一个单独的队列中
        Queue<Queue<Item>> queueOfQueues=makeSingleItemQueues(items);

        //第二步：两两合并,直到只剩一个队列
        while (queueOfQueues.size() >1){
            Queue<Item> q1=queueOfQueues.dequeue();
            Queue<Item> q2=queueOfQueues.dequeue();
            Queue<Item> merged=mergeSortedQueues(q1,q2);
            queueOfQueues.enqueue(merged);
        }

        return queueOfQueues.dequeue();
    }

    public static void main(String[] args) {
        Queue<String> students = new Queue<String>();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");
        Queue<String> result = MergeSort.mergeSort(students);
        //System.out.println(result.toString());

        Queue<String> students2 = new Queue<String>();
        students2.enqueue("Hank");
        students2.enqueue("Norland");
        //System.out.println(makeSingleItemQueues(students).toString());
        //System.out.println(mergeSortedQueues(students, students2).toString());

        System.out.println(mergeSort(students).toString());
    }
}
