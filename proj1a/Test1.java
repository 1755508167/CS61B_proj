public class Test1 {

        public static void main(String[] args){
            DLList<String> l=new DLList();
            l.addLast("2");
            l.addLast("3");
            //long startTime = System.nanoTime(); // 获取开始时间
            l.addLast("4");
            //long endTime = System.nanoTime(); // 获取结束时间
            //long duration = endTime - startTime; // 计算运行时间（单位：
        l.addLast("5");
        l.addLast("6");
        l.addLast("7");
        //System.out.println(l.getFirst());
        //System.out.println(l.getLast());
        //System.out.println("size:"+l.size());
        //System.out.println(l.get(4));
        //System.out.println(l.getRecursive(3));
        //System.out.println(l.size());
        l.printList();
        }
}
