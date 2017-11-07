public class Test {
    public static void main(String[] args) {
        testFun();
    }

    public static void testFun() {
        System.out.println(fun(0));
        System.out.println(fun(1));
        System.out.println(fun(4000));
    }

    public static int fun(int r) {
        return (int)(15 - 1.17*(Math.log(r) / Math.log(2)));
    }

}
