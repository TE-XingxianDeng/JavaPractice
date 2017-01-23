package linkmining.hits;

/**
 * HITS链接分析算法
 *
 * @author lyq
 */
public class Client {
    public static void main(String[] args) {
        String filePath = Client.class.getResource("input.txt").getPath();

        HITSTool tool = new HITSTool(filePath);
        tool.printResultPage();
    }
}
