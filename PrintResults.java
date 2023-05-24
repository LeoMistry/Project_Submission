import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PrintResults {
    public static void generateHTMLReport(Map<String, Integer> phraseMatches, Map<String, Integer> wordCounts, List<String[]> fileTokens) {
        generatePhraseMatchesPage(phraseMatches);
        generateWordFrequencyPages(wordCounts, fileTokens);
        generateFileComparisonPage(fileTokens);
    }

    private static void generatePhraseMatchesPage(Map<String, Integer> phraseMatches) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("phrase_matches.html"))) {
            writer.write("<html>");
            writer.write("<head><title>Phrase Matches</title></head>");
            writer.write("<body>");
        
            writer.write("<h1>Phrase Matches:</h1>");
            writer.write("<table style=\"border: 1px solid black;\">");
            writer.write("<tr><th>Phrase</th><th>Count</th></tr>");
            // Write phrase matches into table 
            for (Map.Entry<String, Integer> entry : phraseMatches.entrySet()) {
                writer.write("<tr>");
                writer.write("<td style=\"border: 1px solid black;\">" + entry.getKey() + "</td>");
                writer.write("<td style=\"border: 1px solid black;\">" + entry.getValue() + "</td>");
                writer.write("</tr>");
            }
            writer.write("</table>");

            writer.write("</body>");
            writer.write("</html>");
        } catch (IOException e) {
            System.out.println("An error occurred while generating the Phrase Matches page: " + e.getMessage());
        }
    }

    private static void generateWordFrequencyPages(Map<String, Integer> wordCounts, List<String[]> fileTokens) {
        // Generate the word frequency pages for each file separately
        int fileIndex = 1;
        for (String[] tokens : fileTokens) {
            String fileName = "file_" + fileIndex + "_word_frequency.html";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write("<html>");
                writer.write("<head><title>Word Frequency - File " + fileIndex + "</title></head>");
                writer.write("<body>");

                writer.write("<h1>Word Frequency - File " + fileIndex + "</h1>");
                writer.write("<table style=\"border: 1px solid black;\">");
                writer.write("<tr><th>Word</th><th>Frequency</th></tr>");
                // Write word frequencies into table
                for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
                    String word = entry.getKey();
                    int frequency = entry.getValue();
                    writer.write("<tr>");
                    writer.write("<td style=\"border: 1px solid black;\">" + word + "</td>");
                    writer.write("<td style=\"border: 1px solid black;\">" + frequency + "</td>");
                    writer.write("</tr>");
                }
                writer.write("</table>");

                writer.write("</body>");
                writer.write("</html>");
            } catch (IOException e) {
                System.out.println("An error occurred while generating the Word Frequency page for File " + fileIndex + ": " + e.getMessage());
            }
            fileIndex++;
        }
    }

    private static void generateFileComparisonPage(List<String[]> fileTokens) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("file_comparison.html"))) {
            writer.write("<html>");
            writer.write("<head><title>File Comparison</title></head>");
            writer.write("<body>");
            writer.write("<h1>Match Percentage:</h1>");
            writer.write("<table style=\"border: 1px solid black;\">");
            writer.write("<tr><th>File A</th><th>File B</th><th>Match Percentage</th></tr>");
            // Compare each file againt the others and enter match percentage 
            for (int i = 0; i < fileTokens.size(); i++) {
                String[] tokensA = fileTokens.get(i);
                for (int j = i + 1; j < fileTokens.size(); j++) {
                    String[] tokensB = fileTokens.get(j);
                    double matchPercentage = calculateMatchPercentage(tokensA, tokensB);
                    String formattedPercentage = String.format("%.1f", matchPercentage);
                    writer.write("<tr>");
                    writer.write("<td style=\"border: 1px solid black;\">File " + (i + 1) + "</td>");
                    writer.write("<td style=\"border: 1px solid black;\">File " + (j + 1) + "</td>");
                    writer.write("<td style=\"border: 1px solid black;\">" + formattedPercentage + "%</td>");
                    writer.write("</tr>");
                }
            }
            writer.write("</table>");
            writer.write("</body>");
            writer.write("</html>");
        } catch (IOException e) {
            System.out.println("An error occurred while generating the File Comparison page: " + e.getMessage());
        }
    }
    
    // Counts tokens that match between two arrays 
    private static double calculateMatchPercentage(String[] tokensA, String[] tokensB) {
        int totalTokens = Math.max(tokensA.length, tokensB.length);
        int matchedTokens = 0;
        for (String tokenA : tokensA) {
            for (String tokenB : tokensB) {
                if (tokenA.equals(tokenB)) {
                    matchedTokens++;
                    break;
                }
            }
        }
        return (double) matchedTokens / totalTokens * 100.0;
    }
}    
