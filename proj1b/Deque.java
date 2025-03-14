public interface Deque<T> {
    void addFirst(T item);
    void addLast(T item);
    T removeFirst();
    T removeLast();
    T get(int i);
    default boolean isEmpty(){
        return size()==0;
    }
    int size();
    void printDeque();
}
