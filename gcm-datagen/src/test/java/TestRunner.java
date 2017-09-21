import me.renf.gcm.generator.GenConfig;

public class TestRunner {
    public static void main(String[] args) throws  Exception{
        testGenerate();
    }

    public static void testGenerate() throws Exception{
        String[] args = {"-o", "pathway", "-n", "1000000"};
        GenConfig config = new GenConfig(args);

    }
}
