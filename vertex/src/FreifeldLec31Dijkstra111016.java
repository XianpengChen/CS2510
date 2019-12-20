import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import tester.Tester;

class Vertex {
    String name;
    IList<Edge> outEdges;
    Vertex(String name) {
        this.name = name;
        this.outEdges = new MtList<Edge>();
    }
    void setOutEdges(IList<Edge> outEdges) {
        this.outEdges = outEdges;
    }
}

class Edge {
    Vertex to, from;
    int weight;
    Edge(Vertex from, Vertex to, int weight) {
        this.to = to;
        this.from = from;
        this.weight = weight;
    }
}

class Graph {
    IList<Vertex> allVertices;
    Graph(IList<Vertex> allVertices) {
        this.allVertices = allVertices;
    }
}

class GraphUtils {
    ArrayList<Vertex> shortestPath(Vertex source, Vertex target) {
        HashSet<Vertex> unvisitedNodes = new HashSet<Vertex>();
        HashMap<Vertex, Integer> distance = new HashMap<Vertex, Integer>();
        HashMap<Vertex, Vertex> predecessors = new HashMap<Vertex, Vertex>();
        distance.put(source, 0);
        unvisitedNodes.add(source);
        while(unvisitedNodes.size() > 0) {
            Vertex v = this.getMin(unvisitedNodes, distance);
            unvisitedNodes.remove(v);
            for(Edge e : v.outEdges) {
                if(distance.get(e.to) == null || distance.get(e.to) > distance.get(v) + e.weight) {
                    distance.put(e.to, distance.get(v) + e.weight);
                    unvisitedNodes.add(e.to);
                    predecessors.put(e.to, v);
                }
            }
        }
        // get path to target
        ArrayList<Vertex> path = new ArrayList<Vertex>();
        Vertex step = target;
        if(predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while(predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(0, step);
        }
        return path;
        //return distance.get(target); // return total weight of path
    }

    Vertex getMin(HashSet<Vertex> unvisitedNodes, HashMap<Vertex, Integer> distance) {
        Vertex min = null;
        for(Vertex v : unvisitedNodes) {
            if(min == null) {
                min = v;
            }
            else if(distance.get(v) != null && distance.get(min) != null
                    && distance.get(v) < distance.get(min)) {
                min = v;
            }
        }
        return min;
    }
}

class ExamplesGraphs {
    Vertex a = new Vertex("A");
    Vertex b = new Vertex("B");
    IList<Edge> aOut = new ConsList<Edge>(new Edge(a, b, 1), new MtList<Edge>());

    Vertex one, two, three, four, five, six;
    IList<Edge> oneOut;
    IList<Edge> twoOut;
    IList<Edge> threeOut;
    IList<Edge> fourOut;
    IList<Edge> fiveOut;
    HashMap<Vertex, Vertex> predecessors;
    void initConditions() {
        this.a.setOutEdges(aOut);
        this.one = new Vertex("1");
        this.two = new Vertex("2");
        this.three = new Vertex("3");
        this.four = new Vertex("4");
        this.five = new Vertex("5");
        this.six = new Vertex("6");

        this.oneOut = new ConsList<Edge>(new Edge(one, two, 2), 
                new ConsList<Edge>(new Edge(one, three, 4), new MtList<Edge>()));
        this.twoOut = new ConsList<Edge>(new Edge(two, three, 1), 
                new ConsList<Edge>(new Edge(two, four, 4), 
                        new ConsList<Edge>(new Edge(two, five, 2), new MtList<Edge>())));
        this.threeOut = new ConsList<Edge>(new Edge(three, five, 3), 
                new MtList<Edge>());
        this.fourOut = new ConsList<Edge>(new Edge(four, six, 2), 
                new MtList<Edge>());
        this.fiveOut = new ConsList<Edge>(new Edge(five, four, 3), 
                new ConsList<Edge>(new Edge(five, six, 2), new MtList<Edge>()));
        this.one.setOutEdges(oneOut);
        this.two.setOutEdges(twoOut);
        this.three.setOutEdges(threeOut);
        this.four.setOutEdges(fourOut);
        this.five.setOutEdges(fiveOut);
    }
    void testDijkstra(Tester t) {
        this.initConditions();
        ArrayList<Vertex> abExpectedPath = new ArrayList<Vertex>();
        abExpectedPath.add(a);
        abExpectedPath.add(b);
        t.checkExpect(new GraphUtils().shortestPath(a, b), abExpectedPath);
        ArrayList<Vertex> oneSix = new ArrayList<Vertex>();
        oneSix.add(one);
        oneSix.add(two);
        oneSix.add(five);
        oneSix.add(six);
        t.checkExpect(new GraphUtils().shortestPath(one, six), oneSix);
    }
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