package processing;

import java.util.ArrayList;
import java.util.List;

public class ParallelScanEngine {

    public List<Long> prefixSum(List<Integer> values) {

        List<Long> result = new ArrayList<>();

        if (values == null) {
            return result;
        }

        long running = 0;

        for (Integer value : values) {

            if (value != null) {
                running += value;
            }

            result.add(running);
        }

        return result;
    }

    public List<Integer> prefixCount(List<Boolean> values) {

        List<Integer> result = new ArrayList<>();

        if (values == null) {
            return result;
        }

        int count = 0;

        for (Boolean value : values) {

            if (Boolean.TRUE.equals(value)) {
                count++;
            }

            result.add(count);
        }

        return result;
    }
}