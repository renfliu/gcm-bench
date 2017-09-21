package me.renf.gcm.generator;

import me.renf.gcm.generator.exceptions.ArgumentException;

public class GenConfig {
    private String outFile;
    private long totalLines = 1000000L;
    private float enzymeRatio = 0.0117f;
    private long enzymeLines;
    private float pathwayRatio = 0.0287f;
    private long pathwayLines;
    private float taxonRatio = 0.0619f;
    private long taxonLines;
    private float proteinRatio = 0.0523f;
    private long proteinLines;
    private float geneRatio = 0.784f;
    private long geneLines;

    public GenConfig() {}

    public GenConfig(String[] args) throws ArgumentException{
        this.set(args);
    }

    public String getOutFile() {
        return outFile;
    }

    public long getEnzymeLines() {
        return enzymeLines;
    }

    public long getPathwayLines() {
        return pathwayLines;
    }

    public long getTaxonLines() {
        return taxonLines;
    }

    public long getProteinLines() {
        return proteinLines;
    }

    public long getGeneLines() {
        return geneLines;
    }

    public void set(String[] args) throws ArgumentException{
        for (int i = 0; i < args.length; i+=2) {
            if (args[i].equals("-o") || args[i].equals("--output")) {
                this.outFile = args[i+1];
            }
            else if (args[i].equals("-n") || args[i].equals("--lines")) {
                try {
                    totalLines = Long.parseLong(args[i + 1]);
                }catch (NumberFormatException ne) {
                    throw new ArgumentException(args[i] + " argument error！");
                }
            }
            else if (args[i].equals("--enzyme")) {
                enzymeRatio = parseRatio(args[i+1], args[i]);
            }
            else if (args[i].equals("--pathway")) {
                pathwayRatio = parseRatio(args[i+1], args[i]);
            }
            else if (args[i].equals("--taxonomy")) {
                taxonRatio = parseRatio(args[i+1], args[i]);
            }
            else if (args[i].equals("--protein")) {
                proteinRatio = parseRatio(args[i+1], args[i]);
            }
            else if (args[i].equals("--gene")) {
                geneRatio = parseRatio(args[i+1], args[i]);
            }
            else {
                throw new ArgumentException("the argument can't be recognized : " + args[i]);
            }
        }

        enzymeLines = (long)(totalLines*enzymeRatio);
        pathwayLines = (long)(totalLines*pathwayRatio);
        taxonLines = (long)(totalLines*taxonRatio);
        proteinLines = (long)(totalLines*proteinRatio);
        geneLines = (long)(totalLines*geneRatio);
    }

    private float parseRatio(String ratio, String name) throws ArgumentException {
        try {
            return Float.parseFloat(ratio);
        } catch (NumberFormatException ne) {
            throw new ArgumentException(name + " argument error！ " + ratio + " can't be resolved");
        }
    }

    @Override
    public String toString() {
        return "Generator Config:" +
                "\n\t--output: " + outFile +
                "\n\t--lines: " + totalLines +
                "\n\t--enzyme: " + enzymeRatio +
                "\n\t--pathway: " + pathwayRatio +
                "\n\t--taxonomy: " + taxonRatio +
                "\n\t--protein: " + proteinRatio +
                "\n\t--gene: " + geneRatio;
    }

}
