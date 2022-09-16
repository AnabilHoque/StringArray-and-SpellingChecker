import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;
    private StringArray wordsToCheck;
    private SpellingChecker spellingChecker;

    public UserInterface(Scanner scanner) {
        this.scanner = scanner;
        this.wordsToCheck = new StringArray();
        this.spellingChecker = new SpellingChecker();
    }

    private void displayOptions() {
        System.out.println("Select an option from the following: ");
        System.out.println("(1) Enter a single line of text to spell check. ");
        System.out.println("(2) Enter the name of the file containing the text to spell check (in the text folder). ");
        System.out.println("(3) Quit.");
    }

    private int optionSelection() {
        // allows the selection of one the options available in the Spelling Checking program
        int optionSelected = Integer.MAX_VALUE;
        boolean isValid = false;
        while (!isValid) {
            try {
                optionSelected = Integer.parseInt(scanner.nextLine());
                if (optionSelected >= 1 && optionSelected <= 3) {
                    isValid = true;
                } else {
                    System.out.println("You need to enter a number between 1 and 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("You haven't entered a number. ");
                System.out.println("You need to select a number between 1 and 3.");
            }
        }
        return optionSelected;
    }

    private void executeSelectedOption(int n) {
        switch (n) {
            case 1 -> execute1();
            case 2 -> execute2();
            case 3 -> execute3();
            default -> {
                System.out.println("Error! The program will terminate.");
                execute3();
            }
        }
    }

    private int correctionSelection(StringArray corrections) {
        // allows the user to select the correction to an incorrectly spelt word
        int correctionSelected = Integer.MAX_VALUE;
        boolean isValid = false;
        while (!isValid) {
            try {
                correctionSelected = Integer.parseInt(scanner.nextLine());
                if (correctionSelected >= 1 && correctionSelected <= (corrections.size() + 1) ) {
                    isValid = true;
                } else {
                    System.out.println("You need to enter a number between 1 and " + corrections.size() + 1 + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("You haven't entered a number");
            }
        }
        return correctionSelected;
    }

    private String getCorrectedText(String text, StringArray wordsToCheck) {
        // attempts to correct the text based on user choices
        StringArray wordsWithSpellingErrors = spellingChecker.getWordsWithSpellingErrors(wordsToCheck);
        for (String word : wordsWithSpellingErrors) {
            System.out.println(word + " is spelt incorrectly/ is not in the dictionary of words.");
            StringArray corrections = spellingChecker.getCorrectionsOf(word);
            if (corrections.size() >= 1) {
                System.out.println("These are the possible corrections: ");
                System.out.println(corrections);
                System.out.println(corrections.size() + 1 + ". No spelling errors/correction not in the list.");
                System.out.println("Select the most appropriate option: ");
                int correctionSelected = correctionSelection(corrections) - 1; // note indexing starts from 0, but options are displayed from 1
                if (correctionSelected != corrections.size()) {
                    text = text.replaceFirst("\\b" + word + "\\b", corrections.get(correctionSelected)); // "\\b" is used for word boundaries
                }
            } else {
                System.out.println("No corrections available.");
            }
        }
        return text;

    }

    private void execute1() {
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("Enter a single line of text to spell check: ");
        Tuple<String, StringArray> pair = Reader.readLineToSpellCheck(scanner);
        String entireLine = pair.getX();
        StringArray wordsToCheck = pair.getY();
        entireLine = getCorrectedText(entireLine, wordsToCheck);

        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("This is the corrected text: ");
        System.out.println(entireLine);
        System.out.println("---------------------------------------------------------------------------------------------");
        runDisplayOptionsAndSelection();
    }

    private void execute2() {
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("Enter the name of the file containing the text to spell check (in the text folder): ");
        String filename = "test1";
        boolean doesFileExist = false;
        while (!doesFileExist) {
            filename = scanner.nextLine();
            File possibleFile = new File("text\\" + filename + ".txt");
            if (possibleFile.exists() && !possibleFile.isDirectory()) {
                doesFileExist = true;
            } else {
                System.out.println(filename + " does not exist.");
                System.out.println("Enter a valid file name in the 'text' folder.");
            }
        }
        String entireText = Reader.getStringRepresentationOfTextFile(filename);
        StringArray wordsToCheck = Reader.readTextFileToSpellCheck(filename);
        entireText = getCorrectedText(entireText, wordsToCheck);

        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("This is the corrected text (note this will be written to a new file with name 'yourFilenameCorrected.txt'): ");
        System.out.println(entireText);
        Storage.storeCorrectedText(filename, entireText);
        System.out.println("---------------------------------------------------------------------------------------------");
        runDisplayOptionsAndSelection();
    }

    private void execute3() {
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("Thank you for using the spelling checker program.");
        System.out.println("---------------------------------------------------------------------------------------------");
        System.exit(0);
    }

    private void runDisplayOptionsAndSelection() {
        displayOptions();
        int optionSelected = optionSelection();
        executeSelectedOption(optionSelected);
    }

    public void start() {
        System.out.println("Welcome to the Spelling Checker program!");
        runDisplayOptionsAndSelection();
    }
}
