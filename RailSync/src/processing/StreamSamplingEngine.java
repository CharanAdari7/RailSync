package processing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StreamSamplingEngine {

    private final Random random;

    public StreamSamplingEngine() {
        random = new Random();
    }

    public <T> List<T> sample(
            Iterable<T> stream,
            int sampleSize) {

        List<T> reservoir = new ArrayList<>();

        if (stream == null || sampleSize <= 0) {
            return reservoir;
        }

        int count = 0;

        for (T item : stream) {

            count++;

            if (reservoir.size() < sampleSize) {
                reservoir.add(item);
            } else {

                int index = random.nextInt(count);

                if (index < sampleSize) {
                    reservoir.set(index, item);
                }
            }
        }

        return reservoir;
    }
}