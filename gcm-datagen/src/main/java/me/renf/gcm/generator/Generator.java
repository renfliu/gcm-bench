package me.renf.gcm.generator;

import me.renf.gcm.generator.exceptions.ArgumentException;
import me.renf.gcm.generator.ontology.*;
import me.renf.gcm.generator.output.DataWriter;
import me.renf.gcm.generator.output.DataWriterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;

public class Generator {
    final Logger logger = LoggerFactory.getLogger(Generator.class);

    public static void main(String[] args) {
        Generator gen = new Generator();
        try {
            if (args[0].equals("-h") || args[0].equals("--help")) {
                printUsage();
            }else {
                gen.setArgs(args);
                String r = gen.genData();
                System.out.println(r);
            }
        } catch (ArgumentException ae) {
            gen.logger.error(ae.getMessage());
            printUsage();
        } catch (Exception e) {
            gen.logger.error(e.getMessage());
            printUsage();
        }
    }

    private GenConfig config;

    public Generator(){
        config = new GenConfig();
    }

    public Generator(GenConfig conf) {
        config = conf;
    }

    public Generator(String[] args) throws ArgumentException{
        config = new GenConfig();
        if (args != null) config.set(args);
    }

    public static void printUsage() {
        System.out.println("GCMGenerator is a tool for generating 'Global Catalogue of Microorganisms' ");
        System.out.println("simulated data. You can use this to generate data from 1M to 100G and as more ");
        System.out.println("as your computer can hold. The Global Catalogue of Microorganisms is composed ");
        System.out.println("of XXX parts, so you can change the ratio of each part to generating different ");
        System.out.println("data according to your request. The ratios you need is given as parameters of ");
        System.out.println("the command, and you can find the usage below.");
        System.out.println("");
        System.out.println("OPTIONS:");
        System.out.println("");
        System.out.println("      -o, --output=FILE");
        System.out.println("            -o can be used to specify the file name FILE to save the data     ");
        System.out.println("            generated.");
        System.out.println("");
        System.out.println("      -n, --lines=NUM");
        System.out.println("            the program will generate 1,000,000 lines data as default, you can ");
        System.out.println("            use -n to generate NUM line data.");
        System.out.println("");
        System.out.println("      -h, --help");
        System.out.println("            show help. ");
        System.out.println("");
        System.out.println("      --enzyme=RATIO");
        System.out.println("            you can specify the ratio of enzyme in whole data, or 0.0324 as   ");
        System.out.println("            default.");
        System.out.println("");
        System.out.println("      --pathway=RATIO");
        System.out.println("            the pathway's ratio is specified as RATIO, or 0.0494 as default.");
        System.out.println("");
        System.out.println("      --taxonomy=RATIO");
        System.out.println("            the taxonomy's ratio is specified as RATIO, or 0.0719 as default.");
        System.out.println("");
        System.out.println("      --protein=RATIO");
        System.out.println("            the protein's ratio is specified as RATIO, or 0.0623 as default.");
        System.out.println("");
        System.out.println("      --gene=RATIO");
        System.out.println("            the gene's ratio is specified as RATIO, or 0.784 as default.");
        System.out.println("");
        System.out.println("      --genome=RATIO");
        System.out.println("            the genome's ratio is specified as RATIO, or 0.0595 as default.");
        System.out.println("");
    }

    public void setArgs(String[] args) throws ArgumentException {
        config.set(args);
    }

    public String genData() throws ArgumentException{
        try {
            DataWriter writer = DataWriterFactory.getWriter(config);

            // 写入owl数据
            FileReader reader = new FileReader(new File("res/gcm.n3"));
            char[] buffer = new char[2048];
            int len = -1;
            while ((len = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, len);
            }
            reader.close();

            // 写入pathway数据
            PathwayNode pathwayNode = new PathwayNode(config);
            pathwayNode.generate();

            // 写入obo数据

            // 写入enzyme数据
            EnzymeNode enzymeNode = new EnzymeNode(config);
            enzymeNode.generate();

            // 写入taxonomy数据
            TaxonNode taxonNode = new TaxonNode(config);
            taxonNode.generate();

            // 写入protein数据
            ProteinNode proteinNode = new ProteinNode(config);
            proteinNode.generate();

            // 写入gene数据
            GeneNode geneNode = new GeneNode(config);
            geneNode.generate();

            writer.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return "数据生成成功";
    }
}
