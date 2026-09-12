package processing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

public class ParallelWorkEngine {

    public <T, R> List<R> process(
            List<T> values,
            int threadCount,
            Function<T, R> task) {

        List<R> result = new ArrayList<>();

        if (values == null || values.isEmpty() || task == null) {
            return result;
        }

        if (threadCount <= 0) {
            threadCount = 1;
        }

        ExecutorService executor =
                Executors.newFixedThreadPool(threadCount);

        try {

            List<Future<R>> futures = new ArrayList<>();

            for (T value : values) {

                futures.add(
                        executor.submit(() -> task.apply(value))
                );
            }

            for (Future<R> future : futures) {
                result.add(future.get());
            }

        } catch (Exception e) {

            throw new IllegalStateException(
                    "Parallel processing failed.",
                    e
            );

        } finally {
            executor.shutdown();
        }

        return result;
    }

    public <T> List<List<T>> partition(
            List<T> values,
            int partitions) {

        List<List<T>> result = new ArrayList<>();

        if (values == null || values.isEmpty()) {
            return result;
        }

        if (partitions <= 0) {
            partitions = 1;
        }

        int actualPartitions =
                Math.min(partitions, values.size());

        int baseSize =
                values.size() / actualPartitions;

        int remainder =
                values.size() % actualPartitions;

        int index = 0;

        for (int i = 0; i < actualPartitions; i++) {

            int size =
                    baseSize + (i < remainder ? 1 : 0);

            List<T> partition = new ArrayList<>();

            for (int j = 0; j < size; j++) {
                partition.add(values.get(index++));
            }

            result.add(partition);
        }

        return result;
    }
}