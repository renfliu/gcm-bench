import me.renf.gcm.generator.GenConfig;
import me.renf.gcm.generator.ontology.EnzymeNode;
import me.renf.gcm.generator.random.EnzymeIDGenertor;

public class TestEnzyme {
    public static  void main(String[] args) throws Exception{
        testEnzyme();
        //testEnzymeIDGenerator();
    }

    static void testEnzyme() throws Exception{
        String[] args = {"-o", "enzyme", "-n", "1000000"};
        GenConfig config = new GenConfig(args);
        EnzymeNode enzyme = new EnzymeNode(config);
        enzyme.generate();
    }

    static void testEnzymeIDGenerator() {
        EnzymeIDGenertor genertor = new EnzymeIDGenertor(1385443);
        int[] m = genertor.max;
        for (int i : m) {
            System.out.println(i);
        }
    }
}
