import java.util.Comparator;
import java.util.PriorityQueue;

public class TestComparator {
    public static class Node{
        int priority;
        public Node(int priority){
            this.priority=priority;
        }
    }
    public static class NewComparator implements Comparator<Node>{
        @Override
        public int compare(Node v1,Node v2){
            return -(v2.priority-v1.priority);
        }
    }

    
    public static void main(String[] args){
        NewComparator comparator=new NewComparator();
        PriorityQueue<Node> nodes=new PriorityQueue<>(comparator);
        Node n1=new Node(1);
        Node n2=new Node(2);
        nodes.add(n2);
        nodes.add(n1);
        System.out.println(nodes.poll().priority);
        System.out.println((int)0.5);
    }
}
