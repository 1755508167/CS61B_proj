import java.util.*;
public class Test {
    public static void main(String[] args){
        Set<Integer> set=new HashSet<>();
        set.add(1);
        set.add(3);
        set.add(2);
        List<Integer> list=new ArrayList<>(set);
        for (int i:list){
            System.out.println(i);
        }
    }
}
