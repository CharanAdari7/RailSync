package processing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConflictReductionEngine {

    public List<String> selectNonConflicting(
            List<String> items,
            List<Set<String>> conflicts) {

        List<String> selected = new ArrayList<>();
        Set<String> blocked = new HashSet<>();

        if (items == null || conflicts == null) {
            return selected;
        }

        for (int i = 0; i < items.size(); i++) {

            String item = items.get(i);

            if (item == null || blocked.contains(item)) {
                continue;
            }

            selected.add(item);

            if (i < conflicts.size()
                    && conflicts.get(i) != null) {

                blocked.addAll(conflicts.get(i));
            }
        }

        return selected;
    }
}