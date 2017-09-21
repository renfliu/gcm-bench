import java.util.Random;

public class Test {
    public static void main(String[] args) {
        testAccession("FD_FREDG003055");
    }

    public static void testAccession(String id) {
        Random rand = new Random();
        int r;
        //System.out.println(fun(4000));
        for (int i = 0; i < 100; i++) {
             r = rand.nextInt(4000)+1;
             //System.out.println(fun(i));
        }
        for (int i = 4000; i > 3900; i--) {
            System.out.println(fun(i));
        }
    }

    public static int fun(int r) {
        return (int)(1701 - 10*(Math.log(r) / Math.log(1.05)));   //统计出的pathway出现概率的拟合曲线
    }

}
