package me.renf.gcm.generator.ontology;

import me.renf.gcm.generator.GenConfig;
import me.renf.gcm.generator.exceptions.WriterException;
import me.renf.gcm.generator.output.DataWriter;
import me.renf.gcm.generator.output.DataWriterFactory;
import me.renf.gcm.generator.random.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Random;

public class GeneNode implements NodeGenerator {

    private String[] dbPrefixs = {"Ensembl:ENSACAG", "dictyBase:DDB_G", "WormBase:WBGene", "miRBase:MI", "Pathema:EHI_",
            "FLYBASE:FBgn", "TAIR:AT", "CGNC:", "ZFIN:ZDB-GENE-", "NASONIABASE:NV", "SGD:S", "EcoGene:EG", "BEEBASE:GB",
            "BEETLEBASE:TC", "APHIDBASE:ACYPI", "VectorBase:ISCW", "Xenbase:XB-GENE-", "PseudoCap:PA", "ApiDB_CryptoDB:cgd6_"};
    private String[] geneTypes = {"rRNA", "scRNA", "snRNA", "snoRNA", "protein-coding", "pseudo", "ncRNA", "other",
            "tRNA", "miscRNA", "unknown"};
    //private String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
    //        "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private String[] nomeStatus = {"O", "O", "O", "O", "O", "O", "O", "O", "O", "I", "I", "I", "I"};
    private String[] nomeAuthPrefixs = {"Dere", "Dgri", "Dyak", "Dana", "Dvir", "Dmoj", "Dwil"};
    private String[] symbolPrefixs = {"ENSACAG", "DDB_G", "WBGene", "MI", "EHI_", "FBgn", "AT", "CGNC:", "ZDB-GENE-",
            "NV", "S", "EG", "GB", "TC", "ACYPI", "ISCW", "XB-GENE-", "PA", "cgd6_"};

    private Logger logger = LoggerFactory.getLogger(NodeGenerator.class);
    private GenConfig config;
    public final static double AVG_GENE_LINE = 13.6;
    private long nodes;
    private long geneId;
    private RandomGenerator idGenerator;
    private RandomGenerator stringGenerator = new StringGenerator();
    private TaxonIDGenerator taxonIDGenerator;
    private EnzymeIDGenertor enzymeIDGenertor;
    private PathwayIDGenerator pathwayIDGenerator;
    private ProteinIDGenerator proteinIDGenerator;
    private Random rand;
    private GenomeNode genomeGenerator;
    private GenomeNode genomeIDGenerator;


    public GeneNode(GenConfig config) {
        this.config = config;
        nodes = (long)(config.getGeneLines() / AVG_GENE_LINE);
        geneId = 1;
        idGenerator = new GeneIDGenerator();
        long taxonNodes = new TaxonNode(config).getNodes();
        taxonIDGenerator = new TaxonIDGenerator(taxonNodes);
        genomeGenerator = new GenomeNode(config);
        genomeIDGenerator = new GenomeNode(config);
        long enzymeNodes = new EnzymeNode(config).getNodes();
        enzymeIDGenertor = new EnzymeIDGenertor(enzymeNodes);
        long pathwayNodes = new PathwayNode(config).getNodes();
        long proteinNodes = new ProteinNode(config).getNodes();
        if (config.isSimpleID()) {
            pathwayIDGenerator = new PathwayIDGenerator(pathwayNodes, true);
            proteinIDGenerator = new ProteinIDGenerator(proteinNodes, true);
        }else {
            pathwayIDGenerator = new PathwayIDGenerator(pathwayNodes);
            proteinIDGenerator = new ProteinIDGenerator(proteinNodes);
        }

        rand = new Random(nodes);
    }

    public void generate() {
        try {
            DataWriter writer = DataWriterFactory.getWriter(config);
            for (long i = 0; i < nodes; i++) {
                String id = getID();
                writer.write(getEnzymeAxiom(id));
                //writer.write(getPathwayAxiom(id));
                writePathwayAxiom(id, writer);
                writer.write(getProteinAxiom(id));
                writer.write(getGenomeAxiom(id));
                writer.write(getTaxonAxiom(id)); //没出现
                writer.write(getChromosomeAxiom(id));
                writer.write(getDbXrefsAxiom(id));
                writer.write(getDescriptionAxiom(id));
                writer.write(getFullNameNomeAuthAxiom(id));
                writer.write(getGeneProductAxiom(id));
                writer.write(getGeneTypeAxiom(id));
                writer.write(getMapLocationAxiom(id));
                writer.write(getMdateAxiom(id));
                writer.write(getNomeStatus(id));
                writer.write(getNoteAxiom(id));
                writer.write(getOtherDesignationsAxiom(id));
                writer.write(getSbNomeAuthAxiom(id));
                writer.write(getSymbolAxiom(id));
                writer.write(getSynonymsAxiom(id));
                writer.write(getType(id));
                writer.write(getStartAndEndAxiom(id));
                writer.write(getGeneIdAxiom(id));
                writer.write(getLocusTagAxiom(id));
                writer.write(getOrientationAxiom(id));
                if (rand.nextInt(100) > 98) {
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
        // enzyme 的比例有点低
        int r = rand.nextInt(100);
        if (r < 2) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/x-enzyme>%s<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> .",
                    id, config.getSeparator(), config.getSeparator(), enzymeIDGenertor.random());
            return axiom + "\n";
        }
        return "";
    }

    private String getPathwayAxiom(String id) {
        // pathway 从1-18都有，比例很低
        int r = rand.nextInt(100);
        if (r > 98) {
            int n = rand.nextInt(5800);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < (18 - Math.pow(n, 1/3.0)); i++) {
                sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                        "gcmAnnotation/v1/x-pathway>%s<http://gcm.wdcm.org/data/gcmAnnotation1/pathway/%s> .",
                        id, config.getSeparator(), config.getSeparator(), pathwayIDGenerator.random()));
                sb.append("\n");
            }
            return sb.toString();
        }
        return "";
    }

    private void writePathwayAxiom(String id, DataWriter writer) throws IOException{
        // pathway 从1-18都有，比例很低
        int r = rand.nextInt(100);
        if (r > 98) {
            int n = rand.nextInt(5800);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < (18 - Math.pow(n, 1/3.0)); i++) {
                writer.write(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                        "gcmAnnotation/v1/x-pathway>%s<http://gcm.wdcm.org/data/gcmAnnotation1/pathway/%s> .\n",
                        id, config.getSeparator(), config.getSeparator(), pathwayIDGenerator.random()));
            }
        }
    }

    private String getProteinAxiom(String id) {
        // protein 的比例大概在1/4
        int r = rand.nextInt(100);
        if (r%4 == 0) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/x-protein>%s<http://gcm.wdcm.org/data/gcmAnnotation1/protein/%s> .",
                    id, config.getSeparator(), config.getSeparator(), proteinIDGenerator.random());
            return axiom + "\n";
        }
        return "";
    }

    private String getGenomeAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/x-genome>%s<http://gcm.wdcm.org/data/gcmAnnotation1/genome/%s> .",
                id, config.getSeparator(), config.getSeparator(), genomeIDGenerator.randomID());
        return axiom + "\n";
    }

    private String getTaxonAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/x-taxon>%s<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/%s> .",
                id, config.getSeparator(), config.getSeparator(), taxonIDGenerator.random());  // 增加一个随机值
        return axiom + "\n";
    }

    // 基因所在的染色体
    private String getChromosomeAxiom(String id) {
        int chromosomeId = rand.nextInt(30)+1;
        String chromosome = String.valueOf(chromosomeId);
        if (chromosomeId > 23) {
            chromosome =  "Un";
        }
        if (chromosomeId%2 == 0) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/chromosome>%s\"%s\" .", id, config.getSeparator(), config.getSeparator(), chromosome);
            return axiom + "\n";
        }
        return "";

    }

    private String getDbXrefsAxiom(String id) {
        // 1/25的比例
        int r = rand.nextInt(100);
        if (r % 4 == 0) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/dbXrefs>%s\"%s%10d\" .", id, config.getSeparator(), config.getSeparator(),
                    dbPrefixs[rand.nextInt(dbPrefixs.length)], rand.nextInt((int)nodes/10+1));
            return axiom + "\n";
        }
        return "";
    }

    private String getDescriptionAxiom(String id) {
        // 1/2 的比例
        int r = rand.nextInt(100);
        if (r%2 == 0) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/description>%s\"%s\" .", id, config.getSeparator(), config.getSeparator(),
                    stringGenerator.next());
            return axiom + "\n";
        }
        return "";
    }

    private String getFullNameNomeAuthAxiom(String id) {
        // 1/100 的比例
        int r = rand.nextInt(100);
        if (r == 1) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/fullNameNomeAuth>%s\"%s\" .", id, config.getSeparator(), config.getSeparator(),
                    stringGenerator.next());
            return axiom + "\n";
        }
        return "";
    }

    private String getGeneProductAxiom(String id) {
        // 1-18 都有，但是主要是1和2
        int r = rand.nextInt(100);
        for (int i = 0; i < r%2+1; i++) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/geneProduct>%s\"%s\" .", id, config.getSeparator(), config.getSeparator(),
                    stringGenerator.next());
            return axiom + "\n";
        }
        return "";
    }

    private String getGeneTypeAxiom(String id) {
        // 1/2 的比例
        int r = rand.nextInt(100);
        if (r%2 == 0) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/geneType>%s\"%s\" .", id, config.getSeparator(), config.getSeparator(),
                    geneTypes[r%geneTypes.length]);
            return axiom + "\n";
        }
        return "";
    }

    private String getMapLocationAxiom(String id) {
        String mapLocation = String.format("%d%c%d-%d%c%d", rand.nextInt(100), alphabet[rand.nextInt(6)],
                rand.nextInt(100),rand.nextInt(100), alphabet[rand.nextInt(6)], rand.nextInt(100));
        // 1/200 的比例
        int r = rand.nextInt(100);
        if (r == 2) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/mapLocation>%s\"%s\" .", id, config.getSeparator(), config.getSeparator(), mapLocation);
            return axiom + "\n";
        }
        return "";
    }

    private String getMdateAxiom(String id) {
        // 1/2 的比例
        int r = rand.nextInt(100);
        if (r%2 == 0) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/mdate>%s\"%s\" .", id,  config.getSeparator(), config.getSeparator(),
                    String.format("200%d-%02d-%02d", rand.nextInt(10), rand.nextInt(12)+1, rand.nextInt(30)+1));
            return axiom + "\n";
        }
        return "";
    }

    private String getNomeStatus(String id) {
        // 1/50 的比例
        int r = rand.nextInt(100);
        if (r%50 == 1) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/nomeStatus>%s\"%s\" .", id, config.getSeparator(), config.getSeparator(), nomeStatus[rand.nextInt(13)]);
            return axiom + "\n";
        }
        return "";
    }

    private String getNoteAxiom(String id) {
        // 1/250 的比例
        int r = rand.nextInt(250);
        if (r == 100) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/note>%s\"%s\" .", id, config.getSeparator(), config.getSeparator(), stringGenerator.next());
            return axiom + "\n";
        }
        return "";
    }

    private String getOtherDesignationsAxiom(String id) {
        // 14/250 的比例
        int r = rand.nextInt(260);
        if (r%19 == 1) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/otherDesignations>%s\"%s\" .", id, config.getSeparator(), config.getSeparator(),
                    stringGenerator);
            return axiom + "\n";
        }
        return "";
    }

    private String getSbNomeAuthAxiom(String id) {
        String sbNomeAuth = String.format("%sG%c%04d", nomeAuthPrefixs[rand.nextInt(nomeAuthPrefixs.length)], alphabet[rand.nextInt(alphabet.length)], rand.nextInt(100000));
        // 4/262 的比例
        int r = rand.nextInt(130);
        if (r%65 == 10) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/sbNomeAuth>%s\"%s\" .", id, config.getSeparator(), config.getSeparator(), sbNomeAuth);
            return axiom + "\n";
        }
        return "";
    }

    /**
     * 需要修改数字的大小
     * @param id
     * @return
     */
    private String getSymbolAxiom(String id) {
        // 1/2 的比例
        String symbol = String.format("%s%08d", symbolPrefixs[rand.nextInt(symbolPrefixs.length)], rand.nextInt(100000000));
        int r = rand.nextInt(100);
        if (r%2 == 1) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/symbol>%s\"%s\" .", id, config.getSeparator(), config.getSeparator(), symbol);
            return axiom + "\n";
        }
        return "";
    }

    private String getSynonymsAxiom(String id) {
        // 2/25 的比例
        String synonyms = String.format("%c%d%c%d.%d", alphabet[rand.nextInt(alphabet.length)], rand.nextInt(10),
                alphabet[rand.nextInt(alphabet.length)], rand.nextInt(1000), rand.nextInt(100));
        int r = rand.nextInt(80);
        if (r%10 == 1) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/synonyms>%s\"%s\" .", id, config.getSeparator(), config.getSeparator(), synonyms);
            return axiom + "\n";
        }
        return "";
    }

    private String getType(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://www.w3.org/1999/02/" +
                "22-rdf-syntax-ns#type>%s<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GeneNode> .",
                id, config.getSeparator(), config.getSeparator());
        return axiom + "\n";
    }

    private String getStartAndEndAxiom(String id) {
        int num = Integer.valueOf(id.replaceAll("\\D", ""));
        StringBuilder sb = new StringBuilder();
        int start = rand.nextInt(num/2);
        int end = start + rand.nextInt(num/2);
        sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/start>%s\"%d\" .", id, config.getSeparator(), config.getSeparator(), start));
        sb.append("\n");
        sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/end>%s\"%d\" .", id, config.getSeparator(), config.getSeparator(), end));
        sb.append("\n");
        return sb.toString();
    }

    private String getGeneIdAxiom(String id) {
        // 1/2 的比例
        int r = rand.nextInt(100);
        if (r%2 == 1) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/geneId>%s\"%d\" .", id, config.getSeparator(), config.getSeparator(), geneId++);
            return axiom + "\n";
        }
        return "";
    }

    private String getLocusTagAxiom(String id) {
        // 200/260 的比例
        int len = rand.nextInt(6)+1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(alphabet[rand.nextInt(alphabet.length)]);
        }
        String locustag = String.format("%s%d_%d", sb.toString(), rand.nextInt(10000), rand.nextInt(10000000));
        int r = rand.nextInt(260);
        if (r < 200) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/locusTag>%s\"%s\" .", id, config.getSeparator(), config.getSeparator(), locustag);
            return axiom + "\n";
        }
        return "";
    }

    private String getOrientationAxiom(String id) {
        String orientation = rand.nextInt(10) < 5? "desc" : "asc";
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s>%s<http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/orientation>%s\"%s\" .", id, config.getSeparator(), config.getSeparator(), orientation);
        return axiom + "\n";
    }

}
