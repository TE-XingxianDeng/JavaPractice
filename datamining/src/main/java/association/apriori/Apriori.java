package association.apriori;

import java.util.*;

/**
 * @author Dylan
 * @version 1.00 1/23/2017
 */
public class Apriori {
    private final List<String[]> transactions;
    private List<FrequentItemSet> frequentItemSets;

    public Apriori(List<String[]> transactions) {
        this.transactions = transactions;
        this.frequentItemSets = genCandidate(initPass());
    }

    public void apriori() {
    }

    public List<FrequentItemSet> initPass() {
        List<FrequentItemSet> frequentItemSets = new LinkedList<>();
        for (String[] transaction : transactions) {
            for (String item : transaction) {
                String[] key = new String[]{item};
                FrequentItemSet fis = new FrequentItemSet(key);
                int index;
                if ((index = frequentItemSets.indexOf(fis)) >= 0) {
                    frequentItemSets.get(index).increaseSupCount();
                } else {
                    frequentItemSets.add(fis);
                }
            }
        }
        return frequentItemSets;
    }

    public List<FrequentItemSet> genCandidate(List<FrequentItemSet> fSets) {
        for (FrequentItemSet fSet : fSets) {
            Arrays.sort(fSet.itemSet);
        }
        Collections.sort(fSets);
        int currentLen = fSets.get(0).length;
        List<FrequentItemSet> candidates = new LinkedList<>();
        for (int i = 0; i < fSets.size() - 1; i++) {
            FrequentItemSet currentFSet = fSets.get(i);
            FrequentItemSet nextFSet = fSets.get(i + 1);
            if (oneElementDiff(currentFSet, nextFSet)) {
                String[] newSet = new String[currentLen + 1];
                System.arraycopy(currentFSet.itemSet, 0, newSet, 0, currentLen);
                newSet[currentLen] = nextFSet.itemSet[currentLen - 1];
                FrequentItemSet candidate = new FrequentItemSet(newSet);
                candidates.add(candidate);
            }
        }
        return candidates;
    }

    private boolean oneElementDiff(FrequentItemSet fis1, FrequentItemSet fis2) {
        boolean lastDiff = false;
        int len = fis1.length;
        if (len == 1) {
            return true;
        }
        int lastIndex = len - 1;
        String[] strs1 = fis1.itemSet;
        String[] strs2 = fis2.itemSet;
        if (strs1[0].equals(strs2[0])) {
            for (int i = 0; i < lastIndex; i++) {
                if (!Objects.equals(strs1[i], strs2[i]))
                    return false;
            }
            if (!strs1[lastIndex].equals(strs2[lastIndex])) {
                lastDiff = true;
            }
        } else {
            for (int i = 0; i < lastIndex; i++) {
                if (!Objects.equals(strs1[i + 1], strs2[i]))
                    return false;
            }
        }
        return lastDiff;
    }

    public void outputResult() {
        for (FrequentItemSet fis : frequentItemSets) {
            System.out.print("key: " + Arrays.toString(fis.itemSet));
            System.out.println(", value: " + fis.getSupCount());
        }
    }
}
