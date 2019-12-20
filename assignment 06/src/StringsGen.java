import tester.*;

//Assignment 6
//Chen Xianpeng
//chenxianp
//Khomiakov Kevin 
//khomiakovkevin
interface IComparator<T> {
    // Returns a negative number if t1 comes before t2 in this ordering
    // Returns zero              if t1 is the same as t2 in this ordering
    // Returns a positive number if t1 comes after t2 in this ordering
    int compare(T t1, T t2);
}

class StringLexCompGen implements IComparator<String> {
    public int compare(String t1, String t2) {
        return t1.compareTo(t2);
    }
}

class StringLengthCompGen implements IComparator<String> {
    public int compare(String t1, String t2) {
        return t1.length() - t2.length();
    }
}


interface IList<T> {
    boolean isSorted(IComparator<T> c);
    T getFirst();
    IList<T> getRest();
    boolean isConsList();
    IList<T> merge(IList<T> given, IComparator<T> c);
    //insert the given T into this sorted list by the given comparator c;
    IList<T> insert(T given, IComparator<T> c);
    IList<T> sort(IComparator<T> c);
    boolean sameList(IList<T> given);
}

class Empty<T> implements IList<T> {
    public  boolean isSorted(IComparator<T> c) {
        return true;
    }
    public  T getFirst() {
        return null;
    }
    public IList<T> getRest() {
        return this;
    }
    public boolean isConsList() {
        return false;
    }
    public IList<T> merge(IList<T> given, IComparator<T> c) {
        return given;
    }
    public IList<T> insert(T given, IComparator<T> c) {
        return new Cons<T>(given, this);
    }
    public IList<T> sort(IComparator<T> c) {
        return this;
    }
    public boolean sameList(IList<T> given) {
        return !(given.isConsList());
    }
    
}


class Cons<T> implements IList<T> {
    T first;
    IList<T> rest;
    Cons(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }
    public  T getFirst() {
        return this.first;
    }
    public IList<T> getRest() {
        return this.rest;
    }
    public boolean isConsList() {
        return true;
    }
    public  boolean isSorted(IComparator<T> c) {
        if (this.rest.isConsList()) {
            return c.compare(this.first, this.rest.getFirst()) < 0
                    && this.rest.isSorted(c);
        }
        else {
            return true;
        }
    }
    public IList<T> insert(T given, IComparator<T> c) {
        if (c.compare(given, first) < 0) {
            return new Cons<T>(given, this);
        }
        else {
            return new Cons<T>(this.first, this.rest.insert(given, c));
        }
    }
    public IList<T> merge(IList<T> given, IComparator<T> c) {
        return this.rest.merge(given.insert(first, c), c);
    }
    public IList<T> sort(IComparator<T> c) {
        return this.rest.sort(c).insert(first, c);
    }
    public boolean sameList(IList<T> given) {
        return this.first.equals(given.getFirst()) && 
                this.rest.sameList(given.getRest());
    }
    
}

class Examples {
    IList<String> genmt = new Empty<String>();
    IList<String> gen1 = new Cons<String>("hi", genmt);
    IList<String> gen2 = new Cons<String>("hey", gen1);
    IList<String> gen3 = new Cons<String>("yeah", gen2);
    IList<String> gen4 = new Cons<String>("hello", gen3);
    
    boolean testisSorted(Tester t) {
        return t.checkExpect(this.genmt.isSorted(new StringLexCompGen()), true) &&
                t.checkExpect(this.gen4.isSorted(new StringLexCompGen()), false);
    }
    boolean testgetFirst(Tester t) {
        return t.checkExpect(this.genmt.getFirst(), null) &&
                t.checkExpect(this.gen3.getFirst(), "yeah");
    }
    boolean testgetRest(Tester t) {
        return t.checkExpect(this.genmt.getRest(), genmt) &&
                t.checkExpect(this.gen4.getRest(), this.gen3);
    }
    boolean testisConsList(Tester t) {
        return t.checkExpect(this.genmt.isConsList(), false) &&
                t.checkExpect(this.gen2.isConsList(), true);
    }
    boolean testmerge(Tester t) {
        return t.checkExpect(this.gen1.merge(genmt, new StringLexCompGen()), this.gen1) &&
                t.checkExpect(this.gen1.merge(gen1, new StringLexCompGen()), 
                        new Cons<String>("hi", gen1));
    }
    boolean testinsert(Tester t) {
        return t.checkExpect(this.genmt.insert("hi", new StringLexCompGen()), gen1) &&
                t.checkExpect(this.gen1.insert("a", new StringLengthCompGen()),
                        new Cons<String>("a", gen1));
    }
    boolean testsort(Tester t) {
        return t.checkExpect(this.gen2.sort(new StringLengthCompGen()), 
                new Cons<String>("hi", new Cons<String>("hey", genmt))) &&
                t.checkExpect(this.genmt.sort(new StringLexCompGen()), genmt);
    }
    boolean testsameList(Tester t) {
        return t.checkExpect(this.gen4.sameList(gen4), true) &&
                t.checkExpect(this.gen4.sameList(gen3), false);
    }
    
    
}



