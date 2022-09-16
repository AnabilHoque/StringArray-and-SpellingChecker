public class SpellingChecker {
    // 1. can provide a list of incorrect words based on a StringArray of given words
    //    (incorrect words are words not present in the dictionary)
    // 2. can provide a list of possible corrections to an incorrectly spelt word

    private static final Dictionary dictionary = new Dictionary();

    public StringArray getWordsWithSpellingErrors(StringArray wordsToCheck) {
        // returns a StringArray of incorrectly spelt words
        StringArray wordsWithSpellingErrors = new StringArray();
        for (String word : wordsToCheck) {
            if (!dictionary.isInDictionary(word.toLowerCase())) {
                wordsWithSpellingErrors.add(word);
            }
        }
        return wordsWithSpellingErrors;
    }

    public StringArray getCorrectionsOf(String word) {
        // returns a StringArray of corrections based on an incorrectly spelt words
        // to determine the possible corrections, the method uses a
        // combination of Levenshtein distance and the frequency of the suggestion/correction
        StringArray corrections = new StringArray();
        int maxCorrections = 10;
        for (String possibleCorrection : dictionary.getWordsSortedByFrequency()) {
            if(LevenshteinEditDistance.computeEditDistanceFromTo(word.toLowerCase(), possibleCorrection) <= 2) {
                corrections.add(possibleCorrection);
            }
            if (corrections.size() == maxCorrections) {
                // no need to show more possible corrections
                break;
            }
        }
        return corrections;
    }
}
