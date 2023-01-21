import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Paths;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws IOException, URISyntaxException {
        listFiles(new Path("/hbase/"));
        createDir(new Path("/test"));
        updateFile(new Path("/test/etc_passwd"));
        downloadFile(new Path("/test/etc_passwd"));
        renameFile(new Path("hdfs:///test/etc_passwd"), new Path("/test/etc_passwd2"));
    }

    private static void renameFile(Path path, Path path2) throws IOException, URISyntaxException {
        getFileSystem().rename(path, path2);
        log.info(getFileSystem().exists(path2) ? "rename success" : "rename failed");
    }

    private static void downloadFile(Path src) throws IOException, URISyntaxException {
        getFileSystem().copyToLocalFile(src, new Path("/tmp/downloaded_etc_pass"));
        try (FileReader input = new FileReader(Paths.get("/tmp/downloaded_etc_pass").toFile(), Charset.defaultCharset())) {
            IOUtils.readLines(input)
                    .forEach(log::info);
        }
    }

    private static void updateFile(Path dstPath) throws IOException, URISyntaxException {
        String localFilePath = "/etc/passwd";
        log.info(localFilePath);
        getFileSystem().copyFromLocalFile(new Path(localFilePath), dstPath);
    }

    private static void createDir(Path path) throws IOException, URISyntaxException {
        getFileSystem().mkdirs(path);
    }

    private static void listFiles(Path path) throws IOException, URISyntaxException {
        RemoteIterator<LocatedFileStatus> locatedFileStatusRemoteIterator =
                getFileSystem().listFiles(path, false);
        while (locatedFileStatusRemoteIterator.hasNext()) {
            LocatedFileStatus next = locatedFileStatusRemoteIterator.next();
            log.info(next.getPath().getName());
        }
    }

    private static FileSystem getFileSystem() throws IOException, URISyntaxException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://namenode:9000");
        conf.set("dfs.client.use.datanode.hostname", "true");
        System.setProperty("HADOOP_USER_NAME", "root");
        return FileSystem.get(new URI("hdfs://namenode:9000/"), conf);
    }
}
