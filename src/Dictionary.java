public class Dictionary {
    private StringArray validWords; // dictionary of words that the Spelling Checker uses to recognise valid words (i.e. words spelt correctly)
    private StringArray wordsSortedByFrequency; // StringArray of words ordered by frequency, allows more efficient search of suggestions/corrections for incorrectly spelt words

    public Dictionary() {
        this.validWords = Reader.readValidWordsFile();
        this.validWords.sortStringArray(); // allows us to then perform binary search in O(logn)
        this.wordsSortedByFrequency = Reader.readWordsSortedByFrequencyFile();
    }

    public StringArray getWordsSortedByFrequency() {
        return wordsSortedByFrequency;
    }

    public boolean isInDictionary(String word) {
        return this.validWords.isInStringArray(word);
    }


}
