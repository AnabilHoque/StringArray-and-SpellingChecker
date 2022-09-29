import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

public class StringArray implements Iterable<String> {
    private String[] internalArray;
    private int count = 0; // counts the actual number of elements
    private int capacity = 100; // initial default capacity

    private boolean isSorted = false; // initially StringArray is not sorted

    public StringArray() {
        // initialize new StringArray
        this.internalArray = new String[this.capacity];
    }

    public StringArray(StringArray a) {
        // this StringArray contains copy of string references from 'StringArray a'
        this.count = a.size(); //
        this.capacity = a.getTotalArrayCapacity();
        this.internalArray = new String[this.capacity];
        this.isSorted = a.getIsSorted();
    }

    public int size() {
        // returns the number of string references stored in 'internalArray'
        return this.count;
    }

    public int getTotalArrayCapacity() {
        // returns the number of string references that can be stored in total in 'internalArray' before resizing
        return this.capacity;
    }

    public boolean getIsSorted() {
        // returns whether the StringArray is sorted or not, note by default is false unless you use built-in sortStringArray method
        return this.isSorted;
    }

    public boolean isEmpty() {
        // returns True if StringArray is empty, else False
        return this.count == 0;
    }

    public String get(int index) {
        // returns string reference at 'index' position, else returns null if index invalid
        if ( !(0 <= index && index < this.count) ) {
            return null;
        }
        return this.internalArray[index];
    }

    public void set(int index, String s) {
        // overwrite string stored at index with String s if the index is valid
        if ( !(0 <= index && index < this.count)) {
            return;
        }
        this.internalArray[index] = s;
        this.isSorted = false;
    }

    public void add(String s) {
        // add String s reference at the end of the StringArray, resize if necessary
        if (this.count == this.internalArray.length) {
            resize(2 * this.capacity);
        }
        this.internalArray[this.count] = s;
        this.count++;
        this.isSorted = false;
    }

    public void insert(int index, String s) {
        // insert String s reference at index, moving subsequent elements rightwards
        if (index == 0 && this.count == 0) {
            // StringArray is empty so can only insert at index 0
            this.internalArray[index] = s;
            this.count++;
        } else if (0 <= index && index < this.count) {
            shiftInsert(index, s);
        }
        this.isSorted = false;
    }

    public void remove(int index) {
        // remove string at index, moving all subsequent elements leftwards
        if ( !(0 <= index && index < this.count)) {
            return;
        }
        shiftRemove(index);
        if (this.count <= (int)(0.25 * this.capacity)) {
            resize((int)(0.5 * this.capacity));
        }
    }

    public boolean contains(String s) {
        // returns True if String s in ArrayString, else False (ignore upper/lower case of letters)
        for (String toCompare : this.internalArray) {
            if (toCompare.toLowerCase().equals(s)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsMatchingCase(String s) {
        // returns True if s in ArrayString, else False
        for (String toCompare : this.internalArray) {
            if (toCompare.equals(s)) {
                return true;
            }
        }
        return false;
    }

    public int indexOf(String s) {
        // returns index of first matching String, else -1 (ignore upper/lower case of letters)
        for (int i = 0; i < this.count; i++) {
            if (this.internalArray[i].toLowerCase().equals(s)) {
                return i;
            }
        }
        return -1;
    }

    public int indexOfMatchingCase(String s) {
        // returns index of first matching String, else -1
        for (int i = 0; i < this.count; i++) {
            if (this.internalArray[i].equals(s)) {
                return i;
            }
        }
        return -1;
    }

    private void shiftRemove(int index) {
        // remove at index, shifting subsequent values leftwards
        for (int i = index; i < this.count-1; i++) {
            this.internalArray[i] = this.internalArray[i+1];
        }
        this.count--;
    }

    private void shiftInsert(int index, String s) {
        // insert at index, shifting subsequent values rightward
        if (this.count == this.internalArray.length) {
            resize(2 * this.capacity);
        }
        for (int i = this.count; i > index; i--) {
            this.internalArray[i] = this.internalArray[i-1]; // shifting
        }
        this.internalArray[index] = s; // store newest element s
        this.count++;
    }

    private void resize(int newCapacity) {
        // resize internal array with newCapacity (copying the already stored elements)
        String[] newInternalArray = new String[newCapacity];
        for (int i = 0; i < this.count; i++) {
            newInternalArray[i] = this.internalArray[i];
        }
        this.internalArray = newInternalArray;
        this.capacity = newCapacity;
    }

    public void sortStringArray() {
        // Sort the elements in the StringArray
        Stream<String> transformedStringStream = Arrays.stream(this.internalArray).filter(Objects::nonNull).sorted();
        String[] sortedStringArray = transformedStringStream.toArray(String[]::new);
        for (int i = 0; i < sortedStringArray.length; i++) {
            this.internalArray[i] = sortedStringArray[i];
        }
        this.isSorted = true;
    }

    public boolean isInStringArray(String s) {
        // Based on whether the StringArray is sorted or not, decide whether to perform a linear search or binary search
        if (!this.isSorted) {
            return linearSearch(s);
        } else {
            return binarySearch(s);
        }
    }

    private boolean linearSearch(String s) {
        // returns True if String s in StringArray, else False
        for (String possibleMatch : this.internalArray) {
            if (s.equals(possibleMatch)) {
                return true;
            }
        }
        return false;
    }

    private boolean binarySearch(String s) {
        // returns True if String s in StringArray, else False
        // Assumes StringArray is Sorted
        int low = 0;
        int high = this.count-1;
        while (low <= high) {
            int mid = ((high + low) / 2);
            if (this.internalArray[mid].compareTo(s) == 0) {
                return true;

            } else if (this.internalArray[mid].compareTo(s) < 0) {
                // s comes after the string stored at internalArray[mid]
                low = mid + 1;
            } else {
                // s comes before the string stored at internalArray[mid]
                high = mid - 1;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int currIndex = 0;
        while (currIndex < this.count - 1) {
            sb.append(currIndex+1).append(". ").append(this.internalArray[currIndex]).append('\n');
            currIndex++;
        }
        sb.append(currIndex+1).append(". ").append(this.internalArray[currIndex]);
        return sb.toString();
    }

    @Override
    public Iterator<String> iterator() {
        return new StringArrayIterator();
    }

    private class StringArrayIterator implements Iterator<String> {
        private int currIndex = 0;

        @Override
        public boolean hasNext() {
            return this.currIndex < count;
        }

        @Override
        public String next() {
            if (currIndex < count) {
                String currString = internalArray[currIndex];
                this.currIndex++;
                return currString;
            }

            throw new NoSuchElementException();
        }
    }
}