package processing;

public class SimilarityEngine {

    public double calculate(String first, String second) {

        if (first == null || second == null) {
            return 0.0;
        }

        if (first.equals(second)) {
            return 1.0;
        }

        if (first.isEmpty() && second.isEmpty()) {
            return 1.0;
        }

        if (first.isEmpty() || second.isEmpty()) {
            return 0.0;
        }

        String a = first.toLowerCase();
        String b = second.toLowerCase();

        int matches = 0;
        boolean[] used = new boolean[b.length()];

        for (int i = 0; i < a.length(); i++) {
            for (int j = 0; j < b.length(); j++) {

                if (!used[j] && a.charAt(i) == b.charAt(j)) {
                    matches++;
                    used[j] = true;
                    break;
                }
            }
        }

        return (2.0 * matches) / (a.length() + b.length());
    }
}