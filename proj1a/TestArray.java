public class TestArray {
    public static void main(String[] args) {
        ArrayDeque<Integer> l = new ArrayDeque<>();

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
        /*
        l.addFirst(2);
        l.addFirst(1);
        l.addFirst(0);
        l.addFirst(-1);
         */
        System.out.println(l.size());
        //l.printDeque();
        System.out.println(l.get(0));


    }
}