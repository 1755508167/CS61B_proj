public class Testlld {
    public static void main(String[] args) {
        LinkedListDeque<Integer> l = new LinkedListDeque<>();
        l.addLast(0);
        l.addFirst(1);
        l.addLast(2);
        System.out.println(l.getRecursive(2));
    }
}
