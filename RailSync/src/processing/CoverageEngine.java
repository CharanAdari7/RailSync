package processing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CoverageEngine {

    public Set<String> coveredNodes(
            List<Set<String>> groups) {

        Set<String> covered = new HashSet<>();

        if (groups == null) {
            return covered;
        }

        for (Set<String> group : groups) {

            if (group != null) {
                covered.addAll(group);
            }
        }

        return covered;
    }

    public double coveragePercentage(
            Set<String> covered,
            Set<String> total) {

        if (total == null || total.isEmpty()) {
            return 0.0;
        }

        int count = 0;

        if (covered != null) {
            for (String value : total) {

                if (covered.contains(value)) {
                    count++;
                }
            }
        }

        return ((double) count / total.size()) * 100.0;
    }
}