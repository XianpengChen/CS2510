
class MutablePersonList {
    Sentinel sentinel;
    MutablePersonList() {
        this.sentinel = new Sentinel(new MtLoPerson());
    }
    void removePerson(String name) {
        this.sentinel.rest.removePersonHelp(name, this.sentinel);
    }
    void addPerson(String name, int num) {
        this.sentinel.rest.addPersonHelp(name, num, this.sentinel);
    }

}

abstract class APersonList {
    abstract void removePersonHelp(String name, ANode prev);
    abstract void addPersonHelp(String name, int num, ANode prev);
}

abstract class ANode extends APersonList {
    APersonList rest;
    ANode(APersonList rest) {
        this.rest = rest;
    }
    
}

class Sentinel extends ANode {
    

    Sentinel(APersonList rest) {
        super(rest);
    }

    
    public void removePersonHelp(String name, ANode prev) {
        throw new RuntimeException("Can't try to remove on a Sentinel!");
        
    }

    void addPersonHelp(String name, int num, ANode prev) {
        throw new RuntimeException("Can't try to add on a Sentinel!");
        
    }


   
    
}

class ConsLoPerson extends ANode {
    Person data;

    ConsLoPerson(APersonList rest, Person data) {
        super(rest);
        this.data = data;
       
    }

    @Override
    public void removePersonHelp(String name, ANode prev) {
        if (this.data.name.equals(name)) {
            prev.rest = this.rest;
        }
        else {
            this.rest.removePersonHelp(name, this);
        }
        
    }

    @Override
    void addPersonHelp(String name, int num, ANode prev) {
        prev.rest = new ConsLoPerson(this, new Person(name, num));
        
        
    }
    
}

class MtLoPerson extends APersonList {
    
    MtLoPerson() {};
    public void removePersonHelp(String name, ANode prev) {
        return;
    }
    @Override
    void addPersonHelp(String name, int num, ANode prev) {
        prev.rest = new ConsLoPerson(this, new Person(name, num));
        
    }
    
}

class Person {
    String name;
    int yob;
    Person(String name, int yob) {
        this.name = name;
        this.yob = yob;
    }
}


interface IMutableList<T> {
    // adds an item to the (front of) the list
    void addToFront(T t);
    // adds an item to the end of the list
    void addToEnd(T t);
    // removes an item from list (uses intensional equality)
    void remove(T t);
    // removes the first item from the list that passes the predicate
    void remove(IPred<T> whichOne);
    // gets the numbered item (starting at index 0) of the list
    T get(int index);
    // sets (i.e. replaces) the numbered item (starting at index 0) with the given item
    void set(int index, T t);
    // inserts the given item at the numbered position
    void insert(int index, T t);
    // returns the length of the list
    int size();
    }


interface IPred<T> {
    boolean apply(T t);
}
