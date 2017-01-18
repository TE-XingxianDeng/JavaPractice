package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Using transferTo() between channels
 * {Args: TransferTo.java TransferTo.txt}
 *
 * @author Dylan
 * @version 1.00 1/18/2017
 */
public class TransferTo {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("argumens: sourcefile destfile");
            System.exit(1);
        }
        FileChannel
                in = new FileInputStream(args[0]).getChannel(),
                out = new FileOutputStream(args[1]).getChannel();
        in.transferTo(0, in.size(), out);
        // Or;
        // out.transferFrom(in, 0, in.size());
    }
}
