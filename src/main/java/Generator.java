import exceptions.ArgumentException;

public class Generator {

    public static void main(String[] args) {
        Generator gen = new Generator();
        try {
            gen.setArgs(args);
            String r = gen.genData();
            System.out.println(r);
        } catch (ArgumentException ae) {
            System.out.println(ae.getMessage());
            gen.printUsage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private GenConfig config;

    public Generator(){
        config = new GenConfig();
    }

    public Generator(String[] args) throws ArgumentException{
        config = new GenConfig();
        if (args != null) config.set(args);
    }

    public void printUsage() {
        System.out.println("GCMGenerator is a tool for generating 'Global Catalogue of Microorganisms' simulated data. " +
                "You can use this to generate data from 1M to 100G and as more as your computer can hold. The Global " +
                "Catalogue of Microorganisms is composed of XXX parts, so you can change the ratio of each part to " +
                "generating different data according to your request. The ratios you need is given as parameters of the " +
                "command, and you can find the usage below.");
        System.out.println("");
        System.out.println("SYNOPSIS: ");
        System.out.println("     -x, --XXXX ");
        System.out.println("OPTIONS:");
        System.out.println("");
        System.out.println("");
    }

    public void setArgs(String[] args) throws ArgumentException{
        config.set(args);
    }

    public String genData() throws ArgumentException{

        return "数据生成成功";
    }

}
