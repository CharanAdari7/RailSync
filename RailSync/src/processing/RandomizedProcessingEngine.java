package processing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomizedProcessingEngine {

    private final Random random;

    public RandomizedProcessingEngine() {
        random = new Random();
    }

    public void shuffle(List<?> values) {

        if (values == null) {
            return;
        }

        Collections.shuffle(values, random);
    }

    public <T extends Comparable<T>> void sort(List<T> values) {

        if (values == null || values.size() < 2) {
            return;
        }

        quickSort(values, 0, values.size() - 1);
    }

    private <T extends Comparable<T>> void quickSort(
            List<T> values,
            int low,
            int high) {

        if (low >= high) {
            return;
        }

        int pivotIndex =
                low + random.nextInt(high - low + 1);

        Collections.swap(values, pivotIndex, high);

        T pivot = values.get(high);

        int index = low;

        for (int i = low; i < high; i++) {

            if (values.get(i).compareTo(pivot) <= 0) {
                Collections.swap(values, i, index);
                index++;
            }
        }

        Collections.swap(values, index, high);

        quickSort(values, low, index - 1);
        quickSort(values, index + 1, high);
    }

    public <T> List<T> copy(List<T> values) {

        if (values == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(values);
    }
}