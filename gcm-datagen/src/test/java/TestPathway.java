import me.renf.gcm.generator.GenConfig;
import me.renf.gcm.generator.ontology.PathwayNode;
import me.renf.gcm.generator.random.IDGenerator;
import me.renf.gcm.generator.random.NameGenerator;
import me.renf.gcm.generator.random.PathwayIDGenerator;
import me.renf.gcm.generator.random.RandomGenerator;

import java.io.IOException;

public class TestPathway {
    public static void main(String[] args) throws Exception{
        testGenerator();
        //testIdGenerator();
        //testNameGenerator();
        //testIDGenerator();
    }

    public static void testGenerator() throws Exception{
        String[] args = {"-o", "pathway", "-n", "1000000"};
        GenConfig config = new GenConfig(args);
        PathwayNode pathway = new PathwayNode(config);
        pathway.generate();
    }

    public static void testIdGenerator() throws Exception {
        RandomGenerator generator = new PathwayIDGenerator(10000);
        for (int i = 0; i < 1000; i++) {
            System.out.println(generator.next());
        }
    }

    public static void testNameGenerator() throws IOException {
        RandomGenerator generator = new NameGenerator();
        for (int i = 0; i < 100; i++) {
            System.out.println(generator.next());
        }
    }

    public static void testIDGenerator() throws Exception {
        RandomGenerator generator = new IDGenerator(8, 10000);
        for (int i = 0; i < 50; i++) {
            System.out.println(generator.next());
        }
    }
}
