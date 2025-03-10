public class TestArray {
    public static void main(String[] args) {
        ArrayDeque<Integer> l = new ArrayDeque<>();
        /*
        l.addFirst(-1);
        l.addLast(0);
        l.addLast(1);
        l.addLast(2);
        l.addLast(3);
        l.addLast(4);
        l.addLast(5);
        l.addLast(6);
        l.addLast(7);
        l.addLast(8);

         */

        l.addFirst(0);
        l.removeLast();
        l.addFirst(2);
        l.removeLast();
        l.addFirst(4);
        l.removeLast();
        l.addLast(6);
        l.removeFirst();
        l.addLast(8);
        l.get(0);
        l.addFirst(10);
        l.addLast(11);
        l.addFirst(12);
        l.removeLast();
        l.addFirst(14);
        l.addFirst(15);
        l.addFirst(16);
        l.addFirst(17);
        l.addFirst(18);
        l.removeLast();
        l.addLast(20);
        l.addLast(21);
        System.out.println(l.get(5));
        System.out.println(l.size());
        //l.printDeque();
        System.out.println(l.get(0));

    }
}