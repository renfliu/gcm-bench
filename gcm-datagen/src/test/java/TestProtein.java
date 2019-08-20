import me.renf.gcm.generator.GenConfig;
import me.renf.gcm.generator.ontology.PdbNode;
import me.renf.gcm.generator.ontology.PfamNode;
import me.renf.gcm.generator.ontology.ProteinNode;
import me.renf.gcm.generator.random.ProteinIDGenerator;

public class TestProtein {
    public static void main(String[] args) throws  Exception{
        //testProtein();
        //testPdb();
        //testPfam();
        testProteinIDGenerator();
    }

    public static void testProteinIDGenerator() throws Exception {
        ProteinIDGenerator generator = new ProteinIDGenerator(10000L);
        for (int i = 0; i < 100; i++) {
            System.out.println(generator.random());
        }
    }

    public static void testProtein() throws Exception{
        String[] args = {"-o", "protein", "-n", "1000000"};
        GenConfig config = new GenConfig(args);
        ProteinNode protein = new ProteinNode(config);
        protein.generate();
    }

}
