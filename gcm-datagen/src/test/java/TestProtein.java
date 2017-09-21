import me.renf.gcm.generator.GenConfig;
import me.renf.gcm.generator.ontology.PdbNode;
import me.renf.gcm.generator.ontology.PfamNode;
import me.renf.gcm.generator.ontology.ProteinNode;

public class TestProtein {
    public static void main(String[] args) throws  Exception{
        testProtein();
        //testPdb();
        //testPfam();
    }

    public static void testProtein() throws Exception{
        String[] args = {"-o", "protein", "-n", "1000000"};
        GenConfig config = new GenConfig(args);
        ProteinNode protein = new ProteinNode(config);
        protein.generate();
    }

    public static void testPfam() {
        PfamNode node = new PfamNode();
        for (int i = 0; i < 100; i++){
            System.out.print(node.next());
        }
    }

    public static  void testPdb() {
        PdbNode node = new PdbNode();
        for (int i = 0; i < 100; i++) {
            System.out.print(node.next());
        }
    }
}
