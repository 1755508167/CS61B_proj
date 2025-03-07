public class TestArray {
    public static void main(String[] args) {
        ArrayDeque<Integer> l = new ArrayDeque<>();

        l.addFirst(3);
        l.addLast(4);
        l.addLast(5);
        l.addLast(6);
        l.addFirst(2);
        l.addFirst(1);
        l.addFirst(0);
        l.addFirst(-1);

        l.removeFirst();
        l.removeFirst();
        l.removeFirst();
        System.out.println(l.removeLast());
        System.out.println(l.size());
        l.printDeque();


    }
}