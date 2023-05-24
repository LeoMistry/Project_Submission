import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Array for files that are being checked
        String[] fileNames = {"test1.txt", "test2.txt", "test3.txt", "test4.txt", "test5.txt"};
        Map<String, Integer> wordCounts = new HashMap<>();
        List<String[]> fileTokens = new ArrayList<>();
        for (String fileName : fileNames) {
            try {
                // Tokenizes the files 
                String[] tokens = Tokenizer.tokenizeFile(fileName);
                fileTokens.add(tokens);
                Map<String, Integer> fileWordCounts = WordFrequencyCounter.countWordFrequencies(tokens);
                for (Map.Entry<String, Integer> entry : fileWordCounts.entrySet()) {
                    String word = entry.getKey();
                    int count = entry.getValue();
                    if (wordCounts.containsKey(word)) {
                        wordCounts.put(word, wordCounts.get(word) + count);
                    } else {
                        wordCounts.put(word, count);
                    }
                }
            } catch (IOException e) {
                System.out.println("An error occurred while tokenizing the file: " + e.getMessage());
            }
        }
        
        // Sets the length of words for phrase matching 
        int phraseLength = 4; 

        Map<String, Integer> phraseMatches = new HashMap<>();
        for (int i = 0; i < fileTokens.size(); i++) {
            String[] tokensA = fileTokens.get(i);
            for (int j = i + 1; j < fileTokens.size(); j++) {
                String[] tokensB = fileTokens.get(j);
                List<String> matchedPhrases = PhraseMatcher.matchPhrases(tokensA, tokensB, phraseLength);
                for (String phrase : matchedPhrases) {
                    if (!phraseMatches.containsKey(phrase)) {
                        phraseMatches.put(phrase, 1);
                    } else {
                        phraseMatches.put(phrase, phraseMatches.get(phrase) + 1);
                    }
                }
            }
        }

        // Sort the phraseMatches in descending order
        List<Map.Entry<String, Integer>> sortedMatches = new ArrayList<>(phraseMatches.entrySet());
        sortedMatches.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        //Prints results for word frequencies and phrase matches 
        System.out.println("Phrase matches:");
        for (Map.Entry<String, Integer> entry : sortedMatches) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\nWord frequencies:");
        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        // Generate HTML reports
        PrintResults.generateHTMLReport(phraseMatches, wordCounts, fileTokens);

    }
}
