public class Test {
    public static void main(String[] args) {
        testAccession("FD_FREDG003055");
    }

    public static void testAccession(String id) {
        for (int i = 4000; i > 3800; i--) {
            System.out.println(fun(i));
        }

    }

    public static int fun(int n) {
        return (int)(1701 - 10*(Math.log(n) / Math.log(1.05)));
    }
}
