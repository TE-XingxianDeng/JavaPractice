import java.util.LinkedList;
import java.util.List;

/**
 * @author Dylan
 * @version 1.00 2/21/2017
 */
public class Collections {
    /**
     *
     * @param rawArgsList candidate elements list
     * @param <T> type of candidate element
     * @return
     */
    public <T> List<List<T>> geneArgsList(List<List<T>> rawArgsList) {
        List<List<T>> argsList = new LinkedList<>();
        List<int[]> indexsList = new LinkedList<>();
        int rawArgsListLen = rawArgsList.size();
        int[] indexs = new int[rawArgsListLen];
        int indexH = 0;
        int indexV;
        outer:
        while (indexH < rawArgsListLen) {
            int rawArgsLen = rawArgsList.get(indexH).size();
            for (indexV = 0; indexV < rawArgsLen; indexV++) {
                indexs[indexH] = indexV;
                indexsList.add(indexs);

                int[] nIndexs = new int[rawArgsListLen];
                System.arraycopy(indexs, 0, nIndexs, 0, rawArgsListLen);
                indexs = nIndexs;
                if (indexV == rawArgsLen - 1) {
                    int nonFullIndex = -1;
                    for (int j = indexH; j < rawArgsListLen; j++) {
                        if (indexs[j] < rawArgsList.get(j).size() - 1) {
                            nonFullIndex = j;
                            break;
                        }
                    }

                    if (nonFullIndex == -1) {
                        break outer;
                    }

                    indexs[nonFullIndex]++;
                    for (int i = 0; i < nonFullIndex; i++) {
                        indexs[i] = 0;
                    }
                    indexH = 0;
                    continue outer;
                }
            }
            indexH++;
        }
        for (int[] index : indexsList) {
            List<T> args = new LinkedList<>();
            for (int i = 0; i < index.length; i++) {
                args.add(rawArgsList.get(i).get(index[i]));
            }
            argsList.add(args);
        }
        return argsList;
    }
}
