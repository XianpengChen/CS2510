import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.Iterator;
import java.util.Random;


class Cell {
    int x;
    int y;
    Cell representative;
    static final int CELL_SIZE = 11;
    Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.representative = null;
        
    }
}

class Maze extends  World {
    int width;
    int height;
    ArrayList<Edge> edges;
    ArrayList<Cell> cells;
    @Override
    public WorldScene makeScene() {
        // TODO Auto-generated method stub
        return null;
    }
    
}



class Edge {
    String direction;// "vertical" or "horizontal"
    Cell a;
    Cell b;
    int weight;
    static final int EDGE_SIZE = 2;
    Edge(String direction, Cell a, Cell b, int weight) {
        this.direction = direction;
        this.a = a;
        this.b = b;
        this.weight = weight;
      
    }
    
}

//utility class 
class util {
    
    ArrayList<IList> convert(ArrayList<Edge> ilist) {
  
        for (int i = 0; i < ilist.size(); i++) {
            Edge ie = ilist.get(i);
            ilist.remove(i);
  
            for (int j = 0; j < ilist.size(); i++) {
                Edge je = ilist.get(j);
                
                
            }
            
            
            
        }
        
    }
    boolean haveAPath(IList<Edge> ilist) {
        
    }
        if ()
    }
    ArrayList<ArrayList<Cell>> buildGrid(int width, int height) {
        ArrayList<ArrayList<Cell>> setup = new ArrayList<ArrayList<Cell>>();
        for (int y = 1; y <= height; y++) {
            ArrayList<Cell> row = new  ArrayList<Cell>();
            for(int x = 1; x <= width; x++) {
                row.add(new Cell(x, y)); 
            }
            setup.add(row);  
        }
        return setup;  
    }
    //convert  ArrayList<ArrayList<Cell>> to ArrayList<Cell>
    ArrayList<Cell> convert(ArrayList<ArrayList<Cell>> arr) {
        ArrayList<Cell> set = new ArrayList<Cell>();
        for (int i = 0; i < arr.size(); i++) {
            for (int j = 0; j < arr.get(1).size(); j++) {
                Cell component = arr.get(i).get(j);
                set.add(component);
            }
            return set;
        }     
    }
    //build those horizontal edges in maze
    ArrayList<Edge> buildHorizontalEdge(ArrayList<ArrayList<Cell>> arr) {
        ArrayList<Edge> set = new ArrayList<Edge>();
        for (int i = 0; i < arr.size() - 1; i++) {
            for (int j = 0; j < arr.get(1).size(); j++) {
                Edge a = new Edge("horizontal", arr.get(i).get(j), arr.get(i + 1).get(j), 1);
                set.add(a);
            }
        }
        return set;  
    }
    //build those vertical edges in maze
    ArrayList<Edge> buildVerticalEdge(ArrayList<ArrayList<Cell>> arr) {
       ArrayList<Edge> set = new ArrayList<Edge>();
        for (int i = 0; i < arr.size(); i++) {
            for (int j = 0; j < arr.get(1).size() - 1; j++) {
                Edge b = new Edge("vertical", arr.get(i).get(j), arr.get(i).get(j + 1), 1);
                set.add(b);
            }
        }
        return set;
    }

/*** IList infrastructure code from previous lectures ***/
//Predicate interface using a "type parameter"
interface IPred<T> {
  boolean apply(T t);
}
//Comparator with type parameter T
interface IComparator<T> {
  int compare(T t1, T t2);
}
//Function object interface for a "map" style function. Take an argument
//of type A return something of type R. E.g., take a Circle, return its
//circumference (a Double)
interface IFunc<A, R> {
  R apply(A arg);
}
//Function object interface for a "fold" style function. Take two args
//of type A1, A2 and return an R. Example: take a Book and an Integer,
//return an Integer (for totalPrice)
interface IFunc2<A1, A2, R> {
  R apply(A1 arg1, A2 arg2);
}

//a generic list: could hold anything!
interface IList<T> extends Iterable<T> {
  int length();
  IList<T> filter(IPred<T> p);
  IList<T> sort(IComparator<T> c);
  IList<T> insert(T t, IComparator<T> c);

  //In IList<T>, map is a method parameterized by U, that takes a 
  //function from T values to U values, and produces an IList<U> result.
  <U> IList<U> map(IFunc<T, U> f);
  // foldr is a method parameterized by U, that takes a function from
  // two values of type T and U and returns a U
  <U> U foldr(IFunc2<T, U, U> func, U base);
  boolean isCons();
  ConsList<T> asCons();
}

class MtList<T> implements IList<T> {
  public int length() {
      return 0;
  }
  public IList<T> filter(IPred<T> p) {
      return this;
  }
  public IList<T> sort(IComparator<T> c) {
      return this;
  }
  public IList<T> insert(T t, IComparator<T> c) {
      return new ConsList<T>(t, new MtList<T>());
  }
  public <U> IList<U> map(IFunc<T, U> f) {
      return new MtList<U>();
  }
  public <U> U foldr(IFunc2<T, U, U> func, U base) {
      return base;
  }

  public boolean isCons() {
      return false;
  }
  public ConsList<T> asCons() {
      throw new RuntimeException("I'm not a Cons!");
  }
  @Override
  public Iterator<T> iterator() {
      return new ListIterator<T>(this);
  }
}
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;
  ConsList(T first, IList<T> rest) {
      this.first = first;
      this.rest = rest;
  }
  public int length() {
      return 1 + this.rest.length();
  }
  public IList<T> filter(IPred<T> p) {
      if(p.apply(this.first)) {
          return new ConsList<T>(this.first, this.rest.filter(p));
      }
      else {
          return this.rest.filter(p);
      }
  }
  public IList<T> sort(IComparator<T> c) {
      return this.rest.sort(c).insert(this.first, c);
  }
  // insert given item into sorted list
  public IList<T> insert(T t, IComparator<T> c) {
      if(c.compare(this.first, t) < 0) {
          return new ConsList<T>(this.first, this.rest.insert(t, c));
      }
      else {
          return new ConsList<T>(t, this);
      }
  }
  public <U> IList<U> map(IFunc<T, U> f) {
      return new ConsList<U>(f.apply(this.first), this.rest.map(f));
  }
  public <U> U foldr(IFunc2<T, U, U> func, U base) {
      return func.apply(this.first, this.rest.foldr(func, base));
  }

  public boolean isCons() {
      return true;
  }
  public ConsList<T> asCons() {
      return this;
  }
  @Override
  public Iterator<T> iterator() {
      return new ListIterator<T>(this);
  }
}
class ListIterator<T> implements Iterator<T> {

  IList<T> items;
  ListIterator(IList<T> items) {
      this.items = items;
  }
  @Override
  public boolean hasNext() {
      return this.items.isCons();
  }

  @Override
  public T next() {
      T nxt = this.items.asCons().first;
      this.items = this.items.asCons().rest;
      return nxt;
  }
}
/*** End of IList infrastructure code ***/