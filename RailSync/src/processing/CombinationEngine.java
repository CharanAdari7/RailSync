package processing;

import java.util.ArrayList;
import java.util.List;

public class CombinationEngine {

    public List<List<Integer>> generateSubsets(List<Integer> values) {

        List<List<Integer>> result = new ArrayList<>();

        if (values == null) {
            return result;
        }

        int n = values.size();

        if (n >= 31) {
            throw new IllegalArgumentException(
                    "Too many elements for subset generation."
            );
        }

        int total = 1 << n;

        for (int mask = 0; mask < total; mask++) {

            List<Integer> subset = new ArrayList<>();

            for (int i = 0; i < n; i++) {

                if ((mask & (1 << i)) != 0) {
                    subset.add(values.get(i));
                }
            }

            result.add(subset);
        }

        return result;
    }

    public int minimumGroups(int[] values, int groupSize) {

        if (values == null || groupSize <= 0) {
            return 0;
        }

        return (values.length + groupSize - 1) / groupSize;
    }
}