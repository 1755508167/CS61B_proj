public class Test {
    public static void main(String[] args){
        IntList A=new IntList(1,new IntList(2,new IntList(3,null)));
        IntList B=new IntList(4,new IntList(5,new IntList(6,null)));
        IntList.dcatenate(A,B);
        System.out.println(A);
    }
}
