package net.xian.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Dylan
 * @version 1.00 1/19/2017
 */
public class MavenJarInstaller {
    private static final String BASEDIR = "C:\\Users\\Dylan\\.gradle\\caches\\modules-2\\files-2.1";
    private static final int BSIZE = 1024;

    public static void installMaven() {
        LinkedList<File> receiver = new LinkedList<>();
        FileUtil.scanDir(new File(BASEDIR), receiver);
        HashMap<File, File> newPathsMap = new HashMap<>(receiver.size());
        for (File file : receiver) {
            String[] pathArray = file.getAbsolutePath().split("\\\\");
            ArrayList<String> newPathSagement = new ArrayList<>(4);
            String newPackagePath = pathArray[7].replaceAll("\\.", "\\\\");
            newPathSagement.add(newPackagePath);
            newPathSagement.add(pathArray[8]);
            newPathSagement.add(pathArray[9]);
            newPathSagement.add(pathArray[11]);
            StringBuilder newPathBuilder = new StringBuilder("C:\\Users\\Dylan\\.m2\\repository");
            newPathBuilder.append("\\\\");
            for (String sage : newPathSagement) {
                newPathBuilder.append(sage);
                newPathBuilder.append("\\\\");
            }
            newPathsMap.put(file, new File(newPathBuilder.toString()));
        }
        for (Map.Entry<File, File> entry : newPathsMap.entrySet()) {
            File sourceFile = entry.getKey();
            File destFile = entry.getValue();
            File destDir = destFile.getParentFile();
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            try {
                copyFile(sourceFile, destFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void copyFile(File source, File dest) throws IOException {
        FileChannel
                in = new FileInputStream(source).getChannel(),
                out = new FileOutputStream(dest).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        while (in.read(buffer) != -1) {
            buffer.flip();  // Prepare for writing
            out.write(buffer);
            buffer.clear();  // Prepare for reading
        }
    }
}
