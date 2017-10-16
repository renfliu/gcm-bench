import java.util.Random;

public class Test {
    public static void main(String[] args) {
        testAccession("FD_FREDG003055");
    }

    public static void testAccession(String id) {
        Random rand = new Random();
        System.out.println(rand.nextInt(0));
    }

    public static int fun(int r) {
        return (int)(1701 - 10*(Math.log(r) / Math.log(1.05)));   //统计出的pathway出现概率的拟合曲线
    }

}
