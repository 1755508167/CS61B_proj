public class TestArray {
    public static void main(String[] args) {
        ArrayDeque<Integer> l = new ArrayDeque<>();
        l.addFirst(1);
        l.addLast(4);
        l.addLast(5);
        l.addLast(6);
        System.out.println(l.size());
        l.printDeque();
    }
}