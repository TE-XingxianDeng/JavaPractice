package concurrency;

/**
 * The most basic use of the Thread class
 *
 * @author dylan
 * @version 1.00 2/12/17 7:34 PM
 */
public class BasicThreads {
    public static void main(String[] args) {
        Thread t = new Thread(new LiftOff());
        t.start();
        System.out.println("Waiting for LiftOff");
    }
}
