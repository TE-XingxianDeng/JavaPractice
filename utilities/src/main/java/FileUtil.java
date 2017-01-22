import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class FileUtil {
    public static void convertEncoding(final File file, final Charset origin, final Charset target) throws IOException {
        File tmpFile = new File(System.getProperty("java.io.tmpdir"), "tmp");

        final int BSIZE = 1024;
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        try (FileChannel in = new FileInputStream(file).getChannel();
             FileChannel tmpOut = new FileOutputStream(tmpFile).getChannel()) {

            while (in.read(buffer) != -1) {
                buffer.flip();
                tmpOut.write(target.encode(origin.decode(buffer)));
                buffer.clear();
            }
        }
        try (FileChannel tmpIn = new FileInputStream(tmpFile).getChannel();
             FileChannel out = new FileOutputStream(file).getChannel()) {
            while (tmpIn.read(buffer) != -1) {
                buffer.flip();
                out.write(buffer);
                buffer.clear();
            }
            tmpIn.close();
            out.close();
        }
        tmpFile.delete();
    }
}