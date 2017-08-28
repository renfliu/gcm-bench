import exceptions.ArgumentException;

public class GenConfig {
    private String out;

    public GenConfig() {}

    public GenConfig(String[] args) throws ArgumentException{
        this.set(args);
    }

    public void set(String[] args) throws ArgumentException{
        for (int i = 0; i < args.length; i+=2) {
            if (args[i].equals("-o") || (args[i].equals("--output"))) {
                this.out = args[i+1];
                continue;
            }
            else {
                throw new ArgumentException("the argument can't be recognized : " + args[i]);
            }
        }
    }

    public String getOut() {
        return out;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("--output: " + out);

        return sb.toString();
    }
}
