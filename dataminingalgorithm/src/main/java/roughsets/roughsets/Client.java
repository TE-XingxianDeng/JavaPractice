package roughsets.roughsets;

/**
 * 粗糙集约简算法
 *
 * @author lyq
 */
public class Client {
    public static void main(String[] args) {
        String filePath = Client.class.getResource("input.txt").getPath();

        RoughSetsTool tool = new RoughSetsTool(filePath);
        tool.findingReduct();
    }
}
