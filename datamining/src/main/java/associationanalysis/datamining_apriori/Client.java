package associationanalysis.datamining_apriori;

/**
 * @author Dylan
 * @version 1.00 1/20/2017
 */
public class Client {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\lyq\\Desktop\\icon\\testInput.txt";

        AprioriTool tool = new AprioriTool(filePath, 2);
        tool.printAttachRule(0.7);
    }
}
