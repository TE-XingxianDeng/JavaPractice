package clustering.birch;

/**
 * BIRCH聚类算法调用类
 *
 * @author lyq
 */
public class Client {
    public static void main(String[] args) {
        String filePath = Client.class.getResource("testInput.txt").getPath();
        //内部节点平衡因子B
        int B = 2;
        //叶子节点平衡因子L
        int L = 2;
        //簇直径阈值T
        double T = 0.6;

        BIRCHTool tool = new BIRCHTool(filePath, B, L, T);
        tool.startBuilding();
    }
}
