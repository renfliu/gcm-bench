package me.renf.gcm.generator;

import me.renf.gcm.generator.exceptions.ArgumentException;

public class GenConfig {
    private String outFile;
    private long totalLines = 1000000L;
    private float enzymeRatio = 0.0324f;
    private long enzymeLines;
    private float pathwayRatio = 0.0494f;
    private long pathwayLines;
    private float taxonRatio = 0.0719f;
    private long taxonLines;
    private float proteinRatio = 0.0623f;
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
        if (enzymeLines == 0) {
            enzymeLines = (long)(totalLines*enzymeRatio);
        }
        return enzymeLines;
    }

    public long getPathwayLines() {
        if (pathwayLines == 0) {
            pathwayLines = (long)(totalLines*pathwayRatio);
        }
        return pathwayLines;
    }

    public long getTaxonLines() {
        if (taxonLines == 0) {
            taxonLines = (long)(totalLines*taxonRatio);
        }
        return taxonLines;
    }

    public long getProteinLines() {
        if (proteinLines == 0) {
            proteinLines = (long)(totalLines*proteinRatio);
        }
        return proteinLines;
    }

    public long getGeneLines() {
        if (geneLines == 0) {
            geneLines = (long)(totalLines*geneRatio);
        }
        return geneLines;
    }

    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

    public void setTotalLines(long totalLines) {
        this.totalLines = totalLines;
    }

    public void setEnzymeRatio(float enzymeRatio) {
        this.enzymeRatio = enzymeRatio;
        enzymeLines = (long)(totalLines*enzymeRatio);
    }

    public void setPathwayRatio(float pathwayRatio) {
        this.pathwayRatio = pathwayRatio;
        pathwayLines = (long)(totalLines*pathwayRatio);
    }

    public void setTaxonRatio(float taxonRatio) {
        this.taxonRatio = taxonRatio;
        taxonLines = (long)(totalLines*taxonRatio);
    }

    public void setProteinRatio(float proteinRatio) {
        this.proteinRatio = proteinRatio;
        proteinLines = (long)(totalLines*proteinRatio);
    }

    public void setGeneRatio(float geneRatio) {
        this.geneRatio = geneRatio;
        geneLines = (long)(totalLines*geneRatio);
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
                setEnzymeRatio(parseRatio(args[i+1], args[i]));
            }
            else if (args[i].equals("--pathway")) {
                setPathwayRatio(parseRatio(args[i+1], args[i]));
            }
            else if (args[i].equals("--taxonomy")) {
                setTaxonRatio(parseRatio(args[i+1], args[i]));
            }
            else if (args[i].equals("--protein")) {
                setProteinRatio(parseRatio(args[i+1], args[i]));
            }
            else if (args[i].equals("--gene")) {
                setGeneRatio(parseRatio(args[i+1], args[i]));
            }
            else {
                throw new ArgumentException("the argument can't be recognized : " + args[i]);
            }
        }
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
