import java.util.HashMap;
import java.util.Map;

public class WordFrequencyCounter {
    public static Map<String, Integer> countWordFrequencies(String[] tokens) {
        Map<String, Integer> wordCounts = new HashMap<>();
        // Goes through each token
        for (String token : tokens) {
            String word = token.toLowerCase();
            // Sees if word is in map already
            if (wordCounts.containsKey(word)) {
                // if word is in map +1
                wordCounts.put(word, wordCounts.get(word) + 1);
            } else {
                // if word is not in map add it and +1
                wordCounts.put(word, 1);
            }
        }
        return wordCounts;
    }
}
