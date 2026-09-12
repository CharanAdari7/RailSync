package processing;

import java.util.ArrayList;
import java.util.List;

public class SearchOptimizationEngine {

    public List<String> orderedCandidates(
            List<String> values,
            String keyword) {

        List<String> result = new ArrayList<>();

        if (values == null) {
            return result;
        }

        String target = keyword == null
                ? ""
                : keyword.trim().toLowerCase();

        for (String value : values) {

            if (value == null) {
                continue;
            }

            String lower = value.toLowerCase();

            if (target.isEmpty()
                    || lower.startsWith(target)
                    || lower.contains(target)) {

                result.add(value);
            }
        }

        result.sort((a, b) -> {

            int pa = priority(a, target);
            int pb = priority(b, target);

            if (pa != pb) {
                return Integer.compare(pa, pb);
            }

            return a.compareToIgnoreCase(b);
        });

        return result;
    }

    private int priority(String value, String target) {

        String lower = value.toLowerCase();

        if (lower.equals(target)) {
            return 0;
        }

        if (lower.startsWith(target)) {
            return 1;
        }

        return 2;
    }
}