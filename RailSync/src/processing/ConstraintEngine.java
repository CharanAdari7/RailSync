package processing;

import java.util.ArrayList;
import java.util.List;

public class ConstraintEngine {

    public boolean satisfiesAll(
            List<Boolean> constraints) {

        if (constraints == null) {
            return true;
        }

        for (Boolean value : constraints) {

            if (value == null || !value) {
                return false;
            }
        }

        return true;
    }

    public List<String> failedConstraints(
            List<String> names,
            List<Boolean> values) {

        List<String> failed = new ArrayList<>();

        if (names == null || values == null) {
            return failed;
        }

        int count = Math.min(names.size(), values.size());

        for (int i = 0; i < count; i++) {

            Boolean value = values.get(i);

            if (value == null || !value) {
                failed.add(names.get(i));
            }
        }

        return failed;
    }
}