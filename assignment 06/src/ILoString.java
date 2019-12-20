import tester.*;

//Assignment 6
//Chen Xianpeng
//chenxianp
//Khomiakov Kevin 
//khomiakovkevin
interface ILoString {
    boolean isSorted(IStringsCompare c);
    String getFirst();
    ILoString getRest();
    ILoString merge(ILoString given, IStringsCompare c);
    ILoString insert(String given, IStringsCompare c);
    ILoString sort(IStringsCompare c);
    boolean sameList(ILoString given);
    boolean isConsLoString();

}

class MtLoString implements ILoString {
    public boolean isSorted(IStringsCompare c) {
        return true;
    }
    public String getFirst() {
        return null;
    }
    public ILoString merge(ILoString given, IStringsCompare c) {
        return given;
    }
    public  ILoString insert(String given, IStringsCompare c) {
        return new ConsLoString(given, this);
    }
    public  ILoString sort(IStringsCompare c) {
        return this;
    }
    public boolean sameList(ILoString given) {
        return !(given.isConsLoString());
    } 
    public boolean isConsLoString() {
        return false;
    }
    public  ILoString getRest() {
        return this;
    }
}


class ConsLoString implements ILoString {
    String first;
    ILoString rest;
    ConsLoString(String first, ILoString rest) {
        this.first = first;
        this.rest = rest;
    }
    public String getFirst() {
        return this.first;
    }
    public  ILoString getRest() {
        return this.rest;
    }
    public boolean isConsLoString() {
        return true;
    }
    public boolean isSorted(IStringsCompare c) {
        if (this.rest instanceof MtLoString) {
            return true;
        }
        else {
            return c.comesBefore(first, this.rest.getFirst()) && 
                    this.rest.isSorted(c);
        }
    }
    public  ILoString insert(String given, IStringsCompare c) {
        if (c.comesBefore(given, first)) {
            return new ConsLoString(given, this);
        }
        else {
            return new ConsLoString(this.first, this.rest.insert(given, c));
        }
    }
    public ILoString merge(ILoString given, IStringsCompare c) {
        return this.rest.merge(given.insert(first, c), c);
    }
    public  ILoString sort(IStringsCompare c) {
        return this.rest.sort(c).insert(first, c);
    }
    public boolean sameList(ILoString given) {
        if (given.isConsLoString()) {
            return this.first.equals(given.getFirst()) &&
                    this.rest.sameList(given.getRest());
        }
        else {
            return false;
        }
    }
}

interface IStringsCompare {
    boolean comesBefore(String s1, String s2);
    
}

class StringLexComp implements IStringsCompare {
    public boolean comesBefore(String s1, String s2) {
        return s1.compareTo(s2) <= 0;
    }
}

class StringLengthComp implements IStringsCompare {
    public boolean comesBefore(String s1, String s2) {
        return s1.length() <= s2.length();
    } 
}

class ExamplesILoString {
    ILoString a = new MtLoString();
    ILoString b = new ConsLoString("b", this.a);
    ILoString c = new ConsLoString("c", this.b);
    ILoString d = new ConsLoString("d", this.c);
    ILoString e = new ConsLoString("e", this.d);
    boolean testisSorted(Tester t) {
        return t.checkExpect(this.a.isSorted(new StringLexComp()), true) &&
                t.checkExpect(this.d.isSorted(new StringLengthComp()), true);
    }
    boolean testmerge(Tester t) {
        return t.checkExpect(this.a.merge(b, new StringLexComp()), this.b) &&
                t.checkExpect(this.b.merge(a, new StringLexComp()), this.b);
    }
    boolean testinsert(Tester t) {
        return t.checkExpect(this.a.insert("b", new StringLexComp()), this.b) &&
                t.checkExpect(this.b.insert("c", new StringLengthComp()), this.c);
    }
    boolean testsort(Tester t) {
        return t.checkExpect(this.c.sort(new StringLexComp()), 
                new ConsLoString("b",  new ConsLoString("c", new MtLoString()))) &&
                t.checkExpect(this.e.sort(new StringLengthComp()), this.e);
    }
    boolean testsameList(Tester t) {
        return t.checkExpect(this.e.sameList(e), true) &&
                t.checkExpect(this.d.sameList(e), false);
    }
    
}
   
