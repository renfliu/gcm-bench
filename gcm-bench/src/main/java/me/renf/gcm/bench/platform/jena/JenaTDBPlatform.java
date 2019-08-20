package me.renf.gcm.bench.platform.jena;

import me.renf.gcm.bench.Platform;
import me.renf.gcm.bench.conf.BenchConf;
import me.renf.gcm.bench.exception.BenchmarkLoadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class JenaTDBPlatform extends Platform {
    private String jenaBinRoot;
    private String dataset;

    public JenaTDBPlatform(BenchConf conf) {
        super(conf);
    }

    @Override
    public void init() {
        jenaBinRoot= Paths.get(conf.getJenaPath(), "bin").toString();
        dataset = Paths.get(conf.getDataset()).getFileName().toString();
    }

    @Override
    public void buildData() {

    }

    @Override
    public void loadData() {
        try {
            String[] cmds = {jenaBinRoot + "/tdb2.tdbloader", "--loc", conf.getJenaPath() + "/storage/" + dataset, conf.getDataset()};
            Process load = Runtime.getRuntime().exec(cmds);
            int exitValue = load.waitFor();
            BufferedReader reader;
            if ( 0 != exitValue) {
                reader = new BufferedReader(new InputStreamReader(load.getErrorStream()));
                StringBuilder errorMessage = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    errorMessage.append(line);
                    line = reader.readLine();
                }
                throw new BenchmarkLoadException(errorMessage.toString());
            } else {
                reader = new BufferedReader(new InputStreamReader(load.getInputStream()));
                String line = reader.readLine();
                while (line != null) {
                    System.out.println(line);
                    line = reader.readLine();
                }
            }

            reader.close();
        } catch (IOException e) {
            throw new BenchmarkLoadException("Jena load failed: \n " + e.getMessage());
        } catch (InterruptedException e) {
            throw new BenchmarkLoadException("Jena load failed: \n " + e.getMessage());
        }
    }

    @Override
    public String query(String query) {
        StringBuilder sb = new StringBuilder();
        try {
            System.out.print(query);
            String[] cmds = {jenaBinRoot + "/tdb2.tdbquery", "--time", "--loc", "conf.getJenaPath()" + "/storage/" + dataset,  query};
            Process q = Runtime.getRuntime().exec(cmds);
            StreamThread inputStream = new StreamThread(q.getInputStream());
            StreamThread errorStream = new StreamThread(q.getErrorStream());
            new Thread(inputStream).start();
            new Thread(errorStream).start();

            int exitValue = q.waitFor();
            if ( exitValue == 0) {
                return inputStream.result.toString();
            }else {
                return errorStream.result.toString();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return sb.toString();
    }

    @Override
    public void unload() {

    }

    @Override
    public void exit() {

    }

    private class StreamThread implements  Runnable{
        StringBuilder result;
        InputStream in;

        public StreamThread(InputStream in) {
            this.in = in;
            result = new StringBuilder();
        }

        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = reader.readLine();
                while (line != null) {
                    System.out.println(line);
                    result.append(line);
                    result.append("\n");
                    line = reader.readLine();
                }
                reader.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
