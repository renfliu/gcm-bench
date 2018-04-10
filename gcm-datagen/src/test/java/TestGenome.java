import me.renf.gcm.generator.GenConfig;
import me.renf.gcm.generator.ontology.GenomeNode;

public class TestGenome {

    public static void main(String[] args) throws  Exception{
        testID();
    }

    public static void testID() throws Exception{
        String[] args = {"-o", "genome", "-n", "1000000"};
        GenConfig config = new GenConfig(args);
        GenomeNode genomeGenerator = new GenomeNode(config);
        for (int i = 0; i < 100; i++) {
            System.out.println(genomeGenerator.getID());
        }
    }
}
