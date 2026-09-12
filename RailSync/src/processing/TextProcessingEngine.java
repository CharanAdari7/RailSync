package processing;

import java.util.ArrayList;
import java.util.List;

public class TextProcessingEngine {

    public int findFirst(String text, String pattern) {
        if (text == null || pattern == null) {
            return -1;
        }

        if (pattern.isEmpty()) {
            return 0;
        }

        int[] prefix = buildPrefix(pattern);

        int j = 0;

        for (int i = 0; i < text.length(); i++) {
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = prefix[j - 1];
            }

            if (text.charAt(i) == pattern.charAt(j)) {
                j++;
            }

            if (j == pattern.length()) {
                return i - pattern.length() + 1;
            }
        }

        return -1;
    }

    public List<Integer> findAll(String text, String pattern) {
        List<Integer> positions = new ArrayList<>();

        if (text == null || pattern == null || pattern.isEmpty()) {
            return positions;
        }

        int[] prefix = buildPrefix(pattern);
        int j = 0;

        for (int i = 0; i < text.length(); i++) {

            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = prefix[j - 1];
            }

            if (text.charAt(i) == pattern.charAt(j)) {
                j++;
            }

            if (j == pattern.length()) {
                positions.add(i - pattern.length() + 1);
                j = prefix[j - 1];
            }
        }

        return positions;
    }

    private int[] buildPrefix(String pattern) {
        int[] prefix = new int[pattern.length()];

        int length = 0;

        for (int i = 1; i < pattern.length(); i++) {

            while (length > 0
                    && pattern.charAt(i) != pattern.charAt(length)) {
                length = prefix[length - 1];
            }

            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
            }

            prefix[i] = length;
        }

        return prefix;
    }
}