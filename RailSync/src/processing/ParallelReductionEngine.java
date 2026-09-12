package processing;

import java.util.List;

public class ParallelReductionEngine {

    public long sum(List<Integer> values) {

        if (values == null || values.isEmpty()) {
            return 0;
        }

        long total = 0;

        for (Integer value : values) {

            if (value != null) {
                total += value;
            }
        }

        return total;
    }

    public int maximum(List<Integer> values) {

        if (values == null || values.isEmpty()) {
            return 0;
        }

        int maximum = Integer.MIN_VALUE;

        for (Integer value : values) {

            if (value != null && value > maximum) {
                maximum = value;
            }
        }

        return maximum == Integer.MIN_VALUE ? 0 : maximum;
    }

    public int minimum(List<Integer> values) {

        if (values == null || values.isEmpty()) {
            return 0;
        }

        int minimum = Integer.MAX_VALUE;

        for (Integer value : values) {

            if (value != null && value < minimum) {
                minimum = value;
            }
        }

        return minimum == Integer.MAX_VALUE ? 0 : minimum;
    }
}