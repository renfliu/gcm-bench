package me.renf.gcm.bench.report;

import me.renf.gcm.bench.domain.BenchmarkResult;

public interface BenchmarkReportGenerator {
    /**
     * 从测试结果生成测试报告
     * @param result 运行Benchmark后的测试结果
     * @return 经过统计之后的测试报告
     */
    BenchmarkReport generateReportFromResult(BenchmarkResult result);
}
