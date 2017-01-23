package association.apriori;

/**
 * @author Dylan
 * @version 1.00 1/20/2017
 */
public class Client {
    public static void main(String[] args) {
        String filePath = Client.class.getResource("testInput.txt").getPath();

        AprioriTool tool = new AprioriTool(filePath, 2);
        tool.printAttachRule(0.7);
    }
}
