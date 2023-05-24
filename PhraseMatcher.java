import java.util.ArrayList;
import java.util.List;

public class PhraseMatcher {
    public static List<String> matchPhrases(String[] tokensA, String[] tokensB, int phraseLength) {
        List<String> matchedPhrases = new ArrayList<>();

        // Calculate max length
        int maxLength = Math.min(tokensA.length, tokensB.length) - phraseLength + 1;

        for (int i = 0; i < maxLength; i++) {
            boolean isInQuotes = false;
            StringBuilder phraseBuilder = new StringBuilder();

            for (int j = 0; j < phraseLength; j++) {
                String tokenA = tokensA[i + j];
                String tokenB = tokensB[i + j];

                // Check if if phrase is in quotes 
                if (tokenA.startsWith("\"")) {
                    isInQuotes = !isInQuotes;
                }
                if (tokenB.startsWith("\"")) {
                    isInQuotes = !isInQuotes;
                }

                // Ignores if within quotes
                if (isInQuotes) {
                    break;
                }

                
                phraseBuilder.append(tokenA).append(" ");
            }

            // Add to list if phrase is not there
            String phrase = phraseBuilder.toString().trim();
            if (!phrase.isEmpty()) {
                matchedPhrases.add(phrase);
            }
        }

        return matchedPhrases;
    }
}
