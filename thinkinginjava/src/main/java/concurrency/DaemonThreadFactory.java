package concurrency;

import java.util.concurrent.ThreadFactory;

/**
 * @author dylan
 * @version 1.00 2/12/17 8:43 PM
 */
public class DaemonThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }
}
