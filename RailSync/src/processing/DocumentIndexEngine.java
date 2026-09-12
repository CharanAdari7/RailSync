package processing;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DocumentIndexEngine {

    private final Map<String, List<String>> index;

    public DocumentIndexEngine() {
        index = new LinkedHashMap<>();
    }

    public void addDocument(String documentId, String content) {
        if (documentId == null || documentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Document ID is required.");
        }

        if (content == null) {
            content = "";
        }

        String[] words = content.toLowerCase()
                .replaceAll("[^a-z0-9]+", " ")
                .trim()
                .split("\\s+");

        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }

            index.computeIfAbsent(word, k -> new ArrayList<>());

            if (!index.get(word).contains(documentId)) {
                index.get(word).add(documentId);
            }
        }
    }

    public List<String> search(String term) {
        if (term == null || term.trim().isEmpty()) {
            return new ArrayList<>();
        }

        List<String> result = index.get(
                term.trim().toLowerCase()
        );

        if (result == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(result);
    }

    public void clear() {
        index.clear();
    }

    public int getIndexedTermCount() {
        return index.size();
    }

    public Map<String, List<String>> getIndexSnapshot() {
        Map<String, List<String>> copy = new LinkedHashMap<>();

        for (Map.Entry<String, List<String>> entry : index.entrySet()) {
            copy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }

        return copy;
    }
}