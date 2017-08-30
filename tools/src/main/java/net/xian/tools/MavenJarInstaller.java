package net.xian.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Dylan
 * @version 1.00 1/19/2017
 */
public class MavenJarInstaller {
    private static final int BSIZE = 1024;

    public static void main(String[] args) {
        String userHomeStr = System.getProperty("user.home");
        String gradleJarFileDirStr = String.join(File.separator, userHomeStr, ".gradle", "caches", "modules-2", "files-2.1");
        String mavenJarFileDirStr = String.join(File.separator, userHomeStr, ".m2", "repository");
        installMaven(gradleJarFileDirStr, mavenJarFileDirStr);
    }

    public static void installMaven(String src, String dest) {
        LinkedList<File> receiver = new LinkedList<>();
        FileUtil.scanDir(new File(src), receiver);
        HashMap<File, File> newPathsMap = new HashMap<>(receiver.size());
        for (File file : receiver) {
            String relativePath = file.getAbsolutePath().replace(src, "");
            String[] pathArray = relativePath.split("\\" + File.separator);
            String[] newPathSegment = new String[4];
            String newPackagePath = pathArray[1].replaceAll("\\.", "\\" + File.separator);
            newPathSegment[0] = newPackagePath;
            newPathSegment[1] = pathArray[2];
            newPathSegment[2] = pathArray[3];
            newPathSegment[3] = pathArray[5];
            String newPathStr = String.join(File.separator, newPathSegment);
            newPathsMap.put(file, new File(dest, newPathStr));
        }

        for (Map.Entry<File, File> entry : newPathsMap.entrySet()) {
            File sourceFile = entry.getKey();
            File destFile = entry.getValue();
            File destDir = destFile.getParentFile();
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            if (destFile.exists()) {
                System.out.println("File: " + destFile + " exists, ignore");
            } else {
                try {
                    System.out.println(sourceFile + " -> " + destFile);
                    copyFile(sourceFile, destFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
