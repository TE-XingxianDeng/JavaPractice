package boosting.adaboost;

/**
 * @author Dylan
 * @version 1.00 1/20/2017
 */
public class Client {
    public static void main(String[] agrs) {
        String filePath = Client.class.getResource("input.txt").getPath();
        //误差率阈值
        double errorValue = 0.2;

        AdaBoostTool tool = new AdaBoostTool(filePath, errorValue);
        tool.adaBoostClassify();
    }
}
