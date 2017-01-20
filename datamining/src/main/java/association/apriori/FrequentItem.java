package association.apriori;

/**
 * @author Dylan
 * @version 1.00 1/20/2017
 */
public class FrequentItem implements Comparable<FrequentItem> {
    /**
     * id of frequent item set
     */
    private String[] idArray;
    /**
     * support count of frequent item set
     */
    private int count;
    /**
     * length of frequent item set
     */
    private int length;

    public FrequentItem(String[] idArray, int count) {
        this.idArray = idArray;
        this.count = count;
        length = idArray.length;
    }

    public String[] getIdArray() {
        return idArray;
    }

    public void setIdArray(String[] idArray) {
        this.idArray = idArray;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public int compareTo(FrequentItem o) {
        Integer int1 = Integer.parseInt(this.getIdArray()[0]);
        Integer int2 = Integer.parseInt(o.getIdArray()[0]);

        return int1.compareTo(int2);
    }
}
