package classification.naivebayes;


/**
 * 朴素贝叶斯算法场景调用类
 *
 * @author lyq
 */
public class Client {
    public static void main(String[] args) {
        //训练集数据
        String filePath = Client.class.getResource("input.txt").getPath();
        String testData = "Youth Medium Yes Fair";
        NaiveBayesTool tool = new NaiveBayesTool(filePath);
        System.out.println(testData + " 数据的分类为:" + tool.naiveBayesClassificate(testData));
    }
}
