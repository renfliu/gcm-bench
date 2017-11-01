package me.renf.gcm.bench.monitor;

import me.renf.gcm.bench.conf.BenchConf;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Monitor {
    static final String CPU_FILE = "temp/cpuinfo";
    static final String MEM_FILE = "temp/meminfo";

    private final Logger logger = LoggerFactory.getLogger(Monitor.class);
    private BenchConf conf;
    private boolean isMonitor;
    private Sigar sigar;

    public Monitor(BenchConf conf) {
        this.conf = conf;
        isMonitor = false;
        sigar = initSigar();

        File parentDir = new File(CPU_FILE).getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdir();
        }
    }

    private Sigar initSigar() {
        // set system
        String libpath = new File("gcm-bench/lib").getAbsolutePath();
        String OS = System.getProperty("os.name", "generic");
        String path = System.getProperty("java.library.path");
        if (OS.indexOf("win") > 0 ) { //windows
            path = path + ";" + libpath;
        }else {
            path = path + ":" + libpath;
        }
        System.setProperty("java.library.path", path);

        return new Sigar();
    }

    public void start() {
        isMonitor = true;
        startMonitor();
    }

    public void stop() {
        isMonitor = false;
    }

    public boolean isMonitor() {
        return isMonitor;
    }

    private void startMonitor() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File cpuFile = new File(CPU_FILE);
                    File memFile = new File(MEM_FILE);
                    FileWriter cpuWriter = new FileWriter(cpuFile);
                    FileWriter memWriter = new FileWriter(memFile);
                    while (isMonitor) {
                        try {
                            if (conf.isMonitorCpu()) collectCpuInfo(cpuWriter);
                            if (conf.isMonitorMem()) collectMemInfo(memWriter);

                            Thread.sleep(conf.getMonitorFreq());
                        } catch (InterruptedException e){
                            logger.error(e.getMessage());
                            return;
                        } catch (SigarException e) {
                            logger.error(e.getMessage());
                            return;
                        } catch (IOException e) {
                            logger.error(e.getMessage());
                            return;
                        }
                    }
                    cpuWriter.close();
                    memWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            }
        }).start();
    }

    private void collectCpuInfo(FileWriter writer) throws SigarException, IOException{
        double combined = 0;
        CpuPerc[] cpuPercs = sigar.getCpuPercList();
        for (CpuPerc perc : cpuPercs) {
            combined += perc.getCombined();
        }
        CpuInfo cpuInfo = new CpuInfo();
        cpuInfo.setTimestamp(System.currentTimeMillis());
        cpuInfo.setCombined(combined/cpuPercs.length);

        writer.write(cpuInfo.toString() + "\n");
    }

    private void collectMemInfo(FileWriter writer) throws SigarException, IOException{
        Mem mem = sigar.getMem();
        MemInfo memInfo = new MemInfo();
        memInfo.setTimestamp(System.currentTimeMillis());
        memInfo.setUsed(mem.getUsed());

        writer.write(memInfo.toString() + "\n");
    }
}
