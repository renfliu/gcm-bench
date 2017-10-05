package me.renf.gcm.bench.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class BenchmarkReportWriter {
    private final Logger logger = LoggerFactory.getLogger(BenchmarkReportWriter.class);

    public void write(BenchmarkReport report) {
        File reportFile = new File("report");

        logger.info("clear old report");
        try {
            Files.walkFileTree(reportFile.toPath(), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        logger.info("generating report");
        if (!reportFile.exists()) {
            reportFile.mkdir();
        }
        copyStaticFile();
        report.write( "report/index.html");
    }

    public void copyStaticFile() {
        try {
            Files.walkFileTree(new File("html").toPath(), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if (dir.getNameCount() > 1) {  // directory depth > 1
                        Files.createDirectory(Paths.get("report", dir.getFileName().toString()));
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.getNameCount() > 2) {  // file depth > 2
                        Files.copy(file, Paths.get("report", file.getName(file.getNameCount() - 2).toString(), file.getName(file.getNameCount() - 1).toString()));
                    }else {
                        Files.copy(file, Paths.get("report", file.getName(file.getNameCount() - 1).toString()));
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
