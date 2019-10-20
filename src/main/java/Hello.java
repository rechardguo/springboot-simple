import java.util.Random;

public class Hello {

    public static void main(String[]args){
        long result=0;
        while(true){
            int i=new Random(1000).nextInt(100);
            int j=new Random(2000).nextInt(200);
            result+=Calculate.sum(i,j);
            System.out.println(result);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Calculate{
        public static long sum(long i,long j){
            return i+j;
        }
    }

}
