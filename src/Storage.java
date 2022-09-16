import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Storage {
    public static void storeCorrectedText(String previousFileWithIncorrectSpelling, String correctedText) {
        // stores the text with corrected spelling of words in a new text file (has a Corrected.txt suffix)
        createFileToStoreAndWrite(previousFileWithIncorrectSpelling, correctedText);
    }

    private static void createFileToStoreAndWrite(String previousFileWithIncorrectSpelling, String correctedText) {
        // ensures the new file exists (with the Corrected.txt suffix)
        try {
            File newFile = new File("text\\" + previousFileWithIncorrectSpelling + "Corrected.txt");
            newFile.createNewFile(); // Create new file if it does not exist
            PrintWriter writer = new PrintWriter("text\\" + previousFileWithIncorrectSpelling + "Corrected.txt");
            writer.write(correctedText);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
