package processing;

public class CapacityEngine {

    public double utilization(int occupied, int capacity) {

        if (capacity <= 0) {
            return 0.0;
        }

        if (occupied < 0) {
            occupied = 0;
        }

        return ((double) occupied / capacity) * 100.0;
    }

    public boolean isOverCapacity(int occupied, int capacity) {

        if (capacity < 0) {
            return false;
        }

        return occupied > capacity;
    }

    public int remainingCapacity(int occupied, int capacity) {

        if (capacity <= 0) {
            return 0;
        }

        return Math.max(0, capacity - occupied);
    }
}