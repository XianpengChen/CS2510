package student;

import SortingAlgorithms.SortAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by User on 1/3/2016.
 */
public abstract class SortingHeapSort<T> implements SortAlgorithm<T> {
  List<T> data;

  @Override
  public void init(List<T> list) {
    data = new ArrayList<>();
    data.addAll(list);
  }

  @Override
  public List<T> sort(Comparator<T> comp) {return heapsort(data, comp);
  }

  public abstract List<T> heapsort(List<T> list, Comparator<T> comp);
}
