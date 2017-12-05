package me.renf.gcm.bench.monitor;

import me.renf.gcm.bench.conf.BenchConf;
import me.renf.gcm.bench.domain.BenchmarkResult;
import me.renf.gcm.bench.domain.LoadResult;
import me.renf.gcm.bench.domain.MonitorInfo;
import me.renf.gcm.bench.domain.QueryResult;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

public class Monitor {
    static final String CPU_FILE = "temp/cpuinfo";
    static final String MEM_FILE = "temp/meminfo";

    private static final Logger logger = LoggerFactory.getLogger(Monitor.class);
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
        String libpath = new File("lib").getAbsolutePath();
        String OS = System.getProperty("os.name", "generic");
        String path = System.getProperty("java.library.path");
        if (OS.indexOf("win") > 0 ) {    //windows
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

    public BenchmarkResult addMonitorInfo(BenchmarkResult result) {
        MonitorInfo monitorInfo = createMonitorInfo(result.getStartTime(), result.getEndTime());
        result.setMonitorInfo(monitorInfo);
        addLoadMonitorInfo(result.getLoadResult());
        addQueryMonitorInfo(result.getQueryResults());
        return result;
    }

    private void addLoadMonitorInfo(LoadResult result) {
        MonitorInfo info = createMonitorInfo(result.getStartTime(), result.getEndTime());
        result.setMonitorInfo(info);
    }

    private void addQueryMonitorInfo(List<QueryResult> results) {
        for (QueryResult result : results) {
            MonitorInfo info = createMonitorInfo(result.getStartTime(), result.getEndTime());
            result.setMonitorInfo(info);
        }
    }

    private MonitorInfo createMonitorInfo(long startTime, long endTime) {
        MonitorInfo info = new MonitorInfo();
        info.setStartTime(startTime);
        info.setEndTime(endTime);
        info = setCpuInfo(info);
        info = setMemInfo(info);
        return info;
    }

    private MonitorInfo setCpuInfo(MonitorInfo info) {
        long startTime = info.getStartTime();
        long endTime = info.getEndTime();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(CPU_FILE));
            String line;
            double utilization, sumUtilization = 0;
            long count = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                long time = Long.valueOf(parts[0]);
                utilization = Double.valueOf(parts[1]);
                if (time < startTime) {
                    info.setCpuBefore(utilization);
                } else {
                    sumUtilization += utilization;
                    count++;
                }
                if (time > endTime) {
                    info.setCpuAfter(utilization);
                    break;
                }
            }
            if (count > 0) {
                info.setCpuAverage(sumUtilization / count);
            } else {
                info.setCpuAverage(0);
            }
            reader.close();
        }catch (IOException e) {
            logger.error(e.getMessage());
        }
        return info;
    }

    private MonitorInfo setMemInfo(MonitorInfo info) {
        long startTime = info.getStartTime();
        long endTime = info.getEndTime();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(MEM_FILE));
            String line;
            long occupation, sumOccupation = 0, count = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                long time = Long.valueOf(parts[0]);
                occupation = Long.valueOf(parts[1]);
                if (time < startTime) {
                    info.setMemBefore(occupation);
                }else {
                    sumOccupation += occupation;
                    count++;
                }
                if (time > endTime) {
                    info.setMemAfter(occupation);
                    break;
                }
            }
            if (count > 0) {
                info.setMemAverage(sumOccupation/count);
            } else {
                info.setMemAverage(0);
            }
            reader.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return info;
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
