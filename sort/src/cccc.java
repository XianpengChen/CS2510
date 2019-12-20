import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.Iterator;
import java.util.Random;

interface IComparator<T> {
    int compare(T a, T b);
}

class IntCompare implements IComparator<Integer> {

    @Override
    public int compare(Integer a, Integer b) {
        if (a > b) {
            return 1;
        }
        else if (a < b) {
            return -1;
        }
        else {
            return 0;
        }   
    }
}


//In ArrayUtils
//EFFECT: Sorts the given ArrayList according to the given comparator
class Util {
    <T> void quicksortCopying(ArrayList<T> arr, IComparator<T> comp) {
        // Create a temporary array
        ArrayList<T> temp = new ArrayList<T>();
        // Make sure the temporary array is exactly as big as the given array
        for (int i = 0; i < arr.size(); i = i + 1) {
            temp.add(arr.get(i));
        }
        quicksortCopyingHelp(arr, temp, comp, 0, arr.size());
    }


    //EFFECT: sorts the source array according to comp, in the range of indices [loIdx, hiIdx)
    <T> void quicksortCopyingHelp(ArrayList<T> source, ArrayList<T> temp, IComparator<T> comp,
            int loIdx, int hiIdx) {
        // Step 0: check for completion
        if (loIdx >= hiIdx) {
            return; // There are no items to sort
        }
        // Step 1: select pivot
        T pivot = source.get(loIdx);
        // Step 2: partition items to lower or upper portions of the temp list
        int pivotIdx = partitionCopying(source, temp, comp, loIdx, hiIdx, pivot);
        // Step 4: sort both halves of the list
        quicksortCopyingHelp(source, temp, comp, loIdx, pivotIdx);
        quicksortCopyingHelp(source, temp, comp, pivotIdx + 1, hiIdx);
    }
    //Returns the index where the pivot element ultimately ends up in the sorted source
    //EFFECT: Modifies the source and comp lists in the range [loIdx, hiIdx) such that
    //      all values to the left of the pivot are less than (or equal to) the pivot
    //      and all values to the right of the pivot are greater than it
    <T> int partitionCopying(ArrayList<T> source, ArrayList<T> temp, IComparator<T> comp,
            int loIdx, int hiIdx, T pivot) {
        int curLo = loIdx;
        int curHi = hiIdx - 1;
        // Notice we skip the loIdx index, because that's where the pivot was
        for (int i = loIdx + 1; i < hiIdx; i = i + 1) {
            if (comp.compare(source.get(i), pivot) <= 0) { // lower
                temp.set(curLo, source.get(i));
                curLo = curLo + 1; // advance the current lower index
            }
            else { // upper
                temp.set(curHi, source.get(i));
                curHi = curHi - 1; // advance the current upper index
            }
        }
        temp.set(curLo, pivot); // place the pivot in the remaining spot
        // Step 3: copy all items back into the source
        for (int i = loIdx; i < hiIdx; i = i + 1) {
            source.set(i, temp.get(i));
        }
        return curLo;
    }
}