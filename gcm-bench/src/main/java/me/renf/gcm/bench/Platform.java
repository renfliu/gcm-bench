package me.renf.gcm.bench;

import me.renf.gcm.bench.conf.BenchConf;

import java.util.ArrayList;
import java.util.List;

abstract public class Platform {
    protected BenchConf conf;
    private List<QueryJob> jobs;
    private int jobId;

    public Platform(BenchConf conf) {
        this.conf = conf;
        jobs = new ArrayList<>();
        jobId = 0;
    }

    public int getJobSize() {
        return jobs.size();
    }

    /**
     * 添加Query测试到Query的测试队列
     * @param j 需要进行测试的Query任务
     */
    public void addQueryJob(QueryJob j) {
        jobs.add(j);
    }

    /**
     * 清空所有的Query队列
     */
    public void clearQueryJob() {
        jobs.clear();
    }

    /**
     * 获取下一个Query任务
     * @return
     */
    public QueryJob nextQueryJob() {
        if (jobId < jobs.size()) {
            return jobs.get(jobId++);
        } else {
            return null;
        }
    }

    /**
     * 平台初始化，连接需要进行测试的平台
     */
    abstract public void init();

    /**
     * 加载测试所需要的数据集，测试平台加载测试数据集，并对数据集进行相应的初始化工作
     */
    abstract public void loadData();

    /**
     * 向测试平台添加任务
     */
    abstract public void loadJob();

    /**
     * 实现每一个query的具体实现
     */
    abstract public String query(String query);

    /**
     * 从测试框架中卸载测试数据集
     */
    abstract public void unload();

    /**
     * 退出测试平台
     */
    abstract public void exit();
}
