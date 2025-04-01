package byog.lab5;

import java.util.Random;

public class TestRandom {
    public static void main(String[] args){
        Random random=new Random(100);
        for (int i =0;i<10;i++){
            System.out.println(random.nextInt());
        }

    }
}
