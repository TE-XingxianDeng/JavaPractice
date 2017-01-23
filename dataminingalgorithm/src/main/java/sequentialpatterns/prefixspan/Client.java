package sequentialpatterns.prefixspan;

/**
 * PrefixSpan序列模式挖掘算法
 *
 * @author lyq
 */
public class Client {
    public static void main(String[] args) {
        String filePath = Client.class.getResource("input.txt").getPath();
        //最小支持度阈值率
        double minSupportRate = 0.4;

        PrefixSpanTool tool = new PrefixSpanTool(filePath, minSupportRate);
        tool.prefixSpanCalculate();
    }
}

