package me.renf.gcm.generator.ontology;

import me.renf.gcm.generator.GenConfig;
import me.renf.gcm.generator.exceptions.WriterException;
import me.renf.gcm.generator.output.DataWriter;
import me.renf.gcm.generator.output.DataWriterFactory;
import me.renf.gcm.generator.random.IDGenerator;
import me.renf.gcm.generator.random.NameGenerator;
import me.renf.gcm.generator.random.RandomGenerator;
import me.renf.gcm.generator.random.TaxonIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Random;

public class GeneNode implements NodeGenerator {
    private Logger logger = LoggerFactory.getLogger(NodeGenerator.class);
    private GenConfig config;
    private final int AVG_GENE_LINE = 7;
    private long nodes;
    private RandomGenerator idGenerator;
    private RandomGenerator nameGenerator = new NameGenerator();
    private TaxonIDGenerator taxonIDGenerator = new TaxonIDGenerator();
    private Random rand = new Random();
    private GenomeNode genomeGenerator;



    public GeneNode(GenConfig config) {
        this.config = config;
        nodes = config.getGeneLines() / AVG_GENE_LINE;
        idGenerator = new IDGenerator(8, (int)nodes);
        genomeGenerator = new GenomeNode(config);
    }

    public void generate() {
        try {
            DataWriter writer = DataWriterFactory.getWriter(config);
            for (long i = 0; i < nodes; i++) {
                String id = getID();
                //writer.write(getEnzymeAxiom(id));
                //writer.write(getPathwayAxiom(id));
                //writer.write(getProteinAxiom(id));
                writer.write(getGenomeAxiom(id));
                writer.write(getTaxonAxiom(id));
                //writer.write(getChromosomeAxiom(id));
                //writer.write(getDbXrefsAxiom(id));
                //writer.write(getDescriptionAxiom(id));
                //writer.write(getFullNameNomeAuthAxiom(id));
                //writer.write(getGeneProductAxiom(id));
                //writer.write(getGeneTypeAxiom(id));
                //writer.write(getMapLocationAxiom(id));
                //writer.write(getMdateAxiom(id));
                //writer.write(getNomeStatus(id));
                //writer.write(getNoteAxiom(id));
                //writer.write(getOtherDesignationsAxiom(id));
                //writer.write(getSbNomeAuthAxiom(id));
                //writer.write(getSymbolAxiom(id));
                //writer.write(getSynonymsAxiom(id));
                writer.write(getType(id));
                writer.write(getStartAndEndAxiom(id));
                //writer.write(getGeneIdAxiom(id));
                writer.write(getLocusTagAxiom(id));
                writer.write(getOrientationAxiom(id));
                if (rand.nextInt(100) > 98) {
                    //writer.write(genomeGenerator.next());
                    genomeGenerator.writeNext();
                }
            }
        } catch (WriterException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String getID() {
        return idGenerator.next();
    }

    private String getEnzymeAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/x-enzyme> <http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> .", id, nameGenerator.next());
        return axiom + "\n";
    }

    private String getPathwayAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/x-pathway> <http://gcm.wdcm.org/data/gcmAnnotation1/pathway/%s> .", id, nameGenerator.next());
        return axiom + "\n";
    }

    private String getProteinAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/x-protein> <http://gcm.wdcm.org/data/gcmAnnotation1/protein/%s> .", id, nameGenerator.next());
        return axiom + "\n";
    }

    private String getGenomeAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/x-genome> <http://gcm.wdcm.org/data/gcmAnnotation1/genome/%s> .", id, genomeGenerator.getID());
        return axiom + "\n";
    }

    private String getTaxonAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/x-taxon> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/%s> .", id, taxonIDGenerator.next());
        return axiom + "\n";
    }

    private String getChromosomeAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/chromosome> \"%s\" .", id, nameGenerator.next());
        return axiom + "\n";
    }

    private String getDbXrefsAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/dbXrefs> \"%s\" .", id, nameGenerator.next());
        return axiom + "\n";
    }

    private String getDescriptionAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/description> \"%s\" .", id, nameGenerator.next());
        return axiom + "\n";
    }

    private String getFullNameNomeAuthAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/fullNameNomeAuth> \"%s\" .", id, nameGenerator.next());
        return axiom + "\n";
    }

    private String getGeneProductAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/geneProduct> \"%s\" .", id, nameGenerator.next());
        return axiom + "\n";
    }

    private String getGeneTypeAxiom(String id) {
        String[] types = {"rRNA", "scRNA", "snRNA", "snoRNA", "protein-coding", "pseudo", "ncRNA", "other", "tRNA", "miscRNA", "unknown"};
        int r = rand.nextInt(100);
        if (r > 65) {
            return "";
        }else {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/geneType> \"%s\" .", id, types[r%types.length]);
            return axiom + "\n";
        }

    }

    private String getMapLocationAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/mapLocation> \"%s\" .", id, nameGenerator.next());
        return axiom + "\n";
    }

    private String getMdateAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/mdate> \"%s\" .", id, nameGenerator.next());
        return axiom + "\n";
    }

    private String getNomeStatus(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/nomeStatus> \"%s\" .", id, nameGenerator.next());
        return axiom + "\n";
    }

    private String getNoteAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/note> \"%s\" .", id, nameGenerator.next());
        return axiom + "\n";
    }

    private String getOtherDesignationsAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/otherDesignations> \"%s\" .", id, nameGenerator);
        return axiom + "\n";
    }

    private String getSbNomeAuthAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/sbNomeAuth> \"%s\" .", id, nameGenerator.next());
        return axiom + "\n";
    }

    private String getSymbolAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/symbol> \"%s\" .", id, nameGenerator.next());
        return axiom + "\n";
    }

    private String getSynonymsAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/synonyms> \"%s\" .", id, nameGenerator.next());
        return axiom + "\n";
    }

    private String getType(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://www.w3.org/1999/02/" +
                "22-rdf-syntax-ns#type> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GeneNode> .", id);
        return axiom + "\n";
    }

    private String getStartAndEndAxiom(String id) {
        int num = Integer.valueOf(id.replaceAll("\\D", ""));
        StringBuilder sb = new StringBuilder();
        int start = rand.nextInt(num/2);
        int end = start + rand.nextInt(num/2);
        sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/start> \"%d\" .", id, start));
        sb.append("\n");
        sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/end> \"%d\" .", id, end));
        sb.append("\n");
        return sb.toString();
    }

    private String getGeneIdAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/geneId> \"%s\" .", id, nameGenerator.next());
        return axiom + "\n";
    }

    private String getLocusTagAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/locusTag> \"%s\" .", id, nameGenerator.next());
        return axiom + "\n";
    }

    private String getOrientationAxiom(String id) {
        String orientation = rand.nextInt(10) < 5? "desc" : "asc";
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/orientation> \"%s\" .", id, orientation);
        return axiom + "\n";
    }

}
