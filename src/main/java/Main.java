import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
//        listFiles(new Path("/hbase/"));
//        createDir(new Path("/test"));
        updateFile(new Path("/test/etc_passwd"));
    }

    private static void updateFile(Path dstPath) throws IOException, URISyntaxException {
        String localFilePath = "/etc/passwd";
        System.out.println(localFilePath);
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
            System.out.println(next.getPath());
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
