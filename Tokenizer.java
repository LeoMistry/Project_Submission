import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Tokenizer {
    public static String[] tokenizeFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line;

            //Reads the file line by line 
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String contents = sb.toString();
            // Converts to lowercase and removes punctuation and digits
            contents = contents.replaceAll("[\\p{Punct}\\d]", "").toLowerCase();
            StringTokenizer st = new StringTokenizer(contents);
            String[] tokens = new String[st.countTokens()];
            int i = 0;
            // Stores tokens in an array
            while (st.hasMoreTokens()) {
                tokens[i++] = st.nextToken();
            }
            return tokens;
        }
    }
}
