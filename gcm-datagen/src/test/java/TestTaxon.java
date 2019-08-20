import me.renf.gcm.generator.GenConfig;
import me.renf.gcm.generator.ontology.TaxonName;
import me.renf.gcm.generator.ontology.TaxonNode;

public class TestTaxon {
    public static void main(String[] args) throws Exception{
        //testTaxonName();
        testTaxon();
    }

    public static void testTaxonName() throws  Exception{
        String[] args = {"-o", "taxon", "-n", "1000000"};
        GenConfig config = new GenConfig(args);
        TaxonName taxonName = new TaxonName(config);
        for (int i = 0; i< 10; i++) {
            System.out.print(taxonName.next());
        }
    }

    public static void testTaxon() throws Exception{
        String[] args = {"-o", "taxon", "-n", "1000000"};
        GenConfig config = new GenConfig(args);
        TaxonNode taxonNode = new TaxonNode(config);
        taxonNode.generate();
    }
}
