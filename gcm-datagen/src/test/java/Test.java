import java.util.Random;

public class Test {
    public static void main(String[] args) {
        testRandom();
    }

    public static void testFun() {
        System.out.println(fun(0));
        System.out.println(fun(1));
        System.out.println(fun(4000));
    }


    public static int fun(int r) {
        return (int)(15 - 1.17*(Math.log(r) / Math.log(2)));
    }

    public static void testRandom() {
        long mstart = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            Math.random();
        }
        System.out.println("Math.random: " + (System.currentTimeMillis() - mstart));
        Random rand = new Random();
        long rstart = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            rand.nextInt();
        }
        System.out.println("Random: " + (System.currentTimeMillis() - rstart));
    }

}
