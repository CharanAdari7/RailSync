package processing;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class NetworkFlowEngine {

    public int calculateMaximumFlow(int[][] capacity, int source, int sink) {

        validate(capacity, source, sink);

        int n = capacity.length;

        int[][] residual = new int[n][n];

        for (int i = 0; i < n; i++) {
            residual[i] = Arrays.copyOf(capacity[i], n);
        }

        int totalFlow = 0;

        while (true) {

            int[] parent = new int[n];
            Arrays.fill(parent, -1);

            Queue<Integer> queue = new ArrayDeque<>();
            queue.add(source);
            parent[source] = source;

            while (!queue.isEmpty() && parent[sink] == -1) {

                int current = queue.poll();

                for (int next = 0; next < n; next++) {

                    if (parent[next] == -1
                            && residual[current][next] > 0) {

                        parent[next] = current;
                        queue.add(next);
                    }
                }
            }

            if (parent[sink] == -1) {
                break;
            }

            int pathFlow = Integer.MAX_VALUE;

            int current = sink;

            while (current != source) {
                int previous = parent[current];

                pathFlow = Math.min(
                        pathFlow,
                        residual[previous][current]
                );

                current = previous;
            }

            current = sink;

            while (current != source) {

                int previous = parent[current];

                residual[previous][current] -= pathFlow;
                residual[current][previous] += pathFlow;

                current = previous;
            }

            totalFlow += pathFlow;
        }

        return totalFlow;
    }

    private void validate(
            int[][] capacity,
            int source,
            int sink) {

        if (capacity == null || capacity.length == 0) {
            throw new IllegalArgumentException(
                    "Capacity network cannot be empty."
            );
        }

        int n = capacity.length;

        for (int[] row : capacity) {
            if (row == null || row.length != n) {
                throw new IllegalArgumentException(
                        "Capacity network must be square."
                );
            }
        }

        if (source < 0 || source >= n
                || sink < 0 || sink >= n) {
            throw new IllegalArgumentException(
                    "Invalid source or sink."
            );
        }

        if (source == sink) {
            throw new IllegalArgumentException(
                    "Source and sink must be different."
            );
        }
    }
}