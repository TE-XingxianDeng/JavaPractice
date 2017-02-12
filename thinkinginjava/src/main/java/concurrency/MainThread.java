package concurrency;

/**
 * @author dylan
 * @version 1.00 2/12/17 7:32 PM
 */
public class MainThread {
    public static void main(String[] args) {
        LiftOff launch = new LiftOff();
        launch.run();
    }
}
