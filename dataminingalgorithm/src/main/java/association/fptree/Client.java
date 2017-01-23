package association.fptree;

/**
 * @author Dylan
 * @version 1.00 1/20/2017
 */
public class Client {
    public static void main(String[] args) {
        String filePath = Client.class.getResource("testInput.txt").getPath();
        //最小支持度阈值
        int minSupportCount = 2;

        FPTreeTool tool = new FPTreeTool(filePath, minSupportCount);
        tool.startBuildingTree();
    }
}
