import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import tester.*;

class Util {
    int sum(ArrayList<Integer> given) {
        int a = 0;
        for (int i = 0; i< given.size(); i++) {
            a = a + given.get(i);
        }
        return a;
    }
    boolean  positivePartialSums(ArrayList<Integer> arr) {
        int b = this.sum(arr);
        return b > 0;
    }
    // is the given ArrayList of Strings sorted lexicographically?
    boolean isSorted(ArrayList<String> arr) {
        ArrayList<String> b = arr;

        while (b.size() > 1) {
            String a = b.get(b.size() - 1);
            String c = b.get(b.size() - 2);
            if (a.compareTo(c) < 0) {
                return false;
            }
            else {
                b.remove(b.size() - 1);
            }
        }
        return true;
    }
    // does source contain the sequence of Integers in sequence? 
    //    boolean containsSequence(ArrayList<Integer> source, ArrayList<Integer> sequence) {
    //        
    //    }
    int sum(Iterator<Integer> iter) {
        int answer = 1;
        while (iter.hasNext()) {
            answer = answer + 1;
            iter.remove();
        }
        return answer; 
    }
    <T> ArrayList<T> selectionSort(ArrayList<T> arr, IComparator<T> comp) {
        //T min = arr.get(0);
        ArrayList<T> source = arr;
        ArrayList<T> answer = new ArrayList<T>();
        while (source.size() > 0) {
            T min = this.getMin(source, comp);
            source.remove(min);
            answer.add(min);
        }
        return answer;
    }

    <T>  T getMin(ArrayList<T> arr, IComparator<T> comp) {
        T answer = arr.get(0);
        for (int i = 0; i < arr.size(); i++) {
            if (comp.compare(arr.get(i), answer) < 0) {
                answer = arr.get(i);
            }
        }
        return answer; 
    }
    // insertion sort for ArrayList
    <T> ArrayList<T> insertSort(ArrayList<T> source, IComparator<T> comp){
        int index = 1;
        while (index < source.size()){
            insertSorted(source.get(index), source, index, comp);
            index = index + 1;
        }
        return source;
        //         for (int i = 0; i < source.size(); i++) {
        //             int key = i;
        //             while (key + 1 < source.size() && comp.compare(source.get(key), source.get(key + 1)) > 0) {
        //                 this.swap(source, key, key + 1);
        //                 key = key + 1;
        //                 
        //             }
        //             source.set(i, source.get(key));
        //                     
        //         }
        //         return source;
    }       

    // insert the given (City) object into the given list
    // assuming elements 0 through index - 1 are sorted
    // use comp for comparison
    <T> ArrayList<T> insertSorted(T s, ArrayList<T> source, int index, IComparator<T> comp){
        int loc = index - 1;
        while ((loc >= 0) &&
                // c is smaller that the next highest element 
                (comp.compare(s, source.get(loc)) <= 0)) {
            // move element to the right
            source.set(loc + 1, source.get(loc));
            loc = loc - 1;
        }
        source.set(loc+1, s);
        return source;
    }

    <T> void swap(ArrayList<T> arr, int index1, int index2) {
        T oldValueAtIndex1 = arr.get(index1);
        T oldValueAtIndex2 = arr.get(index2);

        arr.set(index2, oldValueAtIndex1);
        arr.set(index1, oldValueAtIndex2);
    }


}

interface IComparator<T> {
    int compare(T t1, T t2);
}

class Intcompare implements IComparator<Integer> {

    public int compare(Integer t1, Integer t2) {
        if (t1 < t2) {
            return -1;

        }
        else if (t1 == t2) {
            return 0;
        }
        else {
            return 1;
        }
    }

}

class NumberIterator implements Iterator<Integer> {
    int curr = 0;
    public boolean hasNext() {
        return curr < 10;
    }
    public Integer next() {
        if (!this.hasNext()) {
            throw new RuntimeException("no more");

        }
        else {
            int answer = this .curr + 1;
            int a = this.curr;
            this.curr = a + 1;
            return answer;
        }
    }
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Don't do this!"); 
    }
}

class EvenNumberIterator implements Iterator<Integer> {
    int curr = 0;
    public boolean hasNext() {
        return true;
    }
    @Override
    public Integer next() {

        int answer = this.curr + 2;
        int a = this.curr;
        this.curr = a + 2;
        return answer;
    }
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Don't do this!");
    }
}

class StringIterator implements Iterator<String>, java.util.Iterator<String> {
    String given;
    int currIdx;
    StringIterator(String given) {
        this.given = given;
        this.currIdx = 0;
    }

    public boolean hasNext() {
        return currIdx < given.length() - 1;


    }

    @Override
    public  String next() {
        if (!this.hasNext()) {
            throw new RuntimeException("no more");
        }
        else {
            char answer = given.charAt(currIdx + 1);
            String s = "" + answer;
            this.currIdx = this.currIdx + 1;

            return s;
        } 
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Don't do this!"); 
    }

}

class EachCharacter implements Iterable<String> {
    String given;


    public java.util.Iterator<String> iterator() {

        return new StringIterator(given);
    }


}

//Represents the subsequence of the first, third, fifth, etc. items from a given sequence
class EveryOtherIter<T> implements Iterator<T> {
    Iterator<T> source;
    EveryOtherIter(Iterator<T> source) {
        this.source = source;
    }
    public boolean hasNext() {
        // this sequence has a next item if the source does
        return this.source.hasNext();
    }
    public T next() {
        T answer = this.source.next(); // gets the answer, and advances the source
        // We need to have the source "skip" the next value
        if (this.source.hasNext()) {
            this.source.next(); // get the next value and ignore it
        }
        return answer;
    }
    public void remove() {
        // We can remove an item if our source can remove the item
        this.source.remove(); // so just delegate to the source
    }
}

//Represents the ability to produce a sequence of values
//of type T, one at a time
interface Iterator<T> {
    // Does this sequence have at least one more value?
    boolean hasNext();
    // Get the next value in this sequence
    // EFFECT: Advance the iterator to the subsequent value
    T next();
    // EFFECT: Remove the item just returned by next()
    // NOTE: This method may not be supported by every iterator; ignore it for now
    void remove();
}

class Examples {
    ArrayList<Integer> a = new ArrayList<Integer>(
            Arrays.asList(2, 7, 1, 3, 4, 5));
    ArrayList<Integer> b = new ArrayList<Integer>(
            Arrays.asList(2, 7, 1, 3, 4, 5));
    Util u = new Util();
    Intcompare in = new Intcompare();

    ArrayList<Integer> sorted = u.selectionSort(a, in);
    ArrayList<Integer> sorted2 = u.insertSort(b, in);

    boolean testSelectSort(Tester t) {
        return t.checkExpect(sorted.get(5), 7) &&
                t.checkExpect(sorted.get(0), 1) &&
                t.checkExpect(sorted.get(1), 2) &&
                t.checkExpect(sorted.get(2), 3) &&
                t.checkExpect(sorted.get(3), 4) &&
                t.checkExpect(sorted.get(4), 5) &&
                t.checkExpect(sorted2.get(2), 3) &&
                t.checkExpect(sorted2.get(3), 4) &&
                t.checkExpect(sorted2.get(5), 7);
    }
   

}

























class ArrayUtils {
    // EFFECT: Sorts given ArrayList according to given comparator
    <T> void quicksort(ArrayList<T> arr, IComparator<T> comp) {
        quicksortHelp(arr, comp, 0, arr.size());
    }

    <T> void quicksortHelp(ArrayList<T> arr, IComparator<T> comp, 
            int loIdx, int hiIdx) {
        // base case: check if we're done
        if(loIdx >= hiIdx) {
            return;
        }
        // select pivot
        T pivot = arr.get(loIdx);
        // partition into less than pivot, greater than pivot
        int pivotIdx = partition(arr, comp, loIdx, hiIdx, pivot);
        // recursive step: sort each half
        quicksortHelp(arr, comp, loIdx, pivotIdx);
        quicksortHelp(arr, comp, pivotIdx + 1, hiIdx);
    }

    // EFFECT: place all elts less than the pivot to the left of it, all elts greater
    // than or equal to the pivot to the right of it.
    <T> int partition(ArrayList<T> arr, IComparator<T> comp, int loIdx,
            int hiIdx, T pivot) {
        int curLo = loIdx;
        int curHi = hiIdx - 1; // pay attention to index values for off-by-one errors
        while(curLo < curHi) { // start at left and right ends, move towards center
            while(curLo < hiIdx && comp.compare(arr.get(curLo), pivot) <= 0) {
                curLo++; // left side elt is already in the right place
            }
            while(curHi >= loIdx && comp.compare(arr.get(curHi), pivot) > 0) {
                curHi--; // right side elt is already in the right place
            }
            if(curLo < curHi) { 
                // left and right haven't met yet, so both left and right
                // elts must be out of place at this point. Swap.
                swap(arr, curLo, curHi);
            }
        }
        swap(arr, loIdx, curHi); // drop the pivot into its final resting position
        return curHi;
    }

    // EFFECT: Sorts given ArrayList according to given comparator
    <T> void mergesort(ArrayList<T> arr, IComparator<T> comp) {
        // temp array
        ArrayList<T> temp = new ArrayList<T>();
        for(int i = 0; i < arr.size(); i++) {
            temp.add(arr.get(i));
        }
        mergesortHelp(arr, temp, comp, 0, arr.size());
    }

    <T> void mergesortHelp(ArrayList<T> arr, ArrayList<T> temp, IComparator<T> comp, 
            int loIdx, int hiIdx) {
        // base case
        if(hiIdx - loIdx <= 1) {
            return;
        }
        // find the middle of array
        int midIdx = (hiIdx + loIdx) / 2;
        mergesortHelp(arr, temp, comp, loIdx, midIdx);
        mergesortHelp(arr, temp, comp, midIdx, hiIdx);
        // merge
        merge(arr, temp, comp, loIdx, midIdx, hiIdx);
    }

    <T> void merge(ArrayList<T> arr, ArrayList<T> temp, IComparator<T> comp, 
            int loIdx, int midIdx, int hiIdx) {
        int curLo = loIdx;
        int curHi = midIdx;
        int curCopy = loIdx;
        // this is the "zipper" operation: merge two sorted lists
        while(curLo < midIdx && curHi < hiIdx) {
            if(comp.compare(arr.get(curLo), arr.get(curHi)) <= 0) {
                temp.set(curCopy, arr.get(curLo));
                curLo++;
            }
            else {
                temp.set(curCopy, arr.get(curHi));
                curHi++;
            }
            curCopy++;
        }
        // by this point, one or the other of the 2 lists is exhausted, 
        // so copy the rest of the remaining list
        while(curLo < midIdx) {  // left-hand list still has elts left
            temp.set(curCopy, arr.get(curLo));
            curLo++;
            curCopy++;
        }
        while(curHi < hiIdx) {  // right-hand list still has elts left
            temp.set(curCopy, arr.get(curHi));
            curHi++;
            curCopy++;
        }
        // copy merged (sorted) list from temp to the destination
        for(int i = loIdx; i < hiIdx; i++) {
            arr.set(i, temp.get(i));
        }
    }

    <T> void swap(ArrayList<T> arr, int index1, int index2) {
        T oldValueAtIndex2 = arr.get(index2);
        arr.set(index2, arr.get(index1));
        arr.set(index1, oldValueAtIndex2);
    }
}
//Comparator with type parameter T

class IntegerComparator implements IComparator<Integer> {
    @Override
    public int compare(Integer t1, Integer t2) {
        return t1 - t2;
    }
}
class StringComparator implements IComparator<String> {
    public int compare(String t1, String t2) {
        return t1.compareTo(t2);
    }
}

class ExamplesSort {
    ArrayList<Integer> intList = new ArrayList<Integer>(
            Arrays.asList(6, 2, 7, 1, 9, 3, 4, 6, 8, 0, 5));
    ArrayList<String> strList = new ArrayList<String>(
            Arrays.asList("foo", "bar", "baz", "abc", "xyz", "cdg", 
                    "Northeastern", "Beyoncé"));
    void testQuicksort(Tester t) {
        // make a copy of the list since we will be mutating!
        ArrayList<Integer> intListCopy = new ArrayList<Integer>(this.intList);
        new ArrayUtils().quicksort(intListCopy, new IntegerComparator());
        Collections.sort(intList);
        t.checkExpect(intListCopy, intList);
    }
    void testMergesort(Tester t) {
        ArrayList<String> strListCopy = new ArrayList<String>(this.strList);
        new ArrayUtils().mergesort(strListCopy, new StringComparator());
        Collections.sort(strList);
        t.checkExpect(strListCopy, strList);
    }
}

//represent a heap abstract data type
class Heap {
 // Use a specially indexed ArrayList to represent the binary tree
 ArrayList<Integer> heap;
 // We track heapSize separately from the length of the heap arraylist
 // so we can do the heapsort trick where we use the tail of the array
 // to store the sorted elements
 int heapSize = 0;

 Heap(ArrayList<Integer> init) {
     this.heap = init;
     this.heapSize = init.size();
     // Build the heap. We only have to look at the front half of
     // the unordered arraylist because the second half are all 
     // childless leaves, so they have the heap property automatically.
     // This is (n / 2) * log(n) operations ==> O(nlogn)
     for(int i = (init.size() - 1) / 2; i >= 0; i--) {
         this.downheap(i);
     }
 }
 // no arg constructor for convenience
 Heap() {
     this(new ArrayList<Integer>());
 }
 // EFFECT: add an element to the heap
 void add(int elt) {
     this.heap.add(elt);                // add on end of arraylist, O(1)
     this.upheap(this.heap.size() - 1); // upheap recursively, O(logn)
 }
 // Remove and return the max value from the heap. This value represents
 // the "next" item in the priority queue, e.g., next patient to be seen
 // in the emergency department
 int removeMax() {
     int retVal = this.removeMaxForSort(); // see below
     this.heap.remove(this.heap.size() - 1); // not sorting, so nix the element
     return retVal;
 }
 // As above, except we are sorting, so we stick the max into the "last"
 // slot of the arraylist and decrease the heapSize
 int removeMaxForSort() {
     int retVal = this.heap.get(0);
     new ArrayUtils().swap(this.heap, 0, this.heapSize - 1);
     this.heapSize--;
     this.downheap(0);  // we need to downheap() no matter what, to preserve heap invariant
     return retVal;
 }
 // EFFECT: ensure that the element at i obeys the heap invariant
 // with respect to its parents. If not, swap with parent and recur on
 // parent.
 void upheap(int i) {
     int parentIdx = (i - 1) / 2;
     // If item at i is greater than the item at parentIdx...
     if(this.heap.get(i) > this.heap.get(parentIdx)) {
         // ...swap items at indices i and parentIdx
         new ArrayUtils().swap(this.heap, i, parentIdx);
         // recur on parent
         upheap(parentIdx);
     }
 }
 // EFFECT: ensure that elt at i obeys heap invariant with respect
 // to its children. If not, swap with largest child, recur on that
 // child.
 // Also, make sure we are not looking for children beyond the end
 // of the true heap (heapSize)
 void downheap(int i) {
     int leftIdx = 2 * i + 1;   // idx of left child
     int rightIdx = 2 * i + 2;  // idx of right child
     int biggestIdx = -1;
     if(leftIdx < heapSize && heap.get(i) < heap.get(leftIdx) 
             || rightIdx < heapSize && heap.get(i) < heap.get(rightIdx)) {
         if(leftIdx >= heapSize) {     // if left child is beyond end,
             biggestIdx = rightIdx;    // use right child
         }
         else if(rightIdx >= heapSize) {  // if right child is beyond end,
             biggestIdx = leftIdx;        // use left child
         }
         // use index of largest child
         else if(heap.get(leftIdx) > heap.get(rightIdx)) {
             biggestIdx = leftIdx;     
         }
         else {
             biggestIdx = rightIdx;
         }
         new ArrayUtils().swap(heap, i, biggestIdx);
         this.downheap(biggestIdx);
     }
 }
 public int size() {
     return heapSize;
 }
}
//utility class to execute heapsort
class HeapSorter {
 void sort(ArrayList<Integer> arr) {
     Heap heap = new Heap(arr);    // build the heap, O(nlogn)
     // execute heap sort, O(nlogn)
     while(heap.size() > 0) {
         heap.removeMaxForSort();
     }
 }
}
class ArrayUtils2 {
 <T> void swap(ArrayList<T> arr, int index1, int index2) {
     T oldValueAtIndex2 = arr.get(index2);
     arr.set(index2, arr.get(index1));
     arr.set(index1, oldValueAtIndex2);
 }
 void heapsort(ArrayList<Integer> arr) {
     new HeapSorter().sort(arr);
 }
}
class ExamplesHeapSort {
 ArrayList<Integer> intList = new ArrayList<Integer>(
         Arrays.asList(6, 2, 7, 1, 9, 3, 4, 6, 8, 0, 5));
 
 void testHeapSort(Tester t) {
     ArrayList<Integer> intListCopy = new ArrayList<Integer>(this.intList);
     new ArrayUtils2().heapsort(intListCopy);
     Collections.sort(intList);
     t.checkExpect(intListCopy, intList);
 }
}

