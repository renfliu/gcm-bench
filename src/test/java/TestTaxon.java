import me.renf.gcm.GenConfig;
import me.renf.gcm.ontology.TaxonName;
import me.renf.gcm.ontology.TaxonNode;

public class TestTaxon {
    public static void main(String[] args) throws Exception{
        //testTaxonName();
        testTaxon();
    }

    public static void testTaxonName() {
        TaxonName taxonName = new TaxonName();
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
