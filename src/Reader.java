import java.nio.file.Paths;
import java.util.Scanner;

public class Reader {
    public static StringArray readValidWordsFile() {
        // reads the validWords file in the resources' folder, storing it in a StringArray
        StringArray validWords = new StringArray();
        try (Scanner scanner = new Scanner(Paths.get(Reader.class.getResource("validWords").toURI()))){
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                validWords.add(word);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return validWords;
    }
    public static StringArray readWordsSortedByFrequencyFile() {
        // reads the wordsSortedByFrequency file in the resources' folder, storing it in a StringArray
        StringArray wordsSortedByFrequency = new StringArray();
        try (Scanner scanner = new Scanner(Paths.get(Reader.class.getResource("wordsSortedByFrequency").toURI()))){
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\s+");
                wordsSortedByFrequency.add(parts[0]);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return wordsSortedByFrequency;
    }

    public static Tuple<String, StringArray> readLineToSpellCheck(Scanner scanner) {
        // reads a line from the terminal returning both the entire line as a string and the individual words in that line which are stored in a StringArray
        StringArray wordsInSentence = new StringArray();
        String line = scanner.nextLine();
        String[] parts = line.split("\\W+");
        for (String word : parts) {
            wordsInSentence.add(word);
        }
        return new Tuple<String, StringArray>(line, wordsInSentence);
    }

    public static StringArray readTextFileToSpellCheck(String filename) {
        // reads the text in 'filename' and returns a StringArray of the individual words in that file
        StringArray wordsInTextFile = new StringArray();
        try (Scanner scanner = new Scanner(Paths.get("text\\" + filename + ".txt"))){
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals("")) {
                    // if line is a newline
                    continue;
                }
                String[] parts = line.split("\\W+");
                for (String part : parts) {
                    wordsInTextFile.add(part);
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return wordsInTextFile;
    }

    public static String getStringRepresentationOfTextFile(String filename) {
        // returns the entire text in 'filename' as a string
        StringBuilder sb = new StringBuilder();
        try (Scanner scanner = new Scanner(Paths.get("text\\" + filename + ".txt"))){
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                sb.append(line).append("\n");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return sb.toString();
    }
}
