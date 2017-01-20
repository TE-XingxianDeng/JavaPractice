package clustering.kmeans;

/**
 * K-means（K均值）算法调用类
 *
 * @author lyq
 */
public class Client {
    public static void main(String[] args) {
        String filePath = Client.class.getResource("input.txt").getPath();
        //聚类中心数量设定
        int classNum = 3;

        KMeansTool tool = new KMeansTool(filePath, classNum);
        tool.kMeansClustering();
    }
}
