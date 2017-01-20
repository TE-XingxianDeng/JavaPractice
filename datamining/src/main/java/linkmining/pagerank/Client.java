package linkmining.pagerank;

/**
 * PageRank计算网页重要性/排名算法
 *
 * @author lyq
 */
public class Client {
    public static void main(String[] args) {
        String filePath = Client.class.getResource("input.txt").getPath();

        PageRankTool tool = new PageRankTool(filePath);
        tool.printPageRankValue();
    }
}
