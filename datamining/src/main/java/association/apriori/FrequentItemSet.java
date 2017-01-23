package association.apriori;

import java.util.Arrays;

/**
 * @author Dylan
 * @version 1.00 1/23/2017
 */
public class FrequentItemSet implements Comparable<FrequentItemSet> {
    public final int length;
    public final String[] itemSet;
    private int supCount;

    public FrequentItemSet(String[] itemSet) {
        this.itemSet = itemSet;
        this.length = itemSet.length;
        supCount = 1;
    }

    public void increaseSupCount() {
        this.supCount += 1;
    }

    public int getSupCount() {
        return this.supCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FrequentItemSet that = (FrequentItemSet) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(itemSet, that.itemSet);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(itemSet);
    }

    @Override
    public int compareTo(FrequentItemSet o) {
        int result = 0;
        for (int i = 0; i < length; i++) {
            result += (itemSet[i].compareTo(o.itemSet[i]));
        }
        return result;
    }
}
