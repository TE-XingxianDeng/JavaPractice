package association.apriori;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AprioriTool {
    /**
     * minimum support count
     */
    private int minSupportCount;
    /**
     * test data file path
     */
    private String filePath;
    /**
     * goods ID in per transaction
     */
    private ArrayList<String[]> totalGoodsIDs;
    /**
     * list of all calculated frequent item set
     */
    private ArrayList<FrequentItem> resultItem;
    /**
     * set of calculated frequent item set id
     */
    private ArrayList<String[]> resultItemID;

    public AprioriTool(String filePath, int minSupportCount) {
        this.filePath = filePath;
        this.minSupportCount = minSupportCount;
        readDataFile();
    }

    /**
     * read data from file
     */
    private void readDataFile() {
        File file = new File(filePath);
        ArrayList<String[]> dataArray = new ArrayList<>();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            String str;
            String[] tempArray;
            while ((str = in.readLine()) != null) {
                tempArray = str.split(" ");
                dataArray.add(tempArray);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String[] temp;
        totalGoodsIDs = new ArrayList<>();
        for (String[] array : dataArray) {
            temp = new String[array.length - 1];
            System.arraycopy(array, 1, temp, 0, array.length - 1);

            // add transaction ID to list
            totalGoodsIDs.add(temp);
        }
    }

    /**
     * judge whether string array 2 is contained in array 1
     *
     * @param array1
     * @param array2
     * @return
     */
    public boolean isStrContain(String[] array1, String[] array2) {
        if (array1 == null || array2 == null) {
            return false;
        }
        boolean isContain = false;
        for (String s : array2) {
            // initial variable again when compare new letter
            isContain = false;
            // judge each character in array2, if any is included in array1, return isContain
            for (String s2 : array1) {
                if (s.equals(s2)) {
                    isContain = true;
                    break;
                }
            }

            // if judgement finished, break loop
            if (!isContain) {
                break;
            }
        }
        return isContain;
    }

    /**
     * connect itemSet
     */
    private void computeLink() {
        // ending number of connecting, k-itemSet must compute until k-1-itemSet
        int endNum = 0;

        int currentNum = 1;
        // Goods, 1-frequent itemSet map
        HashMap<String, FrequentItem> itemMap = new HashMap<>();
        FrequentItem tempItem;
        // initial list
        ArrayList<FrequentItem> list = new ArrayList<>();
        // itemSet of product by connecting operation
        resultItem = new ArrayList<>();
        resultItemID = new ArrayList<>();
        // class of goods ID
        ArrayList<String> idType = new ArrayList<>();
        for (String[] a : totalGoodsIDs) {
            for (String s : a) {
                if (!idType.contains(s)) {
                    tempItem = new FrequentItem(new String[]{s}, 1);
                    idType.add(s);
                    resultItemID.add(new String[]{s});
                } else {
                    // increase support count by 1
                    tempItem = itemMap.get(s);
                    tempItem.setCount(tempItem.getCount() + 1);
                }
                itemMap.put(s, tempItem);
            }
        }

        // For continue connect computing, pass init frequent itemSet into list
        for (Map.Entry<String, FrequentItem> entry : itemMap.entrySet()) {
            list.add(entry.getValue());
        }
        // sort goods by id, computing result will be inconsistent otherwise
        Collections.sort(list);
        resultItem.addAll(list);

        String[] array1;
        String[] array2;
        String[] resultArray;
        ArrayList<String> tempIds;
        ArrayList<String[]> resultContainer;

        endNum = list.size() - 1;

        while (currentNum < endNum) {
            resultContainer = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                tempItem = list.get(i);
                array1 = tempItem.getIdArray();
                for (int j = i + 1; j < list.size(); j++) {
                    tempIds = new ArrayList<>();
                    array2 = list.get(j).getIdArray();
                    for (int k = 0; k < array1.length; k++) {
                        if (array1[k].equals(array2[k])) {
                            tempIds.add(array1[k]);
                        } else {
                            tempIds.add(array1[k]);
                            tempIds.add(array2[k]);
                        }
                    }
                    resultArray = new String[tempIds.size()];
                    tempIds.toArray(resultArray);

                    boolean isContain = false;
                    if (resultArray.length == (array1.length + 1)) {
                        isContain = isIDArrayContains(resultContainer,
                                resultArray);
                        if (!isContain) {
                            resultContainer.add(resultArray);
                        }
                    }
                }
            }
            list = cutItem(resultContainer);
            currentNum++;
        }

        // output frequent itemSet
        for (int k = 1; k <= currentNum; k++) {
            System.out.println("frequent" + k + "itemSet：");
            for (FrequentItem i : resultItem) {
                if (i.getLength() == k) {
                    System.out.print("{");
                    for (String t : i.getIdArray()) {
                        System.out.print(t + ",");
                    }
                    System.out.print("},");
                }
            }
            System.out.println();
        }
    }

    private boolean isIDArrayContains(ArrayList<String[]> container,
                                      String[] array) {
        boolean isContain = true;
        if (container.size() == 0) {
            isContain = false;
            return isContain;
        }

        for (String[] s : container) {
            if (s.length != array.length) {
                continue;
            }
            isContain = true;
            for (int i = 0; i < s.length; i++) {
                // if one id are not equals, not equals
                if (s[i] != array[i]) {
                    isContain = false;
                    break;
                }
            }

            if (isContain) {
                break;
            }
        }
        return isContain;
    }

    /**
     * @param resultIds
     * @return
     */
    private ArrayList<FrequentItem> cutItem(ArrayList<String[]> resultIds) {
        String[] temp;
        // ignore index
        int ignoreIndex = 0;
        FrequentItem tempItem;
        // Frequent itemSet generate by prune
        ArrayList<FrequentItem> newItem = new ArrayList<>();
        // ids which doesn't match request
        ArrayList<String[]> deleteArray = new ArrayList<>();
        // whether subset is frequent item set
        boolean isContain = true;
        for (String[] array : resultIds) {
            // list all of sub item set, judge whether is exists in itemSet list
            temp = new String[array.length - 1];
            for (ignoreIndex = 0; ignoreIndex < array.length; ignoreIndex++) {
                isContain = true;
                for (int j = 0, k = 0; j < array.length; j++) {
                    if (j != ignoreIndex) {
                        temp[k] = array[j];
                        k++;
                    }
                }
                if (!isIDArrayContains(resultItemID, temp)) {
                    isContain = false;
                    break;
                }
            }

            if (!isContain) {
                deleteArray.add(array);
            }
        }
        resultIds.removeAll(deleteArray);

        int tempCount = 0;
        for (String[] array : resultIds) {
            tempCount = 0;
            for (String[] array2 : totalGoodsIDs) {
                if (isStrArrayContain(array2, array)) {
                    tempCount++;
                }
            }

            if (tempCount >= minSupportCount) {
                tempItem = new FrequentItem(array, tempCount);
                newItem.add(tempItem);
                resultItemID.add(array);
                resultItem.add(tempItem);
            }
        }

        return newItem;
    }

    /**
     * @param array1
     * @param array2
     * @return
     */
    private boolean isStrArrayContain(String[] array1, String[] array2) {
        boolean isContain = true;
        for (String s2 : array2) {
            isContain = false;
            for (String s1 : array1) {
                if (s2.equals(s1)) {
                    isContain = true;
                    break;
                }
            }

            if (!isContain) {
                break;
            }
        }

        return isContain;
    }

    /**
     * @param minConf minimum confidence
     */
    public void printAttachRule(double minConf) {
        // 进行连接和剪枝操作
        computeLink();

        int count1 = 0;
        int count2 = 0;
        ArrayList<String> childGroup1;
        ArrayList<String> childGroup2;
        String[] group1;
        String[] group2;
        // 以最后一个频繁项集做关联规则的输出
        String[] array = resultItem.get(resultItem.size() - 1).getIdArray();
        // 子集总数，计算的时候除去自身和空集
        int totalNum = (int) Math.pow(2, array.length);
        String[] temp;
        // 二进制数组，用来代表各个子集
        int[] binaryArray;
        // 除去头和尾部
        for (int i = 1; i < totalNum - 1; i++) {
            binaryArray = new int[array.length];
            numToBinaryArray(binaryArray, i);

            childGroup1 = new ArrayList<>();
            childGroup2 = new ArrayList<>();
            count1 = 0;
            count2 = 0;
            // 按照二进制位关系取出子集
            for (int j = 0; j < binaryArray.length; j++) {
                if (binaryArray[j] == 1) {
                    childGroup1.add(array[j]);
                } else {
                    childGroup2.add(array[j]);
                }
            }

            group1 = new String[childGroup1.size()];
            group2 = new String[childGroup2.size()];

            childGroup1.toArray(group1);
            childGroup2.toArray(group2);

            for (String[] a : totalGoodsIDs) {
                if (isStrArrayContain(a, group1)) {
                    count1++;

                    // 在group1的条件下，统计group2的事件发生次数
                    if (isStrArrayContain(a, group2)) {
                        count2++;
                    }
                }
            }

            // {A}-->{B}的意思为在A的情况下发生B的概率
            System.out.print("{");
            for (String s : group1) {
                System.out.print(s + ", ");
            }
            System.out.print("}-->");
            System.out.print("{");
            for (String s : group2) {
                System.out.print(s + ", ");
            }
            System.out.print(MessageFormat.format(
                    "},confidence(置信度)：{0}/{1}={2}", count2, count1, count2
                            * 1.0 / count1));
            if (count2 * 1.0 / count1 < minConf) {
                // doesn't match request, not strong rule
                System.out.println("由于此规则置信度未达到最小置信度的要求，不是强规则");
            } else {
                System.out.println("为强规则");
            }
        }
    }

    /**
     * convert decimal to binary
     *
     * @param binaryArray binary array after converting
     * @param num         decimal number which pending to convert
     */
    private void numToBinaryArray(int[] binaryArray, int num) {
        int index = 0;
        while (num != 0) {
            binaryArray[index] = num % 2;
            index++;
            num /= 2;
        }
    }
}