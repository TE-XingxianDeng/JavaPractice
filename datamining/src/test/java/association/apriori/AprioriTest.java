package association.apriori;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Dylan
 * @version 1.00 1/23/2017
 */
public class AprioriTest {
    private List<String[]> transactions;
    private String dataFile = AprioriTest.class.getResource("testInput.txt").getPath();

    private List<String> readData(String dataFile) throws IOException {
        List<String> data = new LinkedList<>();
        try (BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(dataFile)))) {
            String line;
            while ((line = bufReader.readLine()) != null) {
                data.add(line);
            }
        }
        return data;
    }

    private List<String[]> cutHead(List<String> list) {
        LinkedList<String[]> transactions = new LinkedList<>();
        for (String s : list) {
            String[] ids = s.split(" ");
            int idsLen = ids.length;
            String[] transaction = new String[idsLen - 1];
            System.arraycopy(ids, 1, transaction, 0, idsLen - 1);
            transactions.add(transaction);
        }
        return transactions;
    }
    @Before
    public void setUp() throws Exception {
        transactions = cutHead(readData(dataFile));
    }


    @Test
    public void apriori() throws Exception {
        Apriori apriori = new Apriori(transactions);
        apriori.outputResult();
    }
}