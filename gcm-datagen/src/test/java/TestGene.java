import me.renf.gcm.generator.GenConfig;
import me.renf.gcm.generator.ontology.GeneNode;
import me.renf.gcm.generator.ontology.GenomeNode;

public class TestGene {
    public static void main(String[] args) throws Exception{
        //testGenome();
        testGene();
    }

    public static void testGene() throws Exception {
        String[] args = {"-o", "enzyme", "-n", "1000000"};
        GenConfig config = new GenConfig(args);
        GeneNode gene = new GeneNode(config);
        gene.generate();
    }

    public static void testGenome() throws Exception{
        String[] args = {"-o", "enzyme", "-n", "1000000"};
        GenConfig config = new GenConfig(args);
        GenomeNode node = new GenomeNode(config);
        for (int i = 0; i < 100; i++) {
            System.out.print(node.next());
        }
    }
}
