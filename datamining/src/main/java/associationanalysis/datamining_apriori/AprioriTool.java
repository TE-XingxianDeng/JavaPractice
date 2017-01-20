package associationanalysis.datamining_apriori;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
}