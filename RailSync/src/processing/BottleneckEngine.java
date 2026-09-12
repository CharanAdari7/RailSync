package processing;

public class BottleneckEngine {

    public int findBottleneck(int[] capacities) {

        if (capacities == null || capacities.length == 0) {
            return -1;
        }

        int index = 0;

        for (int i = 1; i < capacities.length; i++) {

            if (capacities[i] < capacities[index]) {
                index = i;
            }
        }

        return index;
    }

    public int minimumCapacity(int[] capacities) {

        int index = findBottleneck(capacities);

        if (index == -1) {
            return 0;
        }

        return capacities[index];
    }
}