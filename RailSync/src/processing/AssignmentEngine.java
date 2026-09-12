package processing;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AssignmentEngine {

    public Map<String, String> assign(
            List<String> workers,
            List<String> tasks) {

        Map<String, String> assignments = new LinkedHashMap<>();

        if (workers == null || tasks == null) {
            return assignments;
        }

        int count = Math.min(workers.size(), tasks.size());

        for (int i = 0; i < count; i++) {

            String worker = workers.get(i);
            String task = tasks.get(i);

            if (worker != null && task != null) {
                assignments.put(task, worker);
            }
        }

        return assignments;
    }
}