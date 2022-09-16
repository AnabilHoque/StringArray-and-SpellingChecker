public class LevenshteinEditDistance {
    public static int computeEditDistanceFromTo(String w1, String w2) {
        // cost of transforming w1 to w2
        // based on the Levenshtein distance (delete, insert, replace)
        int[][] cache = new int[w1.length()+1][w2.length()+1];

        // base case
        for (int j = 0; j < w2.length() + 1; j++) {
            // fill up bottom row
            cache[w1.length()][j] = w2.length() - j; // base case when one string is empty and the other isn't
        }

        for (int i = 0; i < w1.length() + 1; i++) {
            // fill up rightmost column
            cache[i][w2.length()] = w1.length() - i;
        }

        // dynamic programming, bottom up
        for (int i = w1.length()-1; i >= 0; i--) {
            for (int j = w2.length()-1; j >= 0; j--) {
                if (w1.charAt(i) == w2.charAt(j)) {
                    cache[i][j] = cache[i+1][j+1];
                } else {
                    cache[i][j] = 1 + Math.min(cache[i+1][j], Math.min(cache[i][j+1], cache[i+1][j+1]));
                }
            }
        }
        return cache[0][0];
    }
}
