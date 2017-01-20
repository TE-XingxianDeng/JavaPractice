package classification.id3;

/**
 * ID3决策树分类算法测试场景类
 *
 * @author lyq
 */
public class Client {
    public static void main(String[] args) {
        String filePath = Client.class.getResource("input.txt").getPath();

        ID3Tool tool = new ID3Tool(filePath);
        tool.startBuildingTree(true);
    }
}
